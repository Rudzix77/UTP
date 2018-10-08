/**
 *
 *  @author Jankowski Michał S16842
 *  Icons: http://flagpedia.net/
 */


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

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

    public JTable create() throws Exception{

        ModelX modelX = new ModelX();

        try(Scanner scan = new Scanner(new File(getClass().getResource(fileName).getFile()))) {

            while (scan.hasNextLine()) {

                String[] elements = scan.nextLine().split("\t");

                if(!modelX.hasHeadersSet()){
                    modelX.setHeaders(elements);
                }else{
                    modelX.addData(elements[0], elements[1], Integer.parseInt(elements[2])*1000, getCountryIcon(elements[3]));
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }


        JTable table = new JTable(modelX);
            table.getColumn("Population").setCellRenderer(new RenderX());
            table.getColumn("Last Edit").setCellRenderer(new RenderT());

        return table;
    }

    private ImageIcon getCountryIcon(String countryCode) throws Exception{

        Image image = ImageIO.read(new File(getClass().getResource("data/icons/" + countryCode + ".png").getFile()));
        ImageIcon icon = new ImageIcon(image.getScaledInstance(30, 20, Image.SCALE_SMOOTH));

        return icon;
    }

}

class RenderX extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        JLabel comp = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

        if (((int) value/1000000) > 20) {
            comp.setForeground(Color.RED);
        }else{
            comp.setForeground(Color.BLACK);
        }

        return comp;
    }
}

class RenderT extends DefaultTableCellRenderer {
    @Override
    public void setValue(Object value) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy hh:mm:ss");

        setText((value == null) ? "" : sdf.format(value));
    }
}


class ModelX extends AbstractTableModel {

    List<String> headers;
    List<Object[]> data;


    public ModelX() {
        this.headers = new ArrayList<>();
        this.data = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return headers.size();
    }

    @Override
    public String getColumnName(int column) {
        return headers.get(column);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 2: return Integer.class;
            case 3: return ImageIcon.class;
            case 4: return Date.class;
            default: return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 2 ? true : false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
        data.get(rowIndex)[columnIndex] = aValue;

        data.get(rowIndex)[4] = new Date();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        return data.get(rowIndex)[columnIndex];
    }

    public boolean hasHeadersSet(){
        return (headers.size() != 0 ? true : false);
    }

    public void setHeaders(String[] headers){
        this.headers.addAll(Arrays.asList(headers));
        this.headers.add("Last Edit");
    }


    public void addData(String name, String capital, Integer population, ImageIcon flag){
        data.add(new Object[]{name, capital, population, flag, new Date()});
    }
}
