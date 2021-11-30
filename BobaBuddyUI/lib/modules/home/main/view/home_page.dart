import 'package:boba_buddy/core/repository/authentication_repository.dart';
import 'package:boba_buddy/modules/home/main/bloc/home_bloc.dart';
import 'package:boba_buddy/modules/home/main/route/routes.dart';
import 'package:flow_builder/flow_builder.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class HomePage extends StatelessWidget {
  const HomePage({Key? key}) : super(key: key);

  static Page page() => const MaterialPage<void>(child: HomePage());

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
        create: (_) => HomeBloc(
            authenticationRepository: context.read<AuthenticationRepository>()),
        child: const HomePageView());
  }
}

class HomePageView extends StatelessWidget {
  const HomePageView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: FlowBuilder<int>(
          state: context.select((HomeBloc bloc) => bloc.state.page),
          onGeneratePages: onGenerateHomePages,
        ),
        bottomNavigationBar:
            BlocBuilder<HomeBloc, HomeState>(builder: (context, state) {
          switch (state.status) {
            case HomeStatus.isAdmin:
              return BottomNavigationBar(
                  fixedColor: Colors.black,
                  items: _pageOptions(isAdmin: true),
                  currentIndex: state.page,
                  onTap: (index) {
                    context.read<HomeBloc>().add(NavigationRequested(index));
                  });
            default:
              return BottomNavigationBar(
                  fixedColor: Colors.black,
                  items: _pageOptions(isAdmin: false),
                  currentIndex: state.page,
                  onTap: (index) {
                    context.read<HomeBloc>().add(NavigationRequested(index));
                  });
          }
        }));
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
