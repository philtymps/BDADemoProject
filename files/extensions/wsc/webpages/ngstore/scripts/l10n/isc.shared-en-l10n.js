/*******************************************************************************
* IBM Confidential
* OCO Source Materials
* IBM Sterling Order Management Store (5725-D10), IBM Order Management (5737-D18), IBM Store Engagement (5737D58)
* (C) Copyright IBM Corp. 2014, 2017 All Rights Reserved.
* The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
******************************************************************************/
angular.module('isc.shared').run(['iscL10nCache', function(iscL10nCache) {
  iscL10nCache.put('./shared/nls/en/shared.nls.json',
    '{"globals":{"BUTTON_Cancel":"Cancel","BUTTON_Close":"Close","ERROR_invalid_input":"Input value is invalid."},"shell":{"MESSAGE_Confirm_exit_dirty":"Warning: You will lose any unsaved changes.","MESSAGE_Confirm_exit_non_dirty":"Warning: You may lose the current view information."},"modal":{"BUTTON_Ok":"OK","BUTTON_Yes":"Yes","BUTTON_No":"No","BUTTON_Apply":"Apply","TITLE_Info":"Information","TITLE_Confirmation":"Confirmation","TITLE_Warning":"Warning","TITLE_Error":"Error","TITLE_Success":"Success"},"wizard":{"BUTTON_Next":"Next","BUTTON_Previous":"Previous","BUTTON_FINISH":"Finish","BUTTON_Page_action_menu":"Page Action Menu","TOOLTIP_Page_action_menu":"Page Action Menu"},"mashup":{"MESSAGE_Mashup_error":"The application failed to respond. Contact the system administrator."},"validation":{"ERROR_mandatory":"Required"},"datatypevalidation":{"ERROR_negative_unsupported":"Input value should be non negative.","ERROR_zero_unsupported":"Input value should not be zero.","ERROR_decimal_unsupported":"Input value should not contain decimal digits.","ERROR_min_value_limit":"Input value is lesser than the minimum limit.","ERROR_max_value_exceeded":"Input value exceeds the maximum limit.","ERROR_decimal_max":"Maximum digits supported after decimals is: ","ERROR_string_max":"Input value exceeds maximum supported length.","ERROR_invalid_email":"Input contains invalid email format.","ERROR_invalid_phone_format":"Input contains invalid phone number format.","ERROR_invalid_char_cc_num":"Credit card number contains invalid characters.","error_input_does_not_conform_pattern":"Input contains invalid characters."},"datefilter":{"LABEL_From_Date":"From","LABEL_To_Date":"To","LABEL_Date_Filter_Options":"Date Filter Options","LABEL_last30Days":"Last 30 days","LABEL_thisMonth":"This month","LABEL_lastMonth":"Last month","LABEL_last3Months":"Last 3 months","LABEL_last6Months":"Last 6 months","LABEL_lastYear":"Last year","LABEL_last7Days":"Last 7 Days","LABEL_today":"Today","LABEL_yesterday":"Yesterday","LABEL_tomorrow":"Tomorrow","LABEL_next7Days":"Next 7 Days","LABEL_nextMonth":"Next month","ERROR_invalid_format":"Invalid date format entered","ERROR_date_missing":"Select/Enter both dates to filter","ERROR_toDate_lesser":"To Date cannot be lesser than From Date","ERROR_date_greater_than_range":"Date(s) entered greater or less than allowed range"},"dialchart":{"LABEL_blankArea":"Blank area","LABEL_noData":"No data to display"},"barcode":{"MESSAGE_No_barcode_field":"No Barcode Scan field found in the current view","MESSAGE_Multiple_barcode_field":"More than one scan fields are present. Place focus on the required scan field and scan again"},"shipmentFilter":{"ACTION_Refine":"Refine Shipment List","TITLE_Filter":"Refine","LABEL_Shipment_date":"Shipment Date","LABEL_Shipment_type":"Shipment Type","LABEL_Shipment_status":"Shipment Status","LABEL_Shipment_carrier":"Carrier","LABEL_Shipment_source":"Source","LABEL_AnyCarrier":"Any Carrier","ACTION_Apply":"Apply","ACTION_ClearAll":"Clear All","ACTION_Close_Filter_Panel":"Click to close the Filter panel","placeholderFormat":"MMM dd,yyyy","TITLE_Expand":"Expand","TITLE_Collapse":"Collapse","source":{"LABEL_All":"All","LABEL_Vendor":"Vendor","LABEL_Store":"Store to Store Transfer","LABEL_Warehouse":"Warehouse"},"type":{"LABEL_All":"All","LABEL_Special_order":"Sales Order","LABEL_Inventory":"Inventory"}},"notes":{"LABEL_Mark_Important":"Mark important","LABEL_Comments":"Notes (${count})","LABEL_Enter_Note":"Add Note (maximum 2000 characters)","noTextError":"No note to add!","maxlengthError":"Note exceeds allowed characters limit of 2000","LABEL_System_Generated_Note":"System generated"},"orderFilter":{"LABEL_ProductPictures":"Product Images","LABEL_Purchased":"Purchased","LABEL_Recommendations":"Recommendations"},"productlist":{"TOOLTIP_Open_product_details":"Click to show product details of ${itemId}"},"batchFilter":{"ACTION_Refine":"Refine Batch List","ACTION_RefineBatchLines":"Refine Batch Lines"},"shipmentorderbarcode":{"LABEL_ScanPickBarCode":"Scan or enter order or shipment #","TOOLTIP_SearchPickOrder":"Scan or enter order or shipment #","MSG_InvalidBarCodeData":"Please scan or enter a valid order number or shipment number"},"quantity":{"ACTION_IncreaseQty":"Increase quantity","ACTION_DecreaseQty":"Decrease quantity","MESSAGE_MaxQuantityError":"The entered quantity exceeds the available quantity.","MESSAGE_LesserMinQtyError":"Quantity is lower than min quantity.","MESSAGE_NegativeQtyError":"Quantity cannot be less than zero."},"portlet":{"MESSAGE_OldSpecPortlets":"Persona based home page has old specification portlets. Adding them towards the end."},"timeslot":{"MESSAGE_No_slots_available":"No slots available.","LABEL_Avaiable_slot":"Available slots","TOOLTIP_Time_slot":"Click to select the time slot ${timerange}"},"calendar":{"MSG_MaxDateLimitExceeded":"New selection exceeds the maximum selectable date for appointments. Earlier selection is retained.","ACTION_previousMonth":"Previous month","ACTION_nextMonth":"Next month","ACTION_previousWeek":"Previous week","ACTION_nextWeek":"Next week","LABEL_eventDuration":"${startTime} - ${endTime}","TITLE_allEventsHeader":"${startTime} - ${endTime}"}}');
}]);

