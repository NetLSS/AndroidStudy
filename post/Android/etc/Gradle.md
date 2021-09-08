# Gradle

```groovy
// 안드로이드 플러그인 사용 선언
plugins {
    id 'com.android.application'
    id 'kotlin-android'
}

android {
    // 해당 모듈을 빌드 할 때 사용되는 SDK 및 빌드 도구 버전 정의
    compileSdk 30
    buildToolsVersion "30.0.3"

    // 해당 모듈의 AndroidManifest.xml 파일로 생성되는 요소를 정의
    defaultConfig {
        applicationId "com.lilcode.example.roomdatabasedemo"
        minSdk 23
        targetSdk 30
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
    // * 서명 설정구성하기
    signingConfigs {
        release {
            storeFile file("keystore.release")
            storePassword "your keystore password here" // System.getenv("KEYSTOREPASSWD")
            keyAlias "your key alias here"
            keyPassword "your key password here" // System.getenv("KEYPASSWD")

            // or System.console().readLine("\nEnter keystore password: ")
        }
    }

    // 앱의 릴리즈 버전이 빌드될 때 APK 파일에 대해 ProGuard를 실행할 것인지 여부 및 셋팅
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    // 프로젝트 빌드 시 사용되는 자바 컴파일러의 버전 지정
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }
}

// 우리 app 모듈의 로컬과 원격 의존성 들.
dependencies {

    implementation 'androidx.core:core-ktx:1.6.0'
    implementation 'androidx.appcompat:appcompat:1.3.1'
    implementation 'com.google.android.material:material:1.4.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.0'
    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.3.1'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1'
    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
}
```