/*
    문제의 코드 
 */

suspend fun riskyTask() {
    try {
        delay(3000L)
        println("The answer is ${10 / 0}")
    } catch (e: Exception) { // try 블록에서 취소 예외가 발생한 경우.. 취소 예외를 부모 코루틴에 전달할 수 없다. (다 잡아버리니까)
        println("Opps, that didn't work")
    }
}

/*
    해결 방법 1
 */

 suspend fun riskyTask() {
    try {
        delay(3000L)
        println("The answer is ${10 / 0}")
    } catch (e: HttpException) { // Exception 말고 특정 예외만 잡기 
        println("Opps, that didn't work")
    }
}

/*
    해결 방법 2
    CancellationException 일 경우 다시 throw 하기
 */

 suspend fun riskyTask() {
    try {
        delay(3000L)
        println("The answer is ${10 / 0}")
    } catch (e: Exception) {
        if (e is CancellationException) {
            throw e
        }
        println("Opps, that didn't work")
    }
}