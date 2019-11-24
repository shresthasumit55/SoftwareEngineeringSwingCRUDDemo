package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javax.swing.table.AbstractTableModel;

import data.Cow;
import data.Farmer;

public class SelectedCowsTableModel extends AbstractTableModel implements ObservantTableModel<List<Cow>>{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String[] cowFields = {"id", "Name", "Cost"};
	
	private List<Cow> selectedCows = new ArrayList<Cow>();

	private void setSelectedCows(List<Cow> selectedCows) {
		this.selectedCows = selectedCows;
		this.fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return selectedCows.size();
	}

	@Override
	public int getColumnCount() {
		return cowFields.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String field = cowFields[columnIndex];
		if(field == "Name") {
			return selectedCows.get(rowIndex).getName();
		}
		if(field == "id") {
			return selectedCows.get(rowIndex).getId();
		}
		if(field == "Cost") {
			return selectedCows.get(rowIndex).getCost();
		}
		return null;
	}
	
	public String getColumnName(int column) {
		return cowFields[column];
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg != null) {
			setSelectedCows(((Farmer)arg).getCows());
		}
		else {
			setSelectedCows(new ArrayList<Cow>());
		}
	}

	@Override
	public List<Cow> getObservedValue() {
		return new ArrayList<Cow>(selectedCows);
	}



}
