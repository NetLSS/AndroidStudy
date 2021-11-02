# startActivityForResult

startActivityForResult 로 엑티비티 호출 후

```java
setResult(RESULT_OK, returnIntent);
finish(); // 까지
```

해주어야 실행한 액티비티의

```onActivityResult``` 에서 받아서 처리가능.

finish() 하지 않으면 ```onActivityResult``` 타지 않음.
