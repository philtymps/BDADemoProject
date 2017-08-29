package com.extension.ilog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import oms.AbstractOmsToOptimizerMapper;
import oms.builders.ArcPropertyBuilder;
import oms.builders.DemandBuilder;
import oms.builders.IRowBuilder;
import oms.builders.InventoryBuilder;
import oms.builders.LocationBuilder;
import oms.builders.NodeTypeBuilder;
import oms.builders.ParameterBuilder;
import oms.builders.PickingCostBuilder;
import oms.builders.ProductBuilder;
import oms.builders.ProductNodePropertyBuilder;
import oms.builders.ProductPropertyBuilder;
import oms.builders.SpaceArcBuilder;
import oms.builders.SpaceNodeBuilder;




public class MockOmsToOptimizerMapper extends AbstractOmsToOptimizerMapper{


	@Override 
	public void update() throws Exception{
		
		List<IRowBuilder> builders;
		int currentDay = 0;
		int lastDay = 0;
		builders = new ArrayList<IRowBuilder>();
		builders.add(new ParameterBuilder(currentDay,lastDay));
		addRows(this.parameters, builders);
		System.out.println("parameters updated");
//		build products
		List<String> products = Arrays.asList(new String[]{"sku1", "sku2", "sku3", "sku4", "sku5"});
		builders = new ArrayList<IRowBuilder>();
		for (String product : products) {
			builders.add(new ProductBuilder(product));
		}
		addRows(this.products, builders);
		System.out.println("products updated");
//		build locations
//		ID	LATITUDE	LONGITUDE
//		
//		
//		
//		
//		
//		
//		
//		List<String> locations = Arrays.asList(new String[]{"sa", "sb", "sc", "cd", "hb", "fc", "sh"});
		builders = new ArrayList<IRowBuilder>();
		
		builders.add(new LocationBuilder("sa",48.112,-1.674));
		builders.add(new LocationBuilder("sb",49.443,1.099));
		builders.add(new LocationBuilder("sc",49.9,2.3));
		builders.add(new LocationBuilder("cd",48.717,2.25));
		builders.add(new LocationBuilder("hb",50.633,3.059));
		builders.add(new LocationBuilder("fc",47.317,5.017));
		builders.add(new LocationBuilder("sh",48.684,6.185));
		addRows(this.locations, builders);
		System.out.println("locations updated");
//		build product properties
		builders = new ArrayList<IRowBuilder>();
		builders.add(new ProductPropertyBuilder("sku1",12));
		builders.add(new ProductPropertyBuilder("sku2",40));
		builders.add(new ProductPropertyBuilder("sku3",1));
		builders.add(new ProductPropertyBuilder("sku4",0.2));
		builders.add(new ProductPropertyBuilder("sku5",60));
		addRows(productProperties, builders);
//		build product nodes properties
		builders = new ArrayList<IRowBuilder>();
		builders.add(new ProductNodePropertyBuilder("sku1","hb",12));
		builders.add(new ProductNodePropertyBuilder("sku2","hb",40));
		builders.add(new ProductNodePropertyBuilder("sku3","hb",1));
		builders.add(new ProductNodePropertyBuilder("sku4","hb",0.2));
		builders.add(new ProductNodePropertyBuilder("sku5","hb",60));
		addRows(productNodeProperties, builders);
		System.out.println("product properties updated");
//		build demands
		builders = new ArrayList<IRowBuilder>();
		builders.add(new DemandBuilder("omsOrder1","line0","sku1","fc",0,5));
		builders.add(new DemandBuilder("omsOrder2","line0","sku2","fc",0,6));
		builders.add(new DemandBuilder("omsOrder3","line0","sku3","fc",0,10));
		builders.add(new DemandBuilder("omsOrder4","line0","sku4","fc",0,1));
		builders.add(new DemandBuilder("omsOrder5","line0","sku5","fc",0,1));
		addRows(demands, builders);
		System.out.println("demands updated");
//		build inventories
		builders = new ArrayList<IRowBuilder>();
		builders.add(new InventoryBuilder("sku1","sa",0,5));
		builders.add(new InventoryBuilder("sku2","sb",0,6));
		builders.add(new InventoryBuilder("sku3","sh",0,10));
		builders.add(new InventoryBuilder("sku4","sc",0,1));
		builders.add(new InventoryBuilder("sku5","sb",0,1));
		addRows(inventories, builders);
		System.out.println("inventories updated");
//		build node types
		builders = new ArrayList<IRowBuilder>();
		builders.add(new NodeTypeBuilder("supplier"));
		builders.add(new NodeTypeBuilder("cross-dock"));
		builders.add(new NodeTypeBuilder("hub"));
		builders.add(new NodeTypeBuilder("final-consignee"));
		builders.add(new NodeTypeBuilder("shop"));
		addRows(nodeTypes, builders);
		System.out.println("node types updated");
//		build space nodes
		builders = new ArrayList<IRowBuilder>();
		builders.add(new SpaceNodeBuilder("sa","supplier"));
		builders.add(new SpaceNodeBuilder("sb","supplier"));
		builders.add(new SpaceNodeBuilder("sc","supplier"));
		builders.add(new SpaceNodeBuilder("cd","cross-dock"));
		builders.add(new SpaceNodeBuilder("hb","hub"));
		builders.add(new SpaceNodeBuilder("fc","final-consignee"));
		builders.add(new SpaceNodeBuilder("sh","shop"));
		addRows(spaceNodes, builders);
		System.out.println("space nodes updated");
//		build space arcs
		builders = new ArrayList<IRowBuilder>();
		builders.add(new SpaceArcBuilder("sa","cd"));
		builders.add(new SpaceArcBuilder("sb","cd"));
		builders.add(new SpaceArcBuilder("sc","cd"));
		builders.add(new SpaceArcBuilder("cd","fc"));
		builders.add(new SpaceArcBuilder("sa","hb"));
		builders.add(new SpaceArcBuilder("sb","hb"));
		builders.add(new SpaceArcBuilder("sc","hb"));
		builders.add(new SpaceArcBuilder("sh","hb"));
		builders.add(new SpaceArcBuilder("hb","fc"));
		addRows(spaceArcs, builders);
		System.out.println("space arcs updated");
//		build arc properties
		builders = new ArrayList<IRowBuilder>();
		builders.add(new ArcPropertyBuilder("sa","cd",0,1));
		builders.add(new ArcPropertyBuilder("sb","cd",0,1));
		builders.add(new ArcPropertyBuilder("sc","cd",0,1));
		builders.add(new ArcPropertyBuilder("cd","fc",0,10));
		builders.add(new ArcPropertyBuilder("sa","hb",0,0));
		builders.add(new ArcPropertyBuilder("sb","hb",0,0));
		builders.add(new ArcPropertyBuilder("sc","hb",0,0));
		builders.add(new ArcPropertyBuilder("sh","hb",0,0));
		builders.add(new ArcPropertyBuilder("hb","fc",0,0));
		addRows(arcProperties, builders);
		System.out.println("arc properties updated");
//		build picking costs
		builders = new ArrayList<IRowBuilder>();
		builders.add(new PickingCostBuilder("sa",5));
		builders.add(new PickingCostBuilder("cd",1));
		builders.add(new PickingCostBuilder("sh",1));
		addRows(pickingCosts, builders);
		System.out.println("picking costs updated");
//		build shutdowns
		builders = new ArrayList<IRowBuilder>();
		addRows(shutdowns, builders);
		scenario.endModifications();
		
	}
	
}
