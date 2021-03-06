
import 'package:boba_buddy/Screens/store_page.dart';
import 'package:boba_buddy/core/model/models.dart';
import 'package:boba_buddy/core/repository/item_repository.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:provider/src/provider.dart';

class PriceUpdaterPage extends StatelessWidget {
  final String imageSrc;
  final Item item;

  const PriceUpdaterPage({Key? key, required this.imageSrc, required this.item})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    double deviceWidth = MediaQuery.of(context).size.width;
    double deviceHeight = MediaQuery.of(context).size.height;
    final newPriceController = TextEditingController();
    ItemRepository itemRepository = context.read<ItemRepository>();

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
                    child: Text(
                      "Update Price",
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
                child: Padding(
                  padding: const EdgeInsets.only(top: 59, right: 20),
                  child: RichText(
                    text: TextSpan(
                        style: TextStyle(
                          color: Colors.grey.shade700,
                          fontSize: 17,
                          fontFamily: "Josefin Sans",
                        ),
                        text: "Cancel",
                        recognizer: TapGestureRecognizer()
                          ..onTap = () {
                            Navigator.pop(context);
                          }),
                  ),
                )),
            Positioned(
              width: deviceWidth - 80,
              height: 100,
              top: 125,
              left: 43,
              //TODO: is this manual refresh done purposefully to keep price up to date?
              child: FutureBuilder(
                future: itemRepository.getItemById(item.id),
                builder:
                    (BuildContext context, AsyncSnapshot<dynamic> snapshot) {
                  if (!snapshot.hasData) {
                    return const Center(child: CircularProgressIndicator());
                  } else {
                    return TextFormField(
                      enabled: false,
                      decoration: InputDecoration(
                          prefixIcon: const Icon(
                            Icons.attach_money_outlined,
                            color: Color.fromRGBO(86, 99, 255, 1.0),
                          ),
                          border: const OutlineInputBorder(),
                          labelText: snapshot.data.price.toString(),
                          disabledBorder: InputBorder.none),
                    );
                  }
                },
              ),
              // child: TextFormField(
              //   enabled: false,
              //   decoration: InputDecoration(
              //     prefixIcon: const Icon(
              //       Icons.attach_money_outlined,
              //       color: Color.fromRGBO(86, 99, 255, 1.0),
              //     ),
              //       border: OutlineInputBorder(),
              //       labelText: "Current Price", //TODO: PlaceHolder for current price data
              //       disabledBorder: InputBorder.none
              //
              //   ),
              // ),
            ),
            Positioned(
              width: deviceWidth - 80,
              height: 100,
              top: 225,
              left: 43,
              child: TextField(
                controller: newPriceController,
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
                    labelText: 'New Price'),
              ),
            ),
            Positioned(
              bottom: 300,
              left: 50,
              child: ElevatedButton(
                onPressed: () async {
                  Item refreshedItem = await itemRepository.updateItemPrice(item.id, double.parse(newPriceController.text));

                  Fluttertoast.showToast(
                    msg: "Price Updated Successfully",
                    toastLength: Toast.LENGTH_SHORT,
                    gravity: ToastGravity.CENTER,
                  );

                  Navigator.pop(context);
                  Route route = MaterialPageRoute(
                      builder: (context) => StorePage(
                            item: refreshedItem,
                            store: refreshedItem.store!,
                            imageSrc: imageSrc,
                          ));
                  Navigator.pushReplacement(context, route);
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
