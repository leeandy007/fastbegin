package com.andy.fast.util;

import android.content.Context;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.util.List;

public class PermissionUtil {

    public interface PermissionListener<T> {
        void showRationale(RequestExecutor executor);
        void onGranted(T result);
        void onDenied(T result);
    }

    public static void requestPermission(final Context context, final PermissionListener<List<String>> listener, String... permissions) {
        AndPermission.with(context)
                .runtime()
                .permission(permissions)
                .rationale(new Rationale<List<String>>() {
                    @Override
                    public void showRationale(Context context, List<String> data, RequestExecutor executor) {
                        listener.showRationale(executor);
                    }
                })
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        listener.onGranted(permissions);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                            listener.onDenied(permissions);
                        }
                    }
                })
                .start();

    }

    public static void requestNotification(Context context, final PermissionListener<Void> listener) {
        AndPermission.with(context)
                .notification()
                .permission()
                .rationale(new Rationale<Void>() {
                    @Override
                    public void showRationale(Context context, Void data, RequestExecutor executor) {
                        listener.showRationale(executor);
                    }
                })
                .onGranted(new Action<Void>() {
                    @Override
                    public void onAction(Void data) {
                        listener.onGranted(data);
                    }
                })
                .onDenied(new Action<Void>() {
                    @Override
                    public void onAction(Void data) {
                        listener.onDenied(data);
                    }
                })
                .start();
    }

    public static void requestNotificationListener(Context context, final PermissionListener<Void> listener) {
        AndPermission.with(context)
                .notification()
                .listener()
                .rationale(new Rationale<Void>() {
                    @Override
                    public void showRationale(Context context, Void data, RequestExecutor executor) {
                        listener.showRationale(executor);
                    }
                })
                .onGranted(new Action<Void>() {
                    @Override
                    public void onAction(Void data) {
                        listener.onGranted(data);
                    }
                })
                .onDenied(new Action<Void>() {
                    @Override
                    public void onAction(Void data) {
                        listener.onDenied(data);
                    }
                })
                .start();
    }

    public static void requestInstallPackagePermission(Context context, final PermissionListener<List<String>> listener) {
        AndPermission.with(context)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .rationale(new Rationale<List<String>>() {
                    @Override
                    public void showRationale(Context context, List<String> data, RequestExecutor executor) {
                        listener.showRationale(executor);
                    }
                })
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        listener.onGranted(data);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        listener.onDenied(data);
                    }
                })
                .start();
    }

    public static void installPackage(Context context, File file, final PermissionListener<File> listener) {
        AndPermission.with(context)
                .install()
                .file(file)
                .rationale(new Rationale<File>() {
                    @Override
                    public void showRationale(Context context, File data, RequestExecutor executor) {
                        listener.showRationale(executor);
                    }
                })
                .onGranted(new Action<File>() {
                    @Override
                    public void onAction(File data) {
                        listener.onGranted(data);
                    }
                })
                .onDenied(new Action<File>() {
                    @Override
                    public void onAction(File data) {
                        listener.onDenied(data);
                    }
                })
                .start();
    }

    public static void setPermission(Context context, int requestCode) {
        AndPermission.with(context).runtime().setting().start(requestCode);
    }



}
