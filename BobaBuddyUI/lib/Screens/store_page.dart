import 'dart:ui';

import 'package:boba_buddy/Database/database.dart';
import 'package:boba_buddy/Model/item.dart';
import 'package:boba_buddy/Model/store.dart';
import 'package:boba_buddy/Screens/price_update_page.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:maps_launcher/maps_launcher.dart';

import 'full_menu_page.dart';

class StorePage extends StatefulWidget {
  const StorePage({
    Key? key,
    required this.imageSrc,
    required this.store,
    required this.item,
  }) : super(key: key);


  final String imageSrc;
  final Store store;
  final Item item;

  @override
  _StorePage createState() => _StorePage();
}

class _StorePage extends State<StorePage> {
  @override
  Widget build(BuildContext context) {
    double deviceWidth = MediaQuery.of(context).size.width;
    double deviceHeight = MediaQuery.of(context).size.height;
    Database db = Database();

    print(widget.item.id);
    print("item id ^^^^^");

    return Scaffold(
      body: SingleChildScrollView(
        child: Stack(
          children: [
            SizedBox(
              width: deviceWidth,
              height: deviceHeight + 100,
            ),
            ClipRRect(
              borderRadius: const BorderRadius.only(
                  bottomLeft: Radius.circular(10),
                  bottomRight: Radius.circular(10)),
              child: Container(
                width: deviceWidth,
                height: 300,
                decoration: BoxDecoration(
                  image: DecorationImage(
                    image: NetworkImage(widget.imageSrc),
                    fit: BoxFit.fitWidth,
                  ),
                  shape: BoxShape.rectangle,
                ),
              ),
            ),
            Positioned(
                top: 20,
                child: IconButton(
                    onPressed: () {
                      Navigator.pop(context);
                    },
                    icon: const Icon(
                      Icons.arrow_back,
                      size: 30,
                      color: Colors.white,
                    ))),
            SizedBox(
                width: deviceWidth,
                height: 300,
                child: Align(
                  alignment: Alignment.bottomCenter,
                  child: Padding(
                      padding: const EdgeInsets.only(bottom: 8),
                      child: ClipRRect(
                          borderRadius:
                              const BorderRadius.all(Radius.circular(30)),
                          child: Container(
                              width: deviceWidth / 3,
                              height: 50,
                              color: Colors.black.withOpacity(0.15),
                              child: BackdropFilter(
                                  filter: ImageFilter.blur(
                                      sigmaX: 3.0,
                                      sigmaY: 3.0,
                                      tileMode: TileMode.mirror),
                                  child: Row(children: [
                                    Padding(
                                        padding: const EdgeInsets.only(
                                            left: 20, top: 10),
                                        child: GestureDetector(
                                          onTap: () {
                                            print("Open in maps");
                                            MapsLauncher.launchQuery(
                                                widget.store.location);
                                          },
                                          child: SizedBox(
                                            height: 50,
                                            //width: deviceWidth / 1.25,
                                            child: RichText(
                                              text: const TextSpan(children: [
                                                WidgetSpan(
                                                    child: Icon(
                                                  Icons.directions_outlined,
                                                  color: Colors.white,
                                                )),
                                                TextSpan(
                                                    text: "Directions",
                                                    style: TextStyle(
                                                        fontFamily:
                                                            "Josefin Sans",
                                                        fontSize: 15))
                                              ]),
                                            ),
                                          ),
                                        )

                                        // Text(
                                        //   "Directions",
                                        //   style: TextStyle(
                                        //       color: Colors.white,
                                        //       fontFamily: "Josefin Sans",
                                        //       fontSize: 15),
                                        // ),
                                        )
                                  ]))))),
                )),
            Positioned(
                top: 325,
                left: 25,
                child: Text(
                  widget.store.name,
                  textAlign: TextAlign.start,
                  style: const TextStyle(
                      fontFamily: "Josefin Sans",
                      fontSize: 30,
                      fontWeight: FontWeight.bold),
                )),
            Positioned(
                top: 370,
                left: 25,
                child: Text(
                  widget.store.location,
                  textAlign: TextAlign.start,
                  style: const TextStyle(
                      fontFamily: "Josefin Sans",
                      fontSize: 15,
                      fontWeight: FontWeight.w600,
                      color: Colors.grey),
                )),
            Positioned(
                top: 680,
                left: 25,
                child: GestureDetector(
                  onTap: () {
                    Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => FullMenuPage(
                                  store: widget.store,
                                )));
                  },
                  child: const SizedBox(
                    height: 50,
                    child: Text('View All Menu Prices',
                        style: TextStyle(
                          fontFamily: "Josefin Sans",
                          fontWeight: FontWeight.bold,
                          fontSize: 20,
                          color: Colors.blue,
                        )),
                  ),
                )),

            Positioned(
              bottom: 200,
              child: SizedBox(
                child: FutureBuilder(
                  future: widget.item.id!.isEmpty
                      ? db.getOneItemFromStore(widget.store.id)
                      : db.getItemById(widget.item.id!),
                  builder: (context, AsyncSnapshot snapshot) {
                    if (!snapshot.hasData) {
                      return const Center(child: CircularProgressIndicator());
                    }
                    // TODO: properly handle empty response
                    // else if (snapshot.data.length == 0) {
                    //   return const Padding(
                    //       padding: EdgeInsets.only(bottom: 100),
                    //       child: Text(
                    //         "no items available",
                    //         style: TextStyle(fontSize: 30),
                    //       ));
                    // }
                    else {
                      print(widget.item.id!);
                      return Stack(children: [
                        SizedBox(
                          width: deviceWidth,
                          height: 300,
                        ),
                        Positioned(
                          left: 25,
                          bottom: 250,
                          child: Text(
                            snapshot.data.name.toString(),
                            style: const TextStyle(
                                color: Colors.black,
                                fontFamily: "Josefin Sans",
                                fontSize: 30,
                                fontWeight: FontWeight.bold),
                          ),
                        ),
                        ClipRRect(
                          borderRadius:
                              const BorderRadius.all(Radius.circular(8)),
                          child: Container(
                              margin: const EdgeInsets.only(left: 25, top: 65),
                              width: 200,
                              height: 200,
                              decoration: const BoxDecoration(
                                image: DecorationImage(
                                  image: NetworkImage(
                                      "https://theforkedspoon.com/wp-content/uploads/2019/03/How-to-make-Bubble-Tea-8.jpg"),
                                  fit: BoxFit.fitWidth,
                                ),
                                shape: BoxShape.rectangle,
                              )),
                        ),
                        Positioned(
                          left: 250,
                          top: 125,
                          child: Text(
                            "\$${snapshot.data.price.toString()}",
                            textAlign: TextAlign.start,
                            style: const TextStyle(
                                color: Colors.black,
                                fontFamily: "Josefin Sans",
                                fontSize: 20,
                                fontWeight: FontWeight.bold),
                          ),
                        ),
                        Positioned(
                          left: 250,
                          top: 175,
                          child: ElevatedButton(
                            onPressed: () {
                              Navigator.of(context).push(MaterialPageRoute(
                                  builder: (context) => PriceUpdaterPage(
                                        item: widget.item,
                                        imageSrc: widget.imageSrc,
                                      )));
                            },
                            child: const Text(
                              'Wrong Price?',
                              style: TextStyle(
                                  fontFamily: "Josefin Sans",
                                  fontWeight: FontWeight.bold,
                                  fontSize: 15),
                            ),
                            style: ElevatedButton.styleFrom(
                              shape: const RoundedRectangleBorder(
                                  borderRadius:
                                      BorderRadius.all(Radius.circular(8))),
                              minimumSize: const Size(100, 40),
                              primary: const Color.fromRGBO(132, 141, 255, 1),
                            ),
                          ),
                        ),
                      ]);
                    }
                  },
                ),
              ),
            )

            // Positioned(bottom: 200,
            //     child: itemWidget(imageSrc: widget.imageSrc, itemId: widget.itemId))
          ],
        ),
      ),
    );
  }
}

