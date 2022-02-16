package com.example.demo.payload.response;

import lombok.Getter;
import lombok.Setter;

public class ResponseMessage {
    @Getter @Setter private String message;
    public ResponseMessage(){}
    public ResponseMessage(String message){
        this.message = message;
    }
}
