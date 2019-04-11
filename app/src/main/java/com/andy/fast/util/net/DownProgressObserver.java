package com.andy.fast.util.net;

import com.andy.fast.bean.DownInfo;
import com.andy.fast.enums.DownState;
import com.andy.fast.util.net.listener.DownloadProgressListener;

import java.lang.ref.SoftReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;

public class DownProgressObserver<T> extends ResourceObserver<T> implements DownloadProgressListener {

    //弱引用结果回调
    private SoftReference<DownloadListener> listener;

    /*下载数据*/
    private DownInfo downInfo;


    public void setDownInfo(DownInfo downInfo) {
        this.listener = new SoftReference<>(downInfo.getListener());
        this.downInfo = downInfo;
    }



    public DownProgressObserver(DownInfo downInfo) {
        this.listener = new SoftReference<>(downInfo.getListener());
        this.downInfo = downInfo;
    }

    @Override
    public void onProgress(long currentLength, long totalLength, boolean done) {
        if (downInfo.getTotalLength() > totalLength) {
            currentLength = downInfo.getTotalLength() - totalLength + currentLength;
        } else {
            downInfo.setTotalLength(totalLength);
        }
        downInfo.setCurrentLength(currentLength);
        if(listener.get() == null)return;
        Observable
                .just(downInfo.getTotalLength())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        /*如果暂停或者停止状态延迟，不需要继续发送回调，影响显示*/
                        if (downInfo.getState() == DownState.PAUSE || downInfo.getState() == DownState.STOP) return;
                        downInfo.setState(DownState.DOWN);
                        listener.get().onProgress(downInfo.getCurrentLength(), downInfo.getTotalLength());
                    }
                });
    }

    @Override
    protected void onStart() {
        if(listener.get() != null){
            listener.get().onStart();
        }
        downInfo.setState(DownState.START);
    }

    @Override
    public void onNext(T t) {
        if(listener.get() != null){
            listener.get().onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable t) {
        if(listener.get() != null){
            listener.get().onError(t);
        }
        DownloadManager.getInstance().remove(downInfo);
        downInfo.setState(DownState.ERROR);
    }

    @Override
    public void onComplete() {
        if(listener.get() != null){
            listener.get().onComplete();
        }
        DownloadManager.getInstance().remove(downInfo);
        downInfo.setState(DownState.FINISH);
    }


}
