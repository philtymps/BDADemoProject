/*******************************************************************************
* IBM Confidential
* OCO Source Materials
* IBM Sterling Order Management Store (5725-D10), IBM Order Management (5737-D18), IBM Store Engagement (5737D58)
* (C) Copyright IBM Corp. 2014, 2017 All Rights Reserved.
* The source code for this program is not published or otherwise divested of its trade secrets, irrespective of what has been deposited with the U.S. Copyright Office.
******************************************************************************/
angular.module('isc.shared').run(['iscL10nCache', function(iscL10nCache) {
  iscL10nCache.put('./shared/nls/fr/shared.nls.json',
    '{"globals":{"BUTTON_Cancel":"Annuler","BUTTON_Close":"Fermer","ERROR_invalid_input":"La valeur d\'entrée n\'est pas valide."},"shell":{"MESSAGE_Confirm_exit_dirty":"Avertissement : toute modification non sauvegardée sera perdue.","MESSAGE_Confirm_exit_non_dirty":"Avertissement : vous risquez de perdre les informations d\'affichage en cours."},"modal":{"BUTTON_Ok":"OK","BUTTON_Yes":"Oui","BUTTON_No":"Non","BUTTON_Apply":"Appliquer","TITLE_Info":"Informations","TITLE_Confirmation":"Confirmation","TITLE_Warning":"Avertissement","TITLE_Error":"Erreur","TITLE_Success":"Réussite"},"wizard":{"BUTTON_Next":"Suivant","BUTTON_Previous":"Précédent","BUTTON_FINISH":"Terminer","BUTTON_Page_action_menu":"Menu d\'action de page","TOOLTIP_Page_action_menu":"Menu d\'action de page"},"mashup":{"MESSAGE_Mashup_error":"L\'application ne répond pas. Contactez l\'administrateur système."},"validation":{"ERROR_mandatory":"Requis"},"datatypevalidation":{"ERROR_negative_unsupported":"La valeur d\'entrée ne doit pas être négative.","ERROR_zero_unsupported":"La valeur d\'entrée ne doit pas être zéro.","ERROR_decimal_unsupported":"La valeur d\'entrée ne doit pas contenir de décimale.","ERROR_min_value_limit":"La valeur d\'entrée est inférieure à la limite minimale.","ERROR_max_value_exceeded":"La valeur d\'entrée dépasse la limite maximale.","ERROR_decimal_max":"Le nombre maximal de caractères pris en charge après les décimales est : ","ERROR_string_max":"La valeur d\'entrée dépasse la longueur maximale prise en charge.","ERROR_invalid_email":"L\'entrée contient un format de courrier électronique non valide.","ERROR_invalid_phone_format":"L\'entrée contient un format de numéro de téléphone non valide.","ERROR_invalid_char_cc_num":"Le numéro de carte de crédit contient des caractères non valides.","error_input_does_not_conform_pattern":"L\'entrée contient des caractères non valides."},"datefilter":{"LABEL_From_Date":"Du","LABEL_To_Date":"Au","LABEL_Date_Filter_Options":"Options de filtre de date","LABEL_last30Days":"30 derniers jours","LABEL_thisMonth":"Mois en cours","LABEL_lastMonth":"Mois précédent","LABEL_last3Months":"3 derniers mois","LABEL_last6Months":"6 derniers mois","LABEL_lastYear":"L\'année dernière","LABEL_last7Days":"7 derniers jours","LABEL_today":"Aujourd\'hui","LABEL_yesterday":"Hier","LABEL_tomorrow":"Demain","LABEL_next7Days":"Les 7 prochains jours","LABEL_nextMonth":"Le mois prochain","ERROR_invalid_format":"Format de date entré non valide","ERROR_date_missing":"Sélectionner/Entrer deux dates pour filtrer","ERROR_toDate_lesser":"La date de fin ne peut pas être antérieure à la date de début","ERROR_date_greater_than_range":"Date(s) entrée(s)s postérieures ou antérieures à la plage autorisée"},"dialchart":{"LABEL_blankArea":"Zone vide","LABEL_noData":"Aucune donnée à afficher"},"barcode":{"MESSAGE_No_barcode_field":"Aucune zone Numérisation du code à barres trouvée dans la vue en cours","MESSAGE_Multiple_barcode_field":"Plusieurs zones de numérisation sont disponibles. Choisissez la zone de numérisation requise et recommencez"},"shipmentFilter":{"ACTION_Refine":"Affiner la liste des expéditions","TITLE_Filter":"Affiner","LABEL_Shipment_date":"Date d\'expédition","LABEL_Shipment_type":"Type d\'expédition","LABEL_Shipment_status":"État d\'expédition","LABEL_Shipment_carrier":"Transporteur","LABEL_Shipment_source":"Source","LABEL_AnyCarrier":"Un transporteur","ACTION_Apply":"Appliquer","ACTION_ClearAll":"Effacer tout","ACTION_Close_Filter_Panel":"Cliquez sur ce bouton pour fermer le panneau de filtrage","placeholderFormat":"MMM jj,aaaa","TITLE_Expand":"Développer","TITLE_Collapse":"Réduire","source":{"LABEL_All":"Toutes","LABEL_Vendor":"Fournisseur","LABEL_Store":"Transfert de magasin à magasin","LABEL_Warehouse":"Entrepôt"},"type":{"LABEL_All":"Tous","LABEL_Special_order":"Bon de commande (vendeur)","LABEL_Inventory":"Stock"}},"notes":{"LABEL_Mark_Important":"Marquer comme Important","LABEL_Comments":"Remarques (${count})","LABEL_Enter_Note":"Ajouter une remarque (2 000 caractères maxi)","noTextError":"Aucune remarque à ajouter !","maxlengthError":"La remarque dépasse la limite de 2 000 caractères","LABEL_System_Generated_Note":"Générée par le système"},"orderFilter":{"LABEL_ProductPictures":"Images de produits","LABEL_Purchased":"Acheté(s)","LABEL_Recommendations":"Recommandations"},"productlist":{"TOOLTIP_Open_product_details":"Cliquez pour afficher les détails du produit ${itemId}"},"batchFilter":{"ACTION_Refine":"Affiner la liste de lots","ACTION_RefineBatchLines":"Affiner les lignes de lot"},"shipmentorderbarcode":{"LABEL_ScanPickBarCode":"Numériser ou entrer un n° de commande ou d\'expédition","TOOLTIP_SearchPickOrder":"Numériser ou entrer un n° de commande ou d\'expédition","MSG_InvalidBarCodeData":"Numérisez ou entrez un numéro de commande ou d\'expédition valide"},"quantity":{"ACTION_IncreaseQty":"Augmenter la quantité","ACTION_DecreaseQty":"Diminuer la quantité","MESSAGE_MaxQuantityError":"La quantité entrée dépasse la quantité disponible.","MESSAGE_LesserMinQtyError":"La quantité est inférieure à la quantité minimale.","MESSAGE_NegativeQtyError":"La quantité ne peut pas être inférieure à zéro."},"portlet":{"MESSAGE_OldSpecPortlets":"La page d\'accueil basée sur une personne comporte des portlets de spécification anciens. Ajout de ceux-ci vers la fin."},"timeslot":{"MESSAGE_No_slots_available":"Aucun créneau disponible","LABEL_Avaiable_slot":"Créneaux disponibles","TOOLTIP_Time_slot":"Cliquez pour sélectionner le créneau horaire ${timerange}"},"calendar":{"MSG_MaxDateLimitExceeded":"La nouvelle sélection dépasse la date maximale sélectionnable pour les rendez-vous. La sélection précédente est conservée.","ACTION_previousMonth":"Mois précédent","ACTION_nextMonth":"Mois prochain","ACTION_previousWeek":"Semaine précédente","ACTION_nextWeek":"Semaine suivante","LABEL_eventDuration":"${startTime} - ${endTime}","TITLE_allEventsHeader":"${startTime} - ${endTime}"}}');
}]);

