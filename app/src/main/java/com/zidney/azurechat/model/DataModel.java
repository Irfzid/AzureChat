package com.zidney.azurechat.model;

import java.util.Map;

public class DataModel {


    private Map<String,String> map;

    public DataModel(Map<String,String> map) {
        this.map = map;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
