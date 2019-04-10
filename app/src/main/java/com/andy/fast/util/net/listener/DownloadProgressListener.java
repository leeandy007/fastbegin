package com.andy.fast.util.net.listener;

public interface DownloadProgressListener {

    /**
     * 下载进度
     * @param currentCount
     * @param totalCount
     * @param done
     */
    void onProgress(long currentCount, long totalCount, boolean done);

}
