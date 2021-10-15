# @JvmStatic
함수와 프로퍼티에 static 하게 접근할 수 있도록 추가적인 메서드 또는 getter / setter 를 생성한다.

Example
아래와 같은 Kotlin object 가 있다.

```kotlin
object Utils {
    fun printAll(list: List<String>) {
        println(list)
    }

    val version = "v0.0.1"
}
```
이 object 의 함수와 프로퍼티를 같은 kotlin 에서는 직접적으로 접근할 수 있다.

```kotlin
fun main() {
    Utils.printAll(listOf("a", "b", "c"))
    println(Utils.version)
}
```
그러나 Java 에서는 해당 object 의 메서드 / 변수에 직접적으로 접근할 수 없다.

```java
public static void main(String[] args) {
    Utils.printAll(Arrays.asList("a", "b", "c")); // compile-error
    System.out.println(Utils.version); // compile-error
 
    Utils.INSTANCE.printAll(Arrays.asList("a", "b", "c")); // ok
    System.out.println(Utils.INSTANCE.getVersion()); // ok
}
```
이와 같은 경우에 @JvmStatic 어노테이션을 붙여주면 Java 에서도 Kotlin 과 동일하게 static 으로 접근할 수 있다.

```kotlin
object Utils {
    @JvmStatic
    fun printAll(list: List<String>) {
        println(list)
    }

    @JvmStatic
    val version = "v0.0.1"
}
```
```java
public static void main(String[] args) {
    Utils.printAll(Arrays.asList("a", "b", "c")); // ok
    System.out.println(Utils.getVersion()); // ok

    Utils.INSTANCE.printAll(Arrays.asList("a", "b", "c")); // ok
    System.out.println(Utils.INSTANCE.getVersion()); // ok
}
```

참고  
https://github.com/occidere/TIL/issues/156
https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-static/
https://jaejong.tistory.com/106#@JvmStatic