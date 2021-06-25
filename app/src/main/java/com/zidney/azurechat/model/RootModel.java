package com.zidney.azurechat.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class RootModel {


    @SerializedName("to")
    private String token;
    @SerializedName("notification")
    private NotificationModel notificationModel;
    @SerializedName("data")
    private Map<String,String> dataModel;

    public Map<String, String> getDataModel() {
        return dataModel;
    }

    public RootModel(String token, NotificationModel notificationModel, Map<String, String> dataModel) {
        this.token = token;
        this.notificationModel = notificationModel;
        this.dataModel = dataModel;
    }

    public void setDataModel(Map<String, String> dataModel) {
        this.dataModel = dataModel;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public NotificationModel getNotificationModel() {
        return notificationModel;
    }

    public void setNotificationModel(NotificationModel notificationModel) {
        this.notificationModel = notificationModel;
    }



}
