package com.jdeveloperapps.kotlinproject.common

import android.view.View

fun View.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()
fun View.dip(value: Float): Int = (value * resources.displayMetrics.density).toInt()