package runner;

import java.awt.EventQueue;
import java.util.List;

import data.Cow;
import model.FarmerListModel;
import model.ObservantTableModel;
import model.SelectedCowsTableModel;
import service.FarmerService;
import view.Window;

public class WindowRunner {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				FarmerService fs = new FarmerService();
				FarmerListModel listModel = new FarmerListModel(fs);
				fs.addObserver(listModel);
				ObservantTableModel<List<Cow>> otm = new SelectedCowsTableModel();
				Window app = new Window(listModel, otm, fs);
				app.addObserver(otm);
				app.setVisible(true);
			}
			
		});
	}

}
