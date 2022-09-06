package br.com.ondetemvagas.webapp.service;

import br.com.ondetemvagas.webapp.config.EmailConfig;
import br.com.ondetemvagas.webapp.entity.PortalJob;
import br.com.ondetemvagas.webapp.exception.SendMailException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Setter
@NoArgsConstructor
@Service
public class MailService {

  private EmailConfig mailConfig;

  @Autowired
  public MailService(EmailConfig mailConfig) {
    this.mailConfig = mailConfig;
  }

  public void jobNotification(String name, String email, List<PortalJob> portalJobs) {
    if (!mailConfig.getEnabled()) {
      log.info("Mail system not enabled for job notifications!! Leaving!");
      return;
    }

    Properties props = System.getProperties();
    props.setProperty("mail.smtp.host", mailConfig.getSmtpHost());
    if (mailConfig.getDebug()) {
      props.put("mail.debug", "true");
    }
    props.put("mail.smtp.port", mailConfig.getSmtpPort());
    if (!Objects.isNull(mailConfig.getSmtpAuth())) {
      props.put("mail.smtp.auth", mailConfig.getSmtpAuth());
    }
    if (!Objects.isNull(mailConfig.getStartTlsEnable())) {
      props.put("mail.smtp.starttls.enable", mailConfig.getStartTlsEnable());
    }
    props.put("mail.smtp.socketFactory.class", mailConfig.getSocketFactoryClass());

    Session session =
        Session.getDefaultInstance(
            props,
            new Authenticator() {
              @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    mailConfig.getFromAddress(), mailConfig.getFromPassword());
              }
            });

    try {
      URL url = getClass().getClassLoader().getResource("email_template.html");
      if (url == null) {
        throw new SendMailException("Unable to retrieve email template!");
      }
      File file = new File(url.getFile());
      String mailTemplate = new String(Files.readAllBytes(file.toPath()));

      // nome da pessoa
      mailTemplate = mailTemplate.replace("__PRIMEIRO_NOME__", name);

      final String jobTemplate =
          """
              <div class="content">
                 <table>
                     <tr>
                         <td class="small" width="20%" style="vertical-align: top; padding-right:10px;">
                             <img width="32" src="https://i.imgur.com/0hmI6C1.png" alt="Nova vaga detectada"/>
                         </td>
                         <td>
                             <h4>__JOB_NAME__</h4>
                             <p>__JOB_DETAILS__</p>
                             <a href="__JOB_URL__" target="_blank" title="Abrir link no navegador" class="btn">Acessar o site da vaga</a>
                         </td>
                     </tr>
                 </table>
              </div>""";

      for (PortalJob portalJob : portalJobs) {
        String jobDetails = portalJob.getJobDescription();
        if (portalJob.getCompanyName() != null && !portalJob.getCompanyName().isEmpty()) {
          boolean addDot =
              !portalJob.getJobDescription().endsWith(".")
                  && !portalJob.getJobDescription().endsWith("!");

          if (addDot) {
            jobDetails += ".";
          }

          jobDetails += " Empresa: " + portalJob.getCompanyName();
        }

        if (portalJob.getPublishedAt() != null) {
          boolean addDot = !jobDetails.endsWith(".") && !jobDetails.endsWith("!");

          if (addDot) {
            jobDetails += ".";
          }

          if (!Objects.isNull(portalJob.getPublishedAt())) {
            jobDetails += " Publicado em: " + portalJob.getPublishedAt() + ".";
          }
        }

        String job = jobTemplate;
        job = job.replace("__JOB_NAME__", portalJob.getJobTitle());
        job = job.replace("__JOB_DETAILS__", jobDetails);
        job = job.replace("__JOB_URL__", portalJob.getJobUrl());

        mailTemplate = mailTemplate.replace("__CONTEUDO_VAGA__", job + "__CONTEUDO_VAGA__");
      }

      mailTemplate = mailTemplate.replace("__CONTEUDO_VAGA__", "");

      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(mailConfig.getFromAddress(), "Avisos de Vagas"));
      message.setReplyTo(
          new Address[] {
            new InternetAddress(mailConfig.getFromAddress(), mailConfig.getFromName())
          });
      message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
      message.setSubject(portalJobs.size() + " nova(s) vaga(s) encontrada(s)!");
      message.setContent(mailTemplate, "text/html; charset=UTF-8");
      message.setSentDate(new java.util.Date());

      Transport.send(message);
      log.info("E-mail para a pessoa enviado com sucesso!");
    } catch (MessagingException | IOException | NullPointerException me) {
      throw new SendMailException(
          "Erro ao enviar e-mail para o usuário que solicitou: " + me.getMessage());
    }
  }
}
