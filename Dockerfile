FROM openjdk:15

MAINTAINER Dennis Jauernig <dennis@jauernig.email>

ADD backend/target/schulatlas.jar app.jar

CMD ["sh", "-c", "java -Dserver.port=$PORT -Dspring.data.mongodb.uri=$MONGODB_URI -Dsecurity.jwt.secret=$JWT_SECRET -jar /app.jar"]
