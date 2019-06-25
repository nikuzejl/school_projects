/**
* Program for managing an inventory of products
* @author Jean Lewis Nikuze
* @version 1.0 3/25/2019
*/

import java.util.* ;

public class ProductInventory{
        private String compName;           //name of the compagny
        private int total;                 //the total number of products added
        private int availNum;              //the number of avaialable products
        private static final int TOP = 5;  //variable for the number of elements to display after searching

        private LinkedPositionalList<Integer> prodNums = new LinkedPositionalList<Integer>();  //positional list for storing product numbers
        private Stack<Product> searched = new LinkedStack<Product>();             	            //stack for storing searched products
        private Stack<Product> tempSearched = new LinkedStack<Product>();                      //temporary stack for already searched products
        private LinkedPositionalList<Product> inventory = new LinkedPositionalList<Product>(); //an inventory of products implemented as a positional list

    /**
    * Default constructor
    */
    public ProductInventory(){
	       this("this company");
    }

    /**
    * Parametized constructor for the inventory
    * @param compName the name of the company
    */
    public ProductInventory(String compName){
       this.compName = compName;
       this.total = 0;
       this.availNum = 0;
    }

 /**
 * Checking if a product number has already been used or not
 * @param p the product to check
 * @return true if the product number has already been used
 * @return false if the product number has not yet been used
 * Time Complexity: O(n)
 */
 private boolean alreadyUsed(Product p){
      Iterator <Integer> nums = prodNums.iterator();
      while(nums.hasNext()){
         int pNum = nums.next();
         if(p.getNum()== pNum)
              return true;      //return true if the product number has already been used
     }
     return false;              //return false if it has not yet been used
 }


 /*
 * Adding a new product
 * @ param p the product to insert
 * Time Complexity: O(n)
 */
 public void insert(Product p){
     Position<Product> currentPosition;   //variable for the position to work on
     Product currentProduct;              //the product at the current position

     if(alreadyUsed(p))                   //Checking if a product number has already been used
        System.out.println("\n" + p.getNum() + " has already been used; use another product number");

     else{                                //insert the product if it has not yet been used
        if (inventory.isEmpty())
           inventory.addFirst(p);         //add the product at the beginning of the list if it is empty

        else if(inventory.last().getElement().compareTo(p) == -1)   //if the new element is bigger than the last element of the list, add it at the end of the list
           inventory.addLast(p);

        else if((inventory.first().getElement()).compareTo(p)== 1)  //add new product at the beginning of the list if it's smaller than the first element of the list
           inventory.addFirst(p);

        else if(inventory.size()== 1){                    //add in the first or second position if the list had one element
           currentPosition = inventory.last();
           currentProduct = currentPosition.getElement(); //the one product of the list

           if(currentProduct.compareTo(p) == -1)
              inventory.addLast(p);                       //if the new product's number is bigger than the previous, add it at the end of the list

           else if(currentProduct.compareTo(p) == 1)
              inventory.addFirst(p);                      //add product at the beginning of the list of it is smaller thant the previous
       }

       else
          addBetween(p);             //add product at an appropriate position of the list

       total++;                      //increment the total number of products
       if(p.isAvailable())
         availNum++;                 //increment the total number of available products

       prodNums.addLast(p.getNum()); //save this product number
       System.out.println("\nYou successfully added " + p.getName());
   }
 }

 /**
 * Inserting a product in the positional list
 * @param p the product to insert
 * Time Complexity: O(n)
 */
 private void addBetween(Product p){
        Position<Product> curPosition = inventory.last(); //the position to work on
        Product current;                                  //the product at the current position
        Position<Product> nextPosition;                   //the following position
        Product nextProd;                                 //the following product

        Iterable <Position<Product>> itPositions = inventory.positions();  //iterablle collection of positions of the inventory
        Iterator <Position<Product>> it = itPositions.iterator();          //iterator for those positions

        curPosition = it.next();                                           //the current position
        current = curPosition.getElement();                                //the current product

        nextPosition= inventory.after(curPosition);                        //position after the current position
        nextProd = nextPosition.getElement();                              //producdt at the following position

        while(it.hasNext() && !(current.compareTo(p)== -1 && p.compareTo(nextProd) == -1)){  //iterate until the new product fits between the current and the next products
 	          Position<Product> prodPosition = it.next();         //get the position
            if(inventory.after(prodPosition) != null){          //if it is not the last
                curPosition = prodPosition;                     //make it become the current postion to work on
                current = curPosition.getElement();             //get the product at this position

                nextPosition= inventory.after(curPosition);     //get the next position
                nextProd = nextPosition.getElement();           //get the next product
            }
        }
        inventory.addAfter(curPosition,p);                      //add new product after the current position(between the current and next products)
 }

