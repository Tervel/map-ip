package com.map_ip.mapip.health;

import com.codahale.metrics.health.HealthCheck;

public class TypeHealthCheck extends HealthCheck {
    private final String type;

    public TypeHealthCheck(String type) {
        this.type = type;
    }

    @Override
    protected Result check() throws Exception {
        final String saying = String.format(type, "TEST");
        if (!saying.contains("TEST")) {
            return Result.unhealthy("type doesn't include a name");
        }
        return Result.healthy();
    }
}
