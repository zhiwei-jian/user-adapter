package com.hide.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hide.entity.User;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @Author Zhiwei Jian
 * @create 1/13/20 2:33 AM
 */

@Component
public class RabbitReceiver {

//    @RabbitListener(
//            bindings = @QueueBinding(
//                    value = @Queue(value = "test.topic.queue",
//                            durable="false"),
//                    exchange = @Exchange(value = "test.topic",
//                            durable="false",
//                            type= "topic",
//                            ignoreDeclarationExceptions = "true"),
//                    key = "user.*"
//            )
//    )
//    @RabbitListener(queues = {"test-q"})
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws Exception {
        System.err.println("--------------------------------routingKey------");
        System.err.println("Payload: " + message.getPayload());
        Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        //
        channel.basicAck(deliveryTag, false);
    }

//    @RabbitListener(
//        bindings = @QueueBinding(
//                value = @Queue(value = "test.topic.queue",
//                        durable="false"),
//                exchange = @Exchange(value = "test.topic",
//                        durable="false",
//                        type= "topic",
//                        ignoreDeclarationExceptions = "true"),
//                key = "user.*"
//        )
//    )
    @RabbitHandler
    public void onMessage1(Message message, Channel channel) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue((byte[]) message.getPayload(), User.class);
        System.err.println("--------------------------------routingKey------");
        System.err.println("Payload: " + user);
        Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag, false);
    }
}
