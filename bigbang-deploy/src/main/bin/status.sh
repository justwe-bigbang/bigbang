#!/bin/sh
APP_MAIN="com.youzan.ic.delivery.ApplicationBootstrap"

deliveryPID=0

getDeliveryPID(){
    javaps=`jps -l | grep $APP_MAIN`
    if [ -n "$javaps" ]; then
        deliveryPID=`echo $javaps | awk '{print $1}'`
    else
        deliveryPID=0
    fi
}

getServerStatus(){
    getDeliveryPID
    echo "================================================================================================================"
    if [ $deliveryPID -ne 0 ]; then
        echo "$APP_MAIN is running(PID=$deliveryPID)"
        echo "================================================================================================================"
    else
        echo "$APP_MAIN is not running"
        echo "================================================================================================================"
    fi
}

getServerStatus