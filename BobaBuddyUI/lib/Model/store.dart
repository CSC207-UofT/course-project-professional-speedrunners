import 'package:boba_buddy/Model/item.dart';
import 'package:json_annotation/json_annotation.dart';
import 'rating.dart';

part 'store.g.dart';

@JsonSerializable(explicitToJson: true)
class Store{
  final String id;
  final String name;
  final String location;
  @JsonKey(toJson: menuToJson)
  final List<Item>? menu;
  final Set<Rating>? ratings;

  static List<String> menuToJson(dynamic menu){
    return menu.map((e) => e.toJson()).toList();
  }

  factory Store.fromJson(Map<String, dynamic> json) => _$StoreFromJson(json);
  Map<String, dynamic> toJson() => _$StoreToJson(this);


  Store(this.id, this.name, this.location, this.menu, this.ratings);
}
