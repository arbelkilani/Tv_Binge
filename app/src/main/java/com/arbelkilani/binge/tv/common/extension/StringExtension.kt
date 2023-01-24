package com.arbelkilani.binge.tv.common.extension

fun String.hashtag(): String =
    "#".plus(split(" ").map {
        it.replaceFirstChar(Char::uppercase)
    }.joinToString(""))
