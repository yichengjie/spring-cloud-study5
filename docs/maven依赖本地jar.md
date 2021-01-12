1. 添加本地依赖
    ```xml
       <dependency>
           <groupId>com.alibaba</groupId>
           <artifactId>fastjson</artifactId>
           <version>1.2.29</version>
           <systemPath>${project.basedir}/lib/fastjson-1.2.29.jar</systemPath>
           <scope>system</scope>
       </dependency>
    ```
2. springboot项目加入本地依赖jar
    ```xml
    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <!-- spring jar包是需要配置这个 -->
        <configuration>
           <includeSystemScope>true</includeSystemScope>
        </configuration>
    </plugin>
    ```
3. 打war包时添加依赖
    ```xml
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
        <configuration>
           <webResources>
             <webResource>
                 <directory>lib</directory>
                 <targetPath>WEB-INF/lib</targetPath>
                 <includes>
                    <include>**/*.jar</include>
                 </includes>
             </webResource>
           </webResources>
         </configuration>
    </plugin>
    ```
