import 'package:beatheaven_flutter/ui/themes/themes.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class Background extends StatelessWidget {
  const Background({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: defaultTheme.backgroundColor,
      ),
    );
  }
}