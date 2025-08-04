package com.anirudh.shyariapplicationbyanirudhlohiya

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.anirudh.shyariapplicationbyanirudhlohiya.Adapter.CategoryAdapter
import com.anirudh.shyariapplicationbyanirudhlohiya.Model.CategoryModel
import com.anirudh.shyariapplicationbyanirudhlohiya.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var db: FirebaseFirestore
    lateinit var shayariList: List<CategoryModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()

        db.collection("Shayari").addSnapshotListener { value, error ->
            shayariList = arrayListOf<CategoryModel>()  // Change this line
            val data = value?.toObjects(CategoryModel::class.java)
            (shayariList as ArrayList<CategoryModel>).addAll(data!!)  // And this line

            binding.rcvCategory.layoutManager = LinearLayoutManager(this)
            binding.rcvCategory.adapter = CategoryAdapter(
                this,
                shayariList as ArrayList<CategoryModel>
            )  // And this line
        }

//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
//                return@OnCompleteListener
//            }
//
//            // Get new FCM registration token
//            val token = task.result
//
//            // Log and toast
//            val msg = getString(R.string.msg_token_fmt, token)
//            val TAG = "MainActivity"
//            Log.d(TAG, msg)
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//        })



        binding.btnMenu.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                binding.drawerLayout.closeDrawer(Gravity.LEFT)
            } else {
                binding.drawerLayout.openDrawer(Gravity.LEFT)
            }
        }

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.share -> {
                    fun shareApp(context: Context) {
                        val appPackageName = context.packageName
                        val sendIntent = Intent().apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            action = Intent.ACTION_SEND
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "Check out this amazing shyari App at: https://play.google.com/store/apps/details?id=$appPackageName"
                            )
                            type = "text/plain"
                        }
                        context.startActivity(sendIntent)
                    }
                    shareApp(this)
                    true
                }

                R.id.more -> {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$packageName")
                            )
                        )
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                            )
                        )
                    }
                    true
                }

                R.id.rate -> {
                    val uri = Uri.parse("market://details?id=" + this.packageName)
                    val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
                    try {
                        startActivity(myAppLinkToMarket)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(this, " Sorry, Not able to open!", Toast.LENGTH_SHORT).show()
                    }
                    true
                }

                R.id.favorite -> {
                    val intent = Intent(this, FavoriteShayariActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                else -> false
            }
        }

    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
        } else {
            super.onBackPressed()
        }
    }

}