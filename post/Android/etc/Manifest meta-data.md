# Android Manifest Meta-data

apk 디컴파일 시 string 값 등 유출이 가능함..


```xml
<meta-data name="string" resource="resource specification" value="string">


```


<meta-data> 태그는 <application>, <activity>, <service> 등에 포함하여 사용할 수 있습니다.


```xml
<application icon="@drawable/icon" label="@string/app_name">    
    <meta-data name="aKey" value="aValue"> ... </meta-data>

```

코드에서 불러와 사용하기

```java
ApplicationInfo appInfo = getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA); 
Bundle aBundle = appInfo.metaData; 
String aValue = aBundle.getString("aKey");
```

String 하드 코딩 방지하기 (string resource 사용)

```xml
<application icon="@drawable/icon" label="@string/app_name">
    <meta-data name="aKey" value="@string/string"> ... </meta-data>

```
---

[ref](https://superakira.tistory.com/entry/Android-android-meta-data-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0)