FROM        ubuntu:22.04
RUN         apt update
RUN         apt install -y openjdk-17-jdk maven tree
COPY        pom.xml /java/
COPY        src/*     /java/src/
RUN         cd /java && mvn package
ENTRYPOINT  tree /java