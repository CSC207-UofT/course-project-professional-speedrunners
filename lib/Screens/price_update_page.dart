import 'package:boba_buddy/Database/database.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

class PriceUpdaterPage extends StatelessWidget {
  final String itemId;

  const PriceUpdaterPage({Key? key, required this.itemId}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    double deviceWidth = MediaQuery.of(context).size.width;
    double deviceHeight = MediaQuery.of(context).size.height;
    return Scaffold(
      resizeToAvoidBottomInset: false,

        body: Stack(
      children: [
        SizedBox(
          width: deviceWidth,
          height: deviceHeight,
        ),


        const Align(
            alignment: Alignment.topCenter,
            child: Padding(
                padding: EdgeInsets.only(top: 57),
                child: Text("Update Price",
                  style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.w600,
                    fontFamily: "Josefin Sans",
                  ),
                ))),

        Positioned(
            top: 40,
            child: IconButton(
                onPressed: () {
                  Navigator.pop(context);
                },
                icon: const Icon(
                  Icons.arrow_back,
                  size: 30,
                  color: Colors.black,
                ))),

        Align(
            alignment: Alignment.topRight,
            child: Padding(padding: EdgeInsets.only(top: 59,
              right: 20
            ),
              child: RichText(
          text: TextSpan(style: TextStyle(color: Colors.grey.shade700,
          fontSize: 17,
            fontFamily: "Josefin Sans",
          ),
              text: "Cancel",
              recognizer: TapGestureRecognizer()
                  ..onTap = (){
                Navigator.pop(context);
                  }

          ),
        ),
            )),

      Positioned(width: deviceWidth -80, height: 100, top: 125,
        left: 43,
        child: TextFormField(
          enabled: false,
          decoration: const InputDecoration(
            prefixIcon: Icon(
              Icons.attach_money_outlined,
              color: Color.fromRGBO(86, 99, 255, 1.0),
            ),
              border: OutlineInputBorder(),
              labelText: 'Current Price', //TODO: PlaceHolder for current price data
              disabledBorder: InputBorder.none

          ),
        ),
      ),
        Positioned(width: deviceWidth -80, height: 100, top: 225,
          left: 43,
          child: TextFormField(
            keyboardType: TextInputType.number,
            decoration: const InputDecoration(
              labelStyle: TextStyle(
                  //color: Colors.grey,
                fontFamily: "Josefin Sans",

              ),
                prefixIcon: Icon(
                  Icons.attach_money_outlined,
                  color: Color.fromRGBO(86, 99, 255, 1.0),
                ),
                border: OutlineInputBorder(),
                labelText: 'New Price'
            ),
          ),
        ),


        Positioned(bottom: 300, left: 50,
          child: ElevatedButton(
            onPressed: () async {

              Database db = Database();

              var item = await db.getItemById(itemId);
              item["price"] = 90.0;
              item["avgRating"] = 0.0;
              await db.updateItemPrice(itemId, item);



              Fluttertoast.showToast(msg: "Price Updated Successfully",
                toastLength: Toast.LENGTH_SHORT,
                gravity: ToastGravity.CENTER,
              );

              Navigator.pop(context);

            },
            child: const Text(
              'Update',
              style: TextStyle(
                  fontFamily: "Josefin Sans",
                  fontWeight: FontWeight.bold,
                  fontSize: 15),
            ),
            style: ElevatedButton.styleFrom(
              shape: const RoundedRectangleBorder(
                  borderRadius: BorderRadius.all(Radius.circular(8))),
              minimumSize: Size(deviceWidth - 100, 60),
              primary: const Color.fromRGBO(86, 99, 255, 1),
            ),
          ),
        ),













      ],
    ));
  }
}
