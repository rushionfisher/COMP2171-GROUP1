package Inventory;
/**
 * @author Taye-Vaughn Jones
*/

import Authentication.Authentication;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;


public class Bracelet{
    private String name;
    private String collection;
    private int ID;
    private static int nextID = 0;
    private double cost;
    private double smallCost;
    private double medCost;
    private double lgCost;
    private static ArrayList <Bracelet> bracelets = new ArrayList <Bracelet>();
    private ArrayList <String> beadQty = new ArrayList <String>();  //["bead-integernumberofamtrequired","bead2-integernumberofamtrequired",...]
    

    //Constructor
    /**
     * 
     * @param name
     * @param cost
     * @param beadQty_small
     * @param beadQty_med
     * @param beadQty_lg
     * @param beadsString
     */
    public Bracelet(String name, String beadQty_small, double smallCost, String beadQty_med, double medCost, String beadQty_lg,double lgCost,String collection){
        this.name = name;
        this.smallCost = smallCost;
        this.medCost  = medCost;
        this.lgCost = lgCost;
        //this.cost = cost;
        this.ID = nextID;
        this.collection = collection;
        nextID++;
      
        //Recording the number of beadsString needed to make each size bracelet

        if(beadQty_small.equals("")){
            beadQty.add(null);
        }
        else{
            beadQty.add(beadQty_small);
        }
        if(beadQty_med.equals("")){
            beadQty.add(null);
        }
        else{
            beadQty.add(beadQty_med);
        }
        if(beadQty_lg.equals("")){
            beadQty.add(null);
        }
        else{
            beadQty.add(beadQty_lg);
        }
    }

     /**
     * Call this function immediately after creatinga  bracelet instance
     * @param b bracelet
     */
    public static void addToArray(Bracelet b){
        bracelets.add(b);
    }

    //Getters
    /**
     * 
     * @return bracelet name
     */
    public String getName(){
        return this.name;
    }

    /**
     * 
     * @return bracelets array
     */
    public static ArrayList <Bracelet> getBracelets(){
        return bracelets;
    }

    /**
     * 
     * @return bracelet collection
     */
    public String getCollection(){
        return this.collection;
    }

    /**
     * return bracelet ID
     * @return bracelet ID
     */
    public int getID(){
        return this.ID;
    }
    
    /**
     * 
     * @return bracelet cost
     */
    public double getCost(){
        return this.cost;
    }


    /**
     * 
     * @return bead quantity and price for a small sized bracelet
     */
    public String getSmallBeadQty(){
        return beadQty.get(0);
    }

    public double getSmlCost(){
        return this.smallCost;
    }

    /**
     * 
     * @return bead quantity for a medium sized bracelet
     */
    public String getMedBeadQty(){
        return beadQty.get(1);
    }

    public double getMedCost(){
        return this.medCost;
    }


    /**
     * 
     * @return bead quantity and price for a large sized bracelet
     */
    public String getLgBeadQty(){
        return beadQty.get(2);
    }

    public double getLgCost(){
        return this.lgCost;
    }
 
 
      
    //Setters

    /**
     * 
     * @param name name of a bracelet
     */
    public void setName(String name){
        this.name = name;
    }
    
    /**
     * 
     * @param ID bracelet ID
     */
    public void setID(int ID){
        this.ID = ID;
    }

    /**
     * 
     * @param collection bracelet collection
     */
    public void setCollection(String collection){
        this.collection = collection;
    }

    /**
     * 
     * @param cost bracelet cost
     */
    public void setCost(double cost){
        this.cost = cost;
    }

    public void setSmlCost(double cost){
        this.smallCost = cost;
    }
 
    public void setMedCost(double cost){
        this.medCost = cost;
    }
 
    public void setLgCost(double cost){
        this.lgCost = cost;
    }




