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

package no.kij.tsuki.ui.main.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import no.kij.tsuki.R

@Composable
internal fun SessionExpiredDialog(
    visible: Boolean,
    onAccept: () -> Unit
) {
    if (visible) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = onAccept) {
                    Text(text = stringResource(id = R.string.session_expired_error_confirm))
                }
            },
            title = { Text(text = stringResource(id = R.string.session_expired_error_title))},
            text = { Text(text = stringResource(id = R.string.session_expired_error_description)) },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        )
    }
}