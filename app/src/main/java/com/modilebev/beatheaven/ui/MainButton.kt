package com.modilebev.beatheaven.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.modilebev.beatheaven.R
import com.modilebev.beatheaven.colorScheme
import com.modilebev.beatheaven.density
import com.modilebev.beatheaven.heightPx
import com.modilebev.beatheaven.themes.getAnimatedAlpha
import com.modilebev.beatheaven.themes.getAnimatedScale
import com.modilebev.beatheaven.widthPx


val topIdent = heightPx / 4.03f / 2
val radius = widthPx / 1.98f / 2
val rightTopCorner = Offset(radius, -topIdent)
val leftBottomCorner = Offset(-radius, 2*radius - topIdent)

val rightTopCorner1 = Offset(widthPx / 2 + widthPx / 2 / 1.98f, heightPx / 2 - topIdent - widthPx / 2 / 1.98f)
val leftBottomCorner1 = Offset(widthPx / 2 - widthPx / 2 / 1.98f, heightPx / 2 - topIdent + widthPx / 2 / 1.98f)

@Composable
fun CreateButton() {
    DrawButton()
    MakeItClickable()
}

@Composable
fun MakeItClickable() {
    Box(modifier = Modifier
        .graphicsLayer {
            clip = true
            shape = CircleShape
            translationY = (heightPx / 2 - topIdent - widthPx / 2 / 1.98f)
            translationX = (widthPx / 2 - widthPx / 2 / 1.98f)
        }
        .size((widthPx / density).dp / 1.98f)
        .clip(CircleShape)
        .clickable {
            onMainButtonClick()
            //button for audio stream
        }
    )
}

fun onMainButtonClick() {

}

@Composable
fun DrawButton() {
    val image = ImageBitmap.imageResource(id = R.drawable.button)
    val scaleMultiplier = widthPx / (image.width * 1.83f)

    val buttonCenter = Offset(widthPx / 2, heightPx / 2 - topIdent)


    Canvas(modifier = Modifier //button render
        .graphicsLayer(
            translationX = buttonCenter.x,
            translationY = buttonCenter.y,
            scaleX = getAnimatedScale(),
            scaleY = getAnimatedScale()
        )
        .size(1.dp) // 1dp is a workaround to prevent annoying render bug
    ) {
        val gradient = Brush.linearGradient(
            colors = colorScheme.MainButtonGradientColors,
            start = rightTopCorner,
            end = leftBottomCorner
        )

        val path = Path().apply {
            addOval(
                Rect(
                    Offset(-radius, -radius),
                    Size(radius*2, radius*2)
                )
            )
        }

        drawPath(path, gradient)
    }

    val animatedAlpha = getAnimatedAlpha()

    Canvas(modifier = Modifier //softlight render
        .graphicsLayer(
            translationX = buttonCenter.x,
            translationY = buttonCenter.y,
        )
        .size(1.dp) // 1dp is a workaround to prevent annoying render bug
    ) {
        val softLightRadius = widthPx / 2
        val softLightCenter = Offset(0f, radius - topIdent)
        val softLightGradient = Brush.radialGradient(
            colors = colorScheme.MainButtonSoftLightGradientColors,
            center = softLightCenter,
            radius = softLightRadius
        )

        drawCircle(
            brush = softLightGradient,
            center = softLightCenter,
            radius = softLightRadius,
            alpha = animatedAlpha
        )
    }

    Canvas(modifier = Modifier //image render
        .graphicsLayer(
            translationX = buttonCenter.x,
            translationY = buttonCenter.y,
            scaleX = getAnimatedScale(),
            scaleY = getAnimatedScale()
        )
        .size(1.dp) // 1dp is a workaround to prevent annoying render bug
    ) {
        withTransform({
            scale(scaleMultiplier, scaleMultiplier, Offset(0f, radius - topIdent))
        }) {
            drawImage(
                image = image,
                topLeft = Offset(0f - image.width / 2,
                    radius - topIdent - image.height / 2),
            )
        }
    }

}