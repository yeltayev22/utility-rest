# utility-rest
Back-end for Digital Engineering GIS Application [Spring]

Add `application.properties` file inside `src/main/resources/application.properties`:
```
### Replace databaseName with url for data source(here shown for local), username and password ###

spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.url=jdbc:sqlserver://localhost;databaseName=DB_NAME
spring.datasource.username=DB_USERNAME
spring.datasource.password=DB_PASSWORD
spring.jpa.show-sql=true
## Hibernate Properties
spring.jpa.properties.hibernate.format_sql=true

## Hibernate Properties
# The SQL dialect makes Hibernate generate better SQL for the chosen database
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServer2012Dialect
# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto=update
```
Database schema:

![Database schema](https://github.com/yeltayev22/utility-rest/blob/master/db_schema.jpg)
