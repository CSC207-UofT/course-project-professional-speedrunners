
import 'package:boba_buddy/Screens/store_page.dart';
import 'package:boba_buddy/core/model/models.dart';
import 'package:boba_buddy/core/repository/item_repository.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:provider/src/provider.dart';

class SearchPage extends StatefulWidget {
  final String searchTerm;

  const SearchPage({Key? key, required this.searchTerm}) : super(key: key);

  @override
  _SearchPage createState() => _SearchPage();
}

class _SearchPage extends State<SearchPage> {
  @override
  Widget build(BuildContext context) {
    double deviceWidth = MediaQuery.of(context).size.width;
    double deviceHeight = MediaQuery.of(context).size.height;

    ItemRepository db = context.read<ItemRepository>();

    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: const Text(
          "Search Results",
          style: TextStyle(
              color: Colors.black,
              fontFamily: "Josefin Sans",
              fontWeight: FontWeight.w600,
              fontSize: 22),
        ), // You can add title here
        backgroundColor: Colors.white, foregroundColor: Colors.black,
      ),
      body: Container(
        margin: const EdgeInsets.only(top: 16),
        child: Container(
            margin: const EdgeInsets.only(left: 10.0),
            child: FutureBuilder(
                future: db.findByNameContain(widget.searchTerm),
                builder: (context, AsyncSnapshot snapshot) {
                  if (!snapshot.hasData) {
                    return const Center(child: CircularProgressIndicator());
                  } else if (snapshot.data.length == 0) {
                    return Center(
                        child: Column(children: [
                      const Padding(
                        padding: EdgeInsets.only(top: 25),
                        child: Text(
                          'No drinks found',
                          style: TextStyle(
                              fontFamily: "Josefin Sans",
                              fontSize: 30,
                              color: Colors.black,
                              fontWeight: FontWeight.bold),
                        ),
                      ),
                      const SizedBox(height: 30),
                      ElevatedButton(
                          onPressed: () {
                            Navigator.of(context).pop();
                          },
                          child: const Text(
                            'Try Again',
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
                            primary: const Color.fromRGBO(86, 99, 255, 1),
                          ))
                    ]));
                  } else {
                    print("___________");
                    print(snapshot.data);
                    return ListView.builder(
                        shrinkWrap: true,
                        itemCount: snapshot.data.length,
                        scrollDirection: Axis.vertical,
                        itemBuilder: (BuildContext context, int index) {
                          return singleShop(
                              context: context,
                              imageSrc:
                                  'https://d1ralsognjng37.cloudfront.net/3586a06b-55c6-4370-a9b9-fe34ef34ad61.jpeg',
                              //todo need image src implemented in entity classes
                              store: snapshot.data[index].store,
                              item: snapshot.data[index]);
                        });
                  }
                })),
      ),
    );
  }
}

Widget singleShop(
    {required String imageSrc,
    required context,
    required Store store,
    required Item item}) {
  const double WIDGETWIDTH = 325;
  const double WIDGETHEIGHT = 220;

  return InkWell(
    onTap: () {
      print("Navigate to ${store.name} shop page");
      Navigator.push(
          context,
          MaterialPageRoute(
              builder: (context) => StorePage(
                    store: store,
                    item: item,
                    imageSrc: imageSrc,
                  )));
    },
    child: Container(
      width: WIDGETWIDTH,
      height: WIDGETHEIGHT,
      margin: const EdgeInsets.only(bottom: 20, right: 15),
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
                width: WIDGETWIDTH + 45,
                height: 160,
                child: Image.network(imageSrc,
                    width: WIDGETWIDTH,
                    height: 100,
                    fit: BoxFit.fitWidth, errorBuilder: (BuildContext context,
                        Object exception, StackTrace? stackTrace) {
                  // Appropriate logging or analytics, e.g.
                  // myAnalytics.recordError(
                  //   'An error occurred loading "https://example.does.not.exist/image.jpg"',
                  //   exception,
                  //   stackTrace,
                  // );
                  return const Image(
                      //fit: BoxFit.fitWidth,
                      image:
                          AssetImage("assets/images/default-store.dart.png"));
                })),
            Positioned(
              bottom: -15,
              child: Align(
                alignment: Alignment.bottomLeft,
                child: SizedBox(
                  width: WIDGETWIDTH,
                  height: 110,
                  child: Align(
                    alignment: Alignment.centerLeft,
                    child: Padding(
                      padding: const EdgeInsets.only(left: 10),
                      child: Text(
                        store.name,
                        maxLines: 1,
                        textAlign: TextAlign.start,
                        style: const TextStyle(
                            fontFamily: "Josefin Sans",
                            color: Colors.black,
                            fontWeight: FontWeight.bold,
                            fontSize: 15),
                        // style: AppTheme.getTextStyle(themeData.textTheme.subtitle1,
                        //     fontWeight: 600, color: Colors.white, letterSpacing: 0.3),
                      ),
                    ),
                  ),
                ),
              ),
            ),
            Positioned(
              bottom: -45,
              left: -12,
              child: SizedBox(
                width: WIDGETWIDTH,
                height: 120,
                child: Center(
                  child: Align(
                    alignment: Alignment.centerLeft,
                    child: Padding(
                      padding: const EdgeInsets.only(left: 23),
                      child: Text(
                        store.location,
                        maxLines: 1,
                        textAlign: TextAlign.start,
                        style: TextStyle(
                            color: Colors.grey[600],
                            fontWeight: FontWeight.w500),
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
