/*******************************************************************************
* IBM Confidential
* OCO Source Materials
* IBM Sterling Order Management Store (5725-D10), IBM Order Management (5737-D18), IBM Store Engagement (5737D58)
* (C) Copyright IBM Corp. 2014, 2017 All Rights Reserved.
* The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
******************************************************************************/
angular.module('isc.shared').run(['iscL10nCache', function(iscL10nCache) {
  iscL10nCache.put('./shared/nls/pl/shared.nls.json',
    '{"globals":{"BUTTON_Cancel":"Anuluj","BUTTON_Close":"Zamknij","ERROR_invalid_input":"Wartość wejściowa jest niepoprawna."},"shell":{"MESSAGE_Confirm_exit_dirty":"Ostrzeżenie: Utracisz niezapisane zmiany.","MESSAGE_Confirm_exit_non_dirty":"Ostrzeżenie: Istnieje możliwość, że utracisz informacje z bieżącego widoku."},"modal":{"BUTTON_Ok":"OK","BUTTON_Yes":"Tak","BUTTON_No":"Nie","BUTTON_Apply":"Zastosuj","TITLE_Info":"Informacja","TITLE_Confirmation":"Potwierdzenie","TITLE_Warning":"Ostrzeżenie","TITLE_Error":"Błąd","TITLE_Success":"Powodzenie"},"wizard":{"BUTTON_Next":"Dalej","BUTTON_Previous":"Wstecz","BUTTON_FINISH":"Zakończ","BUTTON_Page_action_menu":"Menu działań dotyczących strony","TOOLTIP_Page_action_menu":"Menu działań dotyczących strony"},"mashup":{"MESSAGE_Mashup_error":"Aplikacja nie odpowiada. Skontaktuj się z administratorem systemu."},"validation":{"ERROR_mandatory":"Wymagany"},"datatypevalidation":{"ERROR_negative_unsupported":"Wartość wejściowa nie może być ujemna.","ERROR_zero_unsupported":"Wartość wejściowa nie może być równa 0.","ERROR_decimal_unsupported":"Wartość wejściowa nie może być złożona z cyfr dziesiętnych.","ERROR_min_value_limit":"Wartość wejściowa jest mniejsza niż dozwolona wartość minimalna.","ERROR_max_value_exceeded":"Wartość wejściowa jest większa niż dozwolona wartość maksymalna.","ERROR_decimal_max":"Maksymalna obsługiwana liczba cyfr po przecinku: ","ERROR_string_max":"Długość wartości wejściowej jest większa od maksymalnej obsługiwanej długości.","ERROR_invalid_email":"Dane wejściowe zawierają niepoprawny format adresu e-mail.","ERROR_invalid_phone_format":"Dane wejściowe zawierają niepoprawny format numeru telefonu.","ERROR_invalid_char_cc_num":"Numer karty kredytowej zawiera niepoprawne znaki.","error_input_does_not_conform_pattern":"Dane wejściowe zawierają niepoprawne znaki."},"datefilter":{"LABEL_From_Date":"Od","LABEL_To_Date":"Do","LABEL_Date_Filter_Options":"Opcje filtrowania dat","LABEL_last30Days":"Ostatnie 30 dni","LABEL_thisMonth":"Bieżący miesiąc","LABEL_lastMonth":"Ostatni miesiąc","LABEL_last3Months":"Ostatnie 3 miesiące","LABEL_last6Months":"Ostatnie 6 miesięcy","LABEL_lastYear":"Ostatni rok","LABEL_last7Days":"Ostatnie 7 dni","LABEL_today":"Dzisiaj","LABEL_yesterday":"Wczoraj","LABEL_tomorrow":"Jutro","LABEL_next7Days":"Następne 7 dni","LABEL_nextMonth":"Następny miesiąc","ERROR_invalid_format":"Wprowadzono datę w niepoprawnym formacie.","ERROR_date_missing":"Wybierz lub wprowadź dwie daty, aby przefiltrować dane.","ERROR_toDate_lesser":"Data końcowa nie może być wcześniejsza niż data początkowa.","ERROR_date_greater_than_range":"Wprowadzone daty są spoza dopuszczalnego zakresu."},"dialchart":{"LABEL_blankArea":"Pusty obszar","LABEL_noData":"Brak danych do wyświetlenia"},"barcode":{"MESSAGE_No_barcode_field":"W bieżącym widoku nie ma pola Skanowanie kodu paskowego","MESSAGE_Multiple_barcode_field":"Istnieje więcej niż jedno pole skanowania. Wybierz wymagane pole skanowania i powtórz operację."},"shipmentFilter":{"ACTION_Refine":"Zawęź listę wysyłek","TITLE_Filter":"Filtrowanie","LABEL_Shipment_date":"Data wysyłki","LABEL_Shipment_type":"Typ wysyłki","LABEL_Shipment_status":"Status wysyłki","LABEL_Shipment_carrier":"Przewoźnik","LABEL_Shipment_source":"Źródło","LABEL_AnyCarrier":"Dowolny przewoźnik","ACTION_Apply":"Zastosuj","ACTION_ClearAll":"Wyczyść wszystko","ACTION_Close_Filter_Panel":"Kliknij, aby zamknąć panel Filtr","placeholderFormat":"dd mmm rrrr","TITLE_Expand":"Rozwiń","TITLE_Collapse":"Zwiń","source":{"LABEL_All":"Wszystkie","LABEL_Vendor":"Dostawca","LABEL_Store":"Przesyłanie między sklepami","LABEL_Warehouse":"Magazyn"},"type":{"LABEL_All":"Wszystkie","LABEL_Special_order":"Zlecenie sprzedaży","LABEL_Inventory":"Zapasy"}},"notes":{"LABEL_Mark_Important":"Oznacz jako ważne","LABEL_Comments":"Uwagi (${count})","LABEL_Enter_Note":"Dodaj uwagę (maksymalnie 2000 znaków)","noTextError":"Brak uwagi do dodania.","maxlengthError":"Liczba znaków w uwadze przekracza limit dozwolonej liczby znaków (2000)","LABEL_System_Generated_Note":"Generowane przez system"},"orderFilter":{"LABEL_ProductPictures":"Obrazy produktów","LABEL_Purchased":"Kupione","LABEL_Recommendations":"Rekomendacje"},"productlist":{"TOOLTIP_Open_product_details":"Kliknij, aby pokazać szczegóły produktu ${itemId}"},"batchFilter":{"ACTION_Refine":"Zawęź listę partii","ACTION_RefineBatchLines":"Zawęź wiersze partii"},"shipmentorderbarcode":{"LABEL_ScanPickBarCode":"Zeskanuj lub wprowadź numer zlecenia lub wysyłki","TOOLTIP_SearchPickOrder":"Zeskanuj lub wprowadź numer zlecenia lub wysyłki","MSG_InvalidBarCodeData":"Zeskanuj lub wprowadź poprawny numer zlecenia lub wysyłki"},"quantity":{"ACTION_IncreaseQty":"Zwiększ ilość","ACTION_DecreaseQty":"Zmniejsz ilość","MESSAGE_MaxQuantityError":"Wprowadzona ilość jest większa niż dostępna ilość.","MESSAGE_LesserMinQtyError":"Ilość jest mniejsza niż ilość minimalna.","MESSAGE_NegativeQtyError":"Ilość nie może być mniejsza niż zero."},"portlet":{"MESSAGE_OldSpecPortlets":"Strona główna oparta na personach ma stare portlety specyfikacji. Są one dodawane na końcu."},"timeslot":{"MESSAGE_No_slots_available":"Brak dostępnych okresów.","LABEL_Avaiable_slot":"Dostępne okresy","TOOLTIP_Time_slot":"Kliknij, aby wybrać okres ${timerange}"},"calendar":{"MSG_MaxDateLimitExceeded":"Nowy wybór przekracza najpóźniejszą datę, która jest możliwa do wybrania w przypadku terminów. Zostanie zachowany wcześniejszy wybór.","ACTION_previousMonth":"Poprzedni miesiąc","ACTION_nextMonth":"Następny miesiąc","ACTION_previousWeek":"Poprzedni tydzień","ACTION_nextWeek":"Następny tydzień","LABEL_eventDuration":"${startTime}–${endTime}","TITLE_allEventsHeader":"${startTime}–${endTime}"}}');
}]);

