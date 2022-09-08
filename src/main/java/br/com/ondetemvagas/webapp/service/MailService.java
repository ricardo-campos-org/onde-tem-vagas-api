package br.com.ondetemvagas.webapp.service;

import br.com.ondetemvagas.webapp.config.EmailConfig;
import br.com.ondetemvagas.webapp.entity.PortalJob;
import br.com.ondetemvagas.webapp.exception.SendMailException;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
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

  public void jobNotification(String name, String email, Set<PortalJob> portalJobs) {
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
      String mailTemplate = getEmailTemplate();

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

  public String getEmailTemplate() {
    return """
        <!DOCTYPE html>
        <html><head>
          <meta charset="utf-8"/>
          <meta name="viewport" content="width=device-width"/>
          <meta http-equiv="Content-Type" content="text/html"/>
          <title>Onde Tem Vagas | Vagas por e-mail</title>
          <style>* {margin:0; padding:0;}
            * {font-family: "Helvetica Neue", "Helvetica", Helvetica, Arial, sans-serif;}
            img {max-width: 100%;} .collapse {margin:0;padding:0;}
            body {-webkit-font-smoothing:antialiased; -webkit-text-size-adjust:none; width: 100%!important; height: 100%; }
            a {color: #2BA6CB;} .btn {text-decoration:none;color: #FFF;background-color: #666;padding:10px 16px;font-weight:bold;margin-right:10px;text-align:center;cursor:pointer;display: inline-block;}
            p.callout {padding:15px;background-color:#ECF8FF;margin-bottom: 15px;}
            .callout a {font-weight:bold;color: #2BA6CB;} table.social {background-color: #ebebeb;}
            .social .soc-btn {padding: 3px 7px;font-size:12px;margin-bottom:10px;text-decoration:none;color: #FFF;font-weight:bold;display:block;text-align:center;}
            a.fb { background-color: #3B5998!important; } a.tw { background-color: #1daced!important; }
            a.gp { background-color: #DB4A39!important; } a.ms { background-color: #000!important; }
            .sidebar .soc-btn {display:block;width:100%;} table.head-wrap { width: 100%;}
            .header.container table td.logo { padding: 15px; }
            .header.container table td.label { padding: 15px; padding-left:0px;}
            table.body-wrap { width: 100%;} table.footer-wrap { width: 100%;clear:both!important;}
            .footer-wrap .container td.content  p { border-top: 1px solid rgb(215,215,215); padding-top:15px;}
            .footer-wrap .container td.content p {font-size:10px;font-weight: bold;}
            h1,h2,h3,h4,h5,h6 {font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif; line-height: 1.1; margin-bottom:15px; color:#000;}
            h1 small, h2 small, h3 small, h4 small, h5 small, h6 small { font-size: 60%; color: #6f6f6f; line-height: 0; text-transform: none; }
            h1 {font-weight:200; font-size: 44px;} h2 {font-weight:200; font-size: 37px;}
            h3 {font-weight:500; font-size: 27px;} h4 {font-weight:500; font-size: 23px;}
            h5 {font-weight:900; font-size: 17px;} h6 {font-weight:900; font-size: 14px; text-transform: uppercase; color:#444;}
            .collapse { margin:0!important;} p, ul { margin-bottom: 10px; font-weight: normal; font-size:14px; line-height:1.6;}
            p.lead { font-size:17px; } p.last { margin-bottom:0px;} ul li {margin-left:5px;list-style-position: inside;}
            ul.sidebar {background:#ebebeb; display:block;list-style-type: none;}
            ul.sidebar li { display: block; margin:0;} ul.sidebar li a {text-decoration:none;color: #666;padding:10px 16px;margin-right:10px;cursor:pointer;border-bottom: 1px solid #777777;border-top: 1px solid #FFFFFF;display:block;margin:0;}
            ul.sidebar li a.last { border-bottom-width:0px;}
            ul.sidebar li a h1,ul.sidebar li a h2,ul.sidebar li a h3,ul.sidebar li a h4,ul.sidebar li a h5,ul.sidebar li a h6,ul.sidebar li a p { margin-bottom:0!important;}
            .container {display:block!important;max-width:600px!important;margin:0 auto!important; clear:both!important;}
            .content {padding:15px;max-width:800px;margin:0 auto;display:block;}
            .content {padding:15px;max-width:800px;margin:0 auto;display:block;}
            .column {width: 400px;float:left;} .column tr td { padding: 15px; }
            .column-wrap { padding:0!important; margin:0 auto; max-width:600px!important;}
            .column table { width:100%;} .social .column {width: 280px;min-width: 279px;float:left;}
            .clear { display: block; clear: both; }
            @media only screen and (max-width: 600px) {
              a[class="btn"] { display:block!important; margin-bottom:10px!important; color:#FFF!important; background-image:none!important; margin-right:0!important;}
              div[class="column"] { width: auto!important; float:none!important;}
              table.social div[class="column"] {width:auto!important;}
            }</style>
        </head>
        <body bgcolor="#FFFFFF" topmargin="0" leftmargin="0" marginheight="0" marginwidth="0">
          <table class="head-wrap" bgcolor="#ebebeb"><tr><td></td><td class="header container">
            <div class="content"><table bgcolor="#ebebeb"><tr>
              <td><img src="https://i.imgur.com/0hmI6C1.png" width="48"/></td>
              <td align="right"><h6 class="collapse">Onde Tem Vagas! A sua ferramenta de busca de vagas.</h6></td>
          </tr></table></div></td><td></td></tr></table>
          <table class="body-wrap"><tr><td></td><td class="container" bgcolor="#FFFFFF">
            <div class="content"><table><tr><td>
              <h3>Olá __PRIMEIRO_NOME__</h3>
              <p>Confira as últimas vagas que encontramos para você! Se você tiver qualquer dúvida, basta responder este e-mail!</p>
              </td></tr></table>
            </div>
            __CONTEUDO_VAGA__
            <div class="content"><table><tr><td><table class="social" width="100%">
              <tr><td><div class="column"><table align="left"><tr>
                <td><h5>Curta nossa página</h5><p>
                <a href="https://www.facebook.com/Fast-Vagas-100768048014870" target="_blank" class="soc-btn fb">Facebook</a>
              </p></td></tr></table></div>
              <div class="column"><table align="left"><tr><td>
              <h5>Contato</h5><p><strong><a href="mailto:contato@ondetemvagas.com.br">contato@ondetemvagas.com.br</a></strong>
              </p></td></tr></table></div><div class="clear"></div></td></tr></table></td></tr>
            </table></div></td><td></td></tr></table>
        </body>
        </html>
        """;
  }
}
