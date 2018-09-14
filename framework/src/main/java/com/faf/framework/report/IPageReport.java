package com.faf.framework.report;

/**
 * Created by p_zhbgao on 2018/5/16.
 */

public interface IPageReport {

    boolean autoReportPageIn();

    boolean autoReportPageOut();

    Object getPageReportData();

    Object getPageInReportData();

    Object getPageOutReportData();

}
