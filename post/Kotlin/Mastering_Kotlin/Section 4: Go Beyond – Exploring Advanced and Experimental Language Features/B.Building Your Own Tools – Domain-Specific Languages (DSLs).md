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

## Top-level and extension functions

도메인별 언어를 코틀린으로 작성하는 것은 대부분 잘 명명된 함수를 사용하는 것으로 요약된다. 이러한 함수들은 종종 읽기 쉽고 유창한 코드를 만들기 위해 함께 구성될 수 있다. DSL을 사용하려면 어떤 종류의 시작점이 있어야 합니다. 그 시작점은 최상위 함수 또는 확장 함수가 될 것입니다.

이러한 함수 유형을 검토하기 위해 이전 HTML 예제를 다시 살펴보겠습니다.

### Top-level functions

이전의 HTML 예에서 HTML이라는 함수를 호출함으로써 HTML 정의를 구축하기 시작했습니다.

```kotlin
val result = html {
 ...
}
```

html 함수는 글로벌 네임스페이스에서 사용할 수 있는 최상위 함수입니다.

```kotlin
fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()
    html.init()
    return html
}
```

함수의 이름은 HTML에서 반환되는 형식의 이름과 일치합니다. 이것은 함수 호출을 반환될 형식과 일치시키기 때문에 선언형 DSL을 만들 때 일반적인 패턴입니다. 이를 통해 코드가 무엇을 하는지 쉽게 이해할 수 있습니다.

## Extension functions

앞의 예에서 HTML 함수가  호출되었고 그 내부 구현은전달된 init 함수와 함께 사용할 수 있는 HTML 클래스의 초기 인스턴스를 제공하기 위해 남겨졌다. 이것은 처음부터 새로운 HTML 구성을 시작할 때 편리합니다.

경우에 따라 DSL을 기존 개체와 함께 사용할 수 있습니다. 이러한 경우 확장 함수를 사용하여 선언형 DSL에 대체 진입점을 제공할 수 있습니다. 다음 코드는 이를 보여줍니다.

```kotlin
val result = html {
    ...
}

result.html {
    body {
        p {+"add on to existing HTML"}
    }
}
```

기존 HTML 오브젝트의 확장을 활성화하기 위해 다음과 같은 간단한 확장 함수를 만들 수 있습니다.

```kotlin
fun HTML.html(init: HTML.() -> Unit): HTML {
    init()
    return this
}
```

이것은 같은 종류의 수신기 인수를 사용하지만 구성을 위해 확장 함수의 암시적 HTML 수신기에 의존하므로 기존 HTML에 이미 설정된 것에 추가할 수 있다.

## Function types with receivers

코틀린에서 DSL의 힘은 대부분 안전 유형에서 비롯된다. 우리가 조사하던 html 함수를 호출하면 HTML 수신기로 함수 인수를 전달합니다. 코틀린의 람다 구문 때문에, 우리는 전달된 함수를 괄호 밖으로 옮겨서 코드를 읽고 쓰는 것을 더 쉽게 할 수 있다.

```kotlin 
val result = html {
    ...
}
```

다음 스니펫에서는 init 인수가 HTML 타입의 수신기를 가진 함수 타입으로 어떻게 정의되는지 알 수 있다. 수신기로 함수 유형을 나타내려면 먼저 유형을 참조한 다음 .()를 추가해야 합니다. 따라서 이 경우 HTML.()은 HTML 수신기가 있는 유닛을 반환하는 함수 유형을 나타냅니다.

```kotlin 
fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()
    html.init()
    return html
}
```

그런 다음 람다 구문을 사용하여 init 인수를 함수에 전달할 수 있습니다. 전달된 람다 내에서 다음과 같이 HTML 수신기에서 속성 및 호출 메서드에 액세스할 수 있습니다.

```kotlin 
val result = html {
    head {
        ...
    }
    body {
        ...
    }

```

이 경우 헤드 및 바디 함수 호출은 HTML 클래스의 메서드입니다.

