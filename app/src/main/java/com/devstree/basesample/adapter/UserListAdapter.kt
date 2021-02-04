package com.devstree.basesample.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.devstree.basesample.model.User

class UserListAdapter(
    private val dataList: ArrayList<User>,
    private val onItemClick: (position: Int, data: User) -> Unit,
) : RecyclerView.Adapter<ViewHolder>() {

    private var lastSelectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
//        return DataViewHolder(
//            ItemTemplateBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            ), this
//        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        if (viewHolder is DataViewHolder) {
//            viewHolder.bind(dataList[position], position)
//        }
    }

//    fun getSelectedItem(): TemplateModel? {
//        if (lastSelectedPosition == -1) return null
//        if (dataList.isNullOrEmpty()) return null
//        return dataList[lastSelectedPosition]
//    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    companion object {
//        class DataViewHolder(val binding: ItemTemplateBinding, val adapter: UserListAdapter) :
//            ViewHolder(binding.root) {
//            fun bind(data: TemplateModel, position: Int) {
//            }
//        }
    }

}