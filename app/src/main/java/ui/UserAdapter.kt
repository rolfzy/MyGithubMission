package ui



import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter //
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.response.DetailResponse
import com.data.response.ItemsItem

import com.example.mygithubmission.databinding.UserLayoutBinding



class UserAdapter: ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

private val userlist =ArrayList<ItemsItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
    fun setList(newList : List<ItemsItem>){
        val diffResult = DiffUtil.calculateDiff(CallBackMain(userlist, newList))
        userlist.clear()
        userlist.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


    class MyViewHolder(private val binding: UserLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem){
            binding.tvItem.text = "${user.login}"
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.imguser)

            itemView.setOnClickListener{view ->
                val toDetail = Intent(view.context,DetailUserActivity::class.java)
                toDetail.putExtra(DetailUserActivity.EXTRA_USERNAME,user.login)
                view.context.startActivity(toDetail)
            }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }

    }

}
