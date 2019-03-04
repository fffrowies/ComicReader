package com.ffrowies.comicreader.Adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ffrowies.comicreader.R
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso

class MyViewPagerAdapter(internal var context: Context,
                         internal var linkList: List<String>): PagerAdapter() {

    internal var inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int {
        return linkList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.view_pager_item, container, false)
        val pageImage = imageLayout.findViewById<PhotoView>(R.id.page_image)
        Picasso.get().load(linkList[position]).into(pageImage)

        container.addView(imageLayout)
        return imageLayout
    }

}