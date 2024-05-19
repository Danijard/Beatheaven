import 'dart:async';

import 'package:flutter/widgets.dart';

class TapDetector {
  TapDetector._privateConstructor();

  static final TapDetector _instance = TapDetector._privateConstructor();

  static TapDetector get instance => _instance;

  final _controller = StreamController<bool>.broadcast();

  bool _value = false;

  bool get value => _value;

  set value(bool newValue) {
    _value = newValue;
    _controller.sink.add(_value);
  }

  Stream<bool> get onValueChanged => _controller.stream;
}

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

const Duration bottomSheetAnimationDuration = Duration(milliseconds: 300);
const Duration bottomSheetAnimationDelay = Duration(milliseconds: 0);
const Cubic bottomSheetAnimationCurve = Curves.easeInOutExpo;
const Cubic bottomSheetBlurAnimationCurve = Curves.easeInOut;

const Duration mainButtonBackBounceDuration = Duration(milliseconds: 1000);

const buttonLogo = 'assets/images/beatheaven_logo_transparent.png';

var coverURl = 'https://lastfm.freetls.fastly.net/i/u/300x300/46def4fa25d2821f092448be01e639ea.png';
var foundAuthor = 'Автор';
var foundTitle = 'Назавние';