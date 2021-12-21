package com.example.imagedetection

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.graphics.Bitmap
import android.os.Bundle
import com.example.imagedetection.R
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.imagedetection.dich_van_ban
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import android.content.Intent
import android.app.Activity
import android.provider.MediaStore
import com.google.android.gms.vision.text.TextRecognizer
import android.widget.Toast
import android.util.SparseArray
import com.google.android.gms.vision.text.TextBlock
import android.content.ClipData
import android.content.ClipboardManager
import android.view.View
import android.widget.Button
import com.google.android.gms.vision.Frame
import kotlinx.android.synthetic.main.activity_dich_van_ban.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.lang.StringBuilder

class dich_van_ban : AppCompatActivity() {
    var btn_capture: Button? = null
    var btn_copy: Button? = null
    var text: TextView? = null
    var bitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dich_van_ban)


        btn_capture = findViewById(R.id.button_capture)
        btn_copy = findViewById(R.id.button_copy)
        text = findViewById(R.id.text_data)
        if (ContextCompat.checkSelfPermission(
                this@dich_van_ban,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@dich_van_ban, arrayOf(
                    Manifest.permission.CAMERA
                ), REQUEST_CAMERA_CODE
            )
        }
        button_capture.setOnClickListener(View.OnClickListener {
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this@dich_van_ban)
        })
        button_copy.setOnClickListener(View.OnClickListener {
            val scanned_Text = text_data.getText().toString()
            copyToClipBoard(scanned_Text)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, resultUri)
                    getTextFromImage(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun getTextFromImage(bitmap: Bitmap?) {
        val recognizer = TextRecognizer.Builder(this).build()
        if (!recognizer.isOperational) {
            Toast.makeText(this@dich_van_ban, "Error Occurred!!!", Toast.LENGTH_SHORT).show()
        } else {
            val frame = Frame.Builder().setBitmap(bitmap).build()
            val textBlockSparseArray = recognizer.detect(frame)
            val stringBuilder = StringBuilder()
            for (i in 0 until textBlockSparseArray.size()) {
                val textBlock = textBlockSparseArray.valueAt(i)
                stringBuilder.append(textBlock.value)
                stringBuilder.append("\n")
            }
            text_data!!.text = stringBuilder.toString()
            button_capture!!.text = "Retake"
            button_copy!!.visibility = View.VISIBLE
        }
    }

    private fun copyToClipBoard(text: String) {
        val clipBoard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Data", text)
        clipBoard.setPrimaryClip(clip)
        Toast.makeText(this@dich_van_ban, "Copied to Clipboard!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUEST_CAMERA_CODE = 100
    }
}