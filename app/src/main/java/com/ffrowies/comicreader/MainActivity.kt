package com.ffrowies.comicreader

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.ffrowies.comicreader.Adapter.MyComicAdapter
import com.ffrowies.comicreader.Adapter.MySliderAdapter
import com.ffrowies.comicreader.Common.Common
import com.ffrowies.comicreader.Interface.IBannerLoadDoneListener
import com.ffrowies.comicreader.Interface.IComicLoadDoneListener
import com.ffrowies.comicreader.Model.Comic
import com.ffrowies.comicreader.Service.PicassoImageLoadingService
import com.google.firebase.database.*
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_main.*
import ss.com.bannerslider.Slider

class MainActivity : AppCompatActivity(), IBannerLoadDoneListener, IComicLoadDoneListener {

    override fun onComicLoadDoneListener(comicList: List<Comic>) {
        alertDialog.dismiss()

        Common.comicList = comicList
        recycler_comic.adapter = MyComicAdapter(baseContext, comicList)
        txt_comic.text = StringBuilder("NEW COMIC (")
            .append(comicList.size)
            .append(")")

        if (swipe_to_refresh.isRefreshing)
            swipe_to_refresh.isRefreshing = false
    }

    override fun onBannerLoadDoneListener(banners: List<String>) {
        slider.setAdapter(MySliderAdapter(banners))
    }

    //Database
    lateinit var bannersRef : DatabaseReference
    lateinit var comicRef :  DatabaseReference

    //Listener
    lateinit var iBannerLoadDoneListener: IBannerLoadDoneListener
    lateinit var iComicLoadDoneListener: IComicLoadDoneListener

    lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Init listener
        iBannerLoadDoneListener = this
        iComicLoadDoneListener = this

        //Init dialog
        alertDialog = SpotsDialog.Builder().setContext(this@MainActivity)
            .setCancelable(false)
            .setMessage("Please wait...")
            .build()

        //Init DB
        bannersRef = FirebaseDatabase.getInstance().getReference("Banners")
        comicRef = FirebaseDatabase.getInstance().getReference("Comic")

        //Load banner and comic
        swipe_to_refresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark)
        swipe_to_refresh.setOnRefreshListener {
            loadBanners()
            loadComic()
        }
        swipe_to_refresh.post {
            loadBanners()
            loadComic()
        }

        Slider.init(PicassoImageLoadingService())

        recycler_comic.setHasFixedSize(true)
        recycler_comic.layoutManager = GridLayoutManager(this@MainActivity, 2)

        btn_show_filter_search.setOnClickListener {
            startActivity(Intent(this@MainActivity, FilterSearchActivity::class.java))
        }
    }

    private fun loadComic() {

        alertDialog.show()

        comicRef.addListenerForSingleValueEvent(object: ValueEventListener{
            var comicLoad: MutableList<Comic> = ArrayList<Comic>()
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@MainActivity, ""+p0.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (comicSnapshot in p0.children) {
                    val comic = comicSnapshot.getValue(Comic::class.java)
                    comicLoad.add(comic!!)
                }
                iComicLoadDoneListener.onComicLoadDoneListener(comicLoad)
            }

        })
    }

    private fun loadBanners() {
        bannersRef.addListenerForSingleValueEvent(object:ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@MainActivity, ""+p0.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val bannerList = ArrayList<String>()
                for(banner in p0.children) {
                    val image = banner.getValue(String::class.java)
                    bannerList.add(image!!)
                }
                iBannerLoadDoneListener.onBannerLoadDoneListener(bannerList)
            }
        })
    }
}
