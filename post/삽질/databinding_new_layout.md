
## 오류 코드

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">


    </androidx.constraintlayout.widget.ConstraintLayout>

</layout>
```

## 에러 문구

```
The layout "smile_delivery_best_fragment" in layout has no declaration in the base layout folder; this can lead to crashes when the resource is queried in a configuration that does not match this qualifier
```

## 해결 방법

invalid 또는 안스 닫기 후 재오픈 

[참고 링크](https://stackoverflow.com/questions/52547657/the-layout-layout-in-layout-has-no-declaration-in-the-base-layout-folder-erro)