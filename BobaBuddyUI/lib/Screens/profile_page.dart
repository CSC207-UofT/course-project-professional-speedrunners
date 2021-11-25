import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class ProfilePage extends StatelessWidget {
  ProfilePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    double deviceWidth = MediaQuery.of(context).size.width;
    //TODO: finish settings page -- functionality isn't crucial
    return ScreenUtilInit(
      designSize: const Size(393, 830),
      builder: () => Scaffold(
        body: Center(
            child: SafeArea(
          child: Column(
            //mainAxisAlignment: MainAxisAlignment.center,
            //crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              AppBar(
                iconTheme: const IconThemeData(color: Colors.black),
                backgroundColor: Colors.white,
                centerTitle: true,
                title: const Text(
                  "Profile",
                  style: TextStyle(color: Colors.black),
                ),
              ),
              Padding(
                padding: EdgeInsets.only(top: 35.h),
                child: Container(
                  color: Colors.grey.withOpacity(0.2),
                  width: deviceWidth,
                  height: 50.h,
                  child: Padding(
                      padding: EdgeInsets.only(left: 20.w),
                      child: Text(
                        'Account',
                        textAlign: TextAlign.left,
                      )),
                ),
              )
            ],
          ),
        )),
      ),
    );
  }
}

Widget _buildField() {
  return Container();
}
