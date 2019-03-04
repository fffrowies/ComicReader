package com.ffrowies.comicreader.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ffrowies.comicreader.Common.Common
import com.ffrowies.comicreader.Interface.IRecyclerClick
import com.ffrowies.comicreader.Model.Chapter
import com.ffrowies.comicreader.R
import com.ffrowies.comicreader.ViewComicActivity
import java.lang.StringBuilder

class MyChapterAdapter(internal var context: Context,
                       internal var chapterList: List<Chapter>): RecyclerView.Adapter<MyChapterAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.chapter_item, p0, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return chapterList.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.txt_chapter_number.text = StringBuilder(chapterList[p1].Name)

        p0.setClick(object: IRecyclerClick{
            override fun onClick(view: View, position: Int) {
                Common.selectedChapter = chapterList[position]
                Common.chapterIndex = position
                context.startActivity(Intent(context, ViewComicActivity::class.java))
            }

        })
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            iRecyclerClick.onClick(v!!, adapterPosition)
        }

        internal var txt_chapter_number: TextView
        internal lateinit var iRecyclerClick: IRecyclerClick

        fun setClick(iRecyclerClick: IRecyclerClick) {
            this.iRecyclerClick = iRecyclerClick
        }

        init {
            txt_chapter_number = itemView.findViewById(R.id.txt_chapter_number) as TextView

            itemView.setOnClickListener(this)
        }
    }
}