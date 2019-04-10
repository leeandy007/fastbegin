package com.andy.fast.util.net.body;

import com.andy.fast.util.net.listener.DownloadProgressListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class DownloadResponseBody extends ResponseBody {

    private final ResponseBody responseBody;

    private final DownloadProgressListener downloadProgressListener;

    private BufferedSource bufferedSource;

    public DownloadResponseBody(ResponseBody responseBody, DownloadProgressListener downloadProgressListener) {
        this.responseBody = responseBody;
        this.downloadProgressListener = downloadProgressListener;
    }


    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(BufferedSource source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if(downloadProgressListener != null){
                    downloadProgressListener.onProgress(totalBytesRead, contentLength(), bytesRead == -1);
                }
                return bytesRead;
            }
        };
    }


}
