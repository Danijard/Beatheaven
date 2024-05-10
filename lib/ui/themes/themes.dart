import 'dart:ui';

class Themes {
  final Color backgroundColor;
  final Color primaryColor;
  final Color secondaryColor;
  final List<Color> mainButtonGradientColors;
  final List<Color> mainButtonSoftLightGradientColors;
  final Color mainButtonShadowColor;

  Themes({
    required this.backgroundColor,
    required this.primaryColor,
    required this.secondaryColor,
    required this.mainButtonShadowColor,
    required Color mainButtonGradientColor,
  })  : mainButtonGradientColors = [mainButtonGradientColor, primaryColor],
        mainButtonSoftLightGradientColors = [
          mainButtonGradientColor.withOpacity(0.33),
          const Color(0x00FFFFFF)
        ];
}

final defaultTheme = Themes(
  backgroundColor: const Color(0xFF051622),
  primaryColor: const Color(0xFF1A9F96),
  secondaryColor: const Color(0xFFDEB992),
  mainButtonGradientColor: const Color(0xFF5AB758),
  mainButtonShadowColor: const Color(0xFF091927),
);
