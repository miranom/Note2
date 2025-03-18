package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.db.MyAdapter
import com.example.myapplication.db.MyDbManager
import org.w3c.dom.Text
import java.util.ArrayList


class MainActivity : AppCompatActivity() {
    val MyDbManager = MyDbManager(this)
    val myAdapter = MyAdapter(ArrayList(), this)

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        init()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

    override fun onResume(){
        super.onResume()
        MyDbManager.openDb()
        fillAdapter()
    }

    override fun onDestroy(){
        super.onDestroy()
        MyDbManager.closeDb()

    }

    fun onClickNew(view: View){
        val i = Intent(this, MainActivity2::class.java)
        startActivity(i)
    }

    fun init(){
        val rcView = findViewById<RecyclerView>(R.id.recyclerView)
        rcView.layoutManager = LinearLayoutManager(this)
        val swapHelper = getSwapMg()
        swapHelper.attachToRecyclerView(rcView)
        rcView.adapter = myAdapter
    }

    fun fillAdapter(){
        val list = MyDbManager.readDbData()

        myAdapter.uptadeAdapter(list)
        val textView = findViewById<TextView>(R.id.textView)
        if(list.size>0){
            textView.visibility = View.GONE
        }else{
            textView.visibility = View.VISIBLE
        }

    }

    private fun getSwapMg(): ItemTouchHelper{

        return ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                myAdapter.removeItem(viewHolder.adapterPosition, MyDbManager)
            }


        })
    }







    /*
    fun onClickDeleteNote(view: View){




    }
    fun OnDeleteNote(viewHolder:RecyclerView.ViewHolder){
        myAdapter.removeItem(viewHolder.adapterPosition)
    }

     */

}