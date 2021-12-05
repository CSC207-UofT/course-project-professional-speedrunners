import 'dart:async';
import 'dart:convert' as convert;
import 'dart:convert';

import 'package:boba_buddy/core/model/models.dart';
import 'package:http/http.dart' as http;

class StoreApiClient {
  static const url = 'http://10.0.2.2:8080';
  final http.Client _httpClient;

  StoreApiClient({http.Client? httpClient})
      : _httpClient = httpClient ?? http.Client();

  Future<List<Store>> getStores() async {
    final response = await _httpClient.get(Uri.parse(url + '/stores'));
    if (response.statusCode == 200) {
      Iterable storesMap = convert.jsonDecode(response.body);
      return storesMap.map((e) => Store.fromJson(e)).toList();
    }
    throw Exception("Failed to fetch stores from server");
  }

  ///Deletes store from db
  deleteStore({required String storeId}) async {
    http.Response response =
        await _httpClient.delete(Uri.parse(url + '/stores/$storeId'));
    if (response.statusCode == 204) {
      // TODO: do sth to tell user operation was successful (fluttertoast?)
      return;
    }
    throw Exception("delete store operation failed.");
  }

  createStore(
      {required String storeName,
      required String storeAddress,
      required List storeItems}) async {
    var bodyJson =
        convert.jsonEncode({"name": storeName, "location": storeAddress});

    http.Response storeCreateResp = await _httpClient.post(
        Uri.parse(url + '/stores'),
        headers: {"Content-Type": "application/json"},
        body: bodyJson);

    var getStoreIdResp =
        await _httpClient.get(Uri.parse(url + '/stores/?name=$storeName'));
    String storeId = convert.jsonDecode(getStoreIdResp.body)[0]["id"];

    if (storeCreateResp.statusCode == 201 && getStoreIdResp.statusCode == 200) {
      for (var item in storeItems) {
        await _httpClient.post(Uri.parse(url + '/stores/$storeId/items'),
            headers: {"Content-Type": "application/json"},
            body: json.encode(item));
      }
    }
  }
}
