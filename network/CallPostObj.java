package com.example.plivo.sample1.network;

public class CallPostObj {
    private String to;
    private String from;
    private String answer_url;
    private String answer_method;

    private CallPostObj(String to, String from, String answer_url, String answer_method) {
        this.to = to;
        this.from = from;
        this.answer_url = answer_url;
        this.answer_method = answer_method;
    }

    public static class Builder {
        private String to;
        private String from;
        private String answer_url;
        private String answer_method;

        public Builder setTo(String to) {
            this.to = to;
            return this;
        }

        public Builder setFrom(String from) {
            this.from = from;
            return this;
        }

        public Builder setAnswerUrl(String answer_url) {
            this.answer_url = answer_url;
            return this;
        }

        public Builder setAnswerMethod(String answer_method) {
            this.answer_method = answer_method;
            return this;
        }

        public CallPostObj build() {
            return new CallPostObj(this.to, this.from, this.answer_url, this.answer_method);
        }
    }
}
