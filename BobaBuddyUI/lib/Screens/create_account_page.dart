import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class CreateAccountPage extends StatelessWidget{

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      //title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: Scaffold(
        resizeToAvoidBottomInset: false,
//todo this is copied from login page. Make it a create account page
        //appBar: AppBar(title: Text("A Simple App Stateless Widget")),
        body: Stack(
            children:[ Container(
              width: 400,
              height: 1000,
              decoration: const BoxDecoration(
                image: DecorationImage(
                    image: AssetImage("assets/images/BubbleTeaBackground.png"),
                    fit: BoxFit.cover
                ),
              ),
              // child: BackdropFilter(
              //   filter: ImageFilter.blur(sigmaX: 0.5, sigmaY: 0.5),
              //   child: Container(
              //     color: Colors.black.withOpacity(0.3),
              //
              //   ),
              // ),


            ),

              const Padding(padding: EdgeInsets.only(top: 100, left: 50, right: 50),
                child: Text("Boba Buddy", style: TextStyle(fontFamily: "Josefin Sans", color: Colors.white, fontSize: 50, fontWeight: FontWeight.bold ),),
              ),

              Padding(padding: const EdgeInsets.only(top: 250),
                child: Center(
                  child: Column(
                    children: [

                      _buildTextField(context, "Name", const Icon(Icons.person_outline, color: Colors.white,)),
                      // Padding(padding: const EdgeInsets.only(left: 35, right: 35),
                      //
                      //   child: TextFormField(
                      //
                      //
                      //
                      //     decoration: InputDecoration(
                      //
                      //
                      //         prefixIcon: const Icon(Icons.person_outline, color: Colors.white,),
                      //
                      //         border: OutlineInputBorder(
                      //           borderRadius: BorderRadius.circular(10.0),
                      //           borderSide: const BorderSide(
                      //             width: 0,
                      //             style: BorderStyle.none,
                      //           ),
                      //         ),
                      //         filled: true,
                      //         hintStyle: const TextStyle(fontFamily: "Josefin Sans" , fontSize: 20, color: Colors.white, fontWeight: FontWeight.bold),
                      //         hintText: "Name",
                      //         fillColor: Colors.grey.withOpacity(.4)),
                      //
                      //   ),
                      // ),


                      _buildTextField(context, "Email", const Icon(Icons.mail_outline, color: Colors.white,)),

                      // Padding(padding: const EdgeInsets.only(left: 35, right: 35, top: 30),
                      //
                      //   child: TextFormField(
                      //
                      //
                      //
                      //     decoration: InputDecoration(
                      //
                      //
                      //         prefixIcon: const Icon(Icons.mail_outline, color: Colors.white,),
                      //
                      //         border: OutlineInputBorder(
                      //           borderRadius: BorderRadius.circular(10.0),
                      //           borderSide: const BorderSide(
                      //             width: 0,
                      //             style: BorderStyle.none,
                      //           ),
                      //         ),
                      //         filled: true,
                      //         hintStyle: const TextStyle(fontFamily: "Josefin Sans" , fontSize: 20, color: Colors.white, fontWeight: FontWeight.bold),
                      //         hintText: "Email",
                      //         fillColor: Colors.grey.withOpacity(.4)),
                      //
                      //   ),
                      // ),

                      _buildTextField(context, "Password", const Icon(Icons.lock_outlined, color: Colors.white,)),


                      // Padding(padding: const EdgeInsets.only(left: 35, right: 35, top: 30),
                      //
                      //   child: TextFormField(
                      //
                      //
                      //
                      //     decoration: InputDecoration(
                      //
                      //
                      //         prefixIcon: const Icon(Icons.lock_outlined, color: Colors.white,),
                      //
                      //         border: OutlineInputBorder(
                      //           borderRadius: BorderRadius.circular(10.0),
                      //           borderSide: const BorderSide(
                      //             width: 0,
                      //             style: BorderStyle.none,
                      //           ),
                      //         ),
                      //         filled: true,
                      //         hintStyle: const TextStyle(fontFamily: "Josefin Sans" , fontSize: 20, color: Colors.white, fontWeight: FontWeight.bold),
                      //         hintText: "Password",
                      //         fillColor: Colors.grey.withOpacity(.4)),
                      //
                      //   ),
                      // ),

                      _buildTextField(context, "Confirm Password", const Icon(Icons.lock_outlined, color: Colors.white,)),

                      // Padding(padding: const EdgeInsets.only(left: 35, right: 35, top: 30),
                      //
                      //   child: TextFormField(
                      //
                      //
                      //
                      //     decoration: InputDecoration(
                      //
                      //
                      //         prefixIcon: const Icon(Icons.lock_outlined, color: Colors.white,),
                      //
                      //         border: OutlineInputBorder(
                      //           borderRadius: BorderRadius.circular(10.0),
                      //           borderSide: const BorderSide(
                      //             width: 0,
                      //             style: BorderStyle.none,
                      //           ),
                      //         ),
                      //         filled: true,
                      //         hintStyle: const TextStyle(fontFamily: "Josefin Sans" , fontSize: 20, color: Colors.white, fontWeight: FontWeight.bold),
                      //         hintText: "Confirm Password",
                      //         fillColor: Colors.grey.withOpacity(.4)),
                      //
                      //   ),
                      // ),


                      Padding(padding: const EdgeInsets.only(top: 35),
                          child: ElevatedButton(onPressed: (){Navigator.pop(context);},

                            child: const Text('Create Account', style: TextStyle(fontFamily:"Josefin Sans", fontWeight:
                            FontWeight.bold, fontSize: 20

                            ),
                            ),
                            style: ElevatedButton.styleFrom(shape: const RoundedRectangleBorder(borderRadius: BorderRadius.all(Radius.circular(8))),
                              minimumSize: const Size(300, 64), primary: const Color.fromRGBO(86, 99, 255, 1),
                            ),

                          )

                      ),

                      Padding(padding: const EdgeInsets.only(top: 15),
                        child: TextButton(onPressed: () { Navigator.pop(context); },
                          style: ButtonStyle(overlayColor:
                          MaterialStateProperty.resolveWith<Color?>(
                                  (Set<MaterialState> states) {
                                if (states.contains(MaterialState.pressed)) {
                                  return Colors.white.withOpacity(0.2);
                                }
                                return null; // Defer to the widget's default.
                              }),),

                          child: const Text('Already have an account?', style: TextStyle(decoration: TextDecoration.underline, fontSize: 20, fontFamily: "Josefin Sans", fontWeight:
                          FontWeight.bold, color: Colors.white),),

                        ),
                      )




                    ],
                  ),
                ),
              )


            ]
        ),


      ),




    );




  }


  Widget _buildTextField(BuildContext context, String hintText, Icon icon ) {
    return Padding(padding: const EdgeInsets.only(left: 35, right: 35, top: 25),

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
            hintStyle: const TextStyle(fontFamily: "Josefin Sans" , fontSize: 20, color: Colors.white, fontWeight: FontWeight.bold),
            hintText: hintText,
            fillColor: Colors.grey.withOpacity(.4)),

      ),
    );

  }

}