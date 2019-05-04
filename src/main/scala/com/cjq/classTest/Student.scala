package com.cjq.classTest

class Student(t1 : Int, t2 : Int) {
  def change(x : Int, y : Int): Unit = {
    println((x + t1) + "---" + (y + t2))

    println(Student.te)
  }
}

object Student {
  private var te:Int = 5

  def apply(t1: Int, t2: Int): Student = new Student(t1, t2)

  def check(): Int = {
    te += 1
    te
  }

  def main(args: Array[String]): Unit = {
    var student = new Student(23, 56)
    check()
    student.change(1, 1)
    println(Student.te)

    var student1 = Student.apply(12, 28)
    student1.change(2, 4)
  }

}
