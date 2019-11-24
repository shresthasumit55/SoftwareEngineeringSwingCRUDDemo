package model;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractListModel;

import data.Farmer;
import service.FarmerServiceInterface;

public class FarmerListModel extends AbstractListModel<Farmer> implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FarmerServiceInterface farmerService;
	
	public FarmerListModel(FarmerServiceInterface fsi) {
		farmerService = fsi;
	}

	@Override
	public int getSize() {
		return farmerService.getFarmers().size();
	}

	@Override
	public Farmer getElementAt(int index) {
		return farmerService.getFarmers().get(index);
	}

	@Override
	public void update(Observable o, Object arg) {
		Map<String, Integer> changes = (Map<String, Integer>) arg;
		if(changes.containsKey("new")) {
			fireIntervalAdded(this, changes.get("new"), changes.get("new"));
		}
		if(changes.containsKey("remove")) {
			fireIntervalRemoved(this, changes.get("remove"), changes.get("remove"));
		}
	}

}
