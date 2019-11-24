package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;

public class SQLCowDAO implements CowDAO{

	@Override
	public Cow findCowById(int id) {
		return null;
	}

	@Override
	public List<Cow> findCowsByFarmerId(int id) {
		List<Cow> cows = new ArrayList<>();
		try {
			Connection connection = ConnectionFactory.getInstance().getConnection();
			PreparedStatement ps = connection.prepareStatement("Select * FROM cowhashes WHERE farmer_id = ?");
			ps.setInt(1, id);
			ResultSet results = ps.executeQuery();
			
			while(results.next()) {
				cows.add(cowFromResultSet(results));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cows;
	}

	@Override
	public boolean saveCow(Cow cow) {
		return false;
	}
	
	private Cow cowFromResultSet(ResultSet result) throws SQLException {
		Cow cow = new Cow();
		cow.setId(result.getInt("cow_id"));
		cow.setName(result.getString("name"));
		cow.setFarmerId(result.getInt("farmer_id"));
		cow.setCost(result.getInt("cost"));
		return cow;
	}

	@Override
	public boolean deleteCow(Cow cow) {
		return false;
	}

	@Override
	public boolean updateCow(Cow cow) {
		try {
			Connection connection = ConnectionFactory.getInstance().getConnection();
			PreparedStatement ps = connection.prepareStatement("UPDATE cowhashes SET farmer_id = ?, cost = ?, name = ? WHERE cow_id = ?");
			ps.setInt(1, cow.getFarmerId());
			ps.setInt(2, cow.getCost());
			ps.setString(3, cow.getName());
			ps.setInt(4, cow.getId());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
