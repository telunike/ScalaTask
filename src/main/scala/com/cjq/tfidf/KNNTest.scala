package com.cjq.tfidf

import java.util

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object KNNTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("KNN Test")
    conf.setMaster("local[*]")

    val sc = new SparkContext(conf)
    val random = new java.util.Random()
    val map100 = new mutable.HashMap[Int, Int]()
    while (map100.size < 10) {
      val index = random.nextInt(100)
      if (!map100.contains(index)) {
        map100 += (index -> 100)
      }
    }
    map100.foreach(p => println("map100 => " + p))

    val map200 = new mutable.HashMap[Int, Int]()
    while (map200.size < 10) {
      val index = random.nextInt(100) + 100
      if (!map200.contains(index)) {
        map200 += (index -> 200)
      }
    }
    map200.foreach(p => println("map200 => " + p))

    val num = random.nextInt(200)
    val topK = 5
    var index100 = 0
    var index200 = 0
    val mapSum = sc.parallelize((map100 ++ map200).toSeq)
    mapSum.map(p => {
      (Math.abs(p._1 - num), p._2)
    })
      .sortByKey(false)
      .takeOrdered(topK)
      .foreach(p => {
        println("topK : " + p)
        if (p._2 == 100) index100 = index100 + 1
        else index200 = index200 + 1
      })

    println(num + "index100 : " + index100 + ", index200 : " + index200)

    if (index100 > index200) {
      map100 += (num -> 100)
    } else {
      map200 += (num -> 200)
    }

  }

}
