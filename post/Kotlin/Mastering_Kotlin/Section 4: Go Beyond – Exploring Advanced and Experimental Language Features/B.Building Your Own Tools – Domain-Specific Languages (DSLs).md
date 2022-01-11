# Building Your Own Tools – Domain-Specific Languages (DSLs)

이 장에서는 Kotlin으로 작성된 사용자 정의 도메인별 언어(DSL, domain-specific languages)의 개념과 이러한 언어가 표현적이고 편리한 선언적 API를 구축하는 강력한 도구로 어떻게 사용될 수 있는지 소개한다.

DSL이 다양한 문제 유형에 어떻게 적용될 수 있는지 알아보고 고유한 DSL을 만들 것입니다. 장이 진행됨에 따라 자신만의 DSL 도구를 만들 수 있습니다.

이 장에서는 다음 주제를 다룹니다.

- DSL이란 무엇인가?
- 코틀린 DSL의 구성 요소
- 첫 번째 Kotlin DSL 만들기

## What is a DSL?

이 섹션에서는 DSL의 기초에 대해 알아보겠습니다. 우리는 그것들이 무엇이고 그것들이 왜 유용할 수 있는지에 대해 토론할 것이고, 마지막으로 우리는 서로 다른 도메인에 대한 Kotlin 기반 DSL의 몇 가지 예를 간략히 살펴볼 것이다.

## Domain-specific languages

그렇다면 DSL이란 무엇인가? DSL은 특정한 문제나 도메인 공간을 대상으로 하는 컴퓨터 언어이다. 이는 일반적인 컴퓨팅 문제를 해결할 수 있는 범용 프로그래밍 언어와는 대조적이다. HTML은 매우 특정한 문제를 위해 사용되는 도메인별 프로그래밍 언어의 예입니다.

범용 프로그래밍 언어와 도메인별 언어 간의 구별은 때때로 모호할 수 있다. 예를 들어 HTML은 매우 명확하고 집중적인 목적을 가지고 있는 반면 코틀린은 일반적인 컴퓨팅 문제뿐만 아니라 특정 문제에 대한 맞춤형 DSL을 구축하는 데 사용할 수 있다.

## Where are DSLs used?

도메인별 언어는 다양한 컴퓨팅 도메인에서 나타날 수 있습니다. 몇 가지 일반적인 예는 다음과 같다.

- HTML: 웹 기반 콘텐츠 정의용
- SQL: 관계형 데이터 작업용
- YACC: 문법 및 어휘 구문 분석기 작성용

이러한 예들이 당신에게 꽤 친숙하지만 DSL은 덜 친숙한 많은 다른 도메인에서 사용할 수 있다.

Matlab: 수학 프로그래밍
GraphViz: 그래프 레이아웃 정의용
Gradle DSL: Gradle을 사용한 구성 빌드의 경우

보시다시피 DSL의 사용 사례는 다양하고 다양하며 이러한 DSL과 함께 다양한 언어, 구문 및 규칙이 제공됩니다.

여기서 코틀린 기반의 도메인별 언어라는 개념이 매우 매력적으로 다가온다. 이미 Kotlin에 대해 잘 알고 있다면 이미 알고 있는 언어를 사용하여 동일한 유형의 문제 공간에 대해 자신만의 DSL을 만들 수 있습니다. 이를 통해 적합한 모든 문제 영역에 대해 새로운 도구를 만들 수 있습니다.

이제, 코틀린 기반 DSL의 몇 가지 예를 살펴보자.

### HTML

공식 Kotlin 문서에는 HTML을 사용하는 DSL의 예가 포함되어 있습니다. 이 예에는 DSL을 사용하는 여러 기능들이 어떻게 작동하는지 볼 수 있는 플레이그라운드가 포함되어 있습니다.

> DSL 및 타입 세이프 빌더를 위한 코틀린 설명서는 https://kotlinlang.org/docs/reference/type-safe-builders.html에서 확인할 수 있습니다.

다음 코드 조각은 이 Kotlin DSL을 사용하여 Kotlin 구문을 통해 유효한 HTML 코드를 선언하는 것을 보여줍니다.

```kotlin
fun main() {
    val result = html {
        head {
            title { +"HTML encoding with Kotlin" }
        }
        body {
            h1 { +"HTML encoding with Kotlin" }
            p {
                +"this format can be used as an"
                +"alternative markup to HTML"
            }

            // an element with attributes and text content
            a(href = "http://jetbrains.com/kotlin") { +"Kotlin" }

            // mixed content
            p {
                +"This is some"
                b { +"mixed" }
                +"text. For more see the"
                a(href = "http://jetbrains.com/kotlin") {
                    +"Kotlin"
                }
                +"project"
            }

            p {
                +"Can even leverage loops and control flow"
                ul {
                    for (i in 1..3)
                        li { +"${i}*2 = ${i*2}" }
                }
            }
        }
    }

    println(result.toString())
}
```

