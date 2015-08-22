package com.almin.mineweibo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by crazy on 2015/5/16.
 */
public class MineWeiboApplication extends Application{
    private static MineWeiboApplication instance;
//    private User mUser;
    private Bitmap mBmpAvatar;
    private int mDisplayWidth;
    private int mDisplayHeight;
    private AndroidNetworkConnectivityManager mAndroidNetworkConnectivityManager;
    private static final DateFormat sDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");


    @Override
    public void onCreate() {
        Log.e("onCreate", "JbsApplication----onCreate");
        super.onCreate();
        if (instance == null) {
            instance = this;
        }
        MineWeiboConfiguration.init();
//        initImageLoader(getApplicationContext());
        //image-loader初始化
        // 其他初始化
        // 字体资源初始化
    }
//
//    @SuppressWarnings("deprecation")
//    private void initImageLoader(Context context) {
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//                context).threadPriority(Thread.NORM_PRIORITY - 1)
//                .denyCacheImageMultipleSizesInMemory()
//                .discCacheFileNameGenerator(new Md5FileNameGenerator())
//                .tasksProcessingOrder(QueueProcessingType.FIFO)
//                .writeDebugLogs()
//                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
//                .build();
//        ImageLoader.getInstance().init(config);
//    }

    public static MineWeiboApplication getInstance() {
        return instance;
    }

//    public User getUser() {
//        return mUser;
//    }
//
//    public void setUser(User user) {
//        mUser = user;
//    }

    public Bitmap getBmpAvatar() {
        return mBmpAvatar;
    }

    public void setBmpAvatar(Bitmap avatar) {
        this.mBmpAvatar = avatar;
    }

    public AndroidNetworkConnectivityManager getAndroidNetworkConnectivityManager() {
        if (mAndroidNetworkConnectivityManager == null) {
            mAndroidNetworkConnectivityManager = new AndroidNetworkConnectivityManager();
        }
        return mAndroidNetworkConnectivityManager;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //clean the cache when app close
        cleanCache();
    }

    private void cleanCache() {

    }

    public static void showInputMethod(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideInputMethod(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            IBinder binder = view.getWindowToken();
            imm.hideSoftInputFromWindow(binder,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
    }

    public static String saveImage(String directory, String filename,
                                   Bitmap source, byte[] jpegData, int quality,boolean isUpdate) {
        OutputStream outputStream = null;
        String filePath = directory + filename;
        try {
            File dir = new File(directory);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(directory, filename);
            if(isUpdate){
                file.delete();
            }
            if (file.createNewFile()) {
                outputStream = new FileOutputStream(file);
                if (source != null) {
                    source.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                } else {
                    outputStream.write(jpegData);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Throwable t) {
                }
            }
        }
        return filePath;
    }

    public boolean isNetworkAvailable() {
        try {
            ConnectivityManager cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cManager != null) {
                NetworkInfo[] infos = cManager.getAllNetworkInfo();
                if (infos != null) {
                    for (int i = 0; i < infos.length; i++) {
                        if (infos[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isTextEmptyOrNull(String text) {
        return TextUtils.isEmpty(text) || "null".equalsIgnoreCase(text);
    }

    public int getDisplayWidth() {
        return mDisplayWidth;
    }

    public void setDisplayWidth(int displayWidth) {
        this.mDisplayWidth = displayWidth;
    }

    public int getDisplayHeight() {
        return mDisplayHeight;
    }

    public void setDisplayHeight(int displayHeight) {
        this.mDisplayHeight = displayHeight;
    }

    public static String formatDate(Date date) {
        return sDateFormat.format(date);
    }

    public static String getNowDateTime(){
        return sDateFormat.format(new Date());
    }

    public static boolean isHaveSDcard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

}
