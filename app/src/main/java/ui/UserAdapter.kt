package ui


import android.content.Intent
import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.data.response.ItemsItem

import com.example.mygithubmission.databinding.UserLayoutBinding


class UserAdapter : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    private val listUser = ArrayList<ItemsItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listUser[position])

    }

    fun setList(newList: List<ItemsItem>) {
        val diffResult = DiffUtil.calculateDiff(CallBackMain(listUser, newList))
        listUser.clear()
        listUser.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun getItemCount(): Int = listUser.size


    class MyViewHolder(private val binding: UserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            binding.tvItem.text = "${user.login}"
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.imguser)

            itemView.setOnClickListener { view ->
                val toDetail = Intent(view.context, DetailUserActivity::class.java)
                toDetail.putExtra(DetailUserActivity.EXTRA_USERNAME, user.login)
                view.context.startActivity(toDetail)
            }
        }
    }


}
