/*
    문제의 코드 
 */
class MainActivity : ComponentActivity() {
    
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val button = findViewById<Button>(androidx.core.R.id.dialog_button)
        
        button.setOnClickListener { 
            lifecycleScope.launch { // 기기 회전 등 시 취소됨..
                viewModel.postValueToApi()
            }
        }
    }
}

class MainViewModel : ViewModel() {

    suspend fun postValueToApi() {
        delay(10000L)
    }
}

/*
    해결 코드
 */


class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val button = findViewById<Button>(androidx.core.R.id.dialog_button)

        button.setOnClickListener {
            viewModel.postValueToApi()
        }
    }
}

class MainViewModel : ViewModel() {

    fun postValueToApi() {
        viewModelScope.launch { // 기기 회전 등. 시 취소 안됨. 뷰모델 스코프
            delay(10000L)
        }
    }
}