package com.godeltech.mastery.backend.listener;

import static java.lang.String.format;

import com.godeltech.mastery.backend.domain.entity.Client;
import com.godeltech.mastery.backend.domain.event.ClientCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientCreateListener implements ApplicationListener<ClientCreateEvent> {

  private static final String SUBJECT = "Account created successfully";
  private static final String TEXT = """
          Dear %s %s!
          
          Your account has been created.
          
          The following contains temporary passwords for newly created user accounts.
          
          Login: %s
          Temporary Password: %s
          
          Temporary passwords are valid for 90 days
          
          Sincerely,
          Car Diary Team
          """;

  @Value("${spring.mail.host_address}")
  private static String hostAddress;

  private final JavaMailSender javaMailSender;

  @Override
  public void onApplicationEvent(final ClientCreateEvent event) {
    javaMailSender.send(makeMailMessage(event));
  }

  private SimpleMailMessage makeMailMessage(final ClientCreateEvent event) {
    final var message = new SimpleMailMessage();
    message.setSubject(SUBJECT);
    message.setTo(event.getClient().getEmail());
    message.setText(makeMessageText(event.getClient(), event.getPassword()));
    return message;
  }

  private String makeMessageText(final Client client, final String password) {
    return format(TEXT, client.getFirstName(), client.getLastName(), client.getEmail(), password);
  }
}
