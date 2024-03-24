package ui


import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.data.response.DetailResponse
import com.example.mygithubmission.R
import com.example.mygithubmission.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var Binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = arrayOf(
            R.string.Follower,
            R.string.Following

        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(Binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val mbundle = Bundle()
        mbundle.putString(EXTRA_USERNAME, username)
        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory())[DetailUserViewModel::class.java]

        if (username != null) {
            viewModel.detailUser(username)
        }
        viewModel.UserDetail.observe(this) {
            if (it != null) {
                setuserdata(it)
            }
        }
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }



        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username =username.toString()
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()
        supportActionBar?.elevation = 0f
    }


    private fun setuserdata(user: DetailResponse) {
        Binding.apply {
            tvnameUser.text = user.login
            tvfollowerUser.text = "${user.followers} Follower"
            tvfollowingUser.text = "${user.following} Following"
            Glide.with(this@DetailUserActivity)
                .load(user.avatarUrl)
                .into(imgdetailuser)
        }
    }


    private fun showLoading(isLoading: Boolean) {
        Binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}