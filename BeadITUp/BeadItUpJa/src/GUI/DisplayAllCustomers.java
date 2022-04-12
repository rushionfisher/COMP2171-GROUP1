package GUI;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import Order.*;


public class DisplayAllCustomers extends JFrame {
  

    private ArrayList<Customer> allCustomers;
    private JComboBox<String> picTypeL;
    private JButton filter;
    private JButton closeBtn;
    private ArrayList<Customer>filtered  = new ArrayList<Customer>();
    //private ArrayList<String>bounds = new ArrayList<String>();

    private JTable table;
    private JTable filTable;
    private DefaultTableModel model;
    private String name;
    private String type;
    private String quant ;
    private String lowLevel;
    //private Stock stk;

    //private  JScrollPane scrollPane;

    public DisplayAllCustomers() {

        setLayout(new BorderLayout());


        String[] columnNames=  {"CUSTOMER ID","CUSTOMER NAME","TELEPHONE", "PICKUP LOCATION",};
        model=new DefaultTableModel(columnNames,0);
        table = new JTable(model);
        model.addRow(columnNames);
        allCustomers = Customer.getCustomers();
        for (int i = 0 ;i < allCustomers.size();i++){
            String custIdString= String.valueOf(allCustomers.get(i).getID());
            String custNameString= allCustomers.get(i).getcustomerName();
            String tPhone= String.valueOf(allCustomers.get(i).getphoneNumber());
            String picLtn = allCustomers.get(i).getpickupLocation();
            String[] item={custIdString,custNameString,tPhone,picLtn};
            model.addRow(item);        
        }

        JPanel btnPnl = new JPanel(new BorderLayout());
        JPanel topBtnPnl = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JPanel bottombtnPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));


        closeBtn = new JButton ("BACK TO MENU");
        closeBtn.addActionListener(new CloseBtnListener());

        topBtnPnl.add(closeBtn);

        filter = new JButton("Filter");
        filter.addActionListener(new FilterListener2());

        topBtnPnl.add(filter);
        bottombtnPnl.add(new JLabel("Filter By"));

        String []  picType = {"Half Way Tree","Downtown", "Liguanea", "All Types"};
        picTypeL = new JComboBox<String>(picType);
        bottombtnPnl.add(picTypeL);

        btnPnl.add(topBtnPnl, BorderLayout.NORTH);
        btnPnl.add(bottombtnPnl, BorderLayout.CENTER);

        table.getTableHeader().setReorderingAllowed(false);
        
        add(table.getTableHeader(), BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
        add(btnPnl, BorderLayout.SOUTH);

        setTitle("Display Stock");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    
       
        

    }

    private class CloseBtnListener implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {
            CustomerMenu newCst = new CustomerMenu();
            newCst.setVisible(true);
            dispose();
        }
    }

    private class FilterListener2 implements ActionListener
    { 
        public void actionPerformed (ActionEvent event)
        {
            String qFilter = String.valueOf(picTypeL.getSelectedItem());

            filtered = Customer.viewFilteredCustomer(1, qFilter);
            table.setVisible(false);

            String[] columnNames=  {"CUSTOMER ID","CUSTOMER NAME","TELEPHONE", "PICKUP LOCATION",};
            model=new DefaultTableModel(columnNames,0);
            filTable = new JTable(model);
            model.addRow(columnNames);
            for (int i = 0 ;i < filtered.size();i++){
                String custIdString= String.valueOf(filtered.get(i).getID());
                String custNameString= filtered.get(i).getcustomerName();
                String tPhone= String.valueOf(filtered.get(i).getphoneNumber());
                String picLtn = filtered.get(i).getpickupLocation();
                String[] item={custIdString,custNameString,tPhone,picLtn};
                model.addRow(item);        
            }
            add(filTable, BorderLayout.CENTER);





        }

    }
}


