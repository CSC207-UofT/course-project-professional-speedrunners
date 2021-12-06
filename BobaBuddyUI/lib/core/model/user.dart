import 'package:boba_buddy/core/model/role.dart';
import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';

import 'rating.dart';

part 'user.g.dart';

@JsonSerializable(explicitToJson: true)
class User extends Equatable{
  final String? id;
  final String email;
  final String? name;
  final String? password;
  final List<Rating>? ratings;
  final List<Role>? roles;

  const User({required this.email, this.name, this.password, this.ratings, this.roles, this.id});

  factory User.fromJson(Map<String, dynamic> json) => _$UserFromJson(json);
  Map<String, dynamic> toJson() => _$UserToJson(this);

  static const empty = User(email: "");

  @override
  List<Object?> get props => [email, name, password, roles];

}
