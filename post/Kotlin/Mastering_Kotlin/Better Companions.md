# Better companion

`companion object` 는 바깥 클래스(enclsosing class) 에서 메서드나 프로퍼티를 접근할 수 있게하는 효율적인 방법입니다.

약간 Java 의 Static 느낌이라고 할 수도 있습니다.

하지만, `companion object` 를 자바에서 참조할 떄는 살짝 복잡할 수 있습니다.

그럼 어떻게 효율적으로 사용할 수 있는지 알아봅시다.

## 어떻게 `companion object` 가 동작할까

코틀린 클래스에 컴패니언 객체가 선언되면 컴파일러는 컴파일 때 명명된 내부 클래스를 생성하게 됩니다.

아래 코드는 빈 컴패니언 객체를 사용했습니다.

```kotlin
class Widget {
    companion object { }
}
```

앞의 코드에 의해 Java 코드가 컴파일러에 의해 생성되면 아래와 같습니다.

```java
public final class Widget {
   public static final Widget.Companion Companion = new Widget.Companion((DefaultConstructorMarker)null);

   ...
   public static final class Companion {
      private Companion() {
      }

      ...
   }
}
```

즉 바깥 클래스의 인스턴스가 인스턴스화 될 때 마다 내부 클래스에 있는 인스턴스도 인스턴스화 되게됩니다.

이는 java 에서 `companion` 인스턴스를 통해서 해당 클래스의 속성과 메서드에 접근할 수 있게됩니다.

```java
// Main.java
public class Main {
    public static void main(String[] args) {
        Widget.Companion.goo();
    }
}
```

컴패니언 오브젝트를 사용하면 자바의 static 과 매우 유사한 구문을 사용할 수 있게 되는듯 합니다.

그러나 이러한 companion object 는 실제 사용을 위해 인스턴스화 해야하는 실제 개체입니다.

아래에서 컴패니언 오브젝트를 좀 더 잘 사용할 수 있는 방법을 알아보도록 하겠습니다.

## 컴패니언 오브젝트는 도대체 언제 사용하면 좋을까?

코틀린으로 개발을 하다보면 상수를 정의할 때 주로 컴패니언 오브젝트를 사용해오곤 했는데. 그럼 언제 컴패니언 오브젝트를 사용하는 것이 좋을까?

다음 세 가지를 예로 들 수 있을 것 같다.

- private 생성자와 함께 팩토리 메서드를 작성할 때
- static constants 와 메서드 제공
- 최상위 프로퍼티 및 메서드 범위 지정(Scoping top-level properties and functions)

Companion object 는 바깥 클래스의 private 속성, 메서드 및 생성자에 접근할 수 있습니다.

즉 이는 클래스에 private 생성자가 있을 때도 해당 Companion object 에서 이를 사용해서 클래스를 인스턴스화 할 수 있다는 것을 의미합니다.

이를 통해 팩토리 메서드를 작성해서 클래스 생성 방법을 제어 가능합니다. (실제로 팩토리 메서드 경우 이런식으로 정의하는 것을 많이 보셨을 겁니다.)

코틀린은 일반적으로 최상위 메서드와 프로퍼티를 선호하지만, 컴패니언 객체를 통해 이러한 프로퍼티와 메서드를 둘러싸는(scoping) 클래스로 지정할 수 있습니다.

이러면 네임스페이스 오염을 방지할 수 있습니다.

## Companion object 의 naming 🔥

컴패니언 개체에 이름을 수정할 수 있다!

Java 에서 Kotlin 의 컴패니언 오브젝트를 참조하는 경우에 Companion 이라는 이름으로 참조하는데 이 이름을 바꿔 사용이 가능하다는 것.

```kotlin
class Widget {
    companion object Factory {
        fun create() {}
    }
}
```

위와 같이 컴패니언 오브젝트 네이밍을 통해 코드 가시성을 더욱 높여줄 수 있습니다.

위와 같이 이름을 지정하는 경우 생성된 클래스에 새로 지정된 이름이 지정되게 되고, 해당 이름을 사용해 참조할 수 있습니다.

```java
// Main.java
public class Main {
    public static void main(String[] args) {
        Widget.Factory.create();
    }
}
```

이를 통해서 컴패니언 오브젝트에 보다 의미있는 이름을 제공할 수 있고, 위에서 처럼 팩토리 메서드 예시와 같은 보다 좋은(?) 이름 사용이 가능하죠.

그리고 자바에서 사용될 때 Companion 이라는 코틀린 표식이 없어지므로 보다 자연스레 사용이 가능해집니다.

## 마치며

`companion object` 에 네이밍이 가능하다니! 그렇게 큰 기능은 아니지만 재미있는 기능을 알게된 것 같아 기분이 좋네요. 앞으로 유의미한 네이밍을 통해 코드 가시성을 높일 수 있을 것 같네요. 여러분도 적용해보세요! 👍

## Referencs

![](https://images-na.ssl-images-amazon.com/images/I/51H+ZH1ZqXL._SX404_BO1,204,203,200_.jpg)

> mastering kotlin
