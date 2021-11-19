# LOST
Praktikumsprojekt SWE WS 21/22
Ziel des Projekts ist die Entwickelung eines 2D Games.

## Contribute
To contribute always create an extra branch with an identifying name.  
Check [Git.md](https://github.com/D4ve-R/LOST/blob/main/Git.md) on how to keep your branch updated with main.  
When a feature/module is finished, please create a pull request

## Codestyle
For code styling and formatting checkout [CodeStyle.md](https://github.com/D4ve-R/LOST/blob/main/CodeStyle.md)

## Framework
We use [Java Swing Framework](https://docs.oracle.com/javase/tutorial/uiswing/) for basic GUI infrastructure.  
To build and package our application we use [Gradle](https://gradle.org/).

### How to use Gradle
Open the project in your ide. Inside the ide open a new terminal.  
run : 
```
$ cd macpan

# for unix systems
$ ./gradlew run

# for windows systems
$ ./gradlew.bat run
```

## Project structure

```
LOST
│ README.md
│ .gitignore
|      
└───macpan
│   │   
│   └───app
|       | build.gradle
|       |
│       └────src
|             |
|             └───main # source code   
└───.idea     |    └─...
              |
              └───test # tests
                   └─...
```

## CI & Tests
We use Junit5 Jupiter for testing purposes,
all tests must be in the test dir.  
When pushing your commits to the remote repo,
github will run gradle build & testing action.  
Check on github.com if your code got build successfully.  
(Of course you should do some local testing before you push anything).
