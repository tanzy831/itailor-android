package com.thea.itailor.personal.bean;

/**
 * Created by Thea on 2015/9/11 0011.
 */
public class Message {
    private int messageID = 0;
    private int senderAccountID = 0;
    private String context = "";

    public int getMessageID() {
        return messageID;
    }

    public int getSenderAccountID() {
        return senderAccountID;
    }

    public String getContext() {
        return context;
    }
}
