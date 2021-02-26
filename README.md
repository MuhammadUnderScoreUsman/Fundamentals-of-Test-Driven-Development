# Basic CRUD app with Test Driven Development (TDD)
The is Todo application built with TDD for the demonstaration of Test Driven Development. This Project so far based on 3 branches each branch contains its own development process as it being tested.<br/>
TDD says:
* We write the test first
* We fail the test (When we run the test it will fail, cause actual logic so far is not implemented)
* We implement the actual code (According to test which we wrote)
* We Pass the test<br/>

Unit testing has been done using Junit5 and Mockito:<br/>
* Test doubles or also known as Test Fakes (Fake Data Source Layer) 
* Unit Test of ViewModels using Mockito with Junit5
* Tested different cicrcumstances like exception, errors, succcess.

Instrumentation Testing has been done with Junit4
* Room Dao with DataSource Layer and mapper class
* Mapper is used for Separation of Concern
