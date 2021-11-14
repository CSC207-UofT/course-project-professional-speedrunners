import 'package:boba_buddy/Database/database.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class FullMenuPage extends StatelessWidget{
  final String storeId;

  const FullMenuPage({Key? key, required this.storeId}) : super(key: key);

  @override
  Widget build(BuildContext context) {

    var db = Database();

    double deviceWidth = MediaQuery.of(context).size.width;
    double deviceHeight = MediaQuery.of(context).size.height;
    return Scaffold(
      body: Stack(
        children: [

          FutureBuilder(
            future: db.getStoreItems(storeId: storeId),
            builder: (BuildContext context, AsyncSnapshot<dynamic> snapshot) {
            if(!snapshot.hasData){
              return const Center(child: CircularProgressIndicator(),);

            } else{
              return ListView.builder(
                padding: const EdgeInsets.only(top: 100),
                itemCount:snapshot.data.length, //TODO: pull length from api call
                shrinkWrap: true,scrollDirection: Axis.vertical,itemBuilder: (context, index){

                  return singleItem(
                      price: snapshot.data[index]["price"],
                      itemName: snapshot.data[index]["name"],
                      imageSrc: "https://chatime.com/wp-content/uploads/2020/10/Brown-Sugar-Pearls-with-Milk-Tea.png");
              }

              );
            }

          },),


        //   ListView.builder(
        //   padding: const EdgeInsets.only(top: 100),
        //   itemCount:30, //TODO: pull length from api call
        //   shrinkWrap: true,scrollDirection: Axis.vertical,itemBuilder: (context, index){
        //
        //     return singleItem(
        //         price: 12.99,
        //         itemName: "Some Bubble Tea",
        //         imageSrc: "https://chatime.com/wp-content/uploads/2020/10/Brown-Sugar-Pearls-with-Milk-Tea.png");
        // }
        //
        // ),


          Positioned(
            top: 0.0,
            left: 0.0,
            right: 0.0,
            child: AppBar(
              centerTitle: true,
              title: const Text('Menu',style: TextStyle(color: Colors.black,
              fontFamily: "Josefin Sans", fontWeight: FontWeight.bold, fontSize: 22
              ),),// You can add title here
              leading: IconButton(
                icon: const Icon(Icons.arrow_back_ios, color: Colors.black),
                onPressed: () => Navigator.of(context).pop(),
              ),
              backgroundColor: Colors.white.withOpacity(1), //You can make this transparent
              elevation: 0.0, //No shadow
            ),),

      ]


      ),
    );
  }

}

Widget singleItem({required String imageSrc, required String itemName, required double price}){
  return Container(
    margin: const EdgeInsets.only(bottom:30, right: 30, left: 30),
    height: 125,
    //width: 20,
    color: Colors.white,
    
    child: Stack(
      children: [Row(
        children: [
          Container(
            margin: const EdgeInsets.only(right: 10),
            height: 125,
            width: 100,
            child: Image.network(imageSrc, fit: BoxFit.fitWidth,),
          ),
          Align(
              alignment: Alignment.topCenter,
              child: Padding(
                  padding: EdgeInsets.only(top: 15),
                  child: Column(

                      children: [Text(itemName, style:
                    TextStyle(fontSize: 22,
                      fontWeight: FontWeight.w500,
                      fontFamily: "Josefin Sans"
                    ),),
                        Padding(
                            padding: EdgeInsets.only(right: 135),
                            child: Text("\$"+price.toString(),
                              style: TextStyle(
                                  color: Colors.grey.shade500,
                                  fontWeight: FontWeight.bold),
                            ))

  ]
  )
  )
  )


        ],

      ),

        Positioned(right: 40, top: 70,
          child: ElevatedButton(
            onPressed: () {

            },
            child: Text(
              "View",
              style: TextStyle(
                  fontFamily: "Josefin Sans",
                  fontWeight: FontWeight.bold,
                  fontSize: 15),
            ),
            style: ElevatedButton.styleFrom(
              shape: const RoundedRectangleBorder(
                  borderRadius: BorderRadius.all(Radius.circular(8))),
              minimumSize: const Size(150, 45),
              primary: const Color.fromRGBO(86, 99, 255, 1),
            ),
          ),
        ),

  ]
    ),

  );
}