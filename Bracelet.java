import java.util.ArrayList;


public class Bracelet{
    private String name;
    private String collection;
    private int ID;
    private static int nextID = 0;
    private double smallCost;
    private double medCost;
    private double lgCost;
    public static ArrayList <Bracelet> bracelets = new ArrayList <Bracelet>();
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
    public Bracelet(String name,String beadQty_small,double smallCost,String beadQty_med,double medCost,String beadQty_lg,double lgCost, String collection){
        this.name = name;
        this.smallCost = smallCost;
        this.medCost  = medCost;
        this.lgCost = lgCost;
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


    public static Bracelet searchByName(String name){
        for(int i = 0; i < bracelets.size(); i++){
            if(((bracelets.get(i)).getName()).equals(name)){
                //pass to authentication function
                return bracelets.get(i);
            }
        }

        return null;
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
     * @return bead quantity and price for a medium sized bracelet
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
     * @param cost bracelet cost for each size
     */
    public void setSmlCost(double cost){
        this.smallCost = cost;
    }

    public void setMedCost(double cost){
        this.medCost = cost;
    }

    public void setLgCost(double cost){
        this.lgCost = cost;
    }


}