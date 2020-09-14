#!/bin/bash

export SWT_GTK3=0

# do not delete the following line, otherwise it will not work at system startup !
cd /home/bogdan/work/code/java/programe/XmlOperations

java -Dlog4j.configurationFile=log4j2.json -cp ./*:$CLASSPATH bogdanrechi.xml.operations.MainWindow
