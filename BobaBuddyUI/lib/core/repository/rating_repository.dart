
import 'package:boba_buddy/core/model/models.dart';
import 'package:boba_buddy/core/repository/authentication_repository.dart';
import 'package:boba_buddy/utils/services/rest/api_client.dart';
import 'package:boba_buddy/utils/services/rest/rating_api_client.dart';

class RatingRepository{
  final RatingApiClient _apiClient;
  final AuthenticationRepository _authenticationRepository;

  RatingRepository({RatingApiClient? ratingApiClient,
  AuthenticationRepository? authenticationRepository})
      : _apiClient = ratingApiClient ?? RatingApiClient(),
        _authenticationRepository = authenticationRepository ?? AuthenticationRepository();

  String get idToken{
    return _authenticationRepository.currentUser.idToken ?? "";
  }

  Future<Rating> getRating(String ratingId){
    return _apiClient.getRating(ratingId, idToken);
  }

  addRating(int rating, String userEmail, String itemID) async{
    _apiClient.addRating(rating, userEmail, itemID, idToken);
  }

  deleteRating(String ratingID) async{
    _apiClient.deleteRating(ratingID: ratingID, idToken: idToken);
  }

  updateRating(String ratingID, int rating) async{
    _apiClient.updateRating(ratingID, rating, idToken);
  }
}