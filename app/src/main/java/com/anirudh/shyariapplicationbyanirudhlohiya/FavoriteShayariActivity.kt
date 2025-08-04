package com.anirudh.shyariapplicationbyanirudhlohiya

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anirudh.shyariapplicationbyanirudhlohiya.Adapter.FavoriteShayariAdapter
import com.anirudh.shyariapplicationbyanirudhlohiya.Model.ShayariModel
import com.anirudh.shyariapplicationbyanirudhlohiya.databinding.ActivityAllShayariBinding

class FavoriteShayariActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllShayariBinding
    private lateinit var viewModel: FavoriteShayariViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllShayariBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(FavoriteShayariViewModel::class.java)

        binding.rcvAllShayari.layoutManager = LinearLayoutManager(this)
        val adapter = FavoriteShayariAdapter(this, ArrayList())
        binding.rcvAllShayari.adapter = adapter

        viewModel.fetchFavoriteShayaris(this)

        viewModel.favoriteShayaris.observe(this, Observer { shayaris ->
            val favoriteShayaris = shayaris.filter { it.isFavorite == true }
            adapter.updateData(favoriteShayaris)
        })

        binding.btnBack.setOnClickListener {
            // send the user to the main activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}