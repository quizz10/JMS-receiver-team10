package se.iths.jmsdemo220425.receiver;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import se.iths.jmsdemo220425.config.JmsConfig;
import se.iths.jmsdemo220425.model.MessageObject;

@Component
public class Receiver {

    @JmsListener(destination = JmsConfig.JU20DEC_QUEUE)
    public void listen(@Payload MessageObject messageObject) {
        System.out.println("I got a message");
        System.out.println(messageObject);
    }

}
