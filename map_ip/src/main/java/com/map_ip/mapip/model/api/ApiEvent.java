package com.map_ip.mapip.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiEvent {

    @JsonProperty
    @Length(max = 7)
    private String type;

    @JsonProperty
    @Length(max = 11)
    private String ip;

    @JsonProperty
    @Length(max = 26)
    private String datetime;

    public ApiEvent() {
        // Jackson deserialization
    }

    public ApiEvent(String type, String ip, String datetime) {
        this.type = type;
        this.ip = ip;
        this.datetime = datetime;
    }


    public String getType() {
        return type;
    }


    public String getIp() {
        return ip;
    }


    public String getDatetime() {
        return datetime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiEvent event = (ApiEvent) o;

        if (type != null ? !type.equals(event.type) : event.type != null) return false;
        if (ip != null ? !ip.equals(event.ip) : event.ip != null) return false;
        return datetime != null ? datetime.equals(event.datetime) : event.datetime == null;

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (datetime != null ? datetime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApiEvent{" +
                "type='" + type + '\'' +
                ", ip='" + ip + '\'' +
                ", datetime=" + datetime +
                '}';
    }
}
