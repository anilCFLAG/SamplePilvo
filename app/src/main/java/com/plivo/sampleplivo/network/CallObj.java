package com.plivo.sampleplivo.network;

/**
 * {
 "answer_time": "2017-06-28 19:39:05+05:30",
 "bill_duration": 11,
 "billed_duration": 60,
 "call_direction": "inbound",
 "call_duration": 11,
 "call_uuid": "57a1b60a-5c0b-11e7-9082-ef888d0400e9",
 "end_time": "2017-06-28 19:39:15+05:30",
 "from_number": "13475848223",
 "initiation_time": "2017-06-28 19:39:04+05:30",
 "parent_call_uuid": null,
 "resource_uri": "/v1/Account/MAXXXXXXXXXXXXXXXXXX/Call/57a1b60a-5c0b-11e7-9082-ef888d0400e9/",
 "to_number": "19172592277",
 "total_amount": "0.00850",
 "total_rate": "0.00850"
 }
 */
public class CallObj {
    private String answer_time;
    private String initiation_time;
    private String end_time;
    private int bill_duration;
    private int billed_duration;
    private int call_duration;
    private String call_direction;
    private String call_uuid;
    private String parent_call_uuid;
    private String from_number;
    private String to_number;
    private String resource_uri;
    private float total_amount;
    private float total_rate;

    public CallObj(String answer_time, String initiation_time, String end_time, int bill_duration, int billed_duration, int call_duration, String call_direction, String call_uuid, String parent_call_uuid, String from_number, String to_number, String resource_uri, float total_amount, float total_rate) {
        this.answer_time = answer_time;
        this.initiation_time = initiation_time;
        this.end_time = end_time;
        this.bill_duration = bill_duration;
        this.billed_duration = billed_duration;
        this.call_duration = call_duration;
        this.call_direction = call_direction;
        this.call_uuid = call_uuid;
        this.parent_call_uuid = parent_call_uuid;
        this.from_number = from_number;
        this.to_number = to_number;
        this.resource_uri = resource_uri;
        this.total_amount = total_amount;
        this.total_rate = total_rate;
    }
}

