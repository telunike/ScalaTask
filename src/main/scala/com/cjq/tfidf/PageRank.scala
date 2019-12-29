package com.cjq.tfidf

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

class PageRank {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("pageRank")
    conf.setMaster("local[0]")

    val sc = new SparkContext(conf)
    val links = sc.objectFile[(String, Seq[String])]("links")
        .partitionBy(new HashPartitioner(30))
        .persist()

    var ranks = links.mapValues(v => 1.0)

    /*for(i < o until 10) {
      val contributions = links.join(ranks).flatMap{
        case (pageId, (links, rank)) =>
          links.map(dest => (dest, rank / links.size))
      }
      ranks = contributions.reduceByKey((x, y) => x + y).mapValues(v => 0.15 + 0.85 * v)
    }*/

    ranks.saveAsTextFile("ranks")

    sc.stop()
  }

}
