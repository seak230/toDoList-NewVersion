package com.nohjason.myapplicationroom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.nohjason.myapplicationroom.room.MainViewModel
import com.nohjason.myapplicationroom.room.UserEntity
import com.nohjason.myapplicationroom.ui.theme.White

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

            IconButton(onClick = {
                viewModel.deleteProduct(UserEntity(id, text, checked))
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}