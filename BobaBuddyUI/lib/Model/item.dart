import 'package:boba_buddy/Model/data_object.dart';
import 'package:boba_buddy/Model/store.dart';
import 'package:json_annotation/json_annotation.dart';

import 'rating.dart';

part 'item.g.dart';

@JsonSerializable(explicitToJson: true)
class Item{

  final Store? store;
  final List<Rating>? ratings;
  final String id;
  final String name;
  final double price;
  final double avgRating;



  factory Item.fromJson(Map<String, dynamic> json) => _$ItemFromJson(json);

  @override
  Map<String, dynamic> toJson() => _$ItemToJson(this);

  Map<String, dynamic> toSimpleJson(Item instance) => <String, dynamic>{
    'id': instance.id,
    'name': instance.name,
    'price': instance.price,
    'avgRating': instance.avgRating,
  };

  Item(this.store, this.ratings, this.id, this.name, this.price, this.avgRating);
}
