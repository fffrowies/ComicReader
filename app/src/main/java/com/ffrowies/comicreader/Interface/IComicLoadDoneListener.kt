package com.ffrowies.comicreader.Interface

import com.ffrowies.comicreader.Model.Comic

interface IComicLoadDoneListener {
    fun onComicLoadDoneListener(comicList: List<Comic>)
}