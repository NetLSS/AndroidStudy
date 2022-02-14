# item27.변화로부터_코트를_보호하려면_추상화를_사용하라

## 인터페이스 활용

```kotlin
interface MessageDisplay {
    fun show(
        message: String,
        duration: MessageLength = LONG
    )
}

class ToastDisplay(val context: Context): MessageDisplay {

    override fun show(
        message: String,
        duration: MessageLength
    ) {
        val toastDuration = when(duration) {
            SHORT -> Length.SHORT
            LONG -> Length.LONG
        }
        Toast.makeText(context, message, toastDuration)
            .show()
    }
}

enum class MessageLength { SHORT, LONG }
```

## ID 만들기 (nextId)

``` kotlin
var nextId: Int = 0

// use
val newId = nextId++
```

```kotlin
private var nextId: Int = 0
fun getNextId(): Int = nextId

// use
val newId = getNextId()
```

```kotlin
data class Id(private val id: Int)

private var nextId: Int = 0
fun getNextId(): Id = Id(nextId++)
```

## 추상화 방법들

- 상수로 추출
- 동작을 함수로 래핑
- 함수를 클래스로 래핑
- 인터페이스 뒤에 클리스를 숨기기
- 보편적인 객체(universal object)를 특수한 객체(specialstic object)로 래핑한다.

추상화는 자유를 주지만, 코드를 이해하고 수정하기 어렵게 만든다.

## 어떻게 균형을 맞춘담

모든 추상화는 자유를 주지만, 코드가 어떻게 돌아가는 것인지 이해하기 어렵게 만든다.

최상의 답은 언제나 그 사이 어딘가에 있다...

예술의 영역..

- 많은 개발자가 참여하는 프로젝트는 이후에 객체 생성과 사용 방법을 변경하기 어렵다.

추상화는 단순하게 중복성을 제거해서 코드를 구성하기 위한 것이 아니다.
