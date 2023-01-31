FROM        ubuntu:22.04
RUN         apt update
RUN         apt install -y openjdk-17-jdk maven
COPY        pom.xml /java/
COPY        src/*     /java/src/
RUN         cd /java && mvn package && cd /
#ENTRYPOINT  java -jar /java/target/chat-server-1-jar-with-dependencies.jar
