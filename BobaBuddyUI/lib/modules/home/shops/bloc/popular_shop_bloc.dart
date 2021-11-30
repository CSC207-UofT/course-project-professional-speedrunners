import 'package:boba_buddy/core/model/models.dart';
import 'package:boba_buddy/core/repository/store_repository.dart';
import 'package:boba_buddy/modules/home/shops/bloc/popular_shop_event.dart';
import 'package:boba_buddy/modules/home/shops/bloc/popular_shop_state.dart';
import 'package:flutter_bloc/flutter_bloc.dart';


class PopularShopBloc extends Bloc<PopularShopEvent, PopularShopState>{
  final StoreRepository storeRepo;

  PopularShopBloc({required this.storeRepo}): super(const PopularShopState()){
    on<FetchShopList>(_onFetchShopList);
}

  Future<void> _onFetchShopList(FetchShopList event, Emitter<PopularShopState> emit) async{
    try {
      final stores = await _fetchStores();
      return emit(state.copyWith(
        status: PopularShopStatus.loaded,
        stores: stores,
      ));
    }on Exception catch (ex){
      return emit(state.copyWith(status: PopularShopStatus.error, ex: ex));
    }
  }

  Future<List<Store>> _fetchStores() async {
    return storeRepo.getStores();
  }
}