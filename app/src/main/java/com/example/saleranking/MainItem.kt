package com.example.saleranking

data class Item(val reed: Feed)

data class Feed(val results: Array<Result>)

data class Result(val name: String, val artworkUrl100: String)
