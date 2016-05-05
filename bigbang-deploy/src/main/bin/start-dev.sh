#!/usr/bin/env bash
#-------------------------------------------------------------------------------------------------------------
#该脚本的为方便在IDE开发环境启动,自动打包部署
#直接在代码的目录中运行即可
#需要手工创建data目录并设置权限(为默认的有赞的diamond的日志目录)
#    #>sudo mkdir /data
#    #>sudo chmod a+w /data
#-------------------------------------------------------------------------------------------------------------

S0="${BASH_SOURCE[0]}"
localPath(){
DIRNAME="$( dirname "$S0")"

DIR="$( cd "$DIRNAME" && pwd)"

echo "$S0"
echo "$DIRNAME"
echo "$DIR"

PRO="$( cd "$DIRNAME/../../../../" && pwd)"
echo "project dir $PRO"
}
localPath

shutdown(){
   /bin/bash "$DIRNAME/shutdown.sh"
}

shutdown

compile(){
     source ~/.bash_profile
    cd "$DIRNAME/../../../../"
    echo "$PRO"
    mvn clean:clean package -Pdev -Dmaven.test.skip=true
}
compile




moveGz(){
    echo "$PRO/delivery-deploy/target/"
    mkdir  "$PRO/delivery-deploy/target/delivery-deploy"

    mv "$PRO/delivery-deploy/target/delivery-deploy.tar.gz" "$PRO/delivery-deploy/target/delivery-deploy"


    cd $PRO/delivery-deploy/target/delivery-deploy/ &&  tar -xzvf "delivery-deploy.tar.gz"
}
moveGz


#-------------------------------------------------------------------------------------------------------------
#该脚本的使用方式为-->[sh startup.sh]
#该脚本可在服务器上的任意目录下执行,不会影响到日志的输出位置等
#-------------------------------------------------------------------------------------------------------------
APP_MAIN="com.youzan.ic.delivery.ApplicationBootstrap"
APP_LOG="$PRO/delivery-deploy/target/delivery-deploy/logs"
APP_HOME="$PRO/delivery-deploy/target/delivery-deploy"
DEBUG_ARGS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"

mklogdir(){
    mkdir -p "$APP_LOG"

}
mklogdir
## set java path
if [ -z "$JAVA" ] ; then
  JAVA=$(which java)
fi

if [ -z "$JAVA" ]; then
  	echo "Cannot find a Java JDK. Please set either set JAVA or put java (>=1.8) in your PATH." 2>&2
    exit 1
fi
##-Ddruid.log.stmt.executableSql=true
##-Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n
JAVA_OPTS="-Duser.timezone=GMT+8 -server -Xms2048m -Xmx2048m -Xloggc:${APP_LOG}/gc.log"
SERVICE_OPTS="-Dprops.path=${APP_HOME}/conf -Dlogback.configurationFile=${APP_HOME}/conf/logback.xml $DEBUG_ARGS"

CLASSPATH=$APP_HOME/classes

for deliveryJar in "$APP_HOME"/lib/*.jar
do
   CLASSPATH="$CLASSPATH":"$deliveryJar"
done

for deliveryDepJar in "$APP_HOME"/dep/*.jar
do
   CLASSPATH="$CLASSPATH":"$deliveryDepJar"
done

deliveryPID=0

getDeliveryPID(){
    javaps=`$JAVA_HOME/bin/jps -l | grep $APP_MAIN`
    if [ -n "$javaps" ]; then
        deliveryPID=`echo $javaps | awk '{print $1}'`
    else
        deliveryPID=0
    fi
}

startup(){
    getDeliveryPID
    echo "================================================================================================================"
    if [ $deliveryPID -ne 0 ]; then
        echo "$APP_MAIN already started(PID=$deliveryPID)"
        echo "================================================================================================================"
    else
        echo -n "Starting $APP_MAIN"

        ##nohup $JAVA $JAVA_OPTS $SERVICE_OPTS -cp $CLASSPATH $APP_MAIN 1>/dev/null 2>&1 &

        $JAVA $JAVA_OPTS $SERVICE_OPTS -cp $CLASSPATH $APP_MAIN
        getDeliveryPID
        if [ $deliveryPID -ne 0 ]; then
            echo "(PID=$deliveryPID)...[Success]"
            echo "================================================================================================================"
        else
            echo "[Failed]"
            echo "================================================================================================================"
        fi
#        declare -i i=0
#        while (($i < 10))
#        do
#          health=$(curl http://localhost:8025/delivery/health)
#          echo "$health" |grep -q "ok"
#          if [ $? -eq 0 ]
#          then
#            echo "delivery service start completed!"
#            break
#          fi
#          a=`expr $i + 1`
#          echo "Waiting for delivery service start completed!"
#          sleep 1s
#        done
    fi
}

startup