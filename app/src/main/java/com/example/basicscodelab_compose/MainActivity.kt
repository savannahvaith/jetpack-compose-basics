package com.example.basicscodelab_compose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicscodelab_compose.R.string
import com.example.basicscodelab_compose.ui.theme.BasicsCodelabComposeTheme

// Entry Point
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // onCreate - sets things up for us
        super.onCreate(savedInstanceState)
        // we can call composables inside setContent
        setContent {
            BasicsCodelabComposeTheme {
               MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    // rememberSaveable - rememebers even if configuration changes / i.e. rotation/light-darkmode
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    // Logic to show onboarding or greetings
    if(shouldShowOnboarding){
        OnboardingScreen(onContinueClicked = {shouldShowOnboarding = false})
    }else{
        Greetings()
    }
}

@Composable
fun Greetings(names: List<String> = List(1000){"$it"}){
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
            // only renders what the user can see on the screen.
            LazyColumn {
                items(names) { name -> 
                    Greeting(name = name)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val expanded = remember { mutableStateOf(false) }
    // animateDP as state will return a state.
    val extraPadding by animateDpAsState(
        targetValue = if(expanded.value) 48.dp else 0.dp,
        // will take 2 seconds to run -- make sure animation is not turned off in developer settings.
        animationSpec = tween(durationMillis = 2000), label = ""
    )
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row (modifier = Modifier.padding(24.dp)){
            Column( modifier = Modifier
                .weight(1f) // flexible child, whilst the button isn't
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ){
                Text(text = "Hello," )
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                if (expanded.value) {
                    Text(
                        text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                    )
                }

            }
            IconButton(onClick = { expanded.value = !expanded.value }) {
                Icon(
                    imageVector = if (expanded.value) Filled.ExpandLess else Filled.ExpandMore,
                    contentDescription = if (expanded.value) {
                        stringResource(string.show_less)
                    } else {
                        stringResource(string.show_more)
                    }
                )
            }
        }
    }
}

@Composable
fun OnboardingScreen(
    // Pass a function/callback
    onContinueClicked: () -> Unit
){
//    //  TODO: Hoisting to MyApp()
//    // USing by we are using a delegate in comparison to L58. we don't need the .value
//    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            // center children in the column
            verticalArrangement = Arrangement.Center,
            // every child in the column will be centered
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome to the Basics CodeLab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                // function call back, which will execute when it's clicked.
//                onClick = { shouldShowOnboarding = false}
                onClick = onContinueClicked
            ){
                Text(text = "Continue")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview(){
    BasicsCodelabComposeTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}


@Preview(showBackground = true, widthDp = 320, uiMode = UI_MODE_NIGHT_YES, name = "Dark")
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    BasicsCodelabComposeTheme {
       MyApp()
    }
}
