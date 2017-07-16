# Spring-Boot-Seed
Spring-Boot seed ,use it to easily start a new project. Choose log4j2, freemarker and hibernate ,make it BEST PRACTICE

### Get Start
The seed project is made by maven,I recommend you to use IDEA,you can import the project easily,and of course you can you maven
</br>There is somthing you need to change or config to get start
</br>at /src/main/resources/application.properties ,this is the main properties of the project , you may want to change
</br>
</br>``spring.datasource.url=jdbc:mysql://localhost:3306/YOUR_DATA_BASE``
</br>``spring.datasource.username=USERNAME``
</br>``spring.datasource.password=PASSWORD``
</br>``server.port=801``</br>
</br>
I use active propfile to choose develop/production properties,both properties has set logging.config to a config of log4j2 just like
</br></br>
``logging.config=classpath:config/log4j2-develop.xml``
</br></br>
I made for my own,it's great,but you may want to change the config ,especially 
</br></br> 
``<Property name="LOG_HOME">D://logs</Property>``

</br></br>

### FreeMarker
FreeMarker is enabled

### Log4j2
Using lo4j2 by default , it's easy to config

### Active profile
set spring.profiles.active to use witch properties ,one for develop and the other is for production

### Exception handler
Using ErrorAdvice to enable catch global exceptions

### Dao
Hibernate,using spring-boot-starter-data-jpa
</br>Using interface by default , and make Custom*Dao interface and *DaoImpl to extend the function not apply

    
It's very little now,but it works well,you can add anything you want.
I'll add some User management,pregister,login,roles,permissions,Admin management,statistics in the future 
