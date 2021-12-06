import 'dart:async';

import 'package:boba_buddy/core/model/models.dart';
import 'package:boba_buddy/core/model/user_detail.dart';
import 'package:boba_buddy/core/repository/authentication_repository.dart';
import 'package:boba_buddy/utils/services/rest/api_client.dart';
import 'package:boba_buddy/utils/services/rest/user_api_client.dart';
import 'package:cache/cache.dart';

class UserRepository {
  final UserApiClient _userApiClient;
  final CacheClient _cache;
  final AuthenticationRepository _authenticationRepository;
  late final StreamSubscription<User> _subscription;

  UserRepository({UserApiClient? userApiClient,
  CacheClient? cache,
  AuthenticationRepository? authenticationRepository})
      : _userApiClient = userApiClient ?? UserApiClient(),
        _cache = cache ?? CacheClient(),
  _authenticationRepository = authenticationRepository ?? AuthenticationRepository()
  {_subscription = user.listen((user) => _cache.write(key: userCacheKey, value: user));}

  Stream<User> get user {
    return _authenticationRepository.user.asyncMap((userDetail) {
      if(userDetail.isEmpty) return User.empty;
      final user = getUser(userDetail.email, token: userDetail.idToken);
      return user;
    });
  }

  static const userCacheKey = '__user_cache_key__';

  User get currentUser  {
    User? user = _cache.read<User>(key: userCacheKey);
    return user ?? User.empty;
  }

  String get idToken{
    return _authenticationRepository.currentUser.idToken ?? "";
  }

  Future<User> getUser(String email, {String? token}) async{
    User user = await _userApiClient.getUser(email, token ?? idToken);
    if (user.email == currentUser.email) _cache.write(key: userCacheKey, value: user);
    return user;
  }
}
