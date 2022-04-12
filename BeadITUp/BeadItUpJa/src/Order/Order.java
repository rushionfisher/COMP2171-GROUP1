package Order;
/**
 * class is responsible for creating order objects, querying these orders and storing the details to the database
 * @version 1.0
 * @author Mercedes Smith, Marlon Lewis
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.sql.PreparedStatement;
import Authentication.Authentication;
import Inventory.Bracelet;

import java.sql.Date;

public class Order
{
    private int orderNo;
    public static ArrayList <Order> orders = new ArrayList <Order>();
    public ArrayList <String> sizes = new ArrayList <String>();
    private Date orderDate;
    private int customerID;
    private String bracelets = "";
    private String braceletQuantities;
    private String pickupLocation;
    private String braceSizes;
    private double cost;
    private String status;
    
    private Connection conn = Authentication.getDbConn(); //Connection object created

    /**
     * Function used to create new orders and add it to the database
     * @param cusPhoneNumber - phone number for customer
     * @param cusName - name of customer
     * @param braceletQuantities - quantities for each bracelet in the order, in the same order of the string of bracelets in the order
     * @param bracelets - the string of bracelets in the order
     * @param pickupLocation - customer's pickup location
     */
    public Order(String cusPhoneNumber, String cusName, String braceletQuantities,String bracelets, String braceSizes, String pickupLocation)
    {
      reducer(bracelets,braceletQuantities);
      this.pickupLocation = pickupLocation;
      this.orderDate = new java.sql.Date(new java.util.Date().getTime());
      this.braceletQuantities = braceletQuantities;
      this.cost = calcTotalCost(braceletQuantities, bracelets, braceSizes); //calculate the cost based on items and quantities in bracelets and braceletQuantities respectively. 
      this.customerID = getCusId(cusName,cusPhoneNumber);
      this.braceSizes = braceSizes;
      this.status = "open";
      addToDatabase();
    }

    /**
     * consolidates duplicate order items to prevent duplicate items showing in orders
     */
    private void reducer(String b, String q){
      String x="";
      String y="";
      HashMap<String,Integer> c = new HashMap<String,Integer>();

      String[] bs = b.split(",");
      String[] qs = q.split(",");
      for(int i = 0; i < bs.length; i++){
        c.put(bs[i],0);
      }
      for(int i = 0; i < bs.length; i++){
        c.put(bs[i],c.get(bs[i])+Integer.valueOf(qs[i]));
      }
      String r = c.toString().substring(1).replace('}', ' ').strip();
      // System.out.println("[REDUCER ========== ]"+r);
      String[] bqp = r.split(",");
      for(String pair: bqp){
        String[] par = pair.split("=");
        x+=par[0]+",";
        y+=par[1]+",";
      }
      bracelets=x.substring(0, x.length()-1);
      braceletQuantities=y.substring(0, y.length()-1);
      // System.out.println("[bracelets ========== ]"+this.bracelets);
      // System.out.println("[quantities ========== ]"+this.braceletQuantities);

    }

    /**
     * Method used when loading orders into order objects from the database
     * @param cus_id - int representing customer id
     * @param order_quantity - String representing the number of each bracelet in the bracelets string in order
     * @param bracelets - String representing the bracelets in an Order
     * @param pickup_location - String representing the pickup location
     * @param order_number - int representing the order id for the order
     * @param total - total cost of the Order
     * @param order_date - Date when the order was placed
     */
    public Order(int cus_id, String braceSizes, String order_quantity, String bracelets, String pickup_location,int order_number, Double total, Date order_date, String status){
      this.customerID = cus_id;
      this.braceSizes = braceSizes;
      this.orderNo = order_number;
      reducer(bracelets, order_quantity);
      this.pickupLocation = pickup_location;
      this.orderDate = order_date;
      this.cost = total;
      this.status = status;
    }

    
    /**
     * function gets the total cost of order
     * @return int - total cost of order
     */ 
    private double calcTotalCost(String braceletQuantities, String bracelets, String braceSizes){
      double total = 0;
      ArrayList<String> sizes = new ArrayList<String>();
      sizes.addAll(Arrays.asList(braceSizes.split(",")));

      ArrayList<String> items = new ArrayList<String>();
      items.addAll(Arrays.asList(bracelets.split(",")));

      ArrayList<String> qtys = new ArrayList<String>();
      qtys.addAll(Arrays.asList(braceletQuantities.split(",")));
      try {
        int min =  Math.min(items.size(), sizes.size());
        for(int i = 0; i<min; i++){

          if (sizes.get(i).equalsIgnoreCase("SMALL")){
            Bracelet bracelet = Bracelet.searchByName(items.get(i));
            total+=bracelet.getSmlCost()*Integer.valueOf(qtys.get(i));

          }else if (sizes.get(i).equalsIgnoreCase("MEDIUM")){
            Bracelet bracelet = Bracelet.searchByName(items.get(i));
            total+=bracelet.getMedCost()*Integer.valueOf(qtys.get(i));

          }else if (sizes.get(i).equalsIgnoreCase("LARGE")){
            Bracelet bracelet = Bracelet.searchByName(items.get(i));
            total+=bracelet.getLgCost()*Integer.valueOf(qtys.get(i));
          }
          
        }
      } catch (NullPointerException e) {
        System.out.println("[Error] No bracelet exists for one or more items in yout order!");
      }
      System.out.println(total);
      return total;
    }

    /**
     * function gets the customer id for the order from the database
     * @param cusName - name of customer
     * @param cusPhoneNumber - customer's phone number
     * @return customer id
     */
    private int getCusId(String cusName,String cusPhoneNumber){     
      return Customer.getCusId(cusName, cusPhoneNumber, this.pickupLocation);
        
    }

    /**
     * adds an order to the database
     * @return boolean true if added to the database, false if unsuccessful
     */
    public boolean addToDatabase()
    {
        try{
          String query = "insert into Orders (bracelets, braceSizes, order_quantity, customer_id, pickup_location, order_date, total, status) values (?, ?, ?, ?, ?, ?,?, ?)";

          // create the mysql insert prepared statement
          PreparedStatement preparedStmt = conn.prepareStatement(query);
          preparedStmt.setString(1, this.bracelets);
          preparedStmt.setString(2, this.braceSizes);
          preparedStmt.setString(3, this.braceletQuantities);
          preparedStmt.setInt(4, this.customerID);
          preparedStmt.setString(5, this.pickupLocation);
          preparedStmt.setDate(6, this.orderDate);
          preparedStmt.setDouble(7, this.cost);
          preparedStmt.setString(8, this.status);
          
          // execute the preparedstatement
          preparedStmt.execute();
          System.out.println("[ADDED TO DATABASE]");
          return true;
        }
        catch(Exception e)
        {
          e.printStackTrace();
          System.out.println(e.getMessage());
          return false;
        }
    }

    /**
     * deletes order from database
     * @param orderNo - order number of order to delete
     */
    public static void deleteOrder(int orderNo)
    {
      populate();
       try{
         String query = "Delete from Orders where order_number = ?";
         PreparedStatement preparedStmt = Authentication.getDbConn().prepareStatement(query);
          preparedStmt.setInt(1, orderNo); 
          preparedStmt.execute();
          for (Order o:orders){if(o.orderNo==orderNo){orders.remove(orders.indexOf(o));}}
        }
        catch(Exception e)
        {
          e.printStackTrace();
          System.out.println("ORDER NUMBER "+orderNo+": "+e.getMessage());
        }
    }

    /**
     * function updates an order
     * @param orderNo - order number of the order to be updated
     * @param fields - fields of the table for the record that should be updated. string separated with comma delimiters
     * @param values - values to enter into the fields in the same order as the fields were specified
     * @return boolean confirming successful update or not
     */
    public boolean updateOrder(Integer orderNo, String fields, String values)
    { 
      boolean result = true;
        ArrayList<String> columns = new ArrayList<String>();
        columns.addAll(Arrays.asList(fields.split(","))); 
        ArrayList<String> inputs = new ArrayList<String>();
        inputs.addAll(Arrays.asList(values.split(","))); 
        for (int i = 0; i < columns.size(); i++) 
        {
          try {
            String query = String.format("Update orders set %s=? where order_number = ?",columns.get(i));
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, inputs.get(i));
            preparedStmt.setInt(2, orderNo);
            preparedStmt.executeUpdate();
            System.out.println(String.format("[SUCCESSFULLY UPDATED %s IN ORDER]", columns.get(i).toUpperCase()));
          } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(String.format("[FAILED TO UPDATE %s IN ORDER]",columns.get(i).toUpperCase()));
            return false;
          }
        }
        return result;
    }

    /**
     * function populates a list of orders from the database
     * @return ArrayList<Order>
     */
   public static ArrayList <Order> populate()
   {
     ResultSet result;
    try{
      String query = "select * from orders";

      // create the mysql insert prepared statement
      PreparedStatement preparedStmt = Authentication.getDbConn().prepareStatement(query); //.prepareStatement(query);
      
      // execute the prepared statement
      result = preparedStmt.executeQuery();
    
      while (result.next()) 
      {
        Order e = new Order(result.getInt("customer_id"),result.getString("braceSizes"),result.getString("order_quantity"), result.getString("bracelets"), 
        result.getString("pickup_location"),result.getInt("order_number"), result.getDouble("total"),result.getDate("order_date"), result.getString("status"));
        orders.add(e);
      }
      return orders;
    }
    catch(Exception e)
    {
      e.printStackTrace();
      System.out.println(e.getMessage());
      return orders;
    }
  }

  // ---------- GETTERS ---------- //

  /**
     * function gets the set order number
     * @return int - the order number that has been set.
     */
    public int getOrderNo(){return this.orderNo;}

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

    /**
     * function gets the status for the order
     * @return String - the ordered string with braceletquantities for the order
     */ 
    public String getStatus(){return this.status;}

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

    /**
     * function gets the set cost of this order
     * @return int - cost of the order
     */ 
    public double getCost(){
      if(this.cost>0){
        return cost;
      }
      return calcTotalCost(braceletQuantities, bracelets, braceSizes);
      }

    /**
     * function converts the order to a String
     * @return object String
     */ 
    public String toString(){
      String s = "\nCustomer id: "+this.customerID+"\nPickup: "+this.pickupLocation+"\nOrder #";
      return s+getOrderNo()+": \n Bracelets: "+this.bracelets+"\n Quantities: "+getbraceletQuantities()+"\n Total: "+String.valueOf(getCost());
    }

  }