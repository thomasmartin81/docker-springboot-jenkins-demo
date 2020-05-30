package ch.duerri.dockerspringbootdemo.application;

import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    private boolean healthy = true;

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }
}
