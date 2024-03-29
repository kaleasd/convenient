package com.gyh.cloudeureka.listen;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;

@Service
public class HealthStatusService implements HealthIndicator {

    private Boolean status = true;

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status.toString();
    }

    @Override
    public Health health() {
        if (status){
            return new Health.Builder().up().build();
        }
        return new Health.Builder().down().build();
    }
}