```kotlin 
class HTML : TagWithText("html") {
    fun head(init: Head.() -> Unit) = initTag(Head(), init)

    fun body(init: Body.() -> Unit) = initTag(Body(), init)
}
```

람다 블록 내에서 HTML 수신기에 대한 암시적 참조는 HTML 함수 내에 만들어진 새로운 HTML 인스턴스를 참조합니다.

```kotlin 
fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()
    html.init()
    return html
}
```

그러나 앞에서 살펴본 확장 함수의 경우처럼 어떤 HTML 인스턴스라도 사용할 수 있습니다. 이 경우 HTML의 인스턴스를 새로 만드는 대신 확장 함수의 암시적 참조를 사용하여 HTML의 인스턴스에 액세스할 수 있습니다.

```kotlin
fun HTML.html(init: HTML.() -> Unit): HTML {
    this.init()
    return this
}
```

리시버와 함께 기능 타입을 활용함으로써 정적 타입의 안전성과 함께 구성 가능한 기능을 만들 수 있습니다.

## Scope control

우리가 함수 호출을 중첩시키기 시작하면서, 우리는 결국 우리에게 이용 가능한 여러 수신기 문맥을 갖게 될 것이다. 이것은 혼란스럽거나 심지어 잘못되기 시작할 수 있습니다. 예를 들어 HTML 예제에서 다른 HEAD 내부에 HEAD 요소를 생성하는 것은 원하는 동작이 아닐 수 있습니다.

이러한 상황을 피하기 위해 DslMarker 주석을 사용할 수 있습니다. 이러한 주석의 사용은 코틀린 컴파일러에게 주어진 함수의 범위를 제한해야 할 때를 알려주어 외부 범위를 더 이상 사용할 수 없게 할 수 있다.

HTML DSL 예제와 같이 DslMarker를 생성하기 위해 사용자 지정 주석을 만들 수 있습니다.

```kotlin
@DslMarker
annotation class HtmlTagMarker
```

해당 주석을 부모 클래스인 Tag에 추가하여 주어진 범위 내에서 사용할 수 있는 수신기를 표시할 수 있습니다.

```kotlin
@HtmlTagMarker
abstract class Tag(val name: String) : Element {
    ...
}
```

DslMarker를 적용하면 다음 코드가 유효하지 않습니다.

```kotlin
html {
    head {
        // error: can't be called in this context by implicit receiver
        head { } 
    }
}
```

이러한 추가적인 제어 계층은 DSL 내의 어떤 범위에서든 기본적으로 사용 가능한 메소드들이 현재 컨텍스트와 가장 관련이 있음을 보장하여 DSL을 더 쉽게 작업할 수 있게 한다. 이러한 지식으로 우리는 DSL을 최초로 만들고 이를 예시의 도움을 받아 이해할 수 있습니다.

## Creating your first Kotlin DSL

이제 어떤 도구가 우리 마음대로 사용할 수 있는지, 그리고 그것들이 어떻게 함께 작동할 수 있는지 알게 되었으니, 피자집에서 식사를 주문할 수 있는 우리만의 맞춤 DSL을 만들어 봅시다.

### What problem are you trying to solve?

DSL을 만드는 방법에 대해 생각할 때 DSL이 없는 경우 어떤 구문을 사용하고 싶은지, 그리고 솔루션이 어떤 모습일지를 고려하고자 합니다. DSL을 사용하지 않고 코드가 어떻게 보일지 상상해 봅시다.

DSL이 없으면 다음과 같은 모양의 코드를 작성할 수 있습니다.

```kotlin
val order = Order("123")
order.items.put(Sprite, 1)
order.items.put(Coke, 1)

val pizza1 = HawaiianPizza()
pizza1.toppings.add(Pepperoni)

val pizza2 = BuildYourOwn()
pizza2.toppings.add(Pepperoni)
pizza2.toppings.add(Olive)

order.items.put(pizza1, 1)
order.items.put(pizza2, 1)

```

