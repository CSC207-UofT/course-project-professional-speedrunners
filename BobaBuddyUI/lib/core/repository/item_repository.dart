
import 'package:boba_buddy/core/model/item.dart';
import 'package:boba_buddy/utils/services/rest/api_client.dart';

class ItemRepository{
  final ItemApiClient _apiClient;

  ItemRepository({ItemApiClient? itemApiClient})
      : _apiClient = itemApiClient ?? ItemApiClient();
  Future<List<Item>> getMenu(String storeId){
    return _apiClient.getMenu(storeId: storeId);
  }

  Future<List<Item>> findByNameContain(String searchTerm){
    return _apiClient.itemSearch(searchTerm);
  }

  Future<Item> getItemById(String itemId){
    return _apiClient.getItemById(itemId);
  }

  Future<Item> updateItemPrice(String itemId, double newPrice){
    return _apiClient.updateItemPrice(itemId, newPrice);
  }

  Future getOneItemFromStore(String storeId){
    return _apiClient.getOneItemFromStore(storeId);
  }

}