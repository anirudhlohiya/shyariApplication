package com.anirudh.shyariapplicationbyanirudhlohiya.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anirudh.shyariapplicationbyanirudhlohiya.AllShayariActivity
import com.anirudh.shyariapplicationbyanirudhlohiya.MainActivity
import com.anirudh.shyariapplicationbyanirudhlohiya.Model.CategoryModel
import com.anirudh.shyariapplicationbyanirudhlohiya.databinding.ItemCategoryBinding

class CategoryAdapter(val amainActivity: MainActivity, val list: ArrayList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.CatViewHolder>() {

    val colorsList = arrayListOf<String>("#F3DE2C", "#7F9098", "#7CB518", "#FBB02D", "#FE64A3")


    class CatViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        if (position % 5 == 0) {
            holder.binding.itemText.setBackgroundColor(android.graphics.Color.parseColor(colorsList[0]))
        } else if (position % 5 == 1) {
            holder.binding.itemText.setBackgroundColor(android.graphics.Color.parseColor(colorsList[1]))
        } else if (position % 5 == 2) {
            holder.binding.itemText.setBackgroundColor(android.graphics.Color.parseColor(colorsList[2]))
        } else if (position % 5 == 3) {
            holder.binding.itemText.setBackgroundColor(android.graphics.Color.parseColor(colorsList[3]))
        } else if (position % 5 == 4) {
            holder.binding.itemText.setBackgroundColor(android.graphics.Color.parseColor(colorsList[4]))
        }


        holder.binding.itemText.text = list[position].name.toString()
        holder.binding.root.setOnClickListener {
            val intent = Intent(amainActivity, AllShayariActivity::class.java)
            intent.putExtra("id", list[position].id)
            intent.putExtra("name", list[position].name)
            amainActivity.startActivity(intent)
        }
    }

    override fun getItemCount() = list.size
}