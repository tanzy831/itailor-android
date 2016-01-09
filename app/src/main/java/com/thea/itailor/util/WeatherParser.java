package com.thea.itailor.util;

import android.util.Log;
import android.util.Xml;

import com.thea.itailor.recommend.bean.DetailWeather;
import com.thea.itailor.recommend.bean.Exponent;
import com.thea.itailor.recommend.bean.Weather;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Thea on 2015/8/27 0027.
 */
public class WeatherParser {

    public static Weather parse(InputStream is) throws XmlPullParserException, IOException {
        Weather weather = null;

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "UTF-8");

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    weather = new Weather();
                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("city")) {
                        eventType = parser.next();
                        weather.setCity(parser.getText());
                    } else if (parser.getName().equals("high")) {
                        eventType = parser.next();
                        String temp = parser.getText().split(" ")[1];
                        weather.setHighTemperature(Integer.valueOf(temp.substring(0, temp.length() - 1)));
                    } else if (parser.getName().equals("low")) {
                        eventType = parser.next();
                        String temp = parser.getText().split(" ")[1];
                        weather.setLowTemperature(Integer.valueOf(temp.substring(0, temp.length() - 1)));
                        return weather;
                    }
                    break;
            }
            eventType = parser.next();
        }

        return weather;
     }

    public static DetailWeather parseDetail(InputStream is) throws XmlPullParserException, IOException {
        DetailWeather weather = null;
        Exponent exponent = null;
        boolean forecast = false;

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "UTF-8");

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    weather = new DetailWeather();
                    break;
                case XmlPullParser.START_TAG:
                    assert weather != null;
                    if (parser.getName().equals("city")) {
                        eventType = parser.next();
                        weather.setCity(parser.getText());
                    } else if (parser.getName().equals("wendu")) {
                        eventType = parser.next();
                        if (!forecast)
                            weather.setTemperature(Integer.valueOf(parser.getText()));
                    } else if (parser.getName().equals("fengli")) {
                        eventType = parser.next();
                        if (!forecast)
                            weather.setWind(parser.getText());
                    } else if (parser.getName().equals("shidu")) {
                        eventType = parser.next();
                        if (!forecast)
                            weather.setHumidity(parser.getText());
                    } else if (parser.getName().equals("zhishu")) {
                        exponent = new Exponent();
                    } else if (parser.getName().equals("name")) {
                        eventType = parser.next();
                        assert exponent != null;
                        exponent.setName(parser.getText());
                    } else if (parser.getName().equals("value")) {
                        eventType = parser.next();
                        assert exponent != null;
                        exponent.setValue(parser.getText());
                    } else if (parser.getName().equals("detail")) {
                        eventType = parser.next();
                        assert exponent != null;
                        exponent.setDetail(parser.getText());
                    } else if (parser.getName().equals("forecast")) {
                        forecast = true;
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("zhishu")) {
                        assert weather != null;
                        weather.addExponent(exponent);
                    }
                    break;
            }
            eventType = parser.next();
        }

        return weather;
    }
}
