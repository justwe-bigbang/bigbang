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
TMP_HOME="$PROJECT_HOME/tmp"
PIDFILE="$TMP_HOME/service.pid"
if [ ! -f "$PIDFILE" ];then
    echo "PIDFILE:$PIDFILE not found. exists"
    exit
fi
PID=`cat $PIDFILE`
if [ "$PID" == "" ] ; then
    echo "can not find process pid. exists"
    rm $PIDFILE
    exit
fi
if ! ps auxfwww | grep -w $PID | grep -v grep >/dev/null 2>&1;then
    echo "found service pid:$PID, but $PROJECT_NAME isn't runnnging. exists"
    exit
else
    echo -e "`hostname`: stopping $PROJECT_NAME $PID ... "
    kill $PID
    rm $PIDFILE
fi