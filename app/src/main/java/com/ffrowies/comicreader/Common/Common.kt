package com.ffrowies.comicreader.Common

import com.ffrowies.comicreader.Model.Chapter
import com.ffrowies.comicreader.Model.Comic
import java.lang.StringBuilder

object Common {
    fun formatString(name: String): String {
        val finalResult = StringBuilder(if (name.length > 15) name.substring(0,15) + "..." else name)
        return finalResult.toString()
    }

    var comicList: List<Comic> = ArrayList<Comic>()
    var selectedComic: Comic? = null
    lateinit var chapterList: List<Chapter>
    lateinit var selectedChapter: Chapter
    var chapterIndex: Int = -1
    var categories = arrayOf("Action", "Adult", "Adventure", "Comedy", "Completed", "Cooking", "Doujinshi")
}