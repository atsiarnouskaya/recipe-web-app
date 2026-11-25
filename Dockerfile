FROM openjdk:26-ea-oracle

ADD target/recipe-web-app.jar recipe-web-app.jar

ENTRYPOINT ["java", "-jar", "/recipe-web-app.jar"]
