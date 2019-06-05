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
import android.widget.AdapterView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_civil_compliant.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_civil_compliant_advisor.*


class civil_compliant_advisor : AppCompatActivity() { //로그인해서 들어왔을때 화면
    var data:ArrayList<Data> = ArrayList()
    lateinit var adapter:Myadater
    var aaa=0
    var flag=0
    var civil_email=""
    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_civil_compliant_advisor)
        val myRef77777 : DatabaseReference = database.getReference("user_cnt")
        spin_advisor.onItemSelectedListener=SpinnerSelectedListener()
        myRef77777.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                aaa=p0.child("cnt").value.toString().toInt()
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })

        val myRef = database.getReference("user")
        val i=intent
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                data.clear()
                for (snapshot in p0.children) {
                        if(spin_advisor.selectedItem.toString()!="전체"){
                        if(spin_advisor.selectedItem.toString()==snapshot.child("email").value.toString()) {
                            data.add(
                                Data(
                                    snapshot.child("email").value.toString(),
                                    snapshot.child("status").value.toString(),
                                    0
                                )
                            )
                        }
                        }else{
                            data.add(
                                Data(
                                    snapshot.child("email").value.toString(),
                                    snapshot.child("status").value.toString(),
                                    0
                                )
                            )
                        }
                }
                init()
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    fun init(){
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        listview2.layoutManager = layoutManager
        adapter = Myadater(data)
        listview2.adapter = adapter
        val myRef = database.getReference("user")
        val builder = AlertDialog.Builder(ContextThemeWrapper(this@civil_compliant_advisor, R.style.Theme_AppCompat_Light_Dialog))
        adapter.itemClickListener=object:Myadater.OnItemClickListener{
            override fun OnLikeClick(holder: Myadater.ViewHolder, view: View, data: Data, position: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            }

            override fun OnItemClick(holder: Myadater.ViewHolder, view: View, data: Data, position: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                var aa=data.Id.toString()
                var bb=data.sta.toString()
                //////////////////////////////////////

                builder.setTitle("진짜 진짜 ")
                builder.setMessage("진짜로 지우시겠습니까?")
                builder.setPositiveButton("확인") { _, _ ->
                    myRef.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            for (snapshot in dataSnapshot.children) {
                                if(aa==snapshot.child("email").value.toString()&&bb==snapshot.child("status").value.toString()) //어댑터 아이템의 장소와 디비테이블의 장소를 비교
                                {
                                    myRef.child(snapshot.key.toString()).removeValue()

                                    val myRef77777 : DatabaseReference = database.getReference("user_cnt")
                                    val myRef333 : DatabaseReference = database.getReference("complete")

                                    myRef77777.addValueEventListener(object: ValueEventListener {
                                        override fun onDataChange(p0: DataSnapshot) {
                                            aaa=p0.child("cnt").value.toString().toInt()
                                        }
                                        override fun onCancelled(p0: DatabaseError) {
                                        }
                                    })
                                    for(snapshot2 in snapshot.child("likepeople").children) {
                                        aaa++
                                    myRef333.child("complete_id$aaa").child("email")
                                        .setValue(snapshot.child("email").value.toString())
                                    myRef333.child("complete_id$aaa").child("status")
                                        .setValue(snapshot.child("status").value.toString())
                                    myRef333.child("complete_id$aaa").child("writer")
                                        .setValue(snapshot2.value.toString())
                                    myRef77777.child("cnt").setValue(aaa)
                                }
                                    ///////////////
                                }
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            // Failed to read value
                        }
                    })
                }
                builder.setNegativeButton("취소") { _, _ ->
                }
                builder.show()
                //////////////////////////////////////

            }

        }
    }
    inner class SpinnerSelectedListener: AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            val myRef = database.getReference("user")
            myRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    data.clear()
                    for (snapshot in p0.children) {
                        if(spin_advisor.selectedItem.toString()!="전체"){
                            if(spin_advisor.selectedItem.toString()==snapshot.child("email").value.toString()) {
                                data.add(
                                    Data(
                                        snapshot.child("email").value.toString(),
                                        snapshot.child("status").value.toString(),
                                        0
                                    )
                                )
                            }
                        }else{
                            data.add(
                                Data(
                                    snapshot.child("email").value.toString(),
                                    snapshot.child("status").value.toString(),
                                    0
                                )
                            )
                        }
                    }
                    init()
                }
                override fun onCancelled(p0: DatabaseError) {
                }
            })
        }
    }

}

