package com.cjq.tfidf

import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object KmeansTest1 {

  case class Record(
                     id: String,
                     companyName: String,
                     direction: String,
                     productInfo: String
                   )

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("master")
    conf.setMaster("local")

    val sc = new SparkContext(conf)

    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._

    val record = sc.textFile("")
      .map(x => {
        val data = x.split(",")
        Record(data(0), data(1), data(2), data(3))
      }).toDF().cache()

    //将produceInfo去掉空格，分开存储
    val tokenizer = new Tokenizer()
      .setInputCol("produceInfo")
      .setOutputCol("produceFeature")
      .transform(record)

    //计算record的TF值
    val tfData = new HashingTF()
      .setNumFeatures(20)
      .setInputCol("produceFeature")
      .setOutputCol("wordsData")
      .transform(tokenizer)

    //计算idf值
    val idfModel = new IDF()
      .setInputCol("wordsData")
      .setOutputCol("wordsFeature")
      .fit(tfData)

    val tfidfData = idfModel.transform(tfData)

    val trainingData = tfidfData.select("id", "companyName", "wordsFeature")

    val kmeans = new KMeans()
      .setK(10)
      .setMaxIter(5)
      .setFeaturesCol("wordsFeature")
      .setPredictionCol("prediction")
    val kmeansModel = kmeans.fit(trainingData)
    val kmeansData = kmeansModel.transform(trainingData).cache()
    kmeansData.show()
  }

}
