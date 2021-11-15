import 'dart:async';
import 'dart:convert' as convert;
import 'dart:convert';
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


    return cleanedData["content"];

  }


  Future<List> getStoreNames() async {
    var data = await http.get(Uri.parse(url + '/stores'));

    var fixed = convert.jsonDecode(data.body);

    print(fixed);
    var stores = fixed["content"];
    //print(stores);

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

    var data = await http.get(Uri.parse(url + '/items/?name-contain=$searchTerm'));



    var fixed = convert.jsonDecode(data.body);

    print("0000000");
    print(fixed);

    try{
      fixed["content"];
    }catch(e){
      return [];
    }

    int counttt = 0;

    for(var item in fixed["content"]){
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






        fixed["content"][counttt]["store"] = cleaned;
       // print(0000000);
        //print(fixed);


      }
    }

    //print("-------------------");
    //print(fixed['_embedded']["items"]);
    return(_sortItems(fixed["content"]));
    // //fixed = _sortItems(fixed);
    // return fixed['_embedded']["items"];

  }

  Future getItemById(String itemId) async {
    var data = await http.get(Uri.parse(url + '/items/$itemId'));



    print("909090");
    print(data.body);
    var fixed = convert.jsonDecode(data.body);
    print("-0-0-0-0-0-");
    print(fixed);
    //print(fixed["content"][0]);
    //var another =

    //print(itemId);
    //print(fixed);
    return fixed;
  }

  updateItemPrice(String itemId, double newPrice) async {


    try{

      var address = Uri.parse(url + '/items/$itemId');

      Map item = await getItemById(itemId);


      // Map data = {
      //   "id": "35fa9310-a72a-4573-bd0a-6760bd873d95",
      //   "avgRating": 0.0,
      //   "name": "Pearl Latte",
      //   "ratings": [],
      //   "price": 90.0,
      //   "store": {
      //     "id": "8f4ca86c-ee1f-4fa9-859c-410d03d25341",
      //     "avgRating": 0.0,
      //     "name": "Kung Fu Tea on Spadina",
      //     "ratings": [],
      //     "location": "264 Spadina Ave., Toronto, ON M5T 2E4",
      //     "menu": [
      //       "35fa9310-a72a-4573-bd0a-6760bd873d95",
      //       "2818cd8e-0167-4a10-a03a-a3a5d9959637",
      //       "af3051de-4b71-4e64-85a0-931344824e98",
      //       "36e83670-8aec-4e0b-9a41-4987ad4ce85b",
      //       "d7746cfe-fb9b-4a24-b6dc-5af7ce1e4f14"
      //     ],
      //     "links": []
      //   },
      //   "links": [],
      //   "links": [
      //     {
      //       "rel": "self",
      //       "href": "http://127.0.0.1:8080/items/35fa9310-a72a-4573-bd0a-6760bd873d95"
      //     },
      //     {
      //       "rel": "items",
      //       "href": "http://127.0.0.1:8080/items"
      //     },
      //     {
      //       "rel": "stores",
      //       "href": "http://127.0.0.1:8080/stores/8f4ca86c-ee1f-4fa9-859c-410d03d25341"
      //     },
      //     {
      //       "rel": "ratings",
      //       "href": "http://127.0.0.1:8080/items/35fa9310-a72a-4573-bd0a-6760bd873d95/ratings"
      //     }
      //   ]
      // };
      item['price'] = newPrice;
      //encode Map to JSON
      var body = json.encode(item);


      var response = await http.put(address,
          headers: {"Content-Type": "application/json"},
          body: body
      );
      print("${response.statusCode}");
      //print("${response.body}");
      //return response;


    }catch(e){

      print(e);
    }



    // try {
    //   print("000000");
    //   print(newItem);
    //
    //   var something = await http.get(Uri.parse(url + '/items/35fa9310-a72a-4573-bd0a-6760bd873d95'));
    //
    //
    //   var test = convert.jsonEncode(newItem);
    //   print("101010101");
    //   print(something.body);
    //
    //   print("9889898");
    //   var newthing = convert.jsonDecode(something.body);
    //   var converted = convert.jsonEncode(newthing);
    //   print(converted.runtimeType);
    //   await http.put(Uri.parse(url + '/items/35fa9310-a72a-4573-bd0a-6760bd873d95'),
    //       body: convert.json.encode(something));
    //   print('Success');
    //   //return true;
    // }catch(e){
    //   print(e);
    //   //return false;
    //
    // }


  }

  Future getOneItemFromStore(String storeId) async {
    var data = await http.get(Uri.parse(url + '/stores/$storeId/items'));

    //Map cleanedData = json.decode(data.body);



    print("909090");
    print(data.body);
    var fixed = convert.jsonDecode(data.body);


    try{
      print('cleaned');
      print(fixed["content"][0]["id"]);

      fixed["content"][0];
    }catch(e){
      return [];
    }

    //print(cleanedData);
    return fixed["content"][0];

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