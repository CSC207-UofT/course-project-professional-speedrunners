import 'package:boba_buddy/core/model/models.dart';
import 'package:boba_buddy/utils/services/rest/api_client.dart';

class StoreRepository {
  final StoreApiClient _storeApiClient;

  StoreRepository({StoreApiClient? storeApiClient})
      : _storeApiClient = storeApiClient ?? StoreApiClient();

  Future<List<Store>> getStores() {
    return _storeApiClient.getStores();
  }

  deleteStore(String storeId) {
    _storeApiClient.deleteStore(storeId: storeId);
  }

  createStore(String storeName, String address, List items){
    _storeApiClient.createStore(storeName: storeName, storeAddress: address, storeItems: items);
  }
}
