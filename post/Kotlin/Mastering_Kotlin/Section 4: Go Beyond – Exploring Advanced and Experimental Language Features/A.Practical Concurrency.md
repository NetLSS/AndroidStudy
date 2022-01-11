# Practical Concurrency

## Understanding async patterns

비동기 프로그래밍을 위해 우리가 마음대로 사용할 수 있는 다양한 프리미티브, 라이브러리, 패턴이 있습니다. 프로젝트에 대한 비동기 코드를 모델링하는 올바른 방법을 선택하는 것은 특정 요구 사항에 따라 다르지만 사용 가능한 옵션을 이해하면 선택 프로세스를 개선할 수 있습니다. 이 섹션에서는 Kotlin과 Java 모두에서 비동기 코드를 작성하기 위한 여러 기본 요소와 패턴을 살펴보겠습니다.

## Threading primitives

Kotlin과 Java는 비동기 코드 작성을 위한 여러 하위 수준 기본 요소를 공유합니다. 이러한 기본 요소는 간단한 일회성 작업에 사용하거나 더 복잡한 시스템과 결합하여 프로젝트에서 비동기 코드를 관리할 수 있습니다. 이 절에서는 다음을 포함한 몇 가지 원시 요소를 검토한다.

- Thread/ThreadPool
- ExecutorService
- CompletableFuture

### Thread

스레드 클래스는 프로그래밍 언어 내에서 실행 스레드의 프로그래밍 개념에 직접적으로 매핑됩니다. 여러 스레드를 동시에 실행할 수 있습니다. 따라서 새로운 스레드를 생성하여 비차단 방식으로 코드를 실행할 수 있습니다. 간단한 예를 살펴보자.

시작하기 전에 threadExample()이라는 함수를 정의하겠습니다.

```kotlin
fun threadExample() {
    println("Running threadExample()")
    
    println("Finished threadExample()")
}

```

해당 함수 내에서, 우리는 함수의 시작과 종료 시점에 대한 로그 문을 추가했습니다. 이는 최종 스레드 사용의 비차단(non-blocking) 특성을 설명하기 위한 것입니다.

```kotlin
fun threadExample() {
    println("Running threadExample()")
    val thread = Thread {
        Thread.sleep(1000)
        println("Thread finished after 1 second")
    }
    thread.start()
    println("Finished threadExample()")
}
```

Thread 초기화의 람다 내에서 Thread.sleep(1000)을 호출했으며, 이로 인해 Thread의 완료가 1초 지연됩니다. 스레드가 선언되면 start()를 호출하여 해당 스레드의 실행을 시작합니다.

주 함수에서 threadExample()을 호출하면 출력을 검사할 수 있습니다.

```kotlin
fun main() {
    println("Hello Chapter 10")
    threadExample()
}
```

앞의 코드를 실행하면 다음과 같은 출력을 받게 됩니다.

```sh
Hello Chapter 10
Running threadExample()
Finished threadExample()
Thread finished after 1 second
```

이 출력에서 새로운 Thread의 로그 문이 콘솔에 마지막으로 인쇄된 것임을 알 수 있습니다. 이는 초기 스레드가 실행을 계속하는 동안 스레드가 지연되었기 때문입니다.

이 예는 매우 간단하지만 비동기 프로그래밍의 핵심 과제 중 하나를 보여준다. 우리의 threadExample() 함수로, 우리의 스레드는 Finished threadExample() 로그 메시지가 실행되기 전에 생성되고 시작되었다. 그러나 스레드의 실행을 완료하기 전에 println("완료된 스레드Example()")이 실행됩니다. 새로운 스레드가 실행될 때까지 원래 스레드가 완료되지 않도록 하려면 쓰기, 테스트 및 유지 관리가 어려워지는 추가적인 차단 메커니즘이 필요합니다.

