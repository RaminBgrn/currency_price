class ImportantCurrenciesModel {
  String? symbol;
  String? name;
  String? date;
  double? price;
  double? changePercent;

  ImportantCurrenciesModel({
    this.symbol,
    this.name,
    this.date,
    this.price,
    this.changePercent,
  });

  ImportantCurrenciesModel.fromJson(
    Map<String, dynamic> json,
  ) {
    symbol = json['symbol'];
    name = json['name'];
    date = json['date'];
    price = json['price'];
    changePercent = json['change_percent'];
  }
}
