package ch.duerri.dockerspringbootdemo.health;

import ch.duerri.dockerspringbootdemo.application.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@Tag("unit")
class MyHealthIndicatorTest {

    @Mock
    private ApplicationService applicationServiceMock;

    private MyHealthIndicator testingClass;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        testingClass = new MyHealthIndicator(applicationServiceMock);
    }

    @Test
    void healthUp() {
        given(applicationServiceMock.isHealthy()).willReturn(true);

        Health health = testingClass.health();
        assertNotNull(health);
        assertEquals(Status.UP, health.getStatus());

        verify(applicationServiceMock).isHealthy();
    }

    @Test
    void healthDown() {
        given(applicationServiceMock.isHealthy()).willReturn(false);

        Health health = testingClass.health();
        assertNotNull(health);
        assertEquals(Status.DOWN, health.getStatus());

        verify(applicationServiceMock).isHealthy();
    }
}
