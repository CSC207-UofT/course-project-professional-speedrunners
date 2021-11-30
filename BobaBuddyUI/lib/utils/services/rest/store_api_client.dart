import 'dart:async';
import 'dart:convert' as convert;
import 'package:boba_buddy/core/model/models.dart';
import 'package:http/http.dart' as http;

class StoreApiClient {
  static const url = 'http://10.0.2.2:8080';
  final http.Client _httpClient;

  StoreApiClient({http.Client? httpClient})
  :_httpClient = httpClient ?? http.Client();

  Future<List<Store>> getStores() async {
    final response = await _httpClient.get(Uri.parse(url + '/stores'));
    if(response.statusCode == 200){
      Iterable storesMap = convert.jsonDecode(response.body);
      return storesMap.map((e) => Store.fromJson(e)).toList();
    }
    throw Exception("Failed to fetch stores from server");
  }

  ///Deletes store from db
  deleteStore({required String storeId}) async {
     http.Response response = await _httpClient.delete(Uri.parse(url + '/stores/$storeId'));
     if(response.statusCode == 204) {
       // TODO: do sth to tell user operation was successful (fluttertoast?)
       return;
     }
     throw Exception("delete store operation failed.");
  }

}
