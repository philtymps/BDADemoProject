/*
 * Licensed Materials - Property of IBM
 * IBM Call Center for Commerce (5725-P82)
 * (C) Copyright IBM Corp. 2013 All Rights Reserved.
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */
scDefine(["scbase/loader!dojo/_base/declare", "scbase/loader!dojo/_base/kernel", "scbase/loader!dojo/text", "scbase/loader!extn/businessActivity/BusinessActivity", "scbase/loader!sc/plat/dojo/controller/ServerDataController"], function(
_dojodeclare, _dojokernel, _dojotext, _isccsBusinessActivity, _scServerDataController) {
    return _dojodeclare("extn.businessActivity.BusinessActivityBehaviorController", [_scServerDataController], {
        screenId: 'extn.businessActivity.BusinessActivity',
        mashupRefs: []
    });
});
