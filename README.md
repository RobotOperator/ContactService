# Contact Service Program (Enhanced)
My enhancements performed on the Contact Service for the second artifact of my CS499 capstone at Southern New Hampshire University.

## About the Contact Service Program
This program is a Java command line application that facilitates creating, reading from, updating, searching for, and deleting contact information that is stored in a local SQLite database. All locally stored information is XOR encrypted to protect the user information for each contact created. All data supplied for contact information is validated by the underlying contact service.

## Motivation
I originally created the Contact and ContactService classes used in this application along with the included Junit tests for those respective classes for a Quality Assurance and Testing course (CS320) that I was enrolled in a Southern New Hampshire University (SNHU). I wanted to expand upon the use of those classes to add a user interface which facilitated direct interaction with the classes as an example of how they could be used. When working with the code I knew that every time the Contact Program was running it would lose all the stored data should it encounter an unexpected disruption or shutdown. I decided to address this flaw by designing a class that interfaces with a local SQLite application driver to create persistent storage of contact information which will be reloaded into the application upon service startup. This program serves as an example of the knowledge I gained during my time at SNHU to be able to create well commented and tested applications/code.

## Getting Started
To get a local instance of the project up and running follow the steps below.

  1.	Clone down this repository containing the source code, classes, and exported JAR.
  2.	Copy the JAR file to a local directory where you can read and write to.
  3.	Execute the program by running “java -jar ContactProgram.jar”.
  4.	The command line interface will be presented requiring a key for database encryption.
    <br>a.	Supply a key and store it someplace safe, you will need it to decrypt the database in the future.
    
This project was developed in the Eclipse Java IDE for all the client interface, middleware logic, database integration, and Junit testing. The source code for both the internal classes and unit tests can be easily reviewed in most IDEs to compile a new JAR if needed. 

## Installation Requirements
This project is written to be executed with a Java JDK 14.0.2  environment or later. The application makes use of the open source sqlite-jdbc-3.41.2.1.jar driver created by the Xerial project on GitHub. The Apache License and Notice files for licensing is included for that dependency in the project and you can version of that project [here](https://github.com/xerial/sqlite-jdbc/). The driver facilitates the  creation of an SQLite database, a lightweight relational database commonly used for standalone applications or testing, which allows persistent storage of contact information in the local directory where the JAR is executed from. Upon initial launch of the application the database will be created in the local directory and when subsequently launched the application will automatically use the existing database for future contact retrieval, insertions, and updates. All data that is stored in the database, excluding the unique id values that identify contact entries, are XOR encrypted with the key that is provided when the Contact Program starts.

## Usage
After supplying the database key upon launching the Contact Program JAR file, the user is presented with a selection menu of actions to be performed. These actions include viewing stored contacts, adding a contact, updating a contact, deleting a contact, searching for a contact, deleting all contacts, and exiting the service. By supplying the numbers appearing next to each action, users can interact with the Contact Program which initiates additional dialogs. When viewing stored contacts, all contact entries are printed out in a list to the console. The process of adding a contact queries the user for the five fields of a contact, unique id, first name, last name, phone number, and address, which are then inserted into the underlying databased as well as the indexed list in memory. Updating a contact requires specifying a contact by the unique id and then updating either the first name, last name, phone number or address fields by supplying new string values. Contacts are deleted from the service by supplying the unique contact id or by selecting to delete all contacts along with providing a secondary confirmation. Deleting all contacts from the service empties all stored data in the SQLite database. Users can also search for a contact by specifying a unique contact id. All input field values perform validation of data supplied and will notify the user if improper strings are entered. Screenshots have been included below demonstrating some of the user interaction experience.

  ### Testing
  Testing has been performed using Junit5 in Eclipse. The unit tests included with the project can be run to verify existing functionality for each underlying unique class used within Contact Program. When running the unit tests for this project, it is important to ensure the JAR is executed in a directory that does not already contain a copy of the contacts.db database file otherwise testing may unexpectedly fail or overwrite existing data. Testing has been performed using both positive and negative testing conditions to validate data is supplied in expected and correct formats to the classes.
  
  ### Screenshots  
  ##### Launching the Contact Program application and entering contact information.
  <img src="/img/SQLiteApplicationRunning.PNG" width=65% height=65%>
  
  ##### Searching for stored contact information
  <img src="/img/SQLitesearch.PNG" width=65% height=65%>
  
  ##### Deleting stored contacts and exiting the service
  <img src="/img/SQLiteExitService.PNG" width=65% height=65%>
  
  ##### Running the included Junit5 tests in Eclipse
  <img src="/img/Junit.png" width=50% height=50%>
  
  ##### XOR encrypted data when opening the local database offline
  <img src="/img/EncryptedDB.PNG" width=65% height=65%>
  

## Additional Resources
This project would not be possible without the helpful insights provided at the following links and project documentation made available for the technologies employed like Oracle’s Java, SQLite, and the java SQLite driver created by Xerial. I encourage everyone to review these items to get a better understanding of the inner workings of this project.

https://docs.oracle.com/en/java/index.html
<br>https://SQLite.org/index.html
<br>https://github.com/xerial/sqlite-jdbc/ 

### Contact
Lance Cain : lance.cain@snhu.edu





