# Practical Concurrency

## Understanding async patterns

비동기 프로그래밍을 위해 우리가 마음대로 사용할 수 있는 다양한 프리미티브, 라이브러리, 패턴이 있습니다. 프로젝트에 대한 비동기 코드를 모델링하는 올바른 방법을 선택하는 것은 특정 요구 사항에 따라 다르지만 사용 가능한 옵션을 이해하면 선택 프로세스를 개선할 수 있습니다. 이 섹션에서는 Kotlin과 Java 모두에서 비동기 코드를 작성하기 위한 여러 기본 요소와 패턴을 살펴보겠습니다.

## Threading primitives

Kotlin과 Java는 비동기 코드 작성을 위한 여러 하위 수준 기본 요소를 공유합니다. 이러한 기본 요소는 간단한 일회성 작업에 사용하거나 더 복잡한 시스템과 결합하여 프로젝트에서 비동기 코드를 관리할 수 있습니다. 이 절에서는 다음을 포함한 몇 가지 원시 요소를 검토한다.

- Thread/ThreadPool
- ExecutorService
- CompletableFuture