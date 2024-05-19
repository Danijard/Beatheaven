import 'dart:math';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import '../global_ui_values.dart';
import '../themes/themes.dart';

class RotationCurve extends Curve {
  const RotationCurve();

  @override
  double transformInternal(double t) {
    return 3 * pow((1-t), 2) * t * -0.5 + 3 * (1-t) * t*t + t*t*t;
  }
}


class RecordButton extends StatefulWidget {
  const RecordButton({super.key});

  @override
  RecordButtonState createState() => RecordButtonState();
}

class RecordButtonState extends State<RecordButton> with TickerProviderStateMixin {
  late AnimationController _controllerBouncer;
  late AnimationController _controllerRotator;
  late Animation<double> _animationBouncer;
  late Animation<double> _animationRotator;
  double _angle = 0.0;
  double _yButtonOffset = 0.0;

  @override
  void initState() {
    super.initState();

    _controllerRotator = AnimationController(
      duration: const Duration(milliseconds: mainButtonRotationTime),
      vsync: this,
    );

    _controllerRotator.addListener(() {
      setState(() {
        _angle = _animationRotator.value;
      });
    });

    _controllerRotator.addStatusListener((status) {
      if (status == AnimationStatus.completed) {
        _controllerRotator.reset();
      }
    });

    _controllerBouncer = AnimationController(
      duration: mainButtonBackBounceDuration,
      vsync: this,
    );

    _controllerBouncer.addListener(() {
      setState(() {
        _yButtonOffset = _animationBouncer.value;
      });
      if (_controllerBouncer.value >= 0.7) {
        _controllerBouncer.reset();
        _yButtonOffset = 0.0;
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return AnimatedBuilder(
      animation: _controllerBouncer,
      builder: (context, _) {
        return Positioned(
            top: screenHeight / 2 - mainButtonRadius * 2 + _yButtonOffset*(1-(_yButtonOffset*2/(screenHeight*3))),
            left: screenWidth / 2 - mainButtonRadius,
            child: Stack(
              children: [
                Transform.rotate(
                    angle: _yButtonOffset/200+_angle/57.3,
                    origin: Offset(-screenWidth/2+mainButtonRadius, -screenHeight/2+mainButtonRadius),
                    child: const ButtonWidget()),
                ClipOval(
                  child: SizedBox(
                    width: mainButtonRadius * 2,
                    height: mainButtonRadius * 2,
                    child: GestureDetector(
                      onTap: () {
                        TapDetector.instance.value = !TapDetector.instance.value;
                        _controllerRotator.forward();
                        _animationRotator = Tween<double>(begin: 0, end: 3600).animate(CurvedAnimation(
                            parent: _controllerRotator,
                            curve: const RotationCurve()
                        ));
                      },
                      onPanUpdate: (tapInfo) {
                        TapDetector.instance.value = !TapDetector.instance.value;
                        final touchPosition = tapInfo.delta;
                        if (_yButtonOffset + touchPosition.dy > screenHeight / 2 - mainButtonRadius) {
                          setState(() {
                            _yButtonOffset = screenHeight / 2 - mainButtonRadius;
                          });
                        }
                        if (_yButtonOffset + touchPosition.dy > 0) {
                          setState(() {
                            _yButtonOffset += touchPosition.dy;
                          });
                        }
                      },
                      onPanEnd: (tapInfo) {
                        _controllerBouncer.forward();
                        _animationBouncer = Tween<double>(begin: _yButtonOffset, end: 0.0).animate(CurvedAnimation(
                            parent: _controllerBouncer,
                            curve: Curves.elasticOut
                        ));
                      }
                    ),
                  ),
                ),
              ],
            )
        );
      }
    );
  }
}

class ButtonWidget extends StatelessWidget {
  const ButtonWidget({super.key});

  @override
  Widget build(BuildContext context) {
    return const Stack(
      children: [
        _SoftlightGradientLayer(),
        _ButtonLayer(),
        _ImageLayer()
      ],
    );
  }
}

class _ButtonLayer extends StatefulWidget {
  const _ButtonLayer();

  @override
  _ButtonLayerState createState() => _ButtonLayerState();
}

class _ButtonLayerState extends State<_ButtonLayer> {
  double _scale = 0.0;

  @override
  void initState() {
    super.initState();
    Future.delayed(scaleInAnimationDelay, () {
      setState(() {
        _scale = 1.0;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return AnimatedScale(
      scale: _scale,
      curve: scaleInAnimationCurve,
      duration: scaleInAnimationDuration,
      child: Container(
        width: mainButtonRadius * 2,
        height: mainButtonRadius * 2,
        decoration: BoxDecoration(
          boxShadow: [
            BoxShadow(
              color: defaultTheme.backgroundColor,
              blurRadius: 3,
              spreadRadius: 1,
            ),
          ],
          shape: BoxShape.circle,
          gradient: LinearGradient(
            stops: const [0.15 , 1.0],
            begin: Alignment.topRight,
            end: Alignment.bottomLeft,
            colors: defaultTheme.mainButtonGradientColors,
          ),
        ),
      )
    );
  }
}

class _SoftlightGradientLayer extends StatefulWidget {
  const _SoftlightGradientLayer();

  @override
  _SoftlightGradientLayerState createState() => _SoftlightGradientLayerState();
}

class _SoftlightGradientLayerState extends State<_SoftlightGradientLayer> {
  double _opacity = 0.0;

  @override
  void initState() {
    super.initState();
    Future.delayed(fadeInAnimationDelay, () {
      setState(() {
        _opacity = 1.0;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Transform.translate(
      offset: Offset(-mainButtonRadius, -screenHeight/2+mainButtonRadius),
      child: AnimatedOpacity(
        opacity: _opacity,
        curve: fadeInAnimationCurve,
        duration: fadeInAnimationDuration,
        child: Container(
          width: screenWidth,
          height: screenHeight,
          decoration: BoxDecoration(
            gradient: RadialGradient(
              stops: const [0.5, 1.0],
              center: Alignment.center,
              colors: defaultTheme.mainButtonSoftLightGradientColors,
            ),
          ),
        ),
      ),
    );
  }
}

class _ImageLayer extends StatefulWidget {
  const _ImageLayer();

  @override
  _ImageLayerState createState() => _ImageLayerState();
}

class _ImageLayerState extends State<_ImageLayer> {
  double _scale = 0.0;

  @override
  void initState() {
    super.initState();
    Future.delayed(scaleInAnimationDelay, () {
      setState(() {
        _scale = 1.0;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return AnimatedScale(
        scale: _scale,
        curve: scaleInAnimationCurve,
        duration: scaleInAnimationDuration,
        child: Stack(
            children: [
              Image.asset(
                buttonLogo,
                width: mainButtonRadius * 2,
                height: mainButtonRadius * 2,
              ),
            ]
        )
    );
  }
}