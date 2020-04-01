package com.techprimers.security.securitydbexample.service;

import com.techprimers.security.securitydbexample.model.MailRequest;
import com.techprimers.security.securitydbexample.model.MailResponse;
import com.techprimers.security.securitydbexample.model.Usuario;
import com.techprimers.security.securitydbexample.repository.ConfigRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration config;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ConfigRepository configRepository;

    public boolean sendMail(Usuario usuario) throws Exception {
        try {
            String frontProtocol = this.configRepository.findByCodConfig(this.configRepository.FRONT_PROTOCOL).getStrValor();
            String frontAddress = this.configRepository.findByCodConfig(this.configRepository.FRONT_ADDRESS).getStrValor();
            String frontPort = this.configRepository.findByCodConfig(this.configRepository.FRONT_PORT).getStrValor();
            String urlReset = frontProtocol + "://" + frontAddress + ":" + frontPort + "/email?token=" + usuario.getStrTokenReset();

            Map<String, Object> model = new HashMap<>();
            model.put("name", "buscar do usuario");
            model.put("location", "Paraná, Brazil");
            model.put("urlreset", urlReset);

            MailResponse response = new MailResponse();
            MimeMessage message = sender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            //helper.addAttachment("logo.png", new ClassPathResource("logo.png"));

            Template t = config.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(usuario.getStrLogin());
            helper.setText(html, true);
            helper.setSubject("Alteração de senha");
            helper.setFrom("reset@sistema.com");
            sender.send(message);
            return true;
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } // catch MessagingException
    } // sendMail
} // EmailService
