package com.example.kkon

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_complian_add.*

class complian_add : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complian_add)
        val i =intent
        compliant_add_btn.setOnClickListener {
            val s= Intent()
            var cnttt=i.getIntExtra("cntt",-1)
            var civil_emaill=i.getStringExtra("user_emaill")
            s.putExtra("pass1",compliant_add_spot.text.toString())
            s.putExtra("pass2",compliant_add_content.text.toString())
            s.putExtra("pass3",cnttt)
            setResult(Activity.RESULT_OK,s)
            if (compliant_add_spot.text.toString().length == 0 || compliant_add_content.text.toString().length == 0) {
                Toast.makeText(applicationContext, "장소와 내용이 모두 적혀있는지 확인하세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                finish()//여기서 피니시하면 호출한 메인으로 넘어간다
            }
        }

    }

}
