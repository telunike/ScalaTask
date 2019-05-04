package com.cjq.classTest

class ScalaClass1(a: Int, b: Int) {

  var a1 = a
  var b1: Int = b

  def change(x : Int, y : Int): Unit = {
    println(x + a1)
    println(y + b1)
  }
}

object showClass {
  def main(args: Array[String]): Unit = {
    var testClass = new ScalaClass1(2, 8)
    testClass.change(20, 80)
  }
}
