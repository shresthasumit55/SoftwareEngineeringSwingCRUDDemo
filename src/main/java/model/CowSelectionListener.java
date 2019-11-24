package model;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CowSelectionListener implements ListSelectionListener{

	@Override
	public void valueChanged(ListSelectionEvent e) {
		System.out.print(e.getFirstIndex());
	}

}
