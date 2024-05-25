
import 'package:beatheaven/ui/global_ui_values.dart';

void openBottomSheet() {
  if (!AnswerDetector.instance.value) {
    AnswerDetector.instance.value = true;
  }
}

void closeBottomSheet() {
  if (AnswerDetector.instance.value) {
    AnswerDetector.instance.value = false;
  }
}

void foundAnswer(author, title, url) {
  coverURl = url;
  foundAuthor = author;
  foundTitle = title;
}