스레드는 현재 실행 스레드를 차단하지 않고 독립적인 코드 조각을 실행하는 수단을 제공합니다. 그러나 스레드 사용과 관련된 문제가 있습니다. 새 스레드를 만드는 것은 프로그램을 실행할 때 상대적으로 비용이 많이 드는 작업이며 스레드가 너무 많으면 시스템 리소스가 손상될 수 있습니다. 우리는 다음 섹션에서이 문제에 대한 하나의 가능한 해결책을 탐구 할 것입니다.

### ExecutorService

모든 태스크에 대해 새 스레드를 작성하는 것은 비동기 작업을 관리하는 데 적합하지 않습니다. 스레드 및 관련 리소스의 수가 빠르게 감당할 수 없게 될 수 있습니다. 이를 개선하기 위한 한 가지 방법은 ExecutorService를 사용하는 것입니다. ExecutorService를 사용하면 비동기적으로 실행할 작업을 제출할 수 있으며, ExecutorService가 작업이 실행되는 스레드를 관리하는 방법을 결정할 수 있습니다.

ExecutorService에 대해 생성하고 재사용할 특정 스레드 수를 정의할 수 있습니다. 따라서 사용되는 리소스의 양이 제한되고 코드의 잠재적인 성능 영향을 쉽게 이해할 수 있습니다.

고정된 스레드 수를 사용하여 ExecutorService를 만드는 것은 다음 스 니펫과 같이 매우 간단하게 수행할 수 있습니다.

```kotlin
val executor = Executors.newFixedThreadPool(3)
```

`newFixedThreadPool()` 패토리 메서드로 `ExecutorService` 를 생성하도록 레버레지가 가능함 (특정 스레드 개수만큼 할당하도록)

실행자를 사용할 수 있게 되면 해당 실행자를 사용하여 백그라운드에서 실행할 작업을 제출할 수 있습니다.

```kotlin
fun threadPoolExample() {
    executor.submit {
        Thread.sleep(500) // simulate network request
        println("Runnable 1")
    }
    executor.submit {
        Thread.sleep(200) // simulate db access
        println("Runnable 2")
    }
    executor.submit {
        Thread.sleep(300) // simulate network request
        println("Runnable 3")
    }
}
```

이 예제에서 실행자는 사용 가능한 스레드 풀을 사용하여 제출된 실행 가능한 작업을 예약하고 작업을 실행합니다. 이 코드를 실행하면 다음과 같은 출력을 볼 수 있습니다.

```sh
Runnable 2
Runnable 3
Runnable 1
```

이 경우, 우리가 실행자에게 제출하는 첫 번째 Runnable은 가장 긴 기간 동안 스레드를 지연시키기 때문에 마지막으로 완료됩니다. 이것은 네트워크 요청과 같은 장기간 실행되는 작업을 시뮬레이션합니다.

크기 3의 스레드 풀로 이 Executor를 만들었기 때문에 주어진 시간에 3개 이하의 스레드들이 생성되고 실행되어 비동기 코드를 실행하는 데 사용할 수 있는 시스템 자원의 양을 제한할 수 있습니다.

# Advanced threading

스레드 및 ExecutorService와 같은 하위 수준의 비동기 구조 외에도 코틀린과 자바 모두에서 non-blocking 코드를 작성하기 위한 몇 가지 더 진보된 메커니즘이 있다. 이 섹션에서는 다음과 같은 몇 가지 옵션을 살펴보겠습니다.

- CompletableFuture
- RxJava
- Coroutines

## CompletableFuture

CompletableFuture는 Java 8에서 새롭게 추가되었습니다. 그것은 명백하게 완성될 수 있는 미래를 제공한다. 그런 다음 다양한 콜백을 사용하여 미래의 완성에 대응하여 다양한 방법으로 데이터를 변환하고 응답할 수 있다.

다음은 String 값을 내보내기 전에 5초 동안 기다리는 CompletableFuture의 기본 예입니다.

```kotlin
CompletableFuture.supplyAsync {
    Thread.sleep(5000)
    "Future is done"
}
```

다음과 같은 차단 방식으로 이 값을 검색할 수 있습니다.

```kotlin
val returnedValue = future.get()
```

