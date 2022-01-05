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