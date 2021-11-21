# Folder Monitoring System

## What does it do?
This project contains **two REST web services** for the monitoring of the file in the folder **src/main/resources/checkFolder** (only direct files, it does not care of subfolder files)

Here below the exposed services:

1. **getfilesInfo**
   Takes as monitoring input parameter a datetime interval and gives in output the list of the files in the folder that have their last change datetime satisfiing these   constraints. The file information provided are: path, name, dimension, timestamp of the last change and Hash MD5
2. **getfileFromMd5**
   Takes as input parameter the Hash MD5 of the file and gives in output the corresponding information of the file which meets this requiremets (if it exists). The file information provided are: path, name, dimension, timestamp of the last change and Hash MD5

## Framework
*The project is build with **spring boot**, which **contains an integrated tomcat**
*The external dependencies are specified in the **pom.xml** file
*The Web REST services are under the package which ends with **web.rest**, where there is the **Controller**
*The service called by the controller is under the package which ends with **service**. This is called by the Controller thus to perform the business logic
*Under the package which ends with **to** there are the objects for the mapping of the output response, with all the descpriptive info of the file
*In **src/main/resources/static** there is the html page **index.html**, which can be viewd under the default Url of the microservice

## Prerequisites
*The main prerequisites are: have at least **java 8** and an internet connection in order to download the external dependencies

### Exposed services
There are two exsposed services which are released on **http://localhost:8080**

1. _http://localhost:8080/fmonitoringsys/api/filesinfo?start=2015-09-26T01:30:00.000&end=2030-09-26T01:30:00.000_ :
Output:
```
{"message":"Monitor Operation successfully completed","files":[{"path":"C:\\Users\\danie\\Coding\\fmonitoringsys\\target\\classes\\checkFolder\\anotherFile.txt","name":"anotherFile.txt","byteSize":0,"lastModify":"2021-11-19T16:10:26.962","hashMd5":"d41d8cd98f00b204e9800998ecf8427e"},{"path":"C:\\Users\\danie\\Coding\\fmonitoringsys\\target\\classes\\checkFolder\\hello.txt","name":"hello.txt","byteSize":0,"lastModify":"2021-11-19T16:10:06.601","hashMd5":"d41d8cd98f00b204e9800998ecf8427e"},{"path":"C:\\Users\\danie\\Coding\\fmonitoringsys\\target\\classes\\checkFolder\\testFile.txt","name":"testFile.txt","byteSize":0,"lastModify":"2021-11-19T16:10:35.193","hashMd5":"d41d8cd98f00b204e9800998ecf8427e"}]}
```

If there is no file within the given range, the response will give a success message, but the files array will be empty:
```
{"message":"Monitor Operation successfully completed","files":[]}
```

2. _http://localhost:8080/fmonitoringsys/api/file?md5=d41d8cd98f00b204e9800998ecf8427e_
Outout:

```
{"message":"Monitor Operation successfully completed","files":[{"path":"C:\\Users\\danie\\Coding\\fmonitoringsys\\target\\classes\\checkFolder\\anotherFile.txt","name":"anotherFile.txt","byteSize":0,"lastModify":"2021-11-19T16:10:26.962","hashMd5":"d41d8cd98f00b204e9800998ecf8427e"}]}
```
If in input there is an Hash MD5 which has no correspondence, the output will be:
```
{"message":"No file has been found with the given Hash MD5!","files":[]}
```
Note: whenever some error occurs, only the **message** field will be populated. For example, if the folder does not exist, or there are no file, there will be the following output:
```
{"message":"The folder checkFolder to monitor does not exists or there are no file inside!","files":[]}
```