이 코드에서는 모든 항목을 매우 질서정연하게 만들 수 있습니다. 따라 하기는 쉽지만, 코드에는 약간의 반복이 있습니다.

DSL을 사용하지 않고 다음과 같이 이 코드를 단순화할 수 있습니다.

```kotlin
val order = Order("123").apply {
    items.put(Sprite, 1)
    items.put(Coke, 1)   
}

val pizza1 = HawaiianPizza().apply {
    toppings.add(Pepperoni)
}

val pizza2 = BuildYourOwn().apply {
    toppings.add(Pepperoni)
    toppings.add(Olive)   
}

order.apply {
    items.put(pizza1, 1)
    items.put(pizza2, 1)   
}
```

이 버전의 코드는 범위 적용 호출을 사용함으로써 일부 반복을 피하기 시작한다.

우리가 원하는 것은 훨씬 더 자연스럽고 인간이 읽을 수 있는 것이다.

```kotlin
create new order

add coke
add 2 sprite

add pizza {
    pepperoni
    pineapple
    olive
}
```

이 코드는 유사코드 이상도 아니지만 읽고 이해하기 쉬우며 피자숍 DSL을 설계할 때 목표로 삼을 수 있는 목표를 제시합니다.

### Creating your starting point

우리의 목표 DSL 구문 개요에 따라, 우리의 첫 번째 단계는 구성될 수 있는 어떤 종류의 주문 객체를 만드는 것이다:

1. 이렇게 하려면 피자 주문을 저장할 Order 클래스를 만드는 것부터 시작하겠습니다.

```kotlin
class Order(val id: String)
```

주문이 고유할 수 있도록 단일 id 속성을 가진 단순 클래스로 만들었습니다.

2. 다음으로 Order 클래스에 추가할 수 있는 Item 클래스를 만들겠습니다.

```kt
abstract class Item(val name: String)
```

3. 이제 항목 맵과 카운트를 주문 클래스에 추가하겠습니다.

```kt
class Order(val id: String) : Item("Order")  {
    val items: MutableMap<Item, Int> = mutableMapOf()
}
```

4. Item 맵만 있으면 주문을 작성할 수 있습니다. 이를 위해 다음과 같은 최상위 함수를 만듭니다.

```kt
fun order(init: Order.() -> Unit): Order {
    val order = Order(UUID.randomUUID().toString())
    order.init()
    return order
}
```

다음 흥미로운 두 요소 이 기능과 관련하여:준수해야 한다.

- 함수 이름이 반환 유형을 미러링합니다.
- 우리는 주문 수신기를 사용하여 함수 인수를 전달합니다.

이 두 가지 특성은 우리가 함수를 호출하고 DSL의 선언적 특성을 강제하는 데 도움이 되는 새로운 순서를 만들고 있음을 이해할 수 있게 한다. 이를 통해 전달된 람다 내에서 Order 인스턴스를 구성할 수도 있습니다.

```kt
val order = order {
    println(this.id)
}
```

이제 우리는 주문에 상품을 추가할 준비가 되었습니다.

## Adding elements

이제 주문에 항목을 추가하는 방법을 살펴보겠습니다. 구체적으로, 우리는 구성 불가능한 `Soda` 아이템을 추가한 후 구성 가능한 피자 아이템을 추가하는 것을 검토할 것입니다.

### Adding sodas

주문에 탄산음료 추가에 대한 지원을 추가하는 것부터 시작하겠습니다.

1. 이를 위해 Soda를 다음과 같은 몇 가지 옵션이 있는 밀봉된 클래스로 정의하겠습니다.

```kotlin
sealed class Soda(name: String) : Item(name)
object Coke : Soda("Coke")
object Sprite : Soda("Sprite")
object Dr_Pepper : Soda("Dr Pepper")
```

2. Soda 클래스가 마련되어 있으면 Order.Items에 직접 액세스하여 주문에 Soda를 추가할 수 있습니다.

```kt
val order = order {
    this.items[Coke] = this.items.getOrDefault(Coke, 0) + 1
}
```

