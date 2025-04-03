package com.example.slideshow.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Picture(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)
