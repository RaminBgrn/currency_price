import 'package:dollar_price/constants/ui_color.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: UiColor.myGrey()[700],
        centerTitle: true,
        title: Text(
          'Currency Price',
          style: GoogleFonts.vazirmatn(
            fontSize: 28,
            fontWeight: FontWeight.bold,
            color: UiColor.myGrey()[200],
          ),
        ),
      ),
    );
  }
}
