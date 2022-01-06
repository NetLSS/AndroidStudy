## 예시

### svg

```xml
<svg width="240" height="1080" viewBox="0 0 240 1080" fill="none" xmlns="http://www.w3.org/2000/svg">
<rect width="240" height="1080" fill="url(#paint0_linear_1772_102667)" fill-opacity="0.4"/>
<defs>
<linearGradient id="paint0_linear_1772_102667" x1="240" y1="278.45" x2="-8.01412e-06" y2="278.45" gradientUnits="userSpaceOnUse">
<stop stop-color="white" stop-opacity="0"/>
<stop offset="0.513889" stop-color="white"/>
<stop offset="1" stop-color="white" stop-opacity="0"/>
</linearGradient>
</defs>
</svg>

```

### vector 

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:aapt="http://schemas.android.com/aapt"
    xmlns:tools="http://schemas.android.com/tools"
    android:width="240dp"
    android:height="1080dp"
    android:viewportWidth="240"
    android:viewportHeight="1080"
    tools:ignore="VectorRaster">
    <path android:pathData="M0 0H240V1080H0V0Z">
        <aapt:attr name="android:fillColor">
            <gradient
                android:endX="-0.00000801412"
                android:endY="278.45"
                android:startX="240"
                android:startY="278.45"
                android:tileMode="clamp">
                <item
                    android:color="#00FFFFFF"
                    android:offset="0" />
                <item
                    android:color="#66FFFFFF"
                    android:offset="0.513889" />
                <item
                    android:color="#00FFFFFF"
                    android:offset="1" />
            </gradient>
        </aapt:attr>
    </path>
</vector>

```


## 예시 2

```xml
<svg width="44" height="28" viewBox="0 0 44 28" fill="none" xmlns="http://www.w3.org/2000/svg">
<path d="M0 0H44V28H0V0Z" fill="#FFD200"/>
<path fill-rule="evenodd" clip-rule="evenodd" d="M20.8893 19.8948C20.8893 20.5053 20.3918 21.0001 19.7788 21.0001C19.1659 21.0001 18.6684 20.5053 18.6684 19.8948C18.6684 19.2843 19.1659 18.7896 19.7788 18.7896C20.3918 18.7896 20.8893 19.2843 20.8893 19.8948Z" stroke="white" stroke-linecap="round" stroke-linejoin="round"/>
<path fill-rule="evenodd" clip-rule="evenodd" d="M27.552 19.8948C27.552 20.5053 27.0545 21.0001 26.4416 21.0001C25.8286 21.0001 25.3311 20.5053 25.3311 19.8948C25.3311 19.2843 25.8286 18.7896 26.4416 18.7896C27.0545 18.7896 27.552 19.2843 27.552 19.8948Z" stroke="white" stroke-linecap="round" stroke-linejoin="round"/>
<path d="M19.0385 10.3158H29.0326C29.4412 10.3158 29.7729 10.6459 29.7729 11.0526L27.9221 16.9474H18.6684L16.4475 7H14.2266" stroke="white" stroke-linecap="round" stroke-linejoin="round"/>
<path d="M0 0V-1H-1V0H0ZM44 0H45V-1H44V0ZM44 28V29H45V28H44ZM0 28H-1V29H0V28ZM0 1H44V-1H0V1ZM43 0V28H45V0H43ZM44 27H0V29H44V27ZM1 28V0H-1V28H1Z" fill="black" fill-opacity="0.04"/>
</svg>
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android" xmlns:aapt="http://schemas.android.com/aapt"
    android:viewportWidth="44"
    android:viewportHeight="28"
    android:width="44dp"
    android:height="28dp">
    <path
        android:pathData="M0 0H44V28H0V0Z"
        android:fillColor="#FFD200" />
    <path
        android:pathData="M20.8893 19.8948C20.8893 20.5053 20.3918 21.0001 19.7788 21.0001C19.1659 21.0001 18.6684 20.5053 18.6684 19.8948C18.6684 19.2843 19.1659 18.7896 19.7788 18.7896C20.3918 18.7896 20.8893 19.2843 20.8893 19.8948Z"
        android:strokeColor="#FFFFFF"
        android:strokeWidth="1"
        android:strokeLineCap="round"
        android:strokeLineJoin="round" />
    <path
        android:pathData="M27.552 19.8948C27.552 20.5053 27.0545 21.0001 26.4416 21.0001C25.8286 21.0001 25.3311 20.5053 25.3311 19.8948C25.3311 19.2843 25.8286 18.7896 26.4416 18.7896C27.0545 18.7896 27.552 19.2843 27.552 19.8948Z"
        android:strokeColor="#FFFFFF"
        android:strokeWidth="1"
        android:strokeLineCap="round"
        android:strokeLineJoin="round" />
    <path
        android:pathData="M19.0385 10.3158H29.0326C29.4412 10.3158 29.7729 10.6459 29.7729 11.0526L27.9221 16.9474H18.6684L16.4475 7H14.2266"
        android:strokeColor="#FFFFFF"
        android:strokeWidth="1"
        android:strokeLineCap="round"
        android:strokeLineJoin="round" />
    <path
        android:pathData="M0 0V-1H-1V0H0ZM44 0H45V-1H44V0ZM44 28V29H45V28H44ZM0 28H-1V29H0V28ZM0 1H44V-1H0V1ZM43 0V28H45V0H43ZM44 27H0V29H44V27ZM1 28V0H-1V28H1Z"
        android:fillColor="#000000"
        android:fillAlpha="0.04" />
</vector>
```