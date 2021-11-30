import 'dart:ui';

import 'package:boba_buddy/utils/services/auth/firebaseauth.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';
import 'package:fluttertoast/fluttertoast.dart';

class CreateAccountPage extends StatelessWidget {
  CreateAccountPage({Key? key}) : super(key: key);
  final Auth _auth = Auth();
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  TextEditingController confirmPasswordController = TextEditingController();
  TextEditingController nameController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return ScreenUtilInit(
      designSize: const Size(393, 830),
      builder: () => MaterialApp(
        debugShowCheckedModeBanner: false,
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: Scaffold(
          resizeToAvoidBottomInset: false,
          body: SingleChildScrollView(
            child: Stack(children: [
              Container(
                width: 400.w,
                height: 1000.h,
                decoration: const BoxDecoration(
                  image: DecorationImage(
                      image:
                          AssetImage("assets/images/BubbleTeaBackground.png"),
                      fit: BoxFit.cover),
                ),
              ),
              Padding(
                padding: EdgeInsets.only(top: 100.h, left: 50.w, right: 50.w),
                child: const Text(
                  "Boba Buddy",
                  style: TextStyle(
                      fontFamily: "Josefin Sans",
                      color: Colors.white,
                      fontSize: 50,
                      fontWeight: FontWeight.bold),
                ),
              ),
              Padding(
                padding: EdgeInsets.only(top: 250.h),
                child: Center(
                  child: Column(
                    children: [
                      _buildTextField(
                          context,
                          "Name",
                          const Icon(
                            Icons.person_outline,
                            color: Colors.white,
                          ),
                          nameController),
                      _buildTextField(
                          context,
                          "Email",
                          const Icon(
                            Icons.mail_outline,
                            color: Colors.white,
                          ),
                          emailController),
                      _buildTextField(
                          context,
                          "Password",
                          const Icon(
                            Icons.lock_outlined,
                            color: Colors.white,
                          ),
                          passwordController,
                          obscure: true),
                      _buildTextField(
                          context,
                          "Confirm Password",
                          const Icon(
                            Icons.lock_outlined,
                            color: Colors.white,
                          ),
                          confirmPasswordController,
                          obscure: true),
                      Padding(
                          padding: EdgeInsets.only(top: 35.h),
                          child: ElevatedButton(
                            onPressed: () async {
                              if (_fieldCheck(
                                  passwordController.text,
                                  emailController.text,
                                  nameController.text,
                                  confirmPasswordController.text)) {
                                _buildErrorToast("Please fill in all fields");
                                return;
                              } else if (!_emailValid(emailController.text)) {
                                _buildErrorToast("Please enter valid email");
                                return;
                              } else if (passwordController.text !=
                                  confirmPasswordController.text) {
                                _buildErrorToast(
                                    "Passwords don't match. Please try again");
                                confirmPasswordController.clear();
                                return;
                              }

                              Enum response = await _auth.createAccount(
                                  email: emailController.text,
                                  password: passwordController.text,
                                  name: nameController.text);

                              switch (response) {
                                case Response.weakPassword:
                                  {
                                    _buildErrorToast(
                                        "Password Too weak. Please try again");
                                    passwordController.clear();
                                  }
                                  break;
                                case Response.emailInUse:
                                  {
                                    _buildErrorToast(
                                        "Email already in use. Please try again with another email");
                                    emailController.clear();
                                  }
                                  break;
                                case Response.error:
                                  {
                                    _buildErrorToast(
                                        "Hmm something went wrong. Please try again");
                                  }
                                  break;
                                case Response.success:
                                  {
                                    _buildErrorToast(
                                        "Account created. Please sign in");
                                    Navigator.pop(context);
                                  }
                                  break;
                              }
                            },
                            child: const Text(
                              'Create Account',
                              style: TextStyle(
                                  fontFamily: "Josefin Sans",
                                  fontWeight: FontWeight.bold,
                                  fontSize: 20),
                            ),
                            style: ElevatedButton.styleFrom(
                              shape: const RoundedRectangleBorder(
                                  borderRadius:
                                      BorderRadius.all(Radius.circular(8))),
                              minimumSize: Size(300.w, 64.h),
                              primary: const Color.fromRGBO(86, 99, 255, 1),
                            ),
                          )),
                      Padding(
                        padding: EdgeInsets.only(top: 15.h),
                        child: TextButton(
                          onPressed: () {
                            Navigator.pop(context);
                          },
                          style: ButtonStyle(
                            overlayColor:
                                MaterialStateProperty.resolveWith<Color?>(
                                    (Set<MaterialState> states) {
                              if (states.contains(MaterialState.pressed)) {
                                return Colors.white.withOpacity(0.2);
                              }
                              return null; // Defer to the widget's default.
                            }),
                          ),
                          child: const Text(
                            'Already have an account?',
                            style: TextStyle(
                                decoration: TextDecoration.underline,
                                fontSize: 20,
                                fontFamily: "Josefin Sans",
                                fontWeight: FontWeight.bold,
                                color: Colors.white),
                          ),
                        ),
                      )
                    ],
                  ),
                ),
              )
            ]),
          ),
        ),
      ),
    );
  }

  Widget _buildTextField(BuildContext context, String hintText, Icon icon,
      TextEditingController controller,
      {obscure = false}) {
    return Padding(
      padding: EdgeInsets.only(left: 35.w, right: 35.w, top: 25.h),
      child: TextFormField(
        controller: controller,
        obscureText: obscure,
        style: const TextStyle(color: Colors.white),
        decoration: InputDecoration(
            prefixIcon: icon,
            border: OutlineInputBorder(
              borderRadius: BorderRadius.circular(10.0),
              borderSide: const BorderSide(
                width: 0,
                style: BorderStyle.none,
              ),
            ),
            filled: true,
            hintStyle: const TextStyle(
                fontFamily: "Josefin Sans",
                fontSize: 20,
                color: Colors.white,
                fontWeight: FontWeight.bold),
            hintText: hintText,
            fillColor: Colors.grey.withOpacity(.4)),
      ),
    );
  }
}

_buildErrorToast(String message) {
  Fluttertoast.showToast(
    msg: message,
    toastLength: Toast.LENGTH_SHORT,
    gravity: ToastGravity.CENTER,
  );
}

bool _fieldCheck(
    String password, String email, String name, String confirmPassword) {
  return (password.isEmpty ||
      email.isEmpty ||
      name.isEmpty ||
      confirmPassword.isEmpty);
}

bool _emailValid(email) {
  return RegExp(
          r"^[a-zA-Z0-9.a-zA-Z0-9.!#$%&'*+-/=?^_`{|}~]+@[a-zA-Z0-9]+\.[a-zA-Z]+")
      .hasMatch(email);
}
