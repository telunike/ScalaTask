package com.cjq.classTest

class TestTrait1 extends TestTrait {

  override var check: Int = 90

  override def teacher: Unit = {
    println(check + 5)
  }

  def testMatch(str: Any): Unit = {
    if (str != null) {
      str match {
        case 1 => println("one")
        case 2 => println("two")
        case "zhangsan" => println(100)
        case "lisi" => println(1000)
        case _ => println("all")
      }
    }
  }
}

object TestTrait2 {
  def main(args: Array[String]): Unit = {
    var testTrait = new TestTrait1()
    testTrait.teacher

    testTrait.testMatch(1)
    testTrait.testMatch("zhangsan")
    testTrait.testMatch("check")
  }
}
