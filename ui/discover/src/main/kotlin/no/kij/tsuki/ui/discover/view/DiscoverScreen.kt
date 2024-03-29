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

package no.kij.tsuki.ui.discover.view

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import no.kij.tsuki.core.common.unknown
import no.kij.tsuki.ui.base.design.MinContrastOfPrimaryVsSurface
import no.kij.tsuki.ui.base.design.contrastAgainst
import no.kij.tsuki.ui.base.design.verticalGradientScrim
import no.kij.tsuki.ui.base.util.BlurTransformation
import no.kij.tsuki.ui.base.util.DynamicThemePrimaryColorsFromImage
import no.kij.tsuki.ui.base.util.rememberDominantColorState
import no.kij.tsuki.ui.discover.entity.DiscoverListItem
import no.kij.tsuki.ui.discover.navigation.DiscoverNavigator
import no.kij.tsuki.ui.discover.viewmodel.DiscoverState
import no.kij.tsuki.ui.discover.viewmodel.DiscoverViewModel
import org.orbitmvi.orbit.compose.collectAsState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
internal fun DiscoverScreen(
    navigator: DiscoverNavigator, vm: DiscoverViewModel = hiltViewModel()
) {
    val state by vm.collectAsState()
    Surface(Modifier.fillMaxSize()) {
        DiscoverContent(
            state = state,
            onCategorySelected = vm::onDiscoverCategorySelected,
            navigator = navigator,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverAppBar(
    backgroundColor: Color, modifier: Modifier = Modifier
) {
    TopAppBar(title = {
        Text(text = "つき")
    }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = backgroundColor), actions = {
        IconButton(onClick = { /*TODO: Open search*/ }) {
            Icon(
                imageVector = Icons.Filled.Search, contentDescription = null
            )
        }
    }, modifier = modifier
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun DiscoverContent(
    state: DiscoverState<DiscoverListItem.AnimeListItem>,
    onCategorySelected: (DiscoverCategory) -> Unit,
    navigator: DiscoverNavigator,
    modifier: Modifier
) {
    Column(
        modifier = modifier.windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal))
    ) {
        // dynamically theme sub-section
        val surfaceColor = MaterialTheme.colorScheme.surface
        val dominantColorState = rememberDominantColorState { color ->
            // want color with sufficient contrast against surface color
            color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
        }

        DynamicThemePrimaryColorsFromImage(dominantColorState) {
            val pagerState = rememberPagerState()
            val selectedImageUrl = state.trending.getOrNull(pagerState.currentPage)?.banner ?: state.trending.getOrNull(
                pagerState.currentPage
            )?.cover
            LaunchedEffect(selectedImageUrl) {
                if (selectedImageUrl.isNullOrEmpty()) {
                    dominantColorState.reset()
                } else {
                    dominantColorState.updateColorsFromImageUrl(selectedImageUrl)
                }
            }

            val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
            if (!isDragged) {
                LaunchedEffect(pagerState) {
                    launch {
                        repeat(Int.MAX_VALUE) {
                            delay(3000)
                            with(pagerState) {
                                val target = if (currentPage < pageCount - 1) currentPage + 1 else 0
                                animateScrollToPage(
                                    page = target, animationSpec = tween(
                                        durationMillis = 1000, easing = FastOutSlowInEasing
                                    )
                                )
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalGradientScrim(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
                        startYPercentage = 1f,
                        endYPercentage = 0f
                    )
            ) {
                val appBarColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.38f)
                // scrim over status bar that matches app bar
                Spacer(
                    Modifier
                        .background(appBarColor)
                        .fillMaxWidth()
                        .windowInsetsTopHeight(WindowInsets.statusBars)
                )

                DiscoverAppBar(
                    backgroundColor = appBarColor, modifier = Modifier.fillMaxWidth()
                )

                if (state.trending.isNotEmpty()) {

                    FeaturedMedia(
                        items = state.trending,
                        pagerState = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    Spacer(Modifier.height(16.dp))
                }
            }
        }

        DiscoverCategoryTabs(
            categories = DiscoverCategory.values().toList(),
            selectedCategory = state.selectedCategory,
            onCategorySelected = onCategorySelected
        )

        when (state.selectedCategory) {
            DiscoverCategory.Upcoming -> MediaList(
                state.selectedCategory, state.upcoming,
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            DiscoverCategory.Popular -> MediaList(
                state.selectedCategory, state.popularSeason,
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            DiscoverCategory.AllTime -> MediaList(
                state.selectedCategory, state.allTimePopular,
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}

@Composable
private fun MediaList(
    selectedCategory: DiscoverCategory, items: List<DiscoverListItem.AnimeListItem>, modifier: Modifier = Modifier
) {
    Column(modifier) {
        Spacer(Modifier.height(8.dp))
        Column(Modifier.fillMaxSize()) {
            val lastIndex = items.size - 1
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(start = 24.dp, top = 8.dp, end = 24.dp, bottom = 24.dp)
            ) {
                itemsIndexed(items) { index, anime ->
                    TopMediaRowItem(
                        title = anime.title,
                        image = anime.cover,
                        banner = anime.banner,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (index < lastIndex) Spacer(Modifier.height(18.dp))
                }
            }
        }
    }
}

@Composable
private fun TopMediaRowItem(
    title: String, image: String, banner: String, modifier: Modifier = Modifier
) {
    Column(modifier) {
        Box {
            var sizeImage by remember { mutableStateOf(IntSize.Zero) }
            AsyncImage(model = ImageRequest.Builder(LocalContext.current).data(banner).crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .background(Color.Transparent)
                    .height(190.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .onGloballyPositioned {
                        sizeImage = it.size
                    })
            val gradient = Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Black),
                startY = sizeImage.height.toFloat() / 16,
                endY = sizeImage.height.toFloat()
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .align(Alignment.BottomStart)
                    .background(Color.Transparent)
                    .clip(RoundedCornerShape(16.dp))
                    .background(gradient)
            )
            Row {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(image).crossfade(true).build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 10.dp, top = 10.dp)
                        .width(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Column(
                    Modifier
                        .align(Alignment.Bottom)
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
private fun DiscoverCategoryTabs(
    categories: List<DiscoverCategory>,
    selectedCategory: DiscoverCategory,
    onCategorySelected: (DiscoverCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIndex = categories.indexOfFirst { it == selectedCategory }
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        DiscoverCategoryTabIndicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedIndex])
        )
    }

    TabRow(
        selectedTabIndex = selectedIndex, indicator = indicator, modifier = modifier
    ) {
        categories.forEachIndexed { index, category ->
            Tab(selected = index == selectedIndex, onClick = { onCategorySelected(category) }, text = {
                Text(
                    text = when (category) {
                        DiscoverCategory.Upcoming -> "Upcoming"
                        DiscoverCategory.Popular -> "Season"
                        DiscoverCategory.AllTime -> "Popular"
                    }, style = MaterialTheme.typography.bodyMedium
                )
            })
        }
    }
}

@Composable
internal fun DiscoverCategoryTabIndicator(
    modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.onSurface
) {
    Spacer(
        modifier
            .padding(horizontal = 24.dp)
            .height(4.dp)
            .background(color, RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
    )
}


@ExperimentalPagerApi
@Composable
internal fun FeaturedMedia(
    items: List<DiscoverListItem.AnimeListItem>, pagerState: PagerState, modifier: Modifier = Modifier
) {
    HorizontalPager(
        count = items.size, state = pagerState, modifier = modifier
    ) { page ->
        val media = items[page]
        MediaCarouselItem(
            id = media.mediaId,
            coverUrl = media.cover,
            bannerUrl = media.banner,
            title = media.title,
            format = stringResource(id = media.format.value),
            modifier = Modifier
                .padding(4.dp)
                .fillMaxHeight()
        )
    }
}

@Composable
private fun MediaCarouselItem(
    modifier: Modifier = Modifier,
    id: Int,
    coverUrl: String? = null,
    bannerUrl: String? = null,
    title: String? = null,
    format: String? = null
) {
    Column {
        Box {
            if (bannerUrl != null) {
                var sizeImage by remember { mutableStateOf(IntSize.Zero) }
                AsyncImage(model = ImageRequest.Builder(LocalContext.current).data(bannerUrl).transformations(
                    listOf(BlurTransformation(scale = 0.5f, radius = 20))
                ).build(),
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .onGloballyPositioned {
                            sizeImage = it.size
                        })
                val gradient = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black),
                    startY = sizeImage.height.toFloat() / 3,
                    endY = sizeImage.height.toFloat()
                )
                Box(
                    Modifier
                        .matchParentSize()
                        .background(gradient)
                )
            }
            Row {
                if (coverUrl != null) {
                    AsyncImage(
                        model = coverUrl,
                        contentDescription = title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                            .width(120.dp)
                            .clip(RoundedCornerShape(size = 8.dp))
                    )
                }
                Column(
                    Modifier
                        .align(Alignment.Bottom)
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = title ?: String.unknown,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 0.dp)
                    )
                    Text(
                        text = format ?: String.unknown,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 25.dp)
                    )
                }
            }
        }
    }
}