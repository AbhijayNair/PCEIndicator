package org.pceindicator.com.model;


import java.util.Date;

public class NotificationModel {
    public NotificationModel(String messageText, String messageUser, String messageYear, String messageBranch, String messageDivision) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageYear = messageYear;
        this.messageBranch = messageBranch;
        this.messageDivision = messageDivision;
        messageTime = new Date().getTime();
    }

    public NotificationModel() {
    }

    private String messageText;
    private String messageUser;
    private String messageYear;
    private String messageBranch;
    private String messageDivision;
    private long messageTime;

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    public String getMessageBranch() {
        return messageBranch;
    }

    public void setMessageBranch(String messageBranch) {
        this.messageBranch = messageBranch;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageYear() {
        return messageYear;
    }

    public void setMessageYear(String messageYear) {
        this.messageYear = messageYear;
    }

    public String getMessageDivision() {
        return messageDivision;
    }

    public void setMessageDivision(String messageDivision) {
        this.messageDivision = messageDivision;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
