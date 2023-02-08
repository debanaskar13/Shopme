FROM maven:latest as builder

WORKDIR /usr/app/Shopme
COPY ./ ./

RUN mvn install
