package com.appsplanet.helpingkart.Class;


public class NotificationItem {
    private String str_message_notification;
    private String str_time_notification;
    private String str_title_notification;


    public NotificationItem() {
    }

    public NotificationItem(String str_title_notification,String str_message_notification, String str_time_notification) {
        this.str_message_notification = str_message_notification;
        this.str_time_notification = str_time_notification;
        this.str_title_notification = str_title_notification;
    }

    public String getStr_message_notification() {
        return str_message_notification;
    }

    public void setStr_message_notification(String str_message_notification) {
        this.str_message_notification = str_message_notification;
    }

    public String getStr_time_notification() {
        return str_time_notification;
    }

    public void setStr_time_notification(String str_time_notification) {
        this.str_time_notification = str_time_notification;
    }

    public String getStr_title_notification() {
        return str_title_notification;
    }

    public void setStr_title_notification(String str_title_notification) {
        this.str_title_notification = str_title_notification;
    }
}
