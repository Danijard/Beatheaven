import 'dart:async';
import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

import '../global_ui_values.dart';
import '../themes/themes.dart';

class BottomSheetWidget extends StatefulWidget {
  const BottomSheetWidget({super.key});

  @override
  State<BottomSheetWidget> createState() => _BottomSheetWidgetState();
}

class _BottomSheetWidgetState extends State<BottomSheetWidget> with SingleTickerProviderStateMixin {
  late StreamSubscription<bool> _onTapSubscription;
  late StreamSubscription<bool> _onAnswerSubscription;

  late AnimationController _controller;
  late Animation<double> _animationSheet;
  late Animation<double> _animationBlur;

  double _animatedTopIdent = 0;
  double _blur = 0;
  bool _isBottomSheetOpen = false;

  @override
  void initState() {
    super.initState();

    _controller = AnimationController(
      duration: bottomSheetAnimationDuration,
      vsync: this,
    );

    _animationSheet = Tween<double>(
      begin: 0,
      end: screenHeight*11/16,
    ).animate(
      CurvedAnimation(
        parent: _controller,
        curve: bottomSheetAnimationCurve,
      ),
    );

    _animationBlur = Tween<double>(
      begin: 0,
      end: 25,
    ).animate(
      CurvedAnimation(
        parent: _controller,
        curve: bottomSheetBlurAnimationCurve,
      ),
    );

    _controller.addListener(() {
      setState(() {
        _animatedTopIdent = _animationSheet.value;
        _blur = _animationBlur.value;
      });
    });

    _controller.addStatusListener((status) {
      if (status == AnimationStatus.dismissed) {
        _isBottomSheetOpen = false;
      } else {
        _isBottomSheetOpen = true;
      }
    });

    TapDetector.instance.onValueChanged.listen((newValue){
      if (_isBottomSheetOpen) {
        _controller.reverse();
      }
    });
    AnswerDetector.instance.onValueChanged.listen((newValue){
      if (!_isBottomSheetOpen) {
        _controller.forward();
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Positioned(
      top: screenHeight - _animatedTopIdent,
      child: Visibility(
        visible: _isBottomSheetOpen,
        child: Container(
          width: screenWidth,
          height: screenHeight*11/16,
          decoration: const BoxDecoration(
            borderRadius: BorderRadius.only(
              topLeft: Radius.circular(20),
              topRight: Radius.circular(20),
            ),
          ),
          child: Stack(
            children: [
              BackdropFilter(filter: ImageFilter.blur(sigmaX: _blur, sigmaY: _blur,),child: const Opacity( opacity:0),),
              Container(
                decoration: BoxDecoration(
                  color: defaultTheme.bottomSheetColor,
                  borderRadius: const BorderRadius.only(
                    topLeft: Radius.circular(20),
                    topRight: Radius.circular(20),
                  ),
                ),
              ),
              const _Content(),
            ]
          ),
        ),
      ),
    );
  }
}

class _Content extends StatefulWidget {
  const _Content();

  @override
  State<_Content> createState() => _ContentState();
}

class _ContentState extends State<_Content> {
  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Transform.translate(
          offset: Offset(4 * screenWidth / 10, 8),
          child: Container(
            height: 8,
            width: screenWidth / 5,
            decoration: BoxDecoration(
              color: defaultTheme.secondaryColor,
              borderRadius: BorderRadius.circular(10),
              boxShadow: [
                BoxShadow(
                  color: defaultTheme.shadowColor,
                  blurRadius: 10,
                  spreadRadius: 1,
                ),
              ],
            ),
          ),
        ),
        Transform.translate(
          offset: const Offset(40, 28),
          child: Stack(
            children: [
              Container(
                height: screenWidth - 80,
                width: screenWidth - 80,
                decoration: BoxDecoration(
                  color: defaultTheme.secondaryColor,
                  borderRadius: BorderRadius.circular(20),
                  boxShadow: [
                    BoxShadow(
                      color: defaultTheme.shadowColor,
                      blurRadius: 10,
                      spreadRadius: 1,
                    ),
                  ],
                ),
              ),
              Transform.translate(
                offset: const Offset(1, 1),
                child: Container(
                  height: screenWidth - 82,
                  width: screenWidth - 82,
                  decoration: BoxDecoration(
                    color: defaultTheme.secondaryColor,
                    borderRadius: BorderRadius.circular(21),
                  ),
                  child: ClipRRect(
                    borderRadius: BorderRadius.circular(20),
                    child: Image.network(
                      coverURl,
                      fit: BoxFit.contain,
                      errorBuilder: (context, error, stackTrace) {
                        return Center(
                          child: Icon(
                            Icons.error,
                            color: Colors.red,
                            size: screenWidth / 5,
                          ),
                        );
                      },
                    ),
                  ),
                ),
              ),
              Transform.translate(
                offset: Offset(0, screenWidth - 80),
                child: Text(
                  foundTitle,
                  style: const TextStyle(
                    fontFamily: 'DelaGothicOne',
                    color: Colors.white,
                    fontSize: 40,
                    fontWeight: FontWeight.w400,
                  ),
                ),
              ),
              Transform.translate(
                offset: Offset(2, screenWidth - 30),
                child: Text(
                  foundAuthor,
                  style: TextStyle(
                    fontFamily: 'Montserrat',
                    color: defaultTheme.secondaryColor,
                    fontSize: 20,
                    fontWeight: FontWeight.w900,
                  ),
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }
}