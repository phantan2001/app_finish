package com.example.imagedetection

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_quet_ma_vach.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mavach = findViewById<Button>(R.id.MaVach)
        val dich = findViewById<Button>(R.id.Dich)
        val nhandien = findViewById<Button>(R.id.HinhAnh)
        var mediaPlayer = MediaPlayer.create(applicationContext, R.raw.christmass)
        mediaPlayer.start()

        clickEvents()


    }

    private  fun clickEvents() {

        //quet ma vach
        MaVach.setOnClickListener {
            val intent: Intent = Intent (this, quet_ma_vach::class.java)
            startActivity(intent)

        }

        // dich van ban
        Dich.setOnClickListener {
            val intent2: Intent = Intent (this, dich_van_ban::class.java)
            startActivity(intent2)
        }

        // nhan dien do vat

        HinhAnh.setOnClickListener {
            val intent3: Intent = Intent ( this, nhan_dien_do_vat::class.java)
            startActivity(intent3)
        }


    }







}
