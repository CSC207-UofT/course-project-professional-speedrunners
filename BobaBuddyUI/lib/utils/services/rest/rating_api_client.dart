import 'dart:async';
import 'dart:convert' as convert;
import 'package:boba_buddy/core/model/models.dart';
import 'package:http/http.dart' as http;
import 'package:boba_buddy/utils/constant.dart';

class RatingApiClient {
  final http.Client _httpClient;
  final String authHeader = "X-Authorization-Firebase";


  RatingApiClient({http.Client? httpClient})
      :_httpClient = httpClient ?? http.Client();

  Future<Rating> getRating(String ratingId, String idToken) async{
    final response = await _httpClient.get(Uri.parse(url + '/ratings/$ratingId'));

    if(response.statusCode == 200){
      Map<String, dynamic> ratingMap = convert.jsonDecode(response.body);
      return Rating.fromJson(ratingMap);
    }
    print(response.body);
    throw Exception("Something went wrong.");

  }

  addRating(int rating, String userEmail, String itemID, String idToken) async {
    final body = convert.jsonEncode({'rating': '$rating'});
    final response = await _httpClient.post(Uri.parse(url + '/items/$itemID/ratings?createdBy=$userEmail'), headers: {"Content-Type": "application/json", authHeader: idToken},
        body: body);
    if(response.statusCode != 201){
      String msg = response.body;
      throw Exception("Failed to add Rating. error: $msg");
    }
  }

  deleteRating({required String ratingID, required String idToken}) async {
    final response = await _httpClient.delete(Uri.parse(url + '/ratings/$ratingID'), headers: {authHeader: idToken});

    if(response.statusCode != 204){
      String msg = response.body;
      throw Exception("Failed to delete rating. Error: $msg");
    }
  }

  updateRating(String ratingID, int rating, String idToken) async{
    final body = convert.jsonEncode({'rating': '$rating'});
    final response = await _httpClient.put(Uri.parse(url + '/ratings/$ratingID'),
        headers: {"Content-Type": "application/json", authHeader: idToken}, body: body);

    if(response.statusCode != 200){
      String msg = response.body;
      throw Exception("Failed to update rating. Error: $msg");
    }
  }
}