angular.module('isc.shared').run(['iscL10nCache', function(iscL10nCache) {
  iscL10nCache.put('./shared/nls/fr/shared_a11y.nls.json',
    '{"globals":{"MESSAGE_Loading":"Processing"},"miniCart":{"aria_miniCartRegion":"Mini Cart","aria_miniCartPreview":"Mini Cart Preview","aria_miniCartShowPopover":"Mini Cart Show Popover","aria_miniCartShowPreview":"Mini Cart Show Preview","aria_removeProduct":"Remove Product ${itemId}","aria_productsInCart":"products in cart. Click to view mini cart."},"lookup":{"aria_lookupRegion":"Lookup and Search","aria_globalLookupRegion":"Global Lookup and Search","aria_returnLookupRegion":"Returns Lookup and Search","aria_inStoreReceivingLookupRegion":"Instore Receiving Lookup and Search"},"shipmentFilter":{"aria_shipmentListFilterRegion":"Shipment List Filter","aria_shipmentListShowPopover":"Click to view Shipment List Filter","aria_shipmentListFilterOptionsRegion":"Shipment List Filter Options","aria_shipmentFilterButton":"Refine Shipment List","aria_shipmentListFilterCriteriaRegion":"Shipment List Filter Criteria","aria_accordionClickShipmentDate":"Click to toggle Shipment Date filter options panel","aria_accordionClickShipmentStatus":"Click to toggle Shipment Status filter options panel","aria_accordionClickShipmentChannel":"Click to toggle Shipment Channel filter options panel"},"notes":{"aria_notesTextbox":"Enter Note","aria_notesList":"Notes List","aria_addNote":"Add Note","aria_noteIsImportant":"Important Note","aria_addNoteButton":"Click to add a note"},"productlist":{"aria_Open_product_details":"Click to show product details of ${itemId}","aria_UnitPrice":"Unit Price","aria_ListPrice":"List Price"},"shipmentorderbarcode":{"aria_ACTION_Scan_Order_Shipment":"Click to search for Shipments"},"quantity":{"aria_ACTION_DecreaseQty":"Click to decrease quantity by 1","aria_ACTION_IncreaseQty":"Click to increase quantity by 1"},"calendar":{"aria_TITLE_allEventsHeader":"List of appointments between the selected time"}}');
}]);
