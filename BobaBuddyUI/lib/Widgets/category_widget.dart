import 'package:boba_buddy/Database/database.dart';
import 'package:boba_buddy/Screens/category_page.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class CategoryWidget extends StatelessWidget {
  static const double WIDTH = 60;
  static const double HEIGHT = 60;

  const CategoryWidget({Key? key}) : super(key: key);



  @override
  Widget build(BuildContext context) {
    return GridView.count(crossAxisCount: 3,
      crossAxisSpacing: 10,
      mainAxisSpacing: 10,
    shrinkWrap: true,
    children: <Widget> [
      singleCategory("assets/images/milkTea.png", "Milk Tea", BoxFit.fitHeight, context),
      singleCategory("assets/images/slush.png", "Slush", BoxFit.fitWidth, context),
      singleCategory("assets/images/soda.png", "Soda", BoxFit.fitHeight, context),
      singleCategory("assets/images/smoothie.png", "Smoothie", BoxFit.fitHeight, context),
      singleCategory("assets/images/fruitTea.png", "Fruit Tea", BoxFit.fitHeight, context),
      singleCategory("assets/images/saltedCream.png", "Salted Cream", BoxFit.fitWidth, context),



      ],

    );


  }

}

  Widget singleCategory(String image, String title, BoxFit fit, context) {
    return SizedBox(width: 300, height: 300,
      child: InkWell(
        onTap: () {
          print('Category ${title} Selected');
          Database().run();
          Navigator.push(context,
              MaterialPageRoute(builder: (context) => CategoryPage(category: title)));
        },
        child: ClipRRect(
          borderRadius: BorderRadius.all(const Radius.circular(8)),
          child: Stack(
            children: <Widget>[
              SizedBox(
                width: 300,
                height: 300,
                child: Image(
                  image: AssetImage(image),
                  width: 300,
                  height: 300,
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
                        color: Colors.white, fontWeight: FontWeight.bold),
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
