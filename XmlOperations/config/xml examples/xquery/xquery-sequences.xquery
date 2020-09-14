declare variable $sourceFilePath as xs:string external;

let $seq := (1, 3, 5, 6, 8)

		for $item in $seq
		 return
		 	<item>
		 		{$item}
		 	</item>
