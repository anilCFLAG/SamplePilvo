package com.plivo.sampleplivo.network;

/**
 * {
 "message": "call fired",
 "request_uuid": "9834029e-58b6-11e1-b8b7-a5bd0e4e126f",
 "api_id": "97ceeb52-58b6-11e1-86da-77300b68f8bb"
 }
 */
public class BaseResponseObj {
    private String message;
    private String api_id;

    public BaseResponseObj(String message, String api_id) {
        this.message = message;
        this.api_id = api_id;
    }

    public String getMessage() {
        return message;
    }

    public String getApiId() {
        return api_id;
    }
}
