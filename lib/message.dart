
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';

class Message extends StatefulWidget {
  @override
  _MessageState createState() => _MessageState();
}

class _MessageState extends State<Message> {
  final _textController = TextEditingController();
  final List<NewMessage> _messages = [];

  void _handleSubmitted(String text){
    _textController.clear();
    var message = NewMessage(text: text);
    setState(() {
      _messages.insert(_messages.length, message);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          iconTheme: IconThemeData(color: Color.fromARGB(255, 255, 250, 235)),
          title: Text(
            "メッセージ",
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
        body: Column(
          children: [
            Flexible(
              child: ListView.builder(
                itemBuilder: (_, index) => _messages[index],
                itemCount: _messages.length,
              ),
            ),
            Divider(height: 1.0,),
            Container(
              height: 55,
              color: Theme.of(context).primaryColor,
              padding: EdgeInsets.symmetric(vertical: 5.0),
              // margin: EdgeInsets.symmetric(vertical: 8.0),
              child: Row(
                children: [
                  SizedBox(width: 30),
                  Flexible(
                    child: TextField(
                      controller: _textController,
                      onSubmitted: _handleSubmitted,
                      maxLines: null,
                      style: TextStyle(
                        color: Color.fromARGB(255, 0, 55, 122)
                      ),
                      cursorColor: Color.fromARGB(255, 0, 55, 122),
                      cursorHeight: 25,
                      cursorWidth: 1,
                      decoration: InputDecoration(
                        contentPadding: EdgeInsets.symmetric(horizontal: 20.0),
                        border: InputBorder.none,
                        filled: true,
                        fillColor: Color.fromARGB(255, 137, 218, 230),
                        enabledBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.all(Radius.circular(30)),
                          borderSide: BorderSide(
                            color: Color.fromARGB(255, 137, 218, 230),
                            width: 1
                          )
                        ),
                        focusedBorder: OutlineInputBorder(
                            borderRadius: BorderRadius.all(Radius.circular(30)),
                            borderSide: BorderSide(
                                color: Color.fromARGB(255, 137, 218, 230),
                                width: 1
                            )
                        )
                      ),
                    ),
                  ),
                  IconButton(
                      onPressed: () => _handleSubmitted(_textController.text),
                      icon: Icon(Icons.send_rounded),
                    color: Color.fromARGB(255, 255, 122, 105),
                  ),
                ],
              ),
            )
          ],
        )
    );
  }
}

class NewMessage extends StatelessWidget {
  final String text;
  NewMessage({required this.text});

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.symmetric(vertical: 10.0),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          SizedBox(width: 20),
          CircleAvatar(
            backgroundImage: AssetImage("assets/images/dr1.jpg"),
          ),
          SizedBox(width: 15,),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  "suzuki nya",
                  style: TextStyle(
                    color: Color.fromARGB(255, 151, 142, 143)
                  ),
                ),
                SizedBox(height: 5,),
                Text(
                  text,
                  style: TextStyle(
                      color: Color.fromARGB(255, 151, 142, 143)
                  ),
                )
              ],
            ),
          ),
          SizedBox(width: 15,)
        ],
      ),
    );
  }
}