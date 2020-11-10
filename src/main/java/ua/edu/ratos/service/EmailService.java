package ua.edu.ratos.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendgridApiKey;

    @Value("${sendgrid.mail.sender}")
    private String senderEmail;

    @Value("${sendgrid.mail.senderName}")
    private String senderName;

    private SendGrid sendgrid = null;

    @PostConstruct
    public SendGrid createSendGrid() {
        this.sendgrid = new SendGrid(sendgridApiKey);
        return this.sendgrid;
    }

    public void sendEmail(@NonNull String templateId, @NonNull String toEmail,
                          @NonNull String subject, @NonNull Map<String, Object> params) {

        String paramsString = params.entrySet()
                .stream().map(entry->entry.getKey()+":"+entry.getValue()).collect(Collectors.joining(","));

        log.debug("Email is to be sent with " +
                "templateId= {}, to = {}, subject = {}, params = {}", templateId, toEmail, subject, paramsString);

        Mail mail = new Mail();
        mail.setTemplateId(templateId);

        Email from = new Email(senderEmail, senderName);
        mail.setFrom(from);
        mail.setReplyTo(from);

        mail.setSubject(subject);

        String[] toEmails = toEmail.split(",\\s*");

        Personalization personalization = new Personalization();
        for (int i = 0; i < toEmails.length; i++) {
            personalization.addTo(new Email(toEmails[i]));
        }

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            personalization.addDynamicTemplateData(entry.getKey(), entry.getValue());
        }

        mail.addPersonalization(personalization);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = this.sendgrid.api(request);
            int statusCode = response.getStatusCode();
            log.debug("Response code = {}, Response body = {}", statusCode, response.getBody());
            if (statusCode < 200 || statusCode > 203) {
                throw new RuntimeException("Wrong status code: "+statusCode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error sending email through SendGrid. Message:" + e.getMessage());
        }
    }

}
