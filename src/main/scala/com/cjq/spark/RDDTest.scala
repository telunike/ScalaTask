package com.cjq.spark

import org.apache.spark.{SparkConf, SparkContext}

object RDDTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("rddTest")
    conf.setMaster("local")

    val sc = new SparkContext(conf)

    val rddInt = sc.makeRDD(Array(1, 2, 3, 4, 5, 6, 7, 3, 6))
    val rddInt1 = sc.makeRDD(Array(23, 445, 4, 12, 89))
    val rddStr = sc.makeRDD(List("kkadhs", "adjklj", "osdk"))

    //map
    val resutl = rddInt.map(x => x + 1)
    println(resutl.collect().mkString(","))

    //filter
    println(rddInt.filter(x => x > 5).collect().mkString("-"))

    //flatmap
    println(rddStr.flatMap(x => x.split(",")).collect().mkString("---"))

    //distinct
    println(rddInt.distinct().collect().mkString("=="))

    //union
    println(rddInt.union(rddInt1).collect().mkString("==="))

    //intersection(交集)
    println(rddInt.intersection(rddInt1).collect().mkString("_"))

    //subtract(rddInt有但是rddInt1没有的)
    println(rddInt.subtract(rddInt1).collect().mkString("--"))


    /**
      * action
      */

    //count
    println(rddInt.count())

    //countByValue
    println(rddInt.countByValue())

    //reduce:是将rdd前两个元素传给输入函数，产生一个新的值，然后将这个新值与后面一个组成两个元素，再传给输入函数
    println(rddInt.reduce((x, y) => x + y))

    //reduceByKey : 就是先通过key进行分组，然后对key相同的元素进行reduce操作
    val testr = sc.makeRDD(List((1, 3), (1, 5), (2, 7), (3, 6), (3, 5)))
    val check = testr.reduceByKey((x, y) => x + y).collect().mkString("===")
    println(check)

    //fold aggregate

    rddStr.foreach(x => println(x))
  }

}
