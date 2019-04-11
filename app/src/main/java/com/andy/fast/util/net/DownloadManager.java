package com.andy.fast.util.net;

import com.andy.fast.bean.DownInfo;
import com.andy.fast.enums.DownState;
import com.andy.fast.util.net.RxRest.RxRestService;
import com.andy.fast.util.net.exception.HttpTimeException;
import com.andy.fast.util.net.exception.RetryWhenNetworkException;
import com.andy.fast.util.net.interceptor.DownloadInterceptor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class DownloadManager {

    private static final int TIME_OUT = 6;

    /*记录下载数据*/
    private Set<DownInfo> downInfos;

    /*单利对象*/
    private volatile static DownloadManager INSTANCE;

    /*回调sub队列*/
    private HashMap<String, DownProgressObserver> subMap;

    /**
     * 返回全部正在下载的数据
     */
    public Set<DownInfo> getDownInfos() {
        return downInfos;
    }

    private DownloadManager (){
        downInfos=new HashSet<>();
        subMap=new HashMap<>();
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static DownloadManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DownloadManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DownloadManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 开始下载
     */
    public void start(final DownInfo downInfo){
        /*正在下载不处理*/
        if(downInfo==null||subMap.get(downInfo.getUrl())!=null){
            subMap.get(downInfo.getUrl()).setDownInfo(downInfo);
            return;
        }
        DownProgressObserver observer = new DownProgressObserver(downInfo);
        /*记录回调sub*/
        subMap.put(downInfo.getUrl(), observer);
        RxRestService service;
        if(downInfos.contains(downInfo)){
            service = downInfo.getService();
        } else {
            DownloadInterceptor interceptor = new DownloadInterceptor(observer);
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getBasUrl(downInfo.getUrl()))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
            service = retrofit.create(RxRestService.class);
            downInfo.setService(service);
            downInfos.add(downInfo);
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("RANGE", "bytes=" + downInfo.getCurrentLength() + "-");
        Observable<ResponseBody> download = service.download(downInfo.getUrl(), headers);
        download.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .retryWhen(new RetryWhenNetworkException())
                .map(new Function<ResponseBody, DownInfo>() {
                    @Override
                    public DownInfo apply(ResponseBody responseBody) throws Exception {
                        writeCaches(responseBody, new File(downInfo.getSavePath()), downInfo);
                        return downInfo;
                    }
                })/*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*数据回调*/
                .subscribe(observer);;
    }

    /**
     * 停止下载
     */
    public void stop(DownInfo downInfo){
        if(downInfo == null) return;
        downInfo.setState(DownState.STOP);
        downInfo.getListener().onStop();
        if(subMap.containsKey(downInfo.getUrl())) {
            DownProgressObserver observer = subMap.get(downInfo.getUrl());
            observer.dispose();
            subMap.remove(downInfo.getUrl());
        }
    }

    /**
     * 暂停下载
     */
    public void pause(DownInfo downInfo){
        if(downInfo == null) return;
        downInfo.setState(DownState.PAUSE);
        downInfo.getListener().onPuase();
        if(subMap.containsKey(downInfo.getUrl())) {
            DownProgressObserver observer = subMap.get(downInfo.getUrl());
            observer.dispose();
            subMap.remove(downInfo.getUrl());
        }
    }

    /**
     * 停止全部下载
     */
    public void stopAll(){
        for (DownInfo downInfo : downInfos) {
            stop(downInfo);
        }
        subMap.clear();
        downInfos.clear();
    }

    /**
     * 暂停全部下载
     */
    public void pauseAll(){
        for (DownInfo downInfo : downInfos) {
            stop(downInfo);
        }
        subMap.clear();
        downInfos.clear();
    }

    /**
     * 移除下载数据
     */
    public void remove(DownInfo downInfo){
        subMap.remove(downInfo.getUrl());
        downInfos.remove(downInfo);
    }

    /**
     * 写入文件
     *
     * @param file
     * @param info
     * @throws IOException
     */
    private void writeCaches(ResponseBody responseBody, File file, DownInfo info) {
        try {
            RandomAccessFile randomAccessFile = null;
            FileChannel channelOut = null;
            InputStream inputStream = null;
            try {
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                long allLength = 0 == info.getTotalLength() ? responseBody.contentLength() : info.getCurrentLength() + responseBody
                        .contentLength();

                inputStream = responseBody.byteStream();
                randomAccessFile = new RandomAccessFile(file, "rwd");
                channelOut = randomAccessFile.getChannel();
                MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                        info.getCurrentLength(), allLength - info.getCurrentLength());
                byte[] buffer = new byte[1024 * 4];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    mappedBuffer.put(buffer, 0, len);
                }
            } catch (IOException e) {
                throw new HttpTimeException(e.getMessage());
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (channelOut != null) {
                    channelOut.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            }
        } catch (IOException e) {
            throw new HttpTimeException(e.getMessage());
        }
    }

    /**
     * 读取baseurl
     * @param url
     * @return
     */
    private static String getBasUrl(String url) {
        String head = "";
        int index = url.indexOf("://");
        if (index != -1) {
            head = url.substring(0, index + 3);
            url = url.substring(index + 3);
        }
        index = url.indexOf("/");
        if (index != -1) {
            url = url.substring(0, index + 1);
        }
        return head + url;
    }

}
