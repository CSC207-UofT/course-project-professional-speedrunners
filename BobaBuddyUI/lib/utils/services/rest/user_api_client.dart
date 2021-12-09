import 'dart:async';
import 'dart:convert' as convert;
import 'dart:io';
import 'package:boba_buddy/core/model/models.dart';
import 'package:http/http.dart' as http;
import 'package:boba_buddy/utils/constant.dart';

class UserApiClient {
  final http.Client _httpClient;
  final String authHeader = "X-Authorization-Firebase";


  UserApiClient({http.Client? httpClient})
      :_httpClient = httpClient ?? http.Client();

  Future<User> getUser(String email, String idToken) async{
    //TODO: Endpoints might change after security changes
    final response = await _httpClient.get(Uri.parse(url + '/users/$email'), headers: {authHeader: idToken});

    if(response.statusCode == 200){
      Map<String, dynamic> userMap = convert.jsonDecode(response.body);
      return User.fromJson(userMap);
    }else if(response.statusCode == 403){
      throw Exception("Cannot be authenticated by server: \n" + response.body);
    }throw Exception("something went wrong server side: \n" + response.body);
  }

  Future<User> updateEmail(String currentUserEmail, String email, String idToken) async{
    final response = await _httpClient.put(Uri.parse(url + '/users/$currentUserEmail'),
        headers: {"Content-Type": 'application/json', authHeader: idToken},
    body: convert.jsonEncode({"email": email}));

    if (response.statusCode != 200) throw Exception(response.body);
    return User.fromJson(convert.jsonDecode(response.body));
  }

  Future<User> updateName(String currentUserEmail, String name, String idToken) async{
    final response = await _httpClient.put(Uri.parse(url + '/users/$currentUserEmail'),
        headers: {"Content-Type": 'application/json', authHeader: idToken},
        body: convert.jsonEncode({"name": name}));

    if (response.statusCode != 200) throw Exception(response.body);
    return User.fromJson(convert.jsonDecode(response.body));

  }

  createUser({required String email, required String name, required String idToken}) async {
    User user = User(email: email, name: name);

    final response = await _httpClient.post(Uri.parse(url + '/users'),
        headers: <String, String>{'Content-Type': 'application/json', authHeader: idToken},
        body: convert.jsonEncode(user.toJson()),
    );
    if (response.statusCode != 201) throw Exception("Registration failed at backend");
  }
}
