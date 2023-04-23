
// import 'dart:html';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:shared_preferences/shared_preferences.dart';

class ToDo extends StatefulWidget {
  @override
  _ToDoState createState() => _ToDoState();
}

class _ToDoState extends State<ToDo> {
  List<TD> _todo = [];
  List<String> _titles = [];
  List<String> _details = [];
  final t = TextEditingController();
  final d = TextEditingController();
  final String prefTitle = "prefTitle";
  final String prefDetail = "prefDetail";


  @override
  void initState(){
    super.initState();
    _loadTodo();
  }

  _loadTodo() async {

    print("***** loading todo list *****");

    SharedPreferences prefs = await SharedPreferences.getInstance();
    // prefs.remove(prefTitle);
    // prefs.remove(prefDetail);

    setState(() {
      _titles = prefs.getStringList(prefTitle) ?? [];
      _details = prefs.getStringList(prefDetail) ?? [];
      for(int i = 0; i < _titles.length; i++){
        var td = TD(t: _titles[i], d: _details[i]);
        _todo.insert(_todo.length, td);
      }
    });
  }

  void makeNewToDo({String t = "title", String d = "null"}) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    if(t == "") t = "title";
    var td = TD(t: t, d: d);
    _titles.insert(_titles.length, t);
    _details.insert(_details.length, d);

    print("***** making new todo *****");

    setState(() {
      _todo.insert(_todo.length, td);
      prefs.clear();
      prefs.setStringList(prefTitle, _titles);
      prefs.setStringList(prefDetail, _details);
    });
  }

  void addNewToDo(BuildContext context){
    showModalBottomSheet(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(25.0))
      ),
        context: context,
        builder: (context) => Padding(
          padding: EdgeInsets.all(15.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              TextField(
                controller: t,
                style: TextStyle(
                  color: Color.fromARGB(255, 151, 142, 143),
                ),
                decoration: InputDecoration(
                  icon: IconButton(
                    icon: Icon(Icons.arrow_back_ios_rounded),
                    color: Color.fromARGB(255, 151, 142, 143),
                    onPressed: (){
                      t.clear();
                      d.clear();
                      Navigator.pop(context);
                    },
                  ),
                    border: InputBorder.none,
                    hintText: "title",
                    hintStyle: TextStyle(
                        color: Color.fromARGB(255, 151, 142, 143)
                    )
                ),
              ),
              SizedBox(height: 0.5),
              Expanded(
                  child: TextField(
                    controller: d,
                    maxLines: null,
                    style: TextStyle(
                      color: Color.fromARGB(255, 151, 142, 143),
                    ),
                    decoration: InputDecoration(
                        border: InputBorder.none,
                        hintText: "details",
                        hintStyle: TextStyle(
                            color: Color.fromARGB(255, 151, 142, 143)
                        )
                    ),
                  ),
              ),
              Divider(height: 0.5),
              Row(
                mainAxisAlignment: MainAxisAlignment.end,
                children: [
                  ElevatedButton(
                      onPressed: (){
                        Navigator.pop(context);
                        makeNewToDo(t: t.text, d: d.text);
                        t.clear();
                        d.clear();
                      },
                      child: Text("save"),
                      style: ElevatedButton.styleFrom(
                          primary: Theme.of(context).primaryColor,
                          textStyle: TextStyle(
                              color: Color.fromARGB(255, 255, 250, 235)
                          )
                      )
                  ),
                  SizedBox(width: 15),
                ],
              ),
            ]
          ),
        )
    );
  }

  void deleteToDo(String t, String d) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    // var td = TD(t: t, d: d);
    _titles.remove(t);
    _details.remove(d);

    print("***** delete todo *****");

    setState(() {
      _todo.clear();
      prefs.clear();
      prefs.setStringList(prefTitle, _titles);
      prefs.setStringList(prefDetail, _details);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          Flexible(
              child: ListView.builder(
                itemBuilder: (_, int index) => _todo[index],
                itemCount: _todo.length,
              )
          ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: (){
          addNewToDo(context);
        },
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

class TD extends StatelessWidget {
  final String t;
  final String d;
  TD({required this.t, required this.d});

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 2.0,
      clipBehavior: Clip.antiAlias,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.all(Radius.circular(20.0))),
      child: InkWell(
        onTap: (){
          print("tap!");
          _ToDoState().addNewToDo(context);
        },
        onLongPress: (){
          print("long press!");
          _ToDoState().deleteToDo(t, d);
        },
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Padding(
              padding: EdgeInsets.fromLTRB(10.0, 15.0, 10.0, 15.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    t,
                    style: TextStyle(
                        color: Color.fromARGB(255, 104, 103, 99)
                    ),
                  ),
                  Divider(height: 5.0),
                  Text(
                    d,
                    style: TextStyle(
                        color: Color.fromARGB(255, 151, 142, 143)
                    ),
                  ),
                ],
              ),
            )
          ],
        ),
      )
    );
  }
}



