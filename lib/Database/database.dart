import 'dart:async';
import 'dart:convert' as convert;
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

class Database{

  final String url = "http://10.0.2.2:8080";

  Database();



  void run() async {

    var client = http.Client();
    //var url = Uri.http("127.0.0.1:8080", '/users');
    // print('asasas');
    // var test = await http.get(Uri.parse('http://10.0.2.2:8080/users'));

    // var uriResponse = await client.post(Uri.parse('http://10.0.2.2:8080/users'),
    //     body: {"email": "yeyesdsdsdsdsdsmas@gmail.com",
    //       "name": "yeyasmassdsde",
    //       "password": "yeyeasmas123"});


    var data = await http.get(Uri.parse(url + '/users'));

    var fixed = convert.jsonDecode(data.body);

  //getStoreNames();

  }

  Future<List> getStoreItems({required String storeId}) async {
    var data = await http.get(Uri.parse(url + '/stores/$storeId/items'));

    var cleanedData = convert.jsonDecode(data.body);


    return cleanedData['_embedded']['items'];

  }


  Future<List> getStoreNames() async {
    var data = await http.get(Uri.parse(url + '/stores'));

    var fixed = convert.jsonDecode(data.body);

    var stores = fixed['_embedded']['stores'];

    // List<String> storeNames= [];
    //
    // for(var store in stores){
    //   storeNames.add(store['name'].toString());
    // }

    //print(stores[0]['items'][0]);
    //print('+++++++___++++++');
    //print(stores[0]);
    // print(stores[0]["_links"]["items"]);
    // print(stores[3]["menu"]);
    return stores;

  }

  Future<List> itemSearch(String searchTerm) async {


    //default sorted by cheapest first

    var data = await http.get(Uri.parse(url + '/items/?name-contain=Milk'));



    var fixed = convert.jsonDecode(data.body);

    print("0000000");
    print(fixed);

    try{
      fixed['_embedded']["items"];
    }catch(e){
      return [];
    }

    int counttt = 0;

    for(var item in fixed['_embedded']["items"]){
      try{

        item["store"]["name"];
        counttt ++;
      }catch(e){

        var storeData = await http.get(Uri.parse(url + '/stores/${item['store']}'));
        var cleaned = convert.jsonDecode(storeData.body);
        // print("88888888");
        // print(fixed);
        // //fixed = fixed['_embedded']["items"][0];
        // print("00000000000000");
        // print(fixed);
        // item["store"] = fixed;
        // print("99999999");
        // print(item["store"]);
        // print(99999);
        // print(cleaned);






        fixed['_embedded']["items"][counttt]["store"] = cleaned;
       // print(0000000);
        //print(fixed);


      }
    }

    //print("-------------------");
    //print(fixed['_embedded']["items"]);
    return(_sortItems(fixed['_embedded']["items"]));
    // //fixed = _sortItems(fixed);
    // return fixed['_embedded']["items"];

  }

  Future getItemById(String itemId) async {
    var data = await http.get(Uri.parse(url + '/items/$itemId'));



    var fixed = convert.jsonDecode(data.body);

    //print(itemId);
    //print(fixed);
    return fixed;
  }

  Future<bool> updateItemPrice(String itemId, newItem) async {

    try {
      await http.put(Uri.parse(url + '/items/$itemId'),
          body: newItem);
      print('Success');
      return true;
    }catch(e){
      print(e);
      return false;

    }


  }

  Future getOneItemFromStore(String storeId) async {
    var data = await http.get(Uri.parse(url + '/stores/$storeId/items'));

    var cleanedData = convert.jsonDecode(data.body);


    try{
      cleanedData['_embedded']["items"][0];
    }catch(e){
      return [];
    }

    //print(cleanedData);
    return cleanedData['_embedded']['items'][0];

  }

  _sortItems(data){

    //sorts from lowest to highest price


    var copy = List.from(data);
    copy.sort((a, b) => a["price"].compareTo(b["price"]));
    return copy;

    //print(sorted);
    // List prices=[];
    // for(var item in data){
    //   prices.add(item["price"]);
    // }
    // prices.sort()




  }



}