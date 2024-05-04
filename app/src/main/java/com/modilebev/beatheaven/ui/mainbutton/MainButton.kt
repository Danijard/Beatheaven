package com.modilebev.beatheaven.ui.mainbutton

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.modilebev.beatheaven.R
import com.modilebev.beatheaven.colorScheme
import com.modilebev.beatheaven.density
import com.modilebev.beatheaven.heightPx
import com.modilebev.beatheaven.recordTime
import com.modilebev.beatheaven.widthPx
import kotlin.math.ceil


val topIdent = heightPx / 4.03f / 2
val radius = widthPx / 1.98f / 2
val rightTopCorner = Offset(radius, -topIdent)
val leftBottomCorner = Offset(-radius, 2*radius - topIdent)
val buttonCenter = Offset(widthPx / 2, heightPx / 2 - topIdent)
const val rotationSpeed = 0.7f //rps
var rotationTime: Float = recordTime + 15f
var animRotation2: Float = 0f

@Composable
fun CreateButton() {

    val buttonSize = (radius * 2 / density).dp

    val animAlpha = getAnimatedAlpha()
    val animScale = getAnimatedScale()


    val rotationState = remember { mutableFloatStateOf(0f) }


    val buttonRotation by animateFloatAsState(
        targetValue = rotationState.floatValue,
        animationSpec = keyframes {
            durationMillis = ceil(rotationTime * 1000).toInt()
            0f at 0 with CubicBezierEasing(0.75f, -0.6f, 0.5f, 0f)
            ceil(rotationTime / 15 * rotationSpeed) * 360 at ceil(rotationTime * 100).toInt() with CubicBezierEasing(0.5f, 1f, 0.5f, 1f)
            ceil(rotationTime * rotationSpeed) * 360 at ceil(rotationTime * 1000).toInt()
        }
    )
    var animRotation = buttonRotation
    val dotState = remember { mutableFloatStateOf(0f) }

    val dotSize by animateFloatAsState(
        targetValue = dotState.floatValue,
        animationSpec = keyframes {
        durationMillis = ceil(rotationTime * 1000).toInt()
        0f at 0 with CubicBezierEasing(0.1f, 2f, 0.1f, 0.6f)
        1f at ceil(rotationTime*1000/2).toInt() with CubicBezierEasing(0.9f, 0.4f, 0.9f, -1f)
        0f at ceil(rotationTime*1000).toInt()
        }
    )
    val animDot = dotSize



    val softLightRadius = widthPx / 2
    val softLightGradient = Brush.radialGradient(
        colors = colorScheme.MainButtonSoftLightGradientColors,
        center = buttonCenter,
        radius = softLightRadius
    )

    Canvas(modifier = Modifier //!softLight render
        .size(buttonSize)
    ) {
        drawCircle(
            brush = softLightGradient,
            center = buttonCenter,
            radius = softLightRadius,
            alpha = animAlpha
        )
    }

    Canvas(modifier = Modifier //!button render
        .graphicsLayer(
            translationX = buttonCenter.x - radius,
            translationY = buttonCenter.y - radius,
            scaleX = animScale,
            scaleY = animScale,
            rotationZ = animRotation,
        )
        .size(buttonSize)
        .offset((radius / density).dp, (radius / density).dp)
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

        drawCircle(
            color = colorScheme.MainButtonShadow,
            center = Offset(0f,0f),
            radius = radius+5,
            alpha = if (animAlpha > 0.5f) animAlpha - 0.5f else 0f
        )

        drawPath(path, gradient)
    }

    val logoVector: ImageVector = ImageVector.vectorResource(id = R.drawable.button)
    val scaleMultiplier = 0.81f

    Image(
        imageVector = logoVector,
        contentDescription = null,
        modifier = Modifier
            .graphicsLayer(
                translationX = buttonCenter.x - radius,
                translationY = buttonCenter.y - radius,
                scaleX = animScale * scaleMultiplier,
                scaleY = animScale * scaleMultiplier,
                rotationZ = animRotation
            )
            //.offset((radius / density).dp, (radius / density).dp)
            .size(buttonSize)
    )

    Box(modifier = Modifier //!dot render
        .size(buttonSize / 16)
        .graphicsLayer {
            translationX = buttonCenter.x - buttonSize.toPx() / 32
            translationY = buttonCenter.y - buttonSize.toPx() / 32
            scaleX = animDot
            scaleY = animDot
        }
        .clip(shape = CircleShape)
        .background(color = colorScheme.BackgroundColor)
    )

    Box(modifier = Modifier //!clickabe area
        .size(buttonSize)
        .graphicsLayer {
            translationX = buttonCenter.x
            translationY = buttonCenter.y
        }
        .offset((-radius / density).dp, (-radius / density).dp)
        .clip(shape = CircleShape)
        .clickable {
            rotationState.floatValue =
                if (rotationState.floatValue == 0f) 360f * ceil(rotationSpeed * rotationTime) else 0f
            dotState.floatValue =
                if (dotState.floatValue == 0f) 1f else 0f
        }
    )
}


