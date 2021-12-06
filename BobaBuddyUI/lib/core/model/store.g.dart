// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'store.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Store _$StoreFromJson(Map<String, dynamic> json) => Store(
      json['id'] as String,
      json['name'] as String,
      json['location'] as String,
      (json['ratings'] as List<dynamic>?)
          ?.map((e) => Rating.fromJson(e as Map<String, dynamic>))
          .toSet(),
      (json['avgRating'] as num).toDouble(),
      json['imageUrl'] as String?,
    )..menu = (json['menu'] as List<dynamic>?)
        ?.map((e) => Item.fromJson(e as Map<String, dynamic>))
        .toList();

Map<String, dynamic> _$StoreToJson(Store instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'location': instance.location,
      'avgRating': instance.avgRating,
      'menu': Store.menuToJson(instance.menu),
      'ratings': instance.ratings?.map((e) => e.toJson()).toList(),
      'imageUrl': instance.imageUrl,
    };
