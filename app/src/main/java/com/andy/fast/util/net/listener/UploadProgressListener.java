package com.andy.fast.util.net.listener;

public interface UploadProgressListener {

    /**
     * 上传进度
     * @param currentCount
     * @param totalCount
     */
    void onProgress(long currentCount, long totalCount);

}
