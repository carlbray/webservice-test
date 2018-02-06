# webservice-test
This Eclipse / Maven project is an example of testing a RESTful service.
## Installation
You will need Maven installed http://maven.apache.org

Then clone the repository, change to webservice-test directory and run the tests e.g. 
```
clone https://github.com/carlbray/webservice-test.git
cd webservice-test
mvn test
```
This will run the **com.carlbray.test.organisation.OrganisationTest** and create a test report in **target/surefire-reports/index.html**

## Framework Design
To ensure the tests are easy to maintain and aren't brittle I built a number of features into this framework.
### Mapping JSON (JavaScript Object Notation)
Map the JSON objects into Plain Old Java Objects (POJO)

Run the JsonSchema2Pojo class. This class generates POJO classes from the referenced JSON file.

*Alternatively - do it by hand*

I mapped the Json objects into Plain Old Java Objects (POJO) using http://www.jsonschema2pojo.org/ This generates Jackson https://github.com/FasterXML annotated POJO classes.

Then copied each POJO into the **com.carlbray.pojos.organisation** package.

### Calling the Service
I used REST-Assured https://github.com/rest-assured/rest-assured to simplify the work of calling the service.

I wrapped the calling of the service into **com.carlbray.utils.RestUtils.mapJsonObjects** so you only need to pass in the path and the POJO class you want to use.

### Test Running and Reporting
I used TestNG http://testng.org to simplify the work of checking results and creating test reports.

### Data
I put the data used in the testing in to a CSV file **data/org-test-data.csv** so more test data can be added without making code changes.

### References
* https://github.com/rest-assured/rest-assured
* http://www.jsonschema2pojo.org/
* http://testng.org
* https://github.com/FasterXML
