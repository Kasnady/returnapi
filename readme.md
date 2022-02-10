I'm using Eclipse to run this project

Steps to run after import it:
1. Click Debug/Run on TestReturnapiApplicat.java
2. Wait for it to start until you can see this line in console "Tomcat started on port(s): 8080 (http) with context path ''"
3. You can access via "http://localhost:8080"


---


NOTE:
* To test the application, you can use curl, postman, or etc. I'm using Postman for this case.
* You can download postman configuration at "..."
* Orders file was placed in src/main/resources/files/orders.csv
* Below step 1 data was coming from this orders.csv file
* Every restart of application will make DB to become fresh new, and will only have data coming from orders.csv

---

Steps of test:
1. Request token using "1. Get Pending Return Token"
2. Use the token from point 1 and replace Body request in key "returnToken"
