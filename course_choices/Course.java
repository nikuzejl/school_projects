/**
* A program which represents a course and its different details
* @ author Jean Lewis Nikuze
* @ version 1.0 5/1/2019
*/

import java.util.*;
import java.io.*;

public class Course{
    private String name;          //Course ID
    private String title;         //Course title
    private int students;         //the number of students who took the course
    private int votes;            //the number of students who recommended it

   //default constructor
    public Course(){}

   /**Paramterized constructor
   * @ param name the name of the course
   * @ param title the course title
   * @ param students the number of students who took it
   * @ param votes the number of students who recommended it
   */
   public Course(String name, String title, int students, int votes){
      this.name = name;
      this.title = title;
      this.students = students;
      this.votes = votes;
   }

  /**getting the name of the course
  * @return the course name
  * Time Complexity: O(1)
  */
  public String getName(){return name;}

  /**getting the course title
  * @return the course title
  * Time Complexity: O(1)
  */
  public String getTitle(){return title;}

  /**getting the the number of studnents who took the course
  * @return the number of students who took it
  * Time Complexity: O(1)
  */
  public int getStud(){return students;}

  /**getting the number of votes
  * @return the number of students who recommended it
  * Time Complexity: O(1)
  */
  public int getVotes(){return votes;}

  /**getting the name course score
  * @return the course score
  * @throws IllegalArgumentException if no student took the course
  * Time Complexity: O(1)
  */
  public double getScore() throws IllegalArgumentException {
    if(students == 0)
        throw new IllegalArgumentException("We can't divide by 0");

    return ((votes*100)/students);
  }

  /** setting the course name
  * @param s the course name
  * Time Complexity: O(1)
  */
  public void setName(String s){name = s;}

  /** setting the course title
  * @param s the course title
  * Time Complexity: O(1)
  */
  public void setTitle(String s){title = s;}

  /** setting the number of students
  * @param n the number of students who took the course
  * Time Complexity: O(1)
  */
  public void setStud(int n){students = n;}

  /** setting the number of votes
  * @param n the number of students who recommended the course
  * Time Complexity: O(1)
  */
  public void setVotes(int n){votes = n;}

  /** getting the course description
  * @return the course description
  * Time Complexity: O(1)
  */
  public String description(){
    return name + ": " + title + " has been taken by " + students + " students and has a " + (int) getScore() + "% recommendation score.";
  }

  /** printing course details
  * @return the course's string representation
  * Time Complexity: O(1)
  */
  public String toString(){
    return (name + ": " + (int) getScore() + "%");
  }
}
