package com.cjq.spark

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SSKafkaTest {

  def main(args: Array[String]): Unit = {
    //定义zookeeper的地址
    val zookeeperPath = "192.168.56.72:2181,192.168.56.73,192.168.56.74:2181"

    //定义groupid
    val groupId = "testStreaming"

    //定义topic
    val kafkaTopic:Map[String, Int] = Map[String, Int]("test" -> 1)

    val conf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("streamingTest")

    val ssc:StreamingContext = new StreamingContext(conf, Seconds.apply(2))

    val stream = KafkaUtils.createStream(ssc, zookeeperPath, groupId, kafkaTopic, StorageLevel.MEMORY_ONLY)
      .map(record => record._2)

    stream.foreachRDD(
      x =>
        x.collect().foreach(println)
    )

    //开始运行
    ssc.start()
    //计算完毕退出
    ssc.awaitTermination()
  }
}
