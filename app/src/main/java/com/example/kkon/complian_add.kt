package com.example.kkon

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_complian_add.*
import java.io.File

class complian_add : AppCompatActivity() {
    val SELECT_IMAGE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complian_add)
        val i =intent
        compliant_add_btn.setOnClickListener {
            val s= Intent()
            var cnttt=i.getIntExtra("cntt",-1)
            var civil_emaill=i.getStringExtra("user_emaill")
            s.putExtra("pass1",compliant_add_spot.selectedItem.toString())
            s.putExtra("pass2",compliant_add_content.text.toString())
            s.putExtra("pass3",cnttt)
            setResult(Activity.RESULT_OK,s)
            if (compliant_add_spot.selectedItem.toString().length == 0 || compliant_add_content.text.toString().length == 0) {
                Toast.makeText(applicationContext, "장소와 내용이 모두 적혀있는지 확인하세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                finish()//여기서 피니시하면 호출한 메인으로 넘어간다
            }
        }

    }
    fun btnClick(view: View) {
        // TODO

        val intent = Intent(Intent.ACTION_PICK)
        intent.type= android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data= android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, SELECT_IMAGE)

    }



}