/*******************************************************************************
 * IBM Confidential
 * OCO Source Materials
 * IBM Sterling Order Management Store (5725-D10), IBM Order Management (5737-D18), IBM Store Engagement (5737D58)
 * (C) Copyright IBM Corp. 2017 All Rights Reserved.
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 ******************************************************************************/

/**
 *@iscdoc viewmodal
 *@viewname store.views.shipment.backroom-pick.common.print-pick-ticket.print-pick-ticket
 *@package store.views.shipment.backroom-pick.common.print-pick-ticket
 *@class print-pick-ticket
 */
angular.module('store').controller('store.views.shipment.backroom-pick.common.print-pick-ticket.print-pick-ticket',
  ['$scope','$rootScope','$uibModalInstance','iscScreen','iscWizard','modalInput','$filter', 'iscMashup','iscAppInfo','iscResourcePermission','iscI18n','iscModal',
	function($scope,$rootScope,$uibModalInstance,iscScreen,iscWizard,modalInput,$filter,iscMashup,iscAppInfo,iscResourcePermission,iscI18n,iscModal) {
		
		
		iscScreen.initializeModalScreen($scope,{
			
  		model:{
		
  		},

  		mashupRefs : [

  		            /**
                     *@description This mashup is used to get the count of BOPIS Shipments for printing pick ticket which are not printed.
                     */
                    {
                    	 mashupRefId: 'getBOPISShipmentToBePrintedCount',
                         mashupId: 'store.views.shipment.backroom-pick.getBOPISShipmentPrintPickTicketCount'

                    },
                    {
                    	  /**
                         *@description This mashup is used to get the count of BOPIS Shipments for printing pick ticket which are printed.
                         */
                   	 	mashupRefId: 'getBOPISShipmentAlreadyPrintedCount',
                        mashupId: 'store.views.shipment.backroom-pick.getBOPISShipmentPrintPickTicketCount'

                   },
                   {
                	   /**
                        *@description This mashup is used to get the count of SFS Shipments for printing pick ticket which are not printed.
                        */
                   	 	mashupRefId: 'getSFSShipmentToBePrintedCount',
                        mashupId: 'store.views.shipment.backroom-pick.getSFSShipmentPrintPickTicketCount'

                   },
                   {
                	   /**
                        *@description This mashup is used to get the count of SFS Shipments for printing pick ticket which are printed.
                        */
                     	 mashupRefId: 'getSFSShipmentAlreadyPrintedCount',
                         mashupId: 'store.views.shipment.backroom-pick.getSFSShipmentPrintPickTicketCount'

                    }

  		              
			],


			ui:{
				/**
	  			*@property {Boolean} hasPermissionForBOPISShipment - True if user has permission for Picking BOPIS shipments, false otherwise.
	  			 */
				hasPermissionForBOPISShipment:iscResourcePermission.hasPermission("WSC000057"),
				/**
	  			*@property {Boolean} hasPermissionForSFSShipment - True if user has permission for Picking SFS shipments, false otherwise.
	  			 */
				hasPermissionForSFSShipment:iscResourcePermission.hasPermission("WSC000058"),
				/**
	  			*@property {Number} BOPISShipmentsToBePrinted - count of BOPIS shipments which are not printed
	  			 */
				BOPISShipmentsToBePrinted:0,
				/**
	  			*@property {Number} BOPISShipmentsAlreadyPrinted - count of BOPIS shipments which are printed
	  			 */
				BOPISShipmentsAlreadyPrinted:0,
				/**
	  			*@property {Number} totalBOPISShipmentsToBePicked - total count of BOPIS shipments ready for picking
	  			 */
				totalBOPISShipmentsToBePicked:0,
				/**
	  			*@property {Number} SFSShipmentsToBePrinted - count of SFS shipments which are not printed
	  			 */
				SFSShipmentsToBePrinted:0,
				/**
	  			*@property {Number} SFSShipmentsAlreadyPrinted - count of SFS shipments which are printed
	  			 */
				SFSShipmentsAlreadyPrinted:0,
				/**
	  			*@property {Number} totalSFSShipmentsToBePicked - total count of SFS shipments ready for picking
	  			 */
				totalSFSShipmentsToBePicked:0,
				/**
	  			*@property {Boolean} printBOPISShipments - True if Print BOPIS shipment checkbox is selected
	  			 */
				printBOPISShipments:false,
				/**
	  			*@property {Boolean} printSFSShipments - True if Print SFS shipment checkbox is selected
	  			 */
				printSFSShipments:false

			},
			
			initialize : function(){
				
				 var mashupArray = [];
				 var getShipmentToBePrintedInput = {Shipment:{PickTicketPrinted:'Y'}};
        		 var getShipmentAlreadyPrintedInput = {Shipment:{PickTicketPrinted:'N'}};
				 
	        	 if(this.ui.hasPermissionForBOPISShipment) {
	        		 
	        		 mashupArray.push(iscMashup.getMashupRefObj(this,'getBOPISShipmentToBePrintedCount',getShipmentToBePrintedInput));
	        		 mashupArray.push(iscMashup.getMashupRefObj(this,'getBOPISShipmentAlreadyPrintedCount',getShipmentAlreadyPrintedInput));
	        	 }
	        	 if(this.ui.hasPermissionForSFSShipment) {
	        		 
	        		 mashupArray.push(iscMashup.getMashupRefObj(this,'getSFSShipmentToBePrintedCount',getShipmentToBePrintedInput));
	        		 mashupArray.push(iscMashup.getMashupRefObj(this,'getSFSShipmentAlreadyPrintedCount',getShipmentAlreadyPrintedInput));
	        	 }
	        	 
	    		 iscMashup.callMashups(this,mashupArray,{}).then(this.handleInitApiCalls.bind(this),angular.noop);
				
				
			},
			
			  /**
	         *@description This method is a callback handler for mashups invoked in initialize method.
	         * This method initialize the ui objects with appropriate BOPISShipment count and SFSShipment count to be printed and already printed.
	         * 
	         *@param {Object} response - mashup output JSON object
	         */
			handleInitApiCalls:function(response) {
				
				var apiOutput = "";
				
				if(this.ui.hasPermissionForBOPISShipment) {
	        		 
					apiOutput =  iscMashup.getMashupOutput(response,"getBOPISShipmentToBePrintedCount");
					this.ui.BOPISShipmentsToBePrinted = Number(apiOutput.Shipments.TotalNumberOfRecords);
					apiOutput =  iscMashup.getMashupOutput(response,"getBOPISShipmentAlreadyPrintedCount");
	        		this.ui.BOPISShipmentsAlreadyPrinted = Number(apiOutput.Shipments.TotalNumberOfRecords);
	        		
	        		this.ui.totalBOPISShipmentsToBePicked = this.ui.BOPISShipmentsAlreadyPrinted + this.ui.BOPISShipmentsToBePrinted;
	
	        	 }
				
	        	if(this.ui.hasPermissionForSFSShipment) {
	        		 
		        	apiOutput =  iscMashup.getMashupOutput(response,"getSFSShipmentToBePrintedCount");
					this.ui.SFSShipmentsToBePrinted = Number(apiOutput.Shipments.TotalNumberOfRecords);
					apiOutput =  iscMashup.getMashupOutput(response,"getSFSShipmentAlreadyPrintedCount");
					this.ui.SFSShipmentsAlreadyPrinted = Number(apiOutput.Shipments.TotalNumberOfRecords);
        			
					this.ui.totalSFSShipmentsToBePicked = this.ui.SFSShipmentsAlreadyPrinted + this.ui.SFSShipmentsToBePrinted;
	        		 
	        	}
				
			},
			
			/**
			 *@description OnClick handler of "Cancel" button, closes the modal popup.
			 */
			uiClose : function () {
				$uibModalInstance.dismiss({data : 'CANCEL'});
			},
			
			/**
			 *@description OnClick handler of "Apply" button, propagates the data back to invoking screen.
			 */
			uiSubmit : function (action) {
				
				var action = "PRINT_ALL";
				var isPrintingLargeShipments = false;
				
				if(this.ui.printBOPISShipments && !this.ui.printSFSShipments) {
					action = "PRINT_BOPIS";
					isPrintingLargeShipments = Number(this.ui.totalBOPISShipmentsToBePicked) > 100;
				} else if(this.ui.printSFSShipments && !this.ui.printBOPISShipments) {
					action = "PRINT_SFS"
					isPrintingLargeShipments = Number(this.ui.totalSFSShipmentsToBePicked) > 100;
				}
				
				
				if(action == "PRINT_ALL") {
					isPrintingLargeShipments = (Number(this.ui.totalBOPISShipmentsToBePicked) + Number(this.ui.totalSFSShipmentsToBePicked)) > 100;
				}
				
				var modalResponse = {};
				modalResponse = {
					data : {
						shipmentTypeToPrint : action,
						isPrintingLargeShipments:isPrintingLargeShipments
							}
						};
				
				
				$uibModalInstance.close(modalResponse);
			}
			
			
			
			
  	});
		
		
		
	}
]);

