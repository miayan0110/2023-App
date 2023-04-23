import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';

class Profile extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          iconTheme: IconThemeData(color: Color.fromARGB(255, 255, 250, 235)),
          title: Text(
            "プロフィール",
            style: TextStyle(
              color: Color.fromARGB(255, 255, 250, 235)
            ),
          ),
          leading: IconButton(
            onPressed: () {
              Navigator.pop(context);
            },
            icon: Icon(
              Icons.arrow_back_ios_rounded,
              color: Color.fromARGB(255, 255, 250, 235),
            ),
          ),
        ),
        body: Center(
          child: Text(
            "なーんもないよovo",
            style: TextStyle(
              color: Color.fromARGB(255, 206, 196, 197),
              fontSize: 15,
            ),
          ),
        ));
  }
}

class Setting extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          iconTheme: IconThemeData(color: Color.fromARGB(255, 255, 250, 235)),
          title: Text(
            "セッティング",
            style: TextStyle(
                color: Color.fromARGB(255, 255, 250, 235)
            ),
          ),
          leading: IconButton(
            onPressed: () {
              Navigator.pop(context);
            },
            icon: Icon(
              Icons.arrow_back_ios_rounded,
              color: Color.fromARGB(255, 255, 250, 235),
            ),
          ),
        ),
        body: Center(
          child: Text(
            "なーんもないよovo",
            style: TextStyle(
              color: Color.fromARGB(255, 206, 196, 197),
              fontSize: 15,
            ),
          ),
        ));
  }
}

class MyDrawer extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        children: <Widget>[
          UserAccountsDrawerHeader(
            accountEmail: Text(
              "suzukinya@gmail.com",
              style: TextStyle(
                  color: Color.fromARGB(255, 255, 250, 235)
              ),
            ),
            accountName: Text(
              "suzuki nya",
              style: TextStyle(
                  color: Color.fromARGB(255, 255, 250, 235)
              ),
            ),
            currentAccountPicture: CircleAvatar(
              radius: 20,
              backgroundImage: AssetImage("assets/images/dr1.jpg"),
            ),
            decoration: BoxDecoration(
                color: Color.fromARGB(255, 99, 149, 160)
            ),
          ),
          ListTile(
            leading: Icon(
              Icons.messenger_rounded,
              color: Color.fromARGB(255, 151, 142, 143),
            ),
            title: Text(
              "message",
              style: TextStyle(
                  color: Color.fromARGB(255, 151, 142, 143)
              ),
            ),
            onTap: (){
              Navigator.pushNamed(context, '/message');
            },
          ),
          ListTile(
            leading: Icon(
              Icons.account_circle_rounded,
              color: Color.fromARGB(255, 151, 142, 143),
            ),
            title: Text(
              "profile",
              style: TextStyle(
                  color: Color.fromARGB(255, 151, 142, 143)
              ),
            ),
            onTap: (){
              Navigator.pushNamed(context, '/profile');
            },
          ),
          ListTile(
            leading: Icon(
              Icons.settings,
              color: Color.fromARGB(255, 151, 142, 143),
            ),
            title: Text(
              "setting",
              style: TextStyle(
                  color: Color.fromARGB(255, 151, 142, 143)
              ),
            ),
            onTap: (){
              Navigator.pushNamed(context, '/setting');
            },
          )
        ],
      ),
    );
  }
}
