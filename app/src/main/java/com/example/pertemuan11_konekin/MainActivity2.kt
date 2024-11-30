package com.example.pertemuan11_konekin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.pertemuan11_konekin.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("EXTRA_EMAIL")
        val firstName = intent.getStringExtra("EXTRA_FIRSTNAME")
        val lastName = intent.getStringExtra("EXTRA_LASTNAME")
        val avatar = intent.getStringExtra("EXTRA_AVATAR")

        binding.email.text = email
        binding.name.text = firstName+" "+lastName
        Glide.with(binding.root.context)
            .load(avatar)
            .into(binding.image)
    }
}