/*******************************************************************************
* IBM Confidential
* OCO Source Materials
* IBM Sterling Order Management Store (5725-D10), IBM Order Management (5737-D18), IBM Store Engagement (5737D58)
* (C) Copyright IBM Corp. 2014, 2017 All Rights Reserved.
* The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
******************************************************************************/
angular.module('isc.shared').run(['iscL10nCache', function(iscL10nCache) {
  iscL10nCache.put('./shared/nls/ko/shared.nls.json',
    '{"globals":{"BUTTON_Cancel":"취소","BUTTON_Close":"닫기","ERROR_invalid_input":"입력 값이 유효하지 않습니다."},"shell":{"MESSAGE_Confirm_exit_dirty":"경고: 저장되지 않은 변경사항은 유실됩니다.","MESSAGE_Confirm_exit_non_dirty":"경고: 현재 조회 정보가 유실될 수 있습니다."},"modal":{"BUTTON_Ok":"확인","BUTTON_Yes":"예","BUTTON_No":"아니오","BUTTON_Apply":"적용","TITLE_Info":"정보","TITLE_Confirmation":"확정","TITLE_Warning":"경고","TITLE_Error":"에러","TITLE_Success":"성공"},"wizard":{"BUTTON_Next":"다음","BUTTON_Previous":"이전","BUTTON_FINISH":"완료","BUTTON_Page_action_menu":"페이지 조치 메뉴","TOOLTIP_Page_action_menu":"페이지 조치 메뉴"},"mashup":{"MESSAGE_Mashup_error":"애플리케이션이 응답하지 않습니다. 시스템 관리자에게 문의하십시오."},"validation":{"ERROR_mandatory":"필수"},"datatypevalidation":{"ERROR_negative_unsupported":"입력 값은 음수일 수 없습니다.","ERROR_zero_unsupported":"입력 값은 영(0)일 수 없습니다.","ERROR_decimal_unsupported":"입력 값은 소수를 포함할 수 없습니다.","ERROR_min_value_limit":"입력 값이 최소 한계 미만입니다.","ERROR_max_value_exceeded":"입력 값이 최대 한계를 초과합니다.","ERROR_decimal_max":"소숫점 이후 지원되는 최대 숫자: ","ERROR_string_max":"입력 값이 최대 지원 길이를 초과합니다.","ERROR_invalid_email":"입력에 유효하지 않은 이메일 형식이 포함되어 있습니다.","ERROR_invalid_phone_format":"입력에 유효하지 않은 전화번호 형식이 포함되어 있습니다.","ERROR_invalid_char_cc_num":"신용카드 번호에 유효하지 않은 문자가 포함되어 있습니다.","error_input_does_not_conform_pattern":"입력에 유효하지 않은 문자가 포함되어 있습니다."},"datefilter":{"LABEL_From_Date":"시작일","LABEL_To_Date":"종료일","LABEL_Date_Filter_Options":"날짜 필터 옵션","LABEL_last30Days":"최근 30일","LABEL_thisMonth":"이번 달","LABEL_lastMonth":"최근 1달","LABEL_last3Months":"최근 3개월","LABEL_last6Months":"최근 6개월","LABEL_lastYear":"지난 해","LABEL_last7Days":"최근 7일","LABEL_today":"현재","LABEL_yesterday":"어제","LABEL_tomorrow":"내일","LABEL_next7Days":"다음 7일","LABEL_nextMonth":"다음 달","ERROR_invalid_format":"입력한 날짜 형식이 올바르지 않음","ERROR_date_missing":"필터링할 날짜 모두 선택/입력","ERROR_toDate_lesser":"종료 날짜는 시작 날짜 이전일 수 없음","ERROR_date_greater_than_range":"허용된 범위보다 크거나 작은 날짜를 입력함"},"dialchart":{"LABEL_blankArea":"공백 영역","LABEL_noData":"표시할 데이터 없음"},"barcode":{"MESSAGE_No_barcode_field":"현재 뷰에서 바코드 스캔 필드를 찾을 수 없음","MESSAGE_Multiple_barcode_field":"둘 이상의 스캔 필드가 있습니다. 필요한 스캔 필드에 초점을 두고 다시 스캔"},"shipmentFilter":{"ACTION_Refine":"쉽먼트 목록 세분화","TITLE_Filter":"세분화","LABEL_Shipment_date":"쉽먼트 날짜","LABEL_Shipment_type":"쉽먼트 유형","LABEL_Shipment_status":"쉽먼트 상태","LABEL_Shipment_carrier":"운송업체","LABEL_Shipment_source":"소스","LABEL_AnyCarrier":"임의의 운송업체","ACTION_Apply":"적용","ACTION_ClearAll":"모두 지우기","ACTION_Close_Filter_Panel":"필터 패널을 닫으려면 클릭","placeholderFormat":"YYYY, mm dd","TITLE_Expand":"펼치기","TITLE_Collapse":"접기","source":{"LABEL_All":"전체","LABEL_Vendor":"벤더","LABEL_Store":"상점간 재고이전","LABEL_Warehouse":"창고"},"type":{"LABEL_All":"전체","LABEL_Special_order":"판매오더","LABEL_Inventory":"재고"}},"notes":{"LABEL_Mark_Important":"중요 표시","LABEL_Comments":"메모 (${count})","LABEL_Enter_Note":"메모 추가(최대 2000자)","noTextError":"추가할 메모 없음!","maxlengthError":"참고가 허용된 2000자 제한을 초과함","LABEL_System_Generated_Note":"시스템 생성"},"orderFilter":{"LABEL_ProductPictures":"제품 이미지","LABEL_Purchased":"구매됨","LABEL_Recommendations":"권장사항"},"productlist":{"TOOLTIP_Open_product_details":"${itemId}에 대한 제품 상세내역을 표시하려면 클릭"},"batchFilter":{"ACTION_Refine":"Batch 목록 세분화","ACTION_RefineBatchLines":"Batch 라인 세분화"},"shipmentorderbarcode":{"LABEL_ScanPickBarCode":"오더 또는 쉽먼트 번호 스캔 또는 입력","TOOLTIP_SearchPickOrder":"오더 또는 쉽먼트 번호 스캔 또는 입력","MSG_InvalidBarCodeData":"올바른 오더 번호 또는 쉽먼트 번호를 스캔하거나 입력하십시오."},"quantity":{"ACTION_IncreaseQty":"수량 늘리기","ACTION_DecreaseQty":"수량 줄이기","MESSAGE_MaxQuantityError":"입력된 수량이 사용 가능한 수량을 초과합니다.","MESSAGE_LesserMinQtyError":"수량이 최소 수량보다 낮습니다.","MESSAGE_NegativeQtyError":"수량이 0 미만일 수는 없습니다."},"portlet":{"MESSAGE_OldSpecPortlets":"페르소나 기반 홈 페이지에 이전 스펙 포틀릿이 있습니다. 이들을 끝에 추가 중입니다."},"timeslot":{"MESSAGE_No_slots_available":"사용 가능한 슬롯이 없습니다.","LABEL_Avaiable_slot":"사용 가능한 슬롯","TOOLTIP_Time_slot":"시간 슬롯 ${timerange}을(를) 선택하려면 클릭하십시오."},"calendar":{"MSG_MaxDateLimitExceeded":"새 선택사항이 약속일정의 선택 가능한 최대 날짜를 초과합니다. 이전 선택사항이 유지됩니다.","ACTION_previousMonth":"이전 달","ACTION_nextMonth":"다음 달","ACTION_previousWeek":"저번 주","ACTION_nextWeek":"다음 주","LABEL_eventDuration":"${startTime} - ${endTime}","TITLE_allEventsHeader":"${startTime} - ${endTime}"}}');
}]);

