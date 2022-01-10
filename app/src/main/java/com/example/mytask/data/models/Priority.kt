package com.example.mytask.data.models

import androidx.compose.ui.graphics.Color
import com.example.mytask.ui.theme.HighPriorityColor
import com.example.mytask.ui.theme.LowPriorityColor
import com.example.mytask.ui.theme.MediumPriorityColor
import com.example.mytask.ui.theme.NonePriorityColor


enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}