이것은 작동하지만 상세하며 기본 데이터 구조와의 직접적인 상호 작용이 필요합니다. 구문을 더 자연스럽게 만들고 데이터 구조를 숨김으로써 이것을 개선합시다.

이렇게 할 수 있는 방법은 다음과 같습니다.

1. 먼저 이전 코드를 간단한 방법으로 리팩터링하여 지도에 탄산음료를 추가하자.

```kt
fun soda(soda: Soda) = items.put(soda, items.getOrDefault(soda, 0) + 1)
```

이제 구현 세부 정보가 유출되지 않고 코드가 훨씬 적은 탄산음료를 추가할 수 있습니다.

```kt
val order = order {
    soda(Coke)
}
```

3. 또한 Soda 클래스에 UnaryPlus 연산자를 구현하여 주문에 Soda를 추가하는 데 보다 능숙한 구문을 제공할 수 있습니다. 이전 방법의 구현을 사용하여 이 작업을 수행할 수 있습니다.

```kt
operator fun Soda.unaryPlus() = soda(this)
```

4. 이 새로운 추가 연산자를 사용하면 다음과 같은 주문에 탄산음료를 추가할 수 있습니다.

```kt
val order = order {
    soda(Coke)
    +Sprite
}
```

우리가 주문에 탄산음료를 여러 개 추가하고 싶다면요? 우리는 원할 때마다 탄산음료를 수동으로 추가할 수도 있고, 품목 신고와 동시에 수량을 업데이트할 수 있는 메커니즘을 제공할 수도 있습니다.

이를 구현하기 위해 Soda 클래스에 새로운 infix 기능을 추가할 예정입니다.

```kt
infix fun Soda.quantity(quantity: Int) {
    items.put(this, items.getOrDefault(this, 0) + quantity)
}
```

이제 탄산음료를 추가하고 사람이 읽을 수 있는 방식으로 수량을 지정할 수 있습니다.

val order = order {
    soda(Coke)
    +Sprite
    Coke quantity 2
}

