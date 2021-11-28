import 'package:boba_buddy/Admin%20Pages/create_store_page.dart';
import 'package:boba_buddy/Admin%20Pages/delete_store_page.dart';
import 'package:boba_buddy/User%20Data/user_data.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class AdminPage extends StatelessWidget {
  List<String> optionNames = ["Create Store", "Delete Store", "Modify Store"];

  List pageOptions = [CreateStorePage(), DeleteStorePage(), DeleteStorePage()];

  @override
  Widget build(BuildContext context) {
    // TODO: implement AdminPage
    return ScreenUtilInit(
      designSize: const Size(393, 830),
      builder: () => Scaffold(
          body: FutureBuilder(
        future: User().getUserName(),
        builder: (BuildContext context, AsyncSnapshot<dynamic> snapshot) {
          if (!snapshot.hasData) {
            return const CircularProgressIndicator();
          } else {
            return _buildPage(optionNames, snapshot.data, pageOptions);
          }
        },
      )),
    );
  }
}

_buildPage(List<String> optionNames, String name, List pageOptions) {
  return SafeArea(
    child: Center(
        child: Padding(
      padding: EdgeInsets.only(top: 50.h),
      child: Column(
        children: [
          // const CircleAvatar(radius: 50.0,
          //   backgroundImage: AssetImage(
          //       "assets/images/defaultProfilePic.png"),
          // ),

          Padding(
              padding: EdgeInsets.only(top: 40.h, bottom: 25.h),
              child: Text(
                "Hello Admin $name",
                style:
                    const TextStyle(fontWeight: FontWeight.bold, fontSize: 20),
              )),

          ListView.builder(
            shrinkWrap: true,
            itemCount: optionNames.length,
            itemBuilder: (BuildContext context, int index) {
              return _buildField(
                  optionNames[index], pageOptions[index], context);
            },
          )
        ],
      ),
    )),
  );
}

Widget _buildField(String fieldString, page, BuildContext context) {
  return InkWell(
    onTap: () {
      Navigator.of(context).push(MaterialPageRoute(builder: (context) => page));
    },
    child: Container(
      height: 80.h,
      alignment: Alignment.centerLeft,
      child: Padding(
          padding: EdgeInsets.only(left: 40.w),
          child: Text(
            fieldString,
            textAlign: TextAlign.left,
          )),
    ),
  );
}
