package com.andy.fast.util.net.body;

import com.andy.fast.util.net.listener.UploadProgressListener;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class UploadRequestBody extends RequestBody {

    private final RequestBody requestBody;

    private final UploadProgressListener uploadProgressListener;

    private final File file;

    private BufferedSink bufferedSink;

    public UploadRequestBody(File file, UploadProgressListener uploadProgressListener) {
        this.file = file;
        this.requestBody = RequestBody.create(MultipartBody.FORM, file);
        this.uploadProgressListener = uploadProgressListener;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (null == bufferedSink) {
            bufferedSink = Okio.buffer(sink(sink));
        }
        requestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long currentLength = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long totalLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);

                //增加当前写入的字节数
                currentLength += byteCount;
                //获得contentLength的值，后续不再调用
                if (totalLength == 0) {
                    totalLength = contentLength();
                }
                Observable
                        .just(currentLength)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long result) {
                                if(uploadProgressListener != null){
                                    uploadProgressListener.onProgress(currentLength, totalLength, file.getName());
                                }
                            }
                        });
            }
        };
    }
}
