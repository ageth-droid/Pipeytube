package com.pipeytube

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pipeytube.navigation.PipeytubeApp
import com.pipeytube.ui.theme.PipeytubeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PipeytubeTheme {
                PipeytubeApp()
            }
        }
    }
}
