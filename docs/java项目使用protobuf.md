1. 添加依赖
    ```xml
    <dependency>
        <groupId>com.google.protobuf</groupId>
        <artifactId>protobuf-java</artifactId>
        <version>3.12.4</version>
    </dependency>
    ```
2. maven插件配置（pom.xml）
    ```xml
    <build>
        <extensions>
            <extension>
                <groupId>kr.motd.maven</groupId>
                <artifactId>os-maven-plugin</artifactId>
                <version>1.6.2</version>
            </extension>
        </extensions>
        <plugins>
            <plugin>
                <groupId>org.xolstice.maven.plugins</groupId>
                <artifactId>protobuf-maven-plugin</artifactId>
                <version>0.6.1</version>
                <configuration>
                    <protoSourceRoot>${project.basedir}/src/main/proto</protoSourceRoot>
                    <outputDirectory>${project.build.directory}/generated-sources/protobuf/java</outputDirectory>
                    <!--设置是否生成java文件之前清空outputDirectory的文件，默认值为true，设置false时会覆盖同名文件-->
                    <clearOutputDirectory>true</clearOutputDirectory>
                    <protocArtifact>com.google.protobuf:protoc:3.5.1:exe:${os.detected.classifier}</protocArtifact>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
    ```
3. 定义proto文件
    ```proto
    syntax = "proto3";
    option java_package = "com.yicj.study.proto" ;
    option java_outer_classname = "PersonModel" ;
    message Person{
        int32 id = 1 ;
        string name = 2 ;
        string email = 3 ;
    }
    ```
4. 找到plugins中的protobuf点击protobuf:compile生成对应的java文件（target/generated-sources/protobuf/java）
5. 将PersonModel文件移动到源码包下com.yicj.study.proto中
6. 编写单元测试
    ```java
    public class PersonModelTest {
        @Test
        public void hello() throws InvalidProtocolBufferException {
            PersonModel.Person forezp = PersonModel.Person.newBuilder()
                    .setId(1).setName("forezp").setEmail("abc@qq.com").build() ;
            for (byte b : forezp.toByteArray()){
                System.out.print(b);
            }
            String s = CommonUtil.base64Encode(forezp.toByteArray());
            System.out.println();
            System.out.println("====================> " + s);
            System.out.println();
            log.info("\n" + "bytes长度" + forezp.toByteString().size());
            log.info("========forezp Byte 结束==========");
            log.info("========forezp 反序列化生成对象开始======");
            PersonModel.Person forezpCopy = PersonModel.Person.parseFrom(forezp.toByteArray()) ;
            log.info(forezpCopy.toString());
            log.info("====== forezp 反系列化生成对象结束=======");
        }
    }
    ```
