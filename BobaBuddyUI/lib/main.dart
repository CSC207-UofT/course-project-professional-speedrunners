import 'package:boba_buddy/core/repository/authentication_repository.dart';
import 'package:boba_buddy/core/repository/item_repository.dart';
import 'package:boba_buddy/core/repository/store_repository.dart';
import 'package:boba_buddy/core/repository/user_repository.dart';
import 'package:boba_buddy/modules/app/bloc/app_bloc.dart';
import 'package:boba_buddy/modules/home/shops/popular_shop.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flow_builder/flow_builder.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

import 'modules/app/routes/routes.dart';
import 'modules/app/view/app_page.dart';
import 'modules/login/view/login_page.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
  final authRepo = AuthenticationRepository();
  await authRepo.user.first;
  final storeRepo = StoreRepository();
  final userRepo = UserRepository();
  final itemRepo = ItemRepository();
  BlocOverrides.runZoned(
    () => runApp(MyApp(authenticationRepository: authRepo,
      storeRepository: storeRepo,
    userRepository: userRepo,
    itemRepository: itemRepo)),
    blocObserver: SimpleBlocObserver(),
  );
}




class SimpleBlocObserver extends BlocObserver {
  @override
  void onTransition(Bloc bloc, Transition transition) {
    super.onTransition(bloc, transition);
    print(transition);
  }

  @override
  void onError(BlocBase bloc, Object error, StackTrace stackTrace) {
    print(error);
    super.onError(bloc, error, stackTrace);
  }
}
