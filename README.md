# RedFruit

[![CircleCI](https://circleci.com/gh/SoundSonic1/RedFruit.svg?style=svg)](https://circleci.com/gh/SoundSonic1/RedFruit)
[![codecov](https://codecov.io/gh/SoundSonic1/RedFruit/branch/master/graph/badge.svg)](https://codecov.io/gh/SoundSonic1/RedFruit)

RedFruit is a Reddit app, fully written in Kotlin. The app displays modern Android architecture like
MVVM and dependency injection via Dagger2.

In order to build this app, you need to have a Reddit account.  
Once you have that, head over to [this](https://www.reddit.com/prefs/apps) page to get a client id for the app.  
Make a new installed type app with appropriate information.   

Now on your development machine goto  
- Windows: `C:\Users\<Your Username>\.gradle`  
- Mac: `/Users/<Your Username>/.gradle`  
- Linux: `/home/<Your Username>/.gradle`  
  
and then add the following line to gradle.properties (create one if it doesn't exist).  

`REDFRUIT = "xxxxxxxxxxxxxx"`  

where `xxxxxxxxxxxxxx` is the client id that you got after creating a new installed type app.  

For further information on Reddit's oauth usage, use [this guide](https://github.com/reddit-archive/reddit/wiki/oauth2).
