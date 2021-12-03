import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class CategoryPage extends StatelessWidget {
  const CategoryPage({Key? key, required this.category}) : super(key: key);

  final String category;

  @override
  Widget build(BuildContext context) {
    // TODO: implement build when category is implemented in backend
    return Scaffold(
      body: Center(child: Text(category)),
    );
  }
}
