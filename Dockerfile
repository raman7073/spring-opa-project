FROM eclipse-temurin:17
LABEL mantainer="raman.mehta@fiftyfivetech.io"
WORKDIR /app
COPY target/com.fiftyfive.springboot-0.0.1-SNAPSHOT.jar /app/springopa.jar
ENTRYPOINT ["java","-jar","springopa.jar"]