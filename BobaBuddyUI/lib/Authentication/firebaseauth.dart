import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';

enum Response {
  success,
  error,
  weakPassword,
  emailInUse,
  userNotFound,
  incorrectUserOrPass
}

class Auth {
  final _auth = FirebaseAuth.instance;

  Auth();

  Future<Enum> createAccount(
      {required String email, required String password}) async {
    try {
      UserCredential userCredential = await _auth
          .createUserWithEmailAndPassword(email: email, password: password);
    } on FirebaseAuthException catch (e) {
      if (e.code == 'weak-password') {
        print('The password provided is too weak.');
        return (Response.weakPassword);
      } else if (e.code == 'email-already-in-use') {
        print('The account already exists for that email.');
        return (Response.emailInUse);
      }
    } catch (e) {
      print(e);
      return (Response.error);
    }

    return (Response.success);
  }

  Future<Enum> login({required String email, required String password}) async {
    try {
      UserCredential userCredential = await _auth.signInWithEmailAndPassword(
          email: email, password: password);
    } on FirebaseAuthException catch (e) {
      if (e.code == 'user-not-found') {
        print('No user found for that email.');
        return Response.userNotFound;
      } else if (e.code == 'wrong-password') {
        print('Wrong password provided for that user.');
        return Response.incorrectUserOrPass;
      }
    } catch (e) {
      print(e);
      return (Response.error);
    }

    return Response.success;
  }

  ///Returns the current user token
  getUserToken() async {
    final user = _auth.currentUser;
    final idToken = await user!.getIdToken();

    return idToken;
  }

  getUserID() {
    final User? user = _auth.currentUser;
    final uid = user!.uid;
    return uid;
  }

  Future<bool> isAdmin() async {
    List data = [];
    await FirebaseFirestore.instance
        .collection('User Info')
        .doc("Admin Ids")
        .get()
        .then((DocumentSnapshot documentSnapshot) {
      data = documentSnapshot.get("ids");
      return data.contains(getUserID());
    });

    return data.contains(getUserID());
  }
}
