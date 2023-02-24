FROM mysql

WORKDIR /tmp

COPY world-db/world.sql /tmp/
#COPY world-db/*.dump /tmp/

COPY world-db/world.sql /docker-entrypoint-initdb.d

ENV MYSQL_ROOT_PASSWORD example