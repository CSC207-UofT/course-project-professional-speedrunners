import 'package:boba_buddy/core/model/models.dart';
import 'package:boba_buddy/core/repository/authentication_repository.dart';
import 'package:boba_buddy/utils/services/rest/api_client.dart';

class StoreRepository {
  final StoreApiClient _storeApiClient;
  final AuthenticationRepository _authenticationRepository;

  StoreRepository({StoreApiClient? storeApiClient,
  AuthenticationRepository? authenticationRepository})
      : _storeApiClient = storeApiClient ?? StoreApiClient(),
  _authenticationRepository = authenticationRepository ?? AuthenticationRepository();

  String get idToken{
    return _authenticationRepository.currentUser.idToken ?? "";
  }

  Future<List<Store>> getStores() {
    return _storeApiClient.getStores(idToken);
  }

  deleteStore(String storeId) {
    _storeApiClient.deleteStore(storeId: storeId, idToken: idToken);
  }

  createStore(String storeName, String address, List items){
    _storeApiClient.createStore(storeName: storeName, storeAddress: address, storeItems: items, idToken: idToken, email: _authenticationRepository.currentUser.email);
  }
}
