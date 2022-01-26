# item 01 가변성을 제한하라

- var 보다는 val
- mutable 프로퍼티 보다는 immutable 프로퍼티
- mutable 객체와 클래스 보다는 immutable 객체와 클래스
- 변경 필요 대상 필요시 immutable 데이터 클래스
- 컬렉션 사용시 mutable 보다는 읽기 전용 컬렉션
- 변이지점 최소화
- mutable 객체는 외부에 노출하지 말기

하지만 예외도 있음. (효율성)

