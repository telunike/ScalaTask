package com.cjq.spark

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object DataFrameTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("DataFrameTest")
    conf.setMaster("local")

    val sc = new SparkContext(conf)

    val rdd = sc.textFile("")
    val sqlContext = new SQLContext(sc)

    import sqlContext.implicits._

    //rdd转化为dataFrame
    val df = rdd.map { x =>
      val data = x.split(",")
      (data(0), data(1), data(2), data(3))
    }.toDF("id", "name", "type", "intro")
    df.show(2)

    /**
      * 以树的形式打印出DataFrame的schema
      */
    df.printSchema()

    /**
      * 打印name列的数据
      */
    df.select("name").show()

    /**
      * 打印出id和name
      */
    df.select(df("name"), df("id")).show()

    /**
      * 添加过滤条件
      */
    df.filter(df("id") > 2).show()

    /**
      * 分类
      */
    df.groupBy(df("type")).count().show()

    //dataFrame => rdd
    val dRdd = df.rdd
    val ds = dRdd.map{y => {
      (y.get(0).toString, y.get(1).toString, y.get(2).toString, y.get(3).toString)
    }}.toDS()
    ds.show()

    //ds => df
    val df1 = ds.toDF()
    df1.show()

    //df => ds
    val dfToDs = df1.as("test")
    dfToDs.show()


  }

}
