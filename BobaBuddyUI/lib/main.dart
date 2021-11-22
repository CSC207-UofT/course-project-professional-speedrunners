import 'package:boba_buddy/Screens/login_page.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

// void main() => runApp(MyApp());

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
  runApp(MyApp());
}
class MyApp extends StatelessWidget {
  MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return
       ScreenUtilInit( //used for universal scaling across devices
         builder: () => MaterialApp(
            debugShowCheckedModeBanner: false, home: LoginPage()),
       designSize: const Size(393,830), //The size of the device screen in the design draft, in dp
       );

  }
}
