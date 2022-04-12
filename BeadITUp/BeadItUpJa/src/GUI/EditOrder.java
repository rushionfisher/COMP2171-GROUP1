package GUI;

import javax.swing.*;
//import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Inventory.*;
import Order.*;


public class EditOrder extends JFrame {
    


    private JLabel titleLabel;
    private JLabel oFind;
    private JTextField oEntry;

    private JButton changeBtn;
    private JButton ordFind;
    
    private JLabel notFound;
    private JLabel tryAgain;
    private JLabel ordUpd;
    private JLabel ordNumLabel;
    private JLabel statusLabel;
    private JLabel totCstLabel;

    private JButton closeBtn;
    private Order updOrd;
    private Order findOrd;
    private String ordNum;
    private String stat;
    private String totCst;


    public EditOrder()
    {
        setLayout(null);
        setSize(800,600);

        titleLabel = new JLabel("UPDATE ORDER STATUS");
        titleLabel.setBounds(120,30,220,40);
        titleLabel.setFont(titleLabel.getFont().deriveFont(18f));

        oFind = new JLabel("Enter the Order Number");
        oFind.setBounds(140,90,400,40);
        oFind.setFont(oFind.getFont().deriveFont(15f));

        oEntry = new JTextField();
        oEntry.setBounds(140,120,150,20);

         
        ordFind = new JButton("FIND");
        ordFind.setBounds(160,155,100,35);
        ordFind.addActionListener(new bFindListener());


        ordNum  = "Order Number" ;
        stat = "Customer Status";
        totCst = " Total Cost";

        ordNumLabel = new JLabel(ordNum);
        ordNumLabel.setBounds(160,180,200,35);
        ordNumLabel.setVisible(false);

        statusLabel = new JLabel(stat);
        statusLabel.setBounds(160,220,200,35);
        statusLabel.setVisible(false);

        totCstLabel = new JLabel(totCst);
        totCstLabel.setBounds(160,250,200,35);
        totCstLabel.setVisible(false);
        
        changeBtn = new JButton("CHANGE");
        changeBtn.setBounds (160,380,200,40);
        changeBtn.addActionListener(new UpdateItemListener());
        changeBtn.setVisible(true);


        notFound = new JLabel("ORDER Not Found!");
        notFound.setBounds(140,195,200,50);
        notFound.setFont(notFound.getFont().deriveFont(15f));
        notFound.setVisible(false);

        tryAgain = new JLabel("Try Again!");
        tryAgain.setBounds(180,230,100,35);
        tryAgain.setFont(tryAgain.getFont().deriveFont(15f));
        tryAgain.setVisible(false);


        ordUpd = new JLabel ("ORDER Successfully UPDATED!");
        ordUpd.setBounds(110,450,250,50);
        ordUpd.setFont(ordUpd.getFont().deriveFont(15f));
        ordUpd.setVisible(false);

        closeBtn = new JButton("CLICK TO RETURN TO ORDER MENU");
        closeBtn.setBounds(110,500,300,35);
        closeBtn.addActionListener(new closeTabListener());


        add(titleLabel);
        add(oFind);
        add(oEntry);
        add(ordFind);
        add(ordNumLabel);
        add(statusLabel);
        add(totCstLabel);
        add(changeBtn);
        add(notFound);
        add(tryAgain);
        add(ordUpd);
        add(closeBtn);

    }


    public class UpdateItemListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e){
            
            //ArrayList<Integer>oList = new ArrayList<Integer>();

            for (int z = 0; z < Order.orders.size(); z++){
                if (Order.orders.get(z).getOrderNo() == Integer.parseInt(oEntry.getText())){
                   updOrd = Order.orders.get(z);
                }
            }

            if (updOrd != null){
                updOrd.updateOrder(Integer.parseInt(oEntry.getText()), "status" , "closed");
                notFound.setVisible(false);
                tryAgain.setVisible(false);
                ordUpd.setVisible(true);
                closeBtn.setVisible(true);
                    
            }else{
                notFound.setVisible(true);
                tryAgain.setVisible(true);
                ordUpd.setVisible(false);
            }
        }
    }

    public class bFindListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            for (int z = 0; z < Order.orders.size(); z++){
                if (Order.orders.get(z).getOrderNo() == Integer.parseInt(oEntry.getText())){
                   findOrd = Order.orders.get(z);
                }
            }
            if (findOrd != null) {
                System.out.println("Order");
                
                ordNum= oEntry.getText();
                stat= String.valueOf(findOrd.getStatus());
                totCst  = String.valueOf(findOrd.getCost());
                //System.out.println(bracelet);
                //System.out.println(collection);
                //System.out.println(cst);
                ordNumLabel.setText(ordNum);
                ordNumLabel.setVisible(true);

                statusLabel.setText(stat);
                statusLabel.setVisible(true);

                totCstLabel.setText(totCst);
                totCstLabel.setVisible(true);

                notFound.setVisible(false);
                tryAgain.setVisible(false);

            }else{
                notFound.setVisible(true);
                tryAgain.setVisible(true);
                ordNumLabel.setVisible(false);
                statusLabel.setVisible(false);
                totCstLabel.setVisible(false);
            }


        }
    }


    public class closeTabListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e){
            OrderMenu newBc = new OrderMenu();
            newBc.setVisible(true);
            dispose();
        }
    }
}

