package com.natan.shamilov.shmr25.common.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
object AppThemeImpl : AppTheme {

//    @Stable
//    private val fontFamily = FontFamily(
//        Font(R.font.sf_pro_regular, FontWeight.Normal),
//        Font(R.font.sf_pro_medium, FontWeight.Medium),
//        Font(R.font.sf_pro_semibold, FontWeight.SemiBold),
//        Font(R.font.sf_pro_bold, FontWeight.Bold)
//    )

    @Stable
    override val colors = AppColors(
        primary = PrimaryGreen,
        secondary = SecondaryGreen,
        borderGrey = BorderGrey
    )

//    @Stable
//    override val shapes = AppShapes(
//        small = RoundedCornerShape(16.dp),
//        medium = RoundedCornerShape(24.dp),
//        circle = CircleShape
//    )
//
//    @Stable
//    override val typography = AppTypography(
//        bodyLarge = TextStyle(
//            fontFamily = fontFamily,
//            fontWeight = FontWeight.Normal,
//            fontSize = 16.sp,
//            lineHeight = 24.sp,
//            letterSpacing = 0.5.sp
//        ),
//        textSplash = TextStyle(
//            fontFamily = fontFamily,
//            fontWeight = FontWeight.SemiBold,
//            fontSize = 18.sp,
//            lineHeight = 24.sp,
//            color = White
//        ),
//        button = TextStyle(
//            fontFamily = fontFamily,
//            fontWeight = FontWeight.Bold,
//            fontSize = 18.sp,
//            lineHeight = 24.sp,
//            color = White
//        ),
//        title = TextStyle(
//            fontFamily = fontFamily,
//            fontWeight = FontWeight.ExtraBold,
//            fontSize = 36.sp,
//            lineHeight = 36.sp,
//            color = Black,
//            textAlign = TextAlign.Center
//        ),
//        body = TextStyle(
//            fontFamily = fontFamily,
//            fontWeight = FontWeight.Normal,
//            fontSize = 18.sp,
//            lineHeight = 24.sp,
//            color = Black,
//            textAlign = TextAlign.Center
//        ),
//        bodyBold = TextStyle(
//            fontFamily = fontFamily,
//            fontWeight = FontWeight.Bold,
//            fontSize = 18.sp,
//            lineHeight = 24.sp,
//            color = Black,
//            textAlign = TextAlign.Center
//        ),
//        chip = TextStyle(
//            fontFamily = fontFamily,
//            fontWeight = FontWeight.SemiBold,
//            fontSize = 16.sp,
//            lineHeight = 22.sp,
//            textAlign = TextAlign.Center,
//        ),
//        bottom = TextStyle(
//            fontFamily = fontFamily,
//            fontWeight = FontWeight.Bold,
//            fontSize = 11.sp,
//            lineHeight = 11.sp,
//            color = Blue,
//            textAlign = TextAlign.Center
//        ),
//        pro = TextStyle(
//            fontFamily = fontFamily,
//            fontWeight = FontWeight.Bold,
//            fontSize = 16.sp,
//            lineHeight = 16.sp,
//            color = Black,
//            textAlign = TextAlign.Center
//        ),
//        titleSmall = TextStyle(
//            fontFamily = fontFamily,
//            fontWeight = FontWeight.ExtraBold,
//            fontSize = 22.sp,
//            lineHeight = 28.sp,
//            color = Black,
//            textAlign = TextAlign.Center
//        ),
//        proBanner = TextStyle(
//            fontFamily = fontFamily,
//            fontWeight = FontWeight.Bold,
//            fontSize = 26.sp,
//            lineHeight = 26.sp,
//            color = White,
//            textAlign = TextAlign.Center
//        ),
//        description = TextStyle(
//            fontFamily = fontFamily,
//            fontWeight = FontWeight.Normal,
//            fontSize = 14.sp,
//            lineHeight = 15.sp,
//            color = White,
//        ),
//        descriptionBold = TextStyle(
//            fontFamily = fontFamily,
//            fontWeight = FontWeight.Bold,
//            fontSize = 14.sp,
//            lineHeight = 15.sp,
//            color = White,
//        ),
//        banerDescription = TextStyle(
//            fontFamily = fontFamily,
//            fontWeight = FontWeight.Normal,
//            fontSize = 16.sp,
//            lineHeight = 22.sp,
//            color = White,
//        ),
//    )

//    @Stable
//    override val elevations: AppElevations = AppElevations(
//        medium = 4f
//    )
}
