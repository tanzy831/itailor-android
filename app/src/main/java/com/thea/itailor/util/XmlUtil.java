package com.thea.itailor.util;

import android.content.Context;
import android.util.Xml;

import com.thea.itailor.R;
import com.thea.itailor.armoire.bean.Cloth;
import com.thea.itailor.armoire.bean.Lattice;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thea on 2015/8/27 0027.
 */
public class XmlUtil {
    private static final String TAG = "XmlUtil";

    /*public static List<Suit> parseRecommends(Context context) throws XmlPullParserException, IOException {
        List<Suit> recommends = null;
        Suit suit = null;

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(context.getResources().openRawResource(R.raw.recommends), "UTF-8");

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    recommends = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("suit")) {
                        suit = new Suit();
                    }
                    else if (parser.getName().equals("description")) {
                        eventType = parser.next();
                        suit.setDescription(parser.getText());
                    }
                    else if (parser.getName().equals("top")) {
                        eventType = parser.next();
                        suit.setImage(parser.getText());
                    }
                    else if (parser.getName().equals("bottom")) {
                        eventType = parser.next();
                        suit.setImage2(parser.getText());
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("suit")) {
                        recommends.add(suit);
                    }
                    break;
            }
            eventType = parser.next();
        }

        Log.i(TAG, recommends.toString() + "df");
        return recommends;
    }*/

    public static List<Lattice> parseArmoire(Context context) throws XmlPullParserException, IOException {
        List<Lattice> lattices = null;
        Lattice lattice = null;

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(context.getResources().openRawResource(R.raw.armoire), "UTF-8");

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    lattices = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("lattice")) {
                        lattice = new Lattice();
                        lattice.setName(parser.getAttributeValue(0));
                    }
                    else if (parser.getName().equals("cloth")) {
                        Cloth cloth = new Cloth(parser.getAttributeValue(0), parser.getAttributeValue(1));
                        if (lattice != null) {
                            lattice.addCloth(cloth);
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("lattice")) {
                        assert lattices != null;
                        lattices.add(lattice);
                    }
                    break;
            }
            eventType = parser.next();
        }

        return lattices;
    }
}
