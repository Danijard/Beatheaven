import 'package:beatheaven_flutter/ui/global_ui_values.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class TestPolygon extends StatefulWidget {
  const TestPolygon({super.key});

  @override
  TestPolygonState createState() => TestPolygonState();
}

class TestPolygonState extends State<TestPolygon> with SingleTickerProviderStateMixin {
  late AnimationController _controller;
  late Animation<double> _animation;
  double _yPosition = 0.0;

  @override
  void initState() {
    super.initState();

    _controller = AnimationController(
      duration: const Duration(milliseconds: 1000), // duration of animation
      vsync: this,
    ); // animation from 0.0 to 100.0

    _controller.addStatusListener((status) {
      if (status == AnimationStatus.completed) {
        _controller.reset();
        _yPosition = 0.0;
      }
    });

    _controller.addListener(() {
      setState(() {
        _yPosition = _animation.value; // update yPosition with animation value
      });
      print('Controller Value: ${_controller.value}, Animation Value: ${_animation.value}');
    });
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Stack(
        children: [
          Positioned(
              top: _yPosition,
              child: AnimatedBuilder(
                animation: _controller,
                builder: (context, _) {
                  return GestureDetector(
                    onPanUpdate: (tapInfo) {
                      final touchPosition = tapInfo.delta.dy;
                      if (_yPosition + touchPosition > 0) {
                        setState(() {
                          _yPosition += touchPosition;
                        });
                      }
                    },
                    onPanEnd: (tapInfo) {
                      _controller.forward();
                      _animation = Tween<double>(begin: _yPosition, end: 0.0).animate(CurvedAnimation(parent: _controller, curve: Curves.elasticOut));
                    },
                    child: Text(_yPosition.toString()),
                  );
                },
              ),
          ),
        ],
      ),
    );
  }
}