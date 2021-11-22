import 'dart:ui';

import 'package:boba_buddy/Authentication/firebaseauth.dart';
import 'package:boba_buddy/Screens/home_screen.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

import 'create_account_page.dart';

class LoginPage extends StatelessWidget {
  LoginPage({Key? key}) : super(key: key);

  final Auth _auth = Auth();

  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    //todo: Textfields need controllers for capturing text.
    return Scaffold(
      resizeToAvoidBottomInset: false,
      body: Stack(children: [
        //Draws background image and sets stack parent
        Container(
          width: 400,
          height: 1000,
          decoration: const BoxDecoration(
            image: DecorationImage(
                image: AssetImage("assets/images/BubbleTeaBackground.png"),
                fit: BoxFit.cover),
          ),
        ),
        const Padding(
          padding: EdgeInsets.only(top: 100, left: 50, right: 50),
          child: Text(
            "Boba Buddy",
            style: TextStyle(
                fontFamily: "Josefin Sans",
                color: Colors.white,
                fontSize: 50,
                fontWeight: FontWeight.bold),
          ),
        ),
        Padding(
          padding: const EdgeInsets.only(top: 300),
          child: Center(
            child: Column(
              children: [
                _buildTextField(
                    context,
                    "Email",
                    const Icon(
                      Icons.email_outlined,
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
                Padding(
                    padding: const EdgeInsets.only(top: 120),
                    child: ElevatedButton(
                      onPressed: () async {
                        if (!_emailValid(emailController.text)) {
                          _buildErrorToast("Please enter valid email");
                          return;
                        }

                        if (emailController.text.isEmpty ||
                            passwordController.text.isEmpty) {
                          _buildErrorToast("Please fill in all fields");
                          return;
                        }

                        Enum response = await _auth.login(
                            email: emailController.text,
                            password: passwordController.text);

                        switch (response) {
                          case Response.success:
                            {
                              _buildErrorToast("Login Successful");
                              Navigator.of(context).pushReplacement(
                                  MaterialPageRoute(
                                      builder: (context) =>
                                          const HomeScreen()));
                            }
                            break;

                          case Response.incorrectUserOrPass:
                            {
                              _buildErrorToast("Incorrect email or password");
                            }
                            break;

                          case Response.userNotFound:
                            {
                              _buildErrorToast("User not found");
                            }
                            break;

                          case Response.error:
                            {
                              _buildErrorToast(
                                  "Oops something went wrong please try again");
                            }
                            break;
                        }
                      },
                      child: const Text(
                        'Login',
                        style: TextStyle(
                            fontFamily: "Josefin Sans",
                            fontWeight: FontWeight.bold,
                            fontSize: 20),
                      ),
                      style: ElevatedButton.styleFrom(
                        shape: const RoundedRectangleBorder(
                            borderRadius: BorderRadius.all(Radius.circular(8))),
                        minimumSize: const Size(300, 64),
                        primary: const Color.fromRGBO(86, 99, 255, 1),
                      ),
                    )),
                Padding(
                  padding: const EdgeInsets.only(top: 25),
                  child: TextButton(
                    onPressed: () {
                      Navigator.of(context).push(MaterialPageRoute(
                          builder: (context) => CreateAccountPage()));
                    },
                    style: ButtonStyle(
                      overlayColor: MaterialStateProperty.resolveWith<Color?>(
                          (Set<MaterialState> states) {
                        if (states.contains(MaterialState.pressed)) {
                          return Colors.white.withOpacity(0.2);
                        }
                        return null; // Defer to the widget's default.
                      }),
                    ),
                    child: const Text(
                      'Create New Account',
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
    );
  }
}

///Builder for TextFeilds
Widget _buildTextField(BuildContext context, String hintText, Icon icon,
    TextEditingController controller,
    {obscure = false}) {
  return Padding(
    padding: const EdgeInsets.only(left: 35, right: 35, top: 25),
    child: TextFormField(
      obscureText: obscure,
      controller: controller,
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

_buildErrorToast(String message) {
  Fluttertoast.showToast(
    msg: message,
    toastLength: Toast.LENGTH_SHORT,
    gravity: ToastGravity.CENTER,
  );
}

bool _emailValid(email) {
  return RegExp(
          r"^[a-zA-Z0-9.a-zA-Z0-9.!#$%&'*+-/=?^_`{|}~]+@[a-zA-Z0-9]+\.[a-zA-Z]+")
      .hasMatch(email);
}
