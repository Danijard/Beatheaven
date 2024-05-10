
import 'package:beatheaven_flutter/ui/widgets/mainbutton/main_button.dart';
import 'package:flutter/material.dart';
import 'package:beatheaven_flutter/ui/widgets/background.dart';

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
    return const Stack(
      children: [
        Background(),
        MainButton()
      ],
    );
  }
}