package com.example.myapplication.db

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity2
import com.example.myapplication.R
import kotlin.collections.ArrayList

class MyAdapter (listMain:ArrayList<ListItem>, contextM: Context): RecyclerView.Adapter<MyAdapter.MyHolder>() {
    var listArray = listMain
    val context = contextM

    class MyHolder(itemView: View, contextV: Context) : RecyclerView.ViewHolder(itemView) {
        val context = contextV
        val tvTitle = itemView.findViewById<TextView>(R.id.TVtitle)
        fun setData(item:ListItem){
            tvTitle.text = item.title
            itemView.setOnClickListener(){
                val intent = Intent(context, MainActivity2::class.java).apply {
                    putExtra(MyIntentConstans.I_TITLE_KEY,item.title)
                    putExtra(MyIntentConstans.I_DISC_KEY,item.disc)
                    putExtra(MyIntentConstans.I_URI_KEY,item.uri)

                }
                context.startActivity(intent)
            }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyHolder(inflater.inflate(R.layout.rc_item, parent, false), context)
    }

    override fun getItemCount(): Int {
        return listArray.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
         holder.setData(listArray.get(position))
    }

    fun uptadeAdapter(listItems: ArrayList<ListItem>){

        listArray.clear()
        listArray.addAll(listItems)
        notifyDataSetChanged()


    }

    fun removeItem(pos:Int, dbManager: MyDbManager){
        dbManager.removeitemToDb(listArray[pos].id.toString())
        listArray.removeAt(pos)
        notifyItemRangeChanged(0, listArray.size)
        notifyItemRemoved(pos)
    }



}