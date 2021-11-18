import 'dart:ui';

import 'package:boba_buddy/Screens/home_screen.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'create_account_page.dart';

class LoginPage extends StatelessWidget {
  const LoginPage({Key? key}) : super(key: key);

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
                _buildTextField(context, "Email", const Icon(
                          Icons.email_outlined,
                          color: Colors.white,
                        ),),

                _buildTextField(context, "Password", const Icon(
                            Icons.lock_outlined,
                            color: Colors.white,
                          ),),

                Padding(
                    padding: const EdgeInsets.only(top: 120),
                    child: ElevatedButton(
                      onPressed: () {
                        Navigator.of(context).push(MaterialPageRoute(
                            builder: (context) => const HomeScreen()));
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
Widget _buildTextField(BuildContext context, String hintText, Icon icon) {
  return Padding(
    padding: const EdgeInsets.only(left: 35, right: 35, top: 25),
    child: TextFormField(
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

