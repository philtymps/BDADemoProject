/**
 * SciQuotingHelperBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.sforce.soap.schemas._class.scpq.SciQuotingHelper;

public class SciQuotingHelperBindingStub extends org.apache.axis.client.Stub implements com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SciQuotingHelperPortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[3];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getOpportunityAccountPrimaryContact");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "opportunityId"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ID"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "opportunityFields"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String[].class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "accountFields"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String[].class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "primaryContactFields"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String[].class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "sObject"));
        oper.setReturnClass(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "result"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("upsertQuote");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "sciQuote"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__SciQuote__c"), com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "quoteLines"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OpportunityLineItem"), com.sforce.soap.schemas._class.scpq.SciQuotingHelper.OpportunityLineItem[].class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__SciQuote__c"));
        oper.setReturnClass(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "result"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("upsertQuoteAndPrimaryQuoteLines");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "sciQuote"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__SciQuote__c"), com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "applyUpdateEvenIfTimestampUnchanged"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "quoteLines"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__PrimaryQuoteLine__c"), com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__PrimaryQuoteLine__c[].class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__SciQuote__c"));
        oper.setReturnClass(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "result"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

    }

    public SciQuotingHelperBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public SciQuotingHelperBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public SciQuotingHelperBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        addBindings0();
        addBindings1();
    }

    private void addBindings0() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", ">AllowFieldTruncationHeader");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.AllowFieldTruncationHeader.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", ">CallOptions");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CallOptions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", ">DebuggingHeader");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.DebuggingHeader.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", ">DebuggingInfo");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.DebuggingInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", ">getOpportunityAccountPrimaryContact");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.GetOpportunityAccountPrimaryContact.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", ">getOpportunityAccountPrimaryContactResponse");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "sObject");
            qName2 = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "result");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", ">SessionHeader");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SessionHeader.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", ">upsertQuote");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.UpsertQuote.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", ">upsertQuoteAndPrimaryQuoteLines");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.UpsertQuoteAndPrimaryQuoteLines.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", ">upsertQuoteAndPrimaryQuoteLinesResponse");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.UpsertQuoteAndPrimaryQuoteLinesResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", ">upsertQuoteResponse");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.UpsertQuoteResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Account");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Account.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AccountContactRole");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.AccountContactRole.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AccountFeed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.AccountFeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AccountHistory");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.AccountHistory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AccountPartner");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.AccountPartner.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AccountShare");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.AccountShare.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ActivityHistory");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ActivityHistory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AdditionalNumber");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.AdditionalNumber.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ApexClass");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ApexClass.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ApexComponent");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ApexComponent.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ApexLog");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ApexLog.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ApexPage");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ApexPage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ApexTrigger");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ApexTrigger.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Approval");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Approval.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Asset");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Asset.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AssetFeed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.AssetFeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AssignmentRule");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.AssignmentRule.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "AsyncApexJob");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.AsyncApexJob.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Attachment");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Attachment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "BrandTemplate");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.BrandTemplate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "BusinessHours");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.BusinessHours.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "BusinessProcess");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.BusinessProcess.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CallCenter");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CallCenter.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Campaign");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Campaign.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CampaignFeed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CampaignFeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CampaignMember");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CampaignMember.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CampaignMemberStatus");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CampaignMemberStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CampaignShare");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CampaignShare.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Case");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper._case.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CaseComment");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CaseComment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CaseContactRole");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CaseContactRole.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CaseFeed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CaseFeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CaseHistory");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CaseHistory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CaseShare");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CaseShare.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CaseSolution");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CaseSolution.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CaseStatus");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CaseStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CaseTeamMember");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CaseTeamMember.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CaseTeamRole");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CaseTeamRole.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CaseTeamTemplate");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CaseTeamTemplate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CaseTeamTemplateMember");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CaseTeamTemplateMember.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CaseTeamTemplateRecord");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CaseTeamTemplateRecord.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CategoryData");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CategoryData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CategoryNode");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CategoryNode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ClientBrowser");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ClientBrowser.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CollaborationGroup");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CollaborationGroup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CollaborationGroupFeed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CollaborationGroupFeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CollaborationGroupMember");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CollaborationGroupMember.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Community");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Community.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Contact");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Contact.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContactFeed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContactFeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContactHistory");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContactHistory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContactShare");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContactShare.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContentDocument");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContentDocument.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContentDocumentFeed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContentDocumentFeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContentDocumentHistory");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContentDocumentHistory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContentVersion");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContentVersion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContentVersionHistory");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContentVersionHistory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContentWorkspace");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContentWorkspace.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContentWorkspaceDoc");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContentWorkspaceDoc.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Contract");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Contract.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContractContactRole");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContractContactRole.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContractFeed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContractFeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContractHistory");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContractHistory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ContractStatus");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ContractStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "CronTrigger");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.CronTrigger.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Document");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Document.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "DocumentAttachmentMap");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.DocumentAttachmentMap.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "EmailServicesAddress");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.EmailServicesAddress.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "EmailServicesFunction");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.EmailServicesFunction.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "EmailStatus");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.EmailStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "EmailTemplate");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.EmailTemplate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "EntitySubscription");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.EntitySubscription.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Event");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Event.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "EventAttendee");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.EventAttendee.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "EventFeed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.EventFeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "FeedComment");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.FeedComment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "FeedPost");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.FeedPost.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "FeedTrackedChange");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.FeedTrackedChange.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "FiscalYearSettings");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.FiscalYearSettings.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Folder");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Folder.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ForecastShare");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ForecastShare.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Group");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Group.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "GroupMember");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.GroupMember.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Holiday");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Holiday.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ID");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Idea");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Idea.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "IdeaComment");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.IdeaComment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "KnowledgeArticle");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.KnowledgeArticle.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Lead");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Lead.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings1() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LeadFeed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.LeadFeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LeadHistory");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.LeadHistory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LeadShare");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.LeadShare.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LeadStatus");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.LeadStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LogCategory");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.LogCategory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LogCategoryLevel");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.LogCategoryLevel.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LogInfo");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.LogInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LoginIp");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.LoginIp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "LogType");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.LogType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "MailmergeTemplate");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.MailmergeTemplate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Name");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Name.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "NewsFeed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.NewsFeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Note");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Note.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "NoteAndAttachment");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.NoteAndAttachment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OpenActivity");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.OpenActivity.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Opportunity");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Opportunity.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OpportunityCompetitor");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.OpportunityCompetitor.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OpportunityContactRole");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.OpportunityContactRole.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OpportunityFeed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.OpportunityFeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OpportunityFieldHistory");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.OpportunityFieldHistory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OpportunityHistory");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.OpportunityHistory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OpportunityLineItem");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.OpportunityLineItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OpportunityPartner");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.OpportunityPartner.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OpportunityShare");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.OpportunityShare.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OpportunityStage");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.OpportunityStage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Organization");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Organization.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "OrgWideEmailAddress");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.OrgWideEmailAddress.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Partner");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Partner.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "PartnerRole");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.PartnerRole.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Period");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Period.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Pricebook2");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Pricebook2.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Pricebook2History");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Pricebook2History.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "PricebookEntry");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.PricebookEntry.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ProcessInstance");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ProcessInstance.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ProcessInstanceHistory");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ProcessInstanceHistory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ProcessInstanceStep");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ProcessInstanceStep.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "ProcessInstanceWorkitem");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.ProcessInstanceWorkitem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Product2");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Product2.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Product2Feed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Product2Feed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Profile");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Profile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueryResult");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueryResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "QueueSobject");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.QueueSobject.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "RecordType");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.RecordType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Scontrol");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scontrol.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__CPQParameters__c");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__CPQParameters__c.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__CPQParameters__Feed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__CPQParameters__Feed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__PrimaryQuoteLine__c");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__PrimaryQuoteLine__c.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__PrimaryQuoteLine__Feed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__PrimaryQuoteLine__Feed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__SciQuote__c");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__SciQuote__Feed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__Feed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__Sterling_Parameter__c");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__Sterling_Parameter__c.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "scpq__Sterling_Parameter__Feed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__Sterling_Parameter__Feed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Site");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Site.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SiteFeed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SiteFeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SiteHistory");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SiteHistory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "sObject");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Solution");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Solution.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SolutionFeed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SolutionFeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SolutionHistory");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SolutionHistory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "SolutionStatus");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SolutionStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "StaticResource");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.StaticResource.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Task");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Task.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "TaskFeed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.TaskFeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "TaskPriority");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.TaskPriority.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "TaskStatus");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.TaskStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "User");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.User.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "UserFeed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.UserFeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "UserLicense");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.UserLicense.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "UserPreference");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.UserPreference.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "UserProfileFeed");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.UserProfileFeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "UserRole");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.UserRole.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "Vote");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Vote.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "WebLink");
            cachedSerQNames.add(qName);
            cls = com.sforce.soap.schemas._class.scpq.SciQuotingHelper.WebLink.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject[] getOpportunityAccountPrimaryContact(java.lang.String opportunityId, java.lang.String[] opportunityFields, java.lang.String[] accountFields, java.lang.String[] primaryContactFields) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "getOpportunityAccountPrimaryContact"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {opportunityId, opportunityFields, accountFields, primaryContactFields});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.sforce.soap.schemas._class.scpq.SciQuotingHelper.SObject[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c upsertQuote(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c sciQuote, com.sforce.soap.schemas._class.scpq.SciQuotingHelper.OpportunityLineItem[] quoteLines) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "upsertQuote"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {sciQuote, quoteLines});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c) org.apache.axis.utils.JavaUtils.convert(_resp, com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c upsertQuoteAndPrimaryQuoteLines(com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c sciQuote, boolean applyUpdateEvenIfTimestampUnchanged, com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__PrimaryQuoteLine__c[] quoteLines) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://soap.sforce.com/schemas/class/scpq/SciQuotingHelper", "upsertQuoteAndPrimaryQuoteLines"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {sciQuote, new java.lang.Boolean(applyUpdateEvenIfTimestampUnchanged), quoteLines});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c) org.apache.axis.utils.JavaUtils.convert(_resp, com.sforce.soap.schemas._class.scpq.SciQuotingHelper.Scpq__SciQuote__c.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
