# First-party tooling

## Exploring Android KTX

- Android KTX는 Android 프레임워크 및 Jetpack을 위한 다양한 확장 세트입니다.
- 그는 Android KTX 확장 자체가 Jetpack의 일부이며 간단한 Gradle 종속성으로 프로젝트에 추가할 수 있습니다.
- Android KTX에서 제공하는 기능은 확장 기능 및 고차 기능과 같은 기능을 활용하여 Android API를 보다 Kotlin 관용적으로 만드는 것을 목표로 합니다.
- 종속성을 보다 가볍게 만들기 위해 Android KTX는 제공하는 기능에 따라 여러 개의 작은 종속성으로 나뉩니다. 그 중 일부는 다음과 같습니다.
  - Core KTX
  - Fragment KTX
  - SQLite KTX
  - ViewModel KTX
  - Navigation KTX
  - WorkManager KTX
- Android KTX 전체는 많은 유용한 기능을 제공합니다.
- 다음 섹션에서는 Core KTX 및 Fragment KTX라는 두 가지 특정 모듈에서 제공하는 기능을 살펴보겠습니다.

## Adding Core KTX to your project

먼저 Core KTX를 사용하도록 프로젝트를 업데이트합니다. 이렇게 하려면 먼저 google() Maven 저장소가 프로젝트에 추가되었는지 확인해야 합니다. 추가하려면 다음 코드를 사용하세요.

```groovy
repositories {
    google()
}
```

그 다음 (이전에 만들어둔 buildSrc 폴더 안에 참고)

```kotlin
object Android {
    object Tools {
        const val androidGradle = "com.android.tools.build:gradle:3.5.0-beta04"
    }
    
    object Ktx {
        const val core = "androidx.core:core-ktx:1.0.1"
    }
}
```

앱수준 build.gradle.kts 에 추가

```groovy
dependencies {
    ...

    implementation(Deps.Android.Ktx.core)
}
```

이제 Core KTX가 프로젝트에 추가되었으므로 개발자의 삶을 더 쉽게 만드는 몇 가지 방법을 살펴보겠습니다.

## Using Core 

Core KTX에는 다음을 포함하여 다양한 핵심 Android 프레임워크 라이브러리 및 API를 중심으로 구축된 패키지가 포함되어 있습니다.

- androidx.core.animation
- androidx.core.preference
- androidx.core.transition
- androidx.core.view

Android KTX를 사용하여 플랫폼 API를 단순화하는 가장 좋은 예 중 하나는 SharedPreferences를 사용하는 것입니다. Kotlin의 기능을 사용할 수 있으므로 명시적으로 commit() 또는 apply()를 호출할 필요가 없는 매우 유창한 구문을 SharedPreferences 편집에 사용할 수 있습니다.

```kotlin
val preferences = getPreferences(Context.MODE_PRIVATE)
preferences.edit { 
    putBoolean("key", false) 
    putString("key2", "value")
}
```

Android KTX에서 제공하는 유용한 기능의 또 다른 예는 View.onPreDraw() 확장 기능입니다.

```kotlin
button.doOnPreDraw {
    // Perform an action when view is about to be drawn
}
```

이를 통해 새 리스너를 만들거나 해당 리스너를 등록 취소하지 않고도 View가 그려지려고 할 때 실행될 로직이 포함된 람다를 정의할 수 있습니다.

## Using Fragment KTX

이제 프래그먼트 작업을 위한 유틸리티 기능이 포함된 Fragment KTX 모듈을 살펴보겠습니다. 먼저 새로운 종속성 상수를 정의합니다.

```kotlin
object Android {
    ...
    object Ktx {
        const val core = "androidx.core:core-ktx:1.0.1"
        const val fragment = "androidx.fragment:fragment-ktx:1.0.0"
    }
}
```

이제 app/build.gradle.kts를 업데이트하겠습니다.

```kotlin
dependencies {
    ...

    implementation(Deps.Android.Ktx.core)
    implementation(Deps.Android.Ktx.fragment)
}
```

종속성이 추가되면 다음과 같이 commit() 확장 기능을 사용하여 FragmentTransactions를 정의할 수 있습니다.

```kotlin
supportFragmentManager.commit {
    addToBackStack("fragment name")
    add(SampleFragment(), "tag")
    setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out)
}
```

이렇게 하면 새로운 FragmentTransaction을 추가할 때 Kotlin이 더 관용적으로 느껴지고 이러한 작업과 관련된 일부 상용구가 제거됩니다.

Core KTX 및 Fragment KTX에서 보았듯이 이러한 라이브러리는 Android 개발 경험을 실제로 향상시킬 수 있습니다. 이러한 유형의 확장 및 추가 기능은 Android KTX 종속성 전체에서 사용할 수 있으며 Android 개발을 위해 Kotlin을 활용하는 데 실제로 도움이 될 수 있습니다.

다음 섹션에서는 Kotlin Android Extensions 플러그인과 이 플러그인이 Kotlin을 사용하여 Android 개발을 더 쉽게 만드는 추가 기능을 제공하는 방법을 살펴보겠습니다.

## Using Kotlin Android Extensions

Kotlin Android Extensions 플러그인은 Kotlin 및 Android 작업을 위한 추가 기능 세트를 제공합니다. 이에 대한 가장 큰 두 가지 예는 다음과 같습니다.

- findViewById() 없이 Android 보기 참조
- Parcelable 구현 생성

이러한 기능을 사용 설정하려면 app/build.gradle.kts 파일의 androidExtensions 블록 내에서 실험 기능을 사용 설정해야 합니다. 다음 코드 스니펫은 이 작업을 수행하는 방법을 보여줍니다.

