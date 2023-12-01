Decompiled, corrected and refactored code of the mod "The Lord of the Rings" for Minecraft 1.7.10.

Besides me, the following people worked on the code:

* © Mevans a.k.a @TheChildWalrus, 2022 - developer of the original mod.

The documentation below will be useful both to developers and to my future self. Information, guides, pitfalls, tips and all that.

## General information

* This repository is a Gradle project that must be opened via IntelliJ IDEA or imported into the Eclipse IDE.
* The syntax used is Java 8.
* The Gradle version used is 8.4.
* ForgeGradle version used is Anatawa12's 1.2-1.1.+.
* The JDK version used is Eclipse Temurin 1.8.0_392.

Be sure to check that your Java syntax and your JDK are as stated above. Your IDE and your OS environment variables must be configured correctly, otherwise nothing will work.

* The JDK configured in the IDE is responsible for what syntax constructs are in the language and what the output bytecode will be (which must be compatible with Forge's expectations).
* The syntax configured in the IDE is responsible for which language constructs you can use (so as not to accidentally end up with bytecode output that will be unreadable and crash Forge). In some cases, you may want to choose a newer JDK and an older syntax to limit yourself.
* The JDK, configured in the OS environment variables, is responsible for which Java will start building the mod and loading the environment.
* JDK in Eclipse is called `JRE/JDK`, in IntelliJ IDEA - `SDK`. The syntax in Eclipse is called `Compiler Compliance Level`, in IntelliJ IDEA - `Language Level`.

### Where can I configure this?

*Windows Environment Variables:
* First method: `LMB on “My Computer” -> Properties -> Additional system settings -> Environment variables.
* Second method: `Settings -> System -> About the system -> Additional system settings -> Environment variables`.
* When you finally get to the environment variables, create two variables there: `JAVA_HOME` and `Path`. If they already exist, double-click LMB to open them and view the contents.
  * JAVA_HOME should only have one line containing the path to your JDK. For me it is `C:\Users\Hummel009\.jdks\temurin-1.8.0_392`.
  * In Path, in addition to other lines, there must be at least one line containing the path of your JDK\bin. For me it is `C:\Users\Hummel009\.jdks\temurin-1.8.0_392\bin`.
* Eclipse IDE:
  * At the top right there is a magnifying glass through which you can get anywhere.
    * To customize the syntax, search for `Compliance` and open the suggested `Compiler`. There is a number on top.
    * To configure the JDK, search for `Installed JREs` and open the suggested `Installed JREs | Open the preferences dialog`. There you can click on the current JRE and remove it (`Remove`), then add (`Add`) a new `Standard VM` and indicate the location on the disk where your JDK is located, then confirm and select it.
  * An alternative way to get to the syntax and JDK settings is to click `Window -> Preferences -> Java` from the top and select either `Compiler` or `Installed JREs`.
  * If you have many projects in your environment, you can configure each one separately. To do this, click on the project on the left and follow the path `Project -> Properties` (not `Window -> Preferences`) from the top. There is `Java Build Path` and `Java Compiler`.
    * `Java Compiler` is just a window with a number on top.
    * `Java Build Path` is a tabbed window where you are interested in the `Libraries` tab. There you need to click in the center on `Module path`, and then click on the right `Add Library -> JRE System Library`. There you can specify one of the previously added `Installed JREs`.
* IntelliJ IDEA:
  * `Ctrl + Alt + Shift + S` will take you to the project structure where you configure the SDK and Language Level.
  * `Ctrl + Alt + S` followed by `Build... -> Compiler -> Java Compiler` will take you to the Language Level settings in the development environment.
  * `Ctrl + Alt + S` followed by `Build... -> Build Tools -> Gradle -> Gradle JVM` will take you to the environment variable settings in the development environment. This is in case Gradle requires a JDK to run that is different from the one specified in the OS environment variables.
  * An alternative way to get to the project structure or environment settings is to click on the gear in the top right and select `Project Structure` or `Settings` there.
  * Another alternative way to find environment settings is to click `File -> Settings`.

## Installation and operation guide

Actually, first you need to download and unzip the folder with the sources somewhere. We'll call this the **project folder**. It contains folders (gradle, src) and various files.

Version 1.7.10 was released quite a long time ago, and since then all development tools have become outdated. Fortunately, they are still working. However, already at the time of creation they were imperfect and required crutches. Crutches are implemented by creating IDE files.

* There are two files with a .bat extension in the project folder, `setupEclipse` and `setupIdea`. Select
