import 'package:boba_buddy/Widgets/category_widget.dart';
import 'package:boba_buddy/Widgets/popular_shop_widget.dart';
import 'package:boba_buddy/Widgets/search_bar.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:material_floating_search_bar/material_floating_search_bar.dart';

class HomePage extends StatelessWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final controller = FloatingSearchBarController();
    return ScreenUtilInit(
      designSize: const Size(393, 830),
      builder: () => Scaffold(
        resizeToAvoidBottomInset: false,
        body: Stack(children: [
          Positioned(
              left: 11.w,
              top: 135.h,
              child: const Text(
                "Popular Shops",
                style: TextStyle(
                    fontFamily: "Josefin Sans",
                    fontSize: 20,
                    color: Colors.black,
                    fontWeight: FontWeight.bold),
              )),
          Padding(padding: EdgeInsets.only(top: 150.h), child: PopularShops()),
          Positioned(
              left: 11,
              bottom: 280,
              child: Text(
                "Category",
                style: TextStyle(
                    fontFamily: "Josefin Sans",
                    fontSize: 20,
                    color: Colors.black,
                    fontWeight: FontWeight.bold),
              )),
          Positioned(
              width: 350,
              height: 350,
              bottom: -65,
              right: 20,
              child: CategoryWidget()),
          SearchBar()
        ]),
      ),
    );
  }
}
