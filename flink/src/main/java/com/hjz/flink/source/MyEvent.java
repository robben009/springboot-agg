package com.hjz.flink.source;

import java.sql.Timestamp;


public class MyEvent {
    public String user;
    public String url;
    public Long timestamp;

    public MyEvent() {
    }
    public MyEvent(String user, String url, Long timestamp) {
        this.user = user;
        this.url = url;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Event{" +
                "user='" + user + '\'' +
                ", url='" + url + '\'' +
                ", timestamp=" + new Timestamp(timestamp) +
                '}';
    }

}