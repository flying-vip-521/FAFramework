package com.faf.framework.report;

import com.faf.framework.log.Logger;
import com.google.gson.Gson;

/**
 * @ClassName: ReportManager
 * @Description: 上报管理器
 * @Author: flying
 * @CreateDate: 2018/9/13 15:46
 */
public class ReportManager implements IReport {
    private static final String TAG = ReportManager.class.getSimpleName();
    private boolean pageAutoReport = false;
    private IReport reporter = null;
    private volatile Gson gson;

    private ReportManager() {
    }

    private static class Inner {
        private static final ReportManager INSTANCE = new ReportManager();
    }

    public static ReportManager getInstance() {
        return Inner.INSTANCE;
    }

    public boolean isPageAutoReport() {
        return pageAutoReport;
    }

    public void enablePageAutoReport() {
        pageAutoReport = true;
    }

    public void setReporter(IReport reporter) {
        this.reporter = reporter;
    }

    @Override
    public void report(Object data) {
        if (reportReady()) {
            if (data == null) {
                Logger.w(TAG, "report: data is null");
                return;
            }
            log("report", data);
            reporter.report(data);
        }
    }

    @Override
    public void reportPageIn(Object data) {
        if (reportReady()) {
            if (data == null) {
                Logger.w(TAG, "reportPageIn: data is null");
                return;
            }
            log("reportPageIn", data);
            reporter.reportPageIn(data);
        }
    }

    @Override
    public void reportPageOut(Object data) {
        if (reportReady()) {
            if (data == null) {
                Logger.w(TAG, "reportPageOut: data is null");
                return;
            }
            log("reportPageOut", data);
            reporter.reportPageOut(data);
        }
    }

    boolean reportReady() {
        if (reporter == null) {
            throw new RuntimeException("you should setReporter first");
        }
        return true;
    }

    void log(String fun, Object data) {
        if (gson == null) {
            gson = new Gson();
        }
        Logger.v(TAG, fun + ":" + gson.toJson(data));
    }
}
