import 'dart:async';
import 'dart:convert' as convert;
import 'dart:convert';

import 'package:boba_buddy/core/model/models.dart';
import 'package:http/http.dart' as http;
import 'package:boba_buddy/utils/constant.dart';

class StoreApiClient {
  final http.Client _httpClient;
  final String authHeader = "X-Authorization-Firebase";

  StoreApiClient({http.Client? httpClient})
      : _httpClient = httpClient ?? http.Client();

  Future<List<Store>> getStores(String idToken) async {
    final response = await _httpClient
        .get(Uri.parse(url + '/stores'), headers: {authHeader: idToken});
    if (response.statusCode == 200) {
      Iterable storesMap = convert.jsonDecode(response.body);
      return storesMap.map((e) => Store.fromJson(e)).toList();
    }
    throw Exception("Failed to fetch stores from server");
  }

  ///Deletes store from db
  deleteStore({required String storeId, required String idToken}) async {
    http.Response response = await _httpClient.delete(
        Uri.parse(url + '/stores/$storeId'),
        headers: {authHeader: idToken});
    if (response.statusCode == 204) {
      // TODO: do sth to tell user operation was successful (fluttertoast?)
      return;
    }
    throw Exception("delete store operation failed.");
  }

  createStore(
      {required String storeName,
      required String storeAddress,
      required String storeImageUrl,
      required List storeItems,
      required String idToken,
      required String email}) async {
    var bodyJson = convert.jsonEncode(
        {"name": storeName, "location": storeAddress, "owner": email});

    http.Response storeCreateResp = await _httpClient.post(
        Uri.parse(url + '/admin/stores'),
        headers: {"Content-Type": "application/json", authHeader: idToken},
        body: bodyJson);

    if (storeCreateResp.statusCode == 201) {
      String storeId =
          Store.fromJson(convert.jsonDecode(storeCreateResp.body)).id;
      for (var item in storeItems) {
        var itemResponse = await _httpClient.post(
            Uri.parse(url + '/stores/$storeId/items'),
            headers: {"Content-Type": "application/json", authHeader: idToken},
            body: json.encode(item));

        var rsp = await _httpClient
            .get(Uri.parse(url + '/items?name=${item["name"]}'));
        var itemId = convert.jsonDecode(rsp.body)[0]["id"];
        //TODO: item doesn't seem to support image on creation
        await _httpClient.put(
            Uri.parse(url + '/items/$itemId?imageUrl=${item["imageUrl"]}'),
            headers: {authHeader: idToken});
        var itemBody = itemResponse.body;
        print(itemBody);
      }
      //TODO: store call doesn't seem to support image on creation.
      await _httpClient.put(
          Uri.parse(url + '/stores/$storeId?imageUrl=$storeImageUrl'),
          headers: {authHeader: idToken});

      return;
    }
    String msg = storeCreateResp.body;
    throw Exception("Something went wrong. Error: $msg");
  }
}
