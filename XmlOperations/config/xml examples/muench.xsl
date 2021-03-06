﻿<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:output method="xml" indent="yes" />

  <xsl:key name="contacts-by-surname" match="contact" use="surname" />

  <xsl:template match="/">
    <root>
      <!--	<xsl:for-each select="//contact[count(. | key('contacts-by-surname', surname)[1]) = 1]"> -->

      <xsl:for-each select="//contact[.=key('contacts-by-surname', surname)[1]]">
        <group>

          <name>
            <xsl:value-of select="surname" />
          </name>

          <xsl:for-each select="key('contacts-by-surname', surname)">
            <forename>
              <xsl:value-of select="forename" /> (<xsl:value-of select="title" />) </forename>
          </xsl:for-each>

        </group>
      </xsl:for-each>
    </root>
  </xsl:template>

</xsl:stylesheet>