비동기 방식으로 코드를 실행하고 값이 반환되면 응답할 가능성이 더 높습니다. 다음 스니펫은 thenAccept() 방법을 사용하여 이 작업을 수행할 수 있는 한 가지 방법을 보여 줍니다.

```kotlin
CompletableFuture.supplyAsync {
    Thread.sleep(5000)
    "Future is done"
}.thenAccept {
    println(it)
}
```

thenAccept() 방법은 이러한 미래의 완료에 대응할 수 있는 터미널 방법입니다. 완료에 응답하기 위해 thenAccept()를 사용하면 최종 값에 액세스할 수 있습니다. 그러나 최종 값이 필요하지 않다면, 완료에 대한 응답으로 임의 코드를 실행하기 위해 thenRun()을 사용할 수 있습니다.

```kotlin
CompletableFuture.supplyAsync {
    Thread.sleep(5000)
    "Future is done"
}.thenRun {
    println("The future completed")
}
```

Completable 사용 시미래에는 다양한 연산자를 사용하여 데이터 스트림을 변환할 수도 있습니다. 다음 코드 조각에서는 thenApply()를 사용하여 반환된 문자열을 새 문자열 값에 매핑할 수 있습니다.

```kotlin
CompletableFuture.supplyAsync {
    Thread.sleep(5000)
    "Future is done"
}.thenApply {
    "The supplied value was: $it" // 반환된 문자열 값을 새 문자열 값에 매핑 
}.thenAccept {
    println(it)
}
```

완료 가능Future는 Java 8 이상을 대상으로 하는 경우 비동기 작업을 구성, 변환 및 응답하는 데 유용한 API입니다.

### RxJava

RxJava는 관찰 가능한 스트림을 사용하여 이벤트 기반 비동기 코드를 작성하기 위한 라이브러리이다. RxJava는 많은 기능을 제공하며 매우 강력합니다. 그러나 간단한 비동기 코드에도 사용할 수 있다.

RxJava를 사용하여 배경 스레드에서 일부 작업을 실행하는 간단한 예는 다음 코드 스 니펫에서 볼 수 있습니다.

```kotlin
Single.fromCallable {
    Thread.sleep(5000)
    "Single is done"
}
.observeOn(Schedulers.io())
.subscribe { value -> println(value) }
```

RxJava를 사용하면 이벤트가 어떻게 배출되는지, 작업을 실행하기 위해 어떤 일정을 사용해야 하는지 정의한 다음 해당 작업의 완료를 관리할 수 있습니다. Completable과 상당히 유사합니다.우리가 이전에 살펴본 미래 API입니다.

RxJava를 사용하면 데이터 스트림 변환과 같은 고급 작업도 수행할 수 있습니다.

```kotlin
Single.fromCallable {
    Thread.sleep(5000)
    "Single is done"
}
.map { RequestResult(it) }
.observeOn(Schedulers.io())
.subscribe { value -> println(value) }
```

이 예에서는 map() 메소드를 적용하여 내보낸 String 값을 RequestResult 클래스의 인스턴스로 변환했습니다. 이러한 유형의 비동기 데이터 스트림 변환은 RxJava가 대중적인 라이브러리인 이유 중 하나이다.

RxJava는 반드시 이벤트 중심일 필요는 없는 간단한 비동기 코드를 작성하는데 사용될 수 있지만, 이러한 사용 사례에 대해서는 과잉 살상일 수 있다는 점에 유의해야 한다. RxJava는 수많은 사용 사례를 처리할 수 있는 다양한 연산자를 제공하는 대규모 종속성입니다. 간단한 경우 Completable과 같은 경우미래 또는 코루틴이 더 가벼운 솔루션일 수 있습니다.

### Coroutines

코루틴은 콜백을 사용하지 않고 순차적인 방식으로 비동기 코드를 작성할 수 있게 해준다. 코틀린어는 코틀린 언어의 일부이며, 현재는 비차단 코드를 작성하는 기본 코틀린 인동 방식이다.

다음 스니펫은 코루틴을 사용하는 기본적인 예를 보여줍니다.

