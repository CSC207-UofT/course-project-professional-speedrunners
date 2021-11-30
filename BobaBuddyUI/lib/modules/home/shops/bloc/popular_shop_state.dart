import 'package:boba_buddy/core/model/models.dart';
import 'package:equatable/equatable.dart';

enum PopularShopStatus {loading, loaded, error, shopSelected}

class PopularShopState extends Equatable {
  final List<Store> stores;
  final PopularShopStatus status;
  final Exception? ex;

  const PopularShopState(
      {this.ex, this.status = PopularShopStatus.loading, this.stores = const <Store>[]});

  @override
  List<Object?> get props => [stores, status, ex];

  PopularShopState copyWith({
    Exception? ex,
    PopularShopStatus? status,
    List<Store>? stores,
  }) {
    return PopularShopState(
      ex: ex,
      stores: stores ?? this.stores,
      status: status ?? this.status,
    );
  }

  @override
  String toString() {
    return """PopularShopState { status: $status, stores: $stores}""";
  }
}
