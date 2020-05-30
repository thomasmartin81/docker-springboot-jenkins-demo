package ch.duerri.dockerspringbootdemo.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

@RestController
@RequestMapping("/app")
public class ApplicationRestController {
    private static final String VERSION;

    static {
        String version = "Unknown";

        try {
            Properties gitProperties = new Properties();
            gitProperties.load(ApplicationRestController.class.getClassLoader().getResourceAsStream("git.properties"));
            version = gitProperties.getProperty("git.build.time") + "-" + gitProperties.getProperty("git.commit.id");
        } catch (Exception e) {
            e.printStackTrace();
            version += " - Error: " + e.getMessage();
        }
        VERSION = version;
    }

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationRestController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/version")
    public String getVersion() {
        return VERSION;
    }

    @GetMapping("/hostname")
    public String getHostname() {
        String hostname = "unknown";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return "Welcome to the docker test page. hostname=" + hostname;
    }

    @GetMapping("/unhealthy")
    public void setApplicationUnhealthy() {
        applicationService.setHealthy(false);
    }
}
