import 'package:beatheaven_flutter/ui/themes/themes.dart';
import 'package:beatheaven_flutter/ui/global_ui_values.dart';
import 'package:flutter/widgets.dart';

class MainButton extends StatelessWidget {
  const MainButton({super.key});

  @override
  Widget build(BuildContext context) {
    return const Center(
      child: Stack(
        children: [
          _MainButton(),
          _ButtonSoftlightGradient(),
          _ButtonImage(),
        ],
      ),
    );
  }
}

class _MainButton extends StatefulWidget {
  const _MainButton();

  @override
  _MainButtonState createState() => _MainButtonState();
}

class _MainButtonState extends State<_MainButton> {
  double _scale = 0.0;

  @override
  void initState() {
    super.initState();
    Future.delayed(sizeInAnimationDelay, () {
      setState(() {
        _scale = 1.0;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Transform.translate(
      offset: mainButtonCenter,
      child: Center(
        child: AnimatedScale(
          scale: _scale,
          duration: sizeInAnimationDuration,
          child: Stack(
            children: [
              Container(
                width: mainButtonRadius * 2,
                height: mainButtonRadius * 2,
                decoration: BoxDecoration(
                  shape: BoxShape.circle,
                  gradient: LinearGradient(
                    begin: Alignment.topRight,
                    end: Alignment.bottomLeft,
                    colors: defaultTheme.mainButtonGradientColors,
                  ),
                ),
              ),
            ]
          )
        )
      )
    );
  }
}


class _ButtonSoftlightGradient extends StatefulWidget {
  const _ButtonSoftlightGradient();

  @override
  _ButtonSoftlightGradientState createState() => _ButtonSoftlightGradientState();
}

class _ButtonSoftlightGradientState extends State<_ButtonSoftlightGradient> {
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
    return AnimatedOpacity(
      opacity: _opacity,
      duration: fadeInAnimationDuration,
      child: Transform.translate(
        offset: mainButtonCenter,
        child: Container(
          decoration: BoxDecoration(
            shape: BoxShape.circle,
            gradient: RadialGradient(
              center: Alignment.center,
              colors: defaultTheme.mainButtonSoftLightGradientColors,
            ),
          ),
        ),
      ),
    );
  }
}

class _ButtonImage extends StatefulWidget {
  const _ButtonImage();

  @override
  _ButtonImageState createState() => _ButtonImageState();
}

class _ButtonImageState extends State<_ButtonImage> {
  double _scale = 0.0;

  @override
  void initState() {
    super.initState();
    Future.delayed(sizeInAnimationDelay, () {
      setState(() {
        _scale = 1.0;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Transform.translate(
        offset: mainButtonCenter,
        child: Center(
            child: AnimatedScale(
                scale: _scale,
                duration: sizeInAnimationDuration,
                child: Stack(
                    children: [
                      Image.asset(
                        image,
                        width: mainButtonRadius * 2,
                        height: mainButtonRadius * 2,
                      ),
                    ]
                )
            )
        )
    );
  }
}