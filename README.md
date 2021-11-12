# LOST
Praktikumsprojekt SWE WS 21/22
Ziel des Projekts ist die Entwickelung eines 2D Games.

## How to git
This repo is the source, please do not push any changes directly.
Make pull request!

### How to get this project
```
# go to a place in your filesystem you like
# and clone the repo

$ git clone URL 
```

### How to keep main & my branch updated
```
# go to main branch and pull from remote repo
# this updates your main branch with all new changes from remote main branch

$ git checkout main && git pull

# change to your branch and merge main branch into it

$ git checkout <YOUR-BRANCH> && git merge main
```

### How to create pull request
```
git request
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
We us Junit5 Jupiter for testing purposes,
all tests must be in the test dir.
When pushing your commits to the remote repo,
github will run gradle build & testing action.
Check on github.com if your code got build successfully.
(Of course you should do some local testing before you push anything).
