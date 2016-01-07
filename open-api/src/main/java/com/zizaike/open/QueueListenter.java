package com.zizaike.open;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

public class QueueListenter implements MessageListener {

    @Override
    public void onMessage(Message msg) {
        try{
            System.err.print("message"+msg.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}