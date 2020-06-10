package com.test.automation.academy.webservice.payload;

import com.google.gson.annotations.SerializedName;

public class Application {
    @SerializedName(value = "application_id", alternate = {"id"})
    private Integer application_id;
    private String description;
    private String name;
    private String owner;

    public Integer getApplication_id() {
        return application_id;
    }

    public void setApplication_id(Integer application_id) {
        this.application_id = application_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
