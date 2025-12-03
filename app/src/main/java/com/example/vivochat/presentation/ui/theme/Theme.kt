package com.example.vivochat.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.vivochat.R


private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xFF121212), // خلفية داكنة موحدة
    surface = Color(0xFF1E1E1E),     // الأسطح
    onBackground = Color.White,
    onSurface = Color.White,
    onPrimary = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFFFFFBFE), // خلفية فاتحة موحدة
    surface = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onPrimary = Color.White
)

// ----------------------------------------------------
// 2. تعريف الخطوط (كما هي في ملفك)
// ----------------------------------------------------
val interFont = FontFamily(
    Font(R.font.inter)
)
val sansFont = FontFamily(
    Font(R.font.plusjakartasans)
)
val kumbuhFont = FontFamily(
    Font(R.font.kumbuhsans)
)
val montserratFont = FontFamily(
    Font(R.font.montserrat)
)

// ----------------------------------------------------
// 3. تعريف الطباعة (Typography) (تم نقله إلى نطاق الملف)
// ----------------------------------------------------
val AppTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = interFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    // يمكنك إضافة أنماط طباعة أخرى (headline, title, label, etc.) هنا
)


// ----------------------------------------------------
// 4. دالة الثيم الرئيسية المُعدّلة
// ----------------------------------------------------
@Composable
fun VivoChatTheme(
    // المعامل الجديد: لتمرير إعداد المستخدم (يحتوي على قيمة محفوظة أو null)
    userPreferenceDarkTheme: Boolean? = null,

    // إعداد الألوان الديناميكية (Dynamic Color)
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    // 1. تحديد قيمة darkTheme الفعلية
    // إذا كان إعداد المستخدم موجوداً، استخدمه. وإلا، استخدم إعدادات النظام.
    val darkTheme: Boolean = userPreferenceDarkTheme ?: isSystemInDarkTheme()

    val colorScheme = when {
        // الألوان الديناميكية (Android 12+)
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        // الألوان المخصصة (إذا كانت الألوان الديناميكية غير متاحة أو غير مفعلة)
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        // استخدام تعريف الطباعة الذي قمنا بإنشائه
        typography = AppTypography,
        content = content
    )
}