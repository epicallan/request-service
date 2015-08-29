# Petty cash #

### Request Service API  ###

* The service is built around spark which is used for setting up API end points

* Version 0.0.1

### How do I get set up? ###

* Run a git clone command to clone into your eclipse workspace folder
* Configuration : run a maven update or install command on initial setup incase maven doesnt automatically get all the necessary dependencies
* Dependencies: check the pom.xml file
* How to run tests : You may use maven or eclipse's inbuilt JUNIT test tool
* Deployment instructions: Use the maven package goal.

### Change log and Issues ###

* Changed request table reason field from text to varchar
* Added an organization_id field to request table
* Renamed admin-req-fk table to admin_req_fk table

### Miscellenious notes ###

* Spark comes with an embedded server, running the com.petty_requests.main class as a java application starts the app
* The com.petty_requests.Bootstrap class is used by external servers such as tomcat and it extends the servlet class
* Pending: Need to turn most of the tests into integration tests
* The repo contains an sql file with the test database records for testing purpose.



### Contribution guidelines ###

* Pull requests are very much encouraged
* Writing tests: The tests are written using JUNIT with hamcrest for assertion



### Who do I talk to? ###

* epciallan.al@gmail.com