# Selenium Java Test Automation Framework

Welcome to the Selenium Java Framework project! This README file outlines the prerequisites required to set up and use
this framework effectively.

You can contact me on [LinkedIn](https://www.linkedin.com/in/nikolina-djekic/) or by sending mail to *
nikolina.djekic94@gmail.com* or *inanikolina@gmail.com*

Please feel free to reach out to me if you have any suggestion, or you want to contribute to this project.

This project is still in progress, more functionalities will be added to it.

Feel free to fork it and explore it.

♥♥ Happy testing ♥♥

## Local Installation

Installation is pretty simple, just follow the steps:

1. Fork this repository - [direct link](https://github.com/Ninna994/selenium-java-framework/fork)
2. Clone repository `git clone https://github.com/[USERNAME]/selenium-java-framework.git`
3. Open project using IntelliJ IDEA
4. Go to `pom.xml` file and load Maven dependencies
5. Start using framework

## Prerequisites

#### Java Development Kit (JDK)

- **Version**: Java 8 or higher
- **Installation**:
    - Download from the [official Oracle website](https://www.oracle.com/java/technologies/javase-downloads.html) or use
      OpenJDK from [AdoptOpenJDK](https://adoptopenjdk.net/).
    - Follow the installation instructions specific to your operating system.
    - Set the `JAVA_HOME` environment variable to point to the JDK installation directory.
    - Verify installation **after restarting computer** by running `java -version` in cmd

#### Integrated Development Environment (IDE)

- **Recommended**: IntelliJ IDEA
    - Download from the [JetBrains website](https://www.jetbrains.com/idea/download/).
    - IntelliJ IDEA Ultimate Edition is recommended for its advanced features, but the Community Edition also works well
      for this project.
    - Setting up Java SDK in IntelliJ
        * `File > Project Structure > SDKs > +`
        * `JDK` - look for location
        * `Select location > Click Open > Ok > Apply/OK`

#### Apache Maven

- **Version**: 3.6.0 or higher
- **Installation**:
    - Download from the [official Apache Maven website](https://maven.apache.org/download.cgi).
    - Great instructions blog can be found
      here: [How to install Maven on Windows - Mkyong.com](https://mkyong.com/maven/how-to-install-maven-in-windows/)
    - Follow the installation instructions and set the `MAVEN_HOME` environment variable.
    - Add the `bin` directory of Maven to the `PATH` environment variable.

#### Allure Report

- **Installation**:
    - Download the commandline from
      the [official Allure website](https://docs.qameta.io/allure/#_installing_a_commandline). - Follow the installation
      instructions specific to your operating system. - Add Allure to the `PATH` environment variable.

#### Git

- **Version**: Latest stable version
- **Installation**:
    - Download from the [official Git website](https://git-scm.com/downloads).
    - Follow the installation instructions specific to your operating system.

## Quickstart

### Configure Settings

Edit the `settings.properties` file located in `src/main/resources`. Just uncomment option you want to use for configuration. 

Specify `browser`, `run`, `environment`, `timeout`, `system`

**!!! IMPORTANT NOTE !!!** - Examples created for reference
`system` should be name of your project, also name of package you create in `apps/`
`environment` should be notes and provided in `system_properties/` folder where new .properties file should be created using naming convention `system_environment.properties` where you put all the environment specific items.

## Key Component `main/java/custom_framework/apps`

This is where the magic happens. 
Structure of this folder in order to have all functionalities available and **recommended** structure should be: 

```
apps/
├── appName/
│   ├── page_elements
│   │   └── Page1Ui.java <!-- extends AppNameCommonFlows.java -->
│   ├── page_workflow/
│   │   └── Page1Flow.java <!-- extends Page1Ui.java -->
│   ├── test_cases/
│   │   └── Page1Test.java <!-- extends Page1Flow.java -->
│   ├── AppNameCommonFlows.java <!-- extends AppNameNav.java -->
│   └── AppNameNav.java <!-- extends SharedMethods -->
├── app2Name/
│   ├── page_elements/
│   │   └── Page1App2Ui.java <!-- extends App2NameCommonFlows.java -->
│   ├── page_workflow/
│   │   └── Page1App2Flow.java <!-- extends Page1App2Ui.java -->
│   ├── test_cases/
│   │   └── Page1App2Test.java <!-- extends Page1App2Flow.java -->
│   ├── App2NameCommonFlows.java <!-- extends App2NameNav.java -->
│   └── App2NameNav.java <!-- extends SharedMethods -->
objects/
utils/
...
```

## Key Components `main/java/custom_framework/utils`

* 🚀 `FrameworkSetup.java`
    * Initializes the WebDriver based on the settings.properties.
    * Configures Chrome options, DevTools, and logging.
    * Sets up the Selenium Grid if running in a remote environment.
* 🚀 `DriverThreadLocal.java`
    * Manages parallel execution and handles WebDriver instances for each thread.
* 🚀 `ExtendedBy.java`
    * Contains utility methods for locating web elements.
* 🚀 `Listeners.java`
    * Implements TestNG listeners for reporting and handling failed tests.
* 🚀 `Retry.java`
  * Handles the retry logic for failed test cases.
* 🚀 `Settings.java`
  * Loads and manages system settings from the settings.properties file.
* 🚀 `SharedMethods.java`
  * Contains reusable helper methods to promote DRY (Don't Repeat Yourself) code practices.

## Key Components `main/java/resources`

* 🚀 `settings.properties` file.
  * Main configuration of project
  * Here you can specify `browser`, `run`, `environment`, `timeout`, `system`, `gridUrl` and more. All of those are consumed by `FrameworkSetup.java` for configuration
* 🚀 `log4j.properties` file
  * Logging settings
* 🚀 `allure.properties` file
  * Settings for allure reporter
* 🚀 `test_data` folder
  * Folder that contains all test documents and files needed for testing. All images, tables, .csv files etc.
* 🚀 `testng_config` folder
  * Folder that contains `.xml` files for test organization. This framework uses TestNG.
* 🚀 `system_properties` folder
  * Should contain `.properties` files in format `SYSTEMNAME_ENVIRONMENT.properties`
  * **!!! IMPORTANT NOTE !!!** SYSTEMNAME_ENVIRONMENT must match `system` and `environment` from `settings.properties` file

```properties
# For example if you have two systems you are automating on two environments - PROD and TEST
# you should create four files to handle different urls and credentials and everything you want to have accessible for testing
# This whole functionality and actual chosing which .properites file to use is handled in FrameworkSetup file

# file project1_prod.properties
url=https://google.com

admin_email=
admin_username=admin
admin_password=password

system_email=
system_username=system
system_password=password
```

```properties
#file project2_test.properties
url=https://bing.com

admin_email=
admin_username=admin
admin_password=password

system_email=
system_username=system
system_password=password
```