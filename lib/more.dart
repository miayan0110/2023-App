
// import 'dart:html';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
// import 'package:transparent_image/transparent_image.dart';

class More extends StatefulWidget {
  @override
  _MoreState createState() => _MoreState();
}

class _MoreState extends State<More> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
          // child: Column(
          //   mainAxisAlignment: MainAxisAlignment.center,
          //   children: [
          //     // Container(
          //     //   child: FadeInImage.memoryNetwork(
          //     //       placeholder: kTransparentImage,
          //     //       image: "https://i.ibb.co/zSCgXYr/download-20210628222710.webp",
          //     //     fadeInDuration: Duration(seconds: 1),
          //     //   ),
          //     // ),
          //     Text(
          //       "へー何？まだあるんのー？",
          //       style: TextStyle(
          //         color: Color.fromARGB(255, 206, 196, 197),
          //       ),
          //     ),
          //   ],
          // )
        child: GridView.count(
          crossAxisCount: 2,
          padding: EdgeInsets.all(16.0),
          childAspectRatio: 14.0 / 15.0,
          children: [
            PictureCard("Dr.STONE 1", "assets/images/dr1.jpg"),
            PictureCard("Dr.STONE 2", "assets/images/dr2.jpg"),
            PictureCard("Dr.STONE 3", "assets/images/dr3.jpg"),
            PictureCard("Dr.STONE 4", "assets/images/dr4.jpg"),
            PictureCard("Dr.STONE 5", "assets/images/dr5.jpg"),
            PictureCard("Dr.STONE 6", "assets/images/dr6.jpg"),
            PictureCard("Dr.STONE 7", "assets/images/dr7.jpg"),
            PictureCard("Dr.STONE 8", "assets/images/dr8.jpg"),
            PictureCard("Dr.STONE 9", "assets/images/dr9.jpg"),
          ]
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: (){},
        splashColor: Color.fromARGB(255, 99, 149, 160),
        backgroundColor: Color.fromARGB(255, 125, 199, 210),
        child: Icon(
          Icons.add_rounded,
          color: Color.fromARGB(255, 255, 250, 235),
        ),
      ),
    );
  }
}

class PictureCard extends StatelessWidget {
  final String name;
  final String pic;
  const PictureCard(this.name, this.pic);

  @override
  Widget build(BuildContext context) {
    return Card(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          AspectRatio(
            aspectRatio: 16.0/11.0,
            child: Image.asset(
              pic,
              fit: BoxFit.fitWidth,
            ),
          ),
          Padding(
            padding: EdgeInsets.fromLTRB(16.0, 12.0, 16.0, 8.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  name,
                  style: TextStyle(
                    color: Color.fromARGB(255, 151, 142, 143),
                    fontSize: 15
                  ),
                ),
                SizedBox(height: 7.5),
                Row(
                  children: [
                    // SizedBox(width: 102.0,),
                    Text(
                      "いいね",
                      style: TextStyle(
                        color: Color.fromARGB(255, 206, 196, 197),
                        fontSize: 12
                      ),
                    ),
                    Icon(
                      Icons.favorite_border_rounded,
                      size: 12,
                      color: Color.fromARGB(255, 206, 196, 197),
                    ),
                  ],
                ),
              ],
            ),
          )
        ],
      ),
    );
  }
}
