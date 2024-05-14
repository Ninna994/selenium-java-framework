# Commands

## Maven test runs

* Xml files you wish to run using maven command should be added to:

```xml
<configuration>
    <suiteXmlFiles>
        <suiteXmlFile>src/main/resources/testng_config/testTests.xml</suiteXmlFile>
    </suiteXmlFiles>
</configuration>
```

* Add testng.xml suites to pom.xml and run tests with `mvn clean test -Dsurefire.suiteXmlFiles=testTests.xml` command
* If you need more than one test suite to be run use
  comma `mvn clean test -Dsurefire.suiteXmlFiles=testTests.xml,anotherTest.xml`

## Allure Setup and report generator

* To run allure report use in target/ `allure serve`

http://webdriveruniversity.com/
https://demoqa.com/
https://the-internet.herokuapp.com/
https://automationbookstore.dev/