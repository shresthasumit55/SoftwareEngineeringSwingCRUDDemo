package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import data.Cow;
import data.CowDAO;
import data.Farmer;
import data.FarmerDAO;
import data.SQLCowDAO;
import data.SQLFarmerDAO;

public class FarmerService extends Observable implements FarmerServiceInterface {
	private List<Farmer> farmers;
	private FarmerDAO farmerDAO;
	private CowDAO cowDAO;

	public FarmerService() {
		farmerDAO = new SQLFarmerDAO();
		cowDAO = new SQLCowDAO();
	}

	public List<Farmer> getFarmers() {
		if (farmers == null) {
			updateFarmerList();
		}
		return farmers;
	}

	private void updateFarmerList() {
		// Farmer DAO only knows how to retrieve Farmers
		farmers = farmerDAO.getAllFarmers();

		// Use a CowDAO to retrieve the farmers cows
		for (Farmer farmer : farmers) {
			List<Cow> farmersCows = cowDAO.findCowsByFarmerId(farmer.getId());
			farmer.getCows().addAll(farmersCows);
		}
	}

	public ServiceResponse saveFarmer(Farmer farmer) {
		if (farmer.getName().equals("")) {
			return new ServiceResponse(false, "Cannot Save farmer with no name!!");
		}
		// Save the farmer
		if (farmerDAO.saveFarmer(farmer)) {
			postSaveTasks(farmer);
			return new ServiceResponse(true, "Save successful");
		}
		return new ServiceResponse(false, "Save Failed");
	}


	public ServiceResponse editFarmer(Farmer farmer) {
		if (farmer.getName().equals("")) {
			return new ServiceResponse(false, "Cannot update farmer with no name!!");
		}

		// Save the farmer
		if (farmerDAO.updateFarmer(farmer)) {
			postSaveTasks(farmer);
			return new ServiceResponse(true, "Update successful");

		}
		return new ServiceResponse(false, "Update Failed");
	}



	@Override
	public ServiceResponse deleteFarmer(Farmer farmer) {
		if (farmer.getCows().size() > 0) {
			return new ServiceResponse(false, "Cannot remove farmer who owns cows!!");
		}

		// Delete the farmer
		if (farmerDAO.deleteFarmer(farmer)) {

			// Find where the farmer was in the list
			int positionRemoved = farmers.indexOf(farmer);

			// Update the list that service provides
			updateFarmerList();

			// Let everyone know that there is a new farmer
			setChanged();

			Map<String, Integer> changes = new HashMap<>();
			changes.put("remove", positionRemoved);

			notifyObservers(changes);

			// Return success message
			return new ServiceResponse(true, "Deletion Successful");
		}

		return new ServiceResponse(false, "Deletion Failed");
	}

	@Override
	public ServiceResponse transferCowsToFarmer(List<Cow> cows, Farmer farmer) {
		for (Cow cow : cows) {
			if (cow.getFarmerId() == farmer.getId()) {
				return new ServiceResponse(false, "Cannot transfer cow to current owner of cow!!");
			}
		}
		for (Cow cow : cows) {
			cow.setFarmerId(farmer.getId());
			if(!cowDAO.updateCow(cow)) {
				return new ServiceResponse(false, "Transfer Failed");
			}
		}

		// Update the list that service provides
		updateFarmerList();

		// Let everyone know that there is a new farmer
		setChanged();

		return new ServiceResponse(true, "Transfer Successful");

	}


	private void postSaveTasks(Farmer farmer){

		for (Cow cow : farmer.getCows()) {
			cowDAO.saveCow(cow);
		}

		// Update the list that service provides
		updateFarmerList();

		// Let everyone know that there is a new farmer
		setChanged();

		Map<String, Integer> changes = new HashMap<>();
		changes.put("new", farmers.size());

		notifyObservers(changes);

	}
}
