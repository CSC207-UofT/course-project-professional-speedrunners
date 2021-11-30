
import 'package:boba_buddy/core/model/models.dart';
import 'package:equatable/equatable.dart';

abstract class PopularShopEvent extends Equatable{
  const PopularShopEvent();
  @override
  List<Object?> get props => [];
}

class FetchShopList extends PopularShopEvent{
}

class SelectShop extends PopularShopEvent{
  final Store store;

  const SelectShop({required this.store});

  @override
  List<Object?> get props => [store];
}