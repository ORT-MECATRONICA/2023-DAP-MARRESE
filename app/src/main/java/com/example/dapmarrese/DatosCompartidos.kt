import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class sharedData : ViewModel() {
    val dataID = MutableLiveData<String>()
}