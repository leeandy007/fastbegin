package com.andy.fast.util.net.listener;

public interface DownloadProgressListener {

    /**
     * 下载进度
     * @param currentLength
     * @param totalLength
     * @param done
     */
    void onProgress(long currentLength, long totalLength, boolean done);

}
