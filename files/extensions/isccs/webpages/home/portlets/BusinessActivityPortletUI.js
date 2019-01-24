/*
 * Licensed Materials - Property of IBM
 * IBM Call Center for Commerce (5725-P82)
 * (C) Copyright IBM Corp. 2013 All Rights Reserved.
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */
scDefine(["dojo/text!./templates/BusinessActivityPortlet.html", "scbase/loader!dijit/form/Button", "scbase/loader!dojo/_base/declare", "scbase/loader!dojo/_base/kernel", "scbase/loader!dojo/_base/lang", "scbase/loader!dojo/text", "scbase/loader!idx/layout/ContentPane", "scbase/loader!idx/layout/TitlePane", "scbase/loader!isccs/home/portlets/portletComponents/MyAlertQuery", "scbase/loader!isccs/utils/BaseTemplateUtils", "scbase/loader!isccs/utils/SearchUtils", "scbase/loader!isccs/utils/UIUtils", "scbase/loader!sc/plat", "scbase/loader!sc/plat/dojo/binding/ButtonDataBinder", "scbase/loader!sc/plat/dojo/binding/CurrencyDataBinder", "scbase/loader!sc/plat/dojo/binding/ImageDataBinder", "scbase/loader!sc/plat/dojo/utils/BaseUtils", "scbase/loader!sc/plat/dojo/utils/EventUtils", "scbase/loader!sc/plat/dojo/utils/ModelUtils", "scbase/loader!sc/plat/dojo/utils/ScreenUtils", "scbase/loader!sc/plat/dojo/utils/WidgetUtils", "scbase/loader!sc/plat/dojo/widgets/Image", "scbase/loader!sc/plat/dojo/widgets/Label", "scbase/loader!sc/plat/dojo/widgets/Link", "scbase/loader!sc/plat/dojo/widgets/Screen"], function(
templateText, _dijitButton, _dojodeclare, _dojokernel, _dojolang, _dojotext, _idxContentPane, _idxTitlePane, _isccsMyAlertQuery, _isccsBaseTemplateUtils, _isccsSearchUtils, _isccsUIUtils, _scplat, _scButtonDataBinder, _scCurrencyDataBinder, _scImageDataBinder, _scBaseUtils, _scEventUtils, _scModelUtils, _scScreenUtils, _scWidgetUtils, _scImage, _scLabel, _scLink, _scScreen) {
    return _dojodeclare("extn.home.portlets.BusinessActivityPortletUI", [_scScreen], {
        templateString: templateText,
        uId: "BusinessActivityPortlet",
        packageName: "extn.home.portlets",
        className: "BusinessActivityPortlet",
        title: "Alert",
        screen_description: "This is the Alert portlet in the home page",
        screenLoaded: false,
        showRelatedTask: false,
        firstClickEvent: 'true',
        isDirtyCheckRequired: true,
        namespaces: {
            targetBindingNamespaces: [],
            sourceBindingNamespaces: [{
                value: 'portletTitle',
                description: "This namespace is used to store and set the title of the portlet"
            }]
        },
        hotKeys: [],
        events: [{
            name: 'onClick',
            originatingControlUId: 'portletHeaderPanel'
        },{
            name: 'saveCurrentPage'
        }, {
            name: 'reloadScreen'
        }],
        subscribers: {
            local: [{
                eventId: 'portletHeaderPanel_onClick',
                sequence: '30',
                description: 'This event is fired when the portlet header is clicked.',
                handler: {
                    methodName: "clickPortletPnlEvent"
                }
            }, {
                eventId: 'afterScreenInit',
                sequence: '55',
                handler: {
                    methodName: "initializeScreenTemplate"
                }
            }, {
                eventId: 'afterScreenInit',
                sequence: '30',
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
            }, {
                eventId: 'bOrderStates_onClick',
                sequence: '50',
                description: 'openOrderStates',
                handler: {
                    methodName: "openReport"
                }
            }, {
                eventId: 'bReport2_onClick',
                sequence: '50',
                description: 'openOrderReport2',
                handler: {
                    methodName: "openReport2"
                }
            }]
        },
        setInitialized: function(
        event, bEvent, ctrl, args) {
            this.isScreeninitialized = true;
        },
        save: function(
        event, bEvent, ctrl, args) {
            var eventDefinition = null;
            eventDefinition = {};
            _scBaseUtils.setAttributeValue("argumentList", args, eventDefinition);
            _scEventUtils.fireEventToParent(
            this, "onSaveSuccess", eventDefinition);
        },
        onSearch: function(
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
            }
            return isValid;
        },
        clickPortletPnlEvent: function(
        event, bEvent, ctrl, args) {
            if (!(
            _scBaseUtils.isVoid(""))) {
                if (
                _scBaseUtils.equals(
                this.firstClickEvent, "true")) {
                    _scWidgetUtils.showWidget(
                    this, "", false, null);
                    var completeInputModel = null;
                    completeInputModel = _scModelUtils.createNewModelObjectWithRootKey("Shipment");
                    _isccsUIUtils.callApi(
                    this, completeInputModel, "", null);
                    this.firstClickEvent = "false";
                } else {
                    if (
                    _scWidgetUtils.isWidgetVisible(
                    this, "")) {
                        _scWidgetUtils.hideWidget(
                        this, "", false);
                    } else {
                        _scWidgetUtils.showWidget(
                        this, "", false, null);
                    }
                }
            }
        },
        initializeScreenTemplate: function(
        event, bEvent, ctrl, args) {
            if (!(
            _scBaseUtils.isVoid(""))) {
                _scWidgetUtils.hideWidget(
                this, "", false);
            }
        },
        setPortletTitle: function(
        title) {
            var portletModel = null;
            portletModel = _scModelUtils.createNewModelObjectWithRootKey("Portlet");
            _scModelUtils.setStringValueAtModelPath("Portlet.Title", title, portletModel);
            _scBaseUtils.setModel(
            this, "portletTitle", portletModel, null);
        }
    });
});
