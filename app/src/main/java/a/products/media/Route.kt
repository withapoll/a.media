package a.products.media

import kotlinx.serialization.Serializable

@Serializable
object HomePageScreen

@Serializable
data class NewsArticlePageRoute (
    val url: String
)
