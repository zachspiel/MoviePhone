package com.moviephone.android.mainactivity

class ParseItem {
    var imgUrl: String? = null
    var title: String? = null
    var detailUrl: String? = null

    constructor(imgUrl: String?, title: String?, detailUrl: String?) {
        this.imgUrl = imgUrl
        this.title = title
        this.detailUrl = detailUrl
    }

}