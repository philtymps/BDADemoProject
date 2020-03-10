/*******************************************************************************
 * IBM Confidential
 * OCO Source Materials
 * IBM Sterling Order Management Store (5725-D10), IBM Order Management (5737-D18), IBM Store Engagement (5737D58)
 * (C) Copyright IBM Corp. 2017 All Rights Reserved.
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 ******************************************************************************/

/**
 *@iscdoc viewmodalrootconfig
 *@viewname store.views.backroompick.included-in-batch.included-in-batch-shipment
 *@package store.views.backroompick.shortage
 *@class included-in-batch-shipment
 *@description This modal displays Product details.
 *@modaltemplate ./store/views/backroompick/included-in-batch/included-in-batch-shipment.tpl.html
 */

angular.module('store').config(['iscModalProvider',function(iscModalProvider){


	iscModalProvider.registerModal("store.views.shipment.backroom-pick.common.print-pick-ticket.print-pick-ticket",
		{
		
		/**
		  *@iscdoc viewmodalconfig
		  *@viewname store.views.shipment.backroom-pick.common.print-pick-ticket.print-pick-ticket
		  *@configtype modalConfig
		  *@configdata animation "true"
		  *@configdata templateUrl "./store/views/shipment/backroom-pick/common/print-pick-ticket/print-pick-ticket.tpl.html" Template html to load
		  *@configdata controller store.views.shipment.backroom-pick.common.print-pick-ticket.print-pick-ticket" Controller associated with modal
		  *@configdata keyboard "true"
		  *@configdata size "md"
		  */
		
		
			  animation: true,
			  templateUrl: './store/views/shipment/backroom-pick/common/print-pick-ticket/print-pick-ticket.tpl.html',
			  controller:'store.views.shipment.backroom-pick.common.print-pick-ticket.print-pick-ticket',
			  keyboard : true,
			  backdrop : 'static',
			  size: 'md',
			  windowClass: 'print-pick-ticket-dialog'			  
		});
	
}]);

