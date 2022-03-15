
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class Order {


    private int orderNo;
    private static int nextOrdNo = 0;
    public static ArrayList <Order> orders = new ArrayList <Order>();
    private Date orderDate;
    private int customerID;
    private String bracelets = "";
    private String braceletQuantities;
    private String braceSizes;
    private String pickupLocation;
    private String cusName;
    private double cost;



     /**
     * Function used to create new orders and add it to the database
     * @param cusPhoneNumber - phone number for customer
     * @param cusName - name of customer
     * @param braceletQuantities - quantities for each bracelet in the order, in the same order of the string of bracelets in the order
     * @param bracelets - the string of bracelets in the order
     * @param pickupLocation - customer's pickup location
     */
    public Order(String cusPhoneNumber, String cusName, String braceletQuantities, String bracelets, String braceSizes, String pickupLocation)
    {
    
      this.pickupLocation = pickupLocation;
      this.cusName = cusName;
      this.braceletQuantities = braceletQuantities;
      this.bracelets = bracelets;
      this.orderDate = new java.sql.Date(new java.util.Date().getTime());
      this.braceSizes = braceSizes;
      this.orderNo = nextOrdNo;
      nextOrdNo++;
      
      //this.cost = calcTotalCost(braceletQuantities, bracelets); //calculate the cost based on items and quantities in bracelets and braceletQuantities respectively. 
    }


     /**
     * Call this function immediately after creatinga  bracelet instance
     * @param o order
     */
    public static void addToArray(Order o){
        orders.add(o);
    }
    
  

    /**
     * function gets the set order number
     * @return int - the order number that has been set.
     */
    public int getOrderNo(){return this.orderNo;}


     /**
     * function gets the set customer name relating to the order
     * @return String - the customer name that has been set.
     */
    public String getCustName(){return this.cusName;}


    /**
     * function gets the oder date
     * @return Date - date the order was placed
     */
    public Date getorderDate(){return this.orderDate;}

    /**
     * function gets the ordered string with braceletquantities for the order
     * @return String - the ordered string with braceletquantities for the order
     */ 
    public String getbraceletQuantities(){return this.braceletQuantities;}
    

    public String getBracelets(){return this.bracelets;}


    public String getSize(){
        return this.braceSizes;
    }


    /**
     * function gets the pickup location chosen by Customer
     * @return String - pickup location chosen by Customer
     */ 
    public String getpickupLocation(){return this.pickupLocation;}

    /**
     * function gets the customer ID from object
     * @return int - customer id for order
     */ 
    public int getcustomerID(){return this.customerID;}


    public int getSize(Order order){
        return 10;
    }

    /**
     * function gets the set cost of this order
     * @return int - cost of the order
     */ 
    /*public double getCost(){
      if(this.cost>0){
        return cost;
      }
      return calcTotalCost(braceletQuantities, bracelets);
      }*/

    /**
     * function converts the order to a String
     * @return object String
     */ 
    /*public String toString(){
      String s = "\nCustomer id: "+this.customerID+"\nPickup: "+this.pickupLocation+"\nOrder #";
      return s+getOrderNo()+": \n Bracelets: "+this.bracelets+"\n Quantities: "+getbraceletQuantities()+"\n Total: "+String.valueOf(getCost());
    }*/

}
