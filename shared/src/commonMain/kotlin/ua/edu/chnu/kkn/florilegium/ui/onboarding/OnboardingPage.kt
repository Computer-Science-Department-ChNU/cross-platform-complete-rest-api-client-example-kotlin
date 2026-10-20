package ua.edu.chnu.kkn.florilegium.ui.onboarding

import florilegium.shared.generated.resources.Res
import florilegium.shared.generated.resources.onboarding_1_commonplace
import florilegium.shared.generated.resources.onboarding_2_daily_draw
import florilegium.shared.generated.resources.onboarding_3_search_archive
import florilegium.shared.generated.resources.onboarding_4_every_screen
import florilegium.shared.generated.resources.onboarding_commonplace_body
import florilegium.shared.generated.resources.onboarding_commonplace_title
import florilegium.shared.generated.resources.onboarding_daily_body
import florilegium.shared.generated.resources.onboarding_daily_title
import florilegium.shared.generated.resources.onboarding_screens_body
import florilegium.shared.generated.resources.onboarding_screens_title
import florilegium.shared.generated.resources.onboarding_search_body
import florilegium.shared.generated.resources.onboarding_search_title
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class OnboardingPage(
    val illustration: DrawableResource,
    val title: StringResource,
    val body: StringResource,
) {
    Commonplace(Res.drawable.onboarding_1_commonplace, Res.string.onboarding_commonplace_title, Res.string.onboarding_commonplace_body),
    DailyDraw(Res.drawable.onboarding_2_daily_draw, Res.string.onboarding_daily_title, Res.string.onboarding_daily_body),
    SearchArchive(Res.drawable.onboarding_3_search_archive, Res.string.onboarding_search_title, Res.string.onboarding_search_body),
    EveryScreen(Res.drawable.onboarding_4_every_screen, Res.string.onboarding_screens_title, Res.string.onboarding_screens_body),
}
