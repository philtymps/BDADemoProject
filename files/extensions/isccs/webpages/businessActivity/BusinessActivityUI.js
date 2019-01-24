/*
 * Licensed Materials - Property of IBM
 * IBM Call Center for Commerce (5725-P82)
 * (C) Copyright IBM Corp. 2013 All Rights Reserved.
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */
scDefine(["dojo/text!./templates/BusinessActivity.html", "scbase/loader!dijit/form/Button", "scbase/loader!dojo/_base/declare", "scbase/loader!dojo/_base/kernel", "scbase/loader!dojo/_base/lang", "scbase/loader!dojo/text", "scbase/loader!idx/form/DateTextBox", "scbase/loader!idx/form/FilteringSelect", "scbase/loader!idx/form/RadioButtonSet", "scbase/loader!idx/form/TextBox", "scbase/loader!idx/layout/ContentPane", "scbase/loader!idx/layout/TitlePane", "scbase/loader!isccs/utils/BaseTemplateUtils", "scbase/loader!isccs/utils/EventUtils", "scbase/loader!isccs/utils/ModelUtils", "scbase/loader!isccs/utils/OrderUtils", "scbase/loader!isccs/utils/SearchUtils", "scbase/loader!isccs/utils/UIUtils", "scbase/loader!isccs/utils/WidgetUtils", "scbase/loader!sc/plat", "scbase/loader!sc/plat/dojo/binding/ButtonDataBinder", "scbase/loader!sc/plat/dojo/binding/ComboDataBinder", "scbase/loader!sc/plat/dojo/binding/DateDataBinder", "scbase/loader!sc/plat/dojo/binding/RadioSetDataBinder", "scbase/loader!sc/plat/dojo/binding/SimpleDataBinder", "scbase/loader!sc/plat/dojo/layout/AdvancedTableLayout", "scbase/loader!sc/plat/dojo/utils/BaseUtils", "scbase/loader!sc/plat/dojo/utils/EventUtils", "scbase/loader!sc/plat/dojo/utils/ModelUtils", "scbase/loader!sc/plat/dojo/utils/ScreenUtils", "scbase/loader!sc/plat/dojo/utils/WidgetUtils", "scbase/loader!sc/plat/dojo/widgets/ControllerWidget", "scbase/loader!sc/plat/dojo/widgets/IdentifierControllerWidget", "scbase/loader!sc/plat/dojo/widgets/Screen"], function(
templateText, _dijitButton, _dojodeclare, _dojokernel, _dojolang, _dojotext, _idxDateTextBox, _idxFilteringSelect, _idxRadioButtonSet, _idxTextBox, _idxContentPane, _idxTitlePane, _isccsBaseTemplateUtils, _isccsEventUtils, _isccsModelUtils, _isccsOrderUtils, _isccsSearchUtils, _isccsUIUtils, _isccsWidgetUtils, _scplat, _scButtonDataBinder, _scComboDataBinder, _scDateDataBinder, _scRadioSetDataBinder, _scSimpleDataBinder, _scAdvancedTableLayout, _scBaseUtils, _scEventUtils, _scModelUtils, _scScreenUtils, _scWidgetUtils, _scControllerWidget, _scIdentifierControllerWidget, _scScreen) {
    return _dojodeclare("extn.businessActivity.BusinessActivityUI", [_scScreen], {
        templateString: templateText,
        uId: "businessActivityScreen",
        packageName: "extn.businessActivity",
        className: "BusinessActivity",
        title: "TITLE_BusinessActivity",
        screen_description: "This screen is the Order Search screen which is used to enter the search criteria for fetching orders and populate the order search results",
        namespaces: {
            targetBindingNamespaces: [],
            sourceBindingNamespaces: []
        },
        staticBindings: [],
        showRelatedTask: false,
        isDirtyCheckRequired: false,
        hotKeys: [{
            id: "Popup_btnCancel",
            key: "ESCAPE",
            description: "$(_scSimpleBundle:Close)",
            widgetId: "Popup_btnCancel",
            invocationContext: "",
            category: "$(_scSimpleBundle:General)",
            helpContextId: ""
        }],
        events: [{
            name: 'setSelectedRow'
        }, {
            name: 'collapseSearchResults'
        }, {
            name: 'saveCurrentPage'
        }, {
            name: 'reloadScreen'
        }],
        subscribers: {
            global: [
            ],
            local: [{
                eventId: 'afterScreenLoad',
                sequence: '25',
                description: 'Subscriber for after the screen loads',
                handler: {
                    methodName: "updateEditorHeader"
                }
            }, {
                eventId: 'reloadScreen',
                sequence: '28',
                description: 'Subscriver for reloading the screen',
                handler: {
                    methodName: "updateEditorHeader"
                }
            }, {
                eventId: 'afterScreenInit',
                sequence: '25',
                description: 'Subscriber for after the screen is initialized',
                handler: {
                    methodName: "initScreen"
                }
            }, {
                eventId: 'saveCurrentPage',
                sequence: '25',
                description: 'Subscriber for save current page event for wizard',
                handler: {
                    methodName: "save"
                }
            }, {
                eventId: 'afterScreenInit',
                sequence: '50',
                description: 'Subscriber for after screen is initialized',
                handler: {
                    methodName: "setInitialized"
                }
            }]
        },
        handleMashupOutput: function(
        mashupRefId, modelOutput, mashupInput, mashupContext, applySetModel) {
            if (
            _scBaseUtils.equals(
            mashupRefId, "getHoldTypeList")) {
            }
        },
        handleMashupCompletion: function(
        mashupContext, mashupRefObj, mashupRefList, inputData, hasError, data) {
            _isccsBaseTemplateUtils.handleMashupCompletion(
            mashupContext, mashupRefObj, mashupRefList, inputData, hasError, data, this);
        },
        onPopupClose: function(
        event, bEvent, ctrl, args) {
            var isDirty = false;
            isDirty = _scScreenUtils.isDirty(
            this, null, true);
            if (
            _scBaseUtils.equals(
            this.isDirtyCheckRequired, true)) {
                if (
                _scBaseUtils.equals(
                isDirty, true)) {
                    var msg = null;
                    var eventid = null;
                    msg = _scScreenUtils.getString(
                    this, "DirtyCloseConfirmationMessage");
                    _scScreenUtils.showConfirmMessageBox(
                    this, msg, "handleCloseConfirmation", null, null);
                    eventid = _scBaseUtils.getValueFromPath("eventName", event);
                } else {
                    _scWidgetUtils.closePopup(
                    this, "CLOSE", false);
                }
            } else {
                _scWidgetUtils.closePopup(
                this, "CLOSE", false);
            }
            var res = null;
            res = _scModelUtils.createNewModelObjectWithRootKey("response");
            _scModelUtils.addStringValueToModelObject("statusCode", "failure", res);
            return res;
        },
        onPopupConfirm: function(
        event, bEvent, ctrl, args) {
            var isValid = true;
            isValid = _scScreenUtils.validate(
            this);
            if (
            _scBaseUtils.equals(
            false, isValid)) {
                var msg = null;
                msg = _scScreenUtils.getString(
                this, "screenHasErrors");
                _isccsBaseTemplateUtils.showMessage(
                this, msg, "error", null);
            } else {
                this.onApply(
                event, bEvent, ctrl, args);
            }
        },
        onApply: function(
        event, bEvent, ctrl, args) {
            _scWidgetUtils.closePopup(
            this, "APPLY", false);
        },
        setPopupOutput: function(
        model) {
            _scScreenUtils.setModel(
            this, "popupOutput", model, null);
        },
        getPopupOutput: function(
        event, bEvent, ctrl, args) {
            var model = null;
            model = _scScreenUtils.getModel(
            this, "popupOutput");
            return model;
        },
        handleCloseConfirmation: function(
        res) {
            if (
            _scBaseUtils.equals(
            res, "Ok")) {
                _scWidgetUtils.closePopup(
                this, "CLOSE", false);
            }
        },
        updateEditorHeader: function(
        event, bEvent, ctrl, args) {
            _isccsBaseTemplateUtils.updateCustomerMessage(
            this, "CUST_BusinessActivity", true);
            _isccsBaseTemplateUtils.updateTitle(
            this, "TITLE_BusinessActivity", null);
            _scScreenUtils.focusFirstEditableWidget(
            this);
            return true;
        },
        setInitialized: function(
        event, bEvent, ctrl, args) {
            this.isScreeninitialized = true;
        },
        initScreen: function(
        event, bEvent, ctrl, args) {
            var parentScreen = null;
            parentScreen = _isccsUIUtils.getParentScreen(
            this, false);
            var hasParent = false;
            if (!(
            _scScreenUtils.isPopup(
            this))) {
                _scWidgetUtils.hideWidget(
                this, "Popup_navigationPanel", false);
            } else if (!(
            _scBaseUtils.isVoid(
            parentScreen))) {
                if (
                _isccsUIUtils.isScreenRef(
                this)) {
                    _scWidgetUtils.hideWidget(
                    this, "Popup_navigationPanel", false);
                } else {
                    _scWidgetUtils.hideWidget(
                    this, "titleMessagePanel", false);
                    _isccsBaseTemplateUtils.setPopupStyle(
                    this);
                }
            } else {
                _scWidgetUtils.hideWidget(
                this, "titleMessagePanel", false);
                _isccsBaseTemplateUtils.setPopupStyle(
                this);
            }
            return true;
        },
        save: function(
        event, bEvent, ctrl, args) {
            var eventDefinition = null;
            eventDefinition = {};
            _scBaseUtils.setAttributeValue("argumentList", args, eventDefinition);
            _scEventUtils.fireEventToParent(
            this, "onSaveSuccess", eventDefinition);
        }
    });
});
