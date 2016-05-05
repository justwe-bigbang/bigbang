#!/bin/bash

checkuser() {
    user=`id -nu`
    if [ ${user} != 'app' ]
    then
        echo "Stop! Only user app can run this script!"
        exit 3
    fi
}
checkuser
cd `dirname $0`/..
BASE_HOME="`pwd`"
PROJECT_NAME=`basename $BASE_HOME`
PROJECT_HOME="/data/project/${PROJECT_NAME}"
LOG_HOME="/data/logs/$PROJECT_NAME"
CORE_HOME="/data/coredump/$PROJECT_NAME"
TMP_HOME="$PROJECT_HOME/tmp"
CONF_HOME="$PROJECT_HOME/conf"
PIDFILE="$TMP_HOME/service.pid"
STDOUT_LOG="$LOG_HOME/service.log"

export JAVA_HOME="/opt/java"
export LD_LIBRARY_PATH=$PROJECT_HOME/lib:$LD_LIBRARY_PATH
export PATH=${JAVA_HOME}/bin:$PATH
if [ -z "$JAVA" ] ; then
  JAVA=$(which java)
fi
if [ -z "$JAVA" ]; then
    echo "Cannot find a Java JDK. Please set either set JAVA or put java (>=1.8) in your PATH." 2>&1
    exit 1
fi
MEMORY=`free -m | awk '/Mem/{print $2}'`
if [ $MEMORY -gt 15000 ];then
    JVM_HEAP="8192"
elif [ $MEMORY -gt 7500 ];then
    JVM_HEAP="4096"
elif [ $MEMORY -gt 3500 ];then
    JVM_HEAP="2048"
elif [ $MEMORY -gt 1800 ];then
    JVM_HEAP="1024"
else
    JVM_HEAP="512"
fi
JVM_EDEN=$((JVM_HEAP/2))
if ! netstat -tln 2>&1 | grep ':7777' >/dev/null 2>&1; then
    JMX_PORT=7777
else
    JMX_PORT=$((RANDOM%10+7780))
fi
JAVA_VERSION=`java -version 2>&1 |awk 'NR==1{ gsub(/"/,""); print $3 }'`
if [[ "$JAVA_VERSION" > "1.8" ]]; then
    JAVA_OPTS="-server -Xms${JVM_HEAP}m -Xmx${JVM_HEAP}m -Xss256k -XX:SurvivorRatio=8 -XX:+UseG1GC -XX:MaxGCPauseMillis=200"
else
    JAVA_OPTS="-server -Xms${JVM_HEAP}m -Xmx${JVM_HEAP}m -Xmn${JVM_EDEN}m -XX:SurvivorRatio=8 -XX:PermSize=256m -XX:MaxPermSize=256m -Xss256k -XX:-UseAdaptiveSizePolicy -XX:MaxTenuringThreshold=15 -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -XX:+CMSParallelRemarkEnabled -XX:+CMSPermGenSweepingEnabled -XX:+UseFastAccessorMethods -XX:+CMSClassUnloadingEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70"
fi
JAVA_OPTS="${JAVA_OPTS} -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$LOG_HOME/java.hprof -XX:ErrorFile=$LOG_HOME/hs_err_pid_%p.log"
JAVA_OPTS="${JAVA_OPTS} -verbose:gc -Xloggc:$LOG_HOME/gc.log -XX:+PrintHeapAtGC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintTenuringDistribution -XX:+PrintGCApplicationStoppedTime"
JAVA_OPTS="${JAVA_OPTS} -XX:-UseCompressedOops"
JAVA_OPTS="${JAVA_OPTS} -Djava.awt.headless=true -Dfile.encoding=UTF-8"
JAVA_OPTS="${JAVA_OPTS} -Djava.net.preferIPv4Stack=true -Dsun.net.client.defaultConnectTimeout=10000 -Dsun.net.client.defaultReadTimeout=30000"
JAVA_OPTS="${JAVA_OPTS} -Dproject.name=$PROJECT_NAME -Djava.io.tmpdir=$TMP_HOME"
JAVA_OPTS="${JAVA_OPTS} -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.port=$JMX_PORT -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"

export JAVA_OPTS
ulimit -c unlimited

case "$#"
in
0 )
    ;;
1 )
    if [ "$1" = "debug" ]; then
        DEBUG_PORT=$2
        DEBUG_SUSPEND="n"
        JAVA_DEBUG_OPT="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=$DEBUG_PORT,server=y,suspend=$DEBUG_SUSPEND"
    fi;;
* )
    echo "THE PARAMETERS MUST BE TWO OR LESS.PLEASE CHECK AGAIN."
    exit;;
esac
if [ ! -d "$TMP_HOME" ];then
    mkdir $TMP_HOME
fi
if [ -f "$PIDFILE" ]; then
    PID=`cat $PIDFILE`
    if ps auxfwww | grep -w $PID | grep -v grep >/dev/null 2>&1;then
        echo "service is running. Please run stop.sh first,then start.sh. exists"
        exit 1
    else
       echo "found service.pid, but $PROJECT_NAME isn't runnnging, will start it!"
       rm -f $PIDFILE
    fi
fi
if [ -f "$STDOUT_LOG" ]; then
    mv -f $STDOUT_LOG $STDOUT_LOG.`date '+%Y%m%d%H%M%S'`
fi

#------------user defined------------------
service_configurationFile=${CONF_HOME}/logback.xml
SERVICE_OPTS="-Dprops.path=${PROJECT_HOME}/conf -Dservice.configurationFile=$service_configurationFile"
if [ -e $service_configurationFile ]; then
        MAIN_CLASS="com.youzan.ic.delivery.ApplicationBootstrap"
        for i in ${PROJECT_HOME}/lib/* ${PROJECT_HOME}/dep/*;
            do CLASSPATH=$i:"$CLASSPATH";
        done
        CLASSPATH="${CONF_HOME}:$CLASSPATH";
        echo "cd to ${PROJECT_HOME}/bin for workaround relative path"
        cd ${PROJECT_HOME}/bin
        echo LOG CONFIGURATION : $service_configurationFile
        echo CLASSPATH:$CLASSPATH
        nohup $JAVA $JAVA_OPTS $JAVA_DEBUG_OPT $SERVICE_OPTS -classpath $CLASSPATH $MAIN_CLASS 1>>$STDOUT_LOG 2>&1 &
        echo $! > ${PIDFILE}
        echo "cd to ${PROJECT_HOME}/bin for continue"
        cd ${PROJECT_HOME}/bin
else
        echo "log configration file($service_configurationFile) is not exist,please create then first!"
fi
#------------user defined------------------
sleep 10s
PID=`cat ${PIDFILE}`
if ps auxfwww | grep -w $PID | grep -v grep >/dev/null 2>&1;then
   echo "$PROJECT_NAME start Success"
else
   echo "$PROJECT_NAME Start Failed"
   rm -f $PIDFILE
   exit 1
fi