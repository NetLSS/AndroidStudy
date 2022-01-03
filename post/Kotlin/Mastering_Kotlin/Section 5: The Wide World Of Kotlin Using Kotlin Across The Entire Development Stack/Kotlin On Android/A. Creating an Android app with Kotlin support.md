# Creating an Android app with Kotlin support

# `build.gradle` file

- The `ext.kotlin_version` variable defines the version of Kotlin used in the project
- Kotlin Gradle 플러그인이 클래스 경로 종속성으로 추가되어 플러그인을 다른 Gradle 모듈에 적용하여 Kotlin을 인식할 수 있습니다.

## module:app 수준 build.gradle

- kotlin-android 플러그인이 적용되었습니다.
- kotlin-android-extensions 플러그인이 적용되었습니다.
- Kotlin 표준 라이브러리가 종속 항목으로 추가되었습니다.

## The Gradle Kotlin DSL

코틀린 DSL 로 build gradle 을 작성하면 좋은 점

- Type-safe accessors allow you to reference Gradle/build entities by name.
- Improved IDE support makes it easier to find and navigate to dependencies.

### 마이그레이션 하기

```groovy
// root-level build.gradle
buildscript {
   ext.kotlin_version = "1.3.31"
   repositories {
     google()
     jcenter()
 }

 dependencies {
   // addition of ( ) when using classpath function
   classpath("com.android.tools.build:gradle:3.5.0-beta04")
   classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
 }
}
```

이후 파일 이름을 `build.gradle.kts` 으로 변경. (app 수준 gradle 도 마찬가지)

```groovy
task(name = "clean", type = Delete::class) {
    delete(rootProject.buildDir)
}
```

...

```kotlin
buildscript {
  extensions.add("kotlin_version", "1.3.31")
  repositories {
    google()
   jcenter()
 }

 dependencies {
   classpath("com.android.tools.build:gradle:3.5.0-beta04")
   // using extensions.get() to retrieve Kotlin version
   classpath ("org.jetbrains.kotlin:kotlin-gradle-
              plugin:${extensions.get("kotlin_version")}")
 }
}
```

#### app 수준 gradle

```groovy
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}
```

```groovy
android {
  compileSdkVersion(28)
   defaultConfig {
     applicationId = "com.goobar.hellokotlin"
     minSdkVersion(21)
     targetSdkVersion(28)
     versionCode = 1
     versionName = "1.0"
     testInstrumentationRunner = 
         "androidx.test.runner.AndroidJUnitRunner"
 }
 
buildTypes {
  getByName("release") {
    isMinifyEnabled = false
    proguardFiles(getDefaultProguardFile(
        "proguard-android-optimize.txt"), "proguard-rules.pro")
  }
 }
}
```

```groovy
dependencies {

  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.31")

  implementation("androidx.appcompat:appcompat:1.0.2")
  implementation("androidx.core:core-ktx:1.0.2")
  implementation("androidx.constraintlayout:constraintlayout:1.1.3")
  testImplementation("junit:junit:4.12")
  androidTestImplementation("androidx.test.ext:junit:1.1.1")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
```

이제 Gradle 빌드는 Gradle Kotlin DSL 을 사용하게 됩니다.

이제 groovy 가 아닌 친숙한 코틀린으로 구문과 기능을 사용할 수 있습니다!

## Kotlin으로 종속성 관리 간소화

코틀린 객체 선언을 사용하여 종속성을 정의. 재사용이 가능하게 할 수 있다.

1. 루트 디렉토리 아래 `buildSrc` 라는 새 디렉터리를 만든다.
2. 해당 디렉터리 아래에 아래 파일 2개를 만든다
   1. buildSrc/build.gradle.kts
   2. buildSrc/src/main/kotlin/Dependencies.kt

`buildSrc/build.gradle.kts`

```kotlin
repositories { jcenter() }

plugins {
  'kotlin-dsl'
}
```

kotlin-dsl 을 적용해서 Gradle 이 디렉토리에 정의된 객체 및 상수를 사용하게 합니다.

이제 빌드 내에서 사용될 상수를 정의할 수 있습니다.

`buildSrc/src/main/kotlin/Dependencies.kt`

```kotlin
object Deps {
    object Kotlin {
        const val version = "1.3.31"
    }
}
```

4. 사용해보기

```groovy
// root/build.gradle.kts
buildscript {
    extensions.add("kotlin_version", Deps.Kotlin.version)
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.0-beta04")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-
                    plugin:${Deps.Kotlin.version}")
    }
}
```

이런식으로 상수를 참조해서 사용할 수 있고..

### 최종 적으로

```kotlin
object Deps {
    object Kotlin {
        private const val version = "1.3.31"
        
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-
                                  gradle-plugin:$version"
    }
    
    object Android {
        object Tools {
            const val androidGradle = "com.android.tools.build:
                                       gradle:3.5.0-beta04"
        }
    }
}
```

```groovy
// root/build.gradle/kts
buildscript {
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath(Deps.Android.Tools.androidGradle)
        classpath (Deps.Kotlin.gradlePlugin)
    }
}
```

아주 나이스 하게 종속성과 버전이 캡슐화 된것을 만끽하자.

이건 더 큰 모듈과 다중 모듈이 있을 수 있는 프로젝트에서 종속성을 훨씬 쉽게 관리할 수 있게 한다.

또 `build.gradle.kts` 파일에서 종속성 탐색, 종속성 리펙터링 이 더 쉬워지게 된다.

