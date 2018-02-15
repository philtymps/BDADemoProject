package com.mirakl.transform;

import java.util.ArrayList;

public interface IMiraklCSVParser {
	public String getDelimiter();
	public void parseLine(ArrayList<String> header, String[] record);
}
