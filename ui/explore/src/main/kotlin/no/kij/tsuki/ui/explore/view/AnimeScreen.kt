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

package no.kij.tsuki.ui.explore.view

import android.annotation.SuppressLint
import android.icu.text.CaseMap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.collections.immutable.ImmutableList
import no.kij.tsuki.ui.base.component.Banner
import no.kij.tsuki.ui.base.component.TsukiPullRefresh
import no.kij.tsuki.ui.base.component.media.MediaSmall
import no.kij.tsuki.ui.base.component.media.MediaSmallRow
import no.kij.tsuki.ui.base.design.spacing
import no.kij.tsuki.ui.explore.R
import no.kij.tsuki.ui.explore.entity.ExploreListItem
import no.kij.tsuki.ui.explore.navigation.ExploreNavigator
import no.kij.tsuki.ui.explore.viewmodel.ExploreViewModel
import org.orbitmvi.orbit.compose.collectAsState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun ExploreScreen(
    navigator: ExploreNavigator, vm: ExploreViewModel = hiltViewModel()
) {
    val state by vm.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            Banner()
        }
    ) {
        TsukiPullRefresh(loading = state.isLoading, onRefresh = vm::refreshList, Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(0.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraMediumSpace)
            ) {
                ShowList(
                    title = stringResource(id = R.string.trending), items = state.trending, navigator = navigator
                )
                ShowList(
                    title = stringResource(id = R.string.popular_this_season),
                    items = state.popularSeason,
                    navigator = navigator
                )
                ShowList(
                    title = stringResource(id = R.string.upcoming_next_season),
                    items = state.upcoming,
                    navigator = navigator
                )
                ShowList(
                    title = stringResource(id = R.string.all_time_popular),
                    items = state.allTimePopular,
                    navigator = navigator
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmallSpace))
            }
        }
    }
}

@Composable
private fun ShowList(title: String, items: ImmutableList<ExploreListItem.AnimeListItem>, navigator: ExploreNavigator) {
    if (!items.isEmpty()) {
        ExploreRow(list = items, title = title, onItemClicked = { item ->
            navigator.openMedia(
                item.mediaId, ExploreNavigator.From.ANIME
            )
        })
    }
}

@Composable
private fun ExploreRow(
    list: List<ExploreListItem.AnimeListItem>,
    title: String,
    onItemClicked: (ExploreListItem.AnimeListItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(
                start = MaterialTheme.spacing.extraMediumSpace
            )
        )
        Spacer(Modifier.size(MaterialTheme.spacing.mediumSpace))
        MediaSmallRow(mediaList = list, content = { media ->
            MediaSmall(
                image = media.cover,
                label = media.title,
                onClick = { onItemClicked(media) },
                modifier = Modifier.width(MaterialTheme.spacing.mediaCardWidth)
            )
        })
    }
}