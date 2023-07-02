FROM openjdk:17-alpine
WORKDIR /app
COPY  Servidor.jar /app/Servidor.jar
ENV API_PORT=8080 SOCKET_PORT=3030
EXPOSE ${API_PORT} ${SOCKET_PORT}
ENTRYPOINT [ "java","-jar","Servidor.jar" ]