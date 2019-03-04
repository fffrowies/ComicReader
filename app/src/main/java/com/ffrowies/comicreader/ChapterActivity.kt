package com.ffrowies.comicreader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.ffrowies.comicreader.Adapter.MyChapterAdapter
import com.ffrowies.comicreader.Common.Common
import com.ffrowies.comicreader.Model.Comic
import kotlinx.android.synthetic.main.activity_chapter.*
import kotlinx.android.synthetic.main.chapter_item.*

class ChapterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter)

        toolbar.title = Common.selectedComic!!.Name
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        recycler_chapter.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this@ChapterActivity)
        recycler_chapter.layoutManager = layoutManager
        recycler_chapter.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        fetchChapter(Common.selectedComic!!)
    }

    private fun fetchChapter(comic: Comic) {
        Common.chapterList = comic.Chapters!!
        txt_chapter_name.text = StringBuilder("CHAPTER (")
            .append(comic.Chapters!!.size)
            .append(")")
        recycler_chapter.adapter = MyChapterAdapter(this, Common.chapterList)
    }
}
