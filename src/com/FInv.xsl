<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:template match="/">
  <answers>
     <xsl:apply-templates select="//Assignment"/>
     </answers>
  </xsl:template>
  
  <xsl:template match="Assignment">
    <answer>
   <qty>
<xsl:value-of select="@Quantity"/>
</qty>
<from>
<xsl:value-of select="@ShipNode"/>
</from>
</answer>
  </xsl:template>
</xsl:stylesheet>
