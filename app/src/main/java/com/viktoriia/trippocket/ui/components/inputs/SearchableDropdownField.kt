package com.viktoriia.trippocket.ui.components.inputs

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SearchableDropdownField(
    value: String,
    items: List<T>,
    onValueChange: (String) -> Unit,
    onItemSelected: (T) -> Unit,
    filter: (T, String) -> Boolean,
    itemContent: @Composable (T) -> Unit,
    label: @Composable () -> Unit,
    placeholder: String? = null,
    modifier: Modifier = Modifier,
    pageSize: Int = 30,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var expanded by remember { mutableStateOf(false) }

    var visibleCount by remember(value) { mutableIntStateOf(pageSize) }

    val matchedItems = remember(value, items, filter) {
        if (value.isBlank()) items
        else items.filter { filter(it, value) }
    }

    val visibleItems = remember(matchedItems, visibleCount) {
        matchedItems.take(visibleCount)
    }

    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState, matchedItems) {
        snapshotFlow {
            scrollState.maxValue > 0 && scrollState.value >= scrollState.maxValue
        }
            .distinctUntilChanged()
            .filter { atBottom -> atBottom }
            .collect {
                if (visibleCount < matchedItems.size) {
                    visibleCount += pageSize
                }
            }
    }

    ExposedDropdownMenuBox(
        expanded = expanded && visibleItems.isNotEmpty(),
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                expanded = true
            },
            label = label,
            placeholder = placeholder?.let { { Text(it) } },
            singleLine = true,
            keyboardOptions = keyboardOptions,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable)
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) expanded = true
                }
        )

        ExposedDropdownMenu(
            expanded = expanded && visibleItems.isNotEmpty(),
            onDismissRequest = { expanded = false },
            scrollState = scrollState,
            modifier = Modifier
                .heightIn(max = 280.dp)
                .verticalScrollbar(scrollState)
        ) {
            visibleItems.forEach { item ->
                DropdownMenuItem(
                    text = { itemContent(item) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun Modifier.verticalScrollbar(
    scrollState: ScrollState,
    width: Dp = 4.dp,
    edgePadding: Dp = 2.dp,
    minThumbHeight: Dp = 24.dp,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
): Modifier = this.drawWithContent {
    drawContent()

    val maxScroll = scrollState.maxValue
    if (maxScroll <= 0 || maxScroll == Int.MAX_VALUE) return@drawWithContent

    val viewportHeight = size.height
    val contentHeight = viewportHeight + maxScroll

    val thumbWidth = width.toPx()
    val thumbHeight = (viewportHeight * viewportHeight / contentHeight)
        .coerceIn(minThumbHeight.toPx(), viewportHeight)

    val thumbTop = scrollState.value.toFloat() / maxScroll * (viewportHeight - thumbHeight)

    drawRoundRect(
        color = color,
        topLeft = Offset(
            x = size.width - thumbWidth - edgePadding.toPx(),
            y = thumbTop
        ),
        size = Size(thumbWidth, thumbHeight),
        cornerRadius = CornerRadius(thumbWidth / 2)
    )
}