선행 코드는 제어 흐름 구조뿐만 아니라 다양한 기능을 사용한다. DSL을 개발하여 문제 공간의 세부 사항을 추상화할 수 있는 좋은 예입니다. 이 경우 각 HTML 요소에 대해 정확한 구문을 기억할 필요가 없으며, 대신 정적으로 입력된 함수에 의존하여 문서 정의를 작성할 수 있습니다.

이전 코드 조각은 화면에 인쇄할 때 다음과 같은 결과를 생성합니다.

```html
<html>
  <head>
    <title>
      HTML encoding with Kotlin
    </title>
  </head>
  <body>
    <h1>
      HTML encoding with Kotlin
    </h1>
    <p>
      this format can be used as an
      alternative markup to HTML
    </p>
    <a href="http://jetbrains.com/kotlin">
      Kotlin
    </a>
    <p>
      This is some
      <b>
        mixed
      </b>
      text. For more see the
      <a href="http://jetbrains.com/kotlin">
        Kotlin
      </a>
      project
    </p>
    <p>
      Can even leverage loops and control flow
      <ul>
        <li>
          1*2 = 2
        </li>
        <li>
          2*2 = 4
        </li>
        <li>
          3*2 = 6
        </li>
      </ul>
    </p>
  </body>
</html>
```

HTML용 DSL을 빌드하는 이 접근법은 선언적 UI를 빌드하기 위한 코틀린 기반 DSL인 안드로이드 개발을 위한 Jetpack Compose 라이브러리 개발에서 취해지는 접근법과 유사하다.

### Testing

테스트를 위한 코틀린 기반 DSL을 제공하는 여러 테스트 프레임워크가 있다. JUnit test 작성 시 코틀린과 함께 작업할 수 있도록 코틀린 친화적인 편의 기능을 제공하기 위해 만든 작은 라이브러리인 MockitoKotlin이 대표적이다.

MockitoKotlin을 사용하면 다음 코드 조각에서 볼 수 있듯이 매우 가독성이 높은 코틀린식 조롱 코드를 작성할 수 있습니다.

```kotlin
val mockGreetingProvider = mock<GreetingProvider> {
    on { greeting } doReturn "Hello"
    on { friendlyGreeting } doReturn "Hi! How are you?"
}
```

이 예제에서는 모의 함수를 호출하고 람다를 전달하여 인터페이스의 각 속성이나 메서드의 동작을 mock 할 수 있습니다. on and doReturn 기능은 인간이 읽을 수 있고 쓰기 쉬운 mock 코드를 작성하는 데 도움이 됩니다.

### Dependency injection

코틀린 DSL이 잘려나간 또 다른 도메인은 의존성 주입, 즉 의존성 검색이다. 코인(Koin)과 코딘(Kodein)은 객체 그래프의 유형을 통해 응용 프로그램 종속성을 얻을 수 있는 라이브러리입니다.

Kodein을 사용하여, 우리는 다음 예에서 보여지듯이 다양한 함수를 사용하여 객체 그래프를 개발할 수 있다.

```kotlin
val kodein = Kodein {
    bind<GreetingProvider>() with provider { FriendlyGreetingProvider() }
    bind<Repository>() with singleton { NetworkRepository() }
}

class ViewModel(private kodein: Kodein) {
    private val repo: Repository by kodein.instance()
}
```

이제 DSL이 무엇인지 이해했으므로 다음 섹션에서는 Kotlin을 사용하여 DSL을 구축하는 방법에 대해 살펴보겠습니다.

## The building blocks of a DSL in Kotlin

이제 일반적인 의미에서 도메인별 언어가 무엇인지 이해했으므로 Kotlin의 DSL 구성 요소를 살펴보겠습니다. 코틀린 기반 DSL은 사람이 읽을 수 있고 정적으로 입력된 구문을 통해 구성 요소를 구성하고 구축하는 데 활용할 수 있는 수신기를 가진 함수 리터럴로 구성되어 있다. 기본 기능 외에도 맞춤형 DSL을 만들 수 있는 Kotlin의 몇 가지 다른 기능들을 살펴볼 것이다.

- 최상위 및 확장 기능
- receivers가 있는 함수 유형
- 사용자 지정 범위 주석

하나씩 차례대로 살펴보도록 하겠습니다.