package com.extension.ilog;


import oms.AbstractOptimizerToOmsMapper;
import oms.builders.RouteBuilder;


public class MockOptimizerToOmsMapper extends AbstractOptimizerToOmsMapper{
	public void map(){
		for (RouteBuilder rb : routes) {
			System.out.println(rb);
		}
	}
}
