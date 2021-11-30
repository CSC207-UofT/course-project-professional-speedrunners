import 'package:boba_buddy/core/model/models.dart';
import 'package:boba_buddy/core/repository/store_repository.dart';
import 'package:boba_buddy/modules/home/shops/bloc/popular_shop_bloc.dart';
import 'package:boba_buddy/modules/home/shops/bloc/popular_shop_state.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

import 'bloc/popular_shop_event.dart';

const double spaceBetween = 10.00;

class PopularShops extends StatelessWidget {
  const PopularShops({Key? key}):
        super(key: key);

  static Route route(StoreRepository storeRepository) {
    return MaterialPageRoute<void>(builder: (context) => const PopularShops());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: ScreenUtilInit(
        designSize: const Size(393, 830),
        builder: () => Container(
          width: 400.w,
          height: 400.h,
          margin: EdgeInsets.only(top: 16.h),
          child: BlocProvider(
            create: (_) => PopularShopBloc(storeRepo: context.read<StoreRepository>())..add(FetchShopList()),
            child: const PopularShopView(),
          ),
        ),
      )
    );
  }
}

class PopularShopView extends StatelessWidget {
  const PopularShopView({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<PopularShopBloc, PopularShopState>(
      builder: (context, state) {
        switch (state.status) {
          case PopularShopStatus.error:
            return const Text('failed to fetch stores');
          case PopularShopStatus.loaded:
            if (state.stores.isEmpty) {
              return const Center(child: Text('no data'));
            }
            return SingleChildScrollView(
              scrollDirection: Axis.horizontal,
              child: Row(children: <Widget>[
                Container(
                    height: 225,
                    margin: EdgeInsets.only(left: spaceBetween.w),
                    child: ListView.builder(
                        shrinkWrap: true,
                        scrollDirection: Axis.horizontal,
                        itemCount: state.stores.length,
                        itemBuilder: (BuildContext context, int index) {
                          return _buildSingleShop(
                              context: context,
                              imageSrc:
                                  'https://d1ralsognjng37.cloudfront.net/3586a06b-55c6-4370-a9b9-fe34ef34ad61.jpeg',
                              //todo need image src implemented in entity classes
                              store: state.stores[index]);
                        }))
              ]),
            );
          default:
            return const Center(child: CircularProgressIndicator());
        }
      },
    );
  }
}

///Widget builder for a single shop widget
Widget _buildSingleShop(
    {required context, required String imageSrc, required Store store}) {
  final double width = 325.w;
  final double height = 100.h;

  return InkWell(
    // onTap: () {
    //   Navigator.push(
    //       context,
    //       MaterialPageRoute(
    //           builder: (context) => StorePage(
    //                 store: store,
    //                 imageSrc: imageSrc,
    //                 item: item!,
    //               )));
    // },
    child: Container(
      width: width,
      height: height,
      margin: const EdgeInsets.only(bottom: 7, right: 15),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: const BorderRadius.only(
          topLeft: Radius.circular(10),
          topRight: Radius.circular(10),
          bottomLeft: Radius.circular(10),
          bottomRight: Radius.circular(10),
        ),
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withOpacity(0.5),
            spreadRadius: 1,
            blurRadius: 6,
            offset: const Offset(0, 3), // changes position of shadow
          ),
        ],
      ),
      child: ClipRRect(
        //borderRadius: BorderRadius.all(const Radius.circular(8)),
        child: Stack(
          children: <Widget>[
            SizedBox(
                width: width,
                height: 160,
                child: Image.network(imageSrc,
                    width: 200,
                    height: 100,
                    fit: BoxFit.fitWidth, errorBuilder: (BuildContext context,
                        Object exception, StackTrace? stackTrace) {
                  //Error handling for image
                  return const Image(
                      image:
                          AssetImage("assets/images/default-store.dart.png"));
                })),
            Positioned(
              bottom: -15,
              child: Align(
                alignment: Alignment.bottomLeft,
                child: SizedBox(
                  width: width,
                  height: 110,
                  child: Align(
                    alignment: Alignment.centerLeft,
                    child: Padding(
                      padding: const EdgeInsets.only(left: 10),
                      child: Text(
                        store.name,
                        maxLines: 1,
                        textAlign: TextAlign.start,
                        style: const TextStyle(
                            fontFamily: "Josefin Sans",
                            color: Colors.black,
                            fontWeight: FontWeight.bold,
                            fontSize: 15),
                        // style: AppTheme.getTextStyle(themeData.textTheme.subtitle1,
                        //     fontWeight: 600, color: Colors.white, letterSpacing: 0.3),
                      ),
                    ),
                  ),
                ),
              ),
            ),
            Positioned(
              bottom: -45,
              left: -12,
              child: SizedBox(
                width: width,
                height: 120,
                child: Center(
                  child: Align(
                    alignment: Alignment.centerLeft,
                    child: Padding(
                      padding: const EdgeInsets.only(left: 23),
                      child: Text(
                        store.location,
                        maxLines: 1,
                        textAlign: TextAlign.start,
                        style: TextStyle(
                            color: Colors.grey[600],
                            fontWeight: FontWeight.w500),
                        // style: AppTheme.getTextStyle(themeData.textTheme.subtitle1,
                        //     fontWeight: 600, color: Colors.white, letterSpacing: 0.3),
                      ),
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    ),
  );
}
