# Return Order API

Return Order API is a sample code of requesting an order items to be returned to "somewhere". By using simple and yet secured flow which require to get an Access Token with order to be return. Then pass along that token during requesting items of order to be returned.

## Installation

I'm using Eclipse to run this project. So you just need to import it to your Eclipse and choose "Existing Maven Project". Technology used (for more detail may refer to pom.xml):

Name | Version
--- | ---
Java | v11
Spring MVC | v2


## Steps to run after import it:
1. Click Debug/Run on TestReturnapiApplication.java
2. Wait for it to start until you can see this line in console **Tomcat started on port(s): 8080 (http) with context path ''**
3. You can access via **http://localhost:8080"**


##NOTE:
* To test the application, you can use curl, postman, or etc. I'm using Postman for this case.
* You can download postman configuration at root folder with name **Return API.postman_collection.json**
* Orders file was placed in **src/main/resources/files/orders.csv**
* Whole original data was coming from this **orders.csv** file
* Every restart of application will reset the DB, and will only have data from orders.csv

---

##Steps of test (can go step by step in Postman):
1. Request token using "1. Get Pending Return Token"
2. Use the token from point 1 and replace Body request in key "returnToken"
3. Use this request to retrieve created Return Orders from Step 2
4. You may update the particular item order status to **ACCEPTED / REJECTED**