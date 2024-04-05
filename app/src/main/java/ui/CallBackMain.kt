package ui

import androidx.recyclerview.widget.DiffUtil
import com.data.response.ItemsItem

class CallBackMain (
    private val listold: List<ItemsItem>,
    private val listnew: List<ItemsItem>
):DiffUtil.Callback() {
    override fun getOldListSize(): Int = listold.size
    override fun getNewListSize(): Int = listnew.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return listold[oldItemPosition] == listnew[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return listold[oldItemPosition] == listnew[newItemPosition]
    }
}
