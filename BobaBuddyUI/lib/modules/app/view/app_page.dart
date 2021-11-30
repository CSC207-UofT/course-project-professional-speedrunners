import 'package:boba_buddy/core/repository/authentication_repository.dart';
import 'package:boba_buddy/core/repository/item_repository.dart';
import 'package:boba_buddy/core/repository/store_repository.dart';
import 'package:boba_buddy/core/repository/user_repository.dart';
import 'package:boba_buddy/modules/app/bloc/app_bloc.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

import 'app_view.dart';

class MyApp extends StatelessWidget {
  const MyApp({
    Key? key,
    required AuthenticationRepository authenticationRepository,
    required UserRepository userRepository,
    required StoreRepository storeRepository,
    required ItemRepository itemRepository,
  })  : _authenticationRepository = authenticationRepository,
        _userRepository = userRepository,
        _storeRepository = storeRepository,
        _itemRepository = itemRepository,
        super(key: key);

  final AuthenticationRepository _authenticationRepository;
  final UserRepository _userRepository;
  final StoreRepository _storeRepository;
  final ItemRepository _itemRepository;

  @override
  Widget build(BuildContext context) {
    return MultiRepositoryProvider(
        providers: [
          RepositoryProvider<AuthenticationRepository>(
            create: (context) => _authenticationRepository,
          ),
          RepositoryProvider<UserRepository>(
            create: (context) => _userRepository,
          ),
          RepositoryProvider<StoreRepository>(
            create: (context) => _storeRepository,
          ),
          RepositoryProvider<ItemRepository>(
            create: (context) => _itemRepository,
          ),
        ],
        child: BlocProvider(
          create: (_) => AppBloc(authenticationRepository: _authenticationRepository),
          child: ScreenUtilInit(
            //used for universal scaling across devices
            builder: () => const AppView(),
            designSize: const Size(393,
                830), //The size of the device screen in the design draft, in dp
          ),
        ));
  }
}

