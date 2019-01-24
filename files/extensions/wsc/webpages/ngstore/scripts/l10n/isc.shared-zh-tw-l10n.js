/*******************************************************************************
* IBM Confidential
* OCO Source Materials
* IBM Sterling Order Management Store (5725-D10), IBM Order Management (5737-D18), IBM Store Engagement (5737D58)
* (C) Copyright IBM Corp. 2014, 2017 All Rights Reserved.
* The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
******************************************************************************/
angular.module('isc.shared').run(['iscL10nCache', function(iscL10nCache) {
  iscL10nCache.put('./shared/nls/zh-tw/shared.nls.json',
    '{"globals":{"BUTTON_Cancel":"取消","BUTTON_Close":"關閉","ERROR_invalid_input":"輸入值無效。"},"shell":{"MESSAGE_Confirm_exit_dirty":"警告：任何未儲存的變更將會遺失。","MESSAGE_Confirm_exit_non_dirty":"警告：您可能失去現行視圖資訊。"},"modal":{"BUTTON_Ok":"確定","BUTTON_Yes":"是","BUTTON_No":"否","BUTTON_Apply":"套用","TITLE_Info":"資訊","TITLE_Confirmation":"確認","TITLE_Warning":"警告","TITLE_Error":"錯誤","TITLE_Success":"成功"},"wizard":{"BUTTON_Next":"下一步","BUTTON_Previous":"上一步","BUTTON_FINISH":"完成","BUTTON_Page_action_menu":"頁面動作功能表","TOOLTIP_Page_action_menu":"頁面動作功能表"},"mashup":{"MESSAGE_Mashup_error":"應用程式無法回應。請聯絡系統管理者。 "},"validation":{"ERROR_mandatory":"必要"},"datatypevalidation":{"ERROR_negative_unsupported":"輸入值應該為非負值。","ERROR_zero_unsupported":"輸入值不能是零。","ERROR_decimal_unsupported":"輸入值不能包含小數位數。","ERROR_min_value_limit":"輸入值小於下限限制。","ERROR_max_value_exceeded":"輸入值超過上限限制。","ERROR_decimal_max":"小數點後支援的位數上限為：","ERROR_string_max":"輸入值超過支援長度上限。","ERROR_invalid_email":"輸入內容包含無效的電子郵件格式。","ERROR_invalid_phone_format":"輸入內容包含無效的電話號碼格式。","ERROR_invalid_char_cc_num":"信用卡卡號包含無效的字元。","error_input_does_not_conform_pattern":"輸入包含無效的字元。"},"datefilter":{"LABEL_From_Date":"自","LABEL_To_Date":"收件者","LABEL_Date_Filter_Options":"日期過濾選項","LABEL_last30Days":"前 30 天","LABEL_thisMonth":"本月","LABEL_lastMonth":"上個月","LABEL_last3Months":"前 3 個月","LABEL_last6Months":"前 6 個月","LABEL_lastYear":"前一年","LABEL_last7Days":"前 7 天","LABEL_today":"今日","LABEL_yesterday":"昨天","LABEL_tomorrow":"明天","LABEL_next7Days":"接下來的 7 天","LABEL_nextMonth":"下個月","ERROR_invalid_format":"輸入的日期格式無效","ERROR_date_missing":"選取/輸入要過濾的日期","ERROR_toDate_lesser":"「結束日期」不可小於「起始日期」","ERROR_date_greater_than_range":"輸入的日期大於或小於允許的範圍"},"dialchart":{"LABEL_blankArea":"空白區域","LABEL_noData":"沒有資料可供顯示"},"barcode":{"MESSAGE_No_barcode_field":"現行視圖中找不到「條碼掃描」欄位","MESSAGE_Multiple_barcode_field":"存在多個掃描欄位。請將焦點放在需要的掃描欄位並重新掃描"},"shipmentFilter":{"ACTION_Refine":"精簡裝運清單","TITLE_Filter":"精簡","LABEL_Shipment_date":"裝運日期","LABEL_Shipment_type":"裝運類型","LABEL_Shipment_status":"裝運狀態","LABEL_Shipment_carrier":"運輸業者","LABEL_Shipment_source":"來源","LABEL_AnyCarrier":"任何運輸業者","ACTION_Apply":"套用","ACTION_ClearAll":"全部清除","ACTION_Close_Filter_Panel":"按一下以關閉「過濾器」畫面","placeholderFormat":"yyyy/MMM/dd","TITLE_Expand":"展開","TITLE_Collapse":"收合","source":{"LABEL_All":"全部","LABEL_Vendor":"供應商","LABEL_Store":"商店間調貨","LABEL_Warehouse":"倉儲"},"type":{"LABEL_All":"全部","LABEL_Special_order":"銷售訂單","LABEL_Inventory":"庫存"}},"notes":{"LABEL_Mark_Important":"標示為重要","LABEL_Comments":"附註 (${count})","LABEL_Enter_Note":"新增附註（最多 2000 個字元）","noTextError":"沒有可新增的附註！","maxlengthError":"附註超出容許的 2000 個字元限制","LABEL_System_Generated_Note":"系統已產生"},"orderFilter":{"LABEL_ProductPictures":"產品影像","LABEL_Purchased":"已購買","LABEL_Recommendations":"建議"},"productlist":{"TOOLTIP_Open_product_details":"按一下以顯示 ${itemId} 的產品詳細資料"},"batchFilter":{"ACTION_Refine":"精簡批次清單","ACTION_RefineBatchLines":"精簡批次明細行"},"shipmentorderbarcode":{"LABEL_ScanPickBarCode":"掃描或輸入訂單或裝運號碼","TOOLTIP_SearchPickOrder":"掃描或輸入訂單或裝運號碼","MSG_InvalidBarCodeData":"請掃描或輸入有效的訂單號碼或裝運號碼"},"quantity":{"ACTION_IncreaseQty":"增加數量","ACTION_DecreaseQty":"減少數量","MESSAGE_MaxQuantityError":"輸入的數量超過可用的數量。","MESSAGE_LesserMinQtyError":"數量小於最低數量。","MESSAGE_NegativeQtyError":"數量不能小於零。"},"portlet":{"MESSAGE_OldSpecPortlets":"角色式首頁有舊的規格 Portlet。請將它們全部新增。"},"timeslot":{"MESSAGE_No_slots_available":"沒有可用的時段。","LABEL_Avaiable_slot":"可用的時段","TOOLTIP_Time_slot":"按一下以選取時段「${timerange}」"},"calendar":{"MSG_MaxDateLimitExceeded":"新選擇超出約定可選取的日期上限。將保留稍早的選擇。","ACTION_previousMonth":"上個月","ACTION_nextMonth":"下個月","ACTION_previousWeek":"上週","ACTION_nextWeek":"下週","LABEL_eventDuration":"${startTime} - ${endTime}","TITLE_allEventsHeader":"${startTime} - ${endTime}"}}');
}]);

