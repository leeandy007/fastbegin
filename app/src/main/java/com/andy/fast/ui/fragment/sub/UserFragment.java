package com.andy.fast.ui.fragment.sub;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.fast.ui.fragment.base.BaseFragment;
import com.andy.fast.util.IntentUtil;
import com.andy.fast.util.StringUtil;
import com.andy.fast.util.ToastUtil;
import com.bumptech.glide.Glide;
import com.qiji.qixin.R;
import com.qiji.qixin.bean.BaseBean;
import com.qiji.qixin.bean.LoginBean;
import com.qiji.qixin.bean.UpdateImageBean;
import com.qiji.qixin.common.Constants;
import com.qiji.qixin.common.MainApplication;
import com.qiji.qixin.presenter.fragment.UserPresenter;
import com.qiji.qixin.ui.activity.JoinGroupActiviy;
import com.qiji.qixin.ui.activity.PasswordActivity;
import com.qiji.qixin.ui.activity.SettingsActivity;
import com.qiji.qixin.util.CacheUtil;
import com.qiji.qixin.util.CustomUtil;
import com.qiji.qixin.util.FileStorage;
import com.qiji.qixin.util.GlideCacheUtil;
import com.qiji.qixin.view.fragment.UserView;
import com.qiji.qixin.widget.CircleImageView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by guoenbo on 2018/6/4.
 */

public class UserFragment  {

    @BindView(R.id.rl_setting)
    RelativeLayout rl_setting;
    @BindView(R.id.btn_exit)
    Button btn_exit;

    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.iv_user)
    CircleImageView iv_user;


    private LoginBean mLoginBean;
    private CacheUtil mCache;
    private File cacheFile;
    private String cachPath;
    private String HeadUrl,ImageUrl;
    private Uri imageUri;
    private static final String imagepath = Constants.PATH_HEAD + System.currentTimeMillis() + ".jpg";

    @Override
    protected int getLayout(Bundle bundle) {
        return R.layout.fragment_school;
    }

    @Override
    protected UserPresenter<UserView> CreatePresenter() {
        return new UserPresenter<>();
    }

    @Override
    protected void initData() {
        mCache = CacheUtil.get(MainApplication.getContext());
        mLoginBean = MainApplication.getLoginBean();
        if (!StringUtil.isEmpty(mLoginBean)){
            btn_exit.setVisibility(View.VISIBLE);
        }else {
            btn_exit.setVisibility(View.GONE);
        }
        tv_nickname.setText(mLoginBean.getNickname());
        tv_username.setText("账号:"+mLoginBean.getLoginname());

        tv_username.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        tv_username.getPaint().setAntiAlias(true);
        Glide.with(_context).load(mLoginBean.getUserimg()).error(R.mipmap.user_image).into(iv_user);
    }

    @Override
    public void loadView() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil.obtain().Short(_context, s);

    }


    @OnClick({R.id.rl_setting,R.id.rl_password,R.id.btn_exit,R.id.rl_shenqing,R.id.iv_user})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_setting:
                IntentUtil.startActivity(_context, SettingsActivity.class,bundle);
                break;
            case R.id.rl_password:
                IntentUtil.startActivityForResult(_context, PasswordActivity.class,bundle,1);
                break;
            case R.id.btn_exit:
                mCache.clear();
                getActivity().finish();
                break;
            case R.id.rl_shenqing:
                IntentUtil.startActivity(_context, JoinGroupActiviy.class,bundle);
                break;
            case R.id.iv_user:
                if (CustomUtil.sdCardExist()) {
                    showHeadDialog();//弹出菜单选择拍照或相册
                    cachPath = Constants.PATH_HEAD + System.currentTimeMillis() + "-crop" + ".jpg";
                    cacheFile = new File(cachPath);
                } else {
                    ToastUtil.obtain().Short(_context,"未检测到SD卡，无法操作喔~");
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }



    @SuppressWarnings("deprecation")
    private void showHeadDialog() {
        final Dialog dialog = new Dialog(_context, R.style.custom_window_dialog);
        LinearLayout layout = (LinearLayout) ((LayoutInflater) _context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.dialog_choose_userhead, null);

        ImageView imgcatch = (ImageView) layout
                .findViewById(R.id.choose_userhead_catch);
        ImageView imgPhoto = (ImageView) layout
                .findViewById(R.id.choose_userhead_photo);
        //isGetPermission = AndPermission.hasPermission(_context, Manifest.permission.CAMERA );

        imgcatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // if (!isGetPermission) {
                AndPermission.with(_context)
                        .requestCode(100)
                        .permission(Permission.CAMERA)
                        .callback(new PermissionListener() {
                            @Override
                            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                                switch (requestCode) {
                                    case 100:
                                        openCamera();
                                        break;
                                }
                            }

                            @Override
                            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                                AndPermission.defaultSettingDialog(_context).show();
                            }
                        })
                        .rationale(new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
                                AndPermission.rationaleDialog(_context, rationale).show();
                            }
                        }).start();
                dialog.dismiss();
            }
        });

        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AndPermission.with(_context)
                        .requestCode(200)
                        .permission(Permission.CAMERA)
                        .callback(new PermissionListener() {
                            @Override
                            public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                                switch (requestCode) {
                                    case 200:
                                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(intent, 2);
                                        dialog.dismiss();
                                        break;
                                }
                            }

                            @Override
                            public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                                AndPermission.defaultSettingDialog(_context).show();
                                dialog.dismiss();
                            }
                        })
                        .rationale(new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
                                AndPermission.rationaleDialog(_context, rationale).show();
                            }
                        }).start();

            }
        });

        dialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        WindowManager windowManager = ((Activity) _context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.8); // 设置宽度
        lp.height = (int) (display.getHeight() * 0.4); // 设置宽度
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    protected void doActivityResult(int requestCode, Intent data) {
        super.doActivityResult(requestCode, data);
        switch (requestCode) {
            case 1:// 拍照
                startPhotoZoom(new File(imagepath));
                break;
            case 2:// 相册
                // 判断手机系统版本号
                if (Build.VERSION.SDK_INT >= 19) {
                    // 4.4及以上系统使用这个方法处理图片
                    handleImageOnKitKat(data);
                } else {
                    // 4.4以下系统使用这个方法处理图片
                    handleImageBeforeKitKat(data);
                }
                break;
            case 3:// 裁剪
                if (new File(cachPath).exists()) {
                    HeadUrl = cachPath;
                    presenter.updateImage(HeadUrl);
                }
                break;

        }
    }

    /**
     * 相册图片回调
     * android 4.4之后调用
     *
     * @param data
     * @return
     */
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();

        String imagePath = uriToPath(uri);
