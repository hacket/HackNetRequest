### Volley+OkHttp网络请求封装

#### How to use?

**Step 1. Add the JitPack repository to your build file**
Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        jcenter()

        maven { url "https://jitpack.io" }
    }
}
```
**Step 2. Add the dependency**
```
dependencies {
    compile 'com.github.hacket:HackNetRequest:0.2'
}
```
