package com.ffrowies.comicreader.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ffrowies.comicreader.ChapterActivity
import com.ffrowies.comicreader.Common.Common
import com.ffrowies.comicreader.Interface.IRecyclerClick
import com.ffrowies.comicreader.Model.Comic
import com.ffrowies.comicreader.R
import com.squareup.picasso.Picasso

class MyComicAdapter(internal var context: Context,
                     internal var comicList: List<Comic>): RecyclerView.Adapter<MyComicAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.comic_item, p0, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return comicList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        Picasso.get().load(comicList[p1].Image).into(p0.imageView)
        p0.textView.text = comicList[p1].Name

        p0.setClick(object: IRecyclerClick {
            override fun onClick(view: View, position: Int) {
                context.startActivity(Intent(context, ChapterActivity::class.java))
                Common.selectedComic = comicList[position]
            }

        })
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            iRecyclerClick.onClick(v!!, adapterPosition)
        }

        var imageView: ImageView
        var textView: TextView
        lateinit var iRecyclerClick: IRecyclerClick

        fun setClick(iRecyclerClick: IRecyclerClick) {
            this.iRecyclerClick = iRecyclerClick
        }

        init {
            imageView = itemView.findViewById(R.id.img_comic) as ImageView
            textView = itemView.findViewById(R.id.txt_comic_name) as TextView

            itemView.setOnClickListener(this)
        }

    }

}