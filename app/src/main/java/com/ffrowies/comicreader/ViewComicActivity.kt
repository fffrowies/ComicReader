package com.ffrowies.comicreader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ffrowies.comicreader.Adapter.MyViewPagerAdapter
import com.ffrowies.comicreader.Common.Common
import com.ffrowies.comicreader.Model.Chapter
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer
import kotlinx.android.synthetic.main.activity_view_comic.*

class ViewComicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_comic)

        back.setOnClickListener {
            if (Common.chapterIndex == 0) {
                //If user in first chapter but press back
                Toast.makeText(this@ViewComicActivity, "This is First Chapter", Toast.LENGTH_SHORT).show()
            } else {
                Common.chapterIndex--
                fetchLinks(Common.chapterList[Common.chapterIndex])
            }
        }
        next.setOnClickListener {
            if (Common.chapterIndex == Common.chapterList.size - 1) {
                //If user in last chapter but press next
                Toast.makeText(this@ViewComicActivity, "This is Last Chapter", Toast.LENGTH_SHORT).show()
            } else {
                Common.chapterIndex++
                fetchLinks(Common.chapterList[Common.chapterIndex])
            }
        }

        fetchLinks(Common.selectedChapter!!)
    }

    private fun fetchLinks(chapter: Chapter) {
        if (chapter.Links != null) {
            if (chapter.Links!!.size > 0) {
                val adapter = MyViewPagerAdapter(baseContext, chapter.Links!!)
                view_pager.adapter = adapter
                txt_chapter_name_view_comic.text = Common.formatString(Common.selectedChapter!!.Name!!)

                //Create book flip anim
                val bookFlipPageTransformer = BookFlipPageTransformer()
                bookFlipPageTransformer.scaleAmountPercent = 10f
                view_pager.setPageTransformer(true, bookFlipPageTransformer)
            } else {
                Toast.makeText(this@ViewComicActivity, "No image here", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this@ViewComicActivity, "This is Last Chapter", Toast.LENGTH_SHORT).show()
        }
    }
}
