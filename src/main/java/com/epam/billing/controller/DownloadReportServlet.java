package com.epam.billing.controller;

import com.epam.billing.dto.UserActivityUserNameIdDurationRecordingDTO;
import com.epam.billing.service.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        String userName = (String) req.getSession().getAttribute("nameOfUserForUserActivities");
        List<UserActivityUserNameIdDurationRecordingDTO> listOfUserActivity = (List<UserActivityUserNameIdDurationRecordingDTO>) req.getSession().getAttribute("userActivities");
        float totalTimeSpent = (float) req.getSession().getAttribute("totalTimeSpent");

        resp.setContentType("application/pdf");

        resp.setHeader(
                "Content-disposition",
                "inline; filename='Downloaded.pdf'");

        try {

            Document document = new Document();

            PdfWriter.getInstance(
                    document, resp.getOutputStream());

            document.open();

            document.add(new Paragraph(
                    "Report on user \"" + userName + "\" activities" + '\r'));
            for (UserActivityUserNameIdDurationRecordingDTO recordingDTO: listOfUserActivity) {
                document.add(new Paragraph(recordingDTO.getActivityCategoryName() + ":\r" + recordingDTO.getActivityName()
                        + ": " + recordingDTO.getActivityDuration() + " hours\r"));
            }
            document.add(new Paragraph("Total time spent: "+ totalTimeSpent + " hours\r"));
            document.add(new Paragraph("Report formation time: " + new Timestamp(System.currentTimeMillis()).toString()));

            document.close();
        }
        catch (DocumentException | IOException de) {
            de.printStackTrace();
        }
    }
}