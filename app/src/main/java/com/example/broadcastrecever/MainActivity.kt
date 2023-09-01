package com.example.broadcastrecever

import android.Manifest.permission.READ_PHONE_STATE
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var networkChangeReceiver: NetworkChangeReceiver
    private lateinit var dndReceiver: DNDReceiver
    private lateinit var ivIntentImage: ImageView
    private lateinit var intentVideoView: VideoView
    private lateinit var shareButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
        ivIntentImage = findViewById(R.id.ivIntent)
        intentVideoView = findViewById(R.id.vVIntent)
        shareButton = findViewById(R.id.buttonShare)

        val temp = setIntentImage(ivIntentImage)
        setIntentVideoAndPlay(intentVideoView,this)

        networkChangeReceiver = NetworkChangeReceiver()
        IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).also {
            registerReceiver(networkChangeReceiver, it)
        }

        dndReceiver = DNDReceiver()
        IntentFilter(NotificationManager.ACTION_INTERRUPTION_FILTER_CHANGED).also {
            registerReceiver(dndReceiver,it)
        }

        shareButton.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.type = "image/*"
            val uri: Uri = Uri.parse(temp)
            sendIntent.putExtra(Intent.EXTRA_STREAM, uri)
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Shared by Abhishek") // extra msg while sharing
            val chooserIntent = Intent.createChooser(sendIntent, "Share Image")
            startActivity(chooserIntent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkChangeReceiver)
        unregisterReceiver(dndReceiver)
    }

    fun setIntentImage(imageView: ImageView): String{
        if(intent.action.equals(Intent.ACTION_SEND) && intent.type?.startsWith("image/") == true){
            val imageUri = intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
            Log.i("hello",imageUri.toString())
            imageView.setImageURI(imageUri)
            return imageUri.toString()
        }
        return "xxx"
    }

    fun setIntentVideoAndPlay(view: VideoView, context: Context){
        if(intent.action.equals(Intent.ACTION_SEND) && intent.type?.startsWith("video/") == true){
            val videoUri = intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
            Log.i("hello",videoUri.toString())
            view.setVideoURI(videoUri)
        }
        val mediaController = MediaController(context)
        mediaController.setAnchorView(view)
        view.setMediaController(mediaController)
        view.requestFocus()
    }

}