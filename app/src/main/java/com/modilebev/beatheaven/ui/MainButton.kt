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
import com.modilebev.beatheaven.widthPx
import com.modilebev.beatheaven.heightPx

@Composable
fun DrawButton(colorScheme: Themes) {
    val image = ImageBitmap.imageResource(id = R.drawable.button)
    val scaleMultiplier = widthPx / (image.width * 1.83f)

    Canvas(modifier = Modifier.fillMaxSize()) {

        val topIdent = heightPx / 4.03f / 2

        val rightTopCorner = Offset(widthPx / 2 + widthPx / 2 / 1.98f, heightPx / 2 - topIdent - widthPx / 2 / 1.98f)
        val leftBottomCorner = Offset(widthPx / 2 - widthPx / 2 / 1.98f, heightPx / 2 - topIdent + widthPx / 2 / 1.98f)

        val gradient = Brush.linearGradient(
            colors = colorScheme.MainButtonGradientColors,
            start = rightTopCorner,
            end = leftBottomCorner
        )

        val softLightRadius = widthPx / 2
        val softLightCenter = Offset(widthPx / 2, heightPx / 2 - topIdent)
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
                    Offset(widthPx / 2 - widthPx / 2 / 1.98f, heightPx / 2 - topIdent - widthPx / 2 / 1.98f),
                    Size(widthPx / 1.98f, widthPx / 1.98f)
                )
            )
        }
        drawPath(path, gradient)

        withTransform({
            scale(scaleMultiplier, scaleMultiplier, Offset(widthPx / 2, heightPx / 2 - topIdent))
        }) {
            drawImage(
                image = image,
                topLeft = Offset(widthPx / 2 - image.width / 2,
                    heightPx / 2 - topIdent - image.height / 2),
            )
        }
    }
}