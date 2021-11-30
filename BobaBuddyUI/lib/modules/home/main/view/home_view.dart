import 'package:boba_buddy/core/repository/store_repository.dart';
import 'package:boba_buddy/modules/home/shops/popular_shop.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:material_floating_search_bar/material_floating_search_bar.dart';

class MainPage extends StatelessWidget {
  const MainPage({Key? key}) : super(key: key);

  static Page page() => const MaterialPage<void>(child: MainPage());

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
          Padding(padding: EdgeInsets.only(top: 150.h),
              child: const PopularShops()),
          const Positioned(
              left: 11,
              bottom: 280,
              child: Text(
                "Category",
                style: TextStyle(
                    fontFamily: "Josefin Sans",
                    fontSize: 20,
                    color: Colors.black,
                    fontWeight: FontWeight.bold),
              ))
        ]),
      ),
    );
  }
}
