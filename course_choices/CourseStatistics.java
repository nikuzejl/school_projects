/**
* Doing different operations on courses' data
* @ author Jean Lewis Nikuze
* @ version 1.0 5/1/2019
*/

import java.io.*;
import java.util.*;

public class CourseStatistics{
  public static HashMap<String,Course> map;      //hash map for storing courses
  public static PriorityQueue<Course> courseComp; //priority queue for comparing courses
  public static PriorityQueue<Course> courseQueue;//priority queue for storing courses

  //default constructor
  public CourseStatistics(){
     map = new HashMap<String,Course>();
     courseComp = new PriorityQueue<Course>(new CourseComparator<Course>());
     courseQueue = new PriorityQueue<Course>(new CourseComparator<Course>());
  }

  /**
  * adding a new course
  * @ param c the course to add
  * Time Complexity: O(n)
  */
  public void addCourse(Course c){
    if(map.get(c.getName()) == null)       //check if course name is already in the map
         map.put(c.getName(), c);          //add the course to the map

    if(courseQueue.contains(c))            //check if the queue contains a course
         courseQueue.remove(c);            //remove the current course

    courseQueue.add(c);			               //add a course to the queue
  }

  /**
  * getting a course from the map
  * @ param name the name of the course
  * Time Complexity: O(n)
  */
  public Course getCourse(String name){
    return map.get(name);
  }

  /**
  * displaying all courses
  * Time Complexity: O(n)
  */
  public void displayAll(){
    for(Course c: map.values())
       System.out.println(c.description());
  }

  /**
  * getting the total number of courses
  * Time Complexity: O(1)
  */
  public int getTotal(){return map.size();}

  /**
  * finding the best course
  * @ param courses the list of courses
  * Time Complexity: O(n)
  */
  public void findBest(List<Course> courses){
    if(courses.isEmpty())                           //if the are no courses to compare
      System.out.println("Nothing to compare");

    else{
       while(!courses.isEmpty())
          courseComp.add(courses.remove(0));       //move courses from list to priority queue

       System.out.println("Best course to take: " + courseComp.peek().description());
   }
  }

  /**
  * displaying all courses ranked by scores
  * Time Complexity: O(n)
  */
  public void displayStats(){
    while(!courseQueue.isEmpty())
       System.out.println(courseQueue.poll());
  }
}
