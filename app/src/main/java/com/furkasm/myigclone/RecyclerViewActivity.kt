package com.furkasm.myigclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.furkasm.myigclone.databinding.ActivityRecyclerViewBinding

class RecyclerViewActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRecyclerViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}