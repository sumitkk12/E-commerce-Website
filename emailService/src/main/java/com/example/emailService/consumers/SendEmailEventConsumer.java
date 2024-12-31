package com.example.emailService.consumers;
import java.util.Properties;

import com.example.emailService.dtos.SendEmailDto;
import com.example.emailService.utils.EmailUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;



@Service
public class SendEmailEventConsumer {

    private ObjectMapper objectMapper;

    public SendEmailEventConsumer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }


    @KafkaListener(topics = "sendEmail", groupId = "emailServiceGroup")
    public void handleSendEmailEvent(String message) throws JsonProcessingException {
        SendEmailDto event = objectMapper.readValue(message, SendEmailDto.class);


        String to = event.getTo();
        String from = event.getFrom();
        String subject = event.getSubject();
        String body = event.getBody();


        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "vwuyogeibnspyzle");
            }
        };
        Session session = Session.getInstance(props, auth);
        System.out.println(to);
        System.out.println(body);
        EmailUtil.sendEmail(session, to, subject, body);

    }
}
