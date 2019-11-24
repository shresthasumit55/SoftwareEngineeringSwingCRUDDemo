package data;

import java.util.List;

public interface CowDAO {
	public Cow findCowById(int id);
	public List<Cow> findCowsByFarmerId(int id);
	public boolean saveCow(Cow cow);
	public boolean updateCow(Cow cow);
	public boolean deleteCow(Cow cow);
}
