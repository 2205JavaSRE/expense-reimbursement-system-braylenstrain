FROM maven:3.8.5-openjdk-8 AS MAVEN_BUILD_STAGE

COPY ./ ./

RUN mvn clean package

FROM openjdk:8-jre

COPY --from=MAVEN_BUILD_STAGE /target/ExpenseReimbursementSystem-0.0.1-SNAPSHOT-shaded.jar ERS.jar

CMD ["java","-jar","ERS.jar"]
