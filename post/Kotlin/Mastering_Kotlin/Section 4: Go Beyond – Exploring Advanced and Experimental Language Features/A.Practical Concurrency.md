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