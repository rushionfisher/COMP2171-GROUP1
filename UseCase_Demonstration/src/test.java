

class test{

    private void braceTest(){

        Bracelet blueMoon;
        Bracelet redRose;
        
        
        System.out.println("Test");

        blueMoon  = new Bracelet("Blue Moon", "Red-2;yellow-4", 2500.00, "Red-5;yellow-7", 2800.00, "Red-8;yellow-10",3000.00,"Moon");
        Bracelet.addToArray(blueMoon);


        redRose = new Bracelet("Red Rose", "Purple-3;Blue-2", 1500.00, "Purple-5;Blue-4", 1600.00, "Purple-9;Blue-11",1850.00,"Roses");
        Bracelet.addToArray(redRose);

        
        for ( Bracelet brace : Bracelet.bracelets){
            System.out.println("Name - "+ " " + brace.getName() + "\n");
            System.out.println("Bracelet ID - " + " " +  brace.getID());
            System.out.println("Bracelet Collection - " + " " +  brace.getCollection());
            System.out.println("Small Qantity and Price - "+ " "+ brace.getSmallBeadQty()+ " " + brace.getSmlCost());
            System.out.println("Medium Qantity and Price - "+ " "+ brace.getMedBeadQty()+ " " + brace.getMedCost());
            System.out.println("Large Qantity and Price - "+ " "+ brace.getLgBeadQty()+ " " + brace.getLgCost()+"\n\n");
        }

    }

    private void orderTest(){

        Order Michael;
        Order Jamie;

        Michael = new Order("876-8901234","Michael Lee-Chin" ,"2,3", "blueMoon,redRose", "small,medium", "Downtown");
        Order.addToArray(Michael);

        Jamie = new Order("876-4320129","Jamie Spears" ,"5,2", "redRose,blueMoon", "small,large", "Downtown");
        Order.addToArray(Jamie);


        

        for (Order order: Order.orders){
            System.out.println(order.getOrderNo());
            System.out.println(order.getCustName());
            System.out.println(order.getorderDate());
            
            String quantities[] = order.getbraceletQuantities().split(",");
            String bracelets [] = order.getBracelets().split(",");

            /*for (String el: quantities){
                for (String brace: bracelets){
                    System.out.println (order.getCustName() + "ordered" + " " + el + " "+ brace);
                }
               
            }*/
    



        }



    }


    public static void main(String[] args) {
        
        test t = new test();
        t.braceTest();
        t.orderTest();
    }




}


