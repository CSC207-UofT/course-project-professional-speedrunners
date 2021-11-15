import 'dart:ui';

import 'package:boba_buddy/Database/database.dart';
import 'package:boba_buddy/Screens/price_update_page.dart';
import 'package:boba_buddy/Widgets/menu_prices_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'full_menu_page.dart';

class StorePage extends StatefulWidget {
  const StorePage(
      {Key? key,
      required this.storeName,
      required this.address,
      required this.imageSrc,
      required this.storeId, required this.itemId,
      })
      : super(key: key);

  final String storeName;
  final String address;
  final String imageSrc;
  final String storeId;
  final String itemId;

  @override
  _StorePage createState() => _StorePage();
}

class _StorePage extends State<StorePage> {

  @override
  Widget build(BuildContext context) {
    // TODO: finish store page
    double deviceWidth = MediaQuery.of(context).size.width;
    double deviceHeight = MediaQuery.of(context).size.height;
    Database db = Database();

    print(widget.itemId);
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
                              width: deviceWidth / 1.25,
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
                                            left: 20, top: 5),
                                        child: RichText(
                                            text: const TextSpan(children: [
                                          WidgetSpan(
                                              child: Icon(
                                            Icons.call_outlined,
                                            color: Colors.white,
                                          )),
                                          TextSpan(
                                              text: "905-828-2234",
                                              style: TextStyle(
                                                  fontFamily: "Josefin Sans",
                                                  fontSize: 15))
                                        ]))),
                                    Padding(
                                        padding:
                                            const EdgeInsets.only(left: 40),
                                        child: VerticalDivider(
                                            width: 0.7,
                                            thickness: 0.7,
                                            color: Colors.white
                                                .withOpacity(0.45))),
                                    Padding(
                                        padding: const EdgeInsets.only(
                                            left: 25, top: 5),
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
                                                    fontFamily: "Josefin Sans",
                                                    fontSize: 15))
                                          ]),
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
                  widget.storeName,
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
                  widget.address,
                  textAlign: TextAlign.start,
                  style: const TextStyle(
                      fontFamily: "Josefin Sans",
                      fontSize: 15,
                      fontWeight: FontWeight.w600,
                      color: Colors.grey),
                )),
            // Positioned(
            //     top: 400,
            //     left: 25,
            //     child: Text(
            //       "Classic Milk Tea",
            //       style: TextStyle(
            //           color: Colors.black,
            //           fontFamily: "Josefin Sans",
            //           fontSize: 20,
            //           fontWeight: FontWeight.bold),
            //     )),
            // Positioned(
            //     top: 435,
            //     left: 20,
            //     child: ClipRRect(
            //       borderRadius: BorderRadius.all(const Radius.circular(8)),
            //       child: Container(
            //           width: 200,
            //           height: 200,
            //           decoration: const BoxDecoration(
            //             image: DecorationImage(
            //               image: NetworkImage(
            //                   "https://theforkedspoon.com/wp-content/uploads/2019/03/How-to-make-Bubble-Tea-8.jpg"),
            //               fit: BoxFit.fitWidth,
            //             ),
            //             shape: BoxShape.rectangle,
            //           )),
            //     )),
            //
            // //TODO: Migrate wiget components to indivual components
            //
            // const Positioned(
            //     top: 525,
            //     left: 230,
            //     child: Text(
            //       "\$2.88",
            //       textAlign: TextAlign.start,
            //       style: TextStyle(
            //           color: Colors.black,
            //           fontFamily: "Josefin Sans",
            //           fontSize: 20,
            //           fontWeight: FontWeight.bold),
            //     )),
            //
            // // Positioned( top: 550, left: 230,
            // //   child: ElevatedButton(onPressed: () {
            // //     print("pressed");
            // //     Navigator.of(context).push(MaterialPageRoute(
            // //         builder: (context) => PriceUpdaterPage()));
            // //   },
            // //
            // //     child: const Text('Wrong Price?', style: TextStyle(
            // //         fontFamily: "Josefin Sans", fontWeight:
            // //     FontWeight.bold, fontSize: 15
            // //
            // //     ),
            // //     ),
            // //     style: ElevatedButton.styleFrom(
            // //       shape: const RoundedRectangleBorder(
            // //           borderRadius: BorderRadius.all(
            // //               Radius.circular(8))),
            // //       minimumSize: const Size(100, 40),
            // //       primary: const Color.fromRGBO(132, 141, 255, 1),
            // //     ),
            // //
            // //   ),
            // // ),
            //
            Positioned(
                top: 680,
                left: 25,
                child: GestureDetector(
                  onTap: () {
                    Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => FullMenuPage(storeId: widget.storeId,)));
                  },
                  child: SizedBox(
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
            //
            // //Positioned(right: 0.0, left: 0.0, top: 0.0, bottom:-650, child: SizedBox(height: 200, child: MenuPricesWidget())),
            //
            // Positioned(
            //   top: 550,
            //   left: 230,
            //   child: ElevatedButton(
            //     onPressed: () {
            //       Navigator.of(context).push(MaterialPageRoute(
            //           builder: (context) => PriceUpdaterPage()));
            //     },
            //     child: const Text(
            //       'Wrong Price?',
            //       style: TextStyle(
            //           fontFamily: "Josefin Sans",
            //           fontWeight: FontWeight.bold,
            //           fontSize: 15),
            //     ),
            //     style: ElevatedButton.styleFrom(
            //       shape: const RoundedRectangleBorder(
            //           borderRadius: BorderRadius.all(Radius.circular(8))),
            //       minimumSize: const Size(100, 40),
            //       primary: const Color.fromRGBO(132, 141, 255, 1),
            //     ),
            //   ),
            // ),

        Positioned(bottom: 200,
          child: SizedBox(
            child: FutureBuilder(
              future: widget.itemId.isEmpty ?  db.getOneItemFromStore(widget.storeId) : db.getItemById(widget.itemId),
      builder:(context, AsyncSnapshot snapshot) {
            if(!snapshot.hasData){

              return const Center(child: CircularProgressIndicator());
            }
            else if(snapshot.data.length == 0){
              return const Padding(
                  padding: EdgeInsets.only(bottom: 100),
                  child: Text("no items available", style:
                    TextStyle(fontSize: 30)
                    ,));
            }
            else{

              print(widget.itemId);
              return Stack(
                  children: [
                    SizedBox(width: deviceWidth, height: 300,),

                    Positioned(left: 25,
                      bottom: 250,
                      child: Text(
                        snapshot.data["name"].toString(),
                        style: const TextStyle(
                            color: Colors.black,
                            fontFamily: "Josefin Sans",
                            fontSize: 30,
                            fontWeight: FontWeight.bold),
                      ),
                    ),

                    ClipRRect(
                      borderRadius: const BorderRadius.all(Radius.circular(8)),
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

                    Positioned(left: 250, top: 125,
                      child: Text(
                        "\$${snapshot.data["price"].toString()}" ,
                        textAlign: TextAlign.start,
                        style: const TextStyle(
                            color: Colors.black,
                            fontFamily: "Josefin Sans",
                            fontSize: 20,
                            fontWeight: FontWeight.bold),
                      ),
                    ),

                     Positioned(left: 250, top: 175,
                       child: ElevatedButton(
                          onPressed: () {
                            Navigator.of(context).push(MaterialPageRoute( //TODO : send item id to updater page
                                builder: (context) => PriceUpdaterPage(itemId: widget.itemId.isEmpty ? snapshot.data["id"].toString() : widget.itemId, imageSrc: widget.imageSrc, storeId: widget.storeId, storeName: widget.storeName, address: widget.address, )));
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


                  ]
              );
            }


      },),
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

Widget itemWidget({ required String imageSrc, required String itemId}){

  Database db = Database();
  print("+++++++++++++++++++++++++");
  print(itemId);

  return FutureBuilder(
    future: db.getItemById(itemId),
    builder:(BuildContext context, AsyncSnapshot<dynamic> snapshot) {
      if(!snapshot.hasData){
        return const Center(child: CircularProgressIndicator());
      }else{
        return Stack(
          children: [
            Text(
              snapshot.data["name"].toString(),
              style: TextStyle(
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
               "\$${snapshot.data["price"].toString()}" ,
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
                  // Navigator.of(context).push(MaterialPageRoute( //TODO : send item id to updater page
                  //     builder: (context) => PriceUpdaterPage(itemId: itemId,)));
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
      ]
        );
      }


  },);
}
