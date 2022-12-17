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

package no.kij.tsuki.ui.login

internal const val LOGIN_DEEP_LINK_TOKEN = "token"
internal const val LOGIN_DEEP_LINK = "tsuki://app#access_token={$LOGIN_DEEP_LINK_TOKEN}"

internal const val ANILIST_LOGIN = "https://anilist.co/api/v2/oauth/authorize?client_id=10409&response_type=token"
internal const val ANILIST_SIGN_UP = "https://anilist.co/signup"

internal const val BACKGROUND_ALPHA = .3f

internal const val HEADER_ANIMATION_DELAY = 300
internal const val HEADER_ANIMATION_DURATION = 1250

internal const val BOTTOM_ANIM_DELAY = HEADER_ANIMATION_DELAY + HEADER_ANIMATION_DURATION / 2
internal const val BOTTOM_ANIM_DURATION = HEADER_ANIMATION_DURATION
internal const val BOTTOM_ARROW_ANIM_DURATION = 750
internal const val BOTTOM_CROSSFADE_ANIM_DURATION = 800