 /**
 * removing a product by its number
 * @param n the number of the product
 * Time Complexity: O(n)
 */
 public void remove(int n){
    if(inventory.isEmpty())           //error message for trying to remove from an empty inventory
       System.out.println("You need to add products first before you can remove them");

    else{
      Iterator<Product> it = inventory.iterator();
      while(it.hasNext()){
        Product p = it.next();
        if(p.getNum() == n){        //find the product to remove
           if(p.isAvailable())
              availNum--;           //if it was available, decrement the number of available products

           total--;                 //decrement the total number of products
	         System.out.println("\nYou have successfully removed "+ p.getName());
           it.remove();             //remove it
           return;
        }
     }
     System.out.println("\nThis product number does not exist"); //error message if there is no product corresponding to the given number
   }
 }

 /**
 * Displaying all products of the inventory
 * Time Complexity: O(n)
 */
 public void display(){
    if(inventory.isEmpty())
       System.out.println("There is no product to display");   //error message for trying to display an empty inventory

    else
      System.out.println(toString());                          //display all proudcts by calling toString()
 }

 /*
 * Displaying available products
 * Time Complexity: O(n)
 */
 public void displayAvailable(){
     if(inventory.isEmpty())
       System.out.println("You haven't added any products yet"); //error message for trying to display an empty inventory

     else if(availNum == 0)
        System.out.println("There is no available product");     //error message if there is no available product

     else{
       Iterator<Product> it = inventory.iterator();              //iterator of products
       System.out.println("\n\nProducts available in stock of "+ compName);
       while (it.hasNext()){
         Product p = it.next();
         if(p.isAvailable())
	      System.out.println(p);                             //display all available products
       }

       System.out.println("\nTotal number of products: " + total);
       System.out.println("Total number of available products: " + availNum);
     }
 }

 /**
 * Finding a product by its number
 * @param n the number of the product
 * Time Complexity: O(n)
 */
 public Product find(int n){
      Iterator<Product> it = inventory.iterator();       //iterator of products
      Product p;
      while (it.hasNext()){
         p = it.next();
         if(p.getNum()==n){                             //find the product corresponding to the given product number
             searched.push(p);                          //push it on the stack of searched products
             return p;                                  //return it
         }
      }
      System.out.println("\nThere is no product whose number is " + n);  //error if the product was not found
      return null;
 }

 /*
 * The recently searched products
 *Time Complexity: O(1)
 */
 public void topSearched(){
    int size = searched.size();                //size of the stack of searched products
    Product p;                                 //variable for products to display

    if(searched.isEmpty())
      System.out.println("You haven't searched any products yet"); //error if nothing has yet been searched

    else{
      System.out.println("\nThe top searched products are:");

      for(int i = 0; i <size && i < TOP; i++){
         p = searched.pop();
         System.out.println(p);               //print product
         tempSearched.push(p);                //keep it in another temporary stack
      }

    while(tempSearched.size()!= 0)
       searched.push(tempSearched.pop());    //bring back all products to the the stack of searched products
    System.out.println();
    }
 }

 /*
 * Displaying all products of the inventory
 * Time Complexity: O(n)
 */
 public String toString(){
      StringBuilder str = new StringBuilder();      //a string builder to which to append different product details
      Iterator<Product> it = inventory.iterator();
      Product p;
      while (it.hasNext()){
         p = it.next();
         str.append(p.toString());                 //add every product's details to the the output of the whole inventory
      }
      return  "\nAll products of the inventory of " + compName +":\n"+ str.toString() + "\n";
 }


