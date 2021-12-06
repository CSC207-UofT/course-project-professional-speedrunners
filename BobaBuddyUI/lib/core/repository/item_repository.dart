
import 'package:boba_buddy/core/model/item.dart';
import 'package:boba_buddy/core/repository/authentication_repository.dart';
import 'package:boba_buddy/utils/services/rest/api_client.dart';

class ItemRepository{
  final ItemApiClient _apiClient;
  final AuthenticationRepository _authenticationRepository;

  ItemRepository({ItemApiClient? itemApiClient,
  AuthenticationRepository? authenticationRepository})
      : _apiClient = itemApiClient ?? ItemApiClient(),
        _authenticationRepository = authenticationRepository ?? AuthenticationRepository();

  String get idToken{
    return _authenticationRepository.currentUser.idToken ?? "";
  }

  Future<List<Item>> getMenu(String storeId){
    return _apiClient.getMenu(storeId: storeId, idToken: idToken);
  }

  Future<List<Item>> findByNameContain(String searchTerm){
    return _apiClient.itemSearch(searchTerm, idToken);
  }

  Future<Item> getItemById(String itemId){
    return _apiClient.getItemById(itemId, idToken);
  }

  Future<Item> getItemByRating(String ratingId){
    return _apiClient.getItemByRating(ratingId, idToken);
  }

  Future<Item> updateItemPrice(String itemId, double newPrice){
    return _apiClient.updateItemPrice(itemId, newPrice, idToken);
  }

  Future getOneItemFromStore(String storeId){
    return _apiClient.getOneItemFromStore(storeId, idToken);
  }

}