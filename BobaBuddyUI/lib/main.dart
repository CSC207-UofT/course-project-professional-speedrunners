import 'package:boba_buddy/Screens/login_page.dart';
import 'package:cache/cache.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

import 'core/repository/rating_repository.dart';
import 'core/repository/repository.dart';

// void main() => runApp(MyApp());

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final _cacheClient = CacheClient();
    final _authenticationRepository = AuthenticationRepository(cache: _cacheClient);
    final _userRepository = UserRepository(cache: _cacheClient, authenticationRepository: _authenticationRepository);
    final _storeRepository = StoreRepository(authenticationRepository: _authenticationRepository);
    final _itemRepository = ItemRepository(authenticationRepository: _authenticationRepository);
    final _ratingRepository = RatingRepository(authenticationRepository: _authenticationRepository);
    return ScreenUtilInit(
      //used for universal scaling across devices
      builder: () => MultiRepositoryProvider(
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
          RepositoryProvider<RatingRepository>(
            create: (context) => _ratingRepository,
          )
        ],
        child:
            MaterialApp(debugShowCheckedModeBanner: false, home: LoginPage()),
      ),
      designSize: const Size(
          393, 830), //The size of the device screen in the design draft, in dp
    );
  }
}
