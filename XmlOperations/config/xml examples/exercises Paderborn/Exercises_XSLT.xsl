<?xml version="1.0" encoding="UTF-8"?> 
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<!-- In this excercise you will have to use XPath to select the appropriate parts of the given XML document.
      For our solution we used:
	
	XSL Elements
	xsl:value-of == Output the text values of the specified node and all its descendants
	xsl:apply-templates == Applies a template to the current element or its child nodes
	xsl:if == You can use conditional tests either by xsl:if or the xsl:choose/xsl:when statements. Within this excercise you will probably not need to use xsl:choose
	xsl:text == Output text to the result. Use this element if you want to omit whitespaces within the output
	xsl:variable == Store values for later use. Attention: There is a big difference between variables in programming languages (such as Java) and XSLT! Here you can set each variable only ONCE within a context! Values cannot be changed afterwards.
	
	XSLT Functions
	div == You can use some mathematical operations within XSL. While '+', '-' and '*' can be specified within the XSLT expression you have to use 'div' to signal division ('/' has another meaning here)
	not(...) == This function returns the inverse of the passed value. 
	count(...) == This function is used to count the number of elements that match a given XPath expression
	sum(...) == Use this function to calculate the sum of all elements that match a given XPath expression
	&lt; == You must not use '<' or '>' within an elements attributes, so you have to "encode" these signs by '&lt;' and '&gt;' if you want to use them. 
	&gt;  == Use this within an attribute to use an greater-than-test.
	
	
