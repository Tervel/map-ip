package com.map_ip.mapip;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;


/**
 * Author: Thuan Nguyen on 13/08/2016.
 */
public class MapIPConfiguration extends Configuration {
    private String requestType;
    private String requestIp;

    @JsonProperty
    public String getRequestType() {
        return requestType;
    }

    @JsonProperty
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    @JsonProperty
    public String getRequestIp() {
        return requestIp;
    }

    @JsonProperty
    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }
}
