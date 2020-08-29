# Phonebook

This project represents an implementation of contact list through Spring boot framework and postgres database.


## Before-Installation
Make sure to edit [application.properties](src/main/resources/application.properties) as you wish. 
Either of github.** variables should contain valid GitHub account information value in order to create the connection successfully.
Also you may change datasource information to your own system properties(feel free to change datasource to any type supported by Hibernate 6).
Also make sure you have Jdk +1.8 installed on your system.     

## Installation

Use the package manager [maven](https://maven.apache.org/) to install Phonebook. 
Run in project directory:

```bash
mvn clean install
```


## Run
After installation:
```bash
java -jar target/phonebook-1.0-SNAPSHOT.jar
```


## Usage
Asked endpoints[PUT/contacts, POST/contacts/search] would be available as spring boot web application is started.

## My approach for GitHub Api connection challenge  
As you can see, there exists [RepositoryDiscover.java](src/main/java/org/example/phonebook/model/bl/RepositoryDiscover.java) class in the project path. This class has a list of contacts, which's repositories are not loaded yet.
And discovering repositories tasks for those contacts remaining in the list are scheduled in its threadPool bean.

This threadPool runs those tasks periodically (every 10 seconds) if the contact list is not empty and github connection is provided. So if loading repositories for a contact was successful, it would be removed from list, otherwise it remains in the list until the github api is reconnected and scheduled tasks are executed over contact again.

This way, adding new contact wouldn't take any long if the github api is temporary unreachable. It just stores new contact in the list until it would be able to load repositories.    