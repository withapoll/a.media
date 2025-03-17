package a.products.media

import a.products.media.ui.theme.AmediaTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.kwabenaberko.newsapilib.models.Article

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        setContent {
            AmediaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "Newsfeed",
                            modifier = Modifier.align(Alignment.Start)
                                .padding(12.dp),
                            color = Color.Gray,
                            fontSize = 24.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        val articleCount by newsViewModel.articles.observeAsState(emptyList())
                        Text(
                            text = "You have ${articleCount.size} unread articles",
                            modifier = Modifier.align(Alignment.Start)
                                .padding(12.dp),
                            color = Color.DarkGray,
                            fontSize = 16.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                        )
                        HomePage(newsViewModel)
                    }
                }
            }
        }
    }
}

