package hagai.edu.xmlandfirebase.dummy;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hagai.edu.xmlandfirebase.IO;

/**
 * Created by Hagai Zamir on 02-Jun-17.
 */

public class YnetDataSource {
    public  interface  OnYnetArrivedListener {
        void  OnYnetArrived (List<Ynet> data , Exception e);
    }
    public  static  void  getYnet (OnYnetArrivedListener listener){
        //service = Executor.newSingleThread
        ExecutorService service = Executors.newSingleThreadExecutor();
        //service->run
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://www.ynet.co.il/Integration/StoryRss1854.xml");
                    URLConnection con = url.openConnection();
                    InputStream in = con.getInputStream();
                    String xml = IO.read(in , "Windows-1255");
                    List<Ynet> data = parse (xml);


                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });
        //URL url = new URL(...)
        //con = url.openConnection
        //InputStream i n = con.get
        //List
    }

    private static List<Ynet> parse(String xml) {
        List<Ynet> data = new ArrayList<>();
        Document document = Jsoup.parse(xml);

        Elements items = document.getElementsByTag("item");
        for (Element item : items){
            String title = item.getElementsByTag("item").get(0).text().replace("<![CDATA[ ","").replace("]]>","");
            Document descriptionElement = Jsoup.parse(xml);
            String href = descriptionElement.getElementsByTag("description").get(0).attr("href");
            String src = descriptionElement.getElementsByTag("link").get(0).attr("src");
            Log.d("Ness" , "Title"  + title);

        }

    }

    public  static  class  Ynet{
        private  String title;
        private  String link;
        private  String description;
        private  String image;

        //constructor
        public Ynet(String title, String link, String description, String image) {
            this.title = title;
            this.link = link;
            this.description = description;
            this.image = image;
        }
        //getters
        public String getTitle() {
            return title;
        }

        public String getLink() {
            return link;
        }

        public String getDescription() {
            return description;
        }

        public String getImage() {
            return image;
        }

        //toString

        @Override
        public String toString() {
            return "Ynet{" +
                    "title='" + title + '\'' +
                    ", link='" + link + '\'' +
                    ", description='" + description + '\'' +
                    ", image='" + image + '\'' +
                    '}';
        }
    }
}
