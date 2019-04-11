package com.andy.fast.bean;

import com.andy.fast.enums.DownState;
import com.andy.fast.util.net.DownloadListener;
import com.andy.fast.util.net.RxRest.RxRestService;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true)
public class DownInfo implements Serializable {

    /* 存储位置 */
    private String savePath;
    /* 文件总长度 */
    private long totalLength;
    /* 下载长度 */
    private long currentLength;
    /* 下载该文件的url */
    private String url;

    private RxRestService service;

    private DownloadListener listener;

    private DownState state;

    public DownInfo(String url, String savePath, DownloadListener listener) {
        this.savePath = savePath;
        this.url = url;
        this.listener = listener;
    }
}
