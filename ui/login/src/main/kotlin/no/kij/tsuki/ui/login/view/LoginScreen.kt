/*
 * Copyright (C) 2022 Kissor Jeyabalan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package no.kij.tsuki.ui.login.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import no.kij.tsuki.core.common.zero
import no.kij.tsuki.ui.base.design.spacing
import no.kij.tsuki.ui.login.ANILIST_LOGIN
import no.kij.tsuki.ui.login.ANILIST_SIGN_UP
import no.kij.tsuki.ui.login.BACKGROUND_ALPHA
import no.kij.tsuki.ui.login.BOTTOM_ANIM_DELAY
import no.kij.tsuki.ui.login.BOTTOM_ANIM_DURATION
import no.kij.tsuki.ui.login.BOTTOM_ARROW_ANIM_DURATION
import no.kij.tsuki.ui.login.BOTTOM_CROSSFADE_ANIM_DURATION
import no.kij.tsuki.ui.login.HEADER_ANIMATION_DELAY
import no.kij.tsuki.ui.login.HEADER_ANIMATION_DURATION
import no.kij.tsuki.ui.login.LOGIN_DEEP_LINK
import no.kij.tsuki.ui.login.R
import no.kij.tsuki.ui.login.navigation.LoginNavigator
import no.kij.tsuki.ui.login.viewmodel.LoginState
import no.kij.tsuki.ui.login.viewmodel.LoginViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Destination(
    deepLinks = [
        DeepLink(uriPattern = LOGIN_DEEP_LINK)
    ]
)
@Composable
internal fun Login(
    navigator: LoginNavigator,
    vm: LoginViewModel = hiltViewModel()
) {
    val state by vm.collectAsState()

    Login(
        state = state,
        onLogin = navigator::toHome
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Login(state: LoginState, onLogin: () -> Unit) {
    var loading by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    when {
        state.saved -> onLogin()
        state.loading -> loading = true
        state.error != null -> {
            loading = false
            val loginError = stringResource(id = state.error)
            LaunchedEffect(state.error) {
                scope.launch {
                    snackbarHostState.showSnackbar(loginError)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (loading) {
                Loading()
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_login_bg),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.alpha(BACKGROUND_ALPHA)
                )

                Box(modifier = Modifier.padding(MaterialTheme.spacing.extraMediumSpace)) {
                    Header(modifier = Modifier.align(Alignment.TopCenter))
                    Bottom(modifier = Modifier.align(Alignment.BottomCenter))
                }
            }
        }
    }
}

@Composable
private fun Loading() {
    Box {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
internal fun Header(modifier: Modifier = Modifier) {
    Animate(
        delayMillis = HEADER_ANIMATION_DELAY,
        durationMillis = HEADER_ANIMATION_DURATION,
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "つき", fontSize = MaterialTheme.typography.displayLarge.fontSize )
        }
    }
}

@Composable
internal fun Bottom(modifier: Modifier = Modifier) {
    var currentState by rememberSaveable {
        mutableStateOf(State.GetStarted)
    }

    Animate(
        delayMillis = BOTTOM_ANIM_DELAY,
        durationMillis = BOTTOM_ANIM_DURATION,
        modifier = modifier
    ) {
        Crossfade(
            targetState = currentState,
            animationSpec = tween(durationMillis = BOTTOM_CROSSFADE_ANIM_DURATION)
        ) { state ->
            when (state) {
                State.GetStarted -> GetStarted { changedState ->
                    currentState = changedState
                }

                State.Buttons -> LoginSection()
            }
        }
    }
}

@Composable
private fun GetStarted(onStartClicked: (State) -> Unit) {
    Column {
        GetStartedDescription()
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.smallSpace))
        GetStartedButton(onStartClicked)
    }
}

@Composable
private fun GetStartedDescription() {
    Text(
        text = stringResource(id = R.string.get_started_description),
        textAlign = TextAlign.Justify
    )
}

@Composable
private fun GetStartedButton(onStartClicked: (State) -> Unit) {
    val inlineArrow = "inlineArrowContent"
    val text = buildAnnotatedString {
        append(stringResource(id = R.string.get_started_button))
        append(' ')
        appendInlineContent(inlineArrow)
    }

    val translation by rememberInfiniteTransition().animateValue(
        initialValue = Int.zero.dp,
        targetValue = 5.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = BOTTOM_ARROW_ANIM_DURATION,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
    )

    val inlineContent = mapOf(
        inlineArrow to InlineTextContent(
            Placeholder(
                width = MaterialTheme.typography.headlineSmall.fontSize,
                height = MaterialTheme.typography.headlineSmall.fontSize,
                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
            ),
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.offset(x = translation),
                tint = MaterialTheme.colorScheme.onSurface,
            )
        },
    )

    TextButton(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = { onStartClicked(State.Buttons) }
    ) {
        Text(
            text = text,
            inlineContent = inlineContent,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun LoginSection() {
    Column {
        LoginDescription()
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.smallSpace))
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            val buttonWidth = maxWidth / 2 - MaterialTheme.spacing.mediumSpace
            BeginRegisterButton(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .width(buttonWidth)
            )
            BeginLoginButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(buttonWidth)
            )
        }
    }
}

@Composable
private fun LoginDescription() {
    Text(
        text = stringResource(id = R.string.begin_description),
        textAlign = TextAlign.Justify
    )
}

@Composable
private fun BeginRegisterButton(modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current

    Button(
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
        onClick = { uriHandler.openUri(ANILIST_SIGN_UP) }
    ) {
        Text(
            text = stringResource(id = R.string.begin_register_button),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun BeginLoginButton(modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current

    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        onClick = { uriHandler.openUri(ANILIST_LOGIN) }
    ) {
        Text(
            text = stringResource(id = R.string.begin_login_button),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun Animate(
    delayMillis: Int,
    durationMillis: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var animationFinished by rememberSaveable { mutableStateOf(false) }
    val animationVisibleState = remember { MutableTransitionState(animationFinished) }.apply {
        targetState = true
    }

    LaunchedEffect(Unit) {
        animationFinished = true
    }

    AnimatedVisibility(
        modifier = modifier,
        visibleState = animationVisibleState,
        enter = slideInVertically(
            animationSpec = tween(
                delayMillis = delayMillis,
                durationMillis = durationMillis
            ),
        ) { height -> height / 2 } + fadeIn(
            animationSpec = tween(
                delayMillis = delayMillis,
                durationMillis = durationMillis
            ),
            initialAlpha = Float.zero
        )
    ) {
        content()
    }
}

private enum class State {
    GetStarted,
    Buttons
}