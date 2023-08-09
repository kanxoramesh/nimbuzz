package com.example.nimbuzz


import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.os.BuildCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nimbuzz.databinding.ActivityMainBinding
import com.google.modernstorage.photopicker.PhotoPicker

@BuildCompat.PrereleaseSdkCheck
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var picker = PhotoPicker();
    private var triangularList = arrayListOf<Uri>()
    private lateinit var imageAdapter: ImageAdapter

    val photoPickerListener = registerForActivityResult(picker) { uris ->

        if (uris.size < 2) {
            Toast.makeText(this, "Please select two photos", Toast.LENGTH_LONG).show()
            return@registerForActivityResult
        }

        triangularList.clear()
        var size =
            if (binding.lsize.text.toString().isEmpty()) 50 else binding.lsize.text.toString()
                .toInt()
        triangularList.addAll(generateTriangularList(size, uris))
        imageAdapter.notifyDataSetChanged()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(PERMISSION),
            1
        );

        binding.remote.setOnClickListener {
            checkPermission()
        }
        imageAdapter = ImageAdapter(this, triangularList)
        binding.imageRecycler.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.imageRecycler.adapter = imageAdapter
    }


    private fun checkPermission() {
        if (checkSelfPermissionCompat(PERMISSION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(PERMISSION),
                1
            );
        } else {
            photoPicker()
        }
    }

    private fun photoPicker() {
        photoPickerListener.launch(PhotoPicker.Args(PhotoPicker.Type.IMAGES_ONLY, 2))
    }

    private val PERMISSION: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_IMAGES
    } else {
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    }

    private fun AppCompatActivity.checkSelfPermissionCompat(permission: String) =
        ActivityCompat.checkSelfPermission(this, permission)


    private fun generateTriangularList(size: Int, uris: List<Uri>): List<Uri> {
        val triangularList = mutableListOf<Uri>()
        for (i in 1..size) {
            if (isTriangularNumber(i))
                triangularList.add(uris[0])
            else
                triangularList.add((uris[1]))
        }
        return triangularList
    }


    /**
     * Check the triangular number
     */
    private fun isTriangularNumber(n: Int): Boolean {
        var sum = 0
        var i = 1
        while (sum < n) {
            sum += i
            i++
        }
        return sum == n
    }

}

