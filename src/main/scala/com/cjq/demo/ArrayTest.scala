package com.cjq.demo
import Array._

object ArrayTest {

  def main(args: Array[String]): Unit = {
    var a1:Array[String] = new Array[String](3);

    a1(1) = "haha";
    a1(2) = "check";
    a1(0) = "test";

    println(a1(0))

    var array1 = Array(1, 2, 4, 8)
    var array2 = Array(3, 4, 8, 9)

    var concatArray = concat(array1, array2)
    for (x <- concatArray) {
      println(x)
    }

    var list:List[String] = List("aa", "bb", "cc")
    println(list.head)
    println(list.tail)
    println(list(1))

    var list1 = List("c", "j", "q")
    var res = list:::list1
    var res1 = List.concat(list, list1)
    println(res.length + "---" + res1)
    println(list.reverse)

    var set = Set("j","k", "l")
    var set1 = Set("k", "th")
    println(set++set1)
    println(set.&(set1))

    var map:Map[String, Int] = Map()
    map += ("map1" -> 1)
    map += ("map2" -> 2)
    println(map.keys + "->" + map.values)

    var map1:Map[String, Int] = Map()
    map1 += ("mapt" -> 3)

    var mapCombine = map++map1
    mapCombine.keys.foreach(x => println(x + "--->" + mapCombine(x)))

    println(map.++:(map1))
  }
}
