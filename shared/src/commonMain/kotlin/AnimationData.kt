import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp


@Composable
private inline fun <S> Transition<S>.animateDpSize(
    noinline transitionSpec: @Composable Transition.Segment<S>.() -> FiniteAnimationSpec<DpSize> =
        { spring(visibilityThreshold = DpSize(1.dp, 1.dp)) },
    label: String = "IntSizeAnimation",
    targetValueByState: @Composable (state: S) -> DpSize
): State<DpSize> =
    animateValue(DpSizeToVector, transitionSpec, label, targetValueByState)


private val DpSizeToVector: TwoWayConverter<DpSize, AnimationVector2D> =
    TwoWayConverter(
        { AnimationVector2D(it.width.value, it.height.value) },
        { DpSize(it.v1.dp, it.v2.dp) }
    )


data class AnimationData(
    val playSize: DpSize,
    val musicImageSize: Dp,
    val musicImageCorner: Dp,
    val musicNameAlpha: Float,
    val musicNoteRotate: Float,
    val musicNoteScale: Float
)

@Composable
fun updateTransitionData(expended: Boolean): AnimationData {

    val transition = updateTransition(label = "MediaPlayer", targetState = expended)
    val infiniteTransition = rememberInfiniteTransition()

    val playerSize by transition.animateDpSize(
        label = "MediaPlayerSize",
        transitionSpec = { tween(AnimationDuration) }) {
        if (it) IslandType.Large.size else IslandType.Medium.size
    }

    val musicImageSize by transition.animateDp(
        label = "MusicImageSize",
        transitionSpec = { tween(AnimationDuration) }) {
        if (it) 64.dp else 32.dp
    }

    val musicNameAlpha by transition.animateFloat(label = "MusicNameAlpha",
        transitionSpec = { tween(AnimationDuration * 2) }) {
        if (it) 1f else 0f
    }

    val musicImageCorner by transition.animateDp(
        label = "MusicImageCorner",
        transitionSpec = { tween(AnimationDuration) }) {
        if (it) 12.dp else 32.dp
    }

    val musicNoteRotate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val musicNoteScale by infiniteTransition.animateFloat(
        initialValue = if (expended) 1.1f else 1f,
        targetValue = if (expended) 0.5f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        )
    )


    return AnimationData(
        playerSize,
        musicImageSize,
        musicImageCorner,
        musicNameAlpha,
        musicNoteRotate,
        musicNoteScale
    )
}

