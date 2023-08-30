package com.example.basicscodelab_compose

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    var shouldShowOnboarding by remember { mutableStateOf(true) }
    // Logic to show onboarding or greetings
    if(shouldShowOnboarding){
        OnboardingScreen(onContinueClicked = {shouldShowOnboarding = false})
    }else{
        Greetings()
    }
}

@Composable
fun Greetings(names: List<String> = listOf("World", "Compose")){
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
            for(name in names){
                Greeting(name)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val expanded = remember { mutableStateOf(false) }
    var extraPadding = if(expanded.value) 48.dp else 0.dp
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row (modifier = Modifier.padding(24.dp)){
            Column( modifier = Modifier
                .weight(1f) // flexible child, whilst the button isn't
                .padding(bottom = extraPadding)
            ){
                Text(text = "Hello," )
                Text(text = name)
            }
            Button(onClick = { expanded.value = !expanded.value }) {
                Text(if(expanded.value) "Show Less" else "Show More")
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

@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    BasicsCodelabComposeTheme {
       MyApp()
    }
}
