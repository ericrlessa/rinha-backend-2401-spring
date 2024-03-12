# The deployment Image
FROM alpine:latest

RUN apk add --no-cache gcompat

EXPOSE 8080

# Copy the native executable into the containers
COPY ./target/rinhabackend app
ENTRYPOINT ["/app"]