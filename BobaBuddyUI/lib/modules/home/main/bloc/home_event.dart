part of 'home_bloc.dart';

abstract class HomeEvent extends Equatable {
  const HomeEvent();

  @override
  List<Object> get props => [];
}


class UserStatusChanged  extends HomeEvent {
  @visibleForTesting
  const UserStatusChanged(this.user);

  final UserDetail user;

  @override
  List<Object> get props => [user];
}

class NavigationRequested extends HomeEvent {
  final int page;

  const NavigationRequested(this.page);

  @override
  List<Object> get props => [page];
}


