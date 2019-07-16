package com.cjq.tfidf

import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object TFIDFTest2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("TFIDFTest2")
    conf.setMaster("local[2]")

    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val records = sc.textFile("").map{
      x =>
        val data = x.split(",")
        Record(data(0), data(1), data(2), data(3))
    }.toDF().cache()
    val data = new Tokenizer().setInputCol("productInfo").setOutputCol("products").transform(records)
    val tfData = new HashingTF().setInputCol("products").setOutputCol("productFeature").setNumFeatures(20).transform(data)
    val idfModel = new IDF().setInputCol("produceFeature").setOutputCol("feature").fit(tfData)
    idfModel.save("")
    val idfData = idfModel.transform(tfData)
    idfData.select("id", "companyName", "feature").show()

  }
}

case class Record (
                  id : String,
                  companyName : String,
                  direction : String,
                  productInfo : String
                  )