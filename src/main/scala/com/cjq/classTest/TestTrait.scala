package com.cjq.classTest

trait TestTrait {

  var check : Int

  def teacher

  def printInfo(str:String) :Int = {
    if(str != null) {
      return str.length
    } else {
      return 0
    }
  }

}
