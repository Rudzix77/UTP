package zad1;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class CountryTable{

	String fileName;

	public CountryTable(String fileName){
		this.fileName = fileName;
	}

	public JTable create() throws Exception{

		ModelX modelX = new ModelX();

		try(Scanner scan = new Scanner(new File(fileName))) {

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
		table.getColumn(table.getColumnName(2)).setCellRenderer(new RenderX());
		table.getColumn(table.getColumnName(4)).setCellRenderer(new RenderT());

		return table;
	}

	private ImageIcon getCountryIcon(String countryCode) throws Exception{

		try{
			Image image = ImageIO.read(new File("data/icons/" + countryCode + ".png"));
			ImageIcon icon = new ImageIcon(image.getScaledInstance(30, 20, Image.SCALE_SMOOTH));

			return icon;
		}catch (Exception e){
			throw new Exception("Nie znaleziono pliku flagi dla cc: " + countryCode);
		}


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

