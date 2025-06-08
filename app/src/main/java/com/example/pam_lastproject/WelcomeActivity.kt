package com.example.pam_lastproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pam_lastproject.databinding.ActivityWelcomeBinding
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // Cek jika user sudah login, langsung ke MainActivity
        // Baris ini penting agar user yang sudah login tidak melihat halaman welcome lagi
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return // Hentikan eksekusi onCreate lebih lanjut
        }


        // Navigasi saat tombol "Login" ditekan
        binding.btnToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Navigasi saat teks "Register disini" ditekan
        binding.tvToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}