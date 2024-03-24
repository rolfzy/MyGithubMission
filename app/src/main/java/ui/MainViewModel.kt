package ui


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.data.response.DetailResponse
import com.data.response.GithubResponse
import com.data.response.ItemsItem
import data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
//

    companion object{
        private const val TAG = "MainViewModel"
    }



    init {
        finduser("rofik")
    }
  fun finduser(query:String) {
    _isLoading.value = true
    val client = ApiConfig.getApiService().searchUser(query)
    client.enqueue(object : Callback<GithubResponse> {
        override fun onResponse(
            call: Call<GithubResponse>,
            response: Response<GithubResponse>
        ) {
            _isLoading.value = false
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    _listUser.value =(responseBody.items)
                }

            } else {
                Log.e(TAG, "onFailure: ${response.message()}")
            }
        }

        override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
            _isLoading.value =false
            Log.e(TAG, "onFailure: ${t.message}")
        }
    })
}




}