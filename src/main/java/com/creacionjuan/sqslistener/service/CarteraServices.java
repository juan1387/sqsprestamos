package com.creacionjuan.sqslistener.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.creacionjuan.sqslistener.models.Cartera;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CarteraServices {
    private final AmazonSQS sqsClient;

    private String getQueueUrl() {
        return sqsClient.getQueueUrl("cartera-clientes").getQueueUrl();
    }

    public String publishStandardQueueMessage(Integer delaySeconds, Cartera cartera) {
        Map<String, MessageAttributeValue> atributosMensaje = new HashMap<>();
        atributosMensaje.put("cliente",
                new MessageAttributeValue()
                        .withStringValue(String.valueOf(cartera.getClienteid()))
                        .withDataType("Number")
        );
        atributosMensaje.put("dineroprestado",
                new MessageAttributeValue()
                        .withStringValue(String.valueOf(cartera.getDineroPrestado()))
                        .withDataType("Number")
        );
        atributosMensaje.put("fechaEntrega",
                new MessageAttributeValue()
                        .withStringValue(cartera.getFechaEntrega().toString())
                        .withDataType("String")
        );
        atributosMensaje.put("fechadevolucion",
                new MessageAttributeValue()
                        .withStringValue("SinData")
                        .withDataType("String")
        );
        atributosMensaje.put("tasaPrestamo",
                new MessageAttributeValue()
                        .withStringValue(Double.toString(cartera.getTasaPrestamo()))
                        .withDataType("Number")
        );

        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(this.getQueueUrl())
                .withMessageBody(cartera.getClienteid()+"cliente")
                .withDelaySeconds(delaySeconds)
                .withMessageAttributes(atributosMensaje);

        return sqsClient.sendMessage(sendMessageRequest).getMessageId();
    }
}