angular.module('isc.shared').run(['iscL10nCache', function(iscL10nCache) {
  iscL10nCache.put('./shared/nls/ko/shared_a11y.nls.json',
    '{"globals":{"MESSAGE_Loading":"Processing"},"miniCart":{"aria_miniCartRegion":"Mini Cart","aria_miniCartPreview":"Mini Cart Preview","aria_miniCartShowPopover":"Mini Cart Show Popover","aria_miniCartShowPreview":"Mini Cart Show Preview","aria_removeProduct":"Remove Product ${itemId}","aria_productsInCart":"products in cart. Click to view mini cart."},"lookup":{"aria_lookupRegion":"Lookup and Search","aria_globalLookupRegion":"Global Lookup and Search","aria_returnLookupRegion":"Returns Lookup and Search","aria_inStoreReceivingLookupRegion":"Instore Receiving Lookup and Search"},"shipmentFilter":{"aria_shipmentListFilterRegion":"Shipment List Filter","aria_shipmentListShowPopover":"Click to view Shipment List Filter","aria_shipmentListFilterOptionsRegion":"Shipment List Filter Options","aria_shipmentFilterButton":"Refine Shipment List","aria_shipmentListFilterCriteriaRegion":"Shipment List Filter Criteria","aria_accordionClickShipmentDate":"Click to toggle Shipment Date filter options panel","aria_accordionClickShipmentStatus":"Click to toggle Shipment Status filter options panel","aria_accordionClickShipmentChannel":"Click to toggle Shipment Channel filter options panel"},"notes":{"aria_notesTextbox":"Enter Note","aria_notesList":"Notes List","aria_addNote":"Add Note","aria_noteIsImportant":"Important Note","aria_addNoteButton":"Click to add a note"},"productlist":{"aria_Open_product_details":"Click to show product details of ${itemId}","aria_UnitPrice":"Unit Price","aria_ListPrice":"List Price"},"shipmentorderbarcode":{"aria_ACTION_Scan_Order_Shipment":"Click to search for Shipments"},"quantity":{"aria_ACTION_DecreaseQty":"Click to decrease quantity by 1","aria_ACTION_IncreaseQty":"Click to increase quantity by 1"},"calendar":{"aria_TITLE_allEventsHeader":"List of appointments between the selected time"}}');
}]);
