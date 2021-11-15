import 'dart:ui';

import 'package:boba_buddy/Screens/home_screen.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'create_account_page.dart';

class LoginPage extends StatelessWidget {

  @override
  Widget build(BuildContext context) {
    //todo migrate this page to login page file
    // todo set up proper navigator tree
    // todo photoshop background instead of driwing filter as it has weird effect on top of image
    return  Scaffold(
        resizeToAvoidBottomInset: false,

        //appBar: AppBar(title: Text("A Simple App Stateless Widget")),
        body: Stack(
            children: [ Container(
              width: 400,
              height: 1000,
              decoration: const BoxDecoration(
                image: DecorationImage(
                    image: AssetImage("assets/images/BubbleTeaBackground.png"),
                    fit: BoxFit.cover
                ),
              ),

            ),

              const Padding(
                padding: EdgeInsets.only(top: 100, left: 50, right: 50),
                child: Text("Boba Buddy", style: TextStyle(
                    fontFamily: "Josefin Sans",
                    color: Colors.white,
                    fontSize: 50,
                    fontWeight: FontWeight.bold),),
              ),

              Padding(padding: const EdgeInsets.only(top: 300),
                child: Center(
                  child: Column(
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(left: 35, right: 35),

                        child: TextFormField(
                          style: const TextStyle(color: Colors.white),


                          decoration: InputDecoration(


                              prefixIcon: const Icon(Icons.email_outlined,
                                color: Colors.white,),

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
                              hintText: "Email",
                              fillColor: Colors.grey.withOpacity(.4)),

                        ),
                      ),


                      Padding(padding: const EdgeInsets.only(
                          left: 35, right: 35, top: 30),

                        child: TextFormField(
                          obscureText: true,
                          style: const TextStyle(color: Colors.white),


                          decoration: InputDecoration(


                              prefixIcon: const Icon(Icons.lock_outlined,
                                color: Colors.white,),

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
                              hintText: "Password",
                              fillColor: Colors.grey.withOpacity(.4)),

                        ),
                      ),

                      Padding(padding: const EdgeInsets.only(top: 160),
                          child: ElevatedButton(onPressed: () {
                            Navigator.of(context).push(MaterialPageRoute(
                                builder: (context) => const HomeScreen()));
                          },

                            child: const Text('Login', style: TextStyle(
                                fontFamily: "Josefin Sans", fontWeight:
                            FontWeight.bold, fontSize: 20

                            ),
                            ),
                            style: ElevatedButton.styleFrom(
                              shape: const RoundedRectangleBorder(
                                  borderRadius: BorderRadius.all(
                                      Radius.circular(8))),
                              minimumSize: const Size(300, 64),
                              primary: const Color.fromRGBO(86, 99, 255, 1),
                            ),

                          )

                      ),

                      Padding(padding: const EdgeInsets.only(top: 25),
                        child: TextButton(onPressed: () {
                          Navigator.of(context).push(MaterialPageRoute(
                              builder: (context) => CreateAccountPage()));
                        }, //todo add navigation to create account page
                          style: ButtonStyle(overlayColor:
                          MaterialStateProperty.resolveWith<Color?>(
                                  (Set<MaterialState> states) {
                                if (states.contains(MaterialState.pressed)) {
                                  return Colors.white.withOpacity(0.2);
                                }
                                return null; // Defer to the widget's default.
                              }),),

                          child: const Text(
                            'Create New Account', style: TextStyle(
                              decoration: TextDecoration.underline,
                              fontSize: 20,
                              fontFamily: "Josefin Sans",
                              fontWeight:
                              FontWeight.bold,
                              color: Colors.white),),

                        ),
                      )


                    ],
                  ),
                ),
              )


            ]
        ),


      );


  }

}