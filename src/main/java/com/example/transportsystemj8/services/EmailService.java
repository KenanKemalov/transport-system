package com.example.transportsystemj8.services;

import com.example.transportsystemj8.data.entity.Ticket;
import com.example.transportsystemj8.data.entity.Trip;
import jdk.internal.org.xml.sax.InputSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.context.StyleReference;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextUserAgent;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendEmail(String toEmail, Ticket ticket) throws MessagingException, IOException {
        Context context = new Context();
        context.setVariable("depstation", ticket.getTripId().getLocationFrom().getLocationName());
        context.setVariable("arrstation", ticket.getTripId().getLocationTo().getLocationName());
        context.setVariable("passenger", ticket.getCustomerName());
        context.setVariable("transport", ticket.getTripId().getTransportTypeId().getTransportTypeName());
        context.setVariable("departure", ticket.getTripId().getDeparture() + " " + ticket.getTripId().getTimeOfDeparture());
        context.setVariable("seat", ticket.getSeatNumber());
        context.setVariable("arrival", ticket.getTripId().getArrival() + " " + ticket.getTripId().getTimeOfArrival());
        String htmlContent = templateEngine.process("ticket/ticket-template", context);

        Document doc = Jsoup.parse(htmlContent, "UTF-8");
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        SharedContext sharedContext = renderer.getSharedContext();
        sharedContext.setPrint(true);
        sharedContext.setInteractive(false);
        String baseurl = FileSystems.getDefault().getPath("/ticket").toUri().toURL().toString();
        renderer.setDocumentFromString(doc.html(), baseurl);
        renderer.layout();
        renderer.createPDF(outputStream);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
        messageHelper.setFrom("transport.system2000@gmail.com");
        messageHelper.setTo(toEmail);
        messageHelper.setText("In the attachment you will find the ticket for your trip. Thank you for using our services.");
        messageHelper.setSubject("Trip ticket");
        InputStreamSource attachmentSource = new ByteArrayResource(outputStream.toByteArray());
        messageHelper.addAttachment("ticket.pdf", attachmentSource);
        mailSender.send(message);
    }
}
