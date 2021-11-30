part of 'home_bloc.dart';

enum HomeStatus {
  loading,
  isAdmin,
  isNotAdmin,
}

class HomeState extends Equatable {
  const HomeState({required this.status, this.page = 0});

  final HomeStatus status;
  final int page;

  HomeState copyWith({
    HomeStatus? status, int? page}) {
    return HomeState(
      status: status ?? this.status,
      page: page ?? this.page,
    );
  }

  @override
  List<Object> get props => [status, page];
}
