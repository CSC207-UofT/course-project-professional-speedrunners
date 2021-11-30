part of 'profile_bloc.dart';

enum ProfileStatus {
  loading,
  loaded,
  error,
  processing
}

class ProfileState extends Equatable {
  const ProfileState({required this.status, this.user = User.empty, this.field = "-"});

  final ProfileStatus status;
  final User user;
  final String field;

  ProfileState copyWith({
    ProfileStatus? status, User? user, String? field}) {
    return ProfileState(
      status: status ?? this.status,
      field: field ?? this.field,
      user: user ?? this.user,
    );
  }

  @override
  List<Object> get props => [status, field, user];
}
