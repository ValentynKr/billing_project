package com.epam.billing.controller;

import com.epam.billing.dto.UserActivityUserNameIdDurationRecordingDTO;
import com.epam.billing.entity.UserActivityReport;
import com.epam.billing.service.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.TextField;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(urlPatterns = {"/downloadReport"})
public class DownloadReportServlet extends HttpServlet {
    private UserService userService;
    private UserActivityService userActivityService;
    private LanguageService languageService;


    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
        languageService = (LanguageService) getServletContext().getAttribute("languageService");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        String userName = (String) session.getAttribute("nameOfUserForUserActivities");
        List<UserActivityUserNameIdDurationRecordingDTO> listOfUserActivity = (List<UserActivityUserNameIdDurationRecordingDTO>) session.getAttribute("userActivities");
        float totalTimeSpent = (float) session.getAttribute("totalTimeSpent");
        resp.setContentType("application/pdf");
        resp.setHeader("Content-disposition","inline; filename="+userName+"_activities_report.pdf");
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, resp.getOutputStream());
            ReportWriter reportWriter = new ReportWriter(document);
            UserActivityReport userActivityReport = new UserActivityReport()
                    .setListOfUserActivity(listOfUserActivity)
                    .setUserName(userName)
                    .setTotalTimeSpent(totalTimeSpent);
            reportWriter.writeReport(userActivityReport);
        }
        catch (IOException | DocumentException de) {
            de.printStackTrace();
        }
    }
}