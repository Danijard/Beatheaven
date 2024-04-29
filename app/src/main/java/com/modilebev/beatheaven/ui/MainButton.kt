package com.modilebev.beatheaven.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.imageResource
import com.modilebev.beatheaven.R

@Composable
fun DrawButton(colorScheme: Themes) {
    val image = ImageBitmap.imageResource(id = R.drawable.button)
    val multiplier = 1.8f

    Canvas(modifier = Modifier.fillMaxSize()) {

        val topIdent = size.height / 4.03f / 2

        val rightTopCorner = Offset(size.width / 2 + size.width / 2 / 1.98f, size.height / 2 - topIdent - size.width / 2 / 1.98f)
        val leftBottomCorner = Offset(size.width / 2 - size.width / 2 / 1.98f, size.height / 2 - topIdent + size.width / 2 / 1.98f)

        val gradient = Brush.linearGradient(
            colors = colorScheme.MainButtonGradientColors,
            start = rightTopCorner,
            end = leftBottomCorner
        )

        val softLightRadius = size.width / 2
        val softLightCenter = Offset(size.width / 2, size.height / 2 - topIdent)
        val softLightGradient = Brush.radialGradient(
            colors = colorScheme.MainButtonSoftLightGradientColors,
            center = softLightCenter,
            radius = softLightRadius
        )

        drawCircle(
            brush = softLightGradient,
            center = softLightCenter,
            radius = softLightRadius
        )

        val path = Path().apply {
            addOval(
                Rect(
                    Offset(size.width / 2 - size.width / 2 / 1.98f, size.height / 2 - topIdent - size.width / 2 / 1.98f),
                    Size(size.width / 1.98f, size.width / 1.98f)
                )
            )
        }
        drawPath(path, gradient)



        withTransform({
            scale(multiplier, multiplier, Offset(size.width / 2, size.height / 2 - topIdent))
        }) {
            drawImage(
                image = image,
                topLeft = Offset(size.width / 2 - image.width / 2,
                    size.height / 2 - topIdent - image.height / 2),
            )
        }
    }
}