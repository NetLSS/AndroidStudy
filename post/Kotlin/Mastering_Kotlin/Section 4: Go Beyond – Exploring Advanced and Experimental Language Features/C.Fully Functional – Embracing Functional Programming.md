# Fully Functional – Embracing Functional Programming

함수형 프로그래밍은 개발자가 순수한 수학적 연산을 통해 데이터와 비즈니스 로직을 모델링할 수 있도록 도와줌으로써 현대의 객체 지향 코드에서 가장 큰 과제 중 하나인 상태 관리를 제한하기 때문에 확장 가능한 애플리케이션을 작성하는 강력한 도구가 될 수 있다.

이 장에서는 독자에게 코틀린과 함께 함수형 프로그래밍을 달성하는 방법에 대해 더 잘 이해할 수 있도록 제공할 것입니다. 함수형 프로그래밍의 몇 가지 이점에 대해 살펴보고, 코틀린이 최상위 및 고차 함수를 통해 어떻게 지원하는지 살펴본 다음, 코틀린 표준 라이브러리와 화살표를 사용하여 더 많은 함수형 코드를 작성하는 방법에 대해 알아보겠습니다.

이 장은 다음과 같이 세분화됩니다.

- 함수형 프로그래밍의 특성 및 이점
- 고차 함수 쓰기
- 코틀린 표준 라이브러리 활용
- 화살표를 사용한 기능 프로그래밍

