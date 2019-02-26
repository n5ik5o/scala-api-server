FROM java:openjdk-8-jdk-alpine

LABEL maintainer "k.nzk <niizekikentaro@gmail.com>"

ENV SBT_VERSION 0.13.15
ENV SBT_HOME /usr/local/sbt
ENV PATH=${PATH}:${SBT_HOME}/bin
ENV SBT_JAR http://dl.bintray.com/sbt/native-packages/sbt/$SBT_VERSION/sbt-$SBT_VERSION.tgz

ENV HOME=/home/sbt
ENV SBT_OPTS="-Dsbt.ivy.home=$HOME/.ivy2 -Dsbt.boot.directory=$HOME/.sbt"

RUN apk --update add bash wget && \
  # install sbt && \
  cd /tmp && \
  wget --no-verbose ${SBT_JAR} && \
  tar -xf sbt-$SBT_VERSION.tgz && \
  mkdir /usr/local/sbt && \
  mv /tmp/sbt/* $SBT_HOME && \
  echo -ne "- with sbt sbt-$SBT_VERSION\n" >> /root/.built && \
  rm -rf /tmp/sbt* && \
  apk del wget

WORKDIR $HOME
EXPOSE 8080

CMD ["/bin/bash"]
