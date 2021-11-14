

import 'package:boba_buddy/Database/database.dart';
import 'package:boba_buddy/Screens/store_page.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class PopularShops extends StatefulWidget {
  const PopularShops({Key? key}) : super(key: key);

  @override
  _PopularShops createState() => _PopularShops();
}
  class _PopularShops extends State<PopularShops> {


  @override
  Widget build(BuildContext context) {
    const double SPACEBETWEEN = 10.00;

    Database db = Database();

    //final List testData = [["Chatime", "667 some street"],["Some other resturaunt", "another address"],["another store","990 another street"]]; // This is used as example data. We will be using reading from a json for final implementation

    var stores = db.getStoreNames();
    //print('as');
    print(stores);

    return Container(
      margin: const EdgeInsets.only(top: 16),
      child: SingleChildScrollView(
        scrollDirection: Axis.horizontal,
        child: Row(
          children:<Widget> [
            Container(height: 225,
        margin:
            const EdgeInsets.only(left: SPACEBETWEEN),
                child:

                FutureBuilder(
                    future: db.getStoreNames(),
                    builder: (context, AsyncSnapshot snapshot) {
                      if (!snapshot.hasData) {
                        return const Center(child: CircularProgressIndicator());
                      } else {
                        return ListView.builder(
                          shrinkWrap: true,
                            itemCount: snapshot.data.length >= 3? 3: snapshot.data.length,
                            scrollDirection: Axis.horizontal,
                            itemBuilder: (BuildContext context, int index) {
                              return singleShop(
                                                context: context,
                                                imageSrc: 'https://d1ralsognjng37.cloudfront.net/3586a06b-55c6-4370-a9b9-fe34ef34ad61.jpeg', //todo need image src implemented in entity classes
                                                title: snapshot.data[index]["name"] ?? "",
                                                address: snapshot.data[index]["location"] ?? "",
                                storeId: snapshot.data[index]['id'],
                                items: snapshot.data[index]['menu']


                                              );
                            });
                      }
                    })


        //         ListView.builder(
        //         itemCount:testData.length, //todo set a limit for amount of popular shops to be displayed on homepage
        //         shrinkWrap: true,scrollDirection: Axis.horizontal,itemBuilder: (context, index){
        //     return singleShop(
        //               context: context,
        //               imageSrc: 'https://d1ralsognjng37.cloudfront.net/3586a06b-55c6-4370-a9b9-fe34ef34ad61.jpeg', //todo mock data
        //               title: testData.elementAt(index).elementAt(0) ?? "",
        //               address: testData.elementAt(index).elementAt(1) ?? ""
        //             );
        // })
            )
          ],
      ),




          // children: <Widget>[
          //
          //   Container(
          //     margin:
          //     const EdgeInsets.only(left: SPACEBETWEEN),
          //     child: singleCategory(
          //       image: 'images/img_6.png',
          //       title: 'Some Store',
          //       address: 'some address'
          //
          //     ),
          //   ),
          //   Container(
          //     margin:
          //     EdgeInsets.only(left:SPACEBETWEEN ),
          //     child: singleCategory(
          //         image: 'images/img_7.png',
          //         title: "Chatime",
          //         address: 'some address'
          //     ),
          //   ),
          //   Container(
          //     margin:
          //     EdgeInsets.only(left: SPACEBETWEEN),
          //     child: singleCategory(
          //         image: 'images/img_8.png',
          //         title: "Another Store",
          //         address: 'some address'
          //     ),
          //   ),
          //   Container(
          //     margin: EdgeInsets.only(
          //         left: SPACEBETWEEN,
          //         right: SPACEBETWEEN),
          //     child: singleCategory(
          //         image: 'images/img_9.png',
          //         title: "Soda",
          //         address: 'some address'
          //     ),
          //   ),
          // ],
        ),
      );

  }

  }

Widget singleShop({required String imageSrc, required String title, required String address, required context, required String storeId, required items}) {
  const double WIDGETWIDTH = 325;
  const double WIDGETHEIGHT = 100;

  String itemId;

  if(items.length == 0){
    itemId = '';
  }else{
    // itemId = items[0]["id"];
    itemId = '';
  }

  return InkWell(
    onTap: () {
      print("Navigate to ${title} shop page");
      Navigator.push(context,
          MaterialPageRoute(builder: (context) => StorePage(storeName: title,imageSrc: imageSrc, address: address, storeId: storeId, itemId: itemId,)));
    },
    child: Container(width: WIDGETWIDTH, height: WIDGETHEIGHT,
      margin: const EdgeInsets.only(bottom: 7, right: 15),

      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: const BorderRadius.only(
            topLeft: Radius.circular(10),
            topRight: Radius.circular(10),
            bottomLeft: Radius.circular(10),
            bottomRight: Radius.circular(10),
        ),
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withOpacity(0.5),
            spreadRadius: 1,
            blurRadius: 6,
            offset: const Offset(0, 3), // changes position of shadow
          ),
        ],
      ),


      child: ClipRRect(
        //borderRadius: BorderRadius.all(const Radius.circular(8)),
        child: Stack(
          children: <Widget>[
            SizedBox(
              width: WIDGETWIDTH,
              height: 160,
              child: Image.network(imageSrc, width: 200, height: 100, fit: BoxFit.fitWidth,
                errorBuilder: (BuildContext context, Object exception, StackTrace? stackTrace) {
                  // Appropriate logging or analytics, e.g.
                  // myAnalytics.recordError(
                  //   'An error occurred loading "https://example.does.not.exist/image.jpg"',
                  //   exception,
                  //   stackTrace,
                  // );
                  return const Image(
                  image: AssetImage("assets/images/default-store.png"));
                }
                  )
              ),






            Positioned(bottom: -15,
              child: Align(alignment: Alignment.bottomLeft,
                child: SizedBox(
                  width: WIDGETWIDTH,
                  height: 110,
                    child: Align(alignment: Alignment.centerLeft,
                      child: Padding(padding: const EdgeInsets.only(left: 10),
                        child: Text(
                          title,
                          maxLines: 1,
                          textAlign: TextAlign.start,
                          style: const TextStyle(fontFamily: "Josefin Sans",color: Colors.black, fontWeight: FontWeight.bold, fontSize: 15),
                          // style: AppTheme.getTextStyle(themeData.textTheme.subtitle1,
                          //     fontWeight: 600, color: Colors.white, letterSpacing: 0.3),
                        ),
                      ),
                    ),




                ),
              ),
            ),

            Positioned(bottom: -45,left: -12,
              child: SizedBox(
                width: WIDGETWIDTH,
                height: 120,
                child: Center(
                  child: Align(alignment: Alignment.centerLeft,
                    child: Padding(padding: const EdgeInsets.only(left: 23),
                      child: Text(
                        address,
                        maxLines: 1,
                        textAlign: TextAlign.start,
                        style: TextStyle(color: Colors.grey[600], fontWeight: FontWeight.w500),
                        // style: AppTheme.getTextStyle(themeData.textTheme.subtitle1,
                        //     fontWeight: 600, color: Colors.white, letterSpacing: 0.3),
                      ),
                    ),
                  ),



                ),
              ),
            ),
            
            
          ],
        ),
      ),
    ),
  );
}

