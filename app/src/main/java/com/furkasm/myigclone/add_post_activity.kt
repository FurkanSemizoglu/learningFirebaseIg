package com.furkasm.myigclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.furkasm.myigclone.databinding.ActivityAddPostBinding

class add_post_activity : AppCompatActivity() {
    private lateinit var binding : ActivityAddPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}