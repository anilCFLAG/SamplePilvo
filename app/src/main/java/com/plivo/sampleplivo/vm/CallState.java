package com.plivo.sampleplivo.vm;

public class CallState {

    public enum STATE {CALL_IDLE, ON_CALL}

    private STATE state;

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }
}
