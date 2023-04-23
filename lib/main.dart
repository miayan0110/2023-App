//import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'search.dart';
import 'drawer_routes.dart';
import 'todo.dart';
import 'date.dart';
import 'more.dart';
import 'message.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'My App',
      initialRoute: '/',
      routes: {
        '/': (context) => MyHomePage(title: 'home'),
        '/profile': (context) => Profile(),
        '/setting': (context) => Setting(),
        '/message': (context) => Message()
      },
      theme: ThemeData(
        primaryColor: Color.fromARGB(255, 125, 199, 210), //appbar color
        // accentColor: Color.fromARGB(255, 125, 199, 210), //body color
        // primarySwatch: Colors.yellow,  //theme color (appbar + body)
      ),
      // home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _selectedIndex = 0;
  final pages = [ToDo(), Date(), More()];
  // static List<StatelessWidget> _widgetOption = <StatelessWidget>[
  //   Icon(Icons.assignment_rounded, size: 50, color: Color.fromARGB(255, 206, 196, 197),),
  //   Icon(Icons.calendar_today_rounded, size: 50, color: Color.fromARGB(255, 206, 196, 197)),
  //   Icon(Icons.menu_rounded, size: 50, color: Color.fromARGB(255, 206, 196, 197)),
  // ];

  void _onItemTap(int index) {
    setState(() => _selectedIndex = index);
}

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
        length: tabList.length,
        child: Scaffold(
          drawer: MyDrawer(),
          appBar: AppBar(
            title: Text(
              "おはよー",
              style: TextStyle(
                  color: Color.fromARGB(255, 255, 250, 235)
              ),
            ),
            leading: Builder(
              builder: (context) => IconButton(
                splashColor: Color.fromARGB(255, 99, 149, 160),
                  icon: Icon(
                    Icons.menu_rounded,
                    color: Color.fromARGB(255, 255, 250, 235),
                  ),
                  onPressed: () {
                    Scaffold.of(context).openDrawer();
                    },
              ),
            ),
            actions: <Widget>[
              IconButton(
                  onPressed: (){
                    Navigator.push(context, MaterialPageRoute(builder: (context) => Search()));
                  },
                  icon: Icon(
                    Icons.search_rounded,
                    color: Color.fromARGB(255, 255, 250, 235),
                  )
              )
            ],
            // bottom: TabBar(  // 上面的切換條
            //   labelColor: Color.fromARGB(255, 255, 250, 235), //text color
            //   tabs: tabList.map((choice) {
            //     return Tab(
            //       text: choice.title,
            //       icon: Icon(
            //         choice.icon,
            //         color: Color.fromARGB(255, 255, 250, 235),
            //       ),
            //     );
            //   }).toList(),
            // ),
          ),
          bottomNavigationBar: BottomNavigationBar(
            items: [
              BottomNavigationBarItem(icon: Icon(Icons.assignment_rounded), label: "todo"),
              BottomNavigationBarItem(icon: Icon(Icons.calendar_today_rounded), label: "date"),
              BottomNavigationBarItem(icon: Icon(Icons.menu_rounded), label: "more")
            ],
            onTap: _onItemTap,
            currentIndex: _selectedIndex,
            selectedItemColor: Color.fromARGB(255, 125, 199, 210),
          ),
          // body: TabBarView(
          //   children: tabList.map((choice) {
          //     return Center(
          //       child: Icon(
          //         choice.icon,
          //         size: 75,
          //         color: Color.fromARGB(255, 206, 196, 197),
          //       ),
          //     );
          //   }).toList(),
          // ),
          // body: Center(child: _widgetOption.elementAt(_selectedIndex)),
          body: pages[_selectedIndex],
        )
    );
  }
}


class TabChoice {
  final String title;
  final IconData icon;

  const TabChoice(this.title, this.icon);
}

const List<TabChoice> tabList = const <TabChoice>[
  TabChoice("morning", Icons.wb_sunny_rounded),
  TabChoice("night", Icons.bedtime_rounded)
];
