package service;

import java.util.List;

import data.Cow;
import data.Farmer;

public interface FarmerServiceInterface {
	public List<Farmer> getFarmers();
	public ServiceResponse saveFarmer(Farmer farmer);
	public ServiceResponse deleteFarmer(Farmer farmer);
	public ServiceResponse transferCowsToFarmer(List<Cow> cows, Farmer farmer);
	public ServiceResponse editFarmer(Farmer farmer);
}
