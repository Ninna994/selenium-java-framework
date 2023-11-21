# Instructions for using Custom Java Selenium Framework

## Info

This framework is developed by Nikolina Djekic. 

You can contact me on [linkedin](https://www.linkedin.com/in/nikolina-djekic/) 

Please feel free to reach out to me if you have any suggestion or you want to contribute

This project is still in progress, more functionalities will be added to it. 

Feel free to clone it and explore it.

♥♥ Happy testing ♥♥

## Prerequisites

* **Java** installed on your machine
    * Check if Java is installed in cmd - **java -version**
    * If not installed go to [Link](https://www.oracle.com/java/technologies/downloads/#java8) and install it
    * Go to Environment variables > Edit system environment variables > Environment Variables
    * In the top section click New
        * Variable name: JAVA_HOME
        * Variable value: C:\Program Files\Java\jdk1.8.0_51 (or whichever version you downloaded)
        * OK
    * Verify installation **after restarting computer**
* **Maven** installed
    * Find the latest version of Maven in here - [Maven – Download Apache Maven](https://maven.apache.org/download.cgi)
    * Follow the instruction of download and setting up variables as per here
        - [How to install Maven on Windows - Mkyong.com](https://mkyong.com/maven/how-to-install-maven-in-windows/)
* **Git** installed
    * [Link](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git) for instructions on how to
* **IntelliJ** Installed
    * Download Community version [link](https://www.jetbrains.com/idea/download/#section=windows)
    * Setting up Java SDK in IntelliJ
        * File>Project Structure > SDKs > +
        * JDK - look for location
        * Select location > Click Open > Ok > Apply/OK

## Getting familiar with framework

If you got this far you should be able to start automating. Please go to `/templates` and check all Template files in order to get familiar with formatting and usage

## TestNG - Test Next Generation

Detailed Instructions on [Link](https://github.com/Ninna994/docs/wiki/TestNG)

## Appium Setup - appium is still WIP

* **Node.js** version on computer - 16+ - check `node -v`
    * If not installed, install it [here](https://nodejs.org/en/download)
* Setup and Install **Android Studio** [Link](https://developer.android.com/studio?gclid=CjwKCAjw9NeXBhAMEiwAbaY4ltcH4fj3ZdyhXJuaZ7waEeoI4EAIXJQZck-_kiK09lFpJxVcqUVprRoCWvAQAvD_BwE&gclsrc=aw.ds)
    * Open Android Studio
    * Setup PATHS
        * add user variable - `ANDROID_HOME`
        * path to Sdk folder - should be something like --  `C:\Users\UserName\AppData\Local\Android\Sdk`
        * add to PATH two new variables - /platform-tools and tools
            * `%ANDROID_HOME%\platform-tools`
            * If tools is missing -  Quick fix: `Go to the Tools –> SDK manager –> SDK tools.  Deselect Hide obsolete packages option on the right bottom and further install Android SDK Tools(obsolete). A new folder named Tools is now generated in the SDK directory. (C:\Users\..\AppData\Local\Android\Sdk\tools.)`
* Setup **Android Emulator**
    * Go to `Android Studio > Virtual Device Manager > Create Virtual Device`
    * Choose Device - for example Pixel 3 - and download R and Q versions
    * Wait for install and finish - first simulator setup is done
    * Press play and device should boot up
* **Appium Desktop Inspector** Setup
    * [Link for download]("https://github.com/appium/appium-inspector")
    * Go to Releases > Latest Release > Download version
    * Follow install instructions and open Appium Inspector
        * Set remote port to 4724 - we use 4723 for automation and ports should be different
        * run appium on the same port `appium -p 4724`
        * set the path to /
* **Appium 2.0+** - check `appium -v`
    * if not installed `npm install -g appium@next`
    * check `appium -v` for version check
    * install **appium drivers**
        * `appium driver install xcuitest`
        * `appium driver install uiautomator2`
        * confirm installation: `appium driver list`
    * install **appium-doctor** `npm install -g appium-doctor`
        * check `appium-doctor` to check if everything you need is installed correctly
            * `appium-doctor --android`
            * `appium-doctor --ios`
            * ALL SHOULD BE GREEN => SUCCESSFULL INSTALLATION
    * install it locally also - `npm install appium@next`
* Check if uiautomator is installed - run `appium driver list`
    * if not installed run install commands `appium driver install uiautomator2` and similar for rest of needed drivers
* **Maven repository dependencies needed**
    * [Java client](https://mvnrepository.com/artifact/io.appium/java-client)
    * `IMPORTANT NOTE` Make sure to check if version is compatible with our version of selenium used in project. Right now we use Selenium 4.8.1 and java-client 8.3.0 - make sure to install compatible dependencies `IMPORTANT NOTE`