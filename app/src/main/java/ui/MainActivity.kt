package ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import com.data.response.ItemsItem
import com.example.mygithubmission.databinding.ActivityMainBinding

import android.view.View

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.lifecycle.ViewModelProvider
import com.example.mygithubmission.R


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
        binding.topAppBar.setOnMenuItemClickListener{menuitem ->
            when(menuitem.itemId){
                R.id.action_favorite ->{
                    val intent =Intent(this@MainActivity,FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }else -> false
            }
        }

    }





    private fun setUserData(useGithub: List<ItemsItem>) {
        adapter.setList(useGithub)
        binding.rvUser.adapter = adapter //
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}