 /*
 * Testing different functionalities of Product and ProductInventory
 * @throws InputMismatchException when the input is not well formatted
 * Time Complexity: O(n)
 */
 public static void main(String args[]) throws InputMismatchException{
     String prod;             //product name
     String cat;              //product category
     int num;                 //product number
     String stop = "yes";     //string for checking if the user wants to stop or not
     int choice = 0;          //variable for the choice of available options
     int x;                   //varible for storing the user's choice
     int pNum;                //prouct number
     int pAvail;              //product availability(1 if avaialable or another integer if unavailable)


 //Text-based menu for user input

/*
     Scanner inputComp = new Scanner(System.in);
     System.out.println("\t Enter the name of the company");
     String compName = inputComp.nextLine();                              //getting the name of the company
     ProductInventory inv = new ProductInventory(compName);               //creating a product inventory object
     System.out.println("Here is the memu of 6 main operations that you can perform:"); //displaying the menu
     System.out.println("\t 1. Add a product");
     System.out.println("\t 2. Remove a product");
     System.out.println("\t 3. Display all products");
     System.out.println("\t 4. Display available products");
     System.out.println("\t 5. Look up a product");
     System.out.println("\t 6. Display the top searched products");

     while(stop.compareTo("q")!= 0 && stop.compareTo("quit")!= 0){
        System.out.print("\nTo choose an operation, please enter a corresponding number from the menu: ");
        try{
           Scanner input = new Scanner(System.in);
           choice= input.nextInt();

           if(choice == 1){    		     //Adding  a new product
             Scanner input1 = new Scanner(System.in);
	     System.out.print("\nEnter product name: ");
             String pName = input1.nextLine();            //product name as a string
             System.out.print("Enter product category: ");
             String pCat = input1.nextLine();              //rpoduct category as a string
             Scanner input2 = new Scanner(System.in);

            try{
              System.out.print("Enter product number: ");
              pNum = input2.nextInt();
              System.out.print("Enter 1 if you want this product to be available or any other integer if you don't: ");
              pAvail = input2.nextInt();
              if(pAvail == 1){
                  Product p = new Product(pName, pCat,pNum, true);    //create product object if the input was well formatted
                  inv.insert(p);
              }

              else{
                  Product p = new Product(pName, pCat,pNum, false);    //create product object if the input was well formatted
                  inv.insert(p);
              }
            }catch(InputMismatchException ex)                          //catch an exception if the product number is not well formatted
            {System.out.println("Please enter a valid integer");}
	 }

	 else if(choice ==2){                  //removing a product
           try{
             System.out.print("\nEnter the number of the product you want to remove: ");
             x = input.nextInt();
             inv.remove(x);                    //remove the product if the input was well formatted
           }catch(InputMismatchException ex)   //catch an InputMismatchException otherwise
	   {System.out.println("Please enter a valid integer");}
        }

        else if(choice == 3)        //display all products
	  inv.display();

        else if(choice ==4)         //display all avaialable products
          inv.displayAvailable();

        else if(choice == 5){       //searching a product
          try{
   	    System.out.print("Enter the number of the product you want to find: ");
            x = input.nextInt();
            inv.find(x);                     //find the product by it number
           }catch(InputMismatchException ex) //catch an exception if the product number is not well formatted
	   {System.out.println("You should enter a valid product number");}
 	}

        else if(choice == 6)       //displayin the top 5 searched products
           inv.topSearched();

        else                       //error if the choice is not between 1 and 6
            System.out.println("Please enter a valid choice between 1 and 6");

       //Asking the user if she want to do other operations or not
        Scanner input3 = new Scanner(System.in);
        System.out.print("\nDo you want to continue?(you can type \"quit\" or \"q\" to stop or another word such as \"yes\" if you want to do other operations) ");
        stop = input3.nextLine();                 //knowing if the user wants to continue other operations or not

      }catch(InputMismatchException ex)          // catching exception for an invalid choice such as a string
      {System.out.println("Please enter a valid integer between 1 and 6");}

     }
*/

     ProductInventory inv = new ProductInventory("ELECTRONIC DEVICES");        //creating an new inventory of products

     //creating new products
     Product p1 = new Product("iPhone", "Mobile Devices", 2, true);
     Product p2 = new Product("MacBook Pro", "Computers", 1, true);
     Product p3 = new Product("MacBook Air", "Thin Computers", 3, true);
     Product p4 = new Product("MacIntosh", "Old Computers", 7, true);
     Product p5 = new Product("iPad", "Mobile Devices", 8, true);
     Product p6 = new Product("BlackBerry", "Old Phones", 5, true);
     Product p7 = new Product("neXT", "Old Brands", 12, false);
     Product p8 = new Product("Samsung Galaxy", "Android Devices", 9, true);

     //operations on an empty inventory
     inv.remove(7);
     inv.display();

     //inserting new products in the inventory
     inv.insert(p7);
     inv.insert(p8);
     inv.insert(p2);
     inv.insert(p1);
     inv.insert(p3);
     inv.insert(p6);
     inv.insert(p5);
     inv.insert(p4);

     //displaying available products
     inv.display();
     inv.displayAvailable();

     //removing products
     inv.remove(7);
     inv.remove(8);

     //searching
     inv.find(4);
     inv.find(5);
     inv.find(2);
     inv.find(3);
     inv.find(1);
     inv.find(9);
     inv.find(12);

     inv.topSearched();       //showing the top searched products
     System.out.println(inv); //Printing the whole inventory using toString()

  }
}
