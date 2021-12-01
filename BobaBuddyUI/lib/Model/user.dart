import 'package:json_annotation/json_annotation.dart';

import 'rating.dart';

part 'user.g.dart';

@JsonSerializable(explicitToJson: true)
class User {
  final String email;
  final String name;
  final String password;
  final Set<Rating>? ratings;
  final List<String>? roles;

  User(this.email, this.name, this.password, this.ratings, this.roles);

  factory User.fromJson(Map<String, dynamic> json) => _$UserFromJson(json);
  Map<String, dynamic> toJson() => _$UserToJson(this);

}
