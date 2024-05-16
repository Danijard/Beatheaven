import 'package:flutter/widgets.dart';

final double pixelRatio = WidgetsBinding.instance.platformDispatcher.views.first.devicePixelRatio;
final screenHeight = WidgetsBinding.instance.platformDispatcher.views.first.physicalSize.height
     / WidgetsBinding.instance.platformDispatcher.views.first.devicePixelRatio;
final screenWidth = WidgetsBinding.instance.platformDispatcher.views.first.physicalSize.width
     / WidgetsBinding.instance.platformDispatcher.views.first.devicePixelRatio;

final mainButtonRadius = screenWidth / 4;

final mainButtonCenter = Offset(0, -mainButtonRadius);
final mainButtonRightTopCorner = Offset(mainButtonCenter.dx + mainButtonRadius,
    mainButtonCenter.dy - mainButtonRadius);
final mainButtonLeftBottomCorner = Offset(mainButtonCenter.dx - mainButtonRadius,
    mainButtonCenter.dy + mainButtonRadius);

const mainButtonRotationSpeed = 0.7; //rps
const mainButtonRotationTime = 15 * 1000; //milliseconds

const Duration fadeInAnimationDuration = Duration(milliseconds: 5000);
const Duration fadeInAnimationDelay = Duration(milliseconds: 700);
const Cubic fadeInAnimationCurve = Curves.easeOutCubic;
const Duration scaleInAnimationDuration = Duration(milliseconds: 700);
const Duration scaleInAnimationDelay = Duration(milliseconds: 300);
const Cubic scaleInAnimationCurve = Curves.easeInOutSine;
const Duration mainButtonBackBounceDuration = Duration(milliseconds: 1000);

var isActivated = false;

const buttonLogo = 'assets/images/beatheaven_logo_transparent.png';