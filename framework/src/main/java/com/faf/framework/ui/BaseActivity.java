package com.faf.framework.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.faf.framework.report.IPageReport;
import com.faf.framework.report.ReportManager;
import com.githang.statusbar.StatusBarCompat;

/**
 * @ClassName: BaseActivity
 * @Description: java类作用描述
 * @Author: flying
 * @CreateDate: 2018/9/13 15:14
 */
public abstract class BaseActivity extends AppCompatActivity implements IPageReport {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (autoTransparentStatusbar()) {
            transparentStatusbar();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ReportManager.getInstance().isPageAutoReport() && autoReportPageIn()) {
            ReportManager.getInstance().reportPageIn(getPageInReportData());
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (ReportManager.getInstance().isPageAutoReport() && autoReportPageOut()) {
            ReportManager.getInstance().reportPageOut(getPageOutReportData());
        }
    }

    @Override
    public boolean autoReportPageIn() {
        return true;
    }

    @Override
    public boolean autoReportPageOut() {
        return true;
    }

    @Override
    public Object getPageInReportData() {
        return getPageReportData();
    }

    @Override
    public Object getPageOutReportData() {
        return getPageReportData();
    }

    @Override
    public Object getPageReportData() {
        return null;
    }

    private boolean autoTransparentStatusbar() {
        return true;
    }

    protected void transparentStatusbar() {
        Window win = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// 透明状态栏
            win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            win.setStatusBarColor(Color.TRANSPARENT);// SDK21
            //lightStatusBar = true,第三方库会将状态栏图标、字体颜色等设置为深色模式
            //6.0以上设置状态栏为全透明色#00ffffff
            StatusBarCompat.setStatusBarColor(this, Color.parseColor("#00ffffff"), true);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            win.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                win.setStatusBarColor(Color.TRANSPARENT);// SDK21
            }
            //6.0以下设置状态栏为半透明色#32000000，状态栏会有1个半透明背景色
            StatusBarCompat.setStatusBarColor(this, Color.parseColor("#32000000"), true);
        }
    }

}
