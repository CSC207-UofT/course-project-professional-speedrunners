import 'dart:ui';
import 'package:boba_buddy/Screens/price_update_page.dart';
import 'package:boba_buddy/core/model/models.dart';
import 'package:boba_buddy/core/repository/rating_repository.dart';
import 'package:boba_buddy/core/repository/repository.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:maps_launcher/maps_launcher.dart';
import 'package:provider/src/provider.dart';

import 'full_menu_page.dart';

class StorePage extends StatefulWidget {
  const StorePage({
    Key? key,
    required this.imageSrc,
    required this.store,
    required this.item,
  }) : super(key: key);

  final String imageSrc;
  final Store store;
  final Item item;

  @override
  _StorePage createState() => _StorePage();
}

class _StorePage extends State<StorePage> {
  @override
  Widget build(BuildContext context) {
    double deviceWidth = MediaQuery.of(context).size.width;
    double deviceHeight = MediaQuery.of(context).size.height;
    ItemRepository db = context.read<ItemRepository>();
    UserRepository userRepository = context.read<UserRepository>();
    RatingRepository ratingRepository = context.read<RatingRepository>();
    bool isThumbsDownPressed, isThumbsUpPressed;

    return Scaffold(
      body: SingleChildScrollView(
        child: Stack(
          children: [
            SizedBox(
              width: deviceWidth,
              height: deviceHeight + 100,
            ),
            ClipRRect(
              borderRadius: const BorderRadius.only(
                  bottomLeft: Radius.circular(10),
                  bottomRight: Radius.circular(10)),
              child: Container(
                width: deviceWidth,
                height: 300,
                decoration: BoxDecoration(
                  image: DecorationImage(
                    image: NetworkImage(widget.imageSrc),
                    fit: BoxFit.cover,
                  ),
                  shape: BoxShape.rectangle,
                ),
              ),
            ),
            Positioned(
                top: 20,
                child: IconButton(
                    onPressed: () {
                      Navigator.pop(context);
                    },
                    icon: const Icon(
                      Icons.arrow_back,
                      size: 30,
                      color: Colors.white,
                    ))),
            SizedBox(
                width: deviceWidth,
                height: 300,
                child: Align(
                  alignment: Alignment.bottomCenter,
                  child: Padding(
                      padding: const EdgeInsets.only(bottom: 8),
                      child: ClipRRect(
                          borderRadius:
                              const BorderRadius.all(Radius.circular(30)),
                          child: Container(
                              width: deviceWidth / 3,
                              height: 50,
                              color: Colors.black.withOpacity(0.15),
                              child: BackdropFilter(
                                  filter: ImageFilter.blur(
                                      sigmaX: 3.0,
                                      sigmaY: 3.0,
                                      tileMode: TileMode.mirror),
                                  child: Row(children: [
                                    Padding(
                                        padding: const EdgeInsets.only(
                                            left: 20, top: 10),
                                        child: GestureDetector(
                                          onTap: () {
                                            print("Open in maps");
                                            MapsLauncher.launchQuery(
                                                widget.store.location);
                                          },
                                          child: SizedBox(
                                            height: 50,
                                            //width: deviceWidth / 1.25,
                                            child: RichText(
                                              text: const TextSpan(children: [
                                                WidgetSpan(
                                                    child: Icon(
                                                  Icons.directions_outlined,
                                                  color: Colors.white,
                                                )),
                                                TextSpan(
                                                    text: "Directions",
                                                    style: TextStyle(
                                                        fontFamily:
                                                            "Josefin Sans",
                                                        fontSize: 15))
                                              ]),
                                            ),
                                          ),
                                        )

                                        // Text(
                                        //   "Directions",
                                        //   style: TextStyle(
                                        //       color: Colors.white,
                                        //       fontFamily: "Josefin Sans",
                                        //       fontSize: 15),
                                        // ),
                                        )
                                  ]))))),
                )),
            Positioned(
                top: 325,
                left: 25,
                child: Text(
                  widget.store.name,
                  textAlign: TextAlign.start,
                  style: const TextStyle(
                      fontFamily: "Josefin Sans",
                      fontSize: 30,
                      fontWeight: FontWeight.bold),
                )),
            Positioned(
                top: 370,
                left: 25,
                child: Text(
                  widget.store.location,
                  textAlign: TextAlign.start,
                  style: const TextStyle(
                      fontFamily: "Josefin Sans",
                      fontSize: 15,
                      fontWeight: FontWeight.w600,
                      color: Colors.grey),
                )),
            Positioned(
                top: 680,
                left: 25,
                child: GestureDetector(
                  onTap: () {
                    Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => FullMenuPage(
                                  store: widget.store,
                                )));
                  },
                  child: const SizedBox(
                    height: 50,
                    child: Text('View All Menu Prices',
                        style: TextStyle(
                          fontFamily: "Josefin Sans",
                          fontWeight: FontWeight.bold,
                          fontSize: 20,
                          color: Colors.blue,
                        )),
                  ),
                )),

            Positioned(
              bottom: 200,
              child: SizedBox(
                  child: FutureBuilder(
                      future: widget.item.id.isEmpty
                          ? db.getOneItemFromStore(widget.store.id)
                          : db.getItemById(widget.item.id),
                      builder: (context, AsyncSnapshot snapshot) {
                        String email = userRepository.currentUser.email;
                        if (!snapshot.hasData) {
                          return const Center(
                              child: CircularProgressIndicator());
                        }
                        // TODO: properly handle empty response
                        // else if (snapshot.data.length == 0) {
                        //   return const Padding(
                        //       padding: EdgeInsets.only(bottom: 100),
                        //       child: Text(
                        //         "no items available",
                        //         style: TextStyle(fontSize: 30),
                        //       ));
                        // }
                        else {
                          print("888");
                          print(snapshot.data.id);
                          print(snapshot.data.imageUrl);
                          return FutureBuilder(
                              future: userRepository.getUser(email),
                              builder: (context, AsyncSnapshot userSnapshot) {
                                if (!userSnapshot.hasData) {
                                  return const CircularProgressIndicator();
                                }

                                Rating? rating = _getUserRating(
                                    userSnapshot.data, snapshot.data);
                                if (rating == null) {
                                  isThumbsUpPressed = false;
                                  isThumbsDownPressed = false;
                                } else {
                                  if (rating.rating == 1) {
                                    isThumbsUpPressed = true;
                                    isThumbsDownPressed = false;
                                  } else {
                                    isThumbsUpPressed = false;
                                    isThumbsDownPressed = true;
                                  }
                                }
                                print(widget.item.id);
                                return Stack(children: [
                                  SizedBox(
                                    width: deviceWidth,
                                    height: 300,
                                  ),
                                  Positioned(
                                    left: 25,
                                    bottom: 250,
                                    child: Text(
                                      snapshot.data.name.toString(),
                                      style: const TextStyle(
                                          color: Colors.black,
                                          fontFamily: "Josefin Sans",
                                          fontSize: 30,
                                          fontWeight: FontWeight.bold),
                                    ),
                                  ),
                                  Container(
                                      margin: const EdgeInsets.only(
                                          left: 25, top: 65),
                                      width: 200,
                                      height: 200,
                                      decoration: BoxDecoration(
                                          image: DecorationImage(
                                            image: NetworkImage(
                                              snapshot.data.imageUrl ??
                                                  "https://www.trendsetter.com/pub/media/catalog/product/placeholder/default/no_image_placeholder.jpg",

                                              //  "https://theforkedspoon.com/wp-content/uploads/2019/03/How-to-make-Bubble-Tea-8.jpg"
                                            ),
                                            fit: BoxFit.cover,
                                          ),
                                          shape: BoxShape.rectangle,
                                          borderRadius: const BorderRadius.all(
                                              Radius.circular(10)))),
                                  Positioned(
                                    left: 260,
                                    top: 80,
                                    child: Text(
                                      "\$${snapshot.data.price.toString()}",
                                      textAlign: TextAlign.start,
                                      style: const TextStyle(
                                          color: Colors.black,
                                          fontFamily: "Josefin Sans",
                                          fontSize: 20,
                                          fontWeight: FontWeight.bold),
                                    ),
                                  ),
                                  Positioned(
                                    left: 320,
                                    top: 80,
                                    child: Text(
                                      "${(100 * snapshot.data.avgRating).round().toString()}%",
                                      textAlign: TextAlign.start,
                                      style: const TextStyle(
                                          color: Colors.black,
                                          fontFamily: "Josefin Sans",
                                          fontSize: 20,
                                          fontWeight: FontWeight.bold),
                                    ),
                                  ),
                                  Positioned(
                                      left: 260,
                                      top: 150,
                                      child: IconButton(
                                        icon: isThumbsUpPressed
                                            ? const Icon(
                                                Icons.thumb_up_alt_sharp)
                                            : const Icon(
                                                Icons.thumb_up_outlined),
                                        onPressed: () => setState(() {
                                          if (!isThumbsUpPressed &&
                                              isThumbsDownPressed) {
                                            isThumbsDownPressed =
                                                !isThumbsDownPressed;
                                            ratingRepository.updateRating(
                                                rating!.id, 1);
                                          } else if (!isThumbsUpPressed &&
                                              !isThumbsDownPressed) {
                                            ratingRepository.addRating(
                                                1,
                                                userRepository
                                                    .currentUser.email,
                                                snapshot.data.id);
                                          } else {
                                            ratingRepository
                                                .deleteRating(rating!.id);
                                          }
                                          isThumbsUpPressed =
                                              !isThumbsUpPressed;
                                        }),
                                        color: Colors.green,
                                      )),
                                  Positioned(
                                      left: 310,
                                      top: 150,
                                      child: IconButton(
                                        icon: isThumbsDownPressed
                                            ? const Icon(
                                                Icons.thumb_down_alt_sharp)
                                            : const Icon(
                                                Icons.thumb_down_outlined),
                                        onPressed: () => setState(() {
                                          if (!isThumbsDownPressed &&
                                              isThumbsUpPressed) {
                                            isThumbsUpPressed =
                                                !isThumbsUpPressed;
                                            ratingRepository.updateRating(
                                                rating!.id, 0);
                                          } else if (!isThumbsDownPressed &&
                                              !isThumbsUpPressed) {
                                            ratingRepository.addRating(
                                                0,
                                                userRepository
                                                    .currentUser.email,
                                                snapshot.data.id);
                                          } else {
                                            ratingRepository
                                                .deleteRating(rating!.id);
                                          }
                                          isThumbsDownPressed =
                                              !isThumbsDownPressed;
                                        }),
                                        color: Colors.red,
                                      )),
                                  Positioned(
                                    left: 250,
                                    top: 220,
                                    child: ElevatedButton(
                                      onPressed: () {
                                        Navigator.of(context).push(
                                            MaterialPageRoute(
                                                builder: (context) =>
                                                    PriceUpdaterPage(
                                                      item: widget.item,
                                                      imageSrc: widget.imageSrc,
                                                    )));
                                      },
                                      child: const Text(
                                        'Wrong Price?',
                                        style: TextStyle(
                                            fontFamily: "Josefin Sans",
                                            fontWeight: FontWeight.bold,
                                            fontSize: 15),
                                      ),
                                      style: ElevatedButton.styleFrom(
                                        shape: const RoundedRectangleBorder(
                                            borderRadius: BorderRadius.all(
                                                Radius.circular(8))),
                                        minimumSize: const Size(100, 40),
                                        primary: const Color.fromRGBO(
                                            132, 141, 255, 1),
                                      ),
                                    ),
                                  ),
                                ]);
                              });
                        }
                      })),
            )
            // Positioned(bottom: 200,//     child: itemWidget(imageSrc: widget.imageSrc, itemId: widget.itemId))
          ],
        ),
      ),
    );
  }
}

