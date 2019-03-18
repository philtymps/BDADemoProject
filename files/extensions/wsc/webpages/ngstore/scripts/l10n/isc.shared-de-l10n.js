/*******************************************************************************
* IBM Confidential
* OCO Source Materials
* IBM Sterling Order Management Store (5725-D10), IBM Order Management (5737-D18), IBM Store Engagement (5737D58)
* (C) Copyright IBM Corp. 2014, 2017 All Rights Reserved.
* The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
******************************************************************************/
angular.module('isc.shared').run(['iscL10nCache', function(iscL10nCache) {
  iscL10nCache.put('./shared/nls/de/shared.nls.json',
    '{"globals":{"BUTTON_Cancel":"Abbrechen","BUTTON_Close":"Schließen","ERROR_invalid_input":"Der Eingabewert ist ungültig."},"shell":{"MESSAGE_Confirm_exit_dirty":"Warnung: Alle nicht gespeicherten Änderungen gehen verloren.","MESSAGE_Confirm_exit_non_dirty":"Warnung: Möglicherweise gehen aktuelle Ansichtsinformationen verloren."},"modal":{"BUTTON_Ok":"OK","BUTTON_Yes":"Ja","BUTTON_No":"Nein","BUTTON_Apply":"Anwenden","TITLE_Info":"Information","TITLE_Confirmation":"Bestätigung","TITLE_Warning":"Warnung","TITLE_Error":"Fehler","TITLE_Success":"Erfolg"},"wizard":{"BUTTON_Next":"Weiter","BUTTON_Previous":"Zurück","BUTTON_FINISH":"Fertig stellen","BUTTON_Page_action_menu":"Aktionsmenü \'Seite\'","TOOLTIP_Page_action_menu":"Aktionsmenü \'Seite\'"},"mashup":{"MESSAGE_Mashup_error":"Die Anwendung reagiert nicht. Wenden Sie sich an den Systemadministrator."},"validation":{"ERROR_mandatory":"Erforderlich"},"datatypevalidation":{"ERROR_negative_unsupported":"Der Eingabewert darf nicht negativ sein.","ERROR_zero_unsupported":"Eingabewert darf nicht null sein.","ERROR_decimal_unsupported":"Der Eingabewert darf keine Dezimalziffern enthalten.","ERROR_min_value_limit":"Der Eingabewert ist kleiner als der Minimalwert.","ERROR_max_value_exceeded":"Eingabewert übersteigt Maximalwert.","ERROR_decimal_max":"Maximal unterstützte Anzahl von Dezimalstellen: ","ERROR_string_max":"Der Eingabewert übersteigt die maximale unterstützte Länge.","ERROR_invalid_email":"Die Eingabe enthält ein ungültiges E-Mail-Format.","ERROR_invalid_phone_format":"Die Eingabe enthält ein ungültiges Telefonnummernformat.","ERROR_invalid_char_cc_num":"Die Kreditkartennummer enthält ungültige Zeichen.","error_input_does_not_conform_pattern":"Eingabe enthält ungültige Zeichen."},"datefilter":{"LABEL_From_Date":"Von","LABEL_To_Date":"Bis","LABEL_Date_Filter_Options":"Optionen für Datumsfilter","LABEL_last30Days":"Letzte 30 Tage","LABEL_thisMonth":"Dieser Monat","LABEL_lastMonth":"Letzter Monat","LABEL_last3Months":"Letzte 3 Monate","LABEL_last6Months":"Letzte 6 Monate","LABEL_lastYear":"Letztes Jahr","LABEL_last7Days":"Letzte 7 Tage","LABEL_today":"Heute","LABEL_yesterday":"Gestern","LABEL_tomorrow":"Morgen","LABEL_next7Days":"Nächste 7 Tage","LABEL_nextMonth":"Nächster Monat","ERROR_invalid_format":"Ungültiges Datumsformat eingegeben","ERROR_date_missing":"Zum Filtern beide Daten auswählen/eingeben","ERROR_toDate_lesser":"Enddatum darf nicht vor Anfangsdatum liegen","ERROR_date_greater_than_range":"Eingegebene Datumsangaben größer oder kleiner als zulässiger Bereich"},"dialchart":{"LABEL_blankArea":"Leerer Bereich","LABEL_noData":"Keine anzuzeigenden Daten"},"barcode":{"MESSAGE_No_barcode_field":"Kein Feld für Barcode-Scan in aktueller Ansicht gefunden","MESSAGE_Multiple_barcode_field":"Es sind mehrere Scanfelder vorhanden. Legen Sie den Fokus auf das erforderliche Scanfeld und scannen Sie erneut"},"shipmentFilter":{"ACTION_Refine":"Versandliste eingrenzen","TITLE_Filter":"Eingrenzen","LABEL_Shipment_date":"Versanddatum","LABEL_Shipment_type":"Versandart","LABEL_Shipment_status":"Versandstatus","LABEL_Shipment_carrier":"Spediteur","LABEL_Shipment_source":"Quelle","LABEL_AnyCarrier":"Jeder Spediteur","ACTION_Apply":"Anwenden","ACTION_ClearAll":"Alle löschen","ACTION_Close_Filter_Panel":"Klicken Sie, um die Filteranzeige zu schließen","placeholderFormat":"MMM TT,JJJJ","TITLE_Expand":"Einblenden","TITLE_Collapse":"Ausblenden","source":{"LABEL_All":"Alle","LABEL_Vendor":"Anbieter","LABEL_Store":"Transfer Geschäft nach Geschäft","LABEL_Warehouse":"Lager"},"type":{"LABEL_All":"Alle","LABEL_Special_order":"Vertriebsauftrag","LABEL_Inventory":"Bestand"}},"notes":{"LABEL_Mark_Important":"Als wichtig markieren","LABEL_Comments":"Hinweise (${count})","LABEL_Enter_Note":"Hinweis hinzufügen (max. 2000 Zeichen)","noTextError":"Kein Hinweis zum Hinzufügen!","maxlengthError":"Hinweis überschreitet die zulässige Begrenzung von 2000 Zeichen","LABEL_System_Generated_Note":"Vom System generiert"},"orderFilter":{"LABEL_ProductPictures":"Produktimages","LABEL_Purchased":"Gekauft","LABEL_Recommendations":"Empfehlungen"},"productlist":{"TOOLTIP_Open_product_details":"Klicken Sie, um Details zum Produkt ${itemId} anzuzeigen"},"batchFilter":{"ACTION_Refine":"Batchliste eingrenzen","ACTION_RefineBatchLines":"Batchpositionen eingrenzen"},"shipmentorderbarcode":{"LABEL_ScanPickBarCode":"Auftrags- oder Sendungsnr. einscannen oder eingeben","TOOLTIP_SearchPickOrder":"Auftrags- oder Sendungsnr. einscannen oder eingeben","MSG_InvalidBarCodeData":"Scannen oder geben Sie eine gültige Auftragsnummer oder Sendungsnummer ein"},"quantity":{"ACTION_IncreaseQty":"Menge erhöhen","ACTION_DecreaseQty":"Menge verringern","MESSAGE_MaxQuantityError":"Die eingegebene Menge überschreitet die verfügbare Menge.","MESSAGE_LesserMinQtyError":"Menge ist kleiner als Mindestmenge.","MESSAGE_NegativeQtyError":"Menge kann nicht kleiner als null sein."},"portlet":{"MESSAGE_OldSpecPortlets":"Personabasierte Startseite weist alte Spezifikationsportlets auf. Diese werden am Ende hinzugefügt."},"timeslot":{"MESSAGE_No_slots_available":"Keine Zeitfenster verfügbar.","LABEL_Avaiable_slot":"Verfügbare Zeitfenster","TOOLTIP_Time_slot":"Klicken Sie, um das Zeitfenster ${timerange} auszuwählen"},"calendar":{"MSG_MaxDateLimitExceeded":"Neue Auswahl überschreitet das maximal auswählbare Datum für Termine. Die frühere Auswahl wird beibehalten.","ACTION_previousMonth":"Vorheriger Monat","ACTION_nextMonth":"Nächster Monat","ACTION_previousWeek":"Vorherige Woche","ACTION_nextWeek":"Nächste Woche","LABEL_eventDuration":"${startTime} - ${endTime}","TITLE_allEventsHeader":"${startTime} - ${endTime}"}}');
}]);

