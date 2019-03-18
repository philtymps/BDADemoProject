
scDefine(["dojo/text!./templates/OrderLineSummaryExtn.html","scbase/loader!dojo/_base/declare","scbase/loader!dojo/_base/kernel","scbase/loader!dojo/_base/lang","scbase/loader!dojo/text","scbase/loader!gridx/Grid","scbase/loader!gridx/modules/ColumnResizer","scbase/loader!gridx/modules/ColumnWidth","scbase/loader!gridx/modules/HLayout","scbase/loader!gridx/modules/select/Row","scbase/loader!idx/layout/TitlePane","scbase/loader!isccs/common/pricing/OrderLinePriceSummaryInitController","scbase/loader!sc/plat","scbase/loader!sc/plat/dojo/binding/CurrencyDataBinder","scbase/loader!sc/plat/dojo/binding/GridxDataBinder","scbase/loader!sc/plat/dojo/utils/BaseUtils","scbase/loader!sc/plat/dojo/widgets/ControllerWidget","scbase/loader!sc/plat/dojo/widgets/DataLabel","scbase/loader!sc/plat/dojo/widgets/IdentifierControllerWidget"]
 , function(			 
			    templateText
			 ,
			    _dojodeclare
			 ,
			    _dojokernel
			 ,
			    _dojolang
			 ,
			    _dojotext
			 ,
			    _gridxGrid
			 ,
			    _gridxColumnResizer
			 ,
			    _gridxColumnWidth
			 ,
			    _gridxHLayout
			 ,
			    _gridxRow
			 ,
			    _idxTitlePane
			 ,
			    _isccsOrderLinePriceSummaryInitController
			 ,
			    _scplat
			 ,
			    _scCurrencyDataBinder
			 ,
			    _scGridxDataBinder
			 ,
			    _scBaseUtils
			 ,
			    _scControllerWidget
			 ,
			    _scDataLabel
			 ,
			    _scIdentifierControllerWidget
){
return _dojodeclare("extn.order.details.OrderLineSummaryExtnUI",
				[], {
			templateString: templateText
	
	
	
	
	
	
	
					,	
	namespaces : {
		targetBindingNamespaces :
		[
			{
	  scExtensibilityArrayItemId: 'extn_TargetNamespaces_3'
						,
	  description: "Contains Input to ChangeOrder"
						,
	  value: 'extn_ChangeOrderInput'
						
			}
			
		],
		sourceBindingNamespaces :
		[
		]
	}

	
	,
	hotKeys: [ 
	]

,events : [
	]

,subscribers : {

local : [

{
	  eventId: 'extn_OLSCbtnSave_onClick'

,	  sequence: '51'

,	  description: 'Calls changeseOrder API'



,handler : {
methodName : "invokeChangeOrder"

, description :  "Calls changeseOrder API to save changes"  
}
}
,
{
	  eventId: 'extn_OLSCbtnSave_onKeyUp'

,	  sequence: '51'

,	  description: 'Calls changeseOrder API'



,handler : {
methodName : "invokeChangeOrder"

, description :  "Calls changeseOrder API to save changes"  
}
}

]
}

});
});