```kotlin
fun main() {
    GlobalScope.launch {
        delay(500)
        println("Coroutines")
    }
    println("Hello")
    Thread.sleep(1000)
}
```

이 예에서는 GlobalScope.launch { }을(를) 호출하여 새 코루틴을 시작합니다. 이 코드는 시작 스레드를 차단하지 않고 백그라운드에서 실행됩니다. 이 코드는 다음과 같은 출력을 생성합니다.

```sh
Hello
Coroutines
```

런칭된 코루틴이 차단되지 않기 때문에 코루틴에 코루틴 라인이 인쇄되기 전에 Hello가 인쇄됩니다.

이제 이러한 비동기 패턴을 이해했으므로 다음 섹션에서는 코루틴이 무엇이며 어떻게 사용할 수 있는지 살펴봄으로써 코루틴에 대한 이해를 기반으로 구축할 수 있는 방법을 알아보겠습니다.

## The foundations of coroutines

코루틴의 실제 적용에 대해 자세히 살펴보기 전에, 개념으로서 코루틴의 기초에 대해 더 많이 이해하는 것이 유익할 수 있다. 이 섹션에서는 코루틴의 역사를 살펴보고 코틀린에 코루틴이 추가된 방법과 이유를 살펴봅니다. 또한 코루틴이 어떤 문제를 해결하는지, 그리고 왜 코루틴이 유용한 도구인지에 대해 알아보겠습니다.

### What are coroutines?

코루틴은 1958년 멜빈 콘웨이가 조립 프로그램의 구성을 설명하기 위해 이 용어를 사용한 개념으로 거슬러 올라간다. 코루틴은 동시 프로그래밍을 달성하는 수단이다. 이들은 스레드와 유사하지만 일반적으로 더 가볍다. 여러 코루틴이 동일한 리소스를 사용하여 동일한 스레드에서 실제로 실행, 일시 중단, 재개될 수 있기 때문이다.

실과 달리 코루틴은 병렬로 작동하지 않는다. 그들은 협력적인 멀티태스킹을 사용한다. 이것은 운영 체제가 컨텍스트 전환없이 코 루틴에서 코 루틴으로 이동할 수 있음을 의미합니다. 각 코 루틴은 어느 시점에서 실행을 일시 중지합니다.이 시점에서 OS는 다른 루틴의 실행을 재개할 수 있습니다. 이렇게하면 멀티 스레드 환경에서 진정한 병렬 처리와 관련된 복잡성이 제거됩니다. 일반적으로한 번에 하나의 코 루틴만 실행되기 때문에 뮤텍스와 같은 메커니즘이 필요하지 않습니다.

coroutines의 개념은 다양한 프로그래밍 언어에 적용되었으며 오늘날 많은 언어가 다음을 포함하여 coroutines를 지원합니다.

```
Python
Ruby
C++20
Go
JavaScript
```

코루틴은 비동기 작동에 의존할 수 있는 생성기/스트림(generators/streams) 및 통신 순차 프로세스를 포함한 사용 사례에 매우 적합합니다. 오늘날의 문제들은 점점 더 많은 비동기 코드를 요구하기 때문에 코틀린에 코루틴을 도입했다.


### Coroutines with Kotlin

Kotlin에 대한 Coroutine 지원은 1.1 릴리스의 실험 기능으로 처음 제공되었습니다. Kotlin의 1.3 릴리스는 코 루틴에 대한 안정적인 지원을 가져 왔습니다.

코틀린의 코루틴은 `kotlinx.coroutines`이라는 언어로 직접 구축되지 않고 제1자 라이브러리로 구현된다. 이것은 핵심 언어의 표면적을 더 작게 유지하고 코루틴을 더 빨리 반복할 수 있게 해준다. Kotlin coroutines 작업을 시작하려면 프로젝트 종속성으로 Kotlinx-coroutines-core 모듈을 가져와야 합니다.


## Coroutines in practice

이제 코루틴의 기본에 대해 알아보았으므로 코루틴을 활용하여 비동기 작업을 처리하는 방법에 대해 알아보겠습니다.