```groovy
androidExtensions {
 isExperimental = true
}
```

이 구성이 build.gradle.kts 파일에 추가되면 Android Extensions 플러그인에 포함된 기능이 활성화됩니다. 다음 섹션에서는 Android view를 구축하는 데 도움이 되는 이러한 기능 중 하나를 살펴보겠습니다.

## Binding views with Kotlin Android Extensions

실험 기능이 켜지면 합성 뷰 바인딩을 참조하여 뷰에 액세스할 수 있습니다. 이것은 Android Extensions 플러그인이 우리를 위해 뷰 바인딩을 생성한다는 것을 의미합니다. MainActivity에서 다음 가져오기를 추가하여 activity_main.xml 파일에 정의된 버튼을 참조할 수 있습니다.

```kotlin
import kotlinx.android.synthetic.main.activity_main.button
```

가져오기를 포함하고 나면 findViewById() 또는 다른 변수 선언을 호출하지 않고도 해당 뷰를 직접 참조할 수 있습니다.

```kotlin
button.apply {
    text = "Hello Kotlin"
    gravity = Gravity.START
    setTextColor(resources.getColor(R.color.colorAccent))
}
```

기본적으로 보기 이름은 XML의 android:id 속성에 따라 지정됩니다. 이 경우 버튼의 ID가 '@+id/button'이므로 생성된 바인딩의 이름은 버튼으로 지정되었습니다. 그러나 다른 변수 이름을 사용하거나 다른 이름과 충돌이 있는 경우 다음 구문을 사용하여 import 문을 업데이트하고 대체 이름을 제공할 수 있습니다.

```kotlin
import kotlinx.android.synthetic.main.activity_main.button as theButton
```

가져오기를 업데이트한 후 이제 Button이라는 이름으로 버튼을 참조할 수 있습니다.

```kotlin
theButton.apply {
    text = "Hello Kotlin"
    gravity = Gravity.START
    setTextColor(resources.getColor(R.color.colorAccent))
}
```

합성 뷰 바인딩은 액티비티 및 프래그먼트와 함께 사용할 때 캐싱을 처리하고 사용자 지정 뷰에서도 작동하도록 만들 수 있습니다. Gradle 파일에서 androidExtensions 블록을 업데이트하여 요구 사항에 따라 캐싱 전략을 제어할 수도 있습니다.

```groovy
androidExtensions {
   // HASH_MAP, SPARSE_ARRAY, NONE
   defaultCacheImplementation = "HASH_MAP" 
}
```

보기 참조에 Kotlin Android Extensions를 사용하면 Butterknife 또는 여러 findViewById() 호출과 같은 타사 라이브러리를 피할 수 있습니다. 이러한 합성 바인딩(DataBinding, ViewBinding 또는 findViewById())을 사용해야 하는지 여부는 프로젝트와 기본 설정에 따라 크게 달라지며 프로젝트별로 평가해야 합니다.

## Generating Parcelable implementations

Parcelable은 보다 성능이 뛰어난 직렬화 API 제공을 목표로 하는 Android 개발의 공통 인터페이스입니다. Parcelable 인터페이스의 구현을 생성하는 것은 단순하고 반복적인 많은 코드를 생성하는 지루하고 상용구로 가득 찬 작업이 될 수 있습니다. 고맙게도 Kotlin Android Extensions는 Parcelable 구현을 생성할 수 있는 주석을 제공합니다. 이 기능을 사용하려면 Parcelable을 구현하는 모든 클래스에 @Parcelize 주석을 추가할 수 있습니다.

```kotlin
@Parcelize
data class Person(val firstName: String, val lastName: String): Parcelable
```

@Parcelize 주석을 모델 개체에 추가하면 플러그인이 필요한 Parcelable 구현을 생성합니다. 이렇게 하면 해당 Parcelable 구현의 구현 및 유지 관리를 건너뛸 수 있습니다. 이렇게 하면 클래스에 필요한 코드의 양이 줄어들고 클래스가 수정될 때마다 Parcelable 구현을 업데이트할 필요가 없습니다. 플러그인은 코드가 컴파일될 때 이를 수행합니다. 플러그인은 코드가 컴파일될 때 이를 수행합니다. 이는 속성 수정에서 일반적으로 발생할 수 있는 오류로부터 보호하는 데 도움이 되지만 Parcelable 구현을 업데이트하는 것을 잊어버립니다.

Kotlin Android Extensions 플러그인을 사용하면 작성 및 유지 관리해야 하는 코드의 양을 줄이고 플러그인이 일반적인 상용구 코드를 생성하도록 할 수 있습니다.

## Summary

이 장에서는 Kotlin과 Android 개발 간의 관계를 살펴보았습니다. 우리는 초창기부터 최근 Google의 Kotlin First 발표까지 Kotlin이 Android에 채택된 역사를 살펴보았습니다. Kotlin을 지원하여 처음부터 새로운 Android 프로젝트를 만드는 방법과 Kotlin 관용적 Android 코드를 작성하는 방법을 자세히 설명했습니다. 마지막으로 Android-KTX, Kotlin Android Extensions 및 Android 아키텍처 구성 요소와 같은 몇 가지 특정 Kotlin 라이브러리 및 도구를 조사했습니다. 우리는 이러한 도구가 Kotlin을 활용하여 Android 개발을 더 쉽고 즐겁게 만드는 방법을 보았습니다.

다음 장에서는 웹 개발을 위해 Android 외부에서 Kotlin을 사용하는 방법을 살펴보겠습니다.
