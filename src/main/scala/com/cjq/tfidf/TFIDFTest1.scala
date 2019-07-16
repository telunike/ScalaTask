package com.cjq.tfidf

import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

object TFIDFTest1 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local[2]")
    conf.setAppName("TFIDFTest1")
    val sparkContext = new SparkContext(conf)

    val sqlContext = new SQLContext(sparkContext)
    //文档组成语料库
    val data = sqlContext.createDataFrame(Seq(
      (0, "Hi i am tom"),
      (1, "i wish java could use case classes"),
      (2, "today is a good day")
    )).toDF("label", "sentence")
    val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
    val wordData = tokenizer.transform(data)
    wordData.show()

    val hashingTF = new HashingTF().setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(20)
    val featureizedData = hashingTF.transform(wordData)
    featureizedData.show()
    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    //模型计算出来后会保存在hdfs里，后续可以直接加载使用
    val idfModel = idf.fit(featureizedData)
    val rescaledData = idfModel.transform(featureizedData)
    rescaledData.select("features", "label").take(3).foreach(println)

  }

}
