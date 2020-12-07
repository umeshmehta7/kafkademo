Prerequisite
- java 1.8 or above

- kafka 2.0 or above (https://kafka.apache.org/downloads) follow Kafka installation

 
Ensure that zookeeper and kafka server are up and running.

Start Zookeeper

command to start zookeeper

umeshm$ zookeeper-server-start.sh <InstalledLocation>/kafka_2.13-2.5.0/config/zookeeper.properties 

Start Kafka server

command: umeshm$ kafka-server-start.sh ~<InstalledLocation>/kafka_2.13-2.5.0/config/server.properties


Once application is running.

Producer API
http://localhost:8080/api/v1/write?topic=topic5&value=hello%20again1wendty

argument desc
topic: Required, name of the topic (String)
key: optional, message key (String)
value: Required, message (String)


Consumer API
http://localhost:8080/api/v1/read?topic=topic5&offset=earliest

argument desc
topic: Required, topic to read from
offset: optional, values are 1) "earliest" (read all the messages), 2) "latest" (only read the message which is new and not fetched)
