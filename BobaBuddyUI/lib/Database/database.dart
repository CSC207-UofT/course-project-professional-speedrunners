import 'dart:async';
import 'dart:convert' as convert;
import 'dart:convert';
import 'dart:io';

import 'package:uuid/uuid.dart';
import 'package:http/http.dart' as http;

class Database {
  final String url = "http://10.0.2.2:8080";

  Database();

  ///Queries databse for items contained in a provided store
  Future<List> getStoreItems({required String storeId}) async {
    var data = await http.get(Uri.parse(url + '/stores/$storeId/items'));

    var cleanedData = convert.jsonDecode(data.body);

    // todo: Does not check if there are items present. We assume here that all stores contain at least one item. If changed implemented empty list handling needs to be done on full_menu_page as well
    return cleanedData["_embedded"]["items"];
  }

  Future<List> getStores() async {
    var data = await http.get(Uri.parse(url + '/stores'));

    var fixed = json.decode(data.body);

    return fixed['_embedded']['stores'];
  }

  /// Queries the databse for [searchTerm] and returns a list of items sorted by price high to low
  Future<List> itemSearch(String searchTerm) async {
    var data =
        await http.get(Uri.parse(url + '/items/?name-contain=$searchTerm'));

    var cleanData = convert.jsonDecode(data.body);

    try {
      //checks to ensure items are present in the search results
      cleanData["_embedded"]["items"];
    } catch (e) {
      return [];
    }

    return (_sortItemsLowToHigh(cleanData["_embedded"]["items"]));
  }

  /// Queries database and returns the item corresponding to [itemId]
  Future getItemById(String itemId) async {
    //todo: no error checking in place for invalid ids and no error handling on pages if invalid id is passed
    var data = await http.get(Uri.parse(url + '/items/$itemId'));

    var cleanData = convert.jsonDecode(data.body);

    return cleanData;
  }

  /// Updates item price corresponding to [itemId] with [newPrice]
  updateItemPrice(String itemId, double newPrice) async {
    //todo: add error handling and return a future rather than void to actually portray success and failure of update

    //todo: also add corresponding handling to pages
    try {
      var address = Uri.parse(url + '/items/$itemId');

      Map item = await getItemById(itemId);

      item['price'] = newPrice;
      //encode Map to JSON
      var body = json.encode(item);

      var response = await http.put(address,
          headers: {"Content-Type": "application/json"}, body: body);

      print("${response.statusCode}");
    } catch (e) {
      print(e);
    }
  }

  /// Gets 1 item from store corresponding to [storeId]
  Future getOneItemFromStore(String storeId) async {
    var data = await http.get(Uri.parse(url + '/stores/$storeId/items'));

    var cleanData = convert.jsonDecode(data.body);

    try {
      //Ensures at least one item present in store
      cleanData["content"][0];
    } catch (e) {
      return [];
    }

    return cleanData["content"][0];
  }

  ///Helper for sorting [items] by price low to high
  _sortItemsLowToHigh(items) {
    var copy = List.from(items);
    copy.sort((a, b) => a["price"].compareTo(b["price"]));
    return copy;
  }

///Deletes store from db
  deleteStore({required String storeId}) async {

    //todo: add success and failure returns
    var resp = await http.delete(Uri.parse(url + '/stores/$storeId'));

  }

  Future getUser(String email) async {
    var data = await http.get(Uri.parse(url + '/users/$email'));
    var cleanData = convert.jsonDecode(data.body);
    return cleanData;
  }

  addRating(int rating, String userEmail, String itemID) async {
    var uuid = const Uuid().v4();
    var body = json.encode({'rating': '$rating', 'id': uuid});
    var response = await http.post(Uri.parse(url + '/items/$itemID/ratings?createdBy=$userEmail'), headers: {HttpHeaders.CONTENT_TYPE: "application/json"},
        body: body);
    print('Response status: ${response.statusCode}');
    print('Response body: ${response.body}');
  }

  deleteRating({required String ratingID}) async {
    var resp = await http.delete(Uri.parse(url + '/ratings/$ratingID'));
    print("${resp.statusCode}");
  }

  changeUserRating(String ratingID, int rating) async{
    var body = json.encode({'rating': '$rating', 'id': ratingID});

    var response = await http.put(Uri.parse(url + '/ratings/$ratingID'),
        headers: {"Content-Type": "application/json"}, body: body);

    print("${response.statusCode}");
  }

  getUserRating (String email, String itemID) async {
    Map user = await getUser(email);
    //List<String>? ratings = user['ratings'].length != 0 ? List.from(user['ratings']) : null;
    //if (ratings == null){
    //  return null;
    //}
    for (int i = 0; i < user['ratings'].length; i++){
      Map rating = await getRating(user['ratings'][i]['id']);
      if (rating['ratableObject']['id'] == itemID){
        return Future.value(user['ratings'][i]);
      }
    }
    return Future.value(null);
  }

  Future getRating (String ratingID) async {
    var data = await http.get(Uri.parse(url + '/ratings/$ratingID'));
    var cleanData = convert.jsonDecode(data.body);
    return cleanData;
  }

  addUser (String email, String password, String name) async {
    var body = json.encode({'email': email,  'name': name,
      'password': password});
    var response = await http.post(Uri.parse(url + '/users'), headers: {HttpHeaders.CONTENT_TYPE: "application/json"}, body: body);
    print('Response status: ${response.statusCode}');
    print('Response body: ${response.body}');
  }
}
