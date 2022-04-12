package GUI;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import Inventory.*;


public class DisplayAllStock extends JFrame {
  

    private ArrayList<Stock> allStock;
    private JComboBox<String> stkTypeL;
    private JComboBox<String> QTypeL;
    private JButton filter;
    private JButton closeBtn;
    private ArrayList<Stock>filtered  = new ArrayList<Stock>();
    private ArrayList<String>bounds = new ArrayList<String>();

    private JTable table;
    private JTable filTable;
    private DefaultTableModel model;
    private String name;
    private String type;
    private String quant ;
    private String lowLevel;
    private Stock stk;
    public  DisplayAllStock() {

        //JFrame frame = new JFrame();
        setLayout(new BorderLayout());

        String[] columnNames=  {"NAME","TYPE","QUANTITY","LOW LEVEL NUMBER"};
        model=new DefaultTableModel(columnNames,0);
        table = new JTable(model);
        model.addRow(columnNames);
        allStock = Stock.viewStock(1);
        for (int i = 0 ;i < allStock.size();i++){
            String name= allStock.get(i).getStockName();
            String type = allStock.get(i).getType();

            Stock stk = allStock.get(i);
            String quant = String.valueOf(Stock.getQuantity(stk.getStockName()));
            String lowLevel = String.valueOf(allStock.get(i).getLevel());
            String[] item={name,type,quant,lowLevel};
            model.addRow(item);        
        }


        JPanel btnPnl = new JPanel(new BorderLayout());
        JPanel topBtnPnl = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JPanel bottombtnPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));

        closeBtn = new JButton ("BACK TO MENU");
        closeBtn.addActionListener(new CloseBtnListener());

        topBtnPnl.add(closeBtn);

        filter = new JButton("Filter");
        filter.addActionListener(new FilterListener());

        topBtnPnl.add(filter);
        bottombtnPnl.add(new JLabel("Filter By"));

        String []  stkType = {"Beads","Pouch", "Spacers", "All Types"};
        stkTypeL = new JComboBox<String>(stkType);
        bottombtnPnl.add(stkTypeL);

        String []  QType = {"100-300","300-500","500-700", "700-900", "900-1000", "None"};
        QTypeL = new JComboBox<String>(QType);
        bottombtnPnl.add(QTypeL);

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

    private class FilterListener implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {
            String sFilter =String.valueOf(stkTypeL.getSelectedItem());
            String qFilter = String.valueOf(QTypeL.getSelectedItem());

            bounds  = new ArrayList<String>();
            bounds.addAll(Arrays.asList(qFilter.split("-")));

            if (sFilter.equalsIgnoreCase("All Types") && qFilter != ("None")){
                filtered  =  Stock.viewFilteredStock(1, Integer.valueOf(bounds.get(0)), Integer.valueOf(bounds.get(1)),"-");
                table.setVisible(false);

            }else if (qFilter.equalsIgnoreCase("None") && sFilter != ("All Types")){
                filtered  =  Stock.viewFilteredStock(1, 0, 100000000,sFilter);
                table.setVisible(false);
            }else if (qFilter.equalsIgnoreCase("None") && sFilter.equalsIgnoreCase("All Types")){
                filtered  =  Stock.viewStock(1);
                table.setVisible(false);
            }else{
                filtered  =  Stock.viewFilteredStock(1, Integer.valueOf(bounds.get(0)), Integer.valueOf(bounds.get(1)),sFilter);
                System.out.println(filtered.size());
                table.setVisible(false);

            }

            String[] columnNames=  {"NAME","TYPE","QUANTITY","LOW LEVEL NUMBER"};
            model=new DefaultTableModel(columnNames,0);
            filTable = new JTable(model);
            model.addRow(columnNames);
            for (int i = 0 ;i < filtered.size();i++){
                name= filtered.get(i).getStockName();
                type = filtered.get(i).getType();
                stk = filtered.get(i);
                quant = String.valueOf(Stock.getQuantity(stk.getStockName()));
                lowLevel = String.valueOf(filtered.get(i).getLevel());
                String[] item={name,type,quant,lowLevel};
                model.addRow(item);        
                
            }
            add(filTable, BorderLayout.CENTER);
            

        }
    }

    private class CloseBtnListener implements ActionListener
    {
        public void actionPerformed (ActionEvent event)
        {
            StockMenu newSk = new StockMenu();
            newSk.setVisible(true);
            dispose();
        }
    }


}

