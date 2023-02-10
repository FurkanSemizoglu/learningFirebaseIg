package com.furkasm.myigclone

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.furkasm.myigclone.databinding.ActivityAddPostBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.sql.Timestamp
import java.util.UUID

class add_post_activity : AppCompatActivity() {
    private lateinit var binding : ActivityAddPostBinding
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    var selectedPicture : Uri? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseStorage: FirebaseFirestore
    private lateinit var storage : FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        firebaseStorage = Firebase.firestore
        storage = Firebase.storage

        registerLauncher()
    }


    fun clickedUpload(view : View){

        val uuid = UUID.randomUUID()
        val imageName = "$uuid.jpg"

        val storageRef = storage.reference
        val imageRef = storageRef.child("Images").child("$imageName")

        if(selectedPicture != null){
            imageRef.putFile(selectedPicture!!).addOnSuccessListener {

                val uploadPictureReference = storage.reference.child("Images")
                uploadPictureReference.downloadUrl.addOnSuccessListener {
                    val downloadUrl = it.toString()

                    val postMap = hashMapOf<String,Any>()

                    postMap.put("downloadUrl" , downloadUrl)
                    postMap.put("Email" , auth.currentUser!!.email!!)
                    postMap.put("Commnet", binding.editTextCommnet.text.toString())
                    postMap.put("Date", com.google.firebase.Timestamp.now())


                    firebaseStorage.collection("Posts").add(postMap).addOnSuccessListener {

                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(this@add_post_activity,it.localizedMessage,Toast.LENGTH_LONG).show()
                    }



                }


            }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }

    }

    fun clickedImage(view : View){
        // izin istemek

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                Snackbar.make(view,"Permission needed for gallery",Snackbar.LENGTH_INDEFINITE).setAction("Yes"){
                    // request permission
                    permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }.show()
            }else{
                // request permission
                permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else{
            val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }

    }

    private fun registerLauncher(){

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK){
                val intentFromResult = result.data

                if(intentFromResult != null){
                   selectedPicture = intentFromResult.data
                    selectedPicture?.let {
                        binding.imageView.setImageURI(it)
                    }

                }

            }

        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
            if(result){
                val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }else{
                Toast.makeText(this@add_post_activity,"Permission needed",Toast.LENGTH_LONG).show()
            }

        }
    }
}