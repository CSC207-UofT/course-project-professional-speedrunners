import 'dart:async';
import 'dart:convert' as convert;
import 'dart:io';
import 'package:boba_buddy/core/model/models.dart';
import 'package:http/http.dart' as http;

class UserApiClient {
  static const url = 'http://10.0.2.2:8080';
  final http.Client _httpClient;

  UserApiClient({http.Client? httpClient})
      :_httpClient = httpClient ?? http.Client();

  Future<User> getUser(String email) async{
    //TODO: Endpoints might change after security changes
    final response = await _httpClient.get(Uri.parse(url + '/users/$email'));

    if(response.statusCode == 200){
      Map<String, dynamic> userMap = convert.jsonDecode(response.body);
      return User.fromJson(userMap);
    }else if(response.statusCode == 403){
      throw Exception("Cannot be authenticated by server: \n" + response.body);
    }throw Exception("something went wrong server side: \n" + response.body);
  }
  createUser({required String email, required String name}) async {
    User user = User(email: email, name: name);

    final response = await _httpClient.post(Uri.parse(url + '/users'),
        headers: <String, String>{'Content-Type': 'application/json',},
        body: convert.jsonEncode(user.toJson()),
    );
    if (response.statusCode != 201) throw Exception("Registration failed at backend");
  }
}
