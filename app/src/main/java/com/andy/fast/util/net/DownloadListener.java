package com.andy.fast.util.net;

public abstract class DownloadListener<Result> {

    /**
     * 成功后回调方法
     * @param result
     */
    public abstract void onSuccess(Result result);

    /**
     * 开始下载
     */
    public abstract void onStart();

    /**
     * 完成下载
     */
    public abstract void onComplete();

    /**
     * 下载进度
     * @param readLength
     * @param countLength
     */
    public abstract void onProgress(long readLength, long countLength);

    /**
     * 失败或者错误方法
     * 主动调用，更加灵活
     * @param t
     */
    public  void onError(Throwable t){

    }

    /**
     * 暂停下载
     */
    public void onPuase(){

    }

    /**
     * 停止下载销毁
     */
    public void onStop(){

    }


}
