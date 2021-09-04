# divolte-collector-with-apache-kafka

A proof of concept about collecting clickstream data using Javascript, Divolte Collector, Apache Kafka and Java consumer application.

# Youtube video

[Divolte Collector with Apache Kafka](#)

# Architecture Diagram

![alt text](images/architecture-diagram.png)

# Technologies Used

* [Javascript](https://developer.mozilla.org/en-US/docs/Web/JavaScript)
* [Divolte Collector](https://divolte.io/)
* [Apahce Avro](https://avro.apache.org/)
* [Apache Kafka](https://kafka.apache.org/)
* [Java 8](#)
* [Apache Maven](#)


## Requirements

* Divolte Collector and Apache Kafka: require java 8+

## 1- Install Divolte Collector

1. Download divolte collector // you may check the latest version available in the official website.

```
wget http://divolte-releases.s3-website-eu-west-1.amazonaws.com/divolte-collector/0.9.0/distributions/divolte-collector-0.9.0.tar.gz
```

2. Unzip the tar file, and go inside the folder.

```
tar -xzf divolte-collector-*.tar.gz

cd divolte-collector-*
```

3. Update config files inside conf/ with your custom information, you can check [here](https://github.com/soufianeodf/js-divolte-kafka-elk-superset/tree/main/divolte-collector-0.9.0/conf) my example.

4. Run Divolte collector server.

```
./bin/divolte-collector
```

## 2- Install Apache Kafka

1. Download Apache Kafka // you may check the latest version available in the official website.

```
wget https://downloads.apache.org/kafka/2.8.0/kafka_2.13-2.8.0.tgz
```

2. Unzip the tar file, and go inside the folder.

```
tar xzf kafka_*.tgz

cd kafka_*/
```

3. Update config files inside config/ with your custom information, you can check [here](https://github.com/soufianeodf/js-divolte-kafka-elk-superset/tree/main/kafka_2.13-2.8.0/config) my example.

4. Run Zookeeper.

```
./bin/zookeeper-server-start.sh ./config/zookeeper.properties
```

5. Run Kafka cluster.

```
./bin/kafka-server-start.sh ./config/server.properties
```

6. Create a Kafka topic.

```
./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic tracking
```


## 3- Run Java consumer