    /**
     * Add bracelet to database
     * @param b Bracelet object
     */
    public void addToDatabase(){
        try{
            if(Bracelet.searchByName(getName()) == null || Bracelet.getBracelets().size() == 0){
                Connection conn = Authentication.getDbConn();
                String query = "insert into bracelet (id, name,collection,smallBeads, smallCost, mediumBeads, medCost, largeBeads, lgCost)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
                // create the mysql insert prepared statement
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setInt(1, this.getID());
                preparedStmt.setString(2, this.getName());
                preparedStmt.setString(3, this.getCollection());
                //preparedStmt.setDouble(4, this.getCost());
                preparedStmt.setString(4, this.getSmallBeadQty());
                preparedStmt.setDouble(5, this.getSmlCost());
                preparedStmt.setString(6,  this.getMedBeadQty());
                preparedStmt.setDouble(7, this.getMedCost());
                preparedStmt.setString(8,  this.getLgBeadQty());
                preparedStmt.setDouble(9, this.getLgCost());
                // execute the preparedstatement
                preparedStmt.execute();
                System.out.println("Added to database");
                Bracelet.addToArray(this);
            }
            else{
                System.out.println("Duplicate bracelet creation attempt!");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
        
  


  //populate bracelet array with info from database
    public static void populate(){
      try {
            //Creating a DB Connect Object to connect to database
            Connection conn = Authentication.getDbConn();
            // SQL command data stored in String datatype
            String sql = "select * from bracelet";
            PreparedStatement preparedStStmt = conn.prepareStatement(sql);
            ResultSet rs = preparedStStmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String collection = rs.getString("collection");
                //double cost = rs.getDouble("cost");
                String bead_quantity_small = rs.getString("smallBeads");
                double smlCost  = rs.getDouble("smallCost");
                String bead_quantity_medium = rs.getString("mediumBeads");
                double medCost  = rs.getDouble("medCost");
                String bead_quantity_large = rs.getString("largeBeads");
                double lgCost  = rs.getDouble("lgCost");
                Bracelet b = new Bracelet(name,bead_quantity_small,smlCost, bead_quantity_medium, medCost, bead_quantity_large, lgCost, collection);
                b.setID(id);
                bracelets.add(b);
                if(b.getID()>=nextID){
                    nextID = b.getID()+1;
                }
            }

        }
 
        catch (SQLException e) {
            System.out.println(e);
        }
    }

  
    
    /**
     * 
     * @param name bracelet name
     * @return bracelet object
     */
    public static Bracelet searchByName(String name){
        for(int i = 0; i < bracelets.size(); i++){
            if(((bracelets.get(i)).getName()).equals(name)){
                //pass to authentication function
                return bracelets.get(i);
            }
        }

        return null;
    }

    //Display bracelet details when given the name
    /**
     * 
     * @param name bracelet name
     */
    public static void displayBracelet(String name){
        Bracelet bracelet;
        bracelet = searchByName(name);
        if(bracelet == null){
            return;
        }
        else{
            System.out.println(bracelet.getName());
            System.out.println(bracelet.getCollection());
            System.out.println(bracelet.getCost());
            System.out.println(bracelet.getID());
        }

    }

    //Return index of bracelet
    /**
     * 
     * @param name bracelet name
     * @return index of bracelet in array
     */
    public static int getBraceletIndex(String name){
        for(int i=0; i < bracelets.size(); i++){
            if(((bracelets.get(i)).getName()).equals(name)){
                return i;
            }
        }
        return -1;
    }


    //Delete a bracelet
    /**
     * 
     * @param name bracelet name
     */
    public static void deleteBracelet(String name){
        try {  
            Connection conn = Authentication.getDbConn();
            PreparedStatement st = conn.prepareStatement("DELETE FROM bracelet WHERE name = ?");
            st.setString(1,name);
            st.executeUpdate();
            if(getBraceletIndex(name) == -1){
                System.out.println("Bracelet does not exist");
            }
            bracelets.remove(getBraceletIndex(name));
        } catch(Exception e) {
            System.out.println(e);
        }
    }



    //Estimate bracelet quantity

    /**
     * 
     * @return arraylist including the number of bracelets that can be made based on stock levels
     */
    public ArrayList <Integer> estimateQty(){        
        int smallMin = 999999999;
        int medMin = 999999999;
        int largeMin = 999999999;
        String [] beadTypesSmall;
        String [] beadTypesMed;
        String [] beadTypesLg;
        ArrayList <Integer> qty = new ArrayList <Integer>();
        
        if(getSmallBeadQty() == null){
            qty.add(null);
        }
        else
        {
            beadTypesSmall = (getSmallBeadQty()).split(";");
            for(int i = 0; i < beadTypesSmall.length; i++){
                if((Stock.getQuantity((beadTypesSmall[i].split("-"))[0])) / Integer.parseInt((beadTypesSmall[i].split("-"))[1]) < smallMin){
                    smallMin = (Stock.getQuantity((beadTypesSmall[i].split("-"))[0])) / Integer.parseInt((beadTypesSmall[i].split("-"))[1]);
                }
            }
            qty.add(smallMin);
        }

        if(getMedBeadQty() == null){
            qty.add(null);
        }
        else{
            beadTypesMed = (getMedBeadQty()).split(";");
            for(int i = 0; i < beadTypesMed.length; i++){
                if((Stock.getQuantity((beadTypesMed[i].split("-"))[0])) / Integer.parseInt((beadTypesMed[i].split("-"))[1])< medMin){
                    medMin = (Stock.getQuantity((beadTypesMed[i].split("-"))[0])) /  Integer.parseInt((beadTypesMed[i].split("-"))[1]);
                }
               
            }
            qty.add(medMin);
        }
        
        if(getLgBeadQty() == null){
            qty.add(null);
        }
        else
        {
            beadTypesLg = (getLgBeadQty()).split(";");
            for(int i = 0; i < beadTypesLg.length; i++){
                if((Stock.getQuantity((beadTypesLg[i].split("-"))[0])) / Integer.parseInt((beadTypesLg[i].split("-"))[1]) < largeMin){
                    largeMin = (Stock.getQuantity((beadTypesLg[i].split("-"))[0])) /  Integer.parseInt((beadTypesLg[i].split("-"))[1]);
                }
            }
            qty.add(largeMin);
        }

        // null is returned if the bead type and number of beads wasnt specified, otherwise the maximum # of bracelets that can be made based on stock level 
        //is returned for each size, with index 0-2 being the small, mec and large bracelets in that order.
        return qty;
    }

    /**
     * 
     * @param name name of old bracelet
     * @param editedBracelet edited bracelet
     */
    public static void updateBracelet(String name, Bracelet editedBracelet){
        Bracelet b = searchByName(name);
        try {  
            Connection conn = Authentication.getDbConn();
            PreparedStatement st = conn.prepareStatement("UPDATE bracelet SET id = ?, name = ?, collection = ?, cost = ?, smallBeads = ?, smallCost = ?, mediumBeads = ?, medCost = ?, largeBeads = ?, lgCost = ? where name = ?");
            st.setInt(1,b.getID());
            st.setString(2,editedBracelet.getName());
            st.setString(3,editedBracelet.getCollection());
            //st.setDouble(4,editedBracelet.getCost());
            st.setString(4,editedBracelet.getSmallBeadQty());
            st.setDouble(5,editedBracelet.getSmlCost());
            st.setString(6,editedBracelet.getMedBeadQty());
            st.setDouble(7,editedBracelet.getMedCost());
            st.setString(8,editedBracelet.getLgBeadQty());
            st.setDouble(9,editedBracelet.getLgCost());
            st.setString(10,name);
            st.executeUpdate();
            bracelets.remove(Bracelet.getBraceletIndex(b.getName()));
            bracelets.add(editedBracelet);
        } catch(Exception e) {
            System.out.println(e);
        }

    }   

}