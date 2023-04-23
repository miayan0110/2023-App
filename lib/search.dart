import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';

class Search extends StatefulWidget {
  @override
  _SearchState createState() => _SearchState();
}

class _SearchState extends State<Search> {
  final TextEditingController myController = new TextEditingController();
  String myString = "もう~ご主人様は何も言わないの？\n私、泣いちゃうよ~";

  void printString(String text){
    myController.clear();
    setState(() {
      if(text != "") myString = "ご主人様、あなたは" + text + "って言ったの？";
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: Icon(Icons.arrow_back_ios_rounded),
          color: Color.fromARGB(255, 255, 250, 235),
          onPressed: (){
            Navigator.pop(context);
          },
        ),
        title: TextField(
          controller: myController,
          onSubmitted: printString,
          style: TextStyle(
            color: Color.fromARGB(255, 255, 250, 235),
          ),
          decoration: InputDecoration(
            border: InputBorder.none,
            labelText: "ご主人様はお話がありますか~",
            labelStyle: TextStyle(color: Color.fromARGB(255, 255, 250, 235)),
            hintText: "なに~",
            hintStyle: TextStyle(color: Color.fromARGB(100, 255, 250, 235))
          ),
        ),
        actions: <Widget>[
          IconButton(
            icon: Icon(
              Icons.reply_rounded,
              color: Color.fromARGB(255, 255, 250, 235),
            ),
            onPressed: (){
              printString(myController.text);
            },
          )
        ]
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              myString,
              style: TextStyle(
                  color: Color.fromARGB(255, 206, 196, 197)
              ),
            )
          ],
        ),
      )
    );
  }
}
