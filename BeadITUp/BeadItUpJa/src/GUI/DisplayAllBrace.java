package GUI;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import Inventory.*;


public class DisplayAllBrace extends JFrame {
  

    private ArrayList<Bracelet> bracePop;
    private  JScrollPane scrollPane;

    private JTable table;
    private DefaultTableModel model;

    public DisplayAllBrace() {
        setSize(900,900);


        String[] columnNames=  {"Name","SMALL BEAD","SMALL COST", "MED BEAD","MED COST", "LARGE BEAD","LARGE COST","COLLECTION"};
        model=new DefaultTableModel(columnNames,0);
        table = new JTable(model);
        model.addRow(columnNames);
        bracePop = Bracelet.getBracelets();
        for (int i = 0 ;i < bracePop.size();i++){
            String name= bracePop.get(i).getName();
            String collection = bracePop.get(i).getCollection();
            String small = bracePop.get(i).getSmallBeadQty();
            String sCost = String.valueOf(bracePop.get(i).getSmlCost());
            String med  = bracePop.get(i).getMedBeadQty();
            String mCost = String.valueOf(bracePop.get(i).getMedCost());
            String large = bracePop.get(i).getLgBeadQty();
            String lCost = String.valueOf(bracePop.get(i).getLgCost());
            //String cost  =String.valueOf(bracePop.get(i).getCost());
            String[] item={name,small,sCost,med,mCost,large,lCost,collection};
            model.addRow(item);        
        }
        table.setVisible(true);

        add(table);

       
        

    }


}
