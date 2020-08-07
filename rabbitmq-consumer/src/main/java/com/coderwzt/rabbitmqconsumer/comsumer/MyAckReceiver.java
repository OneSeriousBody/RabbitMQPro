package com.coderwzt.rabbitmqconsumer.comsumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author CoderWZT
 * @Create on 2020/8/7.
 */
@Component
public class MyAckReceiver implements ChannelAwareMessageListener {

  @Override
  public void onMessage(Message message, Channel channel) throws Exception {
    long deliveryTag = message.getMessageProperties().getDeliveryTag();
    try {
      //因为传递消息的时候用的map传递,所以将Map从Message内取出需要做些处理
      String msg = message.toString();
      String[] msgArray = msg.split("'");//可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
      Map<String, String> msgMap = mapStringToMap(msgArray[1].trim(),3);
      String messageId=msgMap.get("messageId");
      String messageData=msgMap.get("messageData");
      String createTime=msgMap.get("createTime");
      System.out.println("  MyAckReceiver  messageId:"+messageId+"  messageData:"+messageData+"  createTime:"+createTime);
      System.out.println("消费的主题消息来自："+message.getMessageProperties().getConsumerQueue());
      //true:将一次性ack所有小于deliveryTag的消息
      channel.basicAck(deliveryTag, true);
//			channel.basicReject(deliveryTag, true);//为true会重新放回队列,，那么下次还会消费这消息
    } catch (Exception e) {
      channel.basicReject(deliveryTag, false);
      e.printStackTrace();
    }
  }

  //{key=value,key=value,key=value} 格式转换成map
  private Map<String, String> mapStringToMap(String str,int entryNum ) {
    str = str.substring(1, str.length() - 1);
    String[] strs = str.split(",",entryNum);
    Map<String, String> map = new HashMap<String, String>();
    for (String string : strs) {
      String key = string.split("=")[0].trim();
      String value = string.split("=")[1];
      map.put(key, value);
    }
    return map;
  }

}