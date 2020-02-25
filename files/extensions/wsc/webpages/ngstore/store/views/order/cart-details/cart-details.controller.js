/*******************************************************************************
 * IBM Confidential
 * OCO Source Materials
 * IBM Sterling Order Management Store (5725-D10), IBM Order Management (5737-D18), IBM Store Engagement (5737D58)
 * (C) Copyright IBM Corp.  2015, 2017 All Rights Reserved.
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 ******************************************************************************/


/**
 *@iscdoc viewinfo
 *@viewname store.views.order.cart-details.cart-details
 *@package store.views.order
 *@class cart-details
 *@description Displays the cart details screen in order capture flow.
 *
 */

   angular.module('store').controller('store.views.order.cart-details.cart-details',
		  ['$scope','$rootScope','iscScreen','iscWizard','$timeout','$filter','$locale','iscMashup','iscResourcePermission','iscModal','iscI18n','iscAppContext','iscOrder',
			function($scope,$rootScope,iscScreen,iscWizard,$timeout,$filter,$locale,iscMashup,iscResourcePermission,iscModal,iscI18n,iscAppContext,iscOrder) {				
				iscWizard.initializeWizardPage($scope,{
					  
				      model:{
				    	  /**
				           *@iscdoc model
				  		   *@viewname store.views.order.cart-details.cart-details
				           *@name orderLineList
				           *@description This model contains the getCompleteOrderLineList api output.
				           */
				    	  "orderLineList" : {} ,
				    	  /**
				           *@iscdoc model
				  		   *@viewname store.views.order.cart-details.cart-details
				           *@name orderModel
				           *@description This is order capture wizard model containing the order information.
				           */
			    		  "orderModel" : {},
			    		  /**
				           *@iscdoc model
				  		   *@viewname store.views.order.cart-details.cart-details
				           *@name giftModel
				           *@description This model contains the order level gift information.
				           */
			    		  "giftModel" : {},
			    		  /**
				           *@iscdoc model
				  		   *@viewname store.views.order.cart-details.cart-details
				           *@name couponInput
				           *@description This model contains the translateBarcode api input used when item is scanned.
				           */
			    		  "couponInput":{
			    			  "couponID" :""
			    		  },
			    		  /**
				           *@iscdoc model
				  		   *@viewname store.views.order.cart-details.cart-details
				           *@name scanItemInput
				           *@description This model contains the translateBarcode api input used when coupon is scanned.
				           */
			    		  "scanItemInput":{
			    			  "itemID":""
			    		  },
			    		  /**
				           *@iscdoc model
				  		   *@viewname store.views.order.cart-details.cart-details
				           *@name getRuleDetails_ChargeShipLines
				           *@description This model contains the getRuleDetails api output for CHARGE_SHIP_LINES_AT_STORE.
				           */
						"getRuleDetails_ChargeShipLines":{},
						
						"customerDetails":{}
						
				      },
                        
				      /**
				       *MashupRefs
				       *array containing the list of mashups referred in this controller
				       */
				      
				  		mashupRefs : [
					        
					        
				  			{
				  				
				  				/**
							        *@iscdoc mashup
									*@viewname store.views.order.cart-details.cart-details
									*@mashupid viewcart_getCompleteOrderLineList
									*@mashuprefid viewcart_getCompleteOrderLineList
									*@modelname orderLineList
									*@description This mashup is used to get the list of OrderLines.
									*/
				  				 mashupRefId: 'viewcart_getCompleteOrderLineList',
						         mashupId: 'viewcart_getCompleteOrderLineList',
						         modelName: "orderLineList"
						       
				  			},
				  			 
				  			{
				  				/**
							        *@iscdoc mashup
									*@viewname store.views.order.cart-details.cart-details
									*@mashupid viewcart_getCompleteOrderDetails
									*@mashuprefid viewcart_getCompleteOrderDetails
									*@modelname getCompleteOrderDetails
									*@description This mashup is used to get order details.
									*/
				  				
				  				 mashupRefId: 'viewcart_getCompleteOrderDetails',
						         mashupId: 'viewcart_getCompleteOrderDetails',
						         modelName: 'getCompleteOrderDetails'
				  			},
				  			{
				  			 /**
						        *@iscdoc mashup
								*@viewname store.views.order.cart-details.cart-details
								*@mashupid wsc_paymentCapture_getRuleDetails_ChargeShipLines
								*@mashuprefid getRuleDetails_ChargeShipLines
								*@modelname getRuleDetails_ChargeShipLines
								*@description This mashup is used to get rule details of the Rule 'CHARGE_SHIP_LINES_AT_STORE' for the Store's Enterprise.
								*/
					            	mashupRefId: 'getRuleDetails_ChargeShipLines',
					            	mashupId: 'wsc_paymentCapture_getRuleDetails_ChargeShipLines',
					            	modelName : 'getRuleDetails_ChargeShipLines'
					        },
				  			 
				  			{  
				  				/**
							        *@iscdoc mashup
									*@viewname store.views.order.cart-details.cart-details
									*@mashupid viewcart_ovp_modifyFulfillmentOptions
									*@mashuprefid viewcart_ovp_modifyFulfillmentOptions
									*@description This mashup is used to call modifyFulfillmentOptions when price is overridden for a product.
									*/
				  				mashupRefId: 'viewcart_ovp_modifyFulfillmentOptions',
				  				mashupId: 'viewcart_ovp_modifyFulfillmentOptions'
				  				
				  			},
				  			
				  			{   /**
						        *@iscdoc mashup
								*@viewname store.views.order.cart-details.cart-details
								*@mashupid viewcart_customer_modifyFulfillmentOptions
								*@mashuprefid viewcart_customer_modifyFulfillmentOptions
								*@description This mashup is used to call modifyFulfillmentOptions when customer is identified or order address is changed in customer panel.
								*/
				  				mashupRefId:'viewcart_customer_modifyFulfillmentOptions',
				  				mashupId:'viewcart_customer_modifyFulfillmentOptions'
				  			},
				  			
				  			{
				  				/**
							        *@iscdoc mashup
									*@viewname store.views.order.cart-details.cart-details
									*@mashupid viewcart_getItemAvailabilityForStore
									*@mashuprefid viewcart_getItemAvailabilityForStore
									*@description This mashup is used to check availability of the product when quantity is edited or ShipTo address of order line is changed.
									*/
				  				mashupRefId:'viewcart_getItemAvailabilityForStore',
				  				mashupId:'viewcart_getItemAvailabilityForStore'
				  			},
				  			
				  			{
				  				/**
							        *@iscdoc mashup
									*@viewname store.views.order.cart-details.cart-details
									*@mashupid viewcart_modifyFulfillmentOptions
									*@mashuprefid viewcart_modifyFulfillmentOptions
									*@description This mashup is used to call modifyFulfillmentOptions api when coupon is removed,store is selected for a pickup line and delivery method is changed.
									*/
				  				mashupRefId:'viewcart_modifyFulfillmentOptions',
				  				mashupId:'viewcart_modifyFulfillmentOptions'
				  				
				  			},
				  			
				  			{
				  				/**
							        *@iscdoc mashup
									*@viewname store.views.order.cart-details.cart-details
									*@mashupid viewcart_translateBarcodeForCoupon
									*@mashuprefid viewcart_translateBarcodeForCoupon
									*@description This mashup is used to call translateBarcode api when coupon is scanned.
									*/
				  				mashupRefId:'viewcart_translateBarcodeForCoupon',
				  				mashupId:'viewcart_translateBarcodeForCoupon'	
				  			},
				  			
				  			{   
				  				/**
							        *@iscdoc mashup
									*@viewname store.views.order.cart-details.cart-details
									*@mashupid viewcart_changeOrderForGift
									*@mashuprefid viewcart_changeOrderForGift
									*@description This mashup is used to call changeOrder api to add/edit/delete gift information either at order/orderline level.
									*/
				  				mashupRefId:'viewcart_changeOrderForGift',
				  				mashupId:'viewcart_changeOrderForGift'
				  				
				  			},
				  			
				  			{
				  				/**
							        *@iscdoc mashup
									*@viewname store.views.order.cart-details.cart-details
									*@mashupid vc_modifyFulfillmentOptionsForDeletion
									*@mashuprefid vc_modifyFulfillmentOptionsForDeletion
									*@description This mashup is used to call modifyFulfillmentOptions api to delete a order line.
									*/
				  				mashupRefId:'vc_modifyFulfillmentOptionsForDeletion',
				  				mashupId:'vc_modifyFulfillmentOptionsForDeletion'
				  				
				  			},
				  			
				  			{
				  				/**
							        *@iscdoc mashup
									*@viewname store.views.order.cart-details.cart-details
									*@mashupid vc_getCompleteOrderLineList
									*@mashuprefid vc_getCompleteOrderLineList
									*@description This mashup is used to call getCompleteOrderLineList to get total number of order lines to validate whether the order needs to be reserved.
									*/
				  				mashupRefId:'vc_getCompleteOrderLineList',
				  				mashupId:'vc_getCompleteOrderLineList' 
				  				
				  			},
				  			
				  			{
				  				/**
							        *@iscdoc mashup
									*@viewname store.views.order.cart-details.cart-details
									*@mashupid vc_updateSettledQtyOnAllLines
									*@mashuprefid vc_updateSettledQtyOnAllLines
									*@description This mashup is used to call changeOrder to update SettledQuantity on non-settled lines in case of orders in mixed mode, which is having settled and non-settled lines.
									*/
				  				mashupRefId:'vc_updateSettledQtyOnAllLines',
				  				mashupId:'vc_updateSettledQtyOnAllLines' 
				  				
				  			},
				  			
				            {

				  				/**
							        *@iscdoc mashup
									*@viewname store.views.order.cart-details.cart-details
									*@mashupid vc_getCompleteItemList
									*@mashuprefid vc_getCompleteItemList
									*@description This mashup is used to call getCompleteItemList when a carry product is scanned.
									*/
				            	mashupRefId: 'vc_getCompleteItemList',
				            	mashupId: 'vc_getCompleteItemList'
				            },
				            
				            {
				            mashupRefId: 'vc_getOrderFulfillmentDetails',
				            	mashupId: 'vc_getOrderFulfillmentDetails',
				            	modelName : 'getOrderFulfillmentDetails'
				            }
				          

				  		],
				  		
			            ui:{
			            	/**
			    			 *@iscdoc uiattr
			    			 *@viewname store.views.order.cart-details.cart-details
			    			 *@property {Boolean}  showCouponScanField - true if coupon scan fields needs to be shown, false otherwise.
			    			 */
			            	showCouponScanField: false,
			            	/**
			    			 *@iscdoc uiattr
			    			 *@viewname store.views.order.cart-details.cart-details
			    			 *@property {String} isOrderGift - Y if the order is Gift.
			    			 */
			            	isOrderGift:'',
			            	/**
			    			 *@iscdoc uiattr
			    			 *@viewname store.views.order.cart-details.cart-details
			    			 *@property {Boolean} oLineQtyEditResourcePermission - true/false based on resource permission for editing order line quantity.
			    			 */
			            	oLineQtyEditResourcePermission :false,
			            	/**
			    			 *@iscdoc uiattr
			    			 *@viewname store.views.order.cart-details.cart-details
			    			 *@property {Number} oLineShownCount - pageSize of the Order line list for continuous scrolling.
			    			 */
			            	oLineShownCount: 5,
			            	/**
			    			 *@iscdoc uiattr
			    			 *@viewname store.views.order.cart-details.cart-details
			    			 *@property {Boolean} applyCouponResourcePermission - true/false based on resource permission for applying coupon to order.
			    			 */
			            	applyCouponResourcePermission :false,
							/**
							 *@iscdoc uiattr
			    			 *@viewname store.views.order.cart-details.cart-details
			    			 *@property {Integer} activeTabIndex - index of the tab that is active in mobile
			    			 */
						activeTabIndex: -1,
							
						checkAvailability:true,
						
						availabilityInfoLoading:true
			            	
			            },
			            
				            /**
							 *@iscdoc viewinit
							 *@viewname store.views.order.cart-details.cart-details
							 *@method initialize
							 *@description Initializes the cart details screen with getCompleteOrderDetails and getCompleteOrderLineList api calls.
							 */
				  		    initialize : function(){
				  		    $rootScope.$showBackButtonForStates = true;
				  			this.model.orderModel = iscWizard.getWizardModel("orderModel");
				  			this.model.giftModel = iscWizard.getWizardModel("giftOptionsForOrderModel");
				  			if(!iscCore.isVoid(this.model.giftModel)){
				  				this.ui.isOrderGift = this.model.giftModel.giftFlag;
				  			}
				  			
				  			if(iscResourcePermission.hasPermission("WSC000032")){
				  				this.ui.oLineQtyEditResourcePermission=true;
							}	
				  			
                            this.model.customerDetails = iscWizard.getWizardModel("customerModel");    
                            
				        	var mashupArray = [];
				        	mashupArray.push(iscMashup.getMashupRefObj(this,'viewcart_getCompleteOrderLineList',{"OrderLine" : { "OrderHeaderKey" : this.model.orderModel.Order.OrderHeaderKey}}));
				        	mashupArray.push(iscMashup.getMashupRefObj(this,'viewcart_getCompleteOrderDetails', {"Order" : { "OrderHeaderKey" : this.model.orderModel.Order.OrderHeaderKey}}));
				        	mashupArray.push(iscMashup.getMashupRefObj(this,'getRuleDetails_ChargeShipLines', {}));
				        /*	if(this.ui.checkAvailability){
				        	 	mashupArray.push(iscMashup.getMashupRefObj(this,'vc_getOrderFulfillmentDetails',  {"Order" : { "OrderHeaderKey" : this.model.orderModel.Order.OrderHeaderKey}})); 		
				        	}*/
				       
				        	//mashupArray.push(iscMashup.getMashupRefObj(this,'getNoteList', {"Note" : { "TableKey" : this.model.orderModel.Order.OrderHeaderKey}}));
				        	
				        	iscMashup.callMashups(this,mashupArray,{}).then(this.handleInitApiCalls.bind(this),angular.noop);
				  			},
				  		
				  			 /**
							 *@iscdoc method
							 *@viewname store.views.order.cart-details.cart-details
							 *@methodname handleInitApiCalls
							 *@description callback handler for init api calls getCompleteOrderDetails and getCompleteOrderLineList.
							 *@param {Object} data - Controller data of init api calls.
							 */	
					  		  handleInitApiCalls : function (data){
					  			
					  			var apiOutput = iscMashup.getMashupOutput(data,"viewcart_getCompleteOrderLineList");
					  			var orderLines = []; 
					 	        orderLines = apiOutput.OrderLineList.OrderLine;
					 	        
					 			if(!iscCore.isVoid(orderLines)){
					 				
					 				for(var k= 0; k<orderLines.length ; k++){
					 					orderLines[k].OrderLineTranQuantity.OrderedQty = $filter('number')(orderLines[k].OrderLineTranQuantity.OrderedQty);    
					 					orderLines[k].OrderedQty = $filter('number')(orderLines[k].OrderedQty);
									}
					 			}
					 			
					 			this.model.orderLineList = apiOutput;
					 			
					 			 $timeout(function(){
					 				iscMashup.callMashup(this,"vc_getOrderFulfillmentDetails",{"Order" : { "OrderHeaderKey" : this.model.orderModel.Order.OrderHeaderKey}},{showMask:false}).then(this.handleAvailabilityOfCart.bind(this),angular.noop);
				                    }.bind(this),0);
					  		  },
					  		  
					  		  
					  		handleAvailabilityOfCart:function(data){
					  			this.ui.availabilityInfoLoading = false;
					  			var apiOutput = iscMashup.getMashupOutput(data,"vc_getOrderFulfillmentDetails");
					  			var orderLines = []; 
					 	        orderLines = apiOutput.Order.OrderLines.OrderLine;
					 	        var unAvailableOLines = [];
					 	        
					 			if(!iscCore.isVoid(orderLines)){
					 				
					 				for(var k= 0; k<orderLines.length ; k++){
					 					if(orderLines[k].HasAnyUnavailableQty == 'Y' && orderLines[k].DeliveryMethod !='CARRY'){
					 						unAvailableOLines.push(orderLines[k].OrderLineKey);
					 					}
									}
					 			}
					 			//unAvailableOLines.push('20171122122656244318');
					 			if(unAvailableOLines.length > 0){
					 				this.updateOrderLineListWithAvailabilityInfo(unAvailableOLines);	
					 			}
					 			
					  		}, 
					  		
					  		
					  		updateOrderLineListWithAvailabilityInfo:function(unAvailableOLines){
					  			
					  			var orderLines = []; 
						        orderLines = this.model.orderLineList.OrderLineList.OrderLine;
				     		    for(var j=0; j<unAvailableOLines.length; j++){
									for(var k= 0; k<orderLines.length ; k++){
										if(orderLines[k].OrderLineKey === unAvailableOLines[j]){
											orderLines[k].HasAnyUnavailableQty='Y';
											break;
										}
									}
				     	 	   }
					  		},
				  		  
				  			/**
							 *@iscdoc uimethod
							 *@viewname store.views.order.cart-details.cart-details
							 *@methodname uiOnQuantityFieldFocus
							 *@description This is a onFocus event handler of quantity field.
							 *@param {Object} qtyField - quantity field as JSON object. 
							 *@param {Object} orderlineModel - orderLine data as JSON object. 
							 */
				  			uiOnQuantityFieldFocus :function (qtyField,orderLineModel) {
				  				if($scope.cartDetails.$valid)
				  				var formats = $locale.NUMBER_FORMATS;
					  			var oldQuantity = orderLineModel.OrderLineTranQuantity.OrderedQty;
					  			if(!iscCore.isVoid(oldQuantity)){
					  				oldQuantity=oldQuantity.replace(formats.GROUP_SEP, '');		
					  			}
					  			if(orderLineModel.showQtyUpdate !=='Y'){
					  				qtyField.oldQty = oldQuantity;
					  			}
				  				
				  		    },
				  			
				  		  /**
							 *@iscdoc uimethod
							 *@viewname store.views.order.cart-details.cart-details
							 *@methodname uiCancel
							 *@description This method handles on click of cancel button in cart details screen.
							 */
			              uiCancel : function(){
			            	iscModal.showConfirmationMessage(iscI18n.translate('order.WarningMessage_Cancel')).then(
								function(callBackAction){
									if(callBackAction === 'YES'){
										iscWizard.closeWizard();
									}
			       				},
			  					function(callBackAction){
			       				});
			              },
			            
			              /**
							 *@iscdoc uimethod
							 *@viewname store.views.order.cart-details.cart-details
							 *@methodname uiGetItemDetailsForScan
							 *@description This method handles scanning of a carry item by calling getCompleteItemList api to get product details and subsequently modifyFulfillmentOptions api to add the order line.
							 *@param {String} barCodeData - BarCode data of the scanned product. 
							 */ 
			            uiGetItemDetailsForScan: function(barCodeData){
			            	if(!iscCore.isVoid(barCodeData)){
			            		var lotNumber = undefined;
			            		if(barCodeData.indexOf(":") > -1) {
			            			lotNumber = barCodeData.split(":")[1];
			            			barCodeData = barCodeData.split(":")[0];
			            		}
			            		var getCompleteItemDetailsApiInput = {'Item':{ 'BarCode' :{'BarCodeData': barCodeData}}};
			                	getCompleteItemDetailsApiInput.Item.OrderHeaderKey = this.model.getCompleteOrderDetails.Order.OrderHeaderKey;
			                	iscMashup.callMashup(this,"vc_getCompleteItemList",getCompleteItemDetailsApiInput,{}).then(this.handleItemScan.bind(this, lotNumber),angular.noop);
			            	}
			            	else{
			            		iscModal.showErrorMessage(iscI18n.translate('viewcartdetails.MSG_InvalidBarCodeData'));
							    }
			            },
			            
			            /**
				  		 *@iscdoc uimethod
						 *@viewname store.views.return.add-exch-item.add-exchange-item
				  		 *@methodname uiOpenProductSearch
				  		 *@description Onclick handler function for search icon in scan field.Opens dirty popup if screen is dirty
				  		 * and based on action selected opens search product wizard page or stays in the same screen.
						 *@param {String} searchTxt - Input to search for matching products.
				  		 */
		            uiOpenProductSearch: function(searchTxt){
		            	var that = this;
		            	var openProductSearch = true;
		            	if(this.ui.isScreenDirty){
		            		iscModal.showConfirmationMessage(iscI18n.translate('addItems.MSG_Screen_dirty')).then(function(action){
		            			if(iscCore.isBooleanTrue(action)){
		            				// action is yes. check search text and if valid, open search page.
		            				if(!iscCore.isVoid(searchTxt)){
					            		var pageInput = {
						            			input: searchTxt
						            	};
						            	iscWizard.gotoCustomPage('productSearch',pageInput,{removeCurrent: false,wizardPageCssClass:"fullscreen-modal"});
					            	}
					            	else{
					            		iscModal.showErrorMessage(iscI18n.translate('addItems.MSG_ProductSearchError'));
					            	}
		            			}
		            			else{
		            				// action is no. so clear search text.
		            				that.model.searchInput = '';
		            			}
		            		});
		            	}
		            	else{
		            		if(!iscCore.isVoid(searchTxt)){
			            		var pageInput = {
				            			input: searchTxt,
				            			searchContext : 'scanInStore'
				            	};
				            	//iscWizard.gotoCustomPage('productSearch',pageInput,{wizardPageCssClass:"fullscreen-modal"});
				            	iscWizard.gotoCustomPage('addItems',pageInput,{removeCurrent: false,wizardPageCssClass:"fullscreen-modal"});
			            	}
			            	else{
			            		iscModal.showErrorMessage(iscI18n.translate('addItems.MSG_ProductSearchError'));
			            	}
		            	}
		            },
			            
			            /**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname handleItemScan
						 *@description callback handler for getCompleteItemList api call.
						 *@param {Object} data - Controller data of getCompleteItemList api call.
						 */	
			            handleItemScan: function(data, lotNumber){
			            	
			            	var apiOutput = iscMashup.getMashupOutput(data,"vc_getCompleteItemList");
			    			if(!iscCore.isVoid(apiOutput) && !iscCore.isVoid(apiOutput.Order.OrderLines)){
			    				if(!(this.model.orderLineList.OrderLineList.TotalNumberOfRecords =='0')){
			    					iscOrder.updateOrderLines(apiOutput,this.model.orderLineList);
			    				}
			    				else{
			    					this.model.orderLineList.OrderLineList.OrderLine = [];
			    					this.model.orderLineList.OrderLineList.OrderLine = apiOutput.Order.OrderLines.OrderLine;
			    					this.model.orderLineList.OrderLineList.TotalNumberOfRecords =  apiOutput.Order.OrderLines.TotalNumberOfRecords;
			    				}
			 			    	this.model.getCompleteOrderDetails =iscOrder.updateOrderHeaderDetails(apiOutput,this.model.getCompleteOrderDetails);
			 			    	this.ui.oLineShownCount = this.model.orderLineList.OrderLineList.OrderLine.length;
			 			    	$timeout(function(){
			 		            	var objDiv = document.getElementById("orderLinesHolder");
			 		            	objDiv.scrollTop = objDiv.scrollHeight;
			 		            }, 0);
			    			}
			    			else {
			    				var pageInput = {
				            			input: this.model.scanItemInput.itemID,
				            			searchContext : 'scanInStore'
				            	};
				            	iscWizard.gotoCustomPage('addItems',pageInput,{removeCurrent: false,wizardPageCssClass:"fullscreen-modal"});
				            	
			    			}
			                this.model.scanItemInput.itemID ='';
			                $scope.cartDetails.$setPristine();
			            },
			            
			            /**
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uihandleScreenDirty
						 *@description This method is called on click of Continue to payment button when the screen is dirty.
						 */ 	
			            uihandleScreenDirty: function(){
			            	iscModal.showErrorMessage(iscI18n.translate('globals.MSG_SCREEN_ERRORS')); 
			            }, 
			            
			            /**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname handleWizardBack
						 *@description This method handles the on click of back  in wizard.
						 */ 	
			            
						handleWizardBack:function(){
							var isFirstPage = iscWizard.isFirstPage();
							var isDirty = $scope.cartDetails.$dirty;
							if(isDirty){
								var confirmationMsg = null;
								if(isFirstPage){
									confirmationMsg = iscI18n.translate('order.WarningMessage_Cancel');
									iscModal.showConfirmationMessage(confirmationMsg).then(
											function(callBackAction){
												if(callBackAction === 'YES'){
													iscWizard.closeWizard();
												}
						       				});
								}
								else{
									iscModal.showConfirmationMessage(iscI18n.translate('addItems.MSG_Screen_dirty')).then(function(action){
			            			if(iscCore.isBooleanTrue(action)){
			            				iscWizard.gotoPreviousPage(true);
			            			}
			            		});
								}
								return true;
							}else if(isFirstPage){
								confirmationMsg = iscI18n.translate('order.WarningMessage_Cancel');
								iscModal.showConfirmationMessage(confirmationMsg).then(
										function(callBackAction){
											if(callBackAction === 'YES'){
												iscWizard.closeWizard();
											}
					       				});
								return true;
							}
							else{
								return false;
							}
						},
						
						/**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname handleWizardExit
						 *@description This method handles the on click of cancel button in wizard.
						 */ 
						
						handleWizardExit:function(){
							var confirmationMsg = iscI18n.translate('order.WarningMessage_Cancel');
							
							iscModal.showConfirmationMessage(confirmationMsg).then(
								function(callBackAction){
										//
										if(callBackAction === 'YES'){
												iscWizard.exitWizard();
										}
								},
								function(callBackAction){
										//      Do Nothing

								});
							return true;						
						},
						
						/* 
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiApplyCoupon
						 *@description This method is used to show coupon scan field on click of Apply coupons button and to call translateBarcode api when coupon is scanned.
						 *@param {String} couponID - BarCode data of the scanned coupon. 
						  
						
						 uiApplyCoupon :function(couponID){
			            	if(iscCore.isVoid(couponID)){
			                	this.ui.showCouponScanField = true;
			            	}else{
			            		iscMashup.callMashup(this,"viewcart_translateBarcodeForCoupon",{"BarCode" : { "BarCodeData" : couponID ,"OrderHeaderKey" : this.model.getCompleteOrderDetails.Order.OrderHeaderKey}},{}).then(this.handleApplyCoupon.bind(this),angular.noop);
			            	}
			            },
			            
			            
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiApplyCoupon
						 *@description This method calls modifyFulfillmentOptions api to remove a coupon.
						 *@param {Object} promotion - promotion that needs to removed. 
						  
			            
			            uiRemoveCoupon : function(promotion){
			            	var apiInput ={'Order':{'OrderHeaderKey':this.model.getCompleteOrderDetails.Order.OrderHeaderKey,'Promotions':[{'Promotion':{'Action':'REMOVE','PromotionId' : promotion.PromotionId}}]}}; 
			        		iscMashup.callMashup(this,"viewcart_modifyFulfillmentOptions",apiInput,{}).then(this.handleRemoveCoupon.bind(this),angular.noop);
			            },
			            
			            
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname handleRemoveCoupon
						 *@description This method is the callback handler for  modifyFulfillmentOptions api to remove a coupon.
						 *@param {Object} promotion - promotion that needs to removed. 
						  
			            
			            handleRemoveCoupon : function(data){
			            	this.handleModifyFulfillmentOptionsOutput(data);
							var alertMessage = iscI18n.translate("viewcartdetails.aria_deleteCouponSuccessMessage");
							iscModal.showA11YAlertMessage(alertMessage);
			            },*/
			            
			            
			            /**
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiOpenConfirmationForGiftDelete
						 *@description Shows confirmation dialog when Remove Gift button is clicked.On Conformation,removeGiftOptionsForOrder method is called.
						 */ 
			            
			            uiOpenConfirmationForGiftDelete : function(){
			            	var that = this;
			            	iscModal.showConfirmationMessage("viewcartdetails.LABEL_GiftDeleteConfirmation").then(function(action){
								that.removeGiftOptionsForOrder(action);
							});
			            },
			            
			            /**
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiConfirmDeletion
						 *@description Shows confirmation dialog before deleting order line.On Conformation,deleteOrderLine method is called.
						 *@param {Object} orderlineModel - orderLine data as JSON object. 
						 */ 
			            
			            uiConfirmDeletion : function(orderLineModel){
			            	
			            	var that = this;
			            	iscModal.showConfirmationMessage("viewcartdetails.LABEL_OLineDeleteConfirmation").then(function(action){
								that.deleteOrderLine(action,orderLineModel);
							});
			            },
			            
			            /**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname deleteOrderLine
						 *@description Deletes order line by calling modifyFulfillmentOptions api.
						 *@param {String} action - action is the response of a confirmation dialog to delete order line. 
						 *@param {Object} orderlineModel - orderLine data as JSON object. 
						 */ 
			            
			            deleteOrderLine : function(action,orderLineModel){
			            	if(action === 'YES'){
				            	var apiInput ={'Order':{'OrderHeaderKey':this.model.getCompleteOrderDetails.Order.OrderHeaderKey,'OrderLines':[{'OrderLine':{'Action':'REMOVE','OrderLineKey' : orderLineModel.OrderLineKey}}]}}; 
				            	iscMashup.callMashup(this,"vc_modifyFulfillmentOptionsForDeletion",apiInput,{}).then(this.updateOrderLineList.bind(this),angular.noop);
			            	}
			            },
			            
			            /**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname updateOrderLineList
						 *@description Call back handler of modifyFulfillmentOptions api when order line is deleted.It Updates Order Header and Order line list data.
						 *@param {Object} data - Controller data of modifyFulfillmentOptions api call.
						 */ 
			            
			            updateOrderLineList : function(data){
			            	var apiOutput = iscMashup.getMashupOutput(data,"vc_modifyFulfillmentOptionsForDeletion");
			            	iscOrder.updateOrderLines(apiOutput,this.model.orderLineList);
						    this.model.getCompleteOrderDetails =iscOrder.updateOrderHeaderDetails(apiOutput,this.model.getCompleteOrderDetails);
						    var alertMessage = iscI18n.translate("viewcartdetails.aria_deleteOrderLineSuccessMessage");
							iscModal.showA11YAlertMessage(alertMessage);
							
							if(this.model.orderLineList.OrderLineList.TotalNumberOfRecords == 0){
								this.model.giftModel = {};
			            		iscWizard.setWizardModel("giftOptionsForOrderModel",{});
			            		this.ui.isOrderGift = '';
							}
							
			            },
			            
			            /**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname removeGiftOptionsForOrder
						 *@description Removes Gift options for Order by calling changeOrder api.
						 *@param {String} action - action is the response of a confirmation dialog to remove gift options for order. 
						 */ 
			            
			            removeGiftOptionsForOrder : function(action){
			            	if(action === 'YES'){
			            		var orderLineList = this.model.orderLineList.OrderLineList.OrderLine;
			            		/* update the wizard model data */
			            		this.model.giftModel = {};
			            		iscWizard.setWizardModel("giftOptionsForOrderModel",{});
			            		this.ui.isOrderGift = '';
			            		var apiInput =iscOrder.prepareChangeOrderInputForGiftRemoval(this.model.getCompleteOrderDetails.Order.OrderHeaderKey,orderLineList);
			            		iscMashup.callMashup(this,"viewcart_changeOrderForGift",apiInput,{}).then(this.updateGiftMessageRemovalForOrder.bind(this),angular.noop);
			            	}
			            },
			           
			            /**
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiOpenGiftOptionsForOrder
						 *@description Opens Gift options popup for order when Apply Gift Options button is clicked.
						 */  
			          uiOpenGiftOptionsForOrder : function(){
			               
			               var that = this;	
			               var isAnyLineGift = iscOrder.checkIfAnyOrderlineIsGift(this.model.orderLineList);
			          	   if(isAnyLineGift){
			          		   iscModal.showConfirmationMessage("viewcartdetails.LABEL_GiftOrderConfirmation").then(function(action){
										that.overRideGiftOptions(action);
									});
			          	   }else{
			          		   this.overRideGiftOptions('YES');
			          	   }
			            },
			            
			            /**
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiOpenGiftOptionsForOrderLine
						 *@description Opens Gift options popup for order line on click of 'Make this product a gift' link.
						 *@param {Object} orderlineModel - orderLine data as JSON object. 
						 */
			            
			            uiOpenGiftOptionsForOrderLine : function(orderlineModel){
			            	
			        	var that = this;
			       		var giftOptionsInput = null;
			       		if(orderlineModel.GiftFlag == "Y"){
			                    
			       			var giftInstructionObj =iscOrder.getGiftInstructionForOrderLine(orderlineModel);
			       			var giftMessage = null;
			       			if(giftInstructionObj !== null){
			       			  giftMessage = giftInstructionObj.InstructionText;
			       			}
			       			
			       			var giftRecipient =null;
			       			if(orderlineModel.PersonInfoMarkFor && orderlineModel.PersonInfoMarkFor.FirstName){
			       				giftRecipient = orderlineModel.PersonInfoMarkFor.FirstName;
				       		}
			       			
			       			 giftOptionsInput = {
			               			gift:{
			               				giftFlag:"Y",
			               				giftWrap: orderlineModel.ItemDetails.PrimaryInformation.AllowGiftWrap,
			               				giftRecipient : giftRecipient,
			               				giftMessage : giftMessage, 
			               				giftWrapChecked: orderlineModel.GiftWrap,
			               				showRemoveGift:'Y'
			              			}
			               	};
			       		}else{
			       			
			       			 giftOptionsInput = {
			               			gift:{
			               				giftWrap: orderlineModel.ItemDetails.PrimaryInformation.AllowGiftWrap,
			               				giftWrapChecked: 'N'
			             			}
			               	};
			       		}
			       		
				       	var giftOptionsModalInput = {
				       			modalInput: function(){
				       				return giftOptionsInput;
				       			}
				       	};
			       	
				       	iscModal.openModal('store.views.common.gift.giftoptions',giftOptionsModalInput,{})
								.then (function(callBackData){
									if(callBackData.data !== null && callBackData.data !== undefined){
										that.applyGiftOptionsForOrderLine.call(that,callBackData.data,orderlineModel);
									}
									},
									angular.noop);
			       
			            },
			            
			            /**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname overRideGiftOptions.
						 *@description Opens Gift options popup for Order overriding the existing gift options information at order line level .
						 *@param {String} action - action is the response of a confirmation dialog to override gift options. 
						 */
			            
			            overRideGiftOptions : function(action){
			            	if(action === 'YES'){
			            	   var that = this;	
			            	   this.model.giftModel = iscWizard.getWizardModel("giftOptionsForOrderModel");
			            		var giftOptionsInput = {};
			            		giftOptionsInput.gift = {};
			            		
			            		if(!iscCore.isVoid(this.model.giftModel)){
			            			
			            			giftOptionsInput.gift.giftRecipient = this.model.giftModel.giftRecipient;
			            			giftOptionsInput.gift.giftMessage =   this.model.giftModel.giftMessage;
			            			giftOptionsInput.gift.giftWrapChecked = this.model.giftModel.giftWrapChecked
			            		}
			            		
			                	var giftOptionsModalInput = {
			                			modalInput: function(){
			                				return giftOptionsInput;
			                			}
			                	};
			                	
			                	iscModal.openModal('store.views.common.gift.giftoptions',giftOptionsModalInput,{})
			     					.then(function(callBackData){
			    						if(callBackData !== null && callBackData.data !== null && callBackData.data !== undefined){
			    							that.applyGiftOptionsForOrder.call(that,callBackData.data);
			    						}
			       					},
			       					angular.noop);
			                }
			            	},
			            	
			            	 /**
							 *@iscdoc method
							 *@viewname store.views.order.cart-details.cart-details
							 *@methodname applyGiftOptionsForOrder.
							 *@description Calls changeOrder api to save data captured in gift options popup.
							 *@param {Object} data - JSON object containing data captured in gift options popup. 
							 */
			            	
			            	applyGiftOptionsForOrder : function(data){
			                	var orderLineList =[];
			                	orderLineList = this.model.orderLineList.OrderLineList.OrderLine;
			            		/* update the wizard model data */
			                	var giftPopupData = angular.copy(data);
			            		var apiInput =iscOrder.prepareChangeOrderInputForGift(data,this.model.getCompleteOrderDetails.Order.OrderHeaderKey,orderLineList);
			            		iscMashup.callMashup(this,"viewcart_changeOrderForGift",apiInput,{}).then(this.updateOrderWithGiftMessage.bind(this,giftPopupData),angular.noop);
			            	},
			            
			            	/**
							 *@iscdoc method
							 *@viewname store.views.order.cart-details.cart-details
							 *@methodname applyGiftOptionsForOrderLine.
							 *@description Calls changeOrder api to save data captured in gift options popup.
							 *@param {Object} data - JSON object containing data captured in gift options popup. 
							 *@param {Object} orderlineModel - orderLine data as JSON object.  
							 */
			            	
			            applyGiftOptionsForOrderLine : function(data,orderlineModel){
			            	
			            	var orderLineList =[];
			                orderLineList[0] = orderlineModel;
			            	if((!iscCore.isVoid(data.removeGift))&&(data.removeGift === 'Y')){
			            		var apiInput =iscOrder.prepareChangeOrderInputForGiftRemoval(this.model.getCompleteOrderDetails.Order.OrderHeaderKey,orderLineList);
			            		iscMashup.callMashup(this,"viewcart_changeOrderForGift",apiInput,{}).then(this.updateOrderLineAfterGiftRemoval.bind(this),angular.noop);
			            	}else{
			            		 var apiInput =iscOrder.prepareChangeOrderInputForGift(data,this.model.getCompleteOrderDetails.Order.OrderHeaderKey,orderLineList);
			                     iscMashup.callMashup(this,"viewcart_changeOrderForGift",apiInput,{}).then(this.updateOrderLineWithGiftMessage.bind(this),angular.noop);    	
			            	}
			            	
			        	},
			            
			        	/**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname updateOrderLineAfterGiftRemoval.
						 *@description Updates the order line once the gift options are removed.
						 *@param {Object} data - JSON object containing changeOrder api output. 
						 */
			        	
			        	updateOrderLineAfterGiftRemoval : function(data){
			        		var apiOutput = iscMashup.getMashupOutput(data,"viewcart_changeOrderForGift");
			        		iscOrder.updateOrderLines(apiOutput,this.model.orderLineList);
			        		
			        		if(this.model.orderLineList.OrderLineList.OrderLine.length === 1){
			        			this.model.giftModel = {};
				        		iscWizard.setWizardModel("giftOptionsForOrderModel",{});
				        		this.ui.isOrderGift = '';
				                }
			        		
			        	},
			        	
			        	/**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname updateOrderLineWithGiftMessage.
						 *@description Updates the order line with gift data.
						 *@param {Object} data - JSON object containing changeOrder api output. 
						 */
			        	
			            updateOrderLineWithGiftMessage : function(data){
			            	var apiOutput = iscMashup.getMashupOutput(data,"viewcart_changeOrderForGift");
			            	iscOrder.updateOrderLines(apiOutput,this.model.orderLineList);
			            },
			            
			            /**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname updateOrderWithGiftMessage.
						 *@description Updates the order with gift data.
						 *@param {Object} data - JSON object containing changeOrder api output. 
						 */
			            
			            updateOrderWithGiftMessage : function(giftPopupData, data){
			            	var apiOutput = iscMashup.getMashupOutput(data,"viewcart_changeOrderForGift");
			            	
			            	this.model.giftModel = giftPopupData;
		            		iscWizard.setWizardModel("giftOptionsForOrderModel",giftPopupData);
			            	
			            	if((!iscCore.isVoid(apiOutput.Order.OrderLines)) && (!iscCore.isVoid(apiOutput.Order.OrderLines.OrderLine))){
			            		if(this.model.orderLineList.OrderLineList.OrderLine.length === apiOutput.Order.OrderLines.OrderLine.length){
			            			this.replaceCompleteOrderLineList(apiOutput);
			            		}else{
			            			iscOrder.updateOrderLines(apiOutput,this.model.orderLineList);	
			            		}
			            	}
			            	
			            	this.ui.isOrderGift = 'Y';
			            },
			           
			            /**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname updateGiftMessageRemovalForOrder.
						 *@description Updates the order once the gift options are removed.
						 *@param {Object} data - JSON object containing changeOrder api output. 
						 */
			            
			           updateGiftMessageRemovalForOrder : function(data){
			            	var apiOutput = iscMashup.getMashupOutput(data,"viewcart_changeOrderForGift");
			            	iscOrder.updateOrderLines(apiOutput,this.model.orderLineList);
			            	//this.replaceCompleteOrderLineList(apiOutput);
			            	this.ui.isOrderGift = '';
			            },
			            
			            /**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname replaceCompleteOrderLineList.
						 *@description Updates the orderLineList model with the latest order line list.
						 *@param {Object} apiOutput - JSON object containing changeOrder api output. 
						 */
			            
			            replaceCompleteOrderLineList : function(apiOutput){
			            	if((!iscCore.isVoid(apiOutput.Order.OrderLines)) && (!iscCore.isVoid(apiOutput.Order.OrderLines.OrderLine))){
			            		delete this.model.orderLineList.OrderLineList["OrderLine"];
			                	this.model.orderLineList.OrderLineList.OrderLine = [];
			                	this.model.orderLineList.OrderLineList.OrderLine = apiOutput.Order.OrderLines.OrderLine;	
			            	}
			            },
			            
			            /*
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname handleApplyCoupon.
						 *@description Call back handler for translateBarcode api when coupon is scanned.Updates order with promotion details if applying coupon is successful .
						 *@param {Object} apiOutput - JSON object containing translateBarcode api output. 
						 */
			            
			          /*  handleApplyCoupon : function(data){
			            	var apiOutput = iscMashup.getMashupOutput(data,"viewcart_translateBarcodeForCoupon");
			            		iscOrder.updateOrderLines(apiOutput,this.model.orderLineList);
			    			    this.model.getCompleteOrderDetails =iscOrder.updateOrderHeaderDetails(apiOutput,this.model.getCompleteOrderDetails);
				        		this.model.couponInput.couponID ="";	
			            	$scope.cartDetails.$setPristine();
							var alertMessage = iscI18n.translate("viewcartdetails.aria_applyCouponSuccessMessage");
							iscModal.showA11YAlertMessage(alertMessage);
			            },*/
			            
			            /**
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiCouponApplied
						 *@description This method is used to update order details once a coupon is added or removed
						 *@param {Object} orderDetails - order model that apply coupon directive returns. 
						 */ 
			            uiCouponApplied:function(orderDetails){		        	
				        	this.model.getCompleteOrderDetails = orderDetails;
				        	$scope.cartDetails.$setPristine();
							/*var alertMessage = iscI18n.translate("viewcartdetails.aria_applyCouponSuccessMessage");
							iscModal.showA11YAlertMessage(alertMessage);*/
			            },
			            
			            /**
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiValidateQuantityAndUpdate.
						 *@description Validates order line quantity change and calls getItemAvailabilityForStore api to check the Item availability .
						 *@param {Object} qtyField - quantity field as JSON object. 
						 *@param {Object} orderlineModel - orderLine data as JSON object. 
						 */
			            
				  		uiValidateQuantityAndUpdate : function(qtyField,orderlineModel){
				  			
				  			var formats = $locale.NUMBER_FORMATS;
				  			var newQuantity = orderlineModel.OrderLineTranQuantity.OrderedQty;
				  			newQuantity=newQuantity.replace(formats.GROUP_SEP, '');
				  			orderlineModel.OrderLineTranQuantity.OrderedQty = newQuantity;
				  			if(qtyField.oldQty !== newQuantity){
				  				qtyField.oldQty = newQuantity ;
				  			if (typeof newQuantity == "string")
				  				newQuantity = parseInt(newQuantity,10);
				  			if (newQuantity === 0 ) {
				  				iscModal.showErrorMessage(iscI18n.translate('viewcartdetails.MSG_InvalidQty')); 
				  				orderlineModel.OrderLineTranQuantity.OrderedQty = orderlineModel.OrderedQty;
				  				orderlineModel.showQtyUpdate='N';
						    }else{
					  			var  modifyFulfillmentOptionsInput =iscOrder.prepareModifyFulfillmentOptionsApiInput(this.model.getCompleteOrderDetails.Order.OrderHeaderKey,orderlineModel.OrderLineKey);
					  			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderLineTranQuantity = {};
					  			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderLineTranQuantity.OrderedQty = orderlineModel.OrderLineTranQuantity.OrderedQty;
					  			var getItemAvailabilityForStoreInput = this.getItemAvailabilityForStoreApiInput(this.model.getCompleteOrderDetails.Order.OrderHeaderKey,orderlineModel);
					  			getItemAvailabilityForStoreInput.Promise.modifyFulfillmentOptionsInput =modifyFulfillmentOptionsInput ;
					  			iscMashup.callMashup(this,"viewcart_getItemAvailabilityForStore",getItemAvailabilityForStoreInput,{}).then(this.handleOrderLineQtyChange.bind(this),angular.noop);
						    }
				  			}
				  		},
				  		
				  		/**
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiShowUpdateButton.
						 *@description Shows/hides Update button for quantity field based on the validity of the edited quantity.
						 *@param {Object} qtyField - quantity field as JSON object. 
						 *@param {Object} orderlineModel - orderLine data as JSON object. 
						 */
				  		
				  		uiShowUpdateButton : function(qtyField,orderlineModel){
				  						
				  						var formats = $locale.NUMBER_FORMATS;
							  			var newQuantity = orderlineModel.OrderLineTranQuantity.OrderedQty;
							  			if(!iscCore.isVoid(newQuantity))
							  				newQuantity = newQuantity.replace(formats.GROUP_SEP, '');
							  			if((!iscCore.isVoid(newQuantity)) && qtyField.oldQty !== newQuantity)
							  				orderlineModel.showQtyUpdate ='Y';
							  			else
							  				orderlineModel.showQtyUpdate ='N';
				  			
				  		},
				  		
				  		/**
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiHideUpdateButton.
						 *@description hides Update button for quantity field.
						 *@param {Object} orderlineModel - orderLine data as JSON object. 
						 */
				  		
				  		uiHideUpdateButton : function(orderlineModel){
				  			orderlineModel.showQtyUpdate ='N';
				  		},
				  		
				  		/**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname getItemAvailabilityForStoreApiInput.
						 *@description utility to prepare getItemAvailabilityForStore api input.
						 *@param {String} orderHeaderKey - unique identifier for order. 
						 *@param {String} orderlineModel - orderLine data as JSON object. 
						 *@returns {Object} json object. 
						 */
				  		getItemAvailabilityForStoreApiInput : function(orderHeaderKey,orderlineModel){
				  			var getItemAvailabilityForStoreInput = {'Promise':
													                  {'OrderHeaderKey':orderHeaderKey,
													                   'OrderLineKey':	orderlineModel.OrderLineKey,
													                   'PromiseLines':
													                       {
													                       'PromiseLine':
													                              [ 
													                                {'ItemID':orderlineModel.ItemDetails.ItemID,
													                                 'UnitOfMeasure' : orderlineModel.ItemDetails.UnitOfMeasure,
														                             'RequiredQty': orderlineModel.OrderLineTranQuantity.OrderedQty,
														                             'Quantity':orderlineModel.OrderLineTranQuantity.OrderedQty,
														                             'OrderLine':
														                                {
														                                   'OrderLineKey':orderlineModel.OrderLineKey,
															                               'DeliveryMethod':orderlineModel.DeliveryMethod 
														                                }
													                               }
													                              ] 
													                       }
													                   }
													                }; 

							if(orderlineModel.DeliveryMethod === "SHP"){
								getItemAvailabilityForStoreInput.Promise.ComputeShpAvl = "Y";	
							}
							
							else if(orderlineModel.DeliveryMethod === "PICK"){
								getItemAvailabilityForStoreInput.Promise.ComputePickupAvl = "Y";	
							    getItemAvailabilityForStoreInput.Promise.ShipNode =  orderlineModel.Shipnode.ShipNode;
							}
							else if(orderlineModel.DeliveryMethod === "CARRY"){
							getItemAvailabilityForStoreInput.Promise.ComputeCarryAvl = "Y";
							 getItemAvailabilityForStoreInput.Promise.ShipNode =  orderlineModel.Shipnode.ShipNode;
							}
				  			
							return getItemAvailabilityForStoreInput;
				  		},
				  		
				  		/**
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiGetOrderLineListNext.
						 *@description Shows next set of order lines when order lines panel is scrolled.
						 */
				  		
				  		uiGetOrderLineListNext: function(){
			            	    if(this.ui.oLineShownCount <= this.model.orderLineList.OrderLineList.OrderLine.length){
				                  this.ui.oLineShownCount+=5;
				                }
			            },
			            
			            /**
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiOLineScrollActionValidator 
						 *@description Validates if next page action should be performed by checking whther api call is in progress. The current implementation always returns true since the pagination is client side in cart details.
						 */
			            
			            uiOLineScrollActionValidator : function(){
			            	return true;
			            },
			            
			            /**
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiCheckDirtyStateBeforePayment
						 *@description Onclick event handler for 'Continue to Payment' button.Checks the cart details screen for dirty state.
						 */
			            uiCheckDirtyStateBeforePayment : function (){
			            	
			            	/* Check whether the screen is dirty before navigating to payment screen*/
			            	 
			            	 var isDirty = $scope.cartDetails.$dirty;
			            	 if(isDirty){
			            		 var that = this;
			            		 iscModal.showConfirmationMessage(iscI18n.translate('addItems.MSG_Screen_dirty')).then(function(action){
				            			if(iscCore.isBooleanTrue(action)){
				            				that.uiValidateOrderBeforePayment();
				            			}
			            	 });
			            	 }else{
			            		 this.uiValidateOrderBeforePayment();
			            	 }		
			            },
			            
			            /**
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiValidateOrderBeforePayment
						 *@description Handles 'Continue to Payment' button when screen is not dirty.Validates the order for Shipping address when shipping lines are present.
						 */
			            
			             uiValidateOrderBeforePayment : function(){
			            	 
			            	/* check whether all the lines are carry lines*/  
			            	 
	            		    var orderDetailsModel = {};
	            		    orderDetailsModel.Order= {};
	            		    orderDetailsModel.Order.OrderLines = {};
	            		    orderDetailsModel.Order.OrderLines.OrderLine = this.model.orderLineList.OrderLineList.OrderLine;
	            		   
	            		    if(iscOrder.orderHasOnlyCarryLines(orderDetailsModel)){
	            		    	
	            		    	iscWizard.gotoCustomPage('paymentCapture',{},{});;
	            		    }
	            		    else{
	            		    	
	            		    	
			            	 var isShippingLinePresent = iscOrder.checkIfOrderHasShipLines(this.model.orderLineList.OrderLineList.OrderLine);
			            	 
			             	/* validate whether all the lines have ship to address*/
			            	 var isShippingLinePresent = iscOrder.checkIfOrderHasShipLines(this.model.orderLineList.OrderLineList.OrderLine);
			            	 var checkAvailability = true;
			            	 if(isShippingLinePresent){
			            		 var orderModel = this.model.getCompleteOrderDetails;
			                	 if(iscCore.isVoid(orderModel.Order.PersonInfoShipTo)){
			                		 iscModal.showErrorMessage(iscI18n.translate('viewcartdetails.NoShipToAddressOline')); 
			                		 checkAvailability = false;
			            	 }
			            	 }
			            	     if(checkAvailability){
			            	     	if(!iscOrder.orderHasMixedSettledLines(this.model.orderLineList.OrderLineList.OrderLine)){
			            	     		var apiInput = {};
				                 		apiInput.Order= {};
				                 		apiInput.Order.OrderHeaderKey=this.model.getCompleteOrderDetails.Order.OrderHeaderKey;
				                 		iscMashup.callMashup(this,"vc_getCompleteOrderLineList",apiInput,{}).then(this.goToPaymentPage.bind(this),angular.noop); 
			            	     	}
			            	    	else {
			            	    		var computeAvailabilityApiInput = {};
				                 		computeAvailabilityApiInput.Order= {};
				                 		computeAvailabilityApiInput.Order.OrderHeaderKey=this.model.getCompleteOrderDetails.Order.OrderHeaderKey;
				                 		var updateSettledQtyOnAllLinesApiInput = {};
				                 		updateSettledQtyOnAllLinesApiInput.Order= {};
				                 		updateSettledQtyOnAllLinesApiInput.Order.OrderHeaderKey=this.model.getCompleteOrderDetails.Order.OrderHeaderKey;
				                 		updateSettledQtyOnAllLinesApiInput.Order.OrderLines = {};
				                 		updateSettledQtyOnAllLinesApiInput.Order.OrderLines.OrderLine = [];
				                 		for(var i= 0; i<this.model.orderLineList.OrderLineList.OrderLine.length; i++){
				                 			var orderline = this.model.orderLineList.OrderLineList.OrderLine[i];
											if(orderline.OrderLineTranQuantity && orderline.OrderLineTranQuantity.OrderedQty 
												&& orderline.OrderLineTranQuantity.SettledQuantity && orderline.OrderLineTranQuantity.OrderedQty != orderline.OrderLineTranQuantity.SettledQuantity){
												var lengthOfInputOrderLines = updateSettledQtyOnAllLinesApiInput.Order.OrderLines.OrderLine.length;
												updateSettledQtyOnAllLinesApiInput.Order.OrderLines.OrderLine[lengthOfInputOrderLines] = {};
												updateSettledQtyOnAllLinesApiInput.Order.OrderLines.OrderLine[lengthOfInputOrderLines].OrderLineKey = orderline.OrderLineKey;
												updateSettledQtyOnAllLinesApiInput.Order.OrderLines.OrderLine[lengthOfInputOrderLines].OrderLineTranQuantity = {};
												updateSettledQtyOnAllLinesApiInput.Order.OrderLines.OrderLine[lengthOfInputOrderLines].OrderLineTranQuantity.SettledQuantity = orderline.OrderLineTranQuantity.OrderedQty;
											 }
										}
			            	    		var updateSettledQtyOnAllLinesMashupRefObj = iscMashup.getMashupRefObj(this,"vc_updateSettledQtyOnAllLines",updateSettledQtyOnAllLinesApiInput);
			            	    		var computeAvailabilityMashupRefObj = iscMashup.getMashupRefObj(this,"vc_getCompleteOrderLineList",computeAvailabilityApiInput);
			            	    		var mashupRefList = [updateSettledQtyOnAllLinesMashupRefObj,computeAvailabilityMashupRefObj];
		            					iscMashup.callMashups(this,mashupRefList,{}).then(this.goToPaymentPage.bind(this),angular.noop);
			            	    	}
			            	     }
			            		 
			              }
			             }  
	            		    ,
			              
			              /**
							 *@iscdoc method
							 *@viewname store.views.order.cart-details.cart-details
							 *@methodname goToPaymentPage
							 *@description Callback handler for getCompleteOrderLineList api whose custom mashup checks the availbility of all order lines.If all the lines are available,Payment screen is shown. 
							 *@param {Object} data - JSON object containing getCompleteOrderLineList api output.
						  */
			              
			              goToPaymentPage : function(data){
			            	 var output = iscMashup.getMashupOutput(data,"vc_getCompleteOrderLineList");
			             	 if(output.OrderLineList.IsAnyLineUnAvailable == "N"){
			             		 iscWizard.gotoCustomPage('paymentCapture',{},{});;
			             	 }
			             	 else{
			             		 iscModal.showErrorMessage(iscI18n.translate('viewcartdetails.UnAvailbleLines'));
			             	 }
			              },
                        
			              /**
							 *@iscdoc uimethod
							 *@viewname store.views.order.cart-details.cart-details
							 *@methodname uiGetItemDetails
							 *@description Opens product details screen.
							 *@param {String} itemId - one of the unique identifier of a product.  
							 *@param {String} uom - one of the unique identifier of a product.  
						  */
			              
			            uiGetItemDetails:function(itemId,uom){
			  				if(!iscCore.isVoid(itemId)){
			  					var pageInput = {
			  							input: itemId
			  					};
			  			        iscWizard.gotoCustomPage('addItems',pageInput,{});
			  				}
			  		    },
			  		  
			  		  /**
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiAddOrEditAddress
						 *@description Opens Address capture popup for Adding or Editing Order/OrderLine shippping address.
						 *@param {String} mode - Specifies whether the address needs to be added or edited.
						 *@param {Object} orderlineModel - orderLine data as JSON object.    
					  */
			  		    
			  		  uiAddOrEditAddress : function(mode,orderlineModel){
			 			   var that = this;
			 			   var popupInput = {};
			 			   popupInput.Mode = mode;
			 			  
			 			   popupInput.Context = "showDefaultshipto";
			 			   if(mode == "Edit"){
			 				  if(!iscCore.isVoid(orderlineModel.PersonInfoShipTo)){
			 					 popupInput.PersonInfo = angular.copy(orderlineModel.PersonInfoShipTo);     
			 				  }else{
			 					 popupInput.PersonInfo = angular.copy(this.model.getCompleteOrderDetails.Order.PersonInfoShipTo);   
			 				  }
			 			   }
			 			   
			 			   if(!this.model.getCompleteOrderDetails.Order.PersonInfoShipTo){
			 				  popupInput.hideShipaddress = true;
			 			   }else {
			 				  if(mode=='Add'){
			 					 popupInput.PersonInfo ={};
			 				  }
			 					 popupInput.PersonInfo.IsDefaultShippingAddress='N';
			 			   }
			 			  
			 			   var addressPopUpInput = {
			 					 modalInput: function(){
			          				return popupInput;
			          			}   
			 					   
			 			   };
			 			   
			 			 iscModal.openModal('store.views.common.orderaddress.orderaddresspopup',addressPopUpInput,{})
								.then(function(callBackData){
			          			if(callBackData != null && callBackData.data != null && callBackData.data != undefined){
			          				that.onAddressAdditionOrEdit.call(that,callBackData.data,orderlineModel);
			          			}		            			
			     				},
								angular.noop);
			 		     },
			 		  
			 		    /**
							 *@iscdoc method
							 *@viewname store.views.order.cart-details.cart-details
							 *@methodname onAddressAdditionOrEdit
							 *@description Call back handler of address capture popup,which calls getItemAvailabilityForStore api for captured address to check the availability of the product.
							 *@param {Object} data - data captured in address capture popup.
							 *@param {Object} orderlineModel - orderLine data as JSON object.    
						  */
			 		     
			 		  onAddressAdditionOrEdit : function(data,orderlineModel){
			 			    
			 			    var personInfo = angular.copy(data);
			 			    
			 			   delete personInfo.IsDefaultShippingAddress;
			 			   delete personInfo.IsDefaultBillingAddress;
			 			   
			 			    var modifyFulfillmentOptionsInput =iscOrder.prepareModifyFulfillmentOptionsApiInput(this.model.getCompleteOrderDetails.Order.OrderHeaderKey,orderlineModel.OrderLineKey);
				  			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderLineTranQuantity = {};
				  			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderLineTranQuantity.OrderedQty = orderlineModel.OrderLineTranQuantity.OrderedQty;
				  			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].PersonInfoShipTo = {};
				  			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].PersonInfoShipTo = personInfo;
				  			
				  			/* check if the order has ship to address*/
				  			if((!this.model.getCompleteOrderDetails.Order.PersonInfoShipTo) || (data.IsDefaultShippingAddress === 'Y')){
				  				modifyFulfillmentOptionsInput.Order.PersonInfoShipTo  =  personInfo;
				  				if(!this.model.getCompleteOrderDetails.Order.BillToID){
				  				modifyFulfillmentOptionsInput.Order.CustomerFirstName =  personInfo.FirstName;
				      	        modifyFulfillmentOptionsInput.Order.CustomerLastName  =  personInfo.LastName;
				      	        modifyFulfillmentOptionsInput.Order.CustomerEMailID   =  personInfo.EMailID;
				      	        modifyFulfillmentOptionsInput.Order.CustomerZipCode   =  personInfo.ZipCode;
				  				}
				  			}
			               /* getItemAvailabilityForStore API Input*/
				  			
				  			var getItemAvailabilityForStoreInput = this.getItemAvailabilityForStoreApiInput(this.model.getCompleteOrderDetails.Order.OrderHeaderKey,orderlineModel);
				  			getItemAvailabilityForStoreInput.Promise.PromiseLines.PromiseLine[0].ShipToAddress = personInfo;
				  			getItemAvailabilityForStoreInput.Promise.modifyFulfillmentOptionsInput =modifyFulfillmentOptionsInput ;
				  			iscMashup.callMashup(this,"viewcart_getItemAvailabilityForStore",getItemAvailabilityForStoreInput,{}).then(this.handleShippingLineAddress.bind(this),angular.noop);
				  			
			 		  },
			 		   
			 		 /**
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiIdentifyCustomerPopup
						 *@description Opens identify customer popup.
						 *@param {Object} orderlineModel - orderLine data as JSON object.    
					  */
			 		  
			  		   uiIdentifyCustomerPopup:function(orderlineModel){
			  			var customerInput = {};
			  			var that = this;
			  			customerInput.Mode = "Identify";
			  			var customerPopupInput = {
			          			modalInput: function(){
			          				return customerInput;
			          			}
			          	};
			  			
						iscModal.openModal('store.views.common.customer.customerpopup',customerPopupInput,{})
								.then(function(callBackData){
			        			if(callBackData != null && callBackData.data != null && callBackData.data != undefined){
			        				that.onCustomerIdentification.call(that,callBackData.data);
			        			}		            			
			   				},
								angular.noop);	
			  		  },
			  		  
			  		 /**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname onCustomerIdentification
						 *@description call back handler of identify customer popup.It calls modifyFulfillmentOptions to save the customer information on order.
						 *@param {Object} customerInfo - data passed from identify customer popup .    
					  */
			  		  
			          onCustomerIdentification: function(customerInfo){
			        	  var customerModel = customerInfo.customerModel;
			        	  
			        	   var modifyFulfillmentOptionsInput  = {};
			          	   modifyFulfillmentOptionsInput.Order ={};
			          	   modifyFulfillmentOptionsInput.Order.OrderHeaderKey=this.model.getCompleteOrderDetails.Order.OrderHeaderKey;
			          	   modifyFulfillmentOptionsInput.Order.BillToID = customerModel.Customer.CustomerID; 
			          	   if(!iscCore.isVoid(customerInfo.addressSelected)){
			          		 modifyFulfillmentOptionsInput.Order.PersonInfoShipTo={};
				  			 modifyFulfillmentOptionsInput.Order.PersonInfoShipTo = angular.copy(customerInfo.addressSelected.PersonInfo);
			          		 if(customerInfo.addressSelected.IsDefaultBillTo === 'Y'){
				          		 modifyFulfillmentOptionsInput.Order.PersonInfoBillTo={};
					  			 modifyFulfillmentOptionsInput.Order.PersonInfoBillTo = angular.copy(customerInfo.addressSelected.PersonInfo);
			          		 }
			          	   }
			          	   
			          	   
			      	       if(customerModel.Customer.CustomerType === '02' || (customerModel.Customer.CustomerContactList &&  customerModel.Customer.CustomerContactList.CustomerContact && customerModel.Customer.CustomerContactList.CustomerContact.length === 1)){
			  				modifyFulfillmentOptionsInput.Order.CustomerContactID =  customerModel.Customer.CustomerContactList.CustomerContact[0].CustomerContactID;
			  				modifyFulfillmentOptionsInput.Order.CustomerFirstName =  customerModel.Customer.CustomerContactList.CustomerContact[0].FirstName;
			     	    	modifyFulfillmentOptionsInput.Order.CustomerLastName  =  customerModel.Customer.CustomerContactList.CustomerContact[0].LastName;
			     	    	modifyFulfillmentOptionsInput.Order.CustomerEMailID   =  customerModel.Customer.CustomerContactList.CustomerContact[0].EmailID;
			     	    	
			     	    	if(!iscCore.isVoid(customerInfo.addressSelected)){
			     	    		modifyFulfillmentOptionsInput.Order.CustomerZipCode   =  customerInfo.addressSelected.PersonInfo.ZipCode;
				          	   }
			      	       }
			          	 
			      	      iscMashup.callMashup(this,"viewcart_customer_modifyFulfillmentOptions",modifyFulfillmentOptionsInput,{}).then(this.handleModifyFulfillmentOptionsForCustomer.bind(this),angular.noop);  
			          },
			          
			          /**
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiEditAddressInCustomerPanel
						 *@description Opens address capture popup when Edit link is clicked in customer panel 
						 *@param {Object} customerInfo - data passed from identify customer popup .    
					  */
			          
			          uiEditAddressInCustomerPanel: function(){
			   		      var that = this;
			   		      var popupInput = {};
			   		      popupInput.Mode = 'Edit';
			   			  popupInput.PersonInfo = angular.copy(this.model.getCompleteOrderDetails.Order.PersonInfoShipTo);   
			   			  popupInput.hideShipaddress = true;
			   		      var addressPopUpInput = {
			   				 modalInput: function(){
			        				return popupInput;
			        			}   
			   		   };
			   		   
			   		  iscModal.openModal('store.views.common.orderaddress.orderaddresspopup',addressPopUpInput,{})
			   				.then(function(callBackData){
			        			if(callBackData != null && callBackData.data != null && callBackData.data != undefined){
			        				that.handleEditAddressInCustomerPanel.call(that,callBackData.data);
			        			}		            			
			   				},
			   				angular.noop);
			     		},
			     		
			     		 /**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname handleEditAddressInCustomerPanel
						 *@description Call back handler for order address popup opened in customer panel.this method calls modifyFulfillmentOptions api is called to save the new address.
						 *@param {Object} newAddress - data passed from identify order address popup .    
					     */
			     		
			     		handleEditAddressInCustomerPanel: function(newAddress){
			     		   
			               var personInfo = angular.copy(newAddress);
			 			   delete personInfo.IsDefaultShippingAddress;
			 			    
			     		   var modifyFulfillmentOptionsInput  = {};
			           	   modifyFulfillmentOptionsInput.Order ={};
			           	   modifyFulfillmentOptionsInput.Order.OrderHeaderKey=this.model.getCompleteOrderDetails.Order.OrderHeaderKey;
			           	   modifyFulfillmentOptionsInput.Order.PersonInfoShipTo={}
			           	   modifyFulfillmentOptionsInput.Order.PersonInfoShipTo = personInfo;
			           	  
			           	   if(!this.model.getCompleteOrderDetails.Order.BillToID){
			     	       modifyFulfillmentOptionsInput.Order.CustomerFirstName =   personInfo.FirstName;
			     	       modifyFulfillmentOptionsInput.Order.CustomerLastName  =   personInfo.LastName;
			     	       modifyFulfillmentOptionsInput.Order.CustomerEMailID   =   personInfo.EMailID;
			     	       modifyFulfillmentOptionsInput.Order.CustomerZipCode   =   personInfo.ZipCode;
			           	   }
			            	  iscMashup.callMashup(this,"viewcart_customer_modifyFulfillmentOptions",modifyFulfillmentOptionsInput,{}).then(this.handleModifyFulfillmentOptionsForCustomer.bind(this),angular.noop);  
			     		},
			     		
			     		 /**
						 *@iscdoc uimethod
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname uiOpenOverridePriceModal
						 *@description onclick handler function for override price icon
						 *@param {Object} orderlineModel - orderLine data as JSON object.    
					     */	
			  		  uiOpenOverridePriceModal: function(orderlineModel){
			          	var that = this;
			          	var ovpInput = {};
			          	ovpInput.price = {};
			          	ovpInput.price.listPrice =  orderlineModel.LinePriceInfo.ListPrice;
			          	ovpInput.price.unitPrice =  orderlineModel.LinePriceInfo.UnitPrice;
			          	ovpInput.price.Currency = this.model.getCompleteOrderDetails.Order.PriceInfo.Currency;
			          	
			          	var priceOverrideInput = {
			          			modalInput: function(){
			          				return ovpInput;
			          			}
			          	};
			          	
			          	iscModal.openModal('store.views.common.priceoverride.priceoverride',priceOverrideInput,{})
								.then(function(callBackData){
			          			if(callBackData != null && callBackData.data != null && callBackData.data != undefined){
			          				that.onPriceOverride.call(that,callBackData.data,orderlineModel);
			          			}		            			
			     				},
								angular.noop);	
			          },
			          
			          /**
			           * @iscdoc method
			           * @viewname store.views.order.cart-details.cart-details
			           * @methodname onPriceOverride
			           * @description callback handler function for price override popup.saves the overridden price against item.
			           *@param {Object} data - data sent from override price popup.
					   *@param {Object} orderlineModel - orderLine data as JSON object.  
			           */
			          onPriceOverride: function(data,orderlineModel){
			  			var modifyFulfillmentOptionsInput =iscOrder.prepareModifyFulfillmentOptionsApiInput(this.model.getCompleteOrderDetails.Order.OrderHeaderKey,orderlineModel.OrderLineKey);
			  			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].LinePriceInfo = {};
			  			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].LinePriceInfo.ListPrice = orderlineModel.LinePriceInfo.ListPrice;
			  			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].LinePriceInfo.UnitPrice = data.newprice;
			  			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].LinePriceInfo.IsPriceLocked = 'Y';
			         	iscOrder.addPriceOverrideDefaultNote(modifyFulfillmentOptionsInput,data.reason);
			         	iscMashup.callMashup(this,"viewcart_ovp_modifyFulfillmentOptions",modifyFulfillmentOptionsInput,{}).then(this.handlePriceOverrideApiCall.bind(this),angular.noop);
			          },
			          
			          /**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname handlePriceOverrideApiCall
						 *@description Call back handler of modifyFulfillmentOptions api when unit price of product is overridden.
						 *@param {Object} data - modifyFulfillmentOptions api output. 
						 */
			          
			          handlePriceOverrideApiCall : function(data){
			        	  var apiOutput = iscMashup.getMashupOutput(data,"viewcart_ovp_modifyFulfillmentOptions");  
                          this.handleModifyFulfillmentOptionsApiOutput(apiOutput);
			          },
			          
			          /**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname handleModifyFulfillmentOptionsForCustomer
						 *@description Call back handler of modifyFulfillmentOptions api when customer is identified or order address is changed in customer panel.
						 *@param {Object} data - modifyFulfillmentOptions api output.
						 */
			          
			          handleModifyFulfillmentOptionsForCustomer : function(data){
			        	  var apiOutput = iscMashup.getMashupOutput(data,"viewcart_customer_modifyFulfillmentOptions");  
                          this.handleModifyFulfillmentOptionsApiOutput(apiOutput);
                          
                          $timeout(function(){
				 				iscMashup.callMashup(this,"vc_getOrderFulfillmentDetails",{"Order" : { "OrderHeaderKey" : this.model.orderModel.Order.OrderHeaderKey}},{showMask:false}).then(this.handleAvailabilityOfCart.bind(this),angular.noop);
			                    }.bind(this),0);
			          },
			          
			          /**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname handleModifyFulfillmentOptionsOutput
						 *@description Call back handler of modifyFulfillmentOptions api when coupon is removed,store is selected for a pickup line and delivery method is changed.
						 *@param {Object} data - modifyFulfillmentOptions api output.
						 */
			          
			          handleModifyFulfillmentOptionsOutput : function(data){
			        	  var apiOutput = iscMashup.getMashupOutput(data,"viewcart_modifyFulfillmentOptions");  
                          this.handleModifyFulfillmentOptionsApiOutput(apiOutput);
                          this.model.couponInput.couponID ="";
			          },
			          
			          /**
						 *@iscdoc method
						 *@viewname store.views.order.cart-details.cart-details
						 *@methodname handleModifyFulfillmentOptionsApiOutput
						 *@description Utility which updates orderLineList,getCompleteOrderDetails models when modifyFulfillmentOptions api is called.
						 *@param {Object} apiOutput - modifyFulfillmentOptions api output.
						 */
			          
				  	        handleModifyFulfillmentOptionsApiOutput : function (apiOutput){
				  	        	iscOrder.updateOrderLines(apiOutput,this.model.orderLineList);
		  	    			    this.model.getCompleteOrderDetails =iscOrder.updateOrderHeaderDetails(apiOutput,this.model.getCompleteOrderDetails);
				  	        },
				  	          
				  	      /**
							 *@iscdoc method
							 *@viewname store.views.order.cart-details.cart-details
							 *@methodname handleOrderLineQtyChange
							 *@description Call back handler of getItemAvailabilityForStore api.
							 *@param {Object} data - getItemAvailabilityForStore api output.
							 */
				  	        
				  	        handleOrderLineQtyChange : function(data){
				  	        	this.handleItemAvailabilityAndOrderModification(data);
								var alertMessage = iscI18n.translate("viewcartdetails.aria_updateOrderLineQtySuccessMessage");
								iscModal.showA11YAlertMessage(alertMessage);
				  	        },
				  	      
				  	      /**
							 *@iscdoc method
							 *@viewname store.views.order.cart-details.cart-details
							 *@methodname handleShippingLineAddress
							 *@description Call back handler of getItemAvailabilityForStore api when shipping line is address is added/modified.
							 *@param {Object} data - getItemAvailabilityForStore api output.
							 */
				  	        
				  	      handleShippingLineAddress : function(data){
				  	    	    
				  	     	this.handleItemAvailabilityAndOrderModification(data);  
				  	      $timeout(function(){
				 				iscMashup.callMashup(this,"vc_getOrderFulfillmentDetails",{"Order" : { "OrderHeaderKey" : this.model.orderModel.Order.OrderHeaderKey}},{showMask:false}).then(this.handleAvailabilityOfCart.bind(this),angular.noop);
			                    }.bind(this),0);
				  	      },
				  	      
				  	      /**
							 *@iscdoc method
							 *@viewname store.views.order.cart-details.cart-details
							 *@methodname handleItemAvailabilityAndOrderModification
							 *@description Call back handler of getItemAvailabilityForStore api.
							 *@param {Object} data - getItemAvailabilityForStore api output.
							 */
				  	        
				  	        handleItemAvailabilityAndOrderModification : function(data){
				  	        	
				  	        	var apiOutput = iscMashup.getMashupOutput(data,"viewcart_getItemAvailabilityForStore");
				 			    if(!iscCore.isVoid(apiOutput.Promise)){
				 			    	if(apiOutput.Promise.IsItemQtyAvailable === "N"){
				 			    		iscModal.showErrorMessage(iscI18n.translate('viewcartdetails.OrderLineQtyChangeError'));
				 			    		/* reset the quantity field */
				 			    		var orderLineKey = apiOutput.Promise.OrderLineKey;
				 			    		var orderLineList = []; 
				 			    		orderLineList = this.model.orderLineList.OrderLineList.OrderLine;
			 	  	        		   for(var j=0; j<orderLineList.length; j++){
			 	        					
			 	        						if(orderLineList[j].OrderLineKey == orderLineKey){
			 	        							orderLineList[j].OrderLineTranQuantity.OrderedQty = orderLineList[j].OrderedQty;
			 	        							orderLineList[j].showQtyUpdate ='N';
			 	        							break;
			 	        						}
			 	  	        	 	   }
				 			    	}
				 			    }else{
				 			    	
				 			    	iscOrder.updateOrderLines(apiOutput,this.model.orderLineList);
				 			    	this.model.getCompleteOrderDetails =iscOrder.updateOrderHeaderDetails(apiOutput,this.model.getCompleteOrderDetails);
				 			    	if(iscCore.isVoid(this.model.getCompleteOrderDetails.PromotionsApplied)){
				 			    		this.ui.showCouponScanField = false;
				 			    	}
				 			    	$scope.cartDetails.$setPristine();
				 			    	
				 			    	}
				  	        
				  	        },
				  	        
				  	      /**
							 *@iscdoc uimethod
							 *@viewname store.views.order.cart-details.cart-details
							 *@methodname uiOpenStoreSearch
							 *@description onclick handler function for store address link.It opens Store search popup.
							 *@param {Object} orderlineModel - orderLine data as JSON object.    
						     */	
				            uiOpenStoreSearch: function(orderlineModel){
				            	var that = this;
				            	var currentStoreAddress = iscAppContext.getFromContext('storeAddress').ShipNodePersonInfo;

				            	var storeSearchModalInput = {
				            			modalInput: function(){
				            				return {
				            				inputData : {callingMode : "CARTDETAILS",orderLine : orderlineModel}		
				            						};
				            								}
				            						};
				            	
				            	iscModal.openModal('store.views.common.storesearch.storesearch',storeSearchModalInput,{})
				            			 .then(function(callBackData){
				            		that.afterStoreSelection.call(that,callBackData.data,orderlineModel);
									},angular.noop);
				            },
				            
				            uiValidateQuantity : function(validationResponseObj, angularErrorObject, modelValue, viewValue){
				            	
				            	/* check if the DataType Validation is successful */
				            	
				            	if(!iscCore.isVoid(angularErrorObject) && angularErrorObject.iscDatatypeValidator)
				            		return validationResponseObj;
				            	else
				            	var isQty_a_Number = !isNaN(viewValue);
				            	if(!iscCore.isBooleanTrue(isQty_a_Number)){
				            		validationResponseObj.booleanResponse = !isNaN(viewValue);
					            	validationResponseObj.errorMesssage = iscI18n.translate("globals.ERROR_invalid_input");
		            			}
				            	
				            	return validationResponseObj;
				            },
				            
				            
				            /**
					           * @iscdoc method
					           * @viewname store.views.order.cart-details.cart-details
					           * @methodname afterStoreSelection
					           * @description Callback handler function of store selection popup. Sets ship node for pickup lines
					           * @param {Object} data - data sent from override store selection popup.
							   * @param {Object} orderlineModel - orderLine data as JSON object.  
					           */
				            afterStoreSelection: function(data,orderlineModel){
					  			var  modifyFulfillmentOptionsInput =iscOrder.prepareModifyFulfillmentOptionsApiInput(this.model.getCompleteOrderDetails.Order.OrderHeaderKey,orderlineModel.OrderLineKey);
			    	  			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].ShipNode = data.selectedStore.ShipNode;
			    	  			iscMashup.callMashup(this,"viewcart_modifyFulfillmentOptions",modifyFulfillmentOptionsInput,{}).then(this.handleModifyFulfillmentOptionsOutput.bind(this),angular.noop);
				            },
				            
				            /**
							 *@iscdoc uimethod
							 *@viewname store.views.order.cart-details.cart-details
							 *@methodname uiChangeDeliveryMethod
							 *@description onclick handler function for change delivery method icon.It opens fulfillment options popup.
							 *@param {Object} orderlineModel - orderLine data as JSON object.    
						     */	
				            
				            uiChangeDeliveryMethod : function(orderlineModel){
				            	
				            	var that = this;
				            	var changeDeliveryModalInput = {
				            					modalInput: function ()
									 				{
									  				return {	
									  					inputData : {
									  							OrderHeaderKey : that.model.getCompleteOrderDetails.Order.OrderHeaderKey,
									  							orderLine : orderlineModel,
									  							orderPersonInfoShipTo : that.model.getCompleteOrderDetails.Order.PersonInfoShipTo
									  								}
								  						};
					  								}
				            					};
				            	
				            	iscModal.openModal('store.views.common.fulfillment.fulfillmentoptions',changeDeliveryModalInput,{})
				            			 .then(function(callBackData){
				            		that.handleDeliveryMethodChange.call(that,callBackData.data,orderlineModel);
									},angular.noop);
				            	
				            },
				            
				            


				            /**
							 *@iscdoc uimethod
							 *@viewname store.views.order.comments.comments
							 *@methodname uiPostComment
							 *@description Handles the scenario when 'post comment' button is clicked on the modal.
							 */
							uiAddComments : function(){
								
								var that = this;
								var orderModel = angular.copy(this.model.getCompleteOrderDetails);   
								var commentsModalInput = {
						       			modalInput: function(){
						       				return orderModel;
						       			}
						       	};
								
								iscModal.openModal('store.views.order.comments.comments',commentsModalInput,{})
		            			 .then(function(callBackData){
		            				 that.model.getCompleteOrderDetails.Order.Notes.NumberOfNotes = callBackData.NoteList.TotalNumberOfRecords;
							},angular.noop);
								
								
							
								
								
							},	
				            /**
					           * @iscdoc method
					           * @viewname store.views.order.cart-details.cart-details
					           * @methodname handleDeliveryMethodChange
					           * @description Callback handler function of fulfillmentoptions popup. 
					           * @param {Object} data - data sent from override fulfillmentoptions popup.
							   * @param {Object} orderlineModel - orderLine data as JSON object.  
					           */
				            handleDeliveryMethodChange : function(data,orderlineModel){
				            	
				            	var newDelMethod = data.newDelMethod
					  			var  modifyFulfillmentOptionsInput =iscOrder.prepareModifyFulfillmentOptionsApiInput(this.model.getCompleteOrderDetails.Order.OrderHeaderKey,orderlineModel.OrderLineKey);
			    	  			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].DeliveryMethod = newDelMethod;
			    	  			
				            	if(newDelMethod == "PICK"){
				            		modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].ShipNode = data.selectedStore.ShipNode;
				            		modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderLineTranQuantity = {};
				            		modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderLineTranQuantity.SettledQuantity = "0";
				            		if(!iscCore.isVoid(data.selectedStore.Availability.AvailableDate)){
				            			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].ReqShipDate = data.selectedStore.Availability.AvailableDate;
				    	  			}
				            	}else if (newDelMethod == "SHP"){
				            		/* If charge shipping lines at store rule is not turned on, set SettledQuantity as 0 */
				            		if(this.model.getRuleDetails_ChargeShipLines && this.model.getRuleDetails_ChargeShipLines.Rules && this.model.getRuleDetails_ChargeShipLines.Rules.RuleSetValue == 'N'){
		            					modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderLineTranQuantity = {};
				            			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderLineTranQuantity.SettledQuantity = "0";
		            				}
				            		if(!iscCore.isVoid(data.EarliestShipDate)){
				            		modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].EarliestShipDate = data.EarliestShipDate;
				            		}
				            		
				            		if(!iscCore.isVoid(data.personInfoShipTo)){
				            		modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].PersonInfoShipTo = {};
				            	
				            		
				            		/* check if the order has ship to address*/
				    	  			if((!this.model.getCompleteOrderDetails.Order.PersonInfoShipTo) || (data.personInfoShipTo.IsDefaultShippingAddress === 'Y')){
				    	  				modifyFulfillmentOptionsInput.Order.PersonInfoShipTo  =  data.personInfoShipTo;
				    	  				if(!this.model.getCompleteOrderDetails.Order.BillToID){
				    	  				modifyFulfillmentOptionsInput.Order.CustomerFirstName =  data.personInfoShipTo.FirstName;
				    	      	        modifyFulfillmentOptionsInput.Order.CustomerLastName  =  data.personInfoShipTo.LastName;
				    	      	        modifyFulfillmentOptionsInput.Order.CustomerEMailID   =  data.personInfoShipTo.EMailID;
				    	      	        modifyFulfillmentOptionsInput.Order.CustomerZipCode   =  data.personInfoShipTo.ZipCode;
				    	  				}
				    	  			}
				    	  			
				            		 delete data.personInfoShipTo.IsDefaultShippingAddress;
				       			     delete data.personInfoShipTo.IsDefaultBillingAddress;
				      			    
				    	  			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].PersonInfoShipTo = data.personInfoShipTo;
				    	  			
				    	  			/* check if the order has ship to address*/
				    	  			if(!this.model.getCompleteOrderDetails.Order.PersonInfoShipTo){
				    	  				modifyFulfillmentOptionsInput.Order.PersonInfoShipTo  =  data.personInfoShipTo;
				    	  			}
				            		}
				            		if(!iscCore.isVoid(data.selectedCarrier)){
				            			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].CarrierServiceCode =data.selectedCarrier.CarrierServiceCode;
				            			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderDates = {};
				            			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderDates.OrderDate = [];
					            		modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderDates.OrderDate[0] = {};
					            		modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderDates.OrderDate[0].CommittedDate = data.selectedCarrier.DeliveryStartDate;
					            		modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderDates.OrderDate[0].DateTypeId ="MIN_DELIVERY";
					            		modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderDates.OrderDate[1] = {};
					            		modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderDates.OrderDate[1].CommittedDate = data.selectedCarrier.DeliveryEndDate;
					            		modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderDates.OrderDate[1].DateTypeId ="MAX_DELIVERY";
				            		}
				            		
				            	}else if(newDelMethod == "CARRY"){
				            		modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].ShipNode = iscAppContext.getFromContext("storeName");
				            		if(orderlineModel.OrderLineTranQuantity.SettledQuantity === "0"){
				            			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderLineTranQuantity = {};
				            			modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderLineTranQuantity.SettledQuantity = "0";
				            		}
				            	}
				            	/* Stamp MarkForKey @ OrderLine If PersonInfoMarkFor element is present*/
				            	if((!iscCore.isVoid(orderlineModel.PersonInfoMarkFor)) && (!iscCore.isVoid(orderlineModel.PersonInfoMarkFor.PersonInfoKey))){
				            		modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].MarkForKey = orderlineModel.PersonInfoMarkFor.PersonInfoKey;
				            	}
				            	iscMashup.callMashup(this,"viewcart_modifyFulfillmentOptions",modifyFulfillmentOptionsInput,{}).then(this.handleModifyFulfillmentOptionsOutput.bind(this),angular.noop);
				            }
				    });
			}
		]);
