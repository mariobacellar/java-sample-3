#!/bin/bash
set echo off

clear

JAVA_HOME="/appl/applications/Java/jdk1.7.0_75"

DIR_ACCENTURE_VSTS_EXTRATOR="/appl/applications/webapps/tmp_removeme/accenture-vsts-extrator"

JAVA_CLASSPATH="${DIR_ACCENTURE_VSTS_EXTRATOR}"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/activation-1.1.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/ant-1.8.2.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/ant-launcher-1.8.2.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/commons-codec-1.9.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/commons-email-1.5.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/commons-lang-2.6.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/commons-logging-1.1.1.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/gson-2.8.1.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/httpasyncclient-4.1.3.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/httpclient-4.5.2.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/httpcore-4.4.6.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/httpcore-nio-4.4.6.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/javax.mail-1.5.6.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/javax.mail-api-1.6.1.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/jcl-over-slf4j-1.7.12.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/log4j-1.2.17.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/logback-classic-1.1.3.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/logback-core-1.1.3.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/rest-client-legacy-1.3.4.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/slf4j-api-1.7.12.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/spring-2.5.6.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/tim-domain-restClientLegacy-1.3.3.jar"
JAVA_CLASSPATH="${JAVA_CLASSPATH}:${DIR_ACCENTURE_VSTS_EXTRATOR}/target/tim-service-restClientLegacy-1.3.4.jar"

JAVA_CLASS_TASK_EPICOS_EM_DEV_BRANCH_ATIVA="com.accenture.tim.vsts.task.TaskEpicosEmDevBranchAtiva"
JAVA_CMD="java -cp ${JAVA_CLASSPATH} ${JAVA_CLASS_TASK_EPICOS_EM_DEV_BRANCH_ATIVA}"
PATH="${JAVA_HOME}/bin:${PATH}"

$JAVA_CMD

set echo on
