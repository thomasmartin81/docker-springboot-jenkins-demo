package ch.duerri.dockerspringbootdemo;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Tag("local")
@SpringBootTest
class DockerSpringbootDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void validateSpringBootApplicationClassInDockerfile() {
        assertTrue(DockerSpringbootDemoApplication.class.isAnnotationPresent(SpringBootApplication.class));
        File dockerfile = new File("Dockerfile");
        assertTrue(dockerfile.exists());

        AtomicBoolean found = new AtomicBoolean(false);
        try (Stream<String> stream = Files.lines(dockerfile.toPath())) {
            stream.forEach(line -> {
                if (line.startsWith("CMD") && line.contains(DockerSpringbootDemoApplication.class.getName())) {
                    assertFalse(found.getAndSet(true));
                }
            });
        } catch (IOException e) {
            fail(e);
        }
        assertTrue(found.get());
    }
}
