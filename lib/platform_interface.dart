import 'package:beatheaven/ui/global_ui_values.dart';
import 'package:beatheaven/ui_controller.dart';
import 'package:flutter/services.dart';

const androidChannel = MethodChannel('com.modilebev.beatheaven/Kotlin');

Future<void> tryRecording() async {
  try {
    var result = await androidChannel.invokeMethod('tryRecording');
    foundTitle = result[0];
    foundAuthor = result[1];
    coverURl = result[2];
    changeBottomSheetState();
  } on PlatformException catch (e) {
    print('Failed to record: ${e.message}');
  }
}

void setupMethodChannel() {
  androidChannel.setMethodCallHandler((MethodCall call) async {
    switch (call.method) {
      case 'foundSound':
        print('Method name: ${call.method}');
        List<dynamic> args = call.arguments;
        return 'Hello from Dart!';
      default:
        throw MissingPluginException();
    }
  });
}


void answer() {
  AnswerDetector.instance.value = true;
}