angular.module('isc.shared').run(['iscL10nCache', function(iscL10nCache) {
  iscL10nCache.put('./shared/nls/pl/shared_a11y.nls.json',
    '{"globals":{"MESSAGE_Loading":"Processing"},"miniCart":{"aria_miniCartRegion":"Mini Cart","aria_miniCartPreview":"Mini Cart Preview","aria_miniCartShowPopover":"Mini Cart Show Popover","aria_miniCartShowPreview":"Mini Cart Show Preview","aria_removeProduct":"Remove Product ${itemId}","aria_productsInCart":"products in cart. Click to view mini cart."},"lookup":{"aria_lookupRegion":"Lookup and Search","aria_globalLookupRegion":"Global Lookup and Search","aria_returnLookupRegion":"Returns Lookup and Search","aria_inStoreReceivingLookupRegion":"Instore Receiving Lookup and Search"},"shipmentFilter":{"aria_shipmentListFilterRegion":"Shipment List Filter","aria_shipmentListShowPopover":"Click to view Shipment List Filter","aria_shipmentListFilterOptionsRegion":"Shipment List Filter Options","aria_shipmentFilterButton":"Refine Shipment List","aria_shipmentListFilterCriteriaRegion":"Shipment List Filter Criteria","aria_accordionClickShipmentDate":"Click to toggle Shipment Date filter options panel","aria_accordionClickShipmentStatus":"Click to toggle Shipment Status filter options panel","aria_accordionClickShipmentChannel":"Click to toggle Shipment Channel filter options panel"},"notes":{"aria_notesTextbox":"Enter Note","aria_notesList":"Notes List","aria_addNote":"Add Note","aria_noteIsImportant":"Important Note","aria_addNoteButton":"Click to add a note"},"productlist":{"aria_Open_product_details":"Click to show product details of ${itemId}","aria_UnitPrice":"Unit Price","aria_ListPrice":"List Price"},"shipmentorderbarcode":{"aria_ACTION_Scan_Order_Shipment":"Click to search for Shipments"},"quantity":{"aria_ACTION_DecreaseQty":"Click to decrease quantity by 1","aria_ACTION_IncreaseQty":"Click to increase quantity by 1"},"calendar":{"aria_TITLE_allEventsHeader":"List of appointments between the selected time"}}');
}]);