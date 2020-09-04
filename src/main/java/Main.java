import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            Document doc = Jsoup.parse(HttpConn.getString("https://pixiv.net"));
            Elements imgs = doc.getElementsByTag("img");
            for (Element img : imgs) {
                String imgSrc = img.attr("src");
                if (imgSrc.startsWith("//")) {
                        imgSrc = "http:" + imgSrc;
                    }
                byte[] data = readInputStream(HttpConn.getIS(imgSrc));
                File imageFile = new File(imgSrc.replace('/', ':'));
                FileOutputStream outStream = new FileOutputStream(imageFile);
                outStream.write(data);
                outStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}
