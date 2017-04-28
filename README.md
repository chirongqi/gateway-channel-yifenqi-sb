### gateway-channel-yifenqi

######linux运行：
    java -Xms2048M -Xmx2048M -Xss256k -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/java.dump -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:/tmp/gc.log -XX:ErrorFile=/tmp/jvm_error.log -jar -Dspring.profiles.active=test gateway-channel-yifenqi.jar >/dev/null &
    
######window测试环境运行： 
    java -Xms128M -Xmx256M -Xss128k -jar -Dspring.profiles.active=test gateway-channel-yifenqi.jar
######打包：
    clean package docker:build
 
######打包并推送到docker仓库：
    clean package docker:build -DpushImage