> [소스 코드 보러가기](https://github.com/PacktPublishing/Mastering-Kotlin/tree/master/Chapter11/src/pizza)


### Adding pizzas

이제 피자를 주문에 추가할 수 있는 기능을 추가하고 피자를 구성해 보겠습니다.

1. 먼저 피자 클래스에 포함되는 토핑 클래스를 만들 것입니다.

```kt
sealed class Topping(name: String): Item(name) // 토핑 클래스
object Pepperoni : Topping("Pepperoni")
object Olive : Topping("Olive")
object Pineapple : Topping("Pineapple")
```

2. 이제 Pizza 클래스를 추가합니다.

```kt
sealed class Pizza(name: String) : Item(name) {
    val toppings: MutableList<Topping> = mutableListOf()
}
```

이 Pizza 클래스는 Item을 확장하고 피자 토핑을 저장하기 위한 MutableList<Topping> 필드가 있습니다.

3. 다음으로, 우리는 우리의 사용자가 주문할 수있는 피자의 몇 가지 미리 정의 된 유형을 만들 것입니다 :

```kt
class BuildYourOwn(init: Pizza.() -> Unit = {}) : 
                   Pizza("Build Your Own Pizza") {
    init {
        init.invoke(this)
    }
}
class PepperoniPizza(init: Pizza.() -> Unit = {}) : 
                     Pizza("Pepperoni Pizza") {
    init {
        toppings.add(Pepperoni)
        init.invoke(this)
    }
}
```

두 클래스 모두 피자 리시버를 사용하는 함수인 init 인수를 취한다. 그런 다음 클래스 초기화 중에 이 함수를 호출하여 클래스 작성자가 인스턴스를 구성할 수 있도록 합니다. 페퍼로니피자 클래스는 또한 피자에 기본 토핑을 추가합니다.

토핑 클래스에 대해 unaryPlus 연산자를 추가하면 피자 인스턴스를 구성하는 컨텍스트 내에서 토핑을 추가하는 방법을 단순화할 수 있습니다.

```kt
operator fun Topping.unaryPlus() = toppings.add(this)
class PepperoniPizza(init: Pizza.() -> Unit = {}) : 
                     Pizza("Pepperoni Pizza") {
    init {
        +Pepperoni
        init.invoke(this)
    }
}
```

이제 피자를 주문에 추가할 준비가 되었습니다. 시작하려면 Order 클래스에 새 메서드를 만듭니다.

```kt
fun pizza(init: Pizza.() -> Unit) {
    val pizza = BuildYourOwn()
    pizza.init()
    items[pizza] = 1
}
```

이 방법은 피자를 구성할 수 있도록 피자 리시버의 함수 유형을 사용합니다. 또한 기본 `BuildYourOwn` 인스턴스와 피자를 항목 맵에 추가합니다. 이제 주문에 피자를 추가할 수 있습니다.

```kt
val order = order {
    soda(Coke)
    +Sprite
    Dr_Pepper quantity 2
    
    pizza { 
        +Pineapple
    }
}
```

미리 정의된 피자를 추가한 다음 직접 구성하려고 하면 어떨까요? 그러기 위해서는 피자용 unaryPlus 연산자를 주문 클래스에 추가할 수 있습니다.

```kt
operator fun Pizza.unaryPlus() {
    items.put(this, items.getOrDefault(this, 0) + 1)
}
```

이제 + 연산자를 사용하여 피자를 추가한 다음 구성 블록을 넘겨 토핑을 업데이트할 수 있습니다.

```kt
val order = order {
    soda(Coke)
    +Sprite
    Dr_Pepper quantity 2

    pizza {
        +Pineapple
    }
    
    +HawaiianPizza {
        +Pepperoni
    }
}
```

우리는 이제 소다와 피자 둘 다 주문에 추가할 수 있는 몇 가지 방법을 가지고 있습니다. 이제 @DslMarker를 추가하여 구성(configuration)이 가장 로컬 범위와 관련된 기본 function을 차단하는지 확인해 보겠습니다.

### Making it easy to use

현재는 @DslMarker 주석이 없기 때문에 이러한 함수 호출을 중첩하기 시작하면 서로 다른 여러 수신기에 대한 함수에 액세스할 수 있습니다. 예를 들어, 다음의 코드 조각에서, 우리는 피자를 위한 init 블록 내의 soda 함수를 호출할 수 있다.

```kt
+HawaiianPizza {
    +Pepperoni
    soda(Coke) // want to avoid this
}
```

피자에 탄산음료를 첨가하는 것은 말이 되지 않기 때문에 이러한 행동은 이상적이지 않습니다. 따라서 기본적으로 이 동작을 제한하려고 합니다. 이를 위해 새 @DslMarker 주석을 만들 수 있습니다.

```kt
@DslMarker
annotation class ItemTagMarker
```

그런 다음 해당 항목을 항목의 기본 클래스에 추가할 수 있습니다.

```kt
@ItemTagMarker
abstract class Item(val name: String)
```

만약 당신이 암묵적 수신기의 메소드 속성에 접근하려고 한다면 컴파일러는 그것을 오류로 표시할 것이다:

```kt
+HawaiianPizza {
    +Pepperoni
    soda(Coke) // now an error
}
```

이것은 또한 외부 receiver를 참조하고자 하는 경우에는 명시적으로 호출해야 한다는 것을 의미합니다. 이것의 한 예는 Toping.unaryPlus()의 구현에 있다.

```kt
operator fun Topping.unaryPlus() = this@Pizza.toppings.add(this)
```

이제, 우리의 주문이 어떻게 보이는지 보고 싶다면, 우리는 약간의 로깅 기능을 추가할 수 있습니다. 시작하려면 항목 기본 클래스에 인쇄 방법을 추가합니다.

```kt
@ItemTagMarker
abstract class Item(val name: String) {
    open fun log(indent: String = "") {
        println("$indent$name")
    }
}
```

피자에 대해 다음과 같이 해당 동작을 재정의합니다.

```kt
sealed class Pizza(name: String) : Item(name) {

    val toppings: MutableList<Topping> = mutableListOf()

    operator fun Topping.unaryPlus() = this@Pizza.toppings.add(this)

    override fun log(indent: String) {
        super.log(indent)
        toppings.forEach {
            println("$indent      ${it.name}")
        }
    }
}
```

마지막으로 log() 방법을 사용하여 전체 주문을 인쇄할 수 있는 print() 방법을 주문 클래스에 추가합니다.

```kt
fun log() {
    println("Order: $id")
    println("Items")
    items.forEach {
        print("${it.value} x ")
        it.key.log("  ")
    }
}
```

이제 다음 주문을 출력할 수 있습니다.

```kt
fun main() {

    val order = order {
        soda(Dr_Pepper)
        soda(Coke)

        Sprite quantity 1

        +Coke
        +Dr_Pepper

        Sprite quantity 2

        +HawaiianPizza {
            +Pepperoni
        }
        pizza {
            +Pepperoni
            +Olive
        }
    }

    order.log()
}
```

이 오더를 통해 다음과 같은 출력을 얻을 수 있습니다.

```kt
Order: 9de6134e-23f3-44e5-88f1-2af373c399a8
Items
2 x Dr Pepper
2 x Coke
3 x Sprite
1 x Hawaiian Pizza
      Pineapple
      Pepperoni
1 x Build Your Own Pizza
      Pepperoni
      Olive
```

이를 통해 피자집 주문을 위한 매우 간단한 DSL을 구축했습니다. 프로덕션 DSL의 경우, 우리는 더 많은 구성성을 구축하고 기능을 확장하고 싶지만, 구성 요소는 동일합니다.

## Summary

도메인별 언어(DSL)는 특정 유형의 문제를 해결하기 위한 편리하고 선언적이며 안전한 유형 구문을 제공합니다. 코틀린으로 작성된 DSL은 HTML 레이아웃 선언, 모바일 UI 프레임워크 구축, 웹 서버용 HTTP 경로 정의와 같은 문제에 적용되어 왔다. 우리는 코틀린에서 DSL은 주로 기능과 수신기가 있는 함수 리터럴로 구성된다는 것을 보았습니다. 이러한 메커니즘을 통해, 우리는 인간이 읽을 수 있는 선언적 구문을 구축하는 데 유용한 타입 세이프 빌더를 구축할 수 있다. 또한 확장 기능, infix 표기법 및 사용자 지정 주석과 같은 Kotlin 기능이 사용자 지정 DSL의 유용성과 가독성을 어떻게 향상시킬 수 있는지 살펴봤습니다. 마지막으로 모바일 UI를 정의하기 위해 이러한 기능을 자체 도메인별 언어 구성에 적용하는 방법을 배웠다.

다음 챕터에서는 Kotlin의 함수 프로그래밍에 대해 살펴보고 높은 기능의 코드를 작성하는 데 사용할 수 있는 특정 라이브러리에 대해 알아보겠습니다.

## 질문들

- DSL은 무엇의 약자입니까?
- DSL이 적용될 수 있는 도메인의 두 가지 예를 들어볼 수 있나요?
- 코틀린에서 DSL의 두 가지 주요 구성 요소는 무엇입니까?
- DSL 기능의 범위를 제어하는 두 가지 메커니즘은 무엇입니까?
- @DslMarker 주석의 용도는 무엇입니까?
- 맞춤 DSL이 제공할 수 있는 두 가지 이점은 무엇입니까?

## 추가

Functional Kotlin (https://www.packtpub.com/application-development/functional-kotlin)  
Hands-On Design Patterns with Kotlin (https://www.packtpub.com/application-development/hands-design-patterns-kotlin)  
Learning Kotlin by Building Android Applications (https://www.packtpub.com/application-development/learning-kotlin-building-android-applications)  


