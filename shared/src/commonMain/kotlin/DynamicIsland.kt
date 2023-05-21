import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.rounded.FastForward
import androidx.compose.material.icons.rounded.FastRewind
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


const val AnimationDuration = 300

sealed class IslandType(val size: DpSize) {
    object Small : IslandType(DpSize(120.dp, 40.dp))
    object Medium : IslandType(DpSize(200.dp, 40.dp))
    object Large : IslandType(DpSize(350.dp, 230.dp))
}

@Composable
fun DynamicIsland(state: IslandType, modifier: Modifier = Modifier) {

    Box(
        modifier
            .clip(RoundedCornerShape(30.dp))
            .background(Color.Black) //important
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = 0.6f,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        if (state == IslandType.Small) InitialIsland(Modifier.size(state.size))
        else MediaPlayer(Modifier.size(state.size), state != IslandType.Medium)
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalResourceApi::class)
@Composable
private fun MediaPlayer(modifier: Modifier, expended: Boolean = false) {

    val animationData = updateTransitionData(expended)

    Box(
        modifier.background(Color.Black)
    ) {

        Column(
            Modifier.size(animationData.playSize),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painterResource("icon_cover.xml"),
                    "music cover",
                    Modifier
                        .size(animationData.musicImageSize)
                        .clip(RoundedCornerShape(animationData.musicImageCorner))
                )

                Column(
                    Modifier
                        .fillMaxWidth(0.6f)
                        .alpha(animationData.musicNameAlpha)
                ) {
                    Text(
                        "Entropy",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                    Text("Beach Bunny", color = Color.Gray, fontSize = 20.sp)
                }

                Image(
                    Icons.Default.MusicNote,
                    "music icon",
                    Modifier
                        .size(animationData.musicImageSize)
                        .rotate(animationData.musicNoteRotate)
                        .scale(animationData.musicNoteScale),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }

            AnimatedVisibility(
                visible = expended,
                enter = scaleIn(animationSpec = tween(500, AnimationDuration)),
            ) {
                if (expended) {
                    PlayerController(Modifier.fillMaxWidth())
                }
            }
        }

    }
}

@Composable
private fun PlayerController(modifier: Modifier) {

    Column(modifier) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("3:00", color = Color.White)
            LinearProgressIndicator(
                progress = 0.75f,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(10.dp)
                    .fillMaxWidth(0.6f),
                color = Color.White,
                trackColor = Color.Gray,
            )
            Text("-1:00", color = Color.White)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                Icons.Rounded.FastRewind, "", colorFilter = ColorFilter.tint(Color.White)
            )
            Image(
                Icons.Rounded.Pause, "", colorFilter = ColorFilter.tint(Color.White)
            )
            Image(
                Icons.Rounded.FastForward, "", colorFilter = ColorFilter.tint(Color.White)
            )

        }
    }
}


@Composable
fun InitialIsland(modifier: Modifier) {
    Spacer( modifier.background(Color.Black))
}

//@Preview
//@Composable
//fun PreviewExpendedMediaPlay() {
//    MediaPlayer(Modifier.size(IslandType.Large.size), true)
//}
//
//@Preview
//@Composable
//fun PreviewCollapsedMediaPlay() {
//    MediaPlayer(Modifier.size(IslandType.Medium.size), false)
//}
//
//
//@Preview
//@Composable
//fun PreviewInitialIsland() {
//    InitialIsland(Modifier.size(IslandType.Small.size))
//}
