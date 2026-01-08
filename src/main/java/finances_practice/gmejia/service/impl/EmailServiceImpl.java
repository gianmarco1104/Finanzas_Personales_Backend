package finances_practice.gmejia.service.impl;

import finances_practice.gmejia.exception.BusinessException;
import finances_practice.gmejia.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    @Async
    public void sendVerificationEmail(String toEmailSend, String code){
        try{
            // Objeto MimeMessage (HTML)
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            //Contexto de Thymeleaf (Las variables)
            Context context = new Context();
            context.setVariable("code", code);

            //Procesar HTML
            String htmlContent = templateEngine.process("email-template",context);

            helper.setTo(toEmailSend);
            helper.setSubject("Verifica tu cuenta - Finanzas App");
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);

            log.info("Correo enviado exitosamente a {}", toEmailSend);

        }catch (MessagingException e){
            log.error("ERROR CRÍTICO: No se pudo enviar correo a {}: {}", toEmailSend, e.getMessage());
        }
    }
}
