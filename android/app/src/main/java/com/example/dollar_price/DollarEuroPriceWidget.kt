package com.example.dollar_price

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.RemoteViews
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import es.antonborri.home_widget.HomeWidgetPlugin


data class CurrencyInfo(
    val symbol: String = "null",
    val price: String = "null",
    val date: String = "null",
    val changePercent: Double = 0.0
)

/**
 * Implementation of App Widget functionality.
 */
class DollarEuroPriceWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName , R.layout.dollar_euro_price_widget)
            fetchAndShowDollarAndEuroData(context , views)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }


    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

   private fun fetchAndShowDollarAndEuroData(context: Context , views: RemoteViews){
        val responseData = HomeWidgetPlugin.getData(context)
        try {
            val rawData = responseData.getString("DollarEuro" , "null")
            println("RawData Is : $rawData ")

            val gson = Gson()
            val mapType = object : TypeToken<List<CurrencyInfo>>() {}.type
            println("object type is : $mapType")
            println(mapType::class)
            val currencyList: List<CurrencyInfo> = gson.fromJson(rawData, mapType)
            println(currencyList[0].toString())
            val usdDate =
                currencyList.find { it.symbol == "USD" } ?: CurrencyInfo(symbol = "USD")
            val uroDate =
                currencyList.find { it.symbol == "EUR" } ?: CurrencyInfo(symbol = "EUR")
            setDollarData(usdDate , views)
            setEuroData(uroDate, views)
        } catch (e: Exception){
            Log.e("Exception" , "On converting data" , e)
        }
    }

    private fun setDollarData(data: CurrencyInfo, layout: RemoteViews){
        if(data.changePercent > 0){
            layout.setTextColor(R.id.txt_usd_percent , Color.parseColor("#f44336"))
        }else{
            layout.setTextColor(R.id.txt_usd_percent , Color.parseColor("#4eae47"))
        }
        layout.setTextViewText(R.id.txt_dollar, "USD")
        layout.setTextViewText(R.id.txt_usd_date, data.date)
        layout.setTextViewText(R.id.txt_usd_price, data.price)
        layout.setTextViewText(R.id.txt_usd_percent, data.changePercent.toString())
    }

    private fun setEuroData(data : CurrencyInfo , layout: RemoteViews){
        if(data.changePercent > 0){
            layout.setTextColor(R.id.txt_euro_percent , Color.parseColor("#f44336"))
        }else{
            layout.setTextColor(R.id.txt_euro_percent , Color.parseColor("#4eae47"))
        }
        layout.setTextViewText(R.id.txt_euro, "EUR")
        layout.setTextViewText(R.id.txt_euro_price, data.price)
        layout.setTextViewText(R.id.txt_euro_percent, data.changePercent.toString())
        layout.setTextViewText(R.id.txt_date_euro, data.date)
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.dollar_euro_price_widget)


    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}