[깃허브 저장소](https://github.com/PacktPublishing/Mastering-Kotlin/tree/master/Chapter12)

## Understanding functional programming

3장 '코틀린의 프로그래밍 패러다임 이해'에서 살펴본 것처럼 함수형 프로그래밍은 순수한 수학적 함수를 이용해 프로그램이 표현되는 선언형 프로그래밍 패러다임이다. 이 섹션에서는 이것이 실제로 무엇을 의미하는지, 그리고 왜 Kotlin의 함수형 프로그래밍을 도입하고자 하는지 알아보겠습니다.

# Pure functions

함수 프로그래밍의 핵심 교의 중 하나는 순수 함수의 적용을 통한 데이터의 변환이다. 함수 프로그래밍의 가장 엄격한 정의에 따르면, 이러한 순수 함수는 수학적 연산으로 표현된다. 함수형 프로그래밍의 순수하고 수학적 특성은 다음과 같은 두 가지 흥미로운 특성을 낳는다.

변수의 불변성
적은 부작용 

기능 코드의 이러한 속성은 Kotlin 또는 다른 언어로 코드를 작성할 때 큰 도움이 될 수 있으며 다음 섹션에서 두 가지 모두에 대해 살펴보겠습니다.

## Immutability

프로그램을 구축하는 것은 복잡한 상태를 모델링하고 관리하는 연습이 되기도 합니다. 객체 지향 프로그래밍이 이 상태를 더 쉽게 모델링할 수 있지만 데이터 동기화 및 일관성 문제를 일으킬 수 있다. 상태를 나타내는 모델이 언제든 변경될 수 있다면 주어진 애플리케이션의 현재 상태를 추론하는 것이 더 어려워진다. 상태를 언제 어디서 조작하고 있는지, 상태를 어떻게 수정하는 것이 최선인지조차 이해하기 어려워진다.

함수 코드 내에서 변수와 상태의 불변성이 매력적인 이유다. 만약 우리의 상태를 나타내는 모델이 불변이라면, 상태를 만들거나 수정하는 계약은 훨씬 더 명확해진다. 새로운 상태를 나타내려면, 우리는 그 상태를 유지할 새로운 모델을 만들어야 합니다. 이제 주어진 변수의 정적이고 변하지 않는 상태에 의존할 수 있기 때문에 추적하기가 더 쉬워진다.

이는 함수형 프로그래밍의 다음 유용한 특성인 제한된 부작용과 짝을 이룰 때 더욱 관련이 깊어진다.

## Limited side effects

순수 수학 함수는 부작용이 발생하지 않는다. 프로그래밍 세계에서, 이것은 주어진 함수를 호출하면 단일 출력이 생성되고 다른 작업은 하지 않는다는 것을 의미한다. 순수 함수는 전역 상태 조작, 네트워크 요청, 데이터베이스 업데이트 등의 작업 없이 출력을 반환합니다.

부작용을 낳지 않는 함수를 작성함으로써 다시 데이터 흐름과 애플리케이션의 상태가 어떻게 표현되고 조작되는지 추론하기 쉽게 만들고 있다. 함수가 단일 명시적 작업에 초집중적일 때, 상태가 어떻게 수정되는지를 이해하는 것이 훨씬 쉬워진다.

# Reduced complexity

우리 프로그램의 불변성과 부작용의 제한은 그 프로그램들의 복잡성을 줄이는 데 효과가 있다. 어떤 코드 베이스들은 필요 이상으로 복잡하다; 문제 자체는 복잡하다. 그러나 상태가 어떻게 표현, 관리 또는 조작되는지 더 이상 명확하지 않기 때문에 많은 코드 베이스가 복잡해진다. 기능적 프로그래밍이 큰 이점을 얻을 수 있는 곳이 바로 이와 같은 경우입니다.

기능 코드를 코틀린으로 작성하면 다음과 같은 여러 가지 방법으로 복잡성을 줄일 수 있습니다:

- Kotlin은 데이터 클래스를 사용하여 불변성을 적용할 수 있게 해줍니다. 데이터 클래스를 사용하면 상태를 나타내는 불변 모델을 매우 쉽게 정의할 수 있습니다. 다음은 UI의 현재 상태를 나타내는 불변 데이터 클래스를 정의한 예입니다:

```kt
data class ViewState(val title: String, val subtitle: String)
```

또한 Kotlin은 이전 상태를 기반으로 새 상태를 만들 수 있는 편리한 구문을 제공합니다. 이 작업은 데이터 클래스에 대해 생성된 복사 생성자와 함께 명명된 인수를 사용하여 수행할 수 있습니다. 다음 코드를 생각해 보십시오.

```kt
val initialState = ViewState("Hello", "Kotlin")
val updatedState = initialState.copy(title = "Hey There!")
```

  이 코드에서는 제목 속성을 "Hey There!" 값으로 바꾸면서 initialState 변수의 복사본을 만들 수 있습니다. 이를 통해 기존 상태를 수정하지 않고도 원하는 제목 속성을 명시적으로 업데이트할 수 있습니다.

코틀린은 최상위 함수를 지원하기 때문에 유용한 메서드/함수를 포함하는 것 외에 다른 목적을 제공하지 않는 유틸리티 및 도우미 클래스의 생성을 방지함으로써 코틀린 코드 베이스의 복잡성을 줄일 수 있다. 예를 들어, Java에는 다음과 같이 구현되는 DateHelpers라는 클래스가 있을 수 있습니다.

```java
class DateHelpers {
    private static final String pattern = "MM-dd-yyyy";
    
    public String formatDateForUI(Date date) {
        SimpleDateFormat simpleDateFormat = new 
          SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}
```

그러나 Kotlin에는 최상위 함수가 있으며 정적 메소드를 포함하는 클래스를 명시적으로 만들 필요 없이 동일한 함수를 구현할 수 있습니다. 이와 동일한 기능의 코틀린 구현은 다음과 같을 수 있습니다.

```kt
private const val pattern = "MM-dd-yyyy"
fun formatDateForUI(date: Date): String {
    val simpleDateFormat = SimpleDateFormat(pattern)
    return simpleDateFormat.format(date)
}
```

이러한 불변성 적용, 편리하고 명시적인 데이터 클래스 복사 및 도우미 클래스 감소는 코드베이스 내의 복잡성을 줄이는 데 도움이 될 수 있다. 코틀린이 함수 프로그래밍을 지원하고 그 지원의 혜택을 받는 가장 큰 방법 중 하나는 고차 함수 지원을 통해서이다. 다음 섹션에서는 고차 함수가 코틀린의 함수 프로그래밍을 어떻게 개선하는지 살펴보겠습니다.

## Understanding advanced functions

함수형 프로그래밍 언어에서 함수는 일반적으로 언어의 일급 시민으로 취급된다. 고차 함수는 물론 최상위 함수를 지원하는 코틀린에서도 마찬가지여서 함수를 다른 함수의 입출력처럼 처리할 수 있다는 의미다.

이 섹션에서는 코틀린에서 고차 기능의 두 가지 중요한 측면에 대해 알아보겠습니다. 다음은 다음과 같습니다.

- 기능 유형 작업
- 고차 함수 성능 향상

이러한 섹션을 통해 코드에서 고차 함수를 효율적으로 사용하는 방법과 코틀린 표준 라이브러리가 어떻게 구축되는지 이해할 수 있습니다.

## Working with functional types

앞서 이 책에서 살펴본 것처럼 코틀린은 데이터 유형으로서의 함수을 지원합니다. 이를 통해 다음 두 가지 작업을 수행할 수 있습니다.

- 함수를 변수 및 속성으로 저장
- 함수를 다른 함수에 인수로 전달합니다.

이 섹션에서는 이러한 각 사항에 대해 더 자세히 알아보겠습니다.

## Functional variables

함수 변수 작업 방법을 빠르게 새로 고치겠습니다. 다음과 같은 함수 유형을 사용하여 변수를 정의할 수 있습니다.

```kt
var onClickHandler: (ViewState) -> Unit = {}
```

그런 다음 저장된 함수를 다른 변수와 마찬가지로 재할당할 수 있다.

```kt
fun main() {
    onClickHandler = { viewState ->
        println("viewState -> ${viewState.title} 
                ${viewState.subtitle}")
    }
}
```

마지막으로, 변수가 함수를 나타내므로 다음과 같이 변수 이름을 참조하여 함수를 호출할 수 있습니다.

```kt
fun main() {
    onClickHandler = { viewState ->
        println("viewState -> ${viewState.title} 
                ${viewState.subtitle}")
    }
    
    val viewState = ViewState("Hello", "Kotlin")
    onClickHandler(viewState)
    onClickHandler.invoke(viewState)
}
```

함수 유형으로 변수를 저장하고 업데이트할 수 있게 함으로써, 코틀린은 기능 연산에 더 많이 의존하는 기능 측면과 코드 작성을 시작할 수 있게 한다.

## Functional arguments

이제 함수 유형을 사용한 작업을 검토했으므로 이러한 함수 유형을 다른 함수에 대한 인수로 사용하는 방법에 대해 살펴보겠습니다.

다음은 이전에 정의한 onClickHandler 변수의 함수 시그니처와 일치하는 함수 매개 변수를 정의한 고차 함수의 기본 예입니다.

```kt
class ViewModel(val viewState: ViewState, val clickHandler:(ViewState) -> Unit)

fun createViewModel(viewState: ViewState, clickHandler: (ViewState) -> Unit) : ViewModel {
    return ViewModel(viewState, clickHandler)
}
```

그런 다음 onClickHandler 함수 변수를 새로 정의된 createViewModel() 함수에 인수로 전달할 수 있습니다.

```kt
fun main() {
    ...
    
    createViewModel(viewState, onClickHandler)
}
```

다른 유형과 마찬가지로 함수를 변수로 저장한 다음 정의된 유형을 예상하는 위치로 전달할 수 있습니다. 함수 매개변수는 우리가 몇 가지 흥미로운 것들을 할 수 있게 해줍니다. 함수 생성자 파라미터를 사용하여 클래스를 생성하고 구성할 수 있는 예는 다음과 같습니다.

```kt
class LoadingViewModel(config: (LoadingViewModel) -> Unit) {
    var title = ""
    var subtitle = ""
    var loadingMsg = ""
    val successMsg = ""

    init {
        config(this)
    }
}
```

이 LoadingViewModel에는 사용할 때 필요하거나 필요하지 않은 몇 가지 속성이 있습니다. 클래스가 인스턴스화될 때 클라이언트가 각 속성의 값을 지정하도록 강제하는 대신 다음과 같이 LoadingViewModel 인스턴스를 만들고 구성할 수 있습니다.

```kt
LoadingViewModel { loadingViewModel ->
    loadingViewModel.title = "Hello"
    loadingViewModel.loadingMsg "Loading..."
}
```

이렇게 하면 람다 구문과 함수 파라미터를 사용하여 () 없이 LoadingViewModel을 인스턴스화한 다음 전달된 람다 내에서 속성을 구성할 수 있습니다. 이 문법은 클래스를 구성하는 함수이기 때문에 더 기능적인 코드 베이스 내에서 매우 유창하고 자연스럽게 느껴지기 시작합니다.

이전 장에서 살펴본 것처럼 수신기와 함께 함수 유형을 사용함으로써 이러한 구성 패턴을 개선할 수 있습니다. LoadingViewModel을 유일한 매개 변수로 사용하는 대신 LoadingViewModel의 인스턴스를 수신기로 갖도록 함수 매개 변수를 다음과 같이 리팩터링할 수 있습니다.

```kt
class LoadingViewModel(config: LoadingViewModel.() -> Unit) {
    var title = ""
    var subtitle = ""
    var loadingMsg = ""
    val successMsg = ""

    init {
        config(this)
    }
}
```

이제 구성 매개 변수는 LoadingViewModel의 인스턴스를 수신기로 사용하는 함수를 사용하고 현재 인스턴스를 클래스의 init{} 블록 내에 있는 구성 함수에 전달할 수 있습니다.

수신기와 함께 함수 유형을 사용하면 다음과 같이 전달된 LoadingViewModel을 명시적으로 참조할 필요가 없습니다.

```kt
LoadingViewModel {
    this.title = "Hello"
    this.loadingMsg = "Loading..."
}
```

LoadingViewModel 인스턴스를 람다 컨텍스트 내에서 암시적으로 참조할 수 있습니다. 이러한 점을 염두에 두고 이전의 예제를 다음과 같이 더욱 단순화할 수 있습니다.

```kt
LoadingViewModel {
    title = "Hello"
    loadingMsg = "Loading..."
}
```

이 최신 예제에서는 제목을 참조하고 Msg 속성을 로드할 때 이 함수의 명시적인 사용을 제거했습니다. 이것과 다른 고차 함수들의 예들을 통해, 우리는 클래스들과 API들을 디자인하고 소비할 때 그것들이 얼마나 유용하게 사용될 수 있는지 알게 되었습니다. 다음 섹션에서는 고차 기능이 프로그램 성능에 부정적인 영향을 미치지 않도록 하는 방법에 대해 살펴보겠습니다.

## Improving the performance of higher-order functions

고차 함수는 상당히 유용할 수 있습니다. 하지만, 그들의 효용은 공짜가 아닙니다. 고차 함수 사용에 따른 간접비가 발생합니다. 이는 컴파일러 수준에서 고차 함수가 작동하는 방식에서 기인합니다. 이 섹션에서는 인라인 및 노라인 한정자를 통해 고차 함수의 성능 특성을 제어할 수 있는 방법에 대해 살펴보겠습니다.

## The inline modifier

우리가 고차 함수의 성능을 제어할 수 있는 한 가지 방법은 인라인 한정자 키워드를 사용하는 것입니다. 인라인 한정자를 사용하면 컴파일러에게 함수의 구현이 콜 사이트에서 인라인될 수 있음을 알려줌으로써 고차 함수의 성능을 향상시킬 수 있으므로 고차 함수의 변수 캡처 및 클래스 인스턴스화와 관련된 성능 오버헤드를 피할 수 있습니다.

이 기능의 작동 방식을 살펴보려면 먼저 이 기능을 정의한 다음 안전하게 실행:

```kt
fun safelyRun(action: () -> Unit) {
    try {
        action()
    } catch (error: Throwable) {
        println("Caught error: ${error.message}")
    }
}
```

이것은 try/catch 블록으로 호출을 래핑하면서 람다가 전달되는 모든 것을 실행하는 최상위 고차 함수입니다. 함수 매개 변수를 사용하는 것으로 정의되지만 컴파일러 수준에서는 함수 0 클래스의 인스턴스가 필요합니다.

```java
public static final void safelyRun(@NotNull Function0 action) {
   Intrinsics.checkParameterIsNotNull(action, "action");

   try {
      action.invoke();
   } catch (Throwable var4) {
      String var2 = "Caught error: " + var4.getMessage();
      boolean var3 = false;
      System.out.println(var2);
   }

}
```

Function0 클래스는 람다 내에서 해당 값에 액세스할 수 있도록 람다 내에서 참조되는 모든 변수를 캡처합니다.

따라서 안전하게 실행() 기능을 사용하면 다음과 같습니다.

```kt
fun main() {
    val greeting = "Hello"
    safelyRun {
        println("$greeting Kotlin")
    }
}
```

컴파일러는 다음과 같은 코드를 생성합니다.

```java
public static final void main() {
   final String greeting = "Hello";
   safelyRun((Function0)(new Function0() {
      // $FF: synthetic method
      // $FF: bridge method
      public Object invoke() {
         this.invoke();
         return Unit.INSTANCE;
      }

      public final void invoke() {
         String var1 = greeting + " Kotlin";
         boolean var2 = false;
         System.out.println(var1);
      }
   }));
}
```

safeRun()을 호출할 때 외부 클래스와 해당 필드에 대한 암시적 참조가 있는 익명 내부 클래스가 생성됩니다. 이것은 람다가 속성이나 지역 변수와 같은 모든 필수 상태를 캡처할 수 있는 방법입니다. 이 익명 내부 클래스는 람다가 평가될 때마다 인스턴스화됩니다.

고차 함수가 대규모 수집에서 작동하는 경우, 예를 들어 람다 상태 캡처와 관련된 오버헤드가 루프의 각 반복에 대해 지불되므로 더 많은 양의 메모리와 더 많은 수의 가상 메서드 호출이 필요합니다.

다행히 인라인 키워드를 활용하면 이러한 오버헤드를 방지할 수 있습니다. 인라인 함수를 고차 함수에 추가함으로써 컴파일러에게 주어진 함수의 호출을 호출 사이트에서 해당 함수의 실제 구현으로 대체하도록 지시합니다. 예를 업데이트하여 이를 설명하겠습니다.

먼저 다음과 같이 인라인 키워드를 safeRun() 함수에 추가합니다.

```kt
inline fun safelyRun(action: () -> Unit) {
    try {
        action()
    } catch (error: Throwable) {
        println("Caught error: ${error.message}")
    }
}
```

이제 생성된 코드를 살펴보면 인라인 추가의 영향을 알 수 있습니다.

```java
public static final void main() {
   String greeting = "Hello";
   boolean var1 = false;

   try {
      int var7 = false;
      String var8 = greeting + " Kotlin";
      boolean var4 = false;
      System.out.println(var8);
   } catch (Throwable var6) {
      String var2 = "Caught error: " + var6.getMessage();
      boolean var3 = false;
      System.out.println(var2);
   }

}
```

safeRun()의 본문이 main()의 본문으로 다시 작성되었습니다. 이렇게 하면 main()에 정의된 인사말 변수를 캡처하기 위해 Function0의 새 인스턴스를 만들 필요가 없습니다. safeRun()을 호출할 때마다 Function0 인스턴스를 생성할 필요가 없으므로 코드의 성능이 향상되었습니다.

## The noinline modifier

인라인 한정자를 고차 함수에 추가하면 전달된 모든 람다는 콜 사이트에서 인라인 상태가 됩니다. 하지만 이것이 당신이 원하지 않는 경우가 있을 수 있습니다. 이러한 상황에서 우리는 노라인을 활용할 수 있습니다.

우리는 우리의 고차 함수의 함수 파라미터에 noinline 수식어를 추가할 수 있습니다. 이것은 컴파일러에게 특정 람다를 인라인으로 만들지 않도록 지시합니다. 이전 예제를 사용하여 이에 대해 살펴보겠습니다.

1. 두 번째 함수 매개변수를 safeRun()에 추가합니다.

```kt
inline fun safelyRun(action: () -> Unit, action2:() -> Unit) {
    try {
        action()
        action2()
    } catch (error: Throwable) {
        println("Caught error: ${error.message}")
    }
}
```

2. 이제 두 번째 람다를 통과하도록 사용방식을 업데이트하겠습니다.

```kt
fun main() {
    val greeting = "Hello"
    safelyRun({ println("Hi Kotlin")}) {
        println("$greeting Kotlin")
    }
}
```

3. 생성된 코드를 살펴보면 이전과 매우 비슷하다는 것을 알 수 있습니다. 콜 사이트에서 lamda 두 개가 모두 인라인으로 표시됩니다.

```java
public static final void main() {
   String greeting = "Hello";
   boolean var1 = false;

   try {
      int var7 = false;
      String var8 = "Hi Kotlin";
      boolean var4 = false;
      System.out.println(var8);
      var7 = false;
      var8 = greeting + " Kotlin";
      var4 = false;
      System.out.println(var8);
   } catch (Throwable var6) {
      String var2 = "Caught error: " + var6.getMessage();
      boolean var3 = false;
      System.out.println(var2);
   }

}
```

4. 이제 action2 매개 변수에 noinline을 추가하겠습니다.

```kt
inline fun safelyRun(action: () -> Unit, noinline action2:() -> Unit) {
    ...
}
```

5. 이제 생성된 코드는 두 번째 람다를 인라인으로 만들지 않고 대신 함수0 인스턴스를 생성하여 람다에 필요한 로컬 상태를 캡처합니다.

```java
public static final void main() {
   final String greeting = "Hello";
   Function0 action2$iv = (Function0)(new Function0() {
      // $FF: synthetic method
      // $FF: bridge method
      public Object invoke() {
         this.invoke();
         return Unit.INSTANCE;
      }

      public final void invoke() {
         String var1 = greeting + " Kotlin";
         boolean var2 = false;
         System.out.println(var1);
      }
   });
   boolean var2 = false;

   String var4;
   boolean var5;
   try {
      int var3 = false;
      var4 = "Hi Kotlin";
      var5 = false;
      System.out.println(var4);
      action2$iv.invoke();
   } catch (Throwable var6) {
      var4 = "Caught error: " + var6.getMessage();
      var5 = false;
      System.out.println(var4);
   }

}
```

인라인 및 noinline을 사용하면 컴파일러가 고차 함수에 대해 정의한 함수 매개변수를 처리하는 방법을 제어할 수 있습니다. IntelliJ 기반 IDE는 인라인 함수의 성능에 미치는 영향이 무시할 수 있을 때 경고합니다.

## Leveraging the standard library

코틀린 표준 라이브러리은 매우 다양한 유용한 기능을 제공합니다. 이러한 기능을 통해 바퀴를 재창조할 필요 없이 더 많은 기능 코드를 작성할 수 있습니다. 이 섹션에서는 Kotlin 표준 라이브러리를 활용하여 달성할 수 있는 몇 가지 일반적인 기능 패턴에 대해 살펴보겠습니다.

### Manipulating collections

이 책 전반에 걸쳐, 우리는 우리의 예시 안에서 컬렉션을 사용해 왔습니다. Kotlin 표준 라이브러리는 공통 컬렉션 유형을 매우 쉽게 만들 수 있는 도우미 기능을 제공합니다.

```kt
fun main() {
    val list = listOf("Kotlin", "Java", "Swift")
    val mutableList = mutableListOf("Kotlin", "Java", "Swift")
    val arrayList = arrayListOf("Kotlin", "Java", "Swift")
    val array = arrayOf("Kotlin", "Java", "Swift")
    val map = mapOf("Kotlin" to 1, "Java" to 2, "Swift" to 3)
}
```

Kotlin 표준 라이브러리는 이러한 컬렉션 유형을 만든 후 작업할 수 있는 다양한 기능을 제공합니다. 필터, 매핑, 축소, 찾기 등의 작업을 통해 적은 코드로 복잡한 작업을 수행할 수 있는 강력한 기능 체인을 작성할 수 있습니다.

이 섹션에서는 이러한 기능 중 몇 가지와 코틀린 표준 라이브러리의 활용 방법에 대해 살펴보겠습니다.

### Filtering

먼저 필터() 기능을 살펴보겠습니다. 필터() 함수를 사용하면 람다를 제공할 수 있습니다. 람다는 컬렉션에서 항목을 필터링하는 데 사용되는 부울을 반환합니다.

이 예에서는 필터 기능을 사용하여 문자 "K"로 시작하는 항목만 인쇄합니다.

```kt
fun main() {
    ...

    val list = listOf("Kotlin", "Java", "Swift")
    list.filter { it.startsWith("K") }
        .forEach { println(it) }
}
```

이 예에서는 먼저 항목을 필터링한 다음 하나씩 반복하여 인쇄하는 기능을 함께 결합하는 방법을 살펴보십시오. 이러한 패턴을 통해 보다 복잡한 방식으로 이러한 작업을 연결할 수 있습니다.

## Mapping

다음에는 한 값을 다른 값에 매핑하는 방법에 대해 알아보겠습니다. 문자열 "코틀린"을 해당하는 데이터 유형에 매핑해 보겠습니다.

먼저 프로그래밍 언어를 나타내는 밀봉된 클래스를 만듭니다.

```kt
sealed class ProgrammingLanguage(protected val name: String) {
    object Kotlin : ProgrammingLanguage("Kotlin")
    object Java : ProgrammingLanguage("Java")
    object Swift : ProgrammingLanguage("Swift")

    override fun toString(): String {
        return "$name Programming Language"
    }
}
```

다음으로 맵() 함수에 호출을 추가하여 String 항목을 해당 언어로 매핑할 수 있습니다. map 연산자를 통해 결과 유형을 수신 데이터 유형에서 원하는 송신 데이터 유형으로 변경할 수 있습니다. 이 경우 String에서 ProgrammingLanguage로 매핑합니다.

```kt
val list = listOf("Kotlin", "Java", "Swift", "K")
list.filter { it.startsWith("K") }
    .map {
        when (it) {
            "Kotlin" -> ProgrammingLanguage.Kotlin
            else -> null
        }
    }
    .forEach { println(it) }
```

이 기능을 실행하면 다음과 같은 출력이 나옵니다.

```
Kotlin Programming Language
null
```

문자열 "K"를 ProgrammingLanguage에 매핑하여 null을 반환할 수 없으므로 콘솔에 null이 인쇄된 것을 볼 수 있습니다.

이 문제를 해결하기 위해 다른 함수인 filterNotNull()을 사용하여 null이 아닌 값을 필터링할 수 있습니다.

```kt
val list = listOf("Kotlin", "Java", "Swift", "K")
list.filter { it.startsWith("K") }
    .map {
        when (it) {
            "Kotlin" -> ProgrammingLanguage.Kotlin
            else -> null
        }
    }
    .filterNotNull()
    .forEach { println(it) }
```

이제 이 코드를 실행하면 Null이 아닌 값만 표시됩니다.

```
Kotlin Programming Language
```

map() 함수는 데이터 흐름의 특성을 변경하거나 여러 값을 알려진 값 또는 유형의 집합에 매핑할 수 있기 때문에 매우 강력합니다.