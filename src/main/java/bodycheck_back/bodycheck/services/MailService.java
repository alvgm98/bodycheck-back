package bodycheck_back.bodycheck.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

   @Autowired
   private JavaMailSender mailSender;

   public void sendWelcomeEmail(String to, String name) throws MessagingException, IOException {
      // Leer la plantilla del archivo
      String htmlContent = new String(
            Files.readAllBytes(new ClassPathResource("templates/welcome_mail.html").getFile().toPath()),
            StandardCharsets.UTF_8);

      // Reemplazar variables dinámicas
      htmlContent = htmlContent.replace("{{nombre}}", name)
            .replace("{{enlace}}", "https://bodycheck.es/");

      // Crear el correo
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

      helper.setTo(to);
      helper.setSubject("Bienvenido a Bodycheck");
      helper.setText(htmlContent, true);
      helper.setFrom("bodycheck.noreply@gmail.com");

      // Añado el logo como adjunto
      ClassPathResource logo = new ClassPathResource("static/logo.png");
      helper.addInline("imagen_cid", logo, "image/png");

      // Enviar el correo
      mailSender.send(message);
   }
}
