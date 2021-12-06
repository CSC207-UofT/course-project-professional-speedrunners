// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'item.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Item _$ItemFromJson(Map<String, dynamic> json) => Item(
      json['store'] == null
          ? null
          : Store.fromJson(json['store'] as Map<String, dynamic>),
      (json['ratings'] as List<dynamic>?)
          ?.map((e) => Rating.fromJson(e as Map<String, dynamic>))
          .toList(),
      json['id'] as String,
      json['name'] as String,
      (json['price'] as num).toDouble(),
      (json['avgRating'] as num?)?.toDouble(),
      json['imageUrl'] as String?,
    );

Map<String, dynamic> _$ItemToJson(Item instance) => <String, dynamic>{
      'store': instance.store?.toJson(),
      'ratings': instance.ratings?.map((e) => e.toJson()).toList(),
      'id': instance.id,
      'name': instance.name,
      'price': instance.price,
      'avgRating': instance.avgRating,
      'imageUrl': instance.imageUrl,
    };
