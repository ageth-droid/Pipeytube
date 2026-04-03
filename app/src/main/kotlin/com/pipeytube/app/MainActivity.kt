package com.pipeytube.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.pipeytube.data.newpipe.FakeExtractorNewPipeService
import com.pipeytube.data.newpipe.NewPipeRepository
import com.pipeytube.ui.navigation.PipeytubeApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = NewPipeRepository(FakeExtractorNewPipeService())
        setContent { PipeytubeApp(repository) }
    }
}
