FROM ubuntu:18.04

RUN apt-get update && \
    apt-get install -y git curl ca-certificates-java openjdk-17-jdk && \
    curl -SL https://go.dev/dl/go1.19.2.linux-amd64.tar.gz | tar -xzC /usr/local && \
    export PATH=$PATH:/usr/local/go/bin && \
    go install github.com/sorenisanerd/gotty@latest

RUN apt-get install -y locales && \
    locale-gen ko_KR.UTF-8
ENV LC_ALL=ko_KR.UTF-8

ENV GOBIN=/root/go/bin
ENV PATH=$PATH:$GOBIN
ENV GOTTY_CREDENTIAL="admin:bookstore1234"
EXPOSE 10000/tcp

ARG STEP_DUMMY=unkown

RUN git clone https://github.com/nohkwangsun/bookstore.git
WORKDIR bookstore
RUN ./gradlew clean build

CMD ["gotty", "-w", "-p", "10000", "--title-format", "Damazon", "./gradlew", "--console", "plain", "-q","run"]


