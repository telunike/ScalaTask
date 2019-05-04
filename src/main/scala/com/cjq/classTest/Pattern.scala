package com.cjq.classTest

import scala.util.matching.Regex

object Pattern {
  def main(args: Array[String]): Unit = {
    val pattern = new Regex("(s|S)cala")

    val str = "scala is cool, Scala is fantastic, scala"

    println(pattern findFirstIn str)

    var it = pattern.findAllIn(str)
    it.foreach(x => println(x))

    println(pattern replaceFirstIn(str, "c#"))
    println(pattern replaceAllIn(str, "c#"))

    var array = Array(3, 4, 5)
    try{
      println(array(4))
    }catch {
      case ex : IndexOutOfBoundsException => {
      println("index out of bound")
    }
    }finally {
      println("finally...")
    }
  }
}
