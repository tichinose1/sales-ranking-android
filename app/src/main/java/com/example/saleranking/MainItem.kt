package com.example.saleranking

data class Item(val feed: Feed)

data class Feed(val results: Array<Result>)

data class Result(val name: String, val artworkUrl100: String)
