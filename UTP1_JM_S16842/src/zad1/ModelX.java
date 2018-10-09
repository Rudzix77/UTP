package zad1;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ModelX extends AbstractTableModel {

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
