FROM openjdk:8
ADD target/backend-mysql-adv.jar backend-mysql-adv.jar
EXPOSE 3001
ENTRYPOINT ["java", "-jar", "backend-mysql-adv.jar"]
