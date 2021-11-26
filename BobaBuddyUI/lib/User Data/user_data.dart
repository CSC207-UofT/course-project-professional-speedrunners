import 'package:boba_buddy/Authentication/firebaseauth.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

class User {
  User();

  Future<String> getUserName() async {
    String data = '';
    await FirebaseFirestore.instance
        .collection('User Info')
        .doc(Auth().getUserEmail())
        .get()
        .then((DocumentSnapshot documentSnapshot) {
      data = documentSnapshot.get("name");
      print(data);
      return data;
    });

    return data;
  }
}
