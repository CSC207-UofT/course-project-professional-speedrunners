part of 'profile_bloc.dart';

abstract class ProfileEvent extends Equatable {
  const ProfileEvent();
  @override
  List<Object> get props => [];
}

class FieldChangeRequested extends ProfileEvent{
  final String field;
  final String newValue;

  const FieldChangeRequested(this.field, this.newValue);

  @override
  List<Object> get props => [field, newValue];
}

class FetchUserInfo extends ProfileEvent{
}
