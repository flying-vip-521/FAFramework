package com.faf.framework.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import com.faf.framework.FrameworkManager;
import com.faf.framework.log.L;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;


/**
 * @ClassName: AppUtils
 * @Description: 应用信息工具类
 * @Author: flying
 * @CreateDate: 2018/9/12 16:03
 */
public class AppUtils {
    private static final String TAG = AppUtils.class.getSimpleName();

    public static String getPackageName(Context context) {
        if (context != null) {
            return context.getPackageName();
        }
        return "";
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = mActivityManager.getRunningAppProcesses();
        if (list == null || list.size() <= 0) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : list) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


    /**
     * 从配置资源获取字符串
     */
    public static String getResString(@StringRes int resId) {
        Context context = FrameworkManager.getInstance().getContext();
        if (context == null) {
            return null;
        }
        return context.getResources().getString(resId);
    }

    /**
     * 检查App是否处于前台
     */
    public static boolean isAppForeground(Context context) {
        if (context == null) {
            return false;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return false;
        }
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (!appProcess.processName.equals(packageName)) {
                continue;
            }
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取应用名称
     * <p>框架层无法直接获取应用层的String资源，因此在框架需要应用名称的时候可以通过此方法获取</p>
     *
     * @return String 应用名称
     */
    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            applicationInfo = null;
        }
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }

    /**
     * 获取应用图标Drawable资源
     */
    @Nullable
    public static Drawable getApplicationIcon(Context context) {
        PackageManager packageManager;
        ApplicationInfo applicationInfo;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getApplicationContext().getPackageName(), 0);
            return applicationInfo.loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用版本
     */
    @Nullable
    public static String getApplicationVersionName(Context context) {
        PackageManager packageManager;
        PackageInfo packageInfo;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            packageInfo = packageManager.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用版本代号
     */
    public static int getApplicationVersionCode(Context context) {
        PackageManager packageManager;
        PackageInfo packageInfo;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            packageInfo = packageManager.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取应用签名
     */
    @Nullable
    public static String getAppSignature(Context context) {
        PackageManager packageManager;
        PackageInfo packageInfo;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            packageInfo = packageManager.getPackageInfo(context.getApplicationContext().getPackageName(), PackageManager.GET_SIGNATURES);
            Signature signature = packageInfo.signatures[0];

            CertificateFactory certFactory = CertificateFactory
                    .getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory
                    .generateCertificate(new ByteArrayInputStream(signature.toByteArray()));
            String pubKey = cert.getPublicKey().toString();
            String signNumber = cert.getSerialNumber().toString();
            return pubKey + "|" + signNumber;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            L.d(TAG, "package name not found");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }

}
