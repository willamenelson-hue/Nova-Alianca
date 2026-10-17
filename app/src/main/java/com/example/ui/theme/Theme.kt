package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = WarmGoldLight,
  onPrimary = DeepNavy,
  primaryContainer = RoyalBlueSurface,
  onPrimaryContainer = WarmGoldLight,
  secondary = WarmGoldPrimary,
  onSecondary = DeepNavy,
  secondaryContainer = RoyalBlueLight,
  onSecondaryContainer = Color.White,
  tertiary = AmberAccent,
  onTertiary = DeepNavy,
  background = BackgroundDark,
  onBackground = TextPrimaryDark,
  surface = SurfaceDark,
  onSurface = TextPrimaryDark,
  surfaceVariant = SurfaceVariantDark,
  onSurfaceVariant = TextSecondaryDark,
  outline = Color(0xFF3B4F66)
)

private val LightColorScheme = lightColorScheme(
  primary = RoyalBluePrimary,
  onPrimary = Color.White,
  primaryContainer = Color(0xFFD6E4F5),
  onPrimaryContainer = RoyalBlueDark,
  secondary = WarmGoldDark,
  onSecondary = Color.White,
  secondaryContainer = WarmGoldContainer,
  onSecondaryContainer = Color(0xFF3F2E00),
  tertiary = AmberAccent,
  onTertiary = Color.White,
  background = BackgroundLight,
  onBackground = TextPrimaryLight,
  surface = SurfaceLight,
  onSurface = TextPrimaryLight,
  surfaceVariant = SurfaceVariantLight,
  onSurfaceVariant = TextSecondaryLight,
  outline = Color(0xFFC7D3E0)
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Preservamos a paleta comemorativa da igreja
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
