import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:boba_buddy/core/repository/authentication_repository.dart';
import 'package:boba_buddy/modules/app/model/user_detail.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/foundation.dart';

part 'home_event.dart';
part 'home_state.dart';

class HomeBloc extends Bloc<HomeEvent, HomeState> {
  HomeBloc({required AuthenticationRepository authenticationRepository})
      : _authenticationRepository = authenticationRepository,
        super(
        authenticationRepository.currentUser.isAdmin
            ? const HomeState(status: HomeStatus.isAdmin)
            : const HomeState(status: HomeStatus.isNotAdmin),
      ) {
    on<UserStatusChanged>(_onUserStatusChanged);
    on<NavigationRequested>(_onNavigationRequest);
    _userSubscription = _authenticationRepository.user.listen(
          (user) => add(UserStatusChanged(user)),
    );
  }

  final AuthenticationRepository _authenticationRepository;
  late final StreamSubscription<UserDetail> _userSubscription;

  void _onUserStatusChanged(UserStatusChanged event, Emitter<HomeState> emit){
    emit(event.user.isAdmin
        ? state.copyWith(status: HomeStatus.isAdmin)
        : state.copyWith(status: HomeStatus.isNotAdmin));
  }

  void _onNavigationRequest(NavigationRequested event, Emitter<HomeState> emit){
    emit(state.copyWith(page: event.page));
  }

  @override
  Future<void> close() {
    _userSubscription.cancel();
    return super.close();
  }
}
