/*
    문제의 코드..
    not main safe 
 */

suspend fun doNetworkCall(): Result<String> {
    val result = netWorkCall()
    return if (result == "Success") {
        Result.success(result)
    } else Result.failure(Exception())
}

suspend fun netWorkCall(): String {
    delay(3000L)
    return if (Random.nextBoolean()) "Success" else "Error"
}

/*
    수정 후
    main 스레드에서 사용하더라도 문제 없게 디스패처 명시
 */

 suspend fun doNetworkCall(): Result<String> {
    val result = netWorkCall()
    return if (result == "Success") {
        Result.success(result)
    } else Result.failure(Exception())
}

suspend fun netWorkCall(): String = withContext(Dispatchers.IO) { // 수정
    delay(3000L)
    if (Random.nextBoolean()) "Success" else "Error"
}