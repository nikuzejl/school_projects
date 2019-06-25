/**
* comparator for different courses
* @ author Jean Lewis Nikuze
* @ version 1.0 5/1/2019
*/

import java.util.Comparator;

public class CourseComparator<Object> implements Comparator<Object>{
      /**comparing two courses
      * @return -1, 1 or O depending on course scores
      * Time Complexity: O(1)
      */
      @SuppressWarnings({"unchecked"})
      public int compare(Object a, Object b) throws ClassCastException {
        Course first = (Course) a;
        Course second = (Course) b;

        if(first.getScore() < second.getScore())        //if b's score if higher
           return 1;

        else if(first.getScore() > second.getScore())   //if a's score is higher
           return -1;

        else				     	         //if a and b have same scores
           return 0;
      }
  }
