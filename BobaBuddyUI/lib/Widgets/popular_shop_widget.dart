import 'package:boba_buddy/Database/database.dart';
import 'package:boba_buddy/Model/item.dart';
import 'package:boba_buddy/Model/store.dart';
import 'package:boba_buddy/Screens/store_page.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

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

    return ScreenUtilInit(
      designSize: const Size(393, 830),
      builder: () => Container(
        width: 400.w,
        height: 400.h,
        margin: EdgeInsets.only(top: 16.h),
        child: SingleChildScrollView(
          scrollDirection: Axis.horizontal,
          child: Row(
            children: <Widget>[
              Container(
                  height: 225,
                  margin: EdgeInsets.only(left: SPACEBETWEEN.w),
                  child: FutureBuilder(
                      future: db.getStores(),
                      builder: (context, AsyncSnapshot snapshot) {
                        if (!snapshot.hasData) {
                          return const Center(
                              child: CircularProgressIndicator());
                        } else {
                          return ListView.builder(
                              shrinkWrap: true,
                              itemCount: snapshot.data.length >= 3
                                  ? 3
                                  : snapshot.data.length,
                              scrollDirection: Axis.horizontal,
                              itemBuilder: (BuildContext context, int index) {
                                return _buildSingleShop(
                                    context: context,
                                    imageSrc:
                                        'https://d1ralsognjng37.cloudfront.net/3586a06b-55c6-4370-a9b9-fe34ef34ad61.jpeg',
                                    //todo need image src implemented in entity classes
                                    store: snapshot.data[index],
                                    items: snapshot.data[index].menu);
                              });
                        }
                      }))
            ],
          ),
        ),
      ),
    );
  }
}

///Widget builder for a single shop widget
Widget _buildSingleShop(
    {required String imageSrc,
    required context,
    required Store store,
    required List<Item> items}) {
  double WIDGETWIDTH = 325.w;
  double WIDGETHEIGHT = 100.h;

  // TODO: this is hacky. need to look into better structure
  // Also could create a widget for displaying no item status
  Item? item = items.isEmpty ? null : items[0];

  return InkWell(
    onTap: () {
      Navigator.push(
          context,
          MaterialPageRoute(
              builder: (context) => StorePage(
                    store: store,
                    imageSrc: imageSrc,
                    item: item!,
                  )));
    },
    child: Container(
      width: WIDGETWIDTH,
      height: WIDGETHEIGHT,
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
                child: Image.network(imageSrc,
                    width: 200,
                    height: 100,
                    fit: BoxFit.fitWidth, errorBuilder: (BuildContext context,
                        Object exception, StackTrace? stackTrace) {
                  //Error handling for image
                  return const Image(
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
