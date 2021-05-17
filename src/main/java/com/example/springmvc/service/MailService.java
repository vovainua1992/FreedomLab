package com.example.springmvc.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.core.io.Resource;
@Service
public class MailService {
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String username;
    private ExecutorService executorService;
    @Autowired
    private FreeMarkerConfigurer freemarkerConfigurer;
    @Value("classpath:/static/images/logo.png")
    private Resource resourceFile;


    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    //Відправка простих текстових повідомленнь
    public void send(String emailTo,String subject,String message){
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom(username);
                mailMessage.setTo(emailTo);
                mailMessage.setSubject(subject);
                mailMessage.setText(message);

                mailSender.send(mailMessage);
                return 0;
            }
        };
        getExecutorService().submit(callable);
    }


    //Для невеликих html повідомлень
    public void sendMessageWithAttachment(String to,
                                          String subject,
                                          String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // pass 'true' to the constructor to create a multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");

            helper.setFrom(username);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text,true);


            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //Для повідомлень Freemaker
    public void sendMessageUsingFreemarkerTemplate(
            String to, String subject, Map<String, Object> templateModel)
            throws IOException, TemplateException, MessagingException {

        Template freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate("mail/mail.ftl");
        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);

        sendHtmlMessage(to, subject, htmlBody);
    }

    //Функція відправки html повідомлення
    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {

                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setFrom(username);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(htmlBody, true);
                helper.addInline("attachment.png", resourceFile);
                mailSender.send(message);
                return 0;
            }
        };
        getExecutorService().submit(callable);
    }

    //Отримання потоку відправника повідомлень
    private ExecutorService getExecutorService(){
        if (executorService==null)
            executorService = Executors.newSingleThreadExecutor();
        return executorService;
    }
}
