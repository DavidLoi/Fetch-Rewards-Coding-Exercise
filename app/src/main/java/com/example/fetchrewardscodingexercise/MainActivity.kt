package com.example.fetchrewardscodingexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fetchrewardscodingexercise.ui.theme.FetchRewardsCodingExerciseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchRewardsCodingExerciseTheme {
                ListItemScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItemScreen(viewModel: ListItemViewModel = viewModel()) {
    val listItems by viewModel.listItems.observeAsState(initial = emptyMap())

    // Keep track of expanded list
    val expandedGroup = remember { mutableStateOf<Int?>(null)}

    // Get height of screen
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    LaunchedEffect(Unit) {
        viewModel.fetchListItems()
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Fetch Rewards Coding Exercise"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            LazyColumn (
                modifier = Modifier.fillMaxSize()
            ) {
                listItems.forEach { (listId, listItems) ->
                    // Header button for each listId
                    item {
                        Button (
                            onClick = {
                                if (expandedGroup.value == listId) {
                                    expandedGroup.value = null
                                } else {
                                    expandedGroup.value = listId
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "List ID: $listId",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                    // Render the items based on whether the group is expanded or not
                    if (expandedGroup.value == listId) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = screenHeight - 324.dp)
                            ) {
                                LazyColumn {
                                    items(listItems) { item ->
                                        ListItemRow(item)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListItemRow(item: ListItem) {
    Column( modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
        HorizontalDivider()
    }
}