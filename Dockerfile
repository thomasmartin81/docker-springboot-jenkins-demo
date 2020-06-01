FROM 192.168.20.14:8083/openjdk:11.0.7-slim

# healthcheck (exit 0 = success, 1 = unhealthy)
HEALTHCHECK --start-period=10s --interval=5s --timeout=2s --retries=3 \
            CMD curl --silent --fail --request GET http://localhost:8080/actuator/health || exit 1

RUN apt-get update && apt-get -y install curl
RUN unlink /etc/localtime && ln -s /usr/share/zoneinfo/Europe/Zurich /etc/localtime

# command to execute when image is running
CMD ["java", "-cp", "/app/libs/*:/app/classes/", "ch.duerri.dockerspringbootdemo.DockerSpringbootDemoApplication"]

# copy dependencies
COPY target/dependency/ /app/libs/

# copy classes and resources
COPY target/classes/ /app/classes/


