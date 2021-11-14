import 'dart:math';

import 'package:boba_buddy/Screens/full_menu_page.dart';
import 'package:boba_buddy/Screens/login_page.dart';
import 'package:boba_buddy/Screens/search_page.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:material_floating_search_bar/material_floating_search_bar.dart';

class SearchBar extends StatefulWidget {
  const SearchBar({Key? key}) : super(key: key);

  @override
  _SearchBar createState() => _SearchBar();
}

class _SearchBar extends State<SearchBar> {
  final controller = FloatingSearchBarController();

  @override
  Widget build(BuildContext context) {
    final isPortrait =
        MediaQuery.of(context).orientation == Orientation.portrait;

    return FloatingSearchBar(
      //progress: model.isLoading,

      onSubmitted: (query) {
        Container(
          color: Colors.blue,
        );
        print('submitted');

        Navigator.of(context)
            .push(MaterialPageRoute(builder: (context) => SearchPage(searchTerm: query,)));
      },

      margins: EdgeInsets.only(top: 50),
      clearQueryOnClose: true,
      automaticallyImplyBackButton: false,
      hint: 'Search For Drinks',
      backdropColor: Colors.transparent,
      scrollPadding: const EdgeInsets.only(top: 16, bottom: 56),
      transitionDuration: const Duration(milliseconds: 300),
      transitionCurve: Curves.easeInOut,
      physics: const BouncingScrollPhysics(),
      axisAlignment: isPortrait ? 0.0 : -1.0,
      openAxisAlignment: 0.0,
      width: isPortrait ? 600 : 500,
      debounceDelay: const Duration(milliseconds: 500),
      onQueryChanged: (query) {
        // Call your model, bloc, controller here.
      },
      // Specify a custom transition to be used for
      // animating between opened and closed stated.
      transition: ExpandingFloatingSearchBarTransition(),
      leadingActions: const [
        FloatingSearchBarAction(showIfOpened: false, child: Icon(Icons.search)),
      ],
      actions: [
        FloatingSearchBarAction(
          showIfOpened: false,
          child: CircularButton(
            icon: const Icon(Icons.more_horiz),
            onPressed: () {},
          ),
        ),
        FloatingSearchBarAction.searchToClear(
          showIfClosed: false,
        ),
      ],

      builder: (context, _) {
        return Container(
          child: Material(
            color: Colors.transparent,
            borderRadius: BorderRadius.circular(8),
            clipBehavior: Clip.antiAlias,
          ),
        );

        // ClipRRect(
        // borderRadius: BorderRadius.circular(8),
        // child: Material(
        //   color: Colors.blue,
        //   elevation: 4.0,
        // child: Column(
        //   mainAxisSize: MainAxisSize.min,
        //   children: Colors.accents.map((color) {
        //     return Container(height: 112, color: color);
        //   }).toList(),
        // ),
        //),
        // );
      },
    );
  }
}
