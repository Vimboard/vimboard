# Create database

```postgresql
CREATE ROLE vimadmin WITH LOGIN PASSWORD 'vimadmin123';

CREATE DATABASE vimboard WITH OWNER vimadmin;
```

# Create service

Создаём пользователя

```shell script
$ sudo useradd vimboard
$ sudo passwd vimboard
$ sudo chown vimboard:vimboard vimboard-server.jar
$ sudo chmod 500 vimboard-server.jar
```

Создаём ссылку, которая позволит использовать JAR-как сервис

```shell script
$ sudo ln -s /path/to/vimboard-server.jar /etc/init.d/vimboard
$ sudo systemctl daemon-reload
```

Запуск сервиса

```shell script
$ sudo service vimboard start
```

Подробнее
https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html
https://www.baeldung.com/spring-boot-app-as-a-service

# Other

Проверяем пользователя, под которым запущен сервис
`ps aux | grep vimboard`

Проверяем открытые порты.
`sudo netstat -tlpn`

# Used links:
 - about spring - https://habr.com/ru/post/350682/
 - spring-boot-starter - https://habr.com/ru/company/jugru/blog/424503/
 - spring-mvc static resources - https://www.baeldung.com/spring-mvc-static-resources
 - http://aerexu.github.io/java/2016/04/10/Use-external-configuration-to-get-Datasource-in-Spring-boot
 - https://dzone.com/articles/defining-bean-dependencies-with-java-config-in-spring-framework
 - https://regex101.com/ 
