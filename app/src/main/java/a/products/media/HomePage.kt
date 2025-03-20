package a.products.media

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.kwabenaberko.newsapilib.models.Article

@Composable
fun HomePage(newsViewModel: NewsViewModel, navController: NavHostController) {
    val articles by newsViewModel.articles.observeAsState(emptyList())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchQueryBar(newsViewModel)
        CategoriesBar(newsViewModel)
        LazyColumn(
            modifier = Modifier.fillMaxSize())
        {
            items(articles){ article ->
                ArticleItem(article, navController)
            }
        }
    }
}

@Composable
fun ArticleItem(article: Article, navController: NavHostController) {
    Card(
        modifier = Modifier.padding(8.dp),
        colors = CardColors(
            containerColor = Color.Transparent,
            contentColor = Color.Gray,
            disabledContainerColor = Color.DarkGray,
            disabledContentColor = Color.Gray
        ),
        onClick = {
            navController.navigate(NewsArticlePageRoute(article.url))
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = article.urlToImage?:"https://www.google.com/url?sa=i&url=https%3A%2F%2Flovepik.com%2Fimages%2Fpng-error.html&psig=AOvVaw1JphqVbP0MNjxtsZnLKC1z&ust=1742052716832000&source=images&cd=vfe&opi=89978449&ved=0CBUQjRxqFwoTCMi5i6fyiYwDFQAAAAAdAAAAABAJ",
                contentDescription = "Image",
                modifier = Modifier
                    .size(70.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = article.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 3
                )
            }
        }
    }
}

@Composable
fun CategoriesBar(newsViewModel: NewsViewModel) {
    val categoriesList = listOf("Technology", "Business", "Entertainment", "Health", "Science", "Sports", "General")
    var selectedCategory by remember { mutableStateOf("General") }

    Row(
        modifier = Modifier.fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        categoriesList.forEach { category ->
            Button(
                onClick = {
                    selectedCategory = category
                    newsViewModel.fetchNewsTopHeadlines(category)
                },
                modifier = Modifier.padding(8.dp),
                colors = if (selectedCategory == category) {
                    ButtonDefaults.buttonColors(containerColor = Color.Blue, contentColor = Color.White)
                } else {
                    ButtonDefaults.buttonColors(containerColor = Color.LightGray, contentColor = Color.Black)
                }
            ) {
                Text(text = category)
            }
        }
    }
}

@Composable
fun SearchQueryBar(newsViewModel: NewsViewModel) {
    var searchQuery by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                newsViewModel.fetchEverythingWithQuery(searchQuery)
            },
            placeholder = { Text(text = "Search") },
            modifier = Modifier.padding(8.dp)
                .fillMaxWidth()
                .border(color = Color.Gray, width = 2.dp, shape = CircleShape)
                .clip(CircleShape)
        )
    }
}