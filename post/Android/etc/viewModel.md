# Android viewModel

[ref1](https://jeongmin.github.io/2020/05/04/android/architecture-components/viewmodel/)

# MVVM ViewModel VS AAC ViewModel

## MVVM ViewModel

- MVVM 에서 ViewModel 의 역할은 View 와 Model 사이에서 데이터를 관리해주고 바인딩 해주는 역할

## AAC ViewModel

- AAC 에서 ViewModel 은 화면 회전 같은 환경에서 데이터를 보관하고 라이프 사이클을 알고 있어서 Activity 나 Fragment 의 Destroy 시 onClear() 함수를 통한 데이터 해제의 역할.
- 안드로이드 ViewModel 의 목적은 데이터를 간리하고 바인딩하라는 목적으로 만든게 아님.

### .

- MVVM 에서의 뷰모델은 뷰와 모델사이의 데이터를 관리해주고 바인딩해주는 역할이지만
- **안드로이드 ViewModel 의 경우 액티비티 등의 라이프 사이클을 알고 있고 이가 Destroy 되는 것을 인식하기 때문에 데이터를 해제하거나 유지하는 역할을 한다!**
...

하지만 AAC ViewModel 을 아키텍처 MVVM ViewModel 처럼 사용하지 못하나? 그건 아니다. 거기에 더해 화면 회전에 대한 데이터 유지 까지 있으니 더 좋을 수 있다.

하지만 AAC ViewModel 은 Activity 내에서 1개만 생성 가능하다.

AAC ViewModel 은 Activity 안에서의 싱글톤 개념인데 MVVM 패턴에서 뷰와 뷰모델은 1:N 관계를 가지기 때매 Activity 내에 여러 Fragment 를 가질 시 여러 Fragment 에 viewModel 을 사용하긴 어렵겠죠?. 이러한 부분의 차이점이 있어서 AAC Viewmodel 과 MVVM 뷰모델이 서로 다르다고 볼 수 있음.
- 

- 안드로이드 AAC ViewModel 은 View 데이터를 가지는 'Model' 이라고 생각
- AAC의 ViewModel 은 데이터를 가지는 Model 이라고 생각


## 한 액티비티에서 여러 프래그먼트에서 ViewModel 을 사용하는 경우

- 액티비티에서 초기화 후, 여러 프레그먼트에서 해당 뷰모델을 사용
- 프로바이더에서 requireActivity 사용 해서 LifecycleOwner 에서 Fragment 가아닌 이미 생성된 ViewModel 을 불러올 수 있다.
- [ref](https://thdev.tech/androiddev/2020/07/13/Android-Fragment-ViewModel-Example/)
---

- viewmodel 생명주기 : ViewModel Scope -> onCleared() 까지 

---

## MVC

- model-vidw-control
- 액티비티, 프래그먼트에 코드가 다 포함

## MVP

- model-view-presenter
- presenter : view = 1 : 1
- UI 코드와 비즈니스 로직을 분리 함. 유닛 테스트가 가능.
- View와 Model 의 의존성이 없음.
- 단점 : View 와 Presenter 간의 의존성이 높고 1:1 관계를 유지해야함.
- View 가 늘어날 때마다 Presenter 도 같이 늘어남.

## MVVM

- MVP 에서는 Presenter가 View에 어떤 일을 요청했는지 명백히 확인이 가능했음.
  - 하지만 View 와 Presnter 가 강하게 결합되어 있다는 문제.
- MVVM 에서는 데이터바인딩 및 LiveData 또는 RxJava 와 같은 Observable 한 타입을 사용해서 Presnter 와 View 사이에 강하게 연결되었던 점을 끊는데 집중한다.
- Presenter 대신 ViewModel 이라는 구성 요소를 사용한다.
- viewModel 은 view에 표현될 데이터를 Observable 타입으로 관리.
- View 들이 ViewModel 에 데이터를 구독 요청하여 화면에 나타내게 됨.
- 그러므로 ViewModel 과 View는 1:N 관계가 있다. (View '들'이 viewModel 데이터를 구독)
- **ViewMOdel 이 View에 대한 의존성을 갖지 않고 느슨하게 연결되도록 DataBinding 라이브러리가 필수적으로 사용됨**
- ViewModel 은 Model 에 데이터를 요청.
- Model 로 부터 받은 데이터를 가공하여 Observable 한 타입 형태로 ViewModel 에 저장.