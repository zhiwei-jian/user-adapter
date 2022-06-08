package com.hide.amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hide.entity.User;
import com.rabbitmq.tools.json.JSONWriter;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @Author Zhiwei Jian
 * @create 1/11/20 10:57 AM
 */

@Component
public class RabbitSender {

    // RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;
    final ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {

        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("correlationData: " + correlationData);
            System.err.println("ack: " + ack);
            if(!ack){
                System.err.println("Error on ack....");
            }
        }
    };

    final ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText,
                String exchange, String routingKey) {
            System.err.println("return exchange: " + exchange + ", routingKey: "
                    + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
        }
    };

    public void send(Object message, Map<String, Object> properties, String routingKey) throws Exception {
        MessageHeaders messageHeaders = new MessageHeaders(properties);

        Message msg = MessageBuilder.createMessage(message, messageHeaders);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);

//        rabbitTemplate.convertAndSend(msg);

        String id = "1234567890";
        CorrelationData correlationData = new CorrelationData(id);
        //exchange, routingKey, object, correlationData
        if(StringUtils.isBlank(routingKey)){
            routingKey = "user.abc";
        }
        JSONWriter rabbitmqJson = new JSONWriter();
        String jsonmessage = rabbitmqJson.write(msg);

        ObjectMapper objectMapper = new ObjectMapper();

        rabbitTemplate.convertAndSend("test.topic", routingKey, objectMapper.writeValueAsBytes(message));
    }

    public void directSend(Object message, Map<String, Object> properties, String routingKey, String exchangeName) throws Exception {
        MessageHeaders messageHeaders = new MessageHeaders(properties);

        Message msg = MessageBuilder.createMessage(message, messageHeaders);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);

//        rabbitTemplate.convertAndSend(msg);
        JSONWriter rabbitmqJson = new JSONWriter();
        String jsonmessage = rabbitmqJson.write(msg);

        ObjectMapper objectMapper = new ObjectMapper();

        rabbitTemplate.convertAndSend(exchangeName, routingKey, objectMapper.writeValueAsBytes(message));
    }
}
