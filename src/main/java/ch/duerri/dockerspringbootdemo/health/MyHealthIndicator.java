package ch.duerri.dockerspringbootdemo.health;

import ch.duerri.dockerspringbootdemo.application.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import org.springframework.stereotype.Component;

@Component
public class MyHealthIndicator implements HealthIndicator {

    private final ApplicationService applicationService;

    @Autowired
    public MyHealthIndicator(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public Health health() {
        if (!applicationService.isHealthy()) {
            return Health.down().withDetail("error_message", "application is not healthy").build();
        }
        return Health.up().build();
    }

}
