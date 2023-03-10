package com.furkasm.myigclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.furkasm.myigclone.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth


        val currentUser = auth.currentUser

        if(currentUser != null){
            val intent = Intent(this,RecyclerViewActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    fun clickedSıgnIn(view: View){
        val email  = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (email.equals("") || password.equals("")){
            Toast.makeText(this,"Empty area",Toast.LENGTH_LONG).show()
        }else {
            auth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener {
                   // if (email == binding.editTextEmail.text.toString() && password == binding.editTextPassword.text.toString()){
                   // firebase is doing that actually
                    val intent = Intent(this@MainActivity,RecyclerViewActivity::class.java)
                    startActivity(intent)
                    finish()
                  //  }
                }.addOnFailureListener {
                    Toast.makeText(this@MainActivity,it.localizedMessage,Toast.LENGTH_LONG).show()
                }
        }

    }

    fun clickedSıgnUp(view : View){
      //  val email : String? = binding.editTextEmail.text.toString()
      //  val password : String? = binding.editTextPassword.text.toString()

        val email  = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
      //  if(email.isNullOrEmpty() && password.isNullOrEmpty()){
        if (email.equals("") || password.equals("")){
            Toast.makeText(this,"Empty area",Toast.LENGTH_LONG).show()
        }else{
            auth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener {
                    val intent = Intent(this,add_post_activity ::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
                }

        }


    }

}