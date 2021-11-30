import 'package:boba_buddy/core/repository/authentication_repository.dart';
import 'package:boba_buddy/core/repository/user_repository.dart';
import 'package:boba_buddy/modules/app/bloc/app_bloc.dart';
import 'package:boba_buddy/modules/profile/bloc/profile_bloc.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

class ProfilePage extends StatelessWidget {
  const ProfilePage({Key? key}) : super(key: key);

  static Page page() => const MaterialPage<void>(child: ProfilePage());


  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        return ProfileBloc(userRepository: context.read<UserRepository>(),
        authenticationRepository: context.read<AuthenticationRepository>())
          ..add(FetchUserInfo());
      },
      child: ProfilePageView(),

    );
  }
}

class ProfilePageView extends StatelessWidget {

  final List fields = ["Change Password", "Change Email", "Change Username"];

  ProfilePageView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ScreenUtilInit(
      designSize: const Size(393, 830),
      builder: () => Scaffold(
        body: Center(
            child: SafeArea(
                child: Column(children: [
                  BlocBuilder<ProfileBloc, ProfileState>(
                    builder:(context, state) {
                      double deviceWidth = MediaQuery
                          .of(context)
                          .size
                          .width;
                      switch(state.status){
                        case ProfileStatus.loaded:
                          return (SafeArea(
                            child: Center(
                              child: Column(
                                children: [
                                  Padding(
                                    padding: EdgeInsets.only(top: 80.h),
                                    child: SizedBox(
                                      height: 115,
                                      width: 115,
                                      child: Stack(
                                        clipBehavior: Clip.none,
                                        fit: StackFit.expand,
                                        children: [
                                          const CircleAvatar(
                                            backgroundImage: AssetImage(
                                                "assets/images/defaultProfilePic.png"),
                                          ),
                                          Positioned(
                                              bottom: 0,
                                              right: -25,
                                              child: RawMaterialButton(
                                                onPressed: () {},
                                                elevation: 2.0,
                                                fillColor: const Color(0xFFF5F6F9),
                                                child: const Icon(
                                                  Icons.camera_alt_outlined,
                                                  color: Colors.blue,
                                                  size: 20,
                                                ),
                                                padding: const EdgeInsets.all(15.0),
                                                shape: const CircleBorder(),
                                              )),
                                        ],
                                      ),
                                    ),
                                  ),
                                  Padding(
                                    padding: EdgeInsets.only(top: 10.h),
                                    child: Text(
                                      state.user.name ?? state.user.email,
                                      style: const TextStyle(fontSize: 20),
                                    ),
                                  ),
                                  Padding(
                                      padding: EdgeInsets.only(top: 10.h),
                                      child: Text(state.user.email)),
                                  Padding(
                                    padding: EdgeInsets.only(top: 60.h),
                                    child: ListView.builder(
                                      itemCount: fields.length,
                                      shrinkWrap: true,
                                      physics: const NeverScrollableScrollPhysics(),
                                      itemBuilder: (BuildContext context, int index) {
                                        return _buildField(fields[index]);
                                      },
                                    ),
                                  ),
                                  Padding(
                                    padding: EdgeInsets.only(top: 40.h),
                                    child: ElevatedButton(
                                      onPressed: () {
                                        context.read<AppBloc>().add(AppLogoutRequested());
                                      },
                                      child: const Text(
                                        'Logout',
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
                                ],
                              ),
                            ),
                          ));
                        default:
                          return const CircularProgressIndicator();
                      }
                    },
                  ),
                ]
                )
            )
        ),
      ),
    );
  }
  //TODO: finish settings page -- functionality isn't crucial
}

Widget _buildField(String fieldString) {
  return InkWell(
    onTap: () {
      print("tapped");
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