이 섹션에서는 Kotlin에서 코 루틴의 실제 사용법을 살펴 보겠습니다. 사용 가능한 프리미티브를 검토하고 몇 가지 예제를 통해 작업 할 것입니다. 마지막으로, 우리는 적절한 오류 처리 및 코 루틴 범위 제어와 같은 몇 가지 고급 개념을 검토합니다.

### Coroutine primitives

코틀린 코루틴을 다룰 때 이해해야 할 몇 가지 기본 개념이 있습니다. 이러한 점을 강조하기 위해 이전 절의 예를 살펴볼 수 있습니다.

```kotlin
fun main() {
    GlobalScope.launch {
        delay(500)
        println("Coroutines")
    }

    println("Hello")
    Thread.sleep(1000)
}
```

앞의 스니펫에는 세 가지 주요 개념의 예가 있습니다.

```
Coroutine scopes
Coroutine builders
Suspending functions
```

### Coroutine scopes

우리의 간단한 작업 예제에서 GlobalScope는 시작된 코 루틴의 코 루틴 범위입니다.

```kotlin
fun main() {
    GlobalScope.launch {
        delay(500)
        println("Coroutines")
    }

    println("Hello")
    Thread.sleep(1000)
}
```

코루틴 범위는 코루틴의 수명을 정의합니다. GlobalScope 내의 코루틴은 애플리케이션 수명 동안 존재할 수 있습니다. 이것은 올바르게 정리되지 않은 경우 응용 프로그램의 수명 동안 존재하는 스레드와 유사합니다. 코루틴 범위의 개념은 우리가 우리의 코루틴을 제한하여 우리의 애플리케이션 내에서 맥락과 라이프 사이클을 지정할 수 있도록 한다. 이 개념은 구조화된 동시성(structured concurrency.)이라고 알려져 있다.

애플리케이션의 기존 수명 주기와 연결되는 고유한 코루틴 범위를 만들 수 있습니다. 예를 들어, Android 앱에서 작업하는 경우 활동 라이프 사이클에 바인딩된 코루틴 범위를 원할 수 있습니다. 우리는 다음과 같은 것을 작성함으로써 그것을 달성할 수 있다:

```kotlin
class ViewModel {
    private val mainScope = MainScope()
    
    init {
        mainScope.launch { 
            // fetch data
        }
    }

    fun destroy() {
        mainScope.cancel()
    }
}
```

이 코드를 사용하여 mainScope라는 이름의 고유한 범위를 만들었으며, 이 범위는 ViewModel 클래스의 수명에 연결됩니다. 파괴() 방식으로 범위를 정리하는 한 실행 중인 코루틴은 제대로 정리되고 리소스는 방출됩니다.

CoroutineScope를 활용함으로써, 우리는 우리의 코루틴이 올바르게 청소되고 가능한 한 효율적으로 작동하도록 도울 수 있다.

### Coroutine builders

코루틴 빌더는 다른 특성을 가진 코루틴을 시작하는 데 사용할 수 있는 CoroutineScope 클래스의 확장 기능입니다. 작업 예제를 다시 살펴보면 launch() 빌더의 예제를 볼 수 있습니다.

```kotlin
fun main() {
    GlobalScope.launch {
        delay(500)
        println("Coroutines")
    }
    println("Hello")
    Thread.sleep(1000)
}
```

launch()를 호출하여 현재 스레드를 차단하지 않고 새 코루틴을 시작할 수 있습니다. 다음을 포함하여 다양한 다른 코루틴 빌더를 사용할 수 있습니다.

- async : 코루틴을 생성하고 지연된 미래 결과를 반환합니다.
- produce : ReceiveChannel을 통해 값 스트림을 반환하는 코루틴 생성
- broadcast : BroadcastChannel을 통해 값 스트림을 반환하는 코루틴 생성
- runBlocking : 새 코루틴을 실행하고 반환될 때까지 현재 스레드를 차단합니다.

launch()와 다른 빌더를 사용함으로써, 우리는 우리의 코루틴의 결과와 어떻게 상호작용하기를 원하는지 제어할 수 있다.