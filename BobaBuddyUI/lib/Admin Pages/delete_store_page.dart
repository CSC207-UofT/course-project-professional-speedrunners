import 'package:boba_buddy/Screens/login_page.dart';
import 'package:boba_buddy/core/repository/repository.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:provider/src/provider.dart';

class DeleteStorePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    StoreRepository storeRepository = context.read<StoreRepository>();
    return ScreenUtilInit(
      designSize: const Size(393, 830),
      builder: () => Scaffold(
        appBar: AppBar(
          leading: IconButton(
            icon: Icon(Icons.arrow_back, color: Colors.black),
            onPressed: () => Navigator.of(context).pop(),
          ),
          title: Text(
            "Delete Store",
            style: TextStyle(color: Colors.black),
          ),
          centerTitle: true,
          backgroundColor: Colors.white,
        ),
        body: Center(
          child: FutureBuilder(
            future: storeRepository.getStores(),
            builder: (BuildContext context, AsyncSnapshot<dynamic> snapshot) {
              if (!snapshot.hasData) {
                return const CircularProgressIndicator();
              } else {
                print('asaasasasasa');
                print(snapshot.data);
                return (ListView.builder(
                  itemCount: snapshot.data.length,
                  itemBuilder: (BuildContext context, int index) {
                    return singleShop(
                        context: context,
                        imageSrc:
                            'https://d1ralsognjng37.cloudfront.net/3586a06b-55c6-4370-a9b9-fe34ef34ad61.jpeg',
                        //todo need image src implemented in entity classes
                        title: snapshot.data[index].name ?? "",
                        address: snapshot.data[index].location ?? "",
                        storeId: snapshot.data[index].id,
                        itemId: snapshot.data[index].id);
                  },
                ));
              }
            },
          ),
        ),
      ),
    );
  }
}

Widget singleShop({
  required String imageSrc,
  required String title,
  required String address,
  required context,
  required String storeId,
  required String itemId,
}) {
  const double WIDGETWIDTH = 325;
  const double WIDGETHEIGHT = 220;

  return InkWell(
    onTap: () {
      _showDialog(context, storeId);
    },
    child: Container(
      width: WIDGETWIDTH,
      height: WIDGETHEIGHT,
      margin: EdgeInsets.only(top: 25, right: 15.w, left: 15.w),
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
                      image: AssetImage("assets/images/default-store.png"));
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
                        title,
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
                        address,
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

void _showDialog(BuildContext context, String storeId) {
  // flutter defined function
  showDialog(
    context: context,
    builder: (BuildContext context) {
      // return object of type Dialog
      return AlertDialog(
        title: Text("Delete Store?"),
        content: Text(
            "This action cannot be undone and will force you to sign in again"),
        actions: <Widget>[
          // usually buttons at the bottom of the dialog
          FlatButton(
            child: Text("No"),
            onPressed: () {
              Navigator.of(context).pop();
            },
          ),
          FlatButton(
            child: Text("Yes"),
            onPressed: () async {
              await context.read<StoreRepository>().deleteStore(storeId);
              Navigator.of(context).pop();
              Navigator.of(context).pop();

              context.read<AuthenticationRepository>().logOut();

              var pageRoute = PageRouteBuilder(
                pageBuilder: (c, a1, a2) => LoginPage(),
                transitionsBuilder: (c, anim, a2, child) =>
                    FadeTransition(opacity: anim, child: child),
                transitionDuration: const Duration(milliseconds: 100),
              );

              Navigator.pushReplacement(context, pageRoute);
            },
          ),
        ],
      );
    },
  );
}
