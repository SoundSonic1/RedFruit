# RedFruit
This is a Reddit app which is fully written in Kotlin. It is my personal app to learn modern  
Android design patterns such as MVVM and dependency injection with Dagger.  

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
