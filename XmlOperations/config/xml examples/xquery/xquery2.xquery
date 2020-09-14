declare variable $sourceFilePath as xs:string external;

let $count := count(doc($sourceFilePath)//books/book[xs:decimal(price) gt 50.00])

return
<result>
	<count>{$count}</count>
	{
		for $book in doc($sourceFilePath)//books/book
		   let $title := $book/title/text()
		   let $price := $book/price/text()
		   where xs:decimal($price) gt 50.00
		   order by $title
		   return
		      <book>
		         <title>{$title}</title>
		         <price>{$price}</price>
		      </book>
	}
</result>