//-->
<xsl:output method="html" />
	<xsl:template match="/">
		<html>
			<head>
				<title>XSLT Excercises</title>
			</head>
			<body>
			  <ol>
				<li><h4>Example: The name of the first amusement park within our document is: </h4></li> 
					 <xsl:apply-templates select="/rcdb/parc[1]/name" />	<br/><br/> 
								
				<li><h4>In Phantasialand you can ride the following coasters:</h4></li> 
				<!-- Attention: A parc has more than one coaster, separate them by "," if needed. Define a new template!//-->
				<xsl:apply-templates select="//parc[name='Phantasialand']/existingRollerCoasters" mode="phantasia" />
				<p/>
						    
				<li><h4>Which parcs do not have defunct coasters?</h4></li> 
				<!-- use not() //-->
				<xsl:apply-templates select="//parc[not(pastRollerCoasters)]/name" mode="names"/>
				<p/>

				
				<li><h4>The height of the highest coaster (actual or past) is: </h4></li> 
				<!-- Specify that there must not be another coaster that is higher //-->
				<xsl:for-each select="//height">
					<xsl:sort order="descending" data-type="number"/>
					<xsl:if test="position() = 1">
						<xsl:value-of select="."/>
					</xsl:if>
				</xsl:for-each>
				<p/>
				
				<li><h4>... and its name is: </h4></li> 
				<!-- Again: Allow multiple coaster to have the same height and separate them by "," if needed //-->
				<xsl:for-each select="//height">
					<xsl:sort order="descending" data-type="number"/>
					<xsl:if test="position() = 1">
						<xsl:call-template name="name-highest">
							<xsl:with-param name="height" select="."/>
						</xsl:call-template>
					</xsl:if>
				</xsl:for-each>
				<p/>
				
				<li><h4>How many coasters does "Heide Park" have?</h4></li>  
				<!-- Use the count(...) function here.  //-->
				<xsl:value-of select="count(//parc[name='Heide-Park']//coaster)"/>
				<p/>
				
				<li><h4>What is the oldest functional coaster:</h4></li>  
				<!-- Some coasters do not have an <opened> node!  //-->
				<xsl:for-each select="//parc//coaster/opened">
					<xsl:sort order="ascending" data-type="number"/>
					<xsl:if test="position() = 1">
						<xsl:call-template name="oldest-opened">
							<xsl:with-param name="opened" select="."/>
						</xsl:call-template>
					</xsl:if>
				</xsl:for-each>
				<p/>
									
				<li><h4>Which coaster has two names, and what the names? </h4></li> 
				<!-- Have a look at XPath axes specifiers//-->
				<ul>
					<xsl:apply-templates select="//parc//coaster[count(name) > 1]" mode="multi-names"/>
				</ul>
				<p/>
				
				<li><h4>What is wrong with this sort expression? Why does it not sort?</h4></li>
				<!-- Use for-each and sort//-->
						<xsl:for-each select="//coaster/name">	
							<xsl:sort select="name" order="ascending"/> 
							<xsl:value-of select="."/><br />	
     					</xsl:for-each>   
     		<p/>  					
				
				<li><h4>Do it correct: Give a list of all coasters alphabetically sorted: </h4></li>
				<!-- Use for-each and sort//-->
				<xsl:for-each select="//coaster/name">	
					<xsl:sort select="." order="ascending"/> 
					<xsl:value-of select="."/><br />	
				</xsl:for-each>  
     		<p/> 
     						
				<li><h4>*Give a list of all coasters and the persons they have not ridden them yet:</h4></li>  				
				<!-- Use a separate template and specify mode//-->
				<ul>
					<xsl:apply-templates select="/rcdb/parc//coaster/name" mode="coastercheck">
						<xsl:sort select="." order="ascending"/> 
					</xsl:apply-templates>
				</ul>
				<br />		
				
				<li><h4>*Give a list of all persons, the coasters they have ridden, and the parc where the coaster is located: </h4></li> 
				<!-- Use a separate template and specify mode//-->
				<ul>
					<xsl:apply-templates select="//person">
						<xsl:sort select="." order="ascending"/>
					</xsl:apply-templates>
				</ul>
				
				<li><h4>**What is the maximum number of coasters that a parc has? Return multiple parcs</h4></li>  
					<!-- There are 2 possibilities, either sort the list and pick the first one (does not work when two parcs have the same amount of coasters), 
					or use a variable definition, iterate over all existingRollerCoasters elements, and assign to the variable the number of coasters if 
					the number of parcs that have more coasters is 0.
				//-->							
				<xsl:for-each select="//existingRollerCoasters/coaster">
					<xsl:if test="position()=last()">
						<xsl:value-of select="last()"/>
					</xsl:if>
				</xsl:for-each>
				
			  </ol>
			</body>
		</html>
		
	</xsl:template>
	
	<xsl:template match="existingRollerCoasters" mode="phantasia">										
		<xsl:for-each select='coaster'>
			<xsl:if test='position() > 1'>, </xsl:if>

			<xsl:for-each select='name'>
				<xsl:if test='position() > 1'><i> or </i></xsl:if>
				<xsl:value-of select='.'/>
			</xsl:for-each>

		</xsl:for-each>
	</xsl:template>

	<xsl:template match="name" mode="names">
		<xsl:value-of select="."/>
		<br/>
	</xsl:template>

	<xsl:template name="name-highest">
		<xsl:param name="height"/>
		
		<xsl:for-each select="//coaster[height=$height]/name">
			<xsl:if test="position() > 1">, </xsl:if>
			<xsl:value-of select="."/>
		</xsl:for-each>
		<br/>
	</xsl:template>

	<xsl:template name="oldest-opened">
		<xsl:param name="opened"/>
		
		<xsl:for-each select="//parc//coaster[opened=$opened]">
			<xsl:if test="position() > 1">, </xsl:if>
			<xsl:value-of select="name"/>, <xsl:value-of select="opened"/>
		</xsl:for-each>
		<br/>
	</xsl:template>

	<xsl:template match="coaster" mode="multi-names">
		<li>
			<xsl:for-each select="name">
				<xsl:if test="position() > 1">, </xsl:if>
			
				<xsl:value-of select="."/>
			</xsl:for-each>
		</li>
	</xsl:template>

	<xsl:template match="name" mode="coastercheck">
		<xsl:variable name="currentCoaster" select="."/>
		
		<li>
			<xsl:value-of select="."/>
			<br/>
			
			<xsl:for-each select="//person[not(coaster=$currentCoaster)]/name">
				<xsl:if test="position()>1">
					<xsl:text>, </xsl:text>
				</xsl:if>
				<i><xsl:value-of select="."/></i>
			</xsl:for-each>
			<p/>
		</li>
	</xsl:template>
	
	<xsl:template match="person">
		<li>
			<xsl:value-of select="name"/>
			<br/>
			<i>
			<xsl:for-each select="coaster">
				<xsl:if test="position() > 1">, </xsl:if>
				<xsl:value-of select="."/>
				<xsl:variable name="coasterName" select="."/>
				(<xsl:value-of select="//coaster[name=$coasterName]/../../name"/>)
			</xsl:for-each>
			</i>
		</li>
		<p/>
	</xsl:template>
	
</xsl:stylesheet>
