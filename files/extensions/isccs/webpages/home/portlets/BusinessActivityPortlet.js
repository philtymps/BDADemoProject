/*
 * Licensed Materials - Property of IBM
 * IBM Call Center for Commerce (5725-P82)
 * (C) Copyright IBM Corp. 2013 All Rights Reserved.
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */
scDefine(["scbase/loader!dojo/_base/declare", "scbase/loader!extn/home/portlets/BusinessActivityPortletUI", "scbase/loader!isccs/utils/UIUtils", "scbase/loader!sc/plat/dojo/utils/BaseUtils", "scbase/loader!sc/plat/dojo/utils/ModelUtils", "scbase/loader!sc/plat/dojo/utils/WidgetUtils"], function(
_dojodeclare, _isccsBusinessActivityPortletUI, _isccsUIUtils, _scBaseUtils, _scModelUtils, _scWidgetUtils) {
    return _dojodeclare("extn.home.portlets.BusinessActivityPortlet", [_isccsBusinessActivityPortletUI], {
        // custom code here
        getNextAlert: function() {
            _isccsUIUtils.callApi(
            this, _scModelUtils.createNewModelObjectWithRootKey("Inbox"), "getNextAlert", null);
        },
        initScreen: function(
        event, bEvent, ctrl, args) {
            this.reloadContent(
            event, bEvent, ctrl, args);
        },
        openReport: function(event, bEvent, ctrl, args){
        	//screen, screenId, screenInput, constructorArgs, editorId
        	_isccsUIUtils.openNewScreenInEditor(this, "extn.businessActivity.BusinessActivity", {"type":"State"}, {}, "extn.editors.BusinessActivityEditor");
        },
        openReport2: function(event, bEvent, ctrl, args){
        	//screen, screenId, screenInput, constructorArgs, editorId
        	_isccsUIUtils.openNewScreenInEditor(this, "extn.businessActivity.BusinessActivity2", {"type":"Report2"}, {}, "extn.editors.BusinessActivityEditor");
        },
        reloadContent: function(
        event, bEvent, ctrl, args) {
            var options = null;
            var manualRefresh = false;
            manualRefresh = _scBaseUtils.getAttributeValue("ManualRefresh", false, args);
            if (!(
            manualRefresh)) {
                options = {};
                _scBaseUtils.setAttributeValue("skipEventBlocking", true, options);
            }
        }
    });
});