angular.module('isc.shared').run(['iscL10nCache', function(iscL10nCache) {
  iscL10nCache.put('./shared/nls/de/shared_a11y.nls.json',
    '{"globals":{"MESSAGE_Loading":"Processing"},"miniCart":{"aria_miniCartRegion":"Mini Cart","aria_miniCartPreview":"Mini Cart Preview","aria_miniCartShowPopover":"Mini Cart Show Popover","aria_miniCartShowPreview":"Mini Cart Show Preview","aria_removeProduct":"Remove Product ${itemId}","aria_productsInCart":"products in cart. Click to view mini cart."},"lookup":{"aria_lookupRegion":"Lookup and Search","aria_globalLookupRegion":"Global Lookup and Search","aria_returnLookupRegion":"Returns Lookup and Search","aria_inStoreReceivingLookupRegion":"Instore Receiving Lookup and Search"},"shipmentFilter":{"aria_shipmentListFilterRegion":"Shipment List Filter","aria_shipmentListShowPopover":"Click to view Shipment List Filter","aria_shipmentListFilterOptionsRegion":"Shipment List Filter Options","aria_shipmentFilterButton":"Refine Shipment List","aria_shipmentListFilterCriteriaRegion":"Shipment List Filter Criteria","aria_accordionClickShipmentDate":"Click to toggle Shipment Date filter options panel","aria_accordionClickShipmentStatus":"Click to toggle Shipment Status filter options panel","aria_accordionClickShipmentChannel":"Click to toggle Shipment Channel filter options panel"},"notes":{"aria_notesTextbox":"Enter Note","aria_notesList":"Notes List","aria_addNote":"Add Note","aria_noteIsImportant":"Important Note","aria_addNoteButton":"Click to add a note"},"productlist":{"aria_Open_product_details":"Click to show product details of ${itemId}","aria_UnitPrice":"Unit Price","aria_ListPrice":"List Price"},"shipmentorderbarcode":{"aria_ACTION_Scan_Order_Shipment":"Click to search for Shipments"},"quantity":{"aria_ACTION_DecreaseQty":"Click to decrease quantity by 1","aria_ACTION_IncreaseQty":"Click to increase quantity by 1"},"calendar":{"aria_TITLE_allEventsHeader":"List of appointments between the selected time"}}');
}]);