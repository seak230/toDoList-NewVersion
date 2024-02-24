package com.nohjason.myapplicationroom

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nohjason.myapplicationroom.room.MainViewModel
import com.nohjason.myapplicationroom.room.UserEntity
import com.nohjason.myapplicationroom.ui.theme.Blue
import com.nohjason.myapplicationroom.ui.theme.DarkGrayBlue
import com.nohjason.myapplicationroom.ui.theme.MyApplicationRoomTheme
import com.nohjason.myapplicationroom.ui.theme.Purple40
import com.nohjason.myapplicationroom.ui.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationRoomTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val owner = LocalViewModelStoreOwner.current

                    owner?.let {
                        val viewModel: MainViewModel = viewModel(
                            it,
                            "MainViewModel",
                            MainViewModelFactory(
                                LocalContext.current.applicationContext as Application
                            )
                        )
                        ScreenSetup(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenSetup(viewModel: MainViewModel) {
    val allProducts by viewModel.allProducts.observeAsState(listOf())

    MainScreen(
        allProduct = allProducts,
        viewModel = viewModel
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    allProduct: List<UserEntity>,
    viewModel: MainViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGrayBlue)
    ){
        var textState by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .height(600.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(50.dp, 0.dp, 0.dp, 0.dp))
                .background(White)
        ){
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Box(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .height(65.dp)
                        .width(327.dp)
                        .clip(CircleShape)
                        .background(DarkGrayBlue)
                ){
                    Row(
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        TextField(
                            value = textState,
                            onValueChange = { textState = it },
                            modifier = Modifier
                                .height(47.dp)
                                .width(230.dp)
                                .clip(CircleShape),

                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                        )

                        Spacer(modifier = Modifier.width(27.dp))

                        IconButton(onClick = {
                            if (textState.isNotEmpty()) {
                                viewModel.insertProduct(
                                    UserEntity(0, textState, false)
                                )
                                textState = ""
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Search",
                                tint = White,
                                modifier = Modifier.size(38.dp)
                            )
                        }

                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                LazyColumn {
                    items(allProduct) { product ->
                        UserList(
                            id = product.id,
                            text = product.name,
                            checked = product.check,
                            viewModel = viewModel
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }
            }

        }
    }
}

@Composable
fun UserList(
    id: Int,
    text: String,
    checked: Boolean,
    viewModel: MainViewModel
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.LightGray)
            .width(266.dp)
            .height(98.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = {
                    viewModel.updateProduct(UserEntity(id, text, !checked))
                }
            )

            Text(
                text = text,
                textDecoration = if(checked) TextDecoration.LineThrough else null,
            )
        }
    }
}

class MainViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application) as T
    }
}