Widget itemWidget({required String imageSrc, required String itemId}) {
  Database db = Database();

  return FutureBuilder(
    future: db.getItemById(itemId),
    builder: (BuildContext context, AsyncSnapshot<dynamic> snapshot) {
      if (!snapshot.hasData) {
        return const Center(child: CircularProgressIndicator());
      } else {
        return Stack(children: [
          Text(
            snapshot.data.name.toString(),
            style: const TextStyle(
                color: Colors.black,
                fontFamily: "Josefin Sans",
                fontSize: 20,
                fontWeight: FontWeight.bold),
          ),
          ClipRRect(
            borderRadius: const BorderRadius.all(Radius.circular(8)),
            child: Container(
                width: 200,
                height: 200,
                decoration: const BoxDecoration(
                  image: DecorationImage(
                    image: NetworkImage(
                        "https://theforkedspoon.com/wp-content/uploads/2019/03/How-to-make-Bubble-Tea-8.jpg"),
                    fit: BoxFit.fitWidth,
                  ),
                  shape: BoxShape.rectangle,
                )),
          ),
          Text(
            "\$${snapshot.data.price.toString()}",
            textAlign: TextAlign.start,
            style: const TextStyle(
                color: Colors.black,
                fontFamily: "Josefin Sans",
                fontSize: 20,
                fontWeight: FontWeight.bold),
          ),
          Positioned(
            top: 550,
            left: 230,
            child: ElevatedButton(
              onPressed: () {
                // Navigator.of(context).push(MaterialPageRoute( //TODO : send item id to updater page but set default values on store page to optimize db calls when needing store information
                //     builder: (context) => PriceUpdaterPage(itemId: itemId, storeName: , address: '', storeId: '', imageSrc: '',)));
              },
              child: const Text(
                'Wrong Price?',
                style: TextStyle(
                    fontFamily: "Josefin Sans",
                    fontWeight: FontWeight.bold,
                    fontSize: 15),
              ),
              style: ElevatedButton.styleFrom(
                shape: const RoundedRectangleBorder(
                    borderRadius: BorderRadius.all(Radius.circular(8))),
                minimumSize: const Size(100, 40),
                primary: const Color.fromRGBO(132, 141, 255, 1),
              ),
            ),
          ),
        ]);
      }
    },
  );
}
