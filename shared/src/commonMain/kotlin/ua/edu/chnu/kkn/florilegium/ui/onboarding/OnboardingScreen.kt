package ua.edu.chnu.kkn.florilegium.ui.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import florilegium.shared.generated.resources.Res
import florilegium.shared.generated.resources.onboarding_counter
import florilegium.shared.generated.resources.onboarding_get_started
import florilegium.shared.generated.resources.onboarding_next
import florilegium.shared.generated.resources.onboarding_page_indicator
import florilegium.shared.generated.resources.onboarding_skip
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import ua.edu.chnu.kkn.florilegium.ui.common.ObserveEvents
import ua.edu.chnu.kkn.florilegium.ui.theme.Florilegium
import ua.edu.chnu.kkn.florilegium.ui.theme.FlorilegiumTheme
import ua.edu.chnu.kkn.florilegium.ui.theme.Space
import kotlin.math.absoluteValue

private val PlateHeight = 268.dp
private val IllustrationSize = 216.dp
private val DotSize = 8.dp
private val ActiveDotWidth = 26.dp
private const val OffscreenIllustrationScale = 0.8f

@Composable
internal fun OnboardingScreen(
    onFinishOnboarding: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = koinViewModel()
) {
    val pages = OnboardingPage.entries
    val pagerState = rememberPagerState { pages.size }
    val scope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == pages.lastIndex

    ObserveEvents(viewModel.events) { event ->
        when (event) {
            is OnboardingEvent.OnOnboardingPassed -> onFinishOnboarding()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Space.x4, vertical = Space.x3),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(
                    Res.string.onboarding_counter,
                    (pagerState.currentPage + 1).toString().padStart(2, '0'),
                    pages.size.toString().padStart(2, '0'),
                ),
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 12.sp, letterSpacing = 2.4.sp),
                color = Florilegium.colors.stroke,
            )
            TextButton(
                onClick = { viewModel.onAction(OnboardingAction.OnboardingPassed) },
                contentPadding = PaddingValues(horizontal = Space.x2, vertical = Space.x1),
            ) {
                Text(
                    text = stringResource(Res.string.onboarding_skip),
                    style = MaterialTheme.typography.bodySmall,
                    color = Florilegium.colors.muted,
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f).fillMaxWidth(),
        ) { index ->
            OnboardingPageContent(
                page = pages[index],
                pageOffset = { pagerState.pageOffset(index) },
            )
        }

        Column(
            modifier = Modifier.padding(start = Space.x6, end = Space.x6, top = Space.x4, bottom = Space.x8),
            verticalArrangement = Arrangement.spacedBy(Space.x4),
        ) {
            PageIndicator(
                pageCount = pages.size,
                currentPage = pagerState.currentPage,
                onSelect = { scope.launch { pagerState.animateScrollToPage(it) } },
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedButton(
                onClick = {
                    if (isLastPage) viewModel.onAction(OnboardingAction.OnboardingPassed) else scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, Florilegium.colors.stroke),
                contentPadding = PaddingValues(Space.x4),
            ) {
                Text(
                    text = stringResource(if (isLastPage) Res.string.onboarding_get_started else Res.string.onboarding_next).uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    color = Florilegium.colors.accent,
                )
            }
        }
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage, pageOffset: () -> Float, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Space.x6),
        verticalArrangement = Arrangement.spacedBy(Space.x6, Alignment.CenterVertically),
    ) {
        IllustrationPlate(page, pageOffset)
        Column(verticalArrangement = Arrangement.spacedBy(Space.x3)) {
            Text(
                text = stringResource(page.title),
                style = MaterialTheme.typography.displaySmall.copy(lineHeight = 38.sp),
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = stringResource(page.body),
                style = MaterialTheme.typography.bodyMedium.copy(hyphens = Hyphens.Auto),
                color = Florilegium.colors.muted,
                textAlign = TextAlign.Justify,
            )
        }
    }
}

private fun PagerState.pageOffset(page: Int): Float =
    ((currentPage - page) + currentPageOffsetFraction).absoluteValue.coerceAtMost(1f)

@Composable
private fun IllustrationPlate(page: OnboardingPage, pageOffset: () -> Float) {
    val shape = MaterialTheme.shapes.medium
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(PlateHeight)
            .graphicsLayer {
                val offset = pageOffset()
                alpha = 1f - offset
                val scale = 1f - (1f - OffscreenIllustrationScale) * offset
                scaleX = scale
                scaleY = scale
            }
            .background(Florilegium.colors.soft, shape)
            .border(1.dp, Florilegium.colors.line, shape)
            .padding(Space.x2)
            .border(1.dp, Florilegium.colors.hair, RoundedCornerShape(2.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(page.illustration),
            contentDescription = null,
            modifier = Modifier.size(IllustrationSize),
        )
    }
}

@Composable
private fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Space.x2, Alignment.CenterHorizontally),
    ) {
        repeat(pageCount) { index ->
            val isCurrent = index == currentPage
            val width by animateDpAsState(if (isCurrent) ActiveDotWidth else DotSize)
            val fill by animateColorAsState(if (isCurrent) Florilegium.colors.stroke else Color.Transparent)
            val description = stringResource(Res.string.onboarding_page_indicator, index + 1, pageCount)
            Box(
                modifier = Modifier
                    .size(width = width, height = DotSize)
                    .clip(CircleShape)
                    .background(fill)
                    .border(1.dp, Florilegium.colors.stroke, CircleShape)
                    .clickable(role = Role.Tab) { onSelect(index) }
                    .semantics {
                        contentDescription = description
                        selected = isCurrent
                    },
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    FlorilegiumTheme {
        OnboardingScreen(onFinishOnboarding = {})
    }
}
