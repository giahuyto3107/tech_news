package com.example.tech_news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tech_news.core.theme.LocalTechNewsDimens
import com.example.tech_news.core.theme.Tech_newsTheme
import com.example.tech_news.features.feeds.ui.FeedScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tech_newsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FeedScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(
                                horizontal = LocalTechNewsDimens.current.spaceMedium,
                                vertical = LocalTechNewsDimens.current.spaceMedium,
                            )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    Tech_newsTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            FeedScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(
                        horizontal = LocalTechNewsDimens.current.spaceMedium,
                        vertical = LocalTechNewsDimens.current.spaceMedium,
                    )
            )
        }
    }
}