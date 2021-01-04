1. 添加依赖
    ```xml
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>1.3.2</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    ```
2. 启动类添加注解@MapperScan
    ```java
    @MapperScan("com.yicj.study.webflux.mapper")
    @SpringBootApplication
    public class WebFluxApp {
        public static void main(String[] args) {
            SpringApplication.run(WebFluxApp.class, args) ;
        }
    }
    ```
3. 添加配置
    ```properties
    # datasource 配置
    spring.datasource.driver-class-name=com.mysql.jdbc.Driver
    spring.datasource.url=jdbc:mysql://127.0.0.1:3306/demo?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT
    spring.datasource.username=root
    spring.datasource.password=root
    # mybatis 配置
    mybatis.config-location=classpath:mybatis-config.xml
    logging.level.com.yicj.study.webflux.mapper=debug
    ```
4. 新建resources/mybatis-config.xml文件
    ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE configuration
            PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-config.dtd">
    <configuration>
        <!--类型别名配置-->
        <typeAliases>
           <package name="com.yicj.study.webflux.entity"/>
        </typeAliases>
        <!--配置MyBatis 去哪里找映射文件-->
        <!--注意如果这里使用package的话路径一定要与java的mapper接口路径一致，否则将找不到xml文件-->
        <mappers>
            <!--
            如果使用package 这里必须配置为com.yicj.study.mapper，且xml文件必须存放在对应路径下
            <package name="com.yicj.study.mapper"/>
            -->
            <mapper resource="mappers/UserMapper.xml" />
        </mappers>
    </configuration>
    ```
5. 新建resources/mappers/UserMapper.xml文件
    ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper
            PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="com.yicj.study.webflux.mapper.UserMapper">
        <select id="findById" resultType="User" parameterType="int">
            select * from user where id = #{id}
        </select>
    </mapper>
    ```
6. 新建mapper类
    ```java
    public interface UserMapper {
        User findById(Integer id) ;
    }
    ```
