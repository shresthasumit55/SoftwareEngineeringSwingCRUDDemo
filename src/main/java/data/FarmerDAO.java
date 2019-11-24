package data;

import java.util.List;

public interface FarmerDAO {
	public Farmer getFarmerById(int id);
	public List<Farmer> getAllFarmers();
	public boolean saveFarmer(Farmer farmer);
	public boolean updateFarmer(Farmer farmer);
	public boolean deleteFarmer(Farmer farmer);
	
}
