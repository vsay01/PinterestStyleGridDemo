package com.vsay.pintereststylegriddemo.feature_bookmarks.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vsay.pintereststylegriddemo.feature.bookmarks.R



@Composable
fun BookmarkScreen() {
    BookmarkScreenUI(modifier = Modifier)
}

@Composable
fun BookmarkScreenUI(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = androidx.compose.ui.platform.LocalContext.current.getString(R.string.bookmark_screen_title),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = androidx.compose.ui.platform.LocalContext.current.getString(R.string.bookmark_screen_placeholder_text),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkScreenUIPreview() {
    MaterialTheme {
        BookmarkScreenUI(modifier = Modifier)
    }
}
