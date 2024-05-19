import 'dart:ui';

class Themes {
  final Color backgroundColor;
  final Color primaryColor;
  final Color secondaryColor;
  final List<Color> mainButtonGradientColors;
  final List<Color> mainButtonSoftLightGradientColors;
  final Color shadowColor;
  final Color bottomSheetColor;

  Themes({
    required this.bottomSheetColor,
    required this.backgroundColor,
    required this.primaryColor,
    required this.secondaryColor,
    required this.shadowColor,
    required Color mainButtonGradientColor,
  })  : mainButtonGradientColors = [mainButtonGradientColor, primaryColor],
        mainButtonSoftLightGradientColors = [
          mainButtonGradientColor.withOpacity(0.2),
          backgroundColor.withOpacity(0),
          /*const Color(0x00FFFFFF)*/
        ];
}

final defaultTheme = Themes(
  bottomSheetColor: const Color(0xB210645F),
  backgroundColor: const Color(0xFF051622),
  primaryColor: const Color(0xFF1A9F96),
  secondaryColor: const Color(0xFFDEB992),
  mainButtonGradientColor: const Color(0xFF5AB758),
  shadowColor: const Color(0xFF091927),
);