angular.module('isc.shared').run(['iscL10nCache', function(iscL10nCache) {
  iscL10nCache.put('./shared/nls/zh-tw/shared_a11y.nls.json',
    '{"globals":{"MESSAGE_Loading":"Processing"},"miniCart":{"aria_miniCartRegion":"Mini Cart","aria_miniCartPreview":"Mini Cart Preview","aria_miniCartShowPopover":"Mini Cart Show Popover","aria_miniCartShowPreview":"Mini Cart Show Preview","aria_removeProduct":"Remove Product ${itemId}","aria_productsInCart":"products in cart. Click to view mini cart."},"lookup":{"aria_lookupRegion":"Lookup and Search","aria_globalLookupRegion":"Global Lookup and Search","aria_returnLookupRegion":"Returns Lookup and Search","aria_inStoreReceivingLookupRegion":"Instore Receiving Lookup and Search"},"shipmentFilter":{"aria_shipmentListFilterRegion":"Shipment List Filter","aria_shipmentListShowPopover":"Click to view Shipment List Filter","aria_shipmentListFilterOptionsRegion":"Shipment List Filter Options","aria_shipmentFilterButton":"Refine Shipment List","aria_shipmentListFilterCriteriaRegion":"Shipment List Filter Criteria","aria_accordionClickShipmentDate":"Click to toggle Shipment Date filter options panel","aria_accordionClickShipmentStatus":"Click to toggle Shipment Status filter options panel","aria_accordionClickShipmentChannel":"Click to toggle Shipment Channel filter options panel"},"notes":{"aria_notesTextbox":"Enter Note","aria_notesList":"Notes List","aria_addNote":"Add Note","aria_noteIsImportant":"Important Note","aria_addNoteButton":"Click to add a note"},"productlist":{"aria_Open_product_details":"Click to show product details of ${itemId}","aria_UnitPrice":"Unit Price","aria_ListPrice":"List Price"},"shipmentorderbarcode":{"aria_ACTION_Scan_Order_Shipment":"Click to search for Shipments"},"quantity":{"aria_ACTION_DecreaseQty":"Click to decrease quantity by 1","aria_ACTION_IncreaseQty":"Click to increase quantity by 1"},"calendar":{"aria_TITLE_allEventsHeader":"List of appointments between the selected time"}}');
}]);