//        displayImage(imagePath); // 根据图片路径显示图片

        startPhotoZoom(new File(imagePath));
    }

    /**
     * 将Uri转换成Path
     * android 4.4之后调用
     *
     * @param uri
     * @return
     */
    @TargetApi(19)
    private String uriToPath(Uri uri) {
        String path = null;
        if (DocumentsContract.isDocumentUri(_context, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                path = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // content类型的Uri，使用特殊方式处理
            path = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // file类型的Uri，直接获取图片路径即可
            path = uri.getPath();
        }
        return path;
    }

    /**
     * 相册图片回调
     * android 4.4之前调用
     *
     * @param data
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        startPhotoZoom(new File(imagePath));
    }

    /**
     * 将Uri转换成Path
     *
     * @param uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = _context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 适配 android 7.0 的裁剪图片方法实现
     *
     * @param file
     */
    public void startPhotoZoom(File file) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            if (Build.VERSION.SDK_INT >= 24) {
                intent.setDataAndType(getImageContentUri(_context, file), "image/*");//自己使用Content Uri替换File Uri
            } else {
                intent.setDataAndType(Uri.fromFile(file), "image/*");//直接使用File Uri
            }
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 150);
            intent.putExtra("outputY", 150);
            intent.putExtra("return-data", false);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cacheFile));//定义输出的File Uri
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(intent, 3);
        } catch (ActivityNotFoundException e) {
            ToastUtil.obtain().Short(_context,"设备不支持裁剪操作");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 适配 android 7.0 的拍照
     */
    private void openCamera() {  //调用相机拍照
        Intent intent = new Intent();
        File file = new FileStorage().createIconFile(); //工具类，稍后会给出

        if (Build.VERSION.SDK_INT >= 24) {  //针对Android7.0，需要通过FileProvider封装过的路径，提供给外部调用
            imageUri = FileProvider.getUriForFile(_context, "com.qiji.qixin.fileprovider", new File(imagepath));//通过FileProvider创建一个content类型的Uri，进行封装
        } else { //7.0以下，如果直接拿到相机返回的intent值，拿到的则是拍照的原图大小，很容易发生OOM，所以我们同样将返回的地址，保存到指定路径，返回到Activity时，去指定路径获取，压缩图片
            imageUri = Uri.fromFile(new File(imagepath));
        }
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        startActivityForResult(intent, 1);//启动拍照
    }


    /**
     * 获取符合 android 7.0 的 Content Uri
     *
     * @param context
     * @param imageFile
     * @return
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 防止某些设备拍照时自动旋转而丢失数据，在manifest中也需要配置
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void loadImage(UpdateImageBean bean) {
        ImageUrl = bean.getImgurl_200();
        Glide.with(_context).load(ImageUrl).centerCrop().into(iv_user);
        mLoginBean = MainApplication.getLoginBean();
        mLoginBean.setUserimg(ImageUrl);
        mCache.put(Constants.mLoginBean,mLoginBean);
        Map<String,Object> map =  new HashMap<>();
        map.put("id",MainApplication.getLoginBean().getId());
        map.put("userimg",ImageUrl);
        presenter.changeImage(map);
    }

    @Override
    public void changeImage(BaseBean bean) {
       if (bean.getErrcode().equals("0")){
           ToastUtil.obtain().Short(_context,"修改成功");
       }
    }
}
