# kbc-audit

#pom 校验依赖
aop依赖
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

校验依赖
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.6</version>
        </dependency>
        
fastjson json 依赖注意版本
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.29</version>
        </dependency>
#注意拦截器拦截url配置
~~~ 
需要在项目的application.properties中配置
log.allow.origin=
log.intercept.origin=**

路径解释
/logs/**：匹配 /logs/ 下的所有路径
/logs/*：只匹配 /logs/id，不匹配 /logs/id/xxx

