scDefine(["scbase/loader!dojo/_base/declare","scbase/loader!extn/home/HomeExtnUI","scbase/loader!sc/plat/dojo/utils/BaseUtils","scbase/loader!isccs/utils/UIUtils","scbase/loader!sc/plat/dojo/utils/EventUtils","scbase/loader!sc/plat/dojo/utils/ModelUtils","scbase/loader!isccs/utils/BaseTemplateUtils"]
,
function(			 
			    _dojodeclare,
			    _extnHomeExtnUI,
			    _scBaseUtils,
			    _isccsUIUtils,
			    _scEventUtils,
			    _scModelUtils,
			    _isccsBaseTemplateUtils	
){ 
	return _dojodeclare("extn.home.HomeExtn", [_extnHomeExtnUI],{
	// custom code here
	CSGOpenOrderSummary: function(event,bEvent,ctrl,args){
		//var orderListInput = {Order:{}};
		var OrderNo = window.CSGSSOOrderNo;
		if (!_scBaseUtils.equals(OrderNo,"null") && !_scBaseUtils.isVoid(OrderNo)){
			var orderListInput = null;
			orderListInput = _scModelUtils.createNewModelObjectWithRootKey("Order");
			_scModelUtils.setStringValueAtModelPath("Order.OrderNo",OrderNo,orderListInput);
			//orderListInput.Order.OrderNo=OrderNo;
			//orderListInput.Order.DocumentType="0001";
			//orderListInput.Order.EnterpriseCode="BellCanada";
			//orderListInput.Order.DraftOrderFlag="N";
			_isccsUIUtils.callApi(this,orderListInput,"extn_OSCgetOrderList",null);
		}


		//var editorInput = {Order:{}};
		
		//editorInput.Order.OrderHeaderKey="201501070559001086373";
		//var OrderNo = window.CSGSSOOrderNo;
		//if (!_scBaseUtils.isVoid(OrderNo)){
		//	editorInput.Order.OrderNo=OrderNo;
		//	editorInput.Order.DocumentType="0001";
		//	editorInput.Order.EnterpriseCode="BellCanada";
		//	editorInput.Order.DraftOrderFlag="N";
			//var editorInputMdl = JSON.parse(editorInput);
		//	_isccsUIUtils.openWizardInEditor("isccs.order.wizards.orderSummary.OrderSummaryWizard",editorInput,"isccs.editors.OrderEditor",this);
		//}
	},

	
	handleMashupCompletion: function(mashupContext, mashupRefObj, mashupRefList, inputData, hasError, data) {
		_isccsBaseTemplateUtils.handleMashupCompletion( mashupContext, mashupRefObj, mashupRefList, inputData, hasError, data, this);
	},

	handleMashupOutput: function(mashupRefId, modelOutput, mashupInput, mashupContext, applySetModel) {
		if (_scBaseUtils.equals(mashupRefId, "extn_OSCgetOrderList")) {
			var orderModel = null;
			orderModel = _scModelUtils.getModelObjectFromPath("OrderList.Order", modelOutput);
			var editorInput = null;
            		editorInput = _scModelUtils.createNewModelObjectWithRootKey("Order");
			_scModelUtils.setStringValueAtModelPath("Order.OrderHeaderKey",modelOutput.OrderList.LastOrderHeaderKey, editorInput);
			_scModelUtils.setStringValueAtModelPath("Order.DraftOrderFlag", "N", editorInput);
			_isccsUIUtils.openWizardInEditor("isccs.order.wizards.orderSummary.OrderSummaryWizard",editorInput,"isccs.editors.OrderEditor",this);
		}
	}



});
});

