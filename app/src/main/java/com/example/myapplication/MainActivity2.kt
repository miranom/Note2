package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.db.MyDbHelper
import com.example.myapplication.db.MyDbManager
import com.example.myapplication.db.MyIntentConstans


@Suppress("UNUSED_CHANGED_VALUE")
class MainActivity2 : AppCompatActivity() {
    val imageRequestCod = 10
    var tempImamgeUri = "empty"
    val MyDbManager = MyDbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getMyIntents()
    }

    override fun onResume(){
        super.onResume()
        MyDbManager.openDb()

    }

    override fun onDestroy(){
        super.onDestroy()
        MyDbManager.closeDb()
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == imageRequestCod){

            val MyImage: ImageView = findViewById(R.id.MyImage)
            MyImage.setImageURI(data?.data)
            tempImamgeUri = data?.data.toString()
            contentResolver.takePersistableUriPermission(data?.data!!,Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }




    fun OnClickOneImage(view: View)
    {
        val imageLayout = findViewById<ConstraintLayout>(R.id.ImageLayout)
        imageLayout.visibility = View.VISIBLE
        val addImage: Button = findViewById(R.id.fbSave)
        addImage.visibility = View.GONE

    }
    fun OnClickDelete(view: View)
    {
        val imageLayout = findViewById<ConstraintLayout>(R.id.ImageLayout)
        imageLayout.visibility = View.GONE
        val addImage: Button = findViewById(R.id.fbSave)
        addImage.visibility = View.VISIBLE

        tempImamgeUri = "empty"

    }
    fun OnClickChooseImage(view: View){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"

        startActivityForResult(intent, imageRequestCod)

    }






    fun OnClickSave(view: View){
        val myTitleId = findViewById<EditText>(R.id.edTitle)
        val myTitle = myTitleId.text.toString()
        val myDiscId= findViewById<EditText>(R.id.edDis)
        val myDisc = myDiscId.text.toString()
        if(myDisc!=""&&myTitle!=""){
            MyDbManager.insertToDb(myTitle, myDisc, tempImamgeUri)
            finish()
        }
    }
    fun getMyIntents(){
        val i = intent

        if(i!=null){
            if(i.getStringExtra(MyIntentConstans.I_TITLE_KEY)!=null){
                val myTitleId = findViewById<EditText>(R.id.edTitle)
                val myDiscId= findViewById<EditText>(R.id.edDis)
                myTitleId.setText(i.getStringExtra(MyIntentConstans.I_TITLE_KEY))
                myDiscId.setText(i.getStringExtra(MyIntentConstans.I_DISC_KEY))
                if(i.getStringExtra(MyIntentConstans.I_URI_KEY) != "empty"){
                    val imageLayout = findViewById<ConstraintLayout>(R.id.ImageLayout)
                    imageLayout.visibility = View.VISIBLE
                    val addImage: Button = findViewById(R.id.fbSave)
                    addImage.visibility = View.GONE
                    val MyImage = findViewById<ImageView>(R.id.MyImage)
                    MyImage.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstans.I_URI_KEY)))
                }
            }

        }
    }


}

