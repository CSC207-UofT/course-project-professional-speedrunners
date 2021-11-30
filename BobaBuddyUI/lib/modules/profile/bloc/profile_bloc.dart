import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:boba_buddy/core/model/user.dart';
import 'package:boba_buddy/core/repository/authentication_repository.dart';
import 'package:boba_buddy/core/repository/user_repository.dart';
import 'package:boba_buddy/modules/app/model/user_detail.dart';
import 'package:equatable/equatable.dart';

part 'profile_event.dart';
part 'profile_state.dart';

class ProfileBloc extends Bloc<ProfileEvent, ProfileState> {
  ProfileBloc({required UserRepository userRepository,
  required AuthenticationRepository authenticationRepository})
      : _userRepository = userRepository,
        _authenticationRepository = authenticationRepository,
        super(const ProfileState(status: ProfileStatus.loading)) {
    on<FieldChangeRequested>(_onFieldChangeRequest);
    on<FetchUserInfo>(_onFetchUserInfo);
  }

  final UserRepository _userRepository;
  final AuthenticationRepository _authenticationRepository;

  void _onFieldChangeRequest(FieldChangeRequested event, Emitter<ProfileState> emit){
    // TODO: Call user repo to make changes. user repo should be wired with both
    // firebase auth and our own backend api client to make changes on both end

    emit(state.copyWith(status: ProfileStatus.loading));
  }

  void _onFetchUserInfo(FetchUserInfo event, Emitter<ProfileState> emit) async{
    try{
      // Can also get currentUser directly instead. Need to force a authStatusChange to get new user from stream
      final UserDetail userDetail = await _authenticationRepository.currentUser;
      final user = await _userRepository.getUser(userDetail.email);
      return emit(state.copyWith(status: ProfileStatus.loaded,
      user: user));
    }catch (ex){
      return emit(state.copyWith(status: ProfileStatus.error));
    }
  }
}
