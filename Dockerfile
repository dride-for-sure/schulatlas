FROM openjdk:15

MAINTAINER Dennis Jauernig <dennis@jauernig.email>

ADD backend/target/schulatlas.jar app.jar

CMD ["sh", "-c", "java -Dserver.port=$PORT -Dspring.data.mongodb.uri=$MONGODB_URI -Dsecurity.jwt.secret=$JWT_SECRET -Dgoogle.api.geo=$GOOGLE_API_GEO -Daws.access.key.id=$AWS_ACCESS_KEY_ID -Daws.access.key.secret=$AWS_ACCESS_KEY_SECRET -Daws.region=$AWS_REGION -Daws.s3.audio.bucket=$AWS_S3_AUDIO_BUCKET -jar /app.jar"]
