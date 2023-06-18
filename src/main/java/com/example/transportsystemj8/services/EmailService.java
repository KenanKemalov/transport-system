package com.example.transportsystemj8.services;

import com.example.transportsystemj8.data.entity.Ticket;
import com.example.transportsystemj8.data.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;


import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;


    //still needs to be edited - does not include CSS in the pdf attachment
    public void sendEmail(String toEmail, Ticket ticket) throws MessagingException {
        Context context = new Context();
        context.setVariable("depstation", ticket.getTripId().getLocationFrom().getLocationName());
        context.setVariable("arrstation", ticket.getTripId().getLocationTo().getLocationName());
        context.setVariable("passenger", ticket.getCustomerName());
        context.setVariable("transport", ticket.getTripId().getTransportTypeId().getTransportTypeName());
        context.setVariable("departure", ticket.getTripId().getDeparture() + " " + ticket.getTripId().getTimeOfDeparture());
        context.setVariable("seat", ticket.getSeatNumber());
        context.setVariable("arrival", ticket.getTripId().getArrival() + " " + ticket.getTripId().getTimeOfArrival());
        String htmlContent = templateEngine.process("ticket-template", context);


        // Generate PDF from HTML
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        renderer.createPDF(outputStream);


        // Send mail with attachment
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
        messageHelper.setFrom("transport.system2000@gmail.com");
        messageHelper.setTo(toEmail);
        messageHelper.setText("In the attachment you will find the ticket for your trip. Thank you for using our services.");
        messageHelper.setSubject("Trip ticket");
        InputStreamSource attachmentSource = new ByteArrayResource(outputStream.toByteArray());
        messageHelper.addAttachment("ticket.pdf", attachmentSource);
//        FileSystemResource fileSystemResource = new FileSystemResource("/Users/Kenan/IdeaProjects/transport-system/mail/ticket-template.html");
//        messageHelper.addAttachment(fileSystemResource.getFilename(),fileSystemResource);
        mailSender.send(message);
    }
}
