
// import 'dart:html';

import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:shared_preferences/shared_preferences.dart';

class Date extends StatefulWidget {
  @override
  _DateState createState() => _DateState();
}

class _DateState extends State<Date> {
  final String prefCounter = "prefCounter";
  int _counter = 0;

  @override
  void initState(){
    super.initState();
    _loadCounter();
  }

  _loadCounter() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    setState(() {
      _counter = (prefs.getInt(prefCounter) ?? 0);
    });
  }

  void pressedTimes() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    setState(() {
      _counter = (prefs.getInt(prefCounter) ?? 0) + 1;
      prefs.setInt(prefCounter, _counter);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(
                "今日は誰とデートするのー？",
                style: TextStyle(
                  color: Color.fromARGB(255, 206, 196, 197),
                ),
              ),
              Text(
                "$_counter",
                style: TextStyle(
                    color: Color.fromARGB(255, 206, 196, 197),
                    fontSize: 30
                ),
              )
            ],
          )
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: pressedTimes,
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