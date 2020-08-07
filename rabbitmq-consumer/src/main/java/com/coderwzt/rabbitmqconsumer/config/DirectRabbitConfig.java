package com.coderwzt.rabbitmqconsumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author CoderWZT
 * @Create on 2020/8/7.
 */
@Configuration
public class DirectRabbitConfig {

  @Bean
  public Queue TestDirectQueue(){
    return new Queue("TestDirectQueue",true);
  }

  @Bean
  public DirectExchange TestDirectExchange(){
    return new DirectExchange("TestDirectExchange");
  }

  @Bean
  public Binding bindingDirect(){
    return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("TestDirectRouting");
  }
}
