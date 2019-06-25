/**
* Testing functionalities of the courses inventory
* @ author Jean Lewis Nikuze
* @ version 1.0 5/1/2019
*/

import java.util.*;
import java.io.*;

public class CourseStatisticsTester{
  /**
  * displaying details about different courses
  * @ param args file to read from provided in command line
  * Time Complexity: O(1)
  */
  public static void main(String[] args){
     List<Course> compList = new ArrayList<Course> ();   //list for storing courses to compare
     CourseStatistics allCours = new CourseStatistics(); //object for computing course statistics
     int stud;         					 //number of students
     int votes;         				 //number of votes
     Course course; 				         //variable for a new course to add

     FileReader inFile;
     BufferedReader in;
     String line;

    //reading the file
     if(args.length != 1) {
       System.out.println("Error: You need to provide a file name.");
       System.exit(1);
     }

     try{
     inFile = new FileReader(args[0]);
     in = new BufferedReader(inFile);
     line = in.readLine();

        while(line!= null){
          Course c = allCours.getCourse(line);
          if(c == null){		             //if the course is new
            course = new Course();       //new course to add

	         //setting instance variables
            course.setName(line);
            course.setTitle(in.readLine());
            stud = Integer.parseInt(in.readLine());
            votes = Integer.parseInt(in.readLine());
            course.setStud(stud);
            course.setVotes(votes);

            allCours.addCourse(course);              //add the new course
          }

          else{ 				     //if the course has been saved before
            String title = in.readLine();
            stud = Integer.parseInt(in.readLine());
            votes = Integer.parseInt(in.readLine());
            c.setStud(c.getStud() + stud);           //adjust the number of students
            c.setVotes(c.getVotes() + votes);        //adjust the votes

            allCours.addCourse(c);                   //add the course
          }

          line = in.readLine();
       }

   //handling errors related to reading the file
    }catch(FileNotFoundException e) {
         System.out.println("Error: File " + args[0] + " not found.");
         System.exit(1);
    } catch(IOException e) {
       System.out.println(e);
       System.exit(1);
    }

    Scanner input = new Scanner(System.in);

    String stop = "";                      //string from the keyboard

    System.out.println("\n***Displaying courses and their full descriptions...");
    System.out.println("Displaying all " + allCours.getTotal() + " courses");
    allCours.displayAll();

    System.out.println("\n***Displaying courses sorted by their recommendation rates...");
    allCours.displayStats();

    System.out.println("\n***Looking up courses...");
    System.out.println("Enter a course ID to look up statistics. Enter 's' to stop.");

    while(!stop.equals("s")){
      System.out.print("Course ID: ");
      stop = input.nextLine();
      Course c = allCours.getCourse(stop);        //find the course whose ID was typed

      if(c == null && !stop.equals("s"))
        System.out.println("No such course! Try again.");

      else if(c != null && !stop.equals("s"))
         System.out.println(c.description());
    }

    System.out.println("\n***Comparing courses...");
    System.out.println("Enter a course IDs to compare. Enter 's' to stop");
    String sComp = "";                       //string from the keyboard

    while(!sComp.equals("s")){
      System.out.print("Course ID: ");
      sComp = input.nextLine();
      Course c = allCours.getCourse(sComp);  //find the course whose ID was typed

      if(c == null && !sComp.equals("s"))
        System.out.println("No such course! Try again.");

      else if (c != null && !sComp.equals("s"))
         compList.add(c); 	             //add course to the array of courses to compare
    }

    allCours.findBest(compList);             //find the best course
  }
}
