package com.example.kkon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_create_account2.*

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val TAG : String = "CreateAccount"

    var bbb=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account2)
        auth = FirebaseAuth.getInstance()
        val email = findViewById<EditText>(R.id.et_email)
        val password = findViewById<EditText>(R.id.et_password)
        val database77777 : FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef77777 : DatabaseReference = database77777.getReference("user_cnt")
        myRef77777.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                bbb=p0.child("cnt").value.toString().toInt()
//                        for (snapshot in p0.children) {
//                            aaa= snapshot.value.toString().toInt()
//                        }
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })
        //새로운 계정을 생성한다.
        bt_CreateAccount.setOnClickListener {
            //Log.d(TAG, "Data: " + email.text + password.text)

            if (email.text.toString().length == 0 || password.text.toString().length == 0){
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            //updateUI(user)
                            // 아니면 액티비티를 닫아 버린다.
                            ///////////////////////////////////////////
                            val database77777 : FirebaseDatabase = FirebaseDatabase.getInstance()
                            val myRef77777 : DatabaseReference = database77777.getReference("user_cnt")
                            myRef77777.addValueEventListener(object: ValueEventListener {
                                override fun onDataChange(p0: DataSnapshot) {
                                    bbb=p0.child("cnt").value.toString().toInt()
//                        for (snapshot in p0.children) {
//                            aaa= snapshot.value.toString().toInt()
//                        }
                                }
                                override fun onCancelled(p0: DatabaseError) {
                                }
                            })
                            bbb++
                            myRef77777.child("cnt").setValue(bbb)

                            if(spinner.selectedItem.toString()=="학생")
                            {
                                val database9 : FirebaseDatabase = FirebaseDatabase.getInstance()
                                val myRef9 : DatabaseReference = database9.getReference("account")
                                myRef9.child("user$bbb").child("email").setValue(email.text.toString())
                                myRef9.child("user$bbb").child("status").setValue("student")
                            }
                            else
                            {
                                val database9 : FirebaseDatabase = FirebaseDatabase.getInstance()
                                val myRef9 : DatabaseReference = database9.getReference("account")
                                myRef9.child("user$bbb").child("email").setValue(email.text.toString())
                                myRef9.child("user$bbb").child("status").setValue("administrator")
                            }
                            /////////////////////////////////////
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                            //updateUI(null)
                            //입력필드 초기화
                            email?.setText("")
                            password?.setText("")
                            email.requestFocus()
                        }
                    }
            }
        }


        bt_cancel.setOnClickListener {
            finish()
        }





    }
}
