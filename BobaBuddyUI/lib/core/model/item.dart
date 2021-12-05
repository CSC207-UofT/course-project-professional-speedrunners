
import 'package:boba_buddy/core/model/models.dart';
import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';

import 'rating.dart';

part 'item.g.dart';

@JsonSerializable(explicitToJson: true)
class Item extends Equatable{


  final Store? store;
  final List<Rating>? ratings;
  final String id;
  final String name;
  final double price;
  final double avgRating;



  factory Item.fromJson(Map<String, dynamic> json) => _$ItemFromJson(json);

  static Item fromJsonAsNested(Map<String, dynamic> json, Store store){
    return Item(
      store,
      (json['ratings'] as List<dynamic>?)
          ?.map((e) => Rating.fromJson(e as Map<String, dynamic>))
          .toList(),
      json['id'] as String,
      json['name'] as String,
      (json['price'] as num).toDouble(),
      (json['avgRating'] as num).toDouble(),
    );
  }

  Map<String, dynamic> toJson() => _$ItemToJson(this);

  // Map<String, dynamic> toSimpleJson(Item instance) => <String, dynamic>{
  //   'id': instance.id,
  //   'name': instance.name,
  //   'price': instance.price,
  //   'avgRating': instance.avgRating,
  // };

  const Item(this.store, this.ratings, this.id, this.name, this.price, this.avgRating);

  @override
  List<Object?> get props => [id, name, price, avgRating];
}
