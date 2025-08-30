package com.garaninsoft.chitabonus.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.garaninsoft.chitabonus.R
import com.garaninsoft.chitabonus.domain.model.Child
import com.garaninsoft.chitabonus.ui.theme.ChitabonusTheme
import java.util.Date

@Composable
fun ChildCard(
    child: Child,
    pointsPerUnit: Double,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Аватар с возможностью выбора
            AvatarDisplay(
                avatarUri = child.avatarUri,
                size = 64
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Информация
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = child.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Возраст
                child.birthDate?.let { birthDate ->
                    val age = calculateAge(birthDate)
                    Text(
                        text = "$age лет",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Баланс
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Баллы",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "%.1f баллов".format(child.currentBalance),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "≈ %.2f ₽".format(child.currentBalance / pointsPerUnit),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

// Новый компонент для отображения аватара (без возможности выбора)
@Composable
fun AvatarDisplay(
    modifier: Modifier = Modifier,
    avatarUri: String?,
    size: Int = 64
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        if (avatarUri != null) {
            // Показываем реальное изображение
            val context = LocalContext.current
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
        } else {
            // Placeholder
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = stringResource(R.string.avatar),
                modifier = Modifier.size((size - 16).dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

// Функция для вычисления возраста
private fun calculateAge(birthDate: Date): Int {
    val now = Date()
    val diff = now.time - birthDate.time
    return (diff / (1000L * 60 * 60 * 24 * 365)).toInt()
}

@Preview
@Composable
fun PreviewChildCard() {
    ChitabonusTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ChildCard(
                child = Child(
                    id = 1,
                    name = "Мария Иванова",
                    birthDate = Date(System.currentTimeMillis() - 8L * 365 * 24 * 60 * 60 * 1000), // 8 лет
                    currentBalance = 154.5
                ),
                pointsPerUnit = 1.0,
                onClick = {}
            )

            ChildCard(
                child = Child(
                    id = 2,
                    name = "Александр Петров",
                    birthDate = Date(System.currentTimeMillis() - 10L * 365 * 24 * 60 * 60 * 1000), // 10 лет
                    currentBalance = 87.2
                ),
                pointsPerUnit = 1.0,
                onClick = {}
            )
        }
    }
}