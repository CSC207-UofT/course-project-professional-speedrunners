import 'package:boba_buddy/Authentication/firebaseauth.dart';
import 'package:boba_buddy/Screens/admin_page.dart';
import 'package:boba_buddy/Screens/home_page.dart';
import 'package:boba_buddy/Screens/profile_page.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  _HomeScreen createState() => _HomeScreen();
}

class _HomeScreen extends State<HomeScreen> {
  int _currentIndex = 0;
  final List<Widget> _userPageOptions = [const HomePage(), ProfilePage()];

  final List<Widget> _adminPageOptions = [
    const HomePage(),
    ProfilePage(),
    AdminPage()
  ];

  @override
  Widget build(BuildContext context) {
//Decides which layout will be shown to user based on the user privilege
    return FutureBuilder(
      future: Auth().isAdmin(),
      builder: (BuildContext context, AsyncSnapshot<dynamic> snapshot) {
        if (!snapshot.hasData) {
          return const Scaffold(
            body: Center(
              child: CircularProgressIndicator(),
            ),
          );
        } else if (snapshot.data == true) {
          return (Scaffold(
            body:
                IndexedStack(index: _currentIndex, children: _adminPageOptions),
            bottomNavigationBar: BottomNavigationBar(
              fixedColor: Colors.black,
              items: _pageOptions(isAdmin: true),
              currentIndex: _currentIndex,
              onTap: (index) {
                setState(() {
                  _currentIndex = index;
                });
              },
            ),
          ));
        } else {
          return (Scaffold(
            body:
                IndexedStack(index: _currentIndex, children: _userPageOptions),
            bottomNavigationBar: BottomNavigationBar(
              fixedColor: Colors.black,
              items: _pageOptions(isAdmin: false),
              currentIndex: _currentIndex,
              onTap: (index) {
                setState(() {
                  _currentIndex = index;
                });
              },
            ),
          ));
        }
      },
    );
  }
}

_pageOptions({required bool isAdmin}) {
  List<BottomNavigationBarItem> userPages = const [
    BottomNavigationBarItem(
      icon: Icon(Icons.house),
      label: 'Home',
    ),
    BottomNavigationBarItem(
      icon: Icon(Icons.person_outline),
      label: 'Profile',
    ),
  ];

  List<BottomNavigationBarItem> adminPages = const [
    BottomNavigationBarItem(
      icon: Icon(Icons.house),
      label: 'Home',
    ),
    BottomNavigationBarItem(
      icon: Icon(Icons.person_outline),
      label: 'Profile',
    ),
    BottomNavigationBarItem(
      icon: Icon(
        Icons.admin_panel_settings,
      ),
      label: 'Admin Panel',
    )
  ];

  return isAdmin ? adminPages : userPages;
}
