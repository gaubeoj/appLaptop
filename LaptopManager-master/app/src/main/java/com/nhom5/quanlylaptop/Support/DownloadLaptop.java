package com.nhom5.quanlylaptop.Support;

import android.content.Context;
import android.util.Log;

import com.nhom5.quanlylaptop.DAO.LaptopDAO;
import com.nhom5.quanlylaptop.Entity.Laptop;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DownloadLaptop {
    List<Laptop> listLap = new ArrayList<>();
    Laptop laptop;
    String TAG = "LaptopLoader_____";
    String textContent;
    LaptopDAO laptopDAO;
    int listSize;

    public void getListLaptop(InputStream inputStream, Context context) {
        laptopDAO = new LaptopDAO(context);
        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                ArrayList<Laptop> list = laptopDAO.selectLaptop(null, null, null, null);
                if (list != null){
                    listSize = list.size();
                } else {
                    listSize = 0;
                }

                Log.d(TAG, "getListNews: eventType != END_DOC - ET= " + eventType);

                String tagName = parser.getName();
                Log.d(TAG, "Tag name =  " + tagName + ", Độ sâu của thẻ = "
                        + parser.getDepth());

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        Log.d(TAG, "getListNews: case START_TAG");

                        if (tagName.equalsIgnoreCase("item")) {
                            Log.d(TAG, "getListNews: Start Tag <Item>");
                            laptop = new Laptop();
                            laptop.setMaLaptop("LAP" + listSize);
                        }
                        break;

                    case XmlPullParser.TEXT:
                        Log.d(TAG, "getListNews: case TEXT");
                        textContent = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        Log.d(TAG, "getListNews: case END_TAG");
                        if(laptop != null){
                            Log.d(TAG, "getListNews: news != null");
                            if(tagName.equalsIgnoreCase("item")){
                                Log.d(TAG, "getListNews: End Tag <Item>");
                                laptopDAO.insertLaptop(laptop);
                            }

                            if (tagName.equalsIgnoreCase("title")){
                                Log.d(TAG, "getListNews: End Tag <Title>");
                                laptop.setTenLaptop( textContent );
                            }

                            if (tagName.equalsIgnoreCase("link")){
                                Log.d(TAG, "getListNews: End Tag <Link>");
                                laptop.setMaLaptop( textContent );
                            }
                        }
                        break;
                    default:
                        Log.d(TAG, "getListNews: default ET: " + eventType + ", Tag name = " + tagName);
                        break;
                }
                eventType = parser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
