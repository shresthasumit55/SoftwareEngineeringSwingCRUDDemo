package model;


import java.util.Observer;

import javax.swing.table.TableModel;

public interface ObservantTableModel<E> extends Observer, TableModel{
	public E getObservedValue();
}
