# 프로젝트 수준 gradle 의 ext 스코프 사용

```groovy
ext {
    addOnSdkVersion = 28
    minSdkVersion = 21
    compileSdkVersion = 29
    targetSdkVersion = 29
    buildToolsVersion = "29.0.2"

    //...

    lottie = "com.airbnb.android:lottie:3.4.4"

}
```

ext 내부에 변수 처럼 값 설정 후 

모듈 수준 gradle 에서 참조해서 사용..

```groovy
//...

dependencies {
    //...
    implementation rootProject.ext.lottie
    //...
}
```

---

[ref](https://jae-young.tistory.com/30)