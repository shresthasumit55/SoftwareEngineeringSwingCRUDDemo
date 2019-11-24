package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;

public class SQLFarmerDAO implements FarmerDAO{

	@Override
	public List<Farmer> getAllFarmers() {
		List<Farmer> farmers = new ArrayList<Farmer>();
		try {
			Connection sqlConnection = ConnectionFactory.getInstance().getConnection();
			Statement statement = sqlConnection.createStatement();
			ResultSet results = statement.executeQuery("SELECT * FROM farmers");
			Farmer farmer;
			while(results.next()) {
				farmer = new Farmer();
				farmer.setName(results.getString("name"));
				farmer.setId(results.getInt("farmer_id"));
				farmers.add(farmer);
			}
		} catch (SQLException e) {
			// Should replace with log message
			System.out.println("Data could not be retrieved");
			e.printStackTrace();
		}
		return farmers;
	}

	@Override
	public Farmer getFarmerById(int id) {
		return null;
	}

	@Override
	public boolean saveFarmer(Farmer farmer) {
		Connection sqlConnection;
		try {
			sqlConnection = ConnectionFactory.getInstance().getConnection();
			PreparedStatement statement = sqlConnection.prepareStatement("INSERT INTO farmers (name) VALUES (?)");
			statement.setString(1, farmer.getName());
			statement.executeUpdate();

		} catch (SQLException e) {
			// Should replace with log message
			System.out.println("Could not save the farmer");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateFarmer(Farmer farmer) {
		Connection sqlConnection;
		try {
			sqlConnection = ConnectionFactory.getInstance().getConnection();
			PreparedStatement statement = sqlConnection.prepareStatement("UPDATE farmers SET name = ? WHERE farmer_id = ?");
			statement.setString(1, farmer.getName());
			statement.setInt(2, farmer.getId());
			statement.executeUpdate();

		} catch (SQLException e) {
			// Should replace with log message
			System.out.println("Could not update the farmer");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteFarmer(Farmer farmer) {
		Connection sqlConnection;
		try {
			sqlConnection = ConnectionFactory.getInstance().getConnection();
			PreparedStatement statement = sqlConnection.prepareStatement("DELETE FROM farmers WHERE farmer_id = ?");
			statement.setInt(1, farmer.getId());
			statement.executeUpdate();

		} catch (SQLException e) {
			// Should replace with log message
			System.out.println("Could not remove the farmer");
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
