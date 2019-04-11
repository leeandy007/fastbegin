package com.andy.fast.util.net.listener;

public interface UploadProgressListener {

    /**
     * 上传进度
     * @param currentLength
     * @param totalLength
     */
    void onProgress(long currentLength, long totalLength);

}
