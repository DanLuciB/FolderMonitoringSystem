# Folder Monitoring System

## What does it do?
This project contains a scheduled task which periodically monitor the file content of the audit folder: **src/main/resources/checkFolder** notifying when a new file has been added, and **two REST web services API** for querying and exploring the content of the aforementioned folder.
Note that the System takes care only of the direct files of the folder, any files belonging to subfolders are not considered.

For all the services, the file information provided are: 
* file path;
* file name;
* file dimension;
* timestamp of file last change;
* file Hash MD5

The scheduling of the **local monitoring** has been configured every **3 seconds**, and the ouput log information are written in a local Json file under the following path:
**src/main/resources/logs/logFile.json**

Here below the exposed Web services:

1. **getfilesInfo**
   Takes as monitoring input parameter a datetime interval and gives in output the list of the files in the folder that have their last change datetime satisfiing these   constraints.
   
2. **getfileFromMd5**
   Takes as input parameter the Hash MD5 of the file and gives in output the corresponding information of the file which meets this requiremets (if it exists).

The Web services are released on **http://localhost:8080**

## Framework
*The project is build with **spring boot**, which **contains an integrated tomcat**
*The external dependencies are specified in the **pom.xml** file
*The Web REST services are under the package which ends with **web.rest**, where there is the **Controller**
*The Service called by the Controller is under the package which ends with **service**. This is called by the Controller thus to perform the business logic
*Under the package which ends with **to** there are the objects for the mapping of the output response, with all the descpriptive info of the file
*In **src/main/resources/static** there is the html page **index.html**, which can be viewd under the default Url of the microservice

## Prerequisites
*The main prerequisites are: have at least **java 8** and an internet connection in order to download the external dependencies
