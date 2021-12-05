import 'package:boba_buddy/core/repository/store_repository.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:provider/src/provider.dart';

class CreateStorePage extends StatelessWidget {
  const CreateStorePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    TextEditingController storeNameController = TextEditingController();
    TextEditingController storeAddressController = TextEditingController();
    TextEditingController itemNameController = TextEditingController();
    TextEditingController itempriceController = TextEditingController();

    List<Map> storeItems = [];

    return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: AppBar(
        centerTitle: true,
        backgroundColor: Colors.white,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, color: Colors.black),
          onPressed: () => Navigator.of(context).pop(),
        ),
        title: const Text(
          "Create Store",
          style: TextStyle(color: Colors.black),
        ),
      ),
      body: Column(
        children: [
          const SizedBox(
            height: 20,
          ),
          TextFormField(
            controller: storeNameController,
            decoration: const InputDecoration(
                contentPadding: EdgeInsets.only(left: 20),
                hintText: "Store name",
                labelText: "Store Name *"),
          ),
          const SizedBox(height: 15),
          TextFormField(
            controller: storeAddressController,
            decoration: const InputDecoration(
                contentPadding: EdgeInsets.only(left: 20),
                hintText: "Store Address",
                labelText: "Store Address *"),
          ),
          ExpansionTile(
            title: const Text('Items'),
            subtitle: const Text('Add Items To The Store'),
            children: <Widget>[
              const SizedBox(
                height: 20,
              ),

              TextFormField(
                controller: itemNameController,
                decoration: const InputDecoration(
                    contentPadding: EdgeInsets.only(left: 20),
                    hintText: "Item name",
                    labelText: "Item Name *"),
              ),

              const SizedBox(height: 15),

              TextFormField(
                controller: itempriceController,
                keyboardType: TextInputType.number,
                decoration: const InputDecoration(
                    contentPadding: EdgeInsets.only(left: 20),
                    hintText: "Item Price",
                    labelText: "Item Price *"),
              ),
              //TODO: add field for custom image src

              const SizedBox(height: 30),
              TextButton(
                style: TextButton.styleFrom(
                  textStyle: const TextStyle(fontSize: 20),
                ),
                onPressed: () {
                  storeItems.add({
                    "name": itemNameController.text,
                    "price": double.parse(itempriceController.text)
                  });
                  itempriceController.clear();
                  itemNameController.clear();
                  _buildToast(message: "Item Recorded", color: Colors.green);
                },
                child: const Text('Add Item'),
              ),
            ],
          ),
          TextButton(
            style: TextButton.styleFrom(
              textStyle: const TextStyle(fontSize: 20),
            ),
            onPressed: () {
              if (storeNameController.text.isEmpty ||
                  storeAddressController.text.isEmpty) {
                _buildToast(
                    message: "Please Fill Out All Required Fields",
                    color: Colors.red);
                return;
              } else if (storeItems.isNotEmpty) {
                context.read<StoreRepository>().createStore(
                    storeNameController.text,
                    storeAddressController.text,
                    storeItems);
                _buildToast(
                    message: "Store created succesfully", color: Colors.green);
                Navigator.pop(context);
              } else {
                _buildToast(
                    message: "You must add at least one item to the store",
                    color: Colors.red);
              }
            },
            child: const Text('Create Store'),
          ),
        ],
      ),
    );
  }
}

_buildToast({required String message, required Color color}) {
  Fluttertoast.showToast(
      msg: message,
      toastLength: Toast.LENGTH_SHORT,
      gravity: ToastGravity.CENTER,
      timeInSecForIosWeb: 1,
      backgroundColor: color,
      textColor: Colors.white,
      fontSize: 16.0);
}
