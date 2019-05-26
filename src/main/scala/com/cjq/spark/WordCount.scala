package com.cjq.spark

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {

  def main(args: Array[String]): Unit = {
    /**
      * 第一步，创建spark的配置对象sparkconf，设置spark程序运行的配置信息
      * setAppName用来设置应用程序的名称，在程序运行的监控界面上可以看到该名称
      * setMaster:  设置在本地运行还是运行在集群中，运行在本地可以用local参数或者local[k]
      * 如果运行在集群中，以standalone模式运行，需要使用spark://Host:PORT
      */

    val conf = new SparkConf()
    conf.setMaster("local[2]")
    conf.setAppName("count")

    /**
      * 第二步：创建sc对象，sc是spark程序所有功能的唯一入口
      * sc的核心作用， 初始化spark应用程序所需要的核心组件，包括 DAGScheduler TaskScheduler SchedulerBackend
      * 还会负责spark程序向master注册程序
      */
    val sc = new SparkContext(conf)

    /**
      * 第三步，根据具体的数据源等通过sparkcontext来创建rdd
      * rdd的创建方式，外部来源，通过scala集合使用然后产生rdd，通过rdd产生rdd
      */
    val rdd = sc.textFile("file path")

    /**
      * 函数计算，基于具体的业务逻辑
      */
    val words = rdd.flatMap(_.split(",")).flatMap(_.split(" ")).filter(word => word != " ")
    val pairs = words.map(p => (p, 1))
    val wordCount = pairs.reduceByKey(_ + _)
    val result = wordCount.collect()
    result.foreach(println)

    sc.stop()
  }
}
