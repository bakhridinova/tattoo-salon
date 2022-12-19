# salon

<p align="center">
  <img alt="" src="https://user-images.githubusercontent.com/100201504/208244924-345aaa7d-1bba-4043-92af-4f633740acc6.png"\>
</p>


## description
tattoo salon is an open platform where anyone has an opportunity to search for and order images in the catalog. they can also rate them, including for the quality of work.
registered clients can offer their images to the salon, for which they can receive discounts 


## project stack
java EE, postgresql, html5, css, js


## functional rules
The platform supports the following roles and their corresponded functionality.

|                                       | ADMIN |  USER | GUEST |
|                    :-                 |  :-:  |  :-:  |  :-:  |
|sign in/up                             |   +   |   +   |   +   |
|log out                                |   +   |   +   |   -   |
|delete account                         |   -   |   +   |   -   |
|change general information             |   +   |   +   |   -   |
|view available images                  |   +   |   +   |   +   |
|view image details                     |   +   |   +   |   -   |
|rate and leave reviews                 |   -   |   +   |   -   |
|offer their own images                 |   -   |   +   |   -   |
|order images                           |   -   |   +   |   -   |
|view order status including details    |   -   |   +   |   -   |
|change order status including details  |   -   |   +   |   -   |
|view all entity information            |   +   |   +   |   -   |

## database schema
postgresql database is used to store data

<p align="center">
  <img alt="db" src="https://user-images.githubusercontent.com/100201504/208154008-7e83ad45-be0b-43fb-afb5-25b9b6f311a7.png"\>
</p>

## installation

* clone the project 
* create a new postgresql database using dat.sql from src/main/resources/data/ folder 
* change app.properties file, located in the resources/properties/ folder, based on your database configurations
* build the project using maven
* add new tomcat 10.0.271 configuration to the project 
* run tomcat and open http://localhost:8080/ on the browser
* log in as admin and create new users, default account is username: admin/username, password - password

## page view is available <a href="https://github.com/bakhridinova/tattoo-salon/issues/2">here</a>
