
import 'package:boba_buddy/modules/home/main/view/home_page.dart';
import 'package:boba_buddy/modules/home/main/view/home_view.dart';
import 'package:boba_buddy/modules/login/view/login_page.dart';
import 'package:boba_buddy/modules/profile/view/profile_page.dart';
import 'package:flutter/widgets.dart';


List<Page> onGenerateHomePages(int page, List<Page<dynamic>> pages) {
  switch (page) {
    case 0:
      return [MainPage.page()];
    case 1:
      return [ProfilePage.page()];
    default:
      return [MainPage.page()];
  }
}
