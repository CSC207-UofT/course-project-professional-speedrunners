import 'package:boba_buddy/Database/database.dart';
import 'package:boba_buddy/Model/item.dart';
import 'package:boba_buddy/Model/store.dart';
import 'package:boba_buddy/Screens/store_page.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class FullMenuPage extends StatelessWidget {
  final Store store;

  const FullMenuPage({Key? key, required this.store}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var db = Database();

    double deviceWidth = MediaQuery.of(context).size.width;
    double deviceHeight = MediaQuery.of(context).size.height;
    return Scaffold(
      body: Stack(children: [
        ListView.builder(
        padding: const EdgeInsets.only(top: 100),
        itemCount: store.menu!.length,
        shrinkWrap: true,
        scrollDirection: Axis.vertical,
        itemBuilder: (context, index) {
          return singleItem(
              context: context,
              item: store.menu![index],
              imageSrc:
              "https://chatime.com/wp-content/uploads/2020/10/Brown-Sugar-Pearls-with-Milk-Tea.png");
        }),
        Positioned(
          top: 0.0,
          left: 0.0,
          right: 0.0,
          child: AppBar(
            centerTitle: true,
            title: const Text(
              'Menu',
              style: TextStyle(
                  color: Colors.black,
                  fontFamily: "Josefin Sans",
                  fontWeight: FontWeight.bold,
                  fontSize: 22),
            ),
            // You can add title here
            leading: IconButton(
              icon: const Icon(Icons.arrow_back_ios, color: Colors.black),
              onPressed: () => Navigator.of(context).pop(),
            ),
            backgroundColor: Colors.white.withOpacity(1),
            //You can make this transparent
            elevation: 0.0, //No shadow
          ),
        ),
      ]),
    );
  }
}

Widget singleItem(
    {required String imageSrc,
    required Item item,
    required BuildContext context}) {
  return Container(
    margin: const EdgeInsets.only(bottom: 30, right: 30, left: 30),
    height: 125,
    //width: 20,
    color: Colors.white,

    child: Stack(children: [
      Row(
        children: [
          Container(
            margin: const EdgeInsets.only(right: 10),
            height: 125,
            width: 100,
            child: Image.network(
              imageSrc,
              fit: BoxFit.fitWidth,
            ),
          ),
          Align(
              alignment: Alignment.topCenter,
              child: Padding(
                  padding: const EdgeInsets.only(top: 15),
                  child: Column(children: [
                    Text(
                      item.name,
                      style: const TextStyle(
                          fontSize: 19,
                          fontWeight: FontWeight.w500,
                          fontFamily: "Josefin Sans"),
                    ),
                    Padding(
                        padding: const EdgeInsets.only(right: 135),
                        child: Text(
                          "\$" + item.price.toString(),
                          style: TextStyle(
                              color: Colors.grey.shade500,
                              fontWeight: FontWeight.bold),
                        ))
                  ])))
        ],
      ),
      Positioned(
        right: 40,
        top: 70,
        child: ElevatedButton(
          onPressed: () async {
            Item refreshedItem = await Database().getItemById(item.id);
            Navigator.of(context).pop();

            StorePage storePage = StorePage(
              store: refreshedItem.store!,
              //TODO: need image in entity class
              imageSrc: imageSrc,
              item: item,
            );

            var pageRoute = PageRouteBuilder(
              pageBuilder: (c, a1, a2) => storePage,
              transitionsBuilder: (c, anim, a2, child) =>
                  FadeTransition(opacity: anim, child: child),
              transitionDuration: const Duration(milliseconds: 100),
            );
            Navigator.pushReplacement(context, pageRoute);
          },
          child: const Text(
            "View",
            style: TextStyle(
                fontFamily: "Josefin Sans",
                fontWeight: FontWeight.bold,
                fontSize: 15),
          ),
          style: ElevatedButton.styleFrom(
            shape: const RoundedRectangleBorder(
                borderRadius: BorderRadius.all(Radius.circular(8))),
            minimumSize: const Size(150, 45),
            primary: const Color.fromRGBO(86, 99, 255, 1),
          ),
        ),
      ),
    ]),
  );
}
