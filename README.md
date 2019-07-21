Quizweb is simple, pretty cool looking api to handle quizzes.
Api is splited on 3 layers - Controllers, Servises and Domains. Api include standart CRUD operations on quizzes, users and used to this little complex opeartions like refresh authentication after change email during loggging in, nested input fields in thymeleaf etc.
Technology stack:
*Front-end: HTML, CSS, JS implemented by Materialize Framework 
*Back-end: Thymeleaf engine, Maven, Spring Boot and in this: 
spring-boot-starter-data-jpa
spring-boot-starter-thymeleaf
spring-boot-starter-web
spring-boot-starter-security
spring-boot-starter-test
spring-security-test
h2database
lombok

How look relationship in embedded h2 database?
![alt text](https://raw.githubusercontent.com/username/projectname/branch/path/to/img.png)

Here you can test application:

Before you wil

Example account with "USER" role:
email: "mostowiakmarek@gmail.com"
password: "karton123"
USER have acccess to all endpoints "/user/**"

Exmaple account with "ADMIN" role:
email: "pusiomichal@gmail.com"
password: "qwerty123"
ADMIN have acccess to all endpoints "/user/**", "/admin/**"
"/h2/**" <-- endpoint to h2 console

So, have fun and how i please don't destroy everything. I think you will appreaciate freedom in movement on webside that I gave you.
Have fun :)
