package ui


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.data.database.EntityGithub
import com.data.database.GithubRepository
import com.data.response.DetailResponse
import com.example.mygithubmission.R
import com.example.mygithubmission.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ui.DetailUserViewModel.Companion.KEY_AVATAR
import ui.DetailUserViewModel.Companion.KEY_USERNAME

class DetailUserActivity : AppCompatActivity() {
    private lateinit var Binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private lateinit var githubrepository: GithubRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(Binding.root)

        githubrepository = GithubRepository(application)
        viewModel = getViewModel(this@DetailUserActivity)


        val username = intent.getStringExtra(EXTRA_USERNAME)
        val username1 = intent.getStringExtra(KEY_USERNAME).toString()
        val avatarUrl = intent.getStringExtra(KEY_AVATAR).toString()
        val favoriteLivedata = viewModel.FavoriteByName(username1)


        val mbundle = Bundle()
        mbundle.putString(EXTRA_USERNAME, username)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailUserViewModel::class.java]


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
        sectionsPagerAdapter.username = username.toString()
        val viewPager: ViewPager2 = Binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()
        supportActionBar?.elevation = 0f

        favoriteLivedata.observe(this) { userfavorite ->
            if (userfavorite != null) {
                Binding.fabAdd.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                Binding.fabAdd.setImageResource(R.drawable.baseline_favorite_border_24)
            }
        }
        Binding.fabAdd.setOnClickListener {
            if (favoriteLivedata.value == null) {
                val userfavorite = EntityGithub(username = username1, avatarUrl = avatarUrl)
                viewModel.InsertFavUser(userfavorite)
                Toast.makeText(this, "Add $username1 to list favorite", Toast.LENGTH_SHORT).show()
            } else {
                favoriteLivedata.value?.let { favoriteuser ->
                    viewModel.DeleteFavUser(favoriteuser)
                    Toast.makeText(this, "Delete $username1 to list favorite", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    companion object {

        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = arrayOf(
            R.string.Follower,
            R.string.Following

        )
    }


    private fun setuserdata(user: DetailResponse) {
        Binding.apply {
            tvnameUser.text = user.login
            tvname.text = user.name
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

    private fun getViewModel(activity: AppCompatActivity): DetailUserViewModel {
        githubrepository = GithubRepository(application)
        val factory = ViewModelFactory.getInstance(githubrepository)
        return ViewModelProvider(activity, factory)[DetailUserViewModel::class.java]
    }

}