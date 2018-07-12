package com.cj.stshentu.crawler;


import com.cj.stshentu.entity.Standard;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.cj.stcommon.utils.timeUtil.TimeToString.StrToDate2;


public class WebPageSource {

    //用于存储爬取到的数据
    List<Standard> standardList = new ArrayList<>();



    public static void main(String args[]){

        WebPageSource webPageSource = new WebPageSource();
        webPageSource.test("http://www.csres.com/sort/industry/002009_1.html");


    }

    public List<Standard> test(String url){
        String newUrl = crawler(url);
        if(newUrl.length()>10){
            url = newUrl;
            test(url);
        }else {
            System.out.println("============数据解析完成=======================");
        }
        return standardList;
    }


    /**
     * 爬取网页
     * 返回符合规则的新url
     * @param urlStr
     * @return
     */

    public String crawler(String urlStr){

        String str = "";  //用于接收爬取到的网页
        String newUrl = "";  //用于接收返回的符合规则的新url
        URL url;
        int responsecode;
        HttpURLConnection urlConnection;
        BufferedReader reader;
        String line;
        try{
            //生成一个URL对象，要获取源代码的网页地址为：http://www.sina.com.cn
            url=new URL(urlStr);

            //打开URL
            urlConnection = (HttpURLConnection)url.openConnection();

            //获取服务器响应代码
            responsecode=urlConnection.getResponseCode();

            if(responsecode==200){

                //得到输入流，即获得了网页的内容
                reader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"GBK"));

                while((line=reader.readLine())!=null){
                    str += line;
                }
                 newUrl = analysis(str);
            }else{
                System.out.println("获取不到网页的源码，服务器响应代码为："+responsecode);
            }
        }catch(Exception e){
            System.out.println("获取不到网页的源码,出现异常："+e);
        }

        return newUrl;

    }


    /**
     * ================================================================================================================
     * 解析html
     * 返回新url
     */

    public String analysis(String str){
        List<String> titles = new ArrayList<String>();
        List<String> urls = new ArrayList<String>();

        //假设我们获取的HTML的字符内容如下
        String html = str;

        //第一步，将字符内容解析成一个Document类
        Document doc = Jsoup.parse(html);

        //第二步，根据我们需要得到的标签，选择提取相应标签的内容
        Elements elements = doc.select("tr:not(:nth-child(1))>td").select("tr:not(:nth-child(1))>td");
        Elements elements2 = doc.select("table>tbody>tr>td>table>tbody>tr>td>a");

        for( Element element : elements ){
            String title = element.text();
            titles.add(title);
        }
        for( Element element : elements2 ){
            if(element.text().indexOf("下一页") != -1){
                System.out.println(element);

                urls.add(element.select("a").attr("href"));
            }
        }

        //输出测试
        System.out.println("================================输出测试========================================");
        titles = titles.subList(0,titles.size()-15);
        System.out.println(titles);

        Standard standard = new Standard();
        for (int i = 0;i<titles.size();i++) {
            switch ((i + 1) % 5) {
                case 1:
                    standard.setStandardNum(titles.get(i));
                    break;
                case 2:
                    standard.setStandardName(titles.get(i));
                    break;
                case 3:
                    standard.setStandardDepartment(titles.get(i));
                    break;
                case 4:
                    if (titles.get(i).trim().length() > 0) {
                        standard.setActualizeTime(StrToDate2(titles.get(i).trim()));
                    }
                    break;
                case 0:
                    if("作废".equals(titles.get(i))){
                        standard.setState("01");
                    }else if("废止".equals(titles.get(i))){
                        standard.setState("02");
                    }else if("现行".equals(titles.get(i))){
                        standard.setState("11");
                    }else {
                        standard.setState("00");
                    }
                    System.out.println("===================================");
                    System.out.println(standard);
                    standardList.add(standard);
                    standard = new Standard();
                    break;
            }
        }

        for( String url : urls ){
            System.out.println(url);
        }

        System.out.println("================================输出测试========================================");
        if (urls.size()>0){
            return "http://www.csres.com"+urls.get(urls.size()-1);
        }else {

            return "没有了QAQ";
        }

    }


}

