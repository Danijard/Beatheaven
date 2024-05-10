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

const Duration fadeInAnimationDuration = Duration(milliseconds: 2000);
const Duration fadeInAnimationDelay = Duration(milliseconds: 100);
const Duration sizeInAnimationDuration = Duration(milliseconds: 600);
const Duration sizeInAnimationDelay = Duration(milliseconds: 0);


var isActivated = false;

const image = 'assets/images/beatheaven_logo_transparent.png';