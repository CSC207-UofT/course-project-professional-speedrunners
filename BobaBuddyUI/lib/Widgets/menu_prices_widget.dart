import 'package:flutter/material.dart';

class MenuPricesWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      scrollDirection: Axis.horizontal,
      child: Row(
        children: [
          Container(
            margin: EdgeInsets.only(right: 15),
            height: 180,
            child: ListView.builder(
                itemCount: 5,
                shrinkWrap: true,
                scrollDirection: Axis.horizontal,
                itemBuilder: (context, index) {
                  return SingleItem();
                }),
          )
        ],
      ),
    );
  }
}

Widget SingleItem() {
  return Container(
    margin: EdgeInsets.only(right: 15),
    width: 100,
    height: 100,
    child: InkWell(
      onTap: () {
        print('other item tapped');
      },
      child: ClipRRect(
        child: Image(
          image: AssetImage('assets/images/fruitTea.png'),
          fit: BoxFit.fitHeight,
        ),
      ),
    ),
  );
}
