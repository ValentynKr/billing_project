package com.epam.billing.controller;

import com.epam.billing.dto.UserActivityUserNameIdDurationRecordingDTO;
import com.epam.billing.entity.UserActivityReport;
import com.epam.billing.reportWriter.impl.UserActivityReportWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/downloadReport"})
public class DownloadReportServlet extends HttpServlet {

    @Override
    public void init() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String userName = (String) session.getAttribute("nameOfUserForUserActivities");
        List<UserActivityUserNameIdDurationRecordingDTO> listOfUserActivity = (List<UserActivityUserNameIdDurationRecordingDTO>) session.getAttribute("userActivities");
        float totalTimeSpent = (float) session.getAttribute("totalTimeSpent");
        resp.setContentType("application/pdf");
        resp.setHeader("Content-disposition","attachment; filename="+ userName +"_activities_report.pdf");
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, resp.getOutputStream());
            UserActivityReport userActivityReport = new UserActivityReport()
                    .setListOfUserActivity(listOfUserActivity)
                    .setUserName(userName)
                    .setTotalTimeSpent(totalTimeSpent);
            UserActivityReportWriter userActivityReportWriter = new UserActivityReportWriter(document);
            userActivityReportWriter.writeReport(userActivityReport);
        }
        catch (IOException | DocumentException de) {
            de.printStackTrace();
        }
    }
}