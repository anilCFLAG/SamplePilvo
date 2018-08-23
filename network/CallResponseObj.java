package com.example.plivo.sample1.network;

public class CallResponseObj extends BaseResponseObj {

    private String request_uuid;

    public CallResponseObj(String message, String request_uuid, String api_id) {
        super(message, api_id);
        this.request_uuid = request_uuid;
    }

    public String getRequestUuid() {
        return request_uuid;
    }
}
