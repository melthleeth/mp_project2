package com.example.kkon

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_civil_compliant.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.FirebaseDatabase



class civil_compliant : AppCompatActivity() { //로그인해서 들어왔을때 화면
    var data:ArrayList<Data> = ArrayList()
    lateinit var adapter:Myadater
    var aaa=0
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==123)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                val pass1=data?.getStringExtra("pass1")
                val pass2=data?.getStringExtra("pass2")
                var pass3=data?.getIntExtra("pass3",-1)
                val database333 : FirebaseDatabase = FirebaseDatabase.getInstance()
                val myRef333 : DatabaseReference = database333.getReference("user")
                myRef333.child("user$pass3").child("email").setValue(pass1) //user1 email status
                myRef333.child("user$pass3").child("status").setValue(pass2)

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_civil_compliant)
        val database77777 : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef77777 : DatabaseReference = database77777.getReference("user_cnt")
        myRef77777.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                aaa=p0.child("cnt").value.toString().toInt()
//                        for (snapshot in p0.children) {
//                            aaa= snapshot.value.toString().toInt()
//                        }
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("user")
        val i=intent
        val civil_email=i.getStringExtra("user_email") //로그인한 이메일 받아오기
        civil_compliant_back.setOnClickListener {
            //back버튼 눌렀을때 다시 아디 비번 치는곳으로 가기기(finish쓰기)
            finish()
        }

        civil_compliant_add.setOnClickListener {
            //민원 추가하는 버튼튼

            val i= Intent(applicationContext,complian_add::class.java)
            val database77777 : FirebaseDatabase = FirebaseDatabase.getInstance()
            val myRef77777 : DatabaseReference = database77777.getReference("user_cnt")
                myRef77777.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        aaa=p0.child("cnt").value.toString().toInt()
//                        for (snapshot in p0.children) {
//                            aaa= snapshot.value.toString().toInt()
//                        }
                    }
                    override fun onCancelled(p0: DatabaseError) {
                    }
                })
            aaa++
            myRef77777.child("cnt").setValue(aaa)
            i.putExtra("cntt",aaa)
            startActivityForResult(i,123)
        }
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                data.clear()
                for (snapshot in p0.children) {
                    data.add(
                        Data(
                            snapshot.child("email").value.toString(),
                            snapshot.child("status").value.toString()
                        )
                    )
                }
                init()
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }
    fun init(){
       val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        listview.layoutManager = layoutManager
        adapter = Myadater(data)
        listview.adapter = adapter
        adapter.ItemClickListener = object:Myadater.OnItemClickListener{
            override fun OnItemClick(holder: Myadater.ViewHolder, view: View, data: Data, postion: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                val database123 : FirebaseDatabase = FirebaseDatabase.getInstance()
                val myRef123 : DatabaseReference = database123.getReference("user")
            }

        }
    }

}
