package com.epam.billing.reportWriter;

public interface ReportWriter<T> {

        void writeReport(T report);

}
