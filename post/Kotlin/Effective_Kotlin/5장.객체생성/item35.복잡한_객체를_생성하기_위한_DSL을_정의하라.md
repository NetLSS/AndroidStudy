# item35.복잡한_객체를_생성하기_위한_DSL을_정의하라

## 함수 타입을 만드는 기본 적인 방법

- 람다 표현식
  - 익명 함수를 짧게 표현 가능하게 함
- 익명 함수
  - 이름을 갖고 있지 않음
- 함수 래퍼런스

```kotlin
fun plus(a: Int, b: Int) = a + b
```

```kotlin
val plus1: (Int, Int)->Int = { a, b -> a + b}
val plus2: (Int, Int)->Int = fun(a, b) = a + b
val plus3: (Int, Int)->Int = ::plus

val plus4 = { a: Int, b: Int -> a + b}
val plus5 = fun(a: Int, b: Int) = a + b
```

### 확장 함수는 ?

```kotlin
fun Int.myPlus(other: Int) = this + other
```

```kotlin
val myPlus = fun Int.(other: Int) = this + other
```

#### 리시버를 가진 함수 타입

```kotlin
val myPlus: Int.(Int)->Int =
    fun Int.(other: Int) = this + other
```

```kotlin
val myPlus: Int.(Int)->Int = { this + it }
```

사용 (호출) 방법

- 일반적인 객체처럼 invoke 메서드를 사용
- 확장 함수가 아닌 함수처럼 사용
- 일반적인 확장 함수처럼 사용

```kotlin
myPlus.invoke(1, 2)
myPlus(1, 2)
1.myPlus(2)
```

## 실전 html DSL 만들어보기

```kotlin
fun createTable(): TableDsl = table {
    tr { // this.tr 의 this 가 생략된 것으로 보면 될듯. 
        for (i in 1..2) {
            td {
                +"This is column $i"
            }
        }
    }
}
```

```kotlin
fun table(init: TrBuilder.() -> Unit): TableBuilder {
    // ...
}

class TableBuilder {
    fun tr(init: TrBuilder.() -> Unit) { /* ... */ }
}

class TrBuilder {
    fun td(init: TdBuilder.() -> Unit) { /*...*/ }
}

class TdBuilder {
    var text = ""

    operator fun String.unaryPlus() {
        text += this
    }
}
```

### 동작하게 하기

- 빌더를 리턴하게 하기

```kotlin
fun table(init: TableBuilder.() -> Unit): TableBuilder {
    val tableBuilder = TableBuilder()
    init.invoke(tableBuilder)
    return tableBuilder
}
```

이에 apply 를 적용 하면 더 간단해짐.

```kotlin
fun table(init: TableBuilder.() -> Unit) = TableBuilder().apply(init)
```

```kotlin
class TableBuilder {
    var trs = listOf<TrBuilder>()

    fun td(init: TrBuilder.()->Unit) {
        trs = trs + TrBuilder().apply(init)
    }
}

class TrBuilder {
    var tds = listOf<TdBuilder>()

    fun td(init: TdBuilder.() -> Unit) {
        tds = tds + TdBuilder().apply(init)
    }
}
```