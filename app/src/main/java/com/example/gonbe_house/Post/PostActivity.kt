package com.example.gonbe_house.Post

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.gonbe_house.MainActivity
import com.example.gonbe_house.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.add_ticket.view.*
import kotlinx.android.synthetic.main.tweets_ticket.view.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class PostActivity : AppCompatActivity() {

    private var database= FirebaseDatabase.getInstance()
    private var myRef=database.reference

    var ListTweets= ArrayList<Ticket>()
    var adpater:MyTweetAdpater?=null
    var myemail:String?=null
    var UserUID:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle("口コミ")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val b: Bundle? = intent.extras
        myemail=b?.getString("email")
        UserUID=b?.getString("uid")
        //Dummy data
        ListTweets.add(Ticket("0","him","url","add"))

        adpater= MyTweetAdpater(this,ListTweets)
        //this.adpater = MyTweetAdpater(this, ListTweets)
        lvTweets?.adapter=adpater

        LoadPost()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    inner class  MyTweetAdpater(context: Context, var listNotesAdpater: ArrayList<Ticket>) :
        BaseAdapter() {
        var context:Context?= context

        @SuppressLint("InflateParams")
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            val mytweet=listNotesAdpater[p0]

            if(mytweet.tweetPersonUID.equals("add")) {
                val myView = layoutInflater.inflate(R.layout.add_ticket, null)

                myView.iv_attach.setOnClickListener(View.OnClickListener {
                    loadImage()
                })
                myView.iv_post.setOnClickListener(View.OnClickListener {
                    //upload server
                    myRef.child("posts").push().setValue(
                        PostInfo(//UserUID!!,
                            UserUID.toString(),
                            myView.etPost.text.toString(), DownloadURL//!!
                                .toString()))
                    myView.etPost.setText("")
                })
                return myView
            } else if(mytweet.tweetPersonUID.equals("loading")){
                val myView=layoutInflater.inflate(R.layout.loading_ticket,null)
                return myView
            }else{
                val myView=layoutInflater.inflate(R.layout.tweets_ticket,null)
                myView.txt_tweet.setText(mytweet.tweetText)
                myView.txtUserName.setText(mytweet.tweetPersonUID)
//                myView.tweet_picture.setImageURI(mytweet.tweetImageURL)

//                myView.txt_tweet.text = mytweet.tweetText
//
//                //myView.tweet_picture.setImageURI(mytweet.tweetImageURL)
                Picasso.with(context).load("https://i.imgur.com/DvpvklR.png").into(myView.tweet_picture)
                //Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(myView.tweet_picture);
//                Picasso.with(context).load(mytweet.tweetImageURL).into(myView.tweet_picture)
//
                myRef.child("Users").child(mytweet.tweetPersonUID!!)
                    .addValueEventListener(object :ValueEventListener{

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            try {
                                val td= dataSnapshot.value as HashMap<*, *>

                                for(key in td.keys){
                                    val userInfo= td[key] as String
                                    if(key.equals("ProfileImage")){
                                        Picasso.with(context).load(userInfo).into(myView.picture_path)  //ここおおおおお
                                    }else{
                                        myView.txtUserName.setText(userInfo)
                                    }
                                }
                            }catch (_:Exception){}
                        }
                        override fun onCancelled(p0: DatabaseError) {
                        }
                    })
                return myView
            }
        }

        override fun getItem(p0: Int): Any {
            return listNotesAdpater[p0]
        }
        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }
        override fun getCount(): Int {
            return listNotesAdpater.size
        }
    }

    //    //Load image
    val PICK_IMAGE_CODE=123
    fun loadImage(){
        val intent3=Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent3,PICK_IMAGE_CODE)
    }

    @SuppressLint("Recycle")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==PICK_IMAGE_CODE  && data!=null && resultCode == RESULT_OK){
            val selectedImage=data.data
            val filePathColum= arrayOf(MediaStore.Images.Media.DATA)
            val cursor= contentResolver.query(selectedImage!!,filePathColum,null,null,null)
            cursor!!.moveToFirst()
            val coulomIndex=cursor.getColumnIndex(filePathColum[0])
            val picturePath=cursor.getString(coulomIndex)
            cursor.close()
            UploadImage(BitmapFactory.decodeFile(picturePath))
        }
    }


    var DownloadURL:String?=null
    @SuppressLint("SimpleDateFormat")
    fun UploadImage(bitmap:Bitmap){
        ListTweets.add(0,Ticket("0","him","url","loading"))
        adpater!!.notifyDataSetChanged()
        val storage=FirebaseStorage.getInstance()
        val storgaRef=storage.getReferenceFromUrl("gs://gonbe-house.appspot.com")
        val df=SimpleDateFormat("ddMMyyHHmmss")
        val dataobj=Date()
        val imagePath=
            //SplitString(myemail!!.toString()) + "."+
                    df.format(dataobj)+ ".jpg"
        val ImageRef=storgaRef.child("imagePost/"+imagePath )
        val baos=ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data= baos.toByteArray()
        val uploadTask=ImageRef.putBytes(data)
        uploadTask.addOnFailureListener{
            Toast.makeText(applicationContext,"fail to upload",Toast.LENGTH_LONG).show()
        }.addOnSuccessListener { taskSnapshot ->
            DownloadURL= taskSnapshot.storage.downloadUrl.toString()

            ListTweets.removeAt(0)
            adpater!!.notifyDataSetChanged()
        }
    }

//    fun SplitString(email:String):String{
//        val split= email.split("@")
//        return split[0]
//    }


    fun LoadPost(){
        myRef.child("posts")
            .addValueEventListener(object :ValueEventListener{

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    try {
                        ListTweets.clear()
                        ListTweets.add(Ticket("0","him","url","add"))
                        val td= dataSnapshot.value as HashMap<String,Any>

                        for(key in td.keys){
                            val post= td[key] as HashMap<String,Any>
                            ListTweets.add(Ticket(key,
                                post["text"] as String,
                                post["postImage"] as String ,
                                post["userUID"] as String))
                        }

                        adpater!!.notifyDataSetChanged()
                    }catch (_:Exception){}
                }
                override fun onCancelled(p0: DatabaseError) {
                }
            })
    }
}