package Clustering.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Record {
	private HashMap<String, String> cells;
	private List<String> columns;

	public Record() {

	}

	public Record(String x, String y) {
		cells = new HashMap<String, String>();
		cells.put("x", x);
		cells.put("y", y);

	}
	public Record(String tupleLine) {
		cells = new HashMap<String, String>();
		columns = Arrays.asList(new String[] { "x", "y", "class", "truth", "flag" });
		tupleLine = tupleLine.replaceAll(" +", " ");
		String[] cellValues = tupleLine.split(" ");
		cells.put("x", cellValues[0]);
		cells.put("y", cellValues[1]);
	}

	public String toString() {
		return cells.toString();
	}

	public String getValue(String columnName) {
		return cells.get(columnName);
	}

	public void setValue(String columnName, String value) {
		this.cells.put(columnName, value);
	}

	public String getValue(int columnIndex) {
		String columnName = columns.get(columnIndex);
		return getValue(columnName);
	}
}
