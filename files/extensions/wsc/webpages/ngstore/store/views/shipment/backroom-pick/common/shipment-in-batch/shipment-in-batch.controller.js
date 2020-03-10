/*******************************************************************************
 * IBM Confidential
 * OCO Source Materials
 * IBM Sterling Order Management Store (5725-D10), IBM Order Management (5737-D18), IBM Store Engagement (5737D58)
 * (C) Copyright IBM Corp. 2017 All Rights Reserved.
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 ******************************************************************************/

/**
 *@iscdoc viewmodal
 *@viewname store.views.shipment.backroom-pick.common.shipment-in-batch.shipment-in-batch
 *@package store.views.shipment.backroom-pick.common.shipment-in-batch
 *@class shipment-in-batch
 */
angular.module('store').controller('store.views.shipment.backroom-pick.common.shipment-in-batch.shipment-in-batch',
  ['$scope','$rootScope','$uibModalInstance','iscScreen','iscWizard','modalInput','$filter', 'iscMashup','iscAppInfo','iscResourcePermission','iscI18n','iscModal',
	function($scope,$rootScope,$uibModalInstance,iscScreen,iscWizard,modalInput,$filter,iscMashup,iscAppInfo,iscResourcePermission,iscI18n,iscModal) {
		
		
		iscScreen.initializeModalScreen($scope,{

  		model:{
  			 /**
	           *@description This model contains shipment details model.
	           */
         "shipmentDetails":{}
		
  		},

  		mashupRefs : [

			],


			ui:{

			},
			
			initialize : function(){
				this.model.shipmentDetails = modalInput.shipmentDetails;
			},
			
			uiGetDueInImageUrl:function(slaImageRelativePath) {
				
				var slaImageFullURL = "";
	  			if(slaImageRelativePath) {
					slaImageFullURL =  window.location.protocol+"//"+window.location.host+iscAppInfo.getApplicationContext()+"/"+slaImageRelativePath;
	  			}
	  			
	  			return slaImageFullURL;
	  			
			},
			
			/**
			 *@description OnClick handler of "Cancel" button, closes the modal popup.
			 */
			uiClose : function () {
				$uibModalInstance.dismiss({data:'CANCEL'});
			},

			
			
			/**
			 *@description OnClick handler of "Apply" button, propagates the data back to invoking screen.
			 */
			uiSubmit : function (action) {
				$uibModalInstance.close({data:action});
			}
			
			
			
			
  	});
		
		
		
	}
]);