Rating? _getUserRating(User user, ratable) {
  List<Rating>? userRatings = user.ratings;
  if (userRatings != null && ratable.ratings != null) {
    for (Rating i in userRatings) {
      if (ratable.ratings.contains(i)) return i;
    }
  }
}

Widget itemWidget(
    {required String imageSrc,
    required String itemId,
    required BuildContext context}) {
  return FutureBuilder(
    future: context.read<ItemRepository>().getItemById(itemId),
    builder: (BuildContext context, AsyncSnapshot<dynamic> snapshot) {
      if (!snapshot.hasData) {
        return const Center(child: CircularProgressIndicator());
      } else {
        return Stack(children: [
          Text(
            snapshot.data.name.toString(),
            style: const TextStyle(
                color: Colors.black,
                fontFamily: "Josefin Sans",
                fontSize: 20,
                fontWeight: FontWeight.bold),
          ),
          ClipRRect(
            borderRadius: const BorderRadius.all(Radius.circular(8)),
            child: Container(
                width: 200,
                height: 200,
                decoration: const BoxDecoration(
                  image: DecorationImage(
                    image: NetworkImage(
                        "https://theforkedspoon.com/wp-content/uploads/2019/03/How-to-make-Bubble-Tea-8.jpg"),
                    fit: BoxFit.fitWidth,
                  ),
                  shape: BoxShape.rectangle,
                )),
          ),
          Text(
            "\$${snapshot.data.price.toString()}",
            textAlign: TextAlign.start,
            style: const TextStyle(
                color: Colors.black,
                fontFamily: "Josefin Sans",
                fontSize: 20,
                fontWeight: FontWeight.bold),
          ),
          Positioned(
            top: 550,
            left: 230,
            child: ElevatedButton(
              onPressed: () {
                // Navigator.of(context).push(MaterialPageRoute( //TODO : send item id to updater page but set default values on store page to optimize db calls when needing store information
                //     builder: (context) => PriceUpdaterPage(itemId: itemId, storeName: , address: '', storeId: '', imageSrc: '',)));
              },
              child: const Text(
                'Wrong Price?',
                style: TextStyle(
                    fontFamily: "Josefin Sans",
                    fontWeight: FontWeight.bold,
                    fontSize: 15),
              ),
              style: ElevatedButton.styleFrom(
                shape: const RoundedRectangleBorder(
                    borderRadius: BorderRadius.all(Radius.circular(8))),
                minimumSize: const Size(100, 40),
                primary: const Color.fromRGBO(132, 141, 255, 1),
              ),
            ),
          ),
        ]);
      }
    },
  );
}
