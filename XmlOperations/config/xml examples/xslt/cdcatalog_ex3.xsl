<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="xml" indent="no" omit-xml-declaration = "no"/>

  <xsl:template match="/">
    <html>
      <body><xsl:text/><h2>CD Collection</h2>
        <table border="1">
          <tr bgcolor="lightblue">
            <th>

<xsl:text/>Title<xsl:text/>
</th>
            <th>Artist</th>
            <th>Price</th>
          </tr>
			<xsl:apply-templates select="//cd"/>
        </table>
      </body>
    </html>
  </xsl:template>

  <xsl:template match="cd">
	  <tr>
		<td>
		  <i><xsl:value-of select="title" /></i>
		</td>
		<td>
		  <xsl:value-of select="artist" />
		</td>
		<td>
			<xsl:if test="price > 10">
				<xsl:attribute name="style">
					<xsl:text>color:red</xsl:text>
			  </xsl:attribute>
			</xsl:if>
		  <xsl:value-of select="price" />
		</td>
	  </tr>
  </xsl:template>

</xsl:stylesheet>