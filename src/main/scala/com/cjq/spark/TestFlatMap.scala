package com.cjq.spark

import org.apache.spark.{SparkConf, SparkContext}

object TestFlatMap {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
    conf.setAppName("test")
    conf.setMaster("local[1]")

    val sc = new SparkContext(conf)
    val map1 = sc.parallelize(1 to 3, 1)
    /*val map2 = Map((1, 3), (2, 6), (5, 23)).toIndexedSeq
    sc.parallelize(map2, 2)*/
    map1.foreach(println)
    val mapvalue =  map1.map(p => (p, p *2)).flatMapValues(x => (x to 7))
    mapvalue.foreach(println)

    sc.stop()

  }
}
