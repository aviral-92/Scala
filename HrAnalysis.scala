package com.scala.demo

import com.google.common.base.Splitter
import scala.collection.JavaConverters._

object HrAnalysis extends App {

  println(
    """************************************
          |     WELCOME TO HR ANALYSIS APP
          |************************************
        """.stripMargin)

  println

  listAllOptions

  lazy val hr = try {
    val bufferedSource = scala.io.Source.fromFile("csv/HR_Analysis_Dataset.csv")
    val arrayOfLines = try bufferedSource.getLines().toArray finally bufferedSource.close()
    val linesStream = arrayOfLines.drop(1).par.toStream
    val hrData = new java.util.ArrayList[Hr]()
    linesStream foreach (line => {
      val cols = Splitter.on(',').split(line).asScala.toArray
      hrData.add(Hr(cols(0), cols(1), cols(2), cols(3), cols(4), cols(5), cols(6), cols(7), cols(8), cols(9)))
    })
    hrData.asScala.toList
  } catch {
    case e: Exception =>
      e.printStackTrace()
      List[Hr]()
  }

  def listAlldepartments(departments: List[Hr]) = {
    departments foreach (hr => {
      println(hr)
    })
  }

  def departmentInformation(departments: List[Hr]) = {
    println("Please enter department name : ")
    val input = scala.io.StdIn.readLine()
    val departmentList = departments.filter(hr => { hr.dept == input })
    if (departmentList.headOption.isDefined && departmentList.head != null) show(departmentList) else println("No Department Found with given Input")
    def show(departmentsList: List[Hr]) = {
      departmentsList foreach { (hrInformation => println(hrInformation)) }
    }
  }

  def departmntInfoOnSalary(departments: List[Hr]) = {
    println("Please enter department name : ")
    val input1 = scala.io.StdIn.readLine()
    println("Please enter Salary(low,medium,high) : ")
    val input2 = scala.io.StdIn.readLine()
    val departmentList = departments.filter(hr => (hr.dept == input1 && hr.salary == input2))
    if (departmentList.headOption.isDefined && departmentList.head != null) show(departmentList) else println("No Department Found with given Input")
    def show(departmentsList: List[Hr]) = {
      departmentsList foreach { hrInfo => println(hrInfo) }
    }
  }

  def departmntInfoOnSalaryAndProject(departments: List[Hr]) = {
    println("Please enter department name : ")
    val input1 = scala.io.StdIn.readLine()
    println("Please enter Salary(low,medium,high) : ")
    val input2 = scala.io.StdIn.readLine()
    println("Please enter number of Projects : ")
    val input3 = scala.io.StdIn.readLine()
    val departmentList = departments.filter(hr => (hr.dept == input1 && hr.salary == input2 && hr.number_project == input3))
    if (departmentList.headOption.isDefined && departmentList.head != null) show(departmentList) else println("No Department Found with given Input")
    def show(departmentsList: List[Hr]) = {
      departmentsList foreach { hrInfo => println(hrInfo) }
    }
  }

  def listAllOptions: Unit = {
    println(
      """Please Choose any Option to Proceed:
        |1. List All Departments
        |2. Department Information By Department Code
        |3. HR Information By Department Code and Salary
        |4. HR Information By Department Code, Salary and Number of projects
        |5. Exit
      """.stripMargin)
    println("-------------------------------------")
    print("Option :: ")
    scala.io.StdIn.readInt() match {
      case 1 =>
        listAlldepartments(hr); println; listAllOptions
      case 2 =>
        departmentInformation(hr); println; listAllOptions
      case 3 =>
        departmntInfoOnSalary(hr); println; listAllOptions
      case 4 =>
        departmntInfoOnSalaryAndProject(hr); println; listAllOptions
      case 5 => println("Thank You.")
      case _ => println(s"Seems You've chosen the wrong Option."); println; listAllOptions
    }
  }

  case class Hr(satisfaction_level: String, last_evaluation: String, number_project: String,
    average_montly_hours: String, time_spend_company: String, Work_accident: String,
    left: String, promotion_last_5years: String, dept: String, salary: String)
}