package com.cjq.demo

object HelloDemo {

  def main(args: Array[String]): Unit = {
    println("hello world")
    delayed(time())

    check(b = 20, a = 10)

    check(40, 60)
    checkStr(10, "hah", "hash", "shjsh", "ahsha")

    var logWith = log("my is : " , _:String)

    logWith("m1")
    logWith("m2")
    logWith("m3")

    println(test(7))

  }

  def time() = {
    println("enter time method")
    System.nanoTime()
  }

  def delayed(t : => Long): Unit = {
    println("in delayed method")
    println(t)
  }

  def check(a : Int, b : Int): Unit = {
    println("a : " + a + ", b : " + b)
  }

  def checkStr(firstData : Int, args : String*): Unit = {
    println("firstData : " + firstData)

    var i:Int = 0;
    for (arg <- args) {
      println("i : " + i + " haha " + arg)
      i = i + 1
    }
  }

  def log(string: String, str: String)  {
      println(string + "---" + str)
  }

  def test(i : Int) : Int = {
    def test2(k : Int, p : Int): Int = {
      return k + p;
    }
    var sum : Int = 10
    sum = test2(i, 7)
    return sum;
  }
}
