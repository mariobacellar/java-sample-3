xquery version "1.0";
 
declare variable $sourceFilePath as xs:string external;

(:let $my-doc := doc('file://d:/work/code/Xml Operations/example/xquery/terms.xml'):)

let $my-doc := doc($sourceFilePath)

return
<html>
    <head>
        <title>Terms</title>
    </head>
    <body>
    <table border="1">
    <thead>
      <tr>
          <th>Term</th> 
          <th>Definition</th>
          <th>price</th>
      </tr>
    </thead>
    <tbody>{
         for $term in $my-doc/terms/term
         return            
         <tr>       
           <td>{$term/term-name/text()}</td>
           <td>{$term/definition/text()}</td>
           <td>{$term/term-name/text()}</td>
           <td>{$term/definition/text()}</td>
           <td>{$term/price/text()}</td>
         </tr>
       }</tbody>
     </table>
   </body>
</html>