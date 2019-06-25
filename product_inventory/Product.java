/**
* A program representing a product object and its basic components and operations
* @author Jean Lewis Nikuze
* @version 1.0 3/25/2019
*/

public class Product implements Comparable<Object>{
    private String prod;                //product name
    private int num;                    //product number
    private String category;            //product category
    private boolean avail;              //product availability

   /**
   * Defaulty construct
   */
   public Product(){
      this(null,null,0,false);
   }

   /**
   * Parametrized constructor for a product
   * @param prod the name of the product
   * @param category the category to which the product belongs
   * @param num the number of the product
   */
   public Product(String prod, String category, int num, boolean avail){
       this.prod = prod;
       this.category = category;
       this.num = num;
       this.avail = avail;
   }

 /**
 * Getting the name of the product
 * @return the product name
 * Time Complexity: O(1)
 */
 public String getName(){return prod;}

 /**
 * Getting the number of the product
 * @return the product number
 * Time Complexity: O(1)
 */
 public int getNum(){return num;}

 /**
 * checking if the product is available
 * @return true if the product is available
 * @return false if the product is not available
 * Time Complexity: O(1)
 */
 public boolean isAvailable(){return avail == true;}

 /**
 * Comparing two products by their numbers
 * @param o the object to compare to the current one
 * @return -1 if the number of the passed product is greater than the current product's number
 * @return 1 if the number of the passed product is smaller than the current product's number
 * @return 0 if the two products have same numbers
 * Time Complexity: O(1)
 */
 public int compareTo(Object o){
    Product p = (Product) o;

    if(this.num < p.getNum())
      return -1;                //if the number of the passed product is greater than the current product's number

    else if(this.num == p.getNum())
      return 0;                //return 0 if the two produts have the same product numbers	

    else
      return 1;                //if the number of the passed product is smaller than the current product's number
 }

 /**
 * Showing details of the product
 * @return the string showing different details of a product
 * Time Complexity: O(1)
 */
 public String toString(){ return ("\n\t" + num +": " + prod + " in the category of " + category +"\n");}

}
