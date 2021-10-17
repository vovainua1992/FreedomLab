# *FreedomLab* (Pet-Project)
##### (multi-module project)
###### version  0.1.7 SNAPSHOT

A simple social blogging project.  
A project created to study the spring framework.

# Getting-started 
###Edit properties files: 
> Edit config in rows: \
> spring.datasource.url \
> spring.datasource.username \
> spring.datasource.password 
> 
> upload.path 
> 
> spring.mail.host \
> spring.mail.username \
> spring.mail.password \'
> spring.mail.port \
> spring.mail.protocol \
> mail.debug 
> 
> my.server.url 
>> web-application/src/main/resources/application.property
>
>> services/src/test/resources/application.property

###Edit flyway/pom.xml file
> < driver >your_driver< /driver> \
> < url>your_db_url< /url> \
> < user>username< /user> \
> < password>password< /password>

### Run migrate db
> In flyway-module run mvn flyway-maven-plugin:7.9.0:migrate



## Maven modules:
* flyway (database migration)
* services (service code and business logic)
* web (web client )



##Project uses:
>    - freemarker(template engine)
>    - spring boot v.2.3.2
>    - Bootstrap v5
>    - Content-Tools (java-script text editor)
