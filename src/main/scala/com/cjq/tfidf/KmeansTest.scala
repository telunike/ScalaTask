package com.cjq.tfidf

import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object KmeansTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local[2]")
    conf.setAppName("kmeans")

    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val data = sc.textFile("").map{
      x =>
        val temp = x.split(",")
        Record(temp(0), temp(1), temp(2), temp(3))
    }.toDF().cache()

    //定义tokenizer,并转换数据
    val wordData = new Tokenizer()
      .setInputCol("productInfo")
      .setOutputCol("productToken")
      .transform(data)

    //定义hashTF，并转换数据
    val tfData = new HashingTF().setNumFeatures(20)
      .setInputCol("productToken")
      .setOutputCol("productTF")
      .transform(wordData)

    //定义idfmodel
    val idfModel = new IDF().setInputCol("productTF").setOutputCol("productIDF").fit(tfData)

    //存储idfmodel
    //idfModel.save("")

    //转换idf数据
    val idfData = idfModel.transform(tfData)

    //获取tfidf训练数据
    val trainData = idfData.select("id", "companyName", "productIDF")
    trainData.show()

    //kmeans聚类计算
    val kmeans = new KMeans()
      .setK(10)
      .setMaxIter(5)
      .setFeaturesCol("productIDF")
      .setPredictionCol("productKmeans")
    val kmeansModel = kmeans.fit(trainData)
    //kmeansModel.save("")
    val kmeanData = kmeansModel.transform(trainData).cache()
    kmeanData.show()
  }

  case class Record(
                     id: String,
                     companyName: String,
                     direction: String,
                     productInfo: String
                   )

}
