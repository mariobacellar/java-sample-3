﻿<xsl:stylesheet version = '1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>
<xsl:output method="xml" indent="yes"/>

<xsl:template match="/source/AAA"> 
	<table border="true">
		<tr>
		<td>
      <div style="color:darkblue">
        <b>
          <xsl:value-of select="name()"/> 
          <xsl:text> id=</xsl:text> 
				<i>
          <xsl:value-of select="@id"/> 
				</i>
        </b>
      </div> 
		</td>
		</tr>
	</table>
</xsl:template>

</xsl:stylesheet>
