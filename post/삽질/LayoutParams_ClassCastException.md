# LayoutParams_ClassCastException

gradle 의존성 버전 업 후(으로 추정), 기존에는 발생하지 않았던 런타임 FATAL EXCEPTION 빌셍.

원인은 서로 다른 유형의 LayoutParams 을 대입하면서.. 생겼음 

기존 코드 

```kotlin
    view.layoutParams = binding.bottomSheetLayout.layoutParams.apply {
        height = getBottomSheetPersistentDefaultHeight()
    }
```

변경 후 코드
```kotlin
    view.layoutParams.height = getBottomSheetPersistentDefaultHeight()
```

기존에는 이상없이 실행 잘 되던 코드 또한.. 라이브러리 의존성 업데이트 후 에러가 발생할 수 있다는 것을 알게됨.. (그런데 원래 잘못된 코드였기도 함.)

```s
E/AndroidRuntime: FATAL EXCEPTION: main
    Process: xxx, PID: 31930
    java.lang.ClassCastException: androidx.constraintlayout.widget.ConstraintLayout$LayoutParams cannot be cast to android.widget.FrameLayout$LayoutParams
        at android.widget.FrameLayout.onMeasure(FrameLayout.java:198)
        at android.view.View.measure(View.java:27124)
        at android.view.ViewGroup.measureChildWithMargins(ViewGroup.java:7980)
        at androidx.coordinatorlayout.widget.CoordinatorLayout.onMeasureChild(CoordinatorLayout.java:760)
        at androidx.coordinatorlayout.widget.CoordinatorLayout.onMeasure(CoordinatorLayout.java:833)
        at android.view.View.measure(View.java:27124)
        at android.view.ViewGroup.measureChildWithMargins(ViewGroup.java:7980)
        at android.widget.FrameLayout.onMeasure(FrameLayout.java:197)
        at android.view.View.measure(View.java:27124)
        at android.view.ViewGroup.measureChildWithMargins(ViewGroup.java:7980)
        at android.widget.FrameLayout.onMeasure(FrameLayout.java:197)
        at androidx.appcompat.widget.ContentFrameLayout.onMeasure(ContentFrameLayout.java:145)
        at android.view.View.measure(View.java:27124)
        at android.view.ViewGroup.measureChildWithMargins(ViewGroup.java:7980)
        at android.widget.FrameLayout.onMeasure(FrameLayout.java:197)
        at android.view.View.measure(View.java:27124)
        at android.view.ViewGroup.measureChildWithMargins(ViewGroup.java:7980)
        at android.widget.FrameLayout.onMeasure(FrameLayout.java:197)
        at android.view.View.measure(View.java:27124)
        at android.view.ViewGroup.measureChildWithMargins(ViewGroup.java:7980)
        at android.widget.LinearLayout.measureChildBeforeLayout(LinearLayout.java:1552)
        at android.widget.LinearLayout.measureVertical(LinearLayout.java:842)
        at android.widget.LinearLayout.onMeasure(LinearLayout.java:721)
        at android.view.View.measure(View.java:27124)
        at android.view.ViewGroup.measureChildWithMargins(ViewGroup.java:7980)
        at android.widget.FrameLayout.onMeasure(FrameLayout.java:197)
        at com.android.internal.policy.DecorView.onMeasure(DecorView.java:1277)
        at android.view.View.measure(View.java:27124)
        at android.view.ViewRootImpl.performMeasure(ViewRootImpl.java:4510)
        at android.view.ViewRootImpl.measureHierarchy(ViewRootImpl.java:3202)
        at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:3507)
        at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:2893)
        at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:10445)
        at android.view.Choreographer$CallbackRecord.run(Choreographer.java:1108)
        at android.view.Choreographer.doCallbacks(Choreographer.java:866)
        at android.view.Choreographer.doFrame(Choreographer.java:797)
        at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:1092)
        at android.os.Handler.handleCallback(Handler.java:938)
        at android.os.Handler.dispatchMessage(Handler.java:99)
        at android.os.Looper.loopOnce(Looper.java:226)
        at android.os.Looper.loop(Looper.java:313)
        at android.app.ActivityThread.main(ActivityThread.java:8663)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:567)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1135)
E/SchedPolicy: Failed to find cgroup for tid 31930

```