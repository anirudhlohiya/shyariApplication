package com.anirudh.shyariapplicationbyanirudhlohiya.Adapter

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.anirudh.shyariapplicationbyanirudhlohiya.FavoriteShayariActivity
import com.anirudh.shyariapplicationbyanirudhlohiya.Model.ShayariModel
import com.anirudh.shyariapplicationbyanirudhlohiya.R
import com.anirudh.shyariapplicationbyanirudhlohiya.databinding.ItemShayariBinding

class FavoriteShayariAdapter(
    val favoriteShayariActivity: FavoriteShayariActivity, val shayariList: ArrayList<ShayariModel>
) : RecyclerView.Adapter<FavoriteShayariAdapter.ShayariViewHolder>() {

    class ShayariViewHolder(val binding: ItemShayariBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShayariViewHolder {
        return ShayariViewHolder(
            ItemShayariBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = shayariList.size

    override fun onBindViewHolder(holder: ShayariViewHolder, position: Int) {
        val shayari = shayariList[position]
        shayari.isFavorite = loadFavoriteStatus(favoriteShayariActivity, shayari)

        val favoriteDrawable =
            if (shayari.isFavorite!!) R.drawable.shyari_favorite else R.drawable.shyari_not_favorite
        holder.binding.favoriteIcon.setBackgroundResource(favoriteDrawable)

        if (position % 5 == 0) {
            holder.binding.mainBc.setBackgroundResource(R.drawable.gradient1)
        } else if (position % 5 == 1) {
            holder.binding.mainBc.setBackgroundResource(R.drawable.gradient2)
        } else if (position % 5 == 2) {
            holder.binding.mainBc.setBackgroundResource(R.drawable.gradient3)
        } else if (position % 5 == 3) {
            holder.binding.mainBc.setBackgroundResource(R.drawable.gradient4)
        } else if (position % 5 == 4) {
            holder.binding.mainBc.setBackgroundResource(R.drawable.gradient5)
        }

        holder.binding.itemShayari.text = shayariList[position].data.toString()

        holder.binding.btnCopy.setOnClickListener {
            val shayariText = shayariList[position].data.toString()
            // Copy the shayari text to the clipboard
            val clipboardManager =
                favoriteShayariActivity.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText(shayariList[position].data.toString(), shayariText)
            clipboardManager.setPrimaryClip(clipData)

            Toast.makeText(favoriteShayariActivity, "Copied", Toast.LENGTH_SHORT).show()

        }

        holder.binding.btnShare.setOnClickListener {
            val shayariText = shayariList[position].data.toString()

            // Create an intent to share the shayari text
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shayariText)

            // Start the share intent
            favoriteShayariActivity.startActivity(shareIntent)

        }

        holder.binding.btnWhatsapp.setOnClickListener {

            val shayariText = shayariList[position].data.toString()

            // Create an intent to share via WhatsApp
            val whatsappIntent = Intent(Intent.ACTION_SEND)
            whatsappIntent.type = "text/plain"
            whatsappIntent.setPackage("com.whatsapp") // Specify WhatsApp package name

            // Add the shayari text to the intent
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, shayariText)

            try {
                // Try to start the WhatsApp intent
                favoriteShayariActivity.startActivity(whatsappIntent)
            } catch (e: ActivityNotFoundException) {
                // If WhatsApp is not installed, handle the exception here
                Toast.makeText(
                    favoriteShayariActivity,
                    "WhatsApp not installed",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        holder.binding.btnFavorite.setOnClickListener {
            val shayari = shayariList[holder.adapterPosition]
            shayari.isFavorite = !shayari.isFavorite!!
            saveFavoriteStatus(favoriteShayariActivity, shayari)
            val favoriteDrawable =
                if (shayari.isFavorite!!) R.drawable.shyari_favorite else R.drawable.shyari_not_favorite
            holder.binding.favoriteIcon.setBackgroundResource(favoriteDrawable)
            val toastMessage =
                if (shayari.isFavorite!!) "Added to favorites" else "Removed from favorites"
            Toast.makeText(favoriteShayariActivity, toastMessage, Toast.LENGTH_SHORT).show()

            // If the shayari is removed from favorites, update the list and refresh the RecyclerView
            if (!shayari.isFavorite!!) {
                shayariList.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)
            }
        }

    }

    fun saveFavoriteStatus(context: Context, shayari: ShayariModel) {
        val sharedPreferences =
            context.getSharedPreferences("FavoriteShayari", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(shayari.data, shayari.isFavorite!!)
        editor.apply()
    }

    fun loadFavoriteStatus(context: Context, shayari: ShayariModel): Boolean {
        val sharedPreferences =
            context.getSharedPreferences("FavoriteShayari", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(shayari.data, false)
    }

    fun updateData(newShayariList: List<ShayariModel>) {
        shayariList.clear()
        shayariList.addAll(newShayariList)
        notifyDataSetChanged()
    }

}