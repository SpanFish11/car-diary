package com.godeltech.mastery.backend.domain.event;

import com.godeltech.mastery.backend.domain.entity.Client;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;

@Setter
@Getter
public class ClientCreateEvent extends ApplicationEvent {

  @Serial private static final long serialVersionUID = 88708990940061447L;

  private Client client;
  private String password;

  public ClientCreateEvent(final Object source) {
    super(source);
  }

  public ClientCreateEvent(final Object source, final Client client, final String password) {
    super(source);
    this.client = client;
    this.password = password;
  }
}
