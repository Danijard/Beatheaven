import 'dart:async';
import 'dart:ui';

import 'package:beatheaven/ui/global_ui_values.dart';
import 'package:beatheaven/ui/themes/themes.dart';
import 'package:beatheaven/ui/widgets/bottomsheet.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import 'ui/widgets/main_button.dart';

void main() {
  runApp(const BeatheavenApp());
}

class BeatheavenApp extends StatelessWidget {
  const BeatheavenApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      debugShowCheckedModeBanner: false,
      home: MainPage(),
    );
  }
}

class MainPage extends StatelessWidget {
  const MainPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: defaultTheme.backgroundColor,
      body: const Stack(
        children: [
          EverywhereTapDetector(),
          RecordButton(),
          BottomSheetWidget(),
          DebugButton(),
        ]
      ),
    );
  }
}

class EverywhereTapDetector extends StatelessWidget {
  const EverywhereTapDetector({super.key});

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: screenWidth,
      height: screenHeight,
      child: Center(
        child: GestureDetector(
          onTap: () {
            TapDetector.instance.value = !TapDetector.instance.value;
          },
          child: Container(
            color: defaultTheme.backgroundColor,
          ),
        ),
      ),
    );
  }
}

class DebugButton extends StatelessWidget {
  const DebugButton({super.key});

  @override
  Widget build(BuildContext context) {
    return Positioned(
      top: 0,
      left: 0,
      child: GestureDetector(
        onTap: () {
          TapDetector.instance.value = !TapDetector.instance.value;
        },
        child: Container(
          width: 50,
          height: 50,
          color: Colors.red,
        ),
      ),
    );
  }
}

class Channel {
  static const platform = MethodChannel('samples.flutter.dev/function');

  static Future<String> callKotlinFunction() async {
    String result;
    try {
      result = await platform.invokeMethod('callFunction');
    } on PlatformException catch (e) {
      result = "Failed to call function: '${e.message}'.";
    }
    return result;
  }
}