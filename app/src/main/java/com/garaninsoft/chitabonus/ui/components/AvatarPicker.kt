package com.garaninsoft.chitabonus.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.garaninsoft.chitabonus.R
import com.garaninsoft.chitabonus.ui.utils.DemoAvatarProvider

@Composable
fun AvatarPicker(
    avatarUri: String?,
    onAvatarSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    size: Int = 64
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .clickable {
                // Временная демо-реализация: случайный аватар
                val randomAvatarUri = DemoAvatarProvider.getRandomAvatarUri()
                onAvatarSelected(randomAvatarUri)
            },
        contentAlignment = Alignment.Center
    ) {
        if (avatarUri != null) {
            // Показываем выбранное изображение
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context)
                        .data(avatarUri)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = stringResource(R.string.avatar),
                modifier = Modifier
                    .size(size.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            // Иконка редактирования поверх фото
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.change_avatar),
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.BottomEnd),
                tint = MaterialTheme.colorScheme.primary
            )
        } else {
            // Показываем placeholder
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = stringResource(R.string.select_avatar),
                modifier = Modifier.size((size - 16).dp),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            // Иконка камеры
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = stringResource(R.string.select_avatar),
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.BottomEnd),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
fun PreviewAvatarPicker() {
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            AvatarPicker(
                avatarUri = null,
                onAvatarSelected = {}
            )
        }
    }
}