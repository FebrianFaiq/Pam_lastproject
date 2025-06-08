package com.example.pam_lastproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pam_lastproject.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // Navigasi ke halaman Login
        binding.tvToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Proses Register
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmailRegister.text.toString()
            val pass = binding.etPasswordRegister.text.toString()
            val confirmPass = binding.etConfirmPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    if (pass.length >= 6) { // Firebase mensyaratkan password minimal 6 karakter
                        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                                // Langsung pindah ke halaman Login setelah registrasi sukses
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Password harus memiliki minimal 6 karakter", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Password tidak cocok!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Semua field tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}