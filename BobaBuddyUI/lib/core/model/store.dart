import 'package:boba_buddy/core/model/models.dart';
import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';
import 'rating.dart';

part 'store.g.dart';

@JsonSerializable(explicitToJson: true)
class Store extends Equatable{
  final String id;
  final String name;
  final String location;
  final double avgRating;
  @JsonKey(toJson: menuToJson)
  late final List<Item>? menu;
  final Set<Rating>? ratings;

  static List<String> menuToJson(dynamic menu) {
    return menu.map((e) => e.toJson()).toList();
  }

  static List<Item> menuFromJson(List<Map<String, dynamic>> menu, Store store) {
    return menu.map((e) => Item.fromJsonAsNested(e, store)).toList();
  }

  factory Store.fromJson(Map<String, dynamic> json) => storeFromJson(json);

  static Store storeFromJson(Map<String, dynamic> json) {
    Store store = Store(
      json['id'] as String,
      json['name'] as String,
      json['location'] as String,
      (json['ratings'] as List<dynamic>?)
          ?.map((e) => Rating.fromJson(e as Map<String, dynamic>))
          .toSet(),
      json['avgRating'] as double
    );
    List<Item>? menu = (json['menu'] as List<dynamic>?)
        ?.map((e) => Item.fromJsonAsNested(e as Map<String, dynamic>, store))
        .toList();
    store.menu = menu;
    return store;
  }

  Map<String, dynamic> toJson() => _$StoreToJson(this);

  Store(this.id, this.name, this.location, this.ratings, this.avgRating);

  @override
  List<Object?> get props => [id, name, location, avgRating] ;
}
