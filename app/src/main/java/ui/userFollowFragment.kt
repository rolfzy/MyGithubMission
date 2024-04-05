package ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubmission.databinding.FragmentUserFollowerBinding


private const val ARG_POSITION = "position"
private const val ARG_USERNAME = "username"

class userFollowFragment : Fragment() {

    private  var _binding: FragmentUserFollowerBinding? =null
    private val binding get() = _binding!!
    private lateinit var adapter: UserAdapter
    private lateinit var detailVm:DetailUserViewModel

    companion object {

        const val TAG ="DetailUserViewModel"
        const val ARG_USERNAME = ""
        const val ARG_POSITION = "position"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentUserFollowerBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var username = arguments?.getString(ARG_USERNAME)
        var position = 0

        setAdapter()

        detailVm = ViewModelProvider(requireActivity(),ViewModelProvider.NewInstanceFactory())[DetailUserViewModel::class.java]

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username= it.getString(ARG_USERNAME)

        }
        if (position == 1){
            showLoading(true)
            username?.let { detailVm.FollowerData(it) }


        } else {
            showLoading(true)
            username?.let { detailVm.FollowingData(it) }
        }

        detailVm.follower.observe(viewLifecycleOwner){
            if (position == 1){
                adapter.setList(it)
                Log.d(TAG, "Data follower berhasil diterima: $it")

            }
            showLoading(false)
        }
        detailVm.following.observe(viewLifecycleOwner){
            if (position == 2){
                adapter.setList(it)
                Log.d(TAG, "Data following berhasil diterima: $it")

            }
            showLoading(false)
        }
    }
private fun setAdapter(){
    adapter = UserAdapter()
    binding.rvFollow.adapter =adapter
    binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
}
    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}

