package com.example.slideshow.data

import com.example.slideshow.R
import com.example.slideshow.model.Picture

class Datasource {
    fun loadImages(): List<Picture>{
        return listOf<Picture>(
            Picture(R.string.abstractflower, R.drawable.abstract_flower),
            Picture(R.string.image2, R.drawable.image2),
            Picture(R.string.image3, R.drawable.image3),
            Picture(R.string.image4, R.drawable.image4),
            Picture(R.string.image5, R.drawable.image5)
        )
    }
}