<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="xml" indent="yes"/>
  <xsl:strip-space elements="*"/>
  <xsl:template match="//Item">
    <xsl:element name="Item">
      <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>
      <xsl:attribute name="ItemID">
        <xsl:value-of select="./@ItemID"/>
      </xsl:attribute>
      <xsl:attribute name="Quantity">25</xsl:attribute>
      <xsl:attribute name="SupplyType">ONHAND</xsl:attribute>
      <xsl:attribute name="Availability">TRACK</xsl:attribute>
      <xsl:attribute name="AdjustmentType">ADJUSTMENT</xsl:attribute>
      <xsl:attribute name="OrganizationCode">AuroraB2B</xsl:attribute>
      <xsl:attribute name="ShipNode">AB2B_Store_1</xsl:attribute>
    </xsl:element>
    <xsl:element name="Item">
      <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>
      <xsl:attribute name="ItemID">
        <xsl:value-of select="./@ItemID"/>
      </xsl:attribute>
      <xsl:attribute name="Quantity">25</xsl:attribute>
      <xsl:attribute name="SupplyType">ONHAND</xsl:attribute>
      <xsl:attribute name="Availability">TRACK</xsl:attribute>
      <xsl:attribute name="AdjustmentType">ADJUSTMENT</xsl:attribute>
      <xsl:attribute name="OrganizationCode">AuroraB2B</xsl:attribute>
      <xsl:attribute name="ShipNode">AB2B_Store_3</xsl:attribute>
    </xsl:element>
    <xsl:element name="Item">
      <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>
      <xsl:attribute name="ItemID">
        <xsl:value-of select="./@ItemID"/>
      </xsl:attribute>
      <xsl:attribute name="Quantity">25</xsl:attribute>
      <xsl:attribute name="SupplyType">ONHAND</xsl:attribute>
      <xsl:attribute name="Availability">TRACK</xsl:attribute>
      <xsl:attribute name="AdjustmentType">ADJUSTMENT</xsl:attribute>
      <xsl:attribute name="OrganizationCode">AuroraB2B</xsl:attribute>
      <xsl:attribute name="ShipNode">AB2B_Store_7</xsl:attribute>
    </xsl:element>
    <xsl:element name="Item">
      <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>
      <xsl:attribute name="ItemID">
        <xsl:value-of select="./@ItemID"/>
      </xsl:attribute>
      <xsl:attribute name="Quantity">25</xsl:attribute>
      <xsl:attribute name="SupplyType">ONHAND</xsl:attribute>
      <xsl:attribute name="Availability">TRACK</xsl:attribute>
      <xsl:attribute name="AdjustmentType">ADJUSTMENT</xsl:attribute>
      <xsl:attribute name="OrganizationCode">AuroraB2B</xsl:attribute>
      <xsl:attribute name="ShipNode">AB2B_Store_8</xsl:attribute>
    </xsl:element>
    <xsl:element name="Item">
      <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>
      <xsl:attribute name="ItemID">
        <xsl:value-of select="./@ItemID"/>
      </xsl:attribute>
      <xsl:attribute name="Quantity">25</xsl:attribute>
      <xsl:attribute name="SupplyType">ONHAND</xsl:attribute>
      <xsl:attribute name="Availability">TRACK</xsl:attribute>
      <xsl:attribute name="AdjustmentType">ADJUSTMENT</xsl:attribute>
      <xsl:attribute name="OrganizationCode">AuroraB2B</xsl:attribute>
      <xsl:attribute name="ShipNode">AB2B_Store_4</xsl:attribute>
    </xsl:element>
    <xsl:element name="Item">
      <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>
      <xsl:attribute name="ItemID">
        <xsl:value-of select="./@ItemID"/>
      </xsl:attribute>
      <xsl:attribute name="Quantity">25</xsl:attribute>
      <xsl:attribute name="SupplyType">ONHAND</xsl:attribute>
      <xsl:attribute name="Availability">TRACK</xsl:attribute>
      <xsl:attribute name="AdjustmentType">ADJUSTMENT</xsl:attribute>
      <xsl:attribute name="OrganizationCode">AuroraB2B</xsl:attribute>
      <xsl:attribute name="ShipNode">AB2B_Store_5</xsl:attribute>
    </xsl:element>
    <xsl:element name="Item">
      <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>
      <xsl:attribute name="ItemID">
        <xsl:value-of select="./@ItemID"/>
      </xsl:attribute>
      <xsl:attribute name="Quantity">25</xsl:attribute>
      <xsl:attribute name="SupplyType">ONHAND</xsl:attribute>
      <xsl:attribute name="Availability">TRACK</xsl:attribute>
      <xsl:attribute name="AdjustmentType">ADJUSTMENT</xsl:attribute>
      <xsl:attribute name="OrganizationCode">AuroraB2B</xsl:attribute>
      <xsl:attribute name="ShipNode">AB2B_Store_9</xsl:attribute>
    </xsl:element>
    <xsl:element name="Item">
      <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>
      <xsl:attribute name="ItemID">
        <xsl:value-of select="./@ItemID"/>
      </xsl:attribute>
      <xsl:attribute name="Quantity">100</xsl:attribute>
      <xsl:attribute name="SupplyType">ONHAND</xsl:attribute>
      <xsl:attribute name="Availability">TRACK</xsl:attribute>
      <xsl:attribute name="AdjustmentType">ADJUSTMENT</xsl:attribute>
      <xsl:attribute name="OrganizationCode">AuroraB2B</xsl:attribute>
      <xsl:attribute name="ShipNode">AuroB2B_WH1</xsl:attribute>
    </xsl:element>
    <xsl:element name="Item">
      <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>
      <xsl:attribute name="ItemID">
        <xsl:value-of select="./@ItemID"/>
      </xsl:attribute>
      <xsl:attribute name="Quantity">100</xsl:attribute>
      <xsl:attribute name="SupplyType">ONHAND</xsl:attribute>
      <xsl:attribute name="Availability">TRACK</xsl:attribute>
      <xsl:attribute name="AdjustmentType">ADJUSTMENT</xsl:attribute>
      <xsl:attribute name="OrganizationCode">AuroraB2B</xsl:attribute>
      <xsl:attribute name="ShipNode">AuroB2B_WH3</xsl:attribute>
    </xsl:element>
    <xsl:element name="Item">
      <xsl:attribute name="UnitOfMeasure">EACH</xsl:attribute>
      <xsl:attribute name="ItemID">
        <xsl:value-of select="./@ItemID"/>
      </xsl:attribute>
      <xsl:attribute name="Quantity">100</xsl:attribute>
      <xsl:attribute name="SupplyType">ONHAND</xsl:attribute>
      <xsl:attribute name="Availability">TRACK</xsl:attribute>
      <xsl:attribute name="AdjustmentType">ADJUSTMENT</xsl:attribute>
      <xsl:attribute name="OrganizationCode">AuroraB2B</xsl:attribute>
      <xsl:attribute name="ShipNode">AuroB2B_WH4</xsl:attribute>
    </xsl:element>
  </xsl:template>
  <xsl:template match="/">
    <xsl:element name="Items">
      <xsl:apply-templates/>
    </xsl:element>
  </xsl:template>
</xsl:stylesheet>
