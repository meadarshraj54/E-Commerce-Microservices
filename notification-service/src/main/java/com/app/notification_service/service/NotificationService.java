package com.app.notification_service.service;

import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class NotificationService {
    private final JavaMailSender javaMailSender;
    @KafkaListener(topics = "order-placed")
    public void listen(com.app.order.event.OrderPlaced orderPlaced) {

        try {
            log.info("Received order placed event: {}", orderPlaced);

            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

                helper.setFrom("adarsh69.temp@gmail.com");

                // IMPORTANT
                helper.setTo(orderPlaced.getEmail().toString());

                helper.setSubject(String.format("Your order with Order number %s is placed successfully." ,orderPlaced.getOrderNumber()));

                helper.setText(String.format("""
                        Hii %s %s,
                        
                        Your order with Order number %s is placed successfully.
                        
                        Thanks for shopping with us.
                        """, orderPlaced.getFirstName(), orderPlaced.getLastName(), orderPlaced.getOrderNumber()));
            };

            javaMailSender.send(messagePreparator);

            log.info("Email sent successfully");

        } catch (Exception e) {
            log.error("REAL ERROR:", e);
        }
    }
}
