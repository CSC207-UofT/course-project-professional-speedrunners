import 'package:boba_buddy/core/model/models.dart';
import 'package:boba_buddy/utils/services/rest/api_client.dart';
import 'package:boba_buddy/utils/services/rest/user_api_client.dart';

class UserRepository {
  final UserApiClient _userApiClient;
  UserRepository({UserApiClient? userApiClient})
      : _userApiClient = userApiClient ?? UserApiClient();

  Future<User> getUser(String email) async{
    return _userApiClient.getUser(email);
  }
}
