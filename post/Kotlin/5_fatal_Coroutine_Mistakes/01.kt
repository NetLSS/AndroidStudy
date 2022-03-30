
import kotlinx.coroutines.delay

/*
    실수가 존재하는 코드 (하기)
 */

suspend fun getUserFirstName(userIds: List<String>): List<String> {
    val firstNames = mutableListOf<String>()
    for (id in userIds) {
        firstNames.add(getFirstName(id)) // 예상 해보기: getFirstName 이 suspend 이므로.. firstNames 에 추가되는 순서가 달라지려나? or 반대 오히려 기다림.. 1개 끝날때까지 
    }
    return firstNames
}

suspend fun getFirstName(userId: String): String {
    delay(1000L)
    return "First name"
}

/*
    수정 후
 */


suspend fun getUserFirstName(userIds: List<String>): List<String> {
    val firstNames = mutableListOf<Deferred<String>>()
    coroutineScope {
        for (id in userIds) {
            val firstName = async { // async 로 모든 아이디의 firstName 을 동시에 가져옴
                getFirstName(id)
            }
            firstNames.add(firstName)
        }
    }
    
    return firstNames.awaitAll() // 동시에 다 가져와도 순서는 변하지 않음 (deferred 를 추가한거라서)
}

suspend fun getFirstName(userId: String): String {
    delay(1000L)
    return "First name"
}