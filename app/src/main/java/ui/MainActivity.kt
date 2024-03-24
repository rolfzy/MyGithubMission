package ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.data.response.GithubResponse
import com.data.response.ItemsItem
import com.example.mygithubmission.databinding.ActivityMainBinding
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider

import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    companion object {
        private const val TAG = "MainViewModel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        adapter = UserAdapter()


        mainViewModel.listUser.observe(this) { listuser ->
            setUserData(listuser)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    val query = searchView.text.toString()
                    searchView.hide()
                    mainViewModel.finduser(query)
                    false
                }

        }
    }

    private fun setUserData(useGithub: List<ItemsItem>) {
        adapter.submitList(useGithub)
        binding.rvUser.adapter = adapter //
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}


