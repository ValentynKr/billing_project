package com.epam.billing.reportWriter.impl;

import com.epam.billing.dto.UserActivityUserNameIdDurationRecordingDTO;
import com.epam.billing.entity.UserActivityReport;
import com.epam.billing.reportWriter.ReportWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;

import java.sql.Timestamp;

public class UserActivityReportWriter implements ReportWriter<UserActivityReport> {

    Document document;

    public UserActivityReportWriter(Document document) {
        this.document = document;
    }

    @Override
    public void writeReport(UserActivityReport userActivityReport) {
        Font regular = new Font(Font.FontFamily.HELVETICA, 12);
        Font bold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph categoryNameHeading = new Paragraph("Category name", bold);
        Paragraph activityNameHeading = new Paragraph("Activity name", bold);
        Paragraph durationHeading = new Paragraph("Duration", bold);
        Paragraph headingOfReport = new Paragraph("Report on user \"" + userActivityReport.getUserName() + "\" activities", bold);
        headingOfReport.setAlignment(Element.ALIGN_CENTER);

        try {
            document.open();
            document.add(headingOfReport);
            document.add(new Paragraph(System.lineSeparator()));
            PdfPTable table = new PdfPTable(3);
            table.addCell(categoryNameHeading);
            table.addCell(activityNameHeading);
            table.addCell(durationHeading);
            for (UserActivityUserNameIdDurationRecordingDTO recordingDTO : userActivityReport.getListOfUserActivity()) {
                table.addCell(new Paragraph(recordingDTO.getActivityCategoryName(), regular));
                table.addCell(new Paragraph(recordingDTO.getActivityName(), regular));
                table.addCell(new Paragraph(String.valueOf(recordingDTO.getActivityDuration()), regular));
            }
            document.add(table);
            document.add(new Paragraph(System.lineSeparator()));
            document.add(new Paragraph("Total time spent: " + userActivityReport.getTotalTimeSpent() + " hours", bold));
            document.add(new Paragraph("Report formation time: " + new Timestamp(System.currentTimeMillis()).toString(), regular));
            document.close();

        } catch (DocumentException de) {
            de.printStackTrace();
        }
    }
}
