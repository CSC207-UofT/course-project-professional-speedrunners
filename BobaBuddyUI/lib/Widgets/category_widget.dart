import 'package:boba_buddy/Database/database.dart';
import 'package:boba_buddy/Screens/category_page.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class CategoryWidget extends StatelessWidget {
  final double WIDTH = 60;
  final double HEIGHT = 60;

  const CategoryWidget({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ScreenUtilInit(
      designSize: const Size(393,830),
      builder:()=> GridView.count(
        physics: const NeverScrollableScrollPhysics(),
        crossAxisCount: 3,
        crossAxisSpacing: 10.w,
        mainAxisSpacing: 10.w,
        shrinkWrap: true,
        children: <Widget>[
          singleCategory(
              "assets/images/milkTea.png", "Milk Tea", BoxFit.fitHeight, context),
          singleCategory(
              "assets/images/slush.png", "Slush", BoxFit.fitWidth, context),
          singleCategory(
              "assets/images/soda.png", "Soda", BoxFit.fitHeight, context),
          singleCategory("assets/images/smoothie.png", "Smoothie",
              BoxFit.fitHeight, context),
          singleCategory("assets/images/fruitTea.png", "Fruit Tea",
              BoxFit.fitHeight, context),
          singleCategory("assets/images/saltedCream.png", "Salted Cream",
              BoxFit.fitWidth, context),
        ],
      ),
    );
  }
}

Widget singleCategory(String image, String title, BoxFit fit, context) {
  return SizedBox(
    width: 200.w,
    height: 200.h,
    child: InkWell(
      onTap: () {
        // print('Category ${title} Selected');
        // Database().run();
        Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => CategoryPage(category: title)));
      },
      child: ClipRRect(
        borderRadius: BorderRadius.all(const Radius.circular(8)),
        child: Stack(
          children: <Widget>[
            SizedBox(
              width: 300.w,
              height: 300.h,
              child: Image(
                image: AssetImage(image),
                width: 300.w,
                height: 300.h,
                fit: fit,
              ),
            ),
            SizedBox(
              width: 140,
              height: 140,
              child: Center(
                child: Text(
                  title,
                  style: const TextStyle(
                      fontFamily: "Josefin Sans",
                      fontSize: 15,
                      color: Colors.white,
                      fontWeight: FontWeight.bold),
                  // style: AppTheme.getTextStyle(themeData.textTheme.subtitle1,
                  //     fontWeight: 600, color: Colors.white, letterSpacing: 0.3),
                ),
              ),
            )
          ],
        ),
      ),
    ),
  );
}
