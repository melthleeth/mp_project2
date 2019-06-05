package com.example.kkon

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.ContextThemeWrapper
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
    var flag=0
    var civil_email=""
    var civil_status=""
    val database = FirebaseDatabase.getInstance()
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
                myRef333.child("user$pass3").child("writer").setValue(civil_email)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_civil_compliant)
        val myRef77777 : DatabaseReference = database.getReference("user_cnt")

        myRef77777.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                aaa=p0.child("cnt").value.toString().toInt()
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })

        val myRef = database.getReference("user")
        val i=intent
        civil_email=i.getStringExtra("user_email") //로그인한 이메일 받아오기
        /////////////////////////////////////////////////////컴플릿 명단에 있으면 알림오게하기
        val builder = AlertDialog.Builder(ContextThemeWrapper(this@civil_compliant, R.style.Theme_AppCompat_Light_Dialog))
        val myRef33 : DatabaseReference = database.getReference("complete")
        myRef33.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot in p0.children) {
                    if(civil_email==snapshot.child("writer").value.toString())
                    {
                        val email = snapshot.child("email").value.toString()
                        val status = snapshot.child("status").value.toString()
                        //////////////////////////////////////
                        myRef33.child(snapshot.key.toString()).removeValue()
                        builder.setTitle("알림")
                        builder.setMessage("회원님의 (" + email + " , " + status + ") 민원이 처리되었습니다.")
                        builder.setPositiveButton("확인") { _, _ ->

                        }
//                        builder.setNegativeButton("취소") { _, _ ->
//                        }
                        if(civil_email!="") {
                            builder.show()
                        }
                        //////////////////////////////////////
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })

        ////////////////////////////////////////

        civil_compliant_add.setOnClickListener {
            //민원 추가하는 버튼튼

            val i= Intent(applicationContext,complian_add::class.java)
            //  val database77777 : FirebaseDatabase = FirebaseDatabase.getInstance()
            val myRef77777 : DatabaseReference = database.getReference("user_cnt")
            myRef77777.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    aaa=p0.child("cnt").value.toString().toInt()
                }
                override fun onCancelled(p0: DatabaseError) {
                }
            })
            aaa++
            myRef77777.child("cnt").setValue(aaa)
            i.putExtra("cntt",aaa)
            i.putExtra("user_emaill",civil_email)
            startActivityForResult(i,123)
        }
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                data.clear()
                for (snapshot in p0.children) {
                    /////////////////////////
                    flag=0
                    Log.d("aaa","no77")
                    for(snapshot2 in snapshot.child("likepeople").children){
                        if(civil_email==snapshot2.value.toString())
                        {
                            flag=1
                        }
                    }

                    ////////////////////////
                    if(flag==1) {
                        Log.d("aaa","no2")
                        data.add(
                            Data(
                                snapshot.child("email").value.toString(),
                                snapshot.child("status").value.toString(),
                                1
                            )
                        )
                        flag=0
                    }else{
                        Log.d("aaa","no9")
                        data.add(
                            Data(
                                snapshot.child("email").value.toString(),
                                snapshot.child("status").value.toString(),
                                0
                            )
                        )
                        flag=0
                    }


                }
                init()
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        civil_email=""
    }
    fun init(){
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        listview.layoutManager = layoutManager
        adapter = Myadater(data)
        listview.adapter = adapter
        val myRef = database.getReference("user")
        val builder = AlertDialog.Builder(ContextThemeWrapper(this@civil_compliant, R.style.Theme_AppCompat_Light_Dialog))
        adapter.itemClickListener=object:Myadater.OnItemClickListener{
            override fun OnLikeClick(holder: Myadater.ViewHolder, view: View, data: Data, position: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                when(data.like){
                    0 -> {
                        Log.d("aaa","no3")
                        data.like=1
                        var kk=data.Id.toString()
                        var kk2=data.sta.toString()
                        //////////////////////
                        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                for (snapshot in dataSnapshot.children) {
                                    if(kk==snapshot.child("email").value.toString()&&kk2==snapshot.child("status").value.toString()) //어댑터 아이템의 장소와 디비테이블의 장소를 비교
                                    {
                                        ///////////////////////////////////
                                        val myRef77777 : DatabaseReference = database.getReference("user_cnt")
                                        myRef77777.addValueEventListener(object: ValueEventListener {
                                            override fun onDataChange(p0: DataSnapshot) {
                                                aaa=p0.child("cnt").value.toString().toInt()
                                            }
                                            override fun onCancelled(p0: DatabaseError) {
                                            }
                                        })
                                        aaa++
                                        ///////////////////////////////////
                                        myRef.child(snapshot.key.toString()).child("likepeople").child("people$aaa").setValue(civil_email)
                                        myRef77777.child("cnt").setValue(aaa)
                                    }
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {
                                // Failed to read value
                            }
                        })
                        //////////////////////
                    }

                    1 -> {
                        Log.d("aaa","no4")
                        data.like=0
                        var kk=data.Id.toString()
                        //////////////////////
                        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                for (snapshot in dataSnapshot.children) {
                                    if(kk==snapshot.child("email").value.toString()) //어댑터 아이템의 장소와 디비테이블의 장소를 비교
                                    {
                                        ///////////////////////////////////////////
                                        for(snapshot2 in snapshot.child("likepeople").children){
                                            if(civil_email==snapshot2.value.toString())
                                            {
                                               myRef.child(snapshot.key.toString()).child("likepeople").child(snapshot2.key.toString()).removeValue()

                                            }
                                        }
                                    }
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {
                                // Failed to read value
                            }
                        })
                    }
                }
                //init()
            }

            override fun OnItemClick(holder: Myadater.ViewHolder, view: View, data: Data, position: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
    }

}
