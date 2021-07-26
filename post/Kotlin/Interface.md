# 인터페이스

- 상속 관계의 설계 보다는 외부 모듈에서 내가 만든 모듈을 사용할 수 있도록 메서드의 이름을 나열해둔 일종의 명세서로 제공
- 코틀린은 인터페이스 내부에 프로퍼티도 정의할 수 있음
- 메서드와 프로퍼티 앞에는 abstract가 생략된 형태임
- 클래스에서 구현할 때는 상속과 달리 생성자 호출이 필요 없으며, 이름만 지정해주면 됨.


- 상속이 아닌 소스 코드에서 직접 구현 할때는 `object` 키워드 사용
- object를 사용하면 익명 객체를 만들게된다.

예시

```kotlin
var kotlinImpl = object: InterfaceKotlin{
    override var variable: String = "init"
    override fun get(){
        // 구현
    }
    override fun set(){
        // 구현
    }
}
```
- InterfaceKotlin 인터페이스를 구현하는 익명 객체
