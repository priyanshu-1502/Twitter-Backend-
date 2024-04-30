package com.oop.major_assignment_twitter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorObject{
    @JsonProperty("Error")
    private String error;

    @JsonIgnore
    public ErrorObject(String error){
        this.error = error;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
