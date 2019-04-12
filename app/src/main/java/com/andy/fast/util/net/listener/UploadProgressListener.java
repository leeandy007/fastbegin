package com.andy.fast.util.net.listener;

import java.io.File;

public interface UploadProgressListener {

    /**
     * 上传进度
     * @param currentLength
     * @param totalLength
     */
    void onProgress(long currentLength, long totalLength, File file);

}
