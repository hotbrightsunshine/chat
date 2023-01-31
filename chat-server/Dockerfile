FROM        ubuntu:22.04    
RUN         apt update
RUN         apt install -y openjdk-11-jdk maven git
RUN         git clone https://github.com/hotbrightsunshine/chat.git
RUN         rm -rf chat/terminal-chat
RUN         cd /chat/chat-server && mvn clean package && ls -la ./target
ENTRYPOINT  java -jar chat/chat-server/target/chat-server.jar 7777
