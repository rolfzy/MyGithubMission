package ui

import android.util.Log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.data.database.EntityGithub
import com.data.database.GithubRepository

import com.data.response.DetailResponse
import com.data.response.ItemsItem

import data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailUserViewModel(private val githubRepository: GithubRepository) : ViewModel() {
    private val _UserDetail = MutableLiveData<DetailResponse>()
    val UserDetail: LiveData<DetailResponse> = _UserDetail

    private val _Follower = MutableLiveData<List<ItemsItem>>()
    val follower:LiveData<List<ItemsItem>> = _Follower

    private val _Following = MutableLiveData<List<ItemsItem>>()
    val following:LiveData<List<ItemsItem>> = _Following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userFavorite = MutableLiveData<Boolean>()
    val UserFavorite: LiveData<Boolean> = _userFavorite




    fun detailUser(username: String ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _UserDetail.value =response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun FollowerData(username: String=""){
        _isLoading.value = true
        val client = ApiConfig.getApiService().Getdatafollower(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _Follower.value =response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun FollowingData(username: String="") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().Getdatafollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _Following.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })

    }
    fun FavoriteByName(username: String): LiveData<EntityGithub> {
        return githubRepository.getFavoriteByUsername(username)
    }

    fun InsertFavUser(entityGithub: EntityGithub) {
        viewModelScope.launch {
            githubRepository.insertFavorite(entityGithub)
        }

    }

    fun DeleteFavUser(entityGithub: EntityGithub) {
        viewModelScope.launch {
            githubRepository.deleteFavorite(entityGithub)
        }

    }
    companion object {
        private const val TAG = "DetailUserViewModel"
        const val KEY_USER = "key_user"
        const val KEY_USERNAME = " "
        const val KEY_AVATAR = "key_avatar"
    }


}