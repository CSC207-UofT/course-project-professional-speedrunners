import 'package:boba_buddy/core/model/user.dart';
import 'package:equatable/equatable.dart';
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable()
class UserDetail extends Equatable {
  const UserDetail({required this.email,
    this.id,
    this.name,
    this.photo,
    this.roles = const ["ROLE_ANONYMOUS"],
    this.idToken});

  factory UserDetail.fromRepository(User user){
    return UserDetail(id: user.id,
        email: user.email,
        name: user.name,
        roles: user.roles?.map((e) => e?.name).toList());
  }


  /// The current user's email address.
  final String email;

  /// The current user's id.
  final String? id;

  /// The current user's name (display name).
  final String? name;

  final String? idToken;

  /// Url for the current user's photo.
  final String? photo;

  final List<String?>? roles;

  bool get isEmpty => this == UserDetail.empty;

  bool get isNotEmpty => this != UserDetail.empty;

  bool get isAdmin => roles?.contains("ROLE_ADMIN") ?? false;

  @override
  List<Object?> get props => [id, name, email, photo, roles];

  static const empty = UserDetail(email: '');
}
