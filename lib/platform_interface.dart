import 'package:beatheaven/ui/global_ui_values.dart';
import 'package:flutter/services.dart';

const android = MethodChannel('com.modilebev.beatheaven/Kotlin');

void tryRecording() {
  try {
    android.invokeMethod('tryRecording');
  } on PlatformException catch (e) {
    print('Failed to record: ${e.message}');
  }
}

void answer() {
  AnswerDetector.instance.value = true;
}