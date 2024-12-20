package com.example.demo.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

  public void configureMessageBroker (MessageBrokerRegistry registry){
    registry.setApplicationDestinationPrefixes("/apiComment");
    registry.enableSimpleBroker("/apiComment");
  }

  public void registerStompEndpoints(StompEndpointRegistry registry){
    registry.addEndpoint("/ws")
        .setAllowedOriginPatterns("*")
        .withSockJS().setHeartbeatTime(1);
  }

}
