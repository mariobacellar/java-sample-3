#!/bin/bash
set echo off

clear

JAVA_HOME="/appl/applications/Java/jdk1.7.0_75"

DIR_ACCENTURE_VSTS_EXTRATOR="/appl/applications/webapps/tmp_removeme/accenture-vsts-extrator"

JAVA_CLASSPATH="${DIR_ACCENTURE_VSTS_EXTRATOR}"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/accenture-vsts-extrator-0.0.1-SNAPSHOT.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/activation-1.1.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/commons-codec-1.9.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/commons-email-1.5.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/commons-lang-2.6.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/commons-logging-1.1.1.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/gson-2.8.1.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/httpasyncclient-4.1.3.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/httpclient-4.5.2.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/httpcore-4.4.6.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/httpcore-nio-4.4.6.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/javax.mail-1.5.6.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/javax.mail-api-1.6.1.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/jcl-over-slf4j-1.7.12.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/logback-classic-1.1.3.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/logback-core-1.1.3.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/rest-client-legacy-1.3.4.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/slf4j-api-1.7.12.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/spring-2.5.6.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/tim-domain-restClientLegacy-1.3.3.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/tim-service-restClientLegacy-1.3.4.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/dependency/log4j-1.2.17.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/src/main/resources"

JAVA_CLASS_SENDEMAIL="com.accenture.tim.vsts.email.SendEmail"
JAVA_CMD="java -cp ${JAVA_CLASSPATH} ${JAVA_CLASS_SENDEMAIL}"
PATH="${JAVA_HOME}/bin:${PATH}"

$JAVA_CMD







set echo on
