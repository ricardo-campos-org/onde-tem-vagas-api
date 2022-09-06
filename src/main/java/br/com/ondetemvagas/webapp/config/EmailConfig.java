package br.com.ondetemvagas.webapp.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class EmailConfig {

  @Value("${config.mail.enabled}")
  private Boolean enabled;

  @Value("${config.mail.from.address}")
  private String fromAddress;

  @Value("${config.mail.from.name}")
  private String fromName;

  @Value("${config.mail.from.password}")
  private String fromPassword;

  @Value("${config.mail.debug}")
  private Boolean debug;

  @Value("${config.mail.smtp.host}")
  private String smtpHost;

  @Value("${config.mail.smtp.port}")
  private String smtpPort;

  @Value("${config.mail.smtp.auth}")
  private String smtpAuth;

  @Value("${config.mail.smtp.starttls.enable}")
  private String startTlsEnable;

  @Value("${config.mail.smtp.socketFactory.class}")
  private String socketFactoryClass;

  @Value("${config.mail.admin.to.address}")
  private String adminToAddress;

  @Value("${config.mail.admin.to.name}")
  private String adminToName;

  @Value("${config.mail.admin.cc.address}")
  private String adminCcAddress;

  @Value("${config.mail.admin.cc.name}")
  private String adminCcName;
}
