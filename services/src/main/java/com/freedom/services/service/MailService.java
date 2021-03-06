package com.freedom.services.service;

import com.freedom.services.dommain.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service for send Email
 */
@Service
public class MailService {
    private JavaMailSender mailSender;
    private static ExecutorService executorService;
    @Value("${spring.mail.username}")
    private String username;
    @Autowired
    private FreeMarkerConfigurer freemarkerConfigurer;
    @Value("classpath:/static/images/logo.png")
    private Resource resourceFile;
    @Value("${my.server.url}")
    private String url;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Send the user an email with an activation code
     * @param user
     */
    public void sendActivateCode(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getUsername());
        map.put("link", String.format("%s/activate/%s", url, user.getActivationCode()));
        if (!StringUtils.isEmpty(user.getEmail())) {
            try {
                sendMessageUsingFreemarkerTemplate(
                        "mail/activation.ftl",
                        user.getEmail(),
                        "?????????????????? ?????????????? FreedomLab"
                        , map);
            } catch (MessagingException | TemplateException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Send a text message via e-mail
     * @param to the recipient of the e-mail
     * @param subject the subject of the e-mail,
     * @param message text of the e-mail
     * @throws MessagingException
     */
    public void send(String to,
                     String subject,
                     String message) {
        Callable<Integer> callable = () -> {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(username);
            mailMessage.setTo(to);
            mailMessage.setSubject(subject);
            mailMessage.setText(message);

            mailSender.send(mailMessage);
            return 0;
        };
        getExecutorService().submit(callable);
    }

    /**
     * Send html e-mail
     * @param to the recipient of the e-mail
     * @param subject the subject of the e-mail,
     * @param html the html text of the e-mail
     * @throws MessagingException
     */
    public void sendMessageWithHTML(String to,
                                    String subject,
                                    String html) throws MessagingException {
        sendHtmlMessage(to, subject, html);
    }

    private void sendMessageUsingFreemarkerTemplate(String nameTemplate,
                                                    String to,
                                                    String subject,
                                                    Map<String, Object> templateModel)
            throws IOException, TemplateException, MessagingException {
        Template freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate(nameTemplate);
        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
        sendHtmlMessage(to, subject, htmlBody);
    }

    private void sendHtmlMessage(String to,
                                 String subject,
                                 String htmlBody) throws MessagingException {
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

    // Executor Service for sender email
    private ExecutorService getExecutorService() {
        if (executorService == null)
            executorService = Executors.newSingleThreadExecutor();
        return executorService;
    }
}
