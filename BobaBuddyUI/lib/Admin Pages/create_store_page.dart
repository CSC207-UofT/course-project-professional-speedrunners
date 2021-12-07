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
    TextEditingController storeImageUrlController = TextEditingController();
    TextEditingController itemNameController = TextEditingController();
    TextEditingController itemPriceController = TextEditingController();
    TextEditingController itemUrlController = TextEditingController();

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
          const SizedBox(height: 15),
          TextFormField(
            controller: storeImageUrlController,
            decoration: const InputDecoration(
                contentPadding: EdgeInsets.only(left: 20),
                hintText: "Store Image Url",
                labelText: "Store Image Url *"),
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
                controller: itemPriceController,
                keyboardType: TextInputType.number,
                decoration: const InputDecoration(
                    contentPadding: EdgeInsets.only(left: 20),
                    hintText: "Item Price",
                    labelText: "Item Price *"),
              ),
              const SizedBox(height: 15),
              TextFormField(
                controller: itemUrlController,
                decoration: const InputDecoration(
                    contentPadding: EdgeInsets.only(left: 20),
                    hintText: "Item Image Url",
                    labelText: "Item Image Url *"),
              ),
              const SizedBox(height: 30),
              TextButton(
                style: TextButton.styleFrom(
                  textStyle: const TextStyle(fontSize: 20),
                ),
                onPressed: () {
                  if (itemUrlController.text.trim().isEmpty ||
                      itemNameController.text.trim().isEmpty ||
                      itemPriceController.text.trim().isEmpty) {
                    _buildToast(
                        message: "Please fill out all item fields",
                        color: Colors.red);
                    return;
                  }
                  storeItems.add({
                    "name": itemNameController.text,
                    "price": double.parse(itemPriceController.text),
                    "imageUrl": itemUrlController.text
                  });
                  itemPriceController.clear();
                  itemNameController.clear();
                  itemUrlController.clear();
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
                    storeItems,
                    storeImageUrlController.text);
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
