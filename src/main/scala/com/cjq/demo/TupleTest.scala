package com.cjq.demo

import Array._

object TupleTest {
  def main(args: Array[String]): Unit = {

    val ti = new Tuple2("hah", 8)
    var tl = new Tuple3("ha", 2, "c")

    tl.productIterator.foreach(x => println(x))

    val it = Iterator("aaa", "bbb", "ccc")
    while (it.hasNext) {
      println(it.next())

      val it1 = Iterator(1, 3, 5)
      val it2 = Iterator(2, 6, 9)

      println(it1.size + "---" + it2.max)
    }

  }
}
