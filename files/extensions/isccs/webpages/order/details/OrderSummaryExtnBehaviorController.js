


scDefine(["scbase/loader!dojo/_base/declare","scbase/loader!dojo/_base/kernel","scbase/loader!dojo/text","scbase/loader!extn/order/details/OrderSummaryExtn","scbase/loader!sc/plat/dojo/controller/ExtnServerDataController"]
 , function(			 
			    _dojodeclare
			 ,
			    _dojokernel
			 ,
			    _dojotext
			 ,
			    _extnOrderSummaryExtn
			 ,
			    _scExtnServerDataController
){

return _dojodeclare("extn.order.details.OrderSummaryExtnBehaviorController", 
				[_scExtnServerDataController], {

			
			 screenId : 			'extn.order.details.OrderSummaryExtn'

			
			
			
			
			
						,

			
			
			 mashupRefs : 	[
	 		{
		 mashupRefId : 			'getCompleteOrderDetailsBehavior'
,
		 mashupId : 			'orderSummary_getCompleteOrderDetails'
,
		 extnType : 			'ADD'

	}
,
	 		{
		 mashupRefId : 			'extn_getOrderList'
,
		 mashupId : 			'extn_getOrderList'
,
		 extnType : 			'ADD'

	}
,
	 		{
		 mashupRefId : 			'extn_OrderSummaryLins_getOrderList'
,
		 mashupId : 			'extn_OrderSummaryLins_getOrderList'
,
		 extnType : 			'ADD'

	}
,
	 		{
		 mashupRefId : 			'extn_RetryValidationService'
,
		 mashupId : 			'extn_RetryValidationService'
,
		 extnType : 			'ADD'

	}
,
	 		{
		 mashupRefId : 			'extn_UpdateServiceOrderStatus'
,
		 mashupId : 			'extn_UpdateServiceOrderStatus'
,
		 extnType : 			'ADD'

	}
,
	 		{
		 mashupRefId : 			'extn_GetOrderHierarchyBehavior'
,
		 mashupId : 			'extn_GetOrderHierarchyBehavior'
,
		 extnType : 			'ADD'

	}
,
	 		{
		 mashupRefId : 			'extn_OHSCChangeOrder'
,
		 mashupId : 			'OLCChangeOrder'
,
		 extnType : 			'ADD'

	}

	]

}
);
});

