package com.example.kkon


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class Myadater(val items:ArrayList<Data>)
    :RecyclerView.Adapter<Myadater.ViewHolder>(){
    interface OnItemClickListener{
        fun OnItemClick(holder: ViewHolder,view: View,data:Data,position:Int)
    }
    var itemClickListener:OnItemClickListener?=null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val v = LayoutInflater.from(p0.context)
            .inflate(R.layout.listlayout,p0,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        p0.name.text = items.get(p1).Id
        p0.age.text = items.get(p1).sta
    }

    override fun getItemCount(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return items.size
    }
    inner class ViewHolder(itemView: View) //사용할멤버 만들기
        : RecyclerView.ViewHolder(itemView){
        var name: TextView
        var age: TextView
        init{ //초기화를 해줄수있는 init블록
            name = itemView.findViewById(R.id.text1)
            age = itemView.findViewById(R.id.text2)
            itemView.setOnClickListener {
                val position=adapterPosition
                itemClickListener?.OnItemClick(this,it,items[position],position)
            }

        }
    }
}