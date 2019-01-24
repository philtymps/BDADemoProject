


scDefine(["scbase/loader!dojo/_base/declare","scbase/loader!dojo/_base/kernel","scbase/loader!dojo/text","scbase/loader!extn/order/details/OrderSummaryLinesExtn","scbase/loader!sc/plat/dojo/controller/ExtnServerDataController"]
 , function(			 
			    _dojodeclare
			 ,
			    _dojokernel
			 ,
			    _dojotext
			 ,
			    _extnOrderSummaryLinesExtn
			 ,
			    _scExtnServerDataController
){

return _dojodeclare("extn.order.details.OrderSummaryLinesExtnBehaviorController", 
				[_scExtnServerDataController], {

			
			 screenId : 			'extn.order.details.OrderSummaryLinesExtn'

			
			
			
			
			
						,

			
			
			 mashupRefs : 	[
	 		{
		 mashupRefId : 			'extn_OrderSummaryLins_getCompleteOrderDetails'
,
		 mashupId : 			'extn_OrderSummaryLins_getCompleteOrderDetails'
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

	]

}
);
});

