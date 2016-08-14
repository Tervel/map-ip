package com.map_ip.mapip.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DynamoEvent {
    @Length(max = 10)
    private String type;
    @Length(max = 16)
    private String ip;
    @Length(max = 26)
    private String datetime;


    public DynamoEvent() {
        // Jackson deserialization
    }

    public DynamoEvent(String ip, String type, String datetime) {
        this.ip = ip;
        this.type = type;
        this.datetime = datetime;
    }


    @JsonProperty
    public String getIp() {
        return ip;
    }

    @JsonProperty
    public String getType() {
        return type;
    }

    @JsonProperty
    public String getDatetime() {
        return datetime;
    }
}
