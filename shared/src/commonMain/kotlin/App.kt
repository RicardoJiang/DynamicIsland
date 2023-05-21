import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import theme.ComposeDynamicIslandTheme

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    ComposeDynamicIslandTheme  {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            var state: IslandType by remember {
                mutableStateOf(IslandType.Small)
            }

            Column(
                Modifier.padding(top = 10.dp, bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                DynamicIsland(state)

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { state = IslandType.Small }) {
                        Text("Small")
                    }
                    Button(onClick = { state = IslandType.Medium }) {
                        Text("Medium")
                    }
                    Button(onClick = { state = IslandType.Large }) {
                        Text("Large")
                    }
                }

            }


        }
    }
}

expect fun getPlatformName(): String