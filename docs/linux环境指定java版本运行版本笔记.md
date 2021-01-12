1. 编写start.sh脚本
    ```shell
    #!/bin/bash
    export JAVA_HOME=/opt/app/jboss-eap-6.4/jdk1.7.0_71
    export PATH=$JAVA_HOME/bin:$PATH
    export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
    java -version
    java -jar hello.jar
    ```
