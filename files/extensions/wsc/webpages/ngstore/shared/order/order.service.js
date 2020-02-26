/*******************************************************************************
 * IBM Confidential
 * OCO Source Materials
 * IBM Sterling Order Management Store (5725-D10), IBM Order Management (5737-D18), IBM Store Engagement (5737D58)
 * (C) Copyright IBM Corp.  2015, 2017 All Rights Reserved.
 * The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
 ******************************************************************************/

/**
 * @ngdoc service
 * @name iscOrder
 * 
 * 
 * @description 
 * Handles all the common tasks related to order. 
 * 
 */
angular.module('isc.shared').factory('iscOrder', ['$http','$filter','$q','iscAppInfo','iscModal','iscI18n','iscMashup','iscState','iscObjectUtility','iscAppContext','$rootScope','iscResourcePermission',
 function($http,$filter,$q,iscAppInfo,iscModal,iscI18n,iscMashup, iscState,iscObjectUtility,iscAppContext,$rootScope,iscResourcePermission) {
  
   var orderService = {};	 
   
   /**
	 * @ngdoc method
	 * @name iscOrder#updateOrderLines
	 * @description Updates OrderLineList model with getCompleteOrderLineList api output
	 * @param apiOutput {object} Output model object containing getCompleteOrderLineList output
	 * @param orderLineList {object} model which needs to be updated with apiOutput
	 */
   
   orderService.updateOrderLines = function(apiOutput,orderLineList,lotNumber){
     	
	        var orderLines = []; 
	        orderLines = orderLineList.OrderLineList.OrderLine;
	        
			if(!iscCore.isVoid(apiOutput.Order.OrderLines) && !iscCore.isVoid(apiOutput.Order.OrderLines.OrderLine)){
				
				var modifiedLines = [];
				var matchingLineFound = false;
				modifiedLines = apiOutput.Order.OrderLines.OrderLine;
				
     		    for(var j=0; j<modifiedLines.length; j++){
     		    	matchingLineFound = false;
					for(var k= 0; k<orderLines.length ; k++){
						if(orderLines[k].OrderLineKey === modifiedLines[j].OrderLineKey){
							 orderLines[k] =modifiedLines[j];
							 orderLines[k].OrderLineTranQuantity.OrderedQty = $filter('number')(orderLines[k].OrderLineTranQuantity.OrderedQty);    
			 				 orderLines[k].OrderedQty = $filter('number')(orderLines[k].OrderedQty);
							 if(!iscCore.isVoid(orderLines[k].LineSeqNo)) {
								orderLines[k].LineSeqNumber = orderLines[k].LineSeqNo.split('.')[0];
								orderLines[k].DisplayStatus = this.getLineDisplayStatus(orderLines[k]);
							 }
							 matchingLineFound = true;
							 break;
						}
					}
					
					if(!matchingLineFound){
						orderLines[orderLines.length] = modifiedLines[j];
						orderLines[orderLines.length-1].OrderLineTranQuantity.OrderedQty = $filter('number')(orderLines[orderLines.length-1].OrderLineTranQuantity.OrderedQty);
		 				orderLines[orderLines.length-1].OrderedQty = $filter('number')(orderLines[orderLines.length-1].OrderedQty);
		 				if(lotNumber){
		 					orderLines[orderLines.length-1]["InventoryTagAttributes"] = {"LotNumber": lotNumber};
						}
						if(!iscCore.isVoid(orderLines[orderLines.length-1].LineSeqNo)) {
							orderLines[orderLines.length-1].LineSeqNumber = orderLines[orderLines.length-1].LineSeqNo.split('.')[0];
							orderLines[orderLines.length-1].DisplayStatus = this.getLineDisplayStatus(orderLines[orderLines.length-1]);
		 				}
					}
     	 	   }
			}
			
			if(!iscCore.isVoid(apiOutput.Order.DeletedOrderLines)){
				var deletedLines = [];
				deletedLines = apiOutput.Order.DeletedOrderLines.OrderLine;
        		    for(var j=0; j<deletedLines.length; j++){
					
					for(var k= 0; k<orderLines.length ; k++){
						if(orderLines[k].OrderLineKey === deletedLines[j].OrderLineKey){
							orderLines.splice(k,1);
							 break;
						}
					}
        	 	   }
			}
           
		   orderLineList.OrderLineList.TotalNumberOfRecords=orderLines.length;
		   
     };
     
     /**
 	 * @ngdoc method
 	 * @name iscOrder#updateOrderHeaderDetails
 	 * @description Updates order model with getCompleteOrderDetails api output
 	 * @param apiOutput {object} Output model object containing getCompleteOrderDetails output
 	 * @param order {object} model which needs to be updated with apiOutput
 	 * @returns {Object} model containing updated order header details.
 	 */
    
     
     orderService.updateOrderHeaderDetails = function(apiOutput,order){
    	
    	var orderDetails = angular.copy(apiOutput);
    	//preserving OrderLines element in the order header
     	if(!iscCore.isVoid(orderDetails.Order.OrderLines) && !iscCore.isVoid(orderDetails.Order.OrderLines.OrderLine)){
     		delete orderDetails.Order.OrderLines["OrderLine"];
     	}
     	
     	return orderDetails;
     }; 
     
     /**
  	 * @ngdoc method
  	 * @name iscOrder#getGiftInstructionForOrderLine
  	 * @description Returns gift instruction of a order line.
  	 * @param {Object} orderlineModel - orderLine data as JSON object. 
  	 * @returns {Object} object containing gift instruction.
  	 */
     
     orderService.getGiftInstructionForOrderLine = function(orderlineModel){
     	
     	  var instructionList = orderlineModel.Instructions.Instruction;
		  var giftInstruction = null;
			
            if(!iscCore.isVoid(instructionList)){
			for(var k= 0; k<instructionList.length ; k++){
				if(instructionList[k].InstructionType == 'Gift'){
					giftInstruction = instructionList[k];
					break;
				}
			}
            } 	
			return giftInstruction;
     };
     
     
     /**
   	 * @ngdoc method
   	 * @name iscOrder#checkIfAnyOrderlineIsGift
   	 * @description Checks if any of the order lines is a gift.
   	 * @param {Object} orderLineList - list of order lines. 
   	 * @returns {String} Y if any of the order line is gift.
   	 */
     
     orderService.checkIfAnyOrderlineIsGift = function(orderLineList){
     	
    	  var orderLines = []; 
	      orderLines = orderLineList.OrderLineList.OrderLine;
				var isAnyLineGift = false; 
				for(var k= 0; k<orderLines.length ; k++){
					if(orderLines[k].GiftFlag === 'Y'){
						isAnyLineGift = true;
						break;
					}
				}
				return isAnyLineGift;
     };
     
     /**
    	 * @ngdoc method
    	 * @name iscOrder#addPriceOverrideDefaultNote
    	 * @description Adds Order line note to the api input of modifyFulfillmentOptions when unit price of a order line is overriden .
    	 * @param {Object} apiInput - modifyFulfillmentOptions api input. 
    	 * @param {String} priceOverrideReason - reason for overriding the price. 
    	 */
     orderService.addPriceOverrideDefaultNote = function(apiInput,priceOverrideReason){
     	var noteText = iscI18n.translate('addItems.MSG_PriceOverrideReason',{overrideReason: priceOverrideReason});
     	apiInput.Order.OrderLines.OrderLine[0].Notes = [];
     	apiInput.Order.OrderLines.OrderLine[0].Notes[0] = {};
     	apiInput.Order.OrderLines.OrderLine[0].Notes[0].Note = {
     			'NoteText': noteText,
     			'ReasonCode': 'YCD_NEW_ITEM_INFO'
     	};
     };
     
     /**
    	 * @ngdoc method
    	 * @name iscOrder#checkIfOrderHasShipLines
    	 * @description Checks if any of the order lines is a shipping line.
    	 * @param {Object} orderLineList - list of order lines. 
    	 * @returns {Boolean} true if any of the order lines is a shipping line.
    	 */
     
     orderService.checkIfOrderHasShipLines = function(orderLineList){
    	 
    	var isShippingLinePresent = false; 
    	
    	if(!iscCore.isVoid(orderLineList)){
    		for(var i= 0; i<orderLineList.length; i++){
    			if(orderLineList[i].DeliveryMethod === 'SHP'){
    					isShippingLinePresent = true;
    					break;
    				 }
    			}
    	}
        
        return isShippingLinePresent;  
		};
      
		/**
    	 * @ngdoc method
    	 * @name iscOrder#prepareModifyFulfillmentOptionsApiInput
    	 * @description Prepares modifyFulfillmentOptions api input for a OrderLine
    	 * @param {String} orderHeaderKey - Unique identifier of a Order.
    	 * @param {String} orderLineKey - Unique identifier of a Order line. 
    	 * @returns {Object} modifyFulfillmentOptionsInput -modifyFulfillmentOptions api input.
    	 */
		
     orderService.prepareModifyFulfillmentOptionsApiInput = function(orderHeaderKey,orderLineKey){
        var  modifyFulfillmentOptionsInput = {};
		modifyFulfillmentOptionsInput.Order = {};
		modifyFulfillmentOptionsInput.Order.OrderHeaderKey=orderHeaderKey;
		modifyFulfillmentOptionsInput.Order.OrderLines = {};
		modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine = [];
		modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0]={};
		modifyFulfillmentOptionsInput.Order.OrderLines.OrderLine[0].OrderLineKey = orderLineKey;
		return modifyFulfillmentOptionsInput;
     };
     
     
     /**
 	 * @ngdoc method
 	 * @name iscOrder#prepareChangeOrderInputForGift
 	 * @description Prepares changeOrder api input when Order is marked as gift
 	 * @param {Object} giftOptionsData - object containing gift information. 
 	 * @param {String} orderHeaderKey - Unique identifier of a Order.
 	 * @param {Object} orderLineList - list of order lines. 
 	 * @returns {Object} apiInput -changeOrder api input.
 	 */
     
     orderService.prepareChangeOrderInputForGift = function(giftOptionsData,orderHeaderKey,orderLineList){
    	   
	    	var apiInput = {};
	     	apiInput.Order = {};
	     	apiInput.Order.OrderHeaderKey=orderHeaderKey;
	     	apiInput.Order.OrderLines = {};
	     	apiInput.Order.OrderLines.OrderLine = [];
     	
    	   for(var k= 0; k<orderLineList.length ; k++){
    		  apiInput.Order.OrderLines.OrderLine[k] = {};
    		  apiInput.Order.OrderLines.OrderLine[k].OrderLineKey = orderLineList[k].OrderLineKey;
    		  
    		  if((!iscCore.isVoid(giftOptionsData.giftWrapChecked)) && giftOptionsData.giftWrapChecked === "Y"){
          		
          		apiInput.Order.OrderLines.OrderLine[k].GiftWrap = "Y";
          		apiInput.Order.OrderLines.OrderLine[k].GiftFlag = "Y";
          		
          	}else{
          		
          		apiInput.Order.OrderLines.OrderLine[k].GiftWrap = "N";
          		if(giftOptionsData.giftFlag === "Y"){
              		apiInput.Order.OrderLines.OrderLine[k].GiftFlag = "Y";
              	}
          	}
          	
    		 
          	if(!iscCore.isVoid(giftOptionsData.giftMessage)){
          		apiInput.Order.OrderLines.OrderLine[k].Instructions = {};
          		
          		apiInput.Order.OrderLines.OrderLine[k].Instructions.Instruction = [];
          		apiInput.Order.OrderLines.OrderLine[k].Instructions.Instruction[0] = {};
          		apiInput.Order.OrderLines.OrderLine[k].Instructions.Instruction[0].InstructionText=giftOptionsData.giftMessage;
          		apiInput.Order.OrderLines.OrderLine[k].Instructions.Instruction[0].InstructionType='Gift';
          		
          		var noOfInstructions = null;
          		if(orderLineList[k].Instructions){
          			noOfInstructions = parseInt(orderLineList[k].Instructions.NumberOfInstructions);
          		}
          		if(noOfInstructions > 0){
          			var giftInstructionObj =this.getGiftInstructionForOrderLine(orderLineList[k]);
        			var instructionDetailKey = null;
        			if(giftInstructionObj !== null){
        				instructionDetailKey = giftInstructionObj.InstructionDetailKey;
        				apiInput.Order.OrderLines.OrderLine[k].Instructions.Instruction[0].InstructionDetailKey=instructionDetailKey;
        			}
        			
          		}
          
          	}
          	
          	
          	if(!iscCore.isVoid(giftOptionsData.giftRecipient)){
          		apiInput.Order.OrderLines.OrderLine[k].PersonInfoMarkFor = {};
          		apiInput.Order.OrderLines.OrderLine[k].PersonInfoMarkFor.FirstName = giftOptionsData.giftRecipient;
          	}
			}
    	   
    	   return apiInput;
     };
     
     
     /**
  	 * @ngdoc method
  	 * @name iscOrder#prepareChangeOrderInputForGiftRemoval
  	 * @description Prepares changeOrder api input when gift options are removed for a order
  	 * @param {String} orderHeaderKey - Unique identifier of a Order.
  	 * @param {Object} orderLineList - list of order lines. 
  	 * @returns {Object} apiInput -changeOrder api input.
  	 */
     
     orderService.prepareChangeOrderInputForGiftRemoval = function(orderHeaderKey,orderLineList){
       	 
    	var apiInput = {};
     	apiInput.Order = {};
     	apiInput.Order.OrderHeaderKey=orderHeaderKey;
     	apiInput.Order.OrderLines ={};
     	apiInput.Order.OrderLines.OrderLine=[];
     	
     	 for(var k= 0; k<orderLineList.length ; k++){
			 
   		      apiInput.Order.OrderLines.OrderLine[k] = {};
   		      apiInput.Order.OrderLines.OrderLine[k].OrderLineKey = orderLineList[k].OrderLineKey;
         	  apiInput.Order.OrderLines.OrderLine[k].GiftWrap = "N";
         	  apiInput.Order.OrderLines.OrderLine[k].GiftFlag = "N";
   		
         	  var noOfInstructions = parseInt(orderLineList[k].Instructions.NumberOfInstructions);
         		
              if(noOfInstructions > 0){
         			
         		var giftInstructionObj =this.getGiftInstructionForOrderLine(orderLineList[k]);
       			var instructionDetailKey = null;
       			if(giftInstructionObj !== null){
       				instructionDetailKey = giftInstructionObj.InstructionDetailKey;
       				apiInput.Order.OrderLines.OrderLine[k].Instructions = {};
       				apiInput.Order.OrderLines.OrderLine[k].Instructions.Instruction = [];
                 	apiInput.Order.OrderLines.OrderLine[k].Instructions.Instruction[0] = {};
       				apiInput.Order.OrderLines.OrderLine[k].Instructions.Instruction[0].InstructionDetailKey=instructionDetailKey;
       				apiInput.Order.OrderLines.OrderLine[k].Instructions.Instruction[0].Action="REMOVE";
       			}
       			
         		}
        	
			}
     	
     	 return apiInput;
     };
     
     /**
 	 * @ngdoc method
 	 * @name iscOrder#orderHasAnyCarryLines
 	 * @description Checks if any of the order lines is a carry line.
 	 * @param {Object} orderModel -order model containing list of order lines. 
 	 * @returns {Boolean} true if any of the order lines is a carry line.
 	 */
     
	orderService.orderHasAnyCarryLines = function(orderModel){
	if(orderModel !== null && orderModel !== undefined && orderModel.Order && orderModel.Order.OrderLines && orderModel.Order.OrderLines.OrderLine){
		var len = orderModel.Order.OrderLines.OrderLine.length;
		for(var i = 0; i < len; i++){
			var orderLine = orderModel.Order.OrderLines.OrderLine[i];
			var deliveryMethod = orderLine.DeliveryMethod;
			if(deliveryMethod === 'CARRY'){
					return true;
				}
			}
		}
		return false;
	};
	
	/**
 	 * @ngdoc method
 	 * @name iscOrder#orderHasOnlyCarryLines
 	 * @description Checks if all of the order lines is a carry line.
 	 * @param {Object} orderModel -order model containing list of order lines. 
 	 * @returns {Boolean} true if all of the order lines is a carry line.
 	 */
     
	orderService.orderHasOnlyCarryLines = function(orderModel){
		if(orderModel !== null && orderModel !== undefined && orderModel.Order && orderModel.Order.OrderLines && orderModel.Order.OrderLines.OrderLine){
			var len = orderModel.Order.OrderLines.OrderLine.length;
			for(var i = 0; i < len; i++){
				var orderLine = orderModel.Order.OrderLines.OrderLine[i];
				var deliveryMethod = orderLine.DeliveryMethod;
				if(deliveryMethod != 'CARRY'){
					return false;
				}
			}
		}
		return true;
	};
	
	/**
 	 * @ngdoc method
 	 * @name iscOrder#orderHasAnyShippingLines
 	 * @description Checks if any of the order lines is a shipping line.
 	 * @param {Object} orderModel -order model containing list of order lines. 
 	 * @returns {Boolean} true if any of the order lines is a shipping line.
 	 */
	orderService.orderHasAnyShippingLines = function(orderModel){
	if(orderModel !== null && orderModel !== undefined && orderModel.Order && orderModel.Order.OrderLines && orderModel.Order.OrderLines.OrderLine){
		var len = orderModel.Order.OrderLines.OrderLine.length;
		for(var i = 0; i < len; i++){
			var orderLine = orderModel.Order.OrderLines.OrderLine[i];
			var deliveryMethod = orderLine.DeliveryMethod;
			if(deliveryMethod === 'SHP'){
					return true;
				}
			}
		}
		return false;
	};
	
	/**
 	 * @ngdoc method
 	 * @name iscOrder#orderHasAnyPickupLines
 	 * @description Checks if any of the order lines is a pickup line.
 	 * @param {Object} orderModel -order model containing list of order lines. 
 	 * @returns {Boolean} true if any of the order lines is a pickup line.
 	 */
	orderService.orderHasAnyPickupLines = function(orderModel){
	if(orderModel !== null && orderModel !== undefined && orderModel.Order && orderModel.Order.OrderLines && orderModel.Order.OrderLines.OrderLine){
		var len = orderModel.Order.OrderLines.OrderLine.length;
		for(var i = 0; i < len; i++){
			var orderLine = orderModel.Order.OrderLines.OrderLine[i];
			var deliveryMethod = orderLine.DeliveryMethod;
			if(deliveryMethod === 'PICK'){
					return true;
				}
			}
		}
		return false;
	};
	
	
	/**
 	 * @ngdoc method
 	 * @name iscOrder#openOverridePriceModal
 	 * @description Opens the override price pop-up.
	 * @param {Object} orderlineModel - orderLine data as JSON object. 
	 * @param {String} currency - currency for the product price. 
 	 * @param  {Object} ctrl - controller object invoking the util method.
 	 * @param {String} callbackhandler - name of the callbackhandler method that needs to be invoked when override price pop-up is closed.
 	 */
	
	orderService.openOverridePriceModal = function(orderlineModel,currency,ctrl,callbackhandler){
		

      	var ovpInput = {};
      	var callbackhandler = 'onPriceOverride';
      	ovpInput.price = {};
      	ovpInput.price.listPrice =  orderlineModel.LinePriceInfo.ListPrice;
      	ovpInput.price.unitPrice =  orderlineModel.LinePriceInfo.UnitPrice;
      	ovpInput.price.Currency = currency;
      	
      	var priceOverrideInput = {
      			modalInput: function(){
      				return ovpInput;
      			}
      	};
      	
      	iscModal.openModal('store.views.common.priceoverride.priceoverride',priceOverrideInput,{})
				.then(function(callBackData){
      			if(callBackData != null && callBackData.data != null && callBackData.data != undefined){
      				ctrl[callbackhandler].call(ctrl,callBackData.data,orderlineModel);
      			}		            			
 				},
				angular.noop);	
      
      	
	};

	/**
 	 * @ngdoc method
 	 * @name iscOrder#openStoreSearchModal
 	 * @description Opens Store search pop-up in pickup scenarios.
	 * @param {Object} orderlineModel - orderLine data as JSON object. 
 	 * @param  {Object} ctrl - controller object invoking the util method.
 	 * @param {String} callbackhandler - name of the callbackhandler method that needs to be invoked when override price pop-up is closed.
 	 * @param {String} mode - context of where the pop-up is opened. 
 	 */
	
   orderService.openStoreSearchModal = function(orderlineModel,ctrl,callbackhandler,mode){
	   
       	var currentStoreAddress = iscAppContext.getFromContext('storeAddress').ShipNodePersonInfo;
       	var storeSearchModalInput = {
       			modalInput: function(){
       				return {
       				inputData : {callingMode : mode,orderLine : orderlineModel}		
       						};
       								}
       						};
       	
       	iscModal.openModal('store.views.common.storesearch.storesearch',storeSearchModalInput,{})
       			 .then(function(callBackData){
       				ctrl[callbackhandler].call(ctrl,callBackData.data,orderlineModel);
				},angular.noop);
        
   };
   
   /**
	 *@ngdoc method
	 *@name iscOrder#openGiftOptionsForOrderLinePopup
	 *@description Opens Gift options popup for order line.
	 *@param {Object} orderlineModel - orderLine data as JSON object. 
	 *@param  {Object} ctrl - controller object invoking the util method.
 	 *@param {String} callbackhandler - name of the callbackhandler method that needs to be invoked when override price pop-up is closed.
	 */
   
   orderService.openGiftOptionsForOrderLinePopup = function(orderlineModel,ctrl,callbackhandler){
		
   		var giftOptionsInput = null;
   		if(orderlineModel.GiftFlag == "Y"){
                
   			var giftInstructionObj =this.getGiftInstructionForOrderLine(orderlineModel);
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
						ctrl[callbackhandler].call(ctrl,callBackData.data,orderlineModel);
					}
					},
					angular.noop);
   
        
	};
	
	
	/**
	 *@ngdoc method
	 *@name iscOrder#prepareGetItemAvailabilityForStoreApiInput
	 *@description utility to prepare getItemAvailabilityForStore api input.
	 *@param {String} orderHeaderKey - unique identifier for order. 
	 *@param {String} orderlineModel - orderLine data as JSON object. 
	 *@returns {Object} json object. 
	 */
	
	orderService.prepareGetItemAvailabilityForStoreApiInput = function(orderHeaderKey,orderlineModel){
		
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
		
	};
	
	/**
 	 * @ngdoc method
 	 * @name iscOrder#orderHasMixedSettledLines
 	 * @description Checks if order has mixed settled lines, which is some lines settled and some lines not settled.
 	 * @param {Object} orderLineList -order line list model containing list of order lines. 
 	 * @returns {Boolean} true if order has some lines settled and some lines not settled .
 	 */
	orderService.orderHasMixedSettledLines = function(orderLineList){
		var len = orderLineList.length;
		var lineSettlementStatus = [];
		for(var i = 0; i < len; i++){
			var orderLine = orderLineList[i];
			var orderLineTranQty = orderLine.OrderLineTranQuantity;
			var settledQty = orderLineTranQty.SettledQuantity;
			var orderedQty = orderLineTranQty.OrderedQty;
			if(orderedQty == settledQty){
				lineSettlementStatus[i] = "Settled";
			}
			else {
				lineSettlementStatus[i] = "UnSettled";
			}
		}
		for(var j = 0; j < lineSettlementStatus.length; j++){
			if(j > 0 && lineSettlementStatus[j] != lineSettlementStatus[j-1]){
				//return true;
				return false;//Work-around for now, since there is an issue with settling lines using Foundation API
			}
		}
		return false;
	};
	
	/**
 	 * @ngdoc method
 	 * @name iscOrder#viewOrderFromList
 	 * @description Checks if order is a Draft Order or confirmed. Confirmed order are taken to Order summary screen. 
 	 * Draft Orders are taken to Order capture/ Cart details screen if they have permission for modifying the order. If they have permission we invoke changeOrder to modify the SellerOrgCode and Modifyts. 
 	 * @param {Object} order - order model. 
 	 * @returns {Boolean} true if back link needs to be displayed in Order Summary screen for Confirmed orders.
 	 */
	orderService.viewOrderFromList = function(order,showBackLinkInOrderSummary){
		if("Y" == order.DraftOrderFlag) {
			 if(iscResourcePermission.hasPermission("WSC000031")){
				 orderService.modifyDraftOrder({Order:order});
			 }else {
				 iscModal.showErrorMessage(iscI18n.translate('orderList.MSG_NoPermissionOrderCapture'));
			 }
		} else if("N" == order.DraftOrderFlag) {
			
			var orderModel = {};
			orderModel.Order = {};
			orderModel.Order.OrderHeaderKey = order.OrderHeaderKey;
			orderModel.Order.EnterpriseCode = order.EnterpriseCode;
			orderModel.Order.SellerOrganizationCode = order.SellerOrganizationCode;
			orderModel.Order.DocumentType = order.DocumentType;
			
			if(showBackLinkInOrderSummary) {
				iscState.goToState('ordersummary',{orderInput:orderModel,showBackLink:true},{});
			} else {
				iscState.goToState('ordersummary',{orderInput:orderModel,showBackLink:false},{});
			}
			
		}
	};
	
	/**
 	 * @ngdoc method
 	 * @name iscOrder#modifyDraftOrder
 	 * @description This method invokes changeOrder API to modify the SellerOrgCode to CurrentStore and Modifyts to Current timestamp so that coupons are re-evaluated.
 	 * @param {Object} order - order model. 
 	 */
	orderService.modifyDraftOrder = function(order) {

		var currentDate = new Date();
		currentDate = new Date(currentDate.setDate(currentDate.getDate() - 1));
		
		var currentStore = iscAppContext.getFromContext("storeName");
		
		// 510367 - (currentStore != order.Order.SellerOrganizationCode || iscObjectUtility.compareDates(order.Order.Modifyts, currentDate, "LT", true)
		if(!iscCore.isVoid(order)) {
			
			var orderModel = {};
			orderModel.Order = {};
			orderModel.Order.OrderHeaderKey = order.Order.OrderHeaderKey;
			orderModel.Order.SellerOrganizationCode = order.Order.SellerOrganizationCode;
			
			iscMashup.callSimpleMashup(this,'shared.lookup.modifyDraftOrder',orderModel,{}).then(function(response) {
				   
				var apiOutput = iscMashup.getSimpleMashupOutput(response,"shared.lookup.modifyDraftOrder");
				if(!iscCore.isVoid(apiOutput)) {
					iscState.goToState('ordercapture',{action:'QUICKCHECKOUT',orderModel:apiOutput},{});
				} else {
					console.error("ERROR: changeOrder failed !");
				}
				   
			   },angular.noop);
			   
		} else {
			iscState.goToState('ordercapture',{action:'QUICKCHECKOUT',orderModel:order},{});
		}
		
	};

	/**
 	 * @ngdoc method
 	 * @name iscOrder#addNote
 	 * @description Creates a note element and returns the same.
 	 * @param {Object} noteText - note text to be added in note.
	 * @param {Object} reasonCode - reasonCode for note. 
 	 * @returns {Object} Notes element created. 
 	 */
	orderService.addNote = function(noteText,reasonCode){
		var notes = {};
		notes.Note = {
			'NoteText':noteText,
			'ReasonCode':reasonCode,
			'ContactUser':iscAppContext.getFromContext('currentLoginID')
		}
		return notes;
	};
	
	/**
 	 * @ngdoc method
 	 * @name iscOrder#getCssClassByOrderStatus
 	 * @description Gets the css color class based on order status
 	 * @param {Object} order - order model. 
 	 * @returns {Object} String containing Css color class based on order status 
 	 */
	orderService.getCssClassByOrderStatus = function(orderStatus) {
		
		var cssClass = "additional";
		
		if(!iscCore.isVoid(orderStatus)) {
			
			if(orderStatus.match("^1100.7777") || orderStatus.match("^3700")) {
				cssClass = "completed";
			} else if(orderStatus.match("^1000") || orderStatus.match("^1100")) {
				cssClass = "initial";
			} else if(orderStatus.match("^9000")) {
				cssClass = "halt";
			} else if(orderStatus > "1140" &&  orderStatus <= "3350.10.10") {
				cssClass = "inprogress";
			} 
		}
		
		return cssClass;
		
	};
	
	/**
 	 * @ngdoc method
 	 * @name iscOrder#getDisplayStatus
 	 * @description Gets the Order Display Status
 	 * @param {Object} order - order model. 
 	 * @returns {Object} String containing order status 
 	 */
	orderService.getDisplayStatus = function(order){
	   	 if(order.MultipleStatusesExist == 'Y'){
	   		 return iscI18n.translate('globals.LABEL_PartialStatus',{status:order.MaxOrderStatusDesc});
	   	 }else{
	   		 return order.Status;
	   	 }
    };
    
    /**
 	 * @ngdoc method
 	 * @name iscOrder#getLineDisplayStatus
 	 * @description Gets the OrderLine Display Status
 	 * @param {Object} order - orderline model. 
 	 * @returns {Object} String containing orderline status 
 	 */
    orderService.getLineDisplayStatus = function(orderLine){
	   	 if(orderLine.MultipleStatusesExist == 'Y'){
	   		 return iscI18n.translate('globals.LABEL_PartialStatus',{status:orderLine.MaxLineStatusDesc});
	   	 }else{
	   		 return orderLine.Status;
	   	 }
   };
   
   /**
	 * @ngdoc method
	 * @name iscOrder#getOrderAge
	 * @description Gets the OrderLine Display Status
	 * @param {Object} String - order date. 
	 * @returns {Object} String containing orderline status 
	 */
   
   orderService.getOrderAge = function(toDate){
		var fromDate = new Date();
		toDate = new Date(toDate);
		/*getTime() gives date in milliseconds.Hence dividing by no. of ms in a day to get the number of days.*/
		var dateDiff = Math.floor(fromDate.getTime() - toDate.getTime())/(24 * 60 * 60 * 1000);
		return dateDiff;
	};
	
	
   /**
	 * @ngdoc method
	 * @name iscOrder#stampDisplayStatusOnOrderList
	 * @description Stamps Order Display Status on OrderList
	 * @param {Object} order - orderlist model. 
	 * @returns {Object} Model containing orderlist updated with order display status 
	 */
   orderService.stampDisplayStatusOnOrderList = function(orderList) {
	 
	   for(var i = 0;i < orderList.OrderList.Order.length;i++) {
		   var order = orderList.OrderList.Order[i];
		   order.DisplayStatus = orderService.getDisplayStatus(order);
	   }
	   
	   return orderList;
	   
   };
    
   return orderService;
 }]);



