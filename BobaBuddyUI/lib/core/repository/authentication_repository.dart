import 'dart:async';

import 'package:boba_buddy/core/model/models.dart';
import 'package:boba_buddy/core/model/user_detail.dart';
import 'package:boba_buddy/core/repository/user_repository.dart';
import 'package:boba_buddy/utils/services/rest/user_api_client.dart';
import 'package:cache/cache.dart';
import 'package:firebase_auth/firebase_auth.dart' as firebase;
import 'package:flutter/foundation.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:meta/meta.dart';

part 'authentication_exceptions.dart';

enum AuthenticationStatus { unknown, authenticated, unauthenticated }

/// {@template authentication_repository}
/// Repository which manages user authentication.
/// {@endtemplate}
class AuthenticationRepository {
  /// {@macro authentication_repository}
  AuthenticationRepository({
    CacheClient? cache,
    firebase.FirebaseAuth? firebaseAuth,
    GoogleSignIn? googleSignIn,
    UserApiClient? userApiClient,
  })  : _cache = cache ?? CacheClient(),
        _firebaseAuth = firebaseAuth ?? firebase.FirebaseAuth.instance,
        _googleSignIn = googleSignIn ?? GoogleSignIn.standard(),
        _userApiClient = userApiClient ?? UserApiClient()
  {_subscription = user.listen((user) => _cache.write(key: userCacheKey, value: user));}

  final CacheClient _cache;
  final firebase.FirebaseAuth _firebaseAuth;
  final GoogleSignIn _googleSignIn;
  final UserApiClient _userApiClient;

  /// Whether or not the current environment is web
  /// Should only be overriden for testing purposes. Otherwise,
  /// defaults to [kIsWeb]
  @visibleForTesting
  bool isWeb = kIsWeb;

  /// User cache key.
  /// Should only be used for testing purposes.
  @visibleForTesting
  static const userCacheKey = '__user_detail_cache_key__';

  /// Stream of [UserDetail] which will emit the current user when
  /// the authentication state changes.
  ///
  /// Emits [UserDetail.empty] if the user is not authenticated.
  Stream<UserDetail> get user {
    return _firebaseAuth.idTokenChanges().asyncMap((firebaseUser) {
      if(firebaseUser == null || firebaseUser.email == null) return UserDetail.empty;
      final user = _toUserDetail(firebaseUser);
      return user;
    });
  }

  late final StreamSubscription<UserDetail> _subscription;

  Future<UserDetail> _toUserDetail(firebase.User firebaseUser) async{
    //TODO: add idToken to get user call. this only works when spring security is disabled in the backend
    final idToken = await firebaseUser.getIdToken();
    final user = await _userApiClient.getUser(firebaseUser.email!, idToken);
    return UserDetail(id: user.id, email: user.email, roles: user.roles?.map((e) => e.name).toList(), name: user.name, idToken: idToken);
  }

  /// Returns the current cached user.
  /// Defaults to [User.empty] if there is no cached user.
  UserDetail get currentUser  {
    UserDetail? user = _cache.read<UserDetail>(key: userCacheKey);
    return user ?? UserDetail.empty;
  }

  /// Creates a new user with the provided [email] and [password].
  ///
  /// Throws a [SignUpWithEmailAndPasswordFailure] if an exception occurs.
  Future<void> signUp({required String email, required String password, required String name}) async {
    try {
      await _userApiClient.createUser(
          email: email,
          name: name,
          idToken: currentUser.idToken ?? ""
      );
      await _firebaseAuth.createUserWithEmailAndPassword(
        email: email,
        password: password,
      );
    } on firebase.FirebaseAuthException catch (e) {
      throw SignUpWithEmailAndPasswordFailure.fromCode(e.code);
    } catch (_) {
      throw const SignUpWithEmailAndPasswordFailure();
    }
  }

  /// Starts the Sign In with Google Flow.
  ///
  /// Throws a [LogInWithGoogleFailure] if an exception occurs.
  Future<void> logInWithGoogle() async {
    try {
      late final firebase.AuthCredential credential;
      if (isWeb) {
        final googleProvider = firebase.GoogleAuthProvider();
        final userCredential = await _firebaseAuth.signInWithPopup(
          googleProvider,
        );
        credential = userCredential.credential!;
      } else {
        final googleUser = await _googleSignIn.signIn();
        final googleAuth = await googleUser!.authentication;
        credential = firebase.GoogleAuthProvider.credential(
          accessToken: googleAuth.accessToken,
          idToken: googleAuth.idToken,
        );
      }

      await _firebaseAuth.signInWithCredential(credential);
    } on firebase.FirebaseAuthException catch (e) {
      throw LogInWithGoogleFailure.fromCode(e.code);
    } catch (_) {
      throw const LogInWithGoogleFailure();
    }
  }

  /// Signs in with the provided [email] and [password].
  ///
  /// Throws a [LogInWithEmailAndPasswordFailure] if an exception occurs.
  Future<void> logInWithEmailAndPassword({
    required String email,
    required String password,
  }) async {
    try {
      await _firebaseAuth.signInWithEmailAndPassword(
        email: email,
        password: password,
      );
    } on firebase.FirebaseAuthException catch (e) {
      throw LogInWithEmailAndPasswordFailure.fromCode(e.code);
    } catch (_) {
      throw const LogInWithEmailAndPasswordFailure();
    }
  }

  /// Signs out the current user which will emit
  /// [User.empty] from the [user] Stream.
  ///
  /// Throws a [LogOutFailure] if an exception occurs.
  Future<void> logOut() async {
    try {
      await Future.wait([
        _firebaseAuth.signOut(),
        _googleSignIn.signOut(),
      ]);
    } catch (_) {
      throw LogOutFailure();
    }
  }

}