angular.module('isc.shared').run(['iscL10nCache', function(iscL10nCache) {
  iscL10nCache.put('./shared/nls/en/shared_a11y.nls.json',
    '{"globals":{"MESSAGE_Loading":"Processing"},"miniCart":{"aria_miniCartRegion":"Mini Cart","aria_miniCartPreview":"Mini Cart Preview","aria_miniCartShowPopover":"Mini Cart Show Popover","aria_miniCartShowPreview":"Mini Cart Show Preview","aria_removeProduct":"Remove Product ${itemId}","aria_productsInCart":"products in cart. Click to view mini cart."},"lookup":{"aria_lookupRegion":"Lookup and Search","aria_globalLookupRegion":"Global Lookup and Search","aria_returnLookupRegion":"Returns Lookup and Search","aria_inStoreReceivingLookupRegion":"Instore Receiving Lookup and Search"},"shipmentFilter":{"aria_shipmentListFilterRegion":"Shipment List Filter","aria_shipmentListShowPopover":"Click to view Shipment List Filter","aria_shipmentListFilterOptionsRegion":"Shipment List Filter Options","aria_shipmentFilterButton":"Refine Shipment List","aria_shipmentListFilterCriteriaRegion":"Shipment List Filter Criteria","aria_accordionClickShipmentDate":"Click to toggle Shipment Date filter options panel","aria_accordionClickShipmentStatus":"Click to toggle Shipment Status filter options panel","aria_accordionClickShipmentChannel":"Click to toggle Shipment Channel filter options panel"},"notes":{"aria_notesTextbox":"Enter Note","aria_notesList":"Notes List","aria_addNote":"Add Note","aria_noteIsImportant":"Important Note","aria_addNoteButton":"Click to add a note"},"productlist":{"aria_Open_product_details":"Click to show product details of ${itemId}","aria_UnitPrice":"Unit Price","aria_ListPrice":"List Price"},"shipmentorderbarcode":{"aria_ACTION_Scan_Order_Shipment":"Click to search for Shipments"},"quantity":{"aria_ACTION_DecreaseQty":"Click to decrease quantity by 1","aria_ACTION_IncreaseQty":"Click to increase quantity by 1"},"calendar":{"aria_TITLE_allEventsHeader":"List of appointments between the selected time"}}');
}]);
