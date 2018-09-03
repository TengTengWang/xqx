<center> <H1>SpringCloud微服务异常问题汇总</H1></center >

<center> <H4>王腾</H4></center >

<div STYLE="page-break-after: always;"></div>

[TOC]

<div STYLE="page-break-after: always;"></div>

## 前言

当前模块版本号

| 模块                        | 版本号        |
| --------------------------- | ------------- |
| SpringCloud                 | Finchley.SR1  |
| spring-boot-gradle-plugin   | 2.0.1.RELEASE |
| spring-boot-starter-web     | 2.0.1.RELEASE |
| spring-cloud-starter-zuul   | 1.4.5.RELEASE |
| spring-cloud-starter-eureka | 1.4.5.RELEASE |
| apollo-client               | 1.0.0         |



## 一、SpringCloud

### 1.1 java.lang.NoSuchMethodError

**问题描述：**

> java.lang.NoSuchMethodError: org.springframework.boot.builder.SpringApplicationBuilder.<init>([Ljava/lang/Object;)V

**解决方法：**

缺少了spring-cloud和spring-boot-start-web依赖

### 1.2

**问题描述：**

> Description:
>
> Field restTemplate in com.xqx.oauth.servic.TokenServicImpl required a bean of type 'org.springframework.web.client.RestTemplate' that could not be found.
>
> Action:
>
> Consider defining a bean of type 'org.springframework.web.client.RestTemplate' in your configuration.

**解决方法：**

1. main函数中添加如下代码

   ```java
   import org.springframework.cloud.client.loadbalancer.LoadBalanced;
   import org.springframework.context.annotation.Bean;
   @Bean
   @LoadBalanced
   RestTemplate restTemplate(){
       return new RestTemplate();
   }
   ```

2. 

## 二、Eureka

### 2.1 Error creating bean with name 'eurekaInstanceConfigBean'

**问题描述：**

> org.springframework.beans.factory.BeanCreationNotAllowedException: Error creating bean with name 'eurekaInstanceConfigBean': Singleton bean creation not allowed while singletons of this factory are in destruction (Do not request a bean from a BeanFactory in a destroy method implementation!)
> 	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:214) ~[spring-beans-5.0.5.RELEASE.jar:5.0.5.RELEASE]
> 	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:315) ~[spring-beans-5.0.5.RELEASE.jar:5.0.5.RELEASE]

**解决方法：**

在 `pom.xml` 添加 `spring-boot-starter-web` 依赖



## 三、Data JPA

### 3.1 xxx is not mapped

**问题描述：**

> com.xqx.base.exception.DaoException: org.hibernate.hql.internal.ast.QuerySyntaxException: User is not mapped [from User t where t.name=:name and t.password=:password]
>
> 	at com.xqx.user.dao.UserDaoImpl.listObjecstByFeilds(UserDaoImpl.java:78) ~[main/:na]
>
> 	at com.xqx.user.dao.UserDaoImpl$$FastClassBySpringCGLIB$$1c819ba4.invoke(<generated>) ~[main/:na]
>
> 	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204) ~[spring-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]

**解决方法：**

1. Bean对象缺少必要的注解

   ```java
   import javax.persistence.Entity;
   import javax.persistence.GeneratedValue;
   import javax.persistence.Id;
   import javax.persistence.Table;
   @Entity
   @Table(name="u_user")
   public class User {
   	@Id
       @GeneratedValue
       private Long id;
   	...
   }
   ```

2. HQL语句中表名应该是ORM映射的类名。

   ```java
   String sql = "select u.password from users u where u.username='teng'";
   ```

   原来 HQL语句中表名应该是ORM映射的类名，所以应该改成：

   ```java
   String sql = "select u.password from User u where u.username='teng'";
   ```

   sql 语句查找的是生成的User 类，不是普通的表。

3. 添加JPA配置类

   ```java
   import org.springframework.boot.autoconfigure.domain.EntityScan;
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.core.Ordered;
   import org.springframework.core.annotation.Order;
   import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
   import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
   import org.springframework.transaction.annotation.EnableTransactionManagement;
   
   /**
    * JPA 配置类
    */
   @Order(Ordered.HIGHEST_PRECEDENCE)
   @Configuration
   @EnableTransactionManagement(proxyTargetClass = true)
   @EnableJpaRepositories(basePackages = "com.xqx.user.dao")
   @EntityScan(basePackages = "com.xqx.user.entity")
   public class JpaConfiguration {
       @Bean
       PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor(){
           return new PersistenceExceptionTranslationPostProcessor();
       }
   }
   ```


### 3.2 No default constructor for entity

**问题描述：**

> com.xqx.base.exception.DaoException: org.hibernate.InstantiationException: No default constructor for entity:  : com.xqx.user.entity.User
>
> 	at com.xqx.user.dao.UserDaoImpl.listObjects(UserDaoImpl.java:95) ~[main/:na]
>
> 	at com.xqx.user.dao.UserDaoImpl$$FastClassBySpringCGLIB$$1c819ba4.invoke(<generated>) ~[main/:na]

**解决方法：**

所有持久化类必须要求有不带参的构造方法（也是JavaBean的规范）。Hibernate需要使用Java反射为你创建对象。

                                                                                         ——来自官方文档[《Hibernate Getting Started Guide》](http://docs.jboss.org/hibernate/orm/5.2/quickstart/html_single/#hibernate-gsg-tutorial-basic-entity)

当实体类声明了其他带参构造方法时，需要显式声明不带参构造方法。