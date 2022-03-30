import kotlinx.coroutines.*
import kotlin.random.Random

/*
    문제의 코드...!
 */

suspend fun doSomething() {
    val job = CoroutineScope(Dispatchers.Default).launch {
        var random = Random.nextInt(100_000)
        while (random != 50000) {
            random = Random.nextInt(100_000)
        }
    }
    delay(500L) // 추측: 여기서 job도 정지 되려나..?
    job.cancel() 
}

/*
    해결 방법 1
 */

 suspend fun doSomething() {
    val job = CoroutineScope(Dispatchers.Default).launch {
        var random = Random.nextInt(100_000)
        while (random != 50000 && isActive) { // 활성 여부 확인 
            random = Random.nextInt(100_000)
        }
    }
    delay(500L)
    job.cancel()
}

/*
    해결 방법 2
 */

 suspend fun doSomething() {
    val job = CoroutineScope(Dispatchers.Default).launch {
        var random = Random.nextInt(100_000)
        while (random != 50000) {
            random = Random.nextInt(100_000)
            ensureActive() // 추가 
        }
    }
    delay(500L)
    job.cancel()
}