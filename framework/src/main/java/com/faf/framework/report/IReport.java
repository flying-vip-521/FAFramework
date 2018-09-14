package com.faf.framework.report;

/**
 * @ClassName: IReport
 * @Description: java类作用描述
 * @Author: flying
 * @CreateDate: 2018/9/13 15:47
 */
public interface IReport {
    void report(Object data);

    void reportPageIn(Object data);

    void reportPageOut(Object data);
}
