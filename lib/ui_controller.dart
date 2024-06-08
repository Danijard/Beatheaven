
import 'package:beatheaven/ui/global_ui_values.dart';

void changeBottomSheetState() {
  if (!AnswerDetector.instance.value) {
    AnswerDetector.instance.value = !AnswerDetector.instance.value;
  }
}

void foundAnswer(author, title, url) {
  coverURl = url;
  foundAuthor = author;
  foundTitle = title;
}

