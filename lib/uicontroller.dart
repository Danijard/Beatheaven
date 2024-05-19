
import 'package:beatheaven/ui/global_ui_values.dart';

void openBottomSheet() {
  if (!TapDetector.instance.value) {
    TapDetector.instance.value = true;
  }
}

void closeBottomSheet() {
  if (TapDetector.instance.value) {
    TapDetector.instance.value = false;
  }
}

void foundAnswer(author, title, url) {
  coverURl = url;
  foundAuthor = author;
  foundTitle = title;
}

