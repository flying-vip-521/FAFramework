package com.faf.framework.ui;

import android.app.Fragment;
import com.faf.framework.report.IPageReport;
import com.faf.framework.report.ReportManager;

/**
 * @ClassName: BaseFragment
 * @Description: java类作用描述
 * @Author: flying
 * @CreateDate: 2018/9/13 16:37
 */
public class BaseFragment extends Fragment implements IPageReport {

    @Override
    public void onResume() {
        super.onResume();
        if (ReportManager.getInstance().isPageAutoReport() && autoReportPageIn()) {
            ReportManager.getInstance().reportPageIn(getPageInReportData());
        }
    }


    @Override
    public void onPause() {
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
    public Object getPageReportData() {
        return null;
    }

    @Override
    public Object getPageInReportData() {
        return getPageReportData();
    }

    @Override
    public Object getPageOutReportData() {
        return getPageReportData();
    }
}
