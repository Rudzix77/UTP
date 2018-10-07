/**
 *
 *  @author Jankowski Michał S16842
 *
 */

package zad1;


import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;

public class Main {

    private JTable ctab;

    public void createTable(String countriesFileName) throws Exception {
        ctab = new CountryTable(countriesFileName).create();
    }

    public void showGui() {
        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                JFrame f = new JFrame("Countries table");
                f.add( new JScrollPane(ctab) );
                f.pack();
                f.setLocationRelativeTo(null);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setVisible(true);
            }
        });
    }

    public static void main(String[] args)  {
        Main main = new Main();
        try {
            main.createTable("data/countries.txt");
            main.showGui();
        } catch(Exception exc) {
            JOptionPane.showMessageDialog(null, "Table creation failed, " + exc);
        }
    }
}

class CountryTable{

    String fileName;

    public CountryTable(String fileName){
        this.fileName = fileName;
    }

    public JTable create(){

        Vector<Vector<Object>> data = new Vector<>();
        Vector<Object> headers = new Vector<>();
        Vector<Object> row;
        boolean first = true;


        try{
           Scanner scan = new Scanner(new File(Paths.get("").toAbsolutePath().toString()+"/"+fileName));

            while(scan.hasNextLine()){

                String[] line = scan.nextLine().split("\t");

                row = new Vector<>();

                for(String e : line){
                    row.add(e);
                }

                System.out.println(row);

                if(first){
                    headers.add(row);
                    first = false;
                }else{
                    data.add(row);
                }

            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }


        return new JTable(data, headers);
    }

}

class ModelX extends AbstractTableModel{

}
