package com.honeycomb.util;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.LongStream;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class HttpUtil {

    private static final HttpClient client = HttpClient.newHttpClient();

    @SneakyThrows
    public static String getString(String url){
        return getStringResponse(url).body();
    }

    @SneakyThrows
    public static byte[] getBytes(String url){
        return getByteResponse(url).body();
    }

    @SneakyThrows
    public static HttpResponse<byte[]> getByteResponse(String url){
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
    }

    @SneakyThrows
    public static HttpResponse<String> getStringResponse(String url){
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    @SneakyThrows
    public static Path getPath(String url, String filePath){
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofFile(Paths.get(filePath))).body();
    }

    public static void main(String[] args) throws IOException, InterruptedException {

//        long id = 6656378127318533000L;

        LongStream.range(6656378127318533000L, 6656378127318733000L).parallel().forEach(id -> {
            HttpRequest build = HttpRequest.newBuilder()
                    .uri(URI.create("http://test0.galio.ifeng.com/api/entrance/public/submit-article-task"))
                    .header("Authorization", "Bearer 5fc071fd-4a8c-4a30-83ab-da3fb30c3b6a")
                    .header("Content-Type", "application/json")
//                .header("Origin", "https://account.geekbang.org")
//                .header("Cookie", "gksskpitn=c502e55a-0426-4300-97de-bdd206c2a808; SERVERID=1fa1f330efedec1559b3abbcb6e30f50|1587092137|1587092137")
                    .POST(HttpRequest.BodyPublishers.ofString("{\n" +
                            "\t\"bwDetail\": \"\",\n" +
                            "\t\"qualityTask\": 0,\n" +
                            "\t\"sensitiveScore\": 0.0,\n" +
                            "\t\"businessId\": \"zmt_art\",\n" +
                            "\t\"mediaId\": \"1537782\",\n" +
                            "\t\"type\": \"article\",\n" +
                            "\t\"recommendArea\": \"[]\",\n" +
                            "\t\"sourceUrl\": \"http://toutiao.com/group/6816104976431972864/\",\n" +
                            "\t\"auditorTime\": 0,\n" +
                            "\t\"sensitiveWord\": \"\",\n" +
                            "\t\"subTitle\": \"\",\n" +
                            "\t\"coverImage\": \"https://x0.ifengimg.com/res/2020/259FF487D2930980008D22FFA8F3D18E996F2EEB_size47_w640_h360.jpeg\",\n" +
                            "\t\"userTags\": \"\",\n" +
                            "\t\"id\": " + id + ",\n" +
                            "\t\"contentType\": \"article\",\n" +
                            "\t\"specialPriority\": \"{\\\"isCreation\\\":3,\\\"isNewUserArticle\\\":0,\\\"highQuality\\\":1,\\\"accountDataSource\\\":1}\",\n" +
                            "\t\"publishTime\": 1587003360000,\n" +
                            "\t\"machineStatus\": 0,\n" +
                            "\t\"title1\": \"昔日恒大队魂为于汉超发声：做错事就要受罚，但希望大家多点包容\",\n" +
                            "\t\"auditorDetail\": \"\",\n" +
                            "\t\"title2\": \"\",\n" +
                            "\t\"accountLevel\": 3,\n" +
                            "\t\"haveAudio\": 0,\n" +
                            "\t\"simScore\": 0.0,\n" +
                            "\t\"machineDetail\": \"\",\n" +
                            "\t\"tags\": \"\",\n" +
                            "\t\"bucket\": \"none_bucket\",\n" +
                            "\t\"attach1\": \"\",\n" +
                            "\t\"attach2\": \"\",\n" +
                            "\t\"auditorId\": 0,\n" +
                            "\t\"longTime\": 0,\n" +
                            "\t\"field\": \"none_field\",\n" +
                            "\t\"sourceIp\": \"\",\n" +
                            "\t\"sensitiveLevel\": 0,\n" +
                            "\t\"auditorStatus\": 0,\n" +
                            "\t\"contentQuality\": 0,\n" +
                            "\t\"dataSource\": \"20009\",\n" +
                            "\t\"algCatNameNew\": \"\",\n" +
                            "\t\"sourceAlias\": \"体育道德经\",\n" +
                            "\t\"finalStatus\": 0,\n" +
                            "\t\"machineTime\": 0,\n" +
                            "\t\"recommendOperate\": \"nothing\",\n" +
                            "\t\"chnCatNameNew\": \"体育\",\n" +
                            "\t\"priorityReference\": 0.0,\n" +
                            "\t\"longTimeExpireTime\": 0,\n" +
                            "\t\"specialMark\": \"正常\",\n" +
                            "\t\"chnCatName\": \"体育\",\n" +
                            "\t\"auditorName\": \"\",\n" +
                            "\t\"algCatName\": \"\",\n" +
                            "\t\"sourceFrom\": \"抓站\",\n" +
                            "\t\"auditRegion\": \"\",\n" +
                            "\t\"eventTask\": 0,\n" +
                            "\t\"sourceCreator\": \"龙之队球迷\",\n" +
                            "\t\"content\":\"<!-- FORMATER --> <p><img src=\\\"https://x0.ifengimg.com/res/2020/121A3CD34AF60279D035C13DF8194D50F5CCF568_size49_w640_h705.jpeg\\\"></p> <p>北京时间4月14日，广州恒大淘宝俱乐部下发通知，由于队员于汉超私自修改汽车号码牌，严重违反了队纪队规，对于汉超予以开除处罚。这是恒大队历史上首次开除队员，而于汉超也将因此丢掉他在恒大还有两年，价值1200万的年薪。</p> <p><img src=\\\"https://x0.ifengimg.com/res/2020/04F4D3F214F2089D9FFFBEDDFF91714EEAB36747_size20_w414_h261.jpeg\\\"></p> <p>事件发生之后，一时间也成为疫情影响下中国足球最大的新闻，对于于汉超而言，个人年薪早已超过千万，更是曾连续多年入选中国男足国家队，可以说是国内球员最顶级的存在。鉴于于汉超过去的成绩，以及恒大队的处罚方式，这起事件在网络上也引发了舆论激烈的讨论，很多网友都选择支持恒大队的处罚决定，毕竟作为公众人物带头违纪违法，对于社会造成了不好的影响。</p> <p><img src=\\\"https://x0.ifengimg.com/res/2020/5E90035FCEDC8FF89405C132FD938E367D8C8607_size26_w600_h408.jpeg\\\"></p> <p>有一些人却认为恒大队的处罚方式太过严厉，更有人炮轰恒大队的《三九队规》属于违反劳动法。昨天下午，原广州恒大队长郜林也就于汉超事件接受了记者的采访，对于于汉超私自修改汽车号码牌的行为，郜林表示既然做错了事，就必须要接受惩罚，随后郜林也为老队友开脱，希望球迷和网友能够给于汉超多一点包容。</p> <p><img src=\\\"https://x0.ifengimg.com/res/2020/604C44253C776D511A4557B7256EEC512E8FCC12_size14_w396_h275.jpeg\\\"></p> <p>作为从中甲时期就追随恒大的老将，郜林是恒大冲超、两夺亚冠以及连续多年赢得联赛冠军的重要成员，堪称是恒大队魂级别的存在，但是在休赛期，34岁的郜林也成为了恒大队的清洗对象，经过一番商讨之后，郜林最终也选择离队加盟了中甲深圳佳兆业队。此番面对于汉超的处境，郜林也是感同身受，毕竟无论是以哪种方式离队，最终都被迫离开恒大这个大家庭，就在郜林接受记者的采访时，眼神里也满是无奈与辛酸，英雄迟暮也是让人唏嘘不已。</p> <p><img src=\\\"https://x0.ifengimg.com/res/2020/1F238D497F8967A0E188E85E90952FD173B2AB65_size16_w414_h279.jpeg\\\"></p> <p>对于于汉超事件，外界目前仍旧处在揣测之中，毕竟以于汉超的身价，即便是汽车违章罚款，也不会有太大问题，但是他却贸然在广州天河区这样的繁华地段，做出修改汽车号码牌这样的公然违法行为，颇有些自导自演的味道，被恒大开除之后，于汉超目前也就恢复了自由身份，而按照中国足协此前的规定，在新赛季联赛开始之前，还会再开放三个星期的转会时间，届时于汉超也将有机会再寻找一支球队选择加盟，有关于于汉超事件的后续问题，我们也将持续跟进报道。</p>\",\n" +
                            "\t\"tupuData\": \"{\\\"sports_race\\\":[],\\\"classV1\\\":\\\"体育\\\",\\\"classV2\\\":[\\\"体育处罚\\\"],\\\"sports_clubWeight\\\":[1.0],\\\"sports_person\\\":[\\\"于汉超\\\",\\\"郜林\\\"],\\\"sports_raceWeight\\\":[],\\\"id\\\":\\\"110\\\",\\\"sports_itemWeight\\\":[],\\\"sports_personWeight\\\":[0.68,0.32],\\\"sports_club\\\":[\\\"广州恒大淘宝\\\"],\\\"sports_item\\\":[]}\",\n" +
                            "\t\"qualityCheck\": 0,\n" +
                            "\t\"original\": 0,\n" +
                            "\t\"reserve5\": \"\",\n" +
                            "\t\"reserve4\": \"\",\n" +
                            "\t\"contentQualityTag\": \"\",\n" +
                            "\t\"reserve1\": 3,\n" +
                            "\t\"textLength\": 0,\n" +
                            "\t\"reserve3\": \"\",\n" +
                            "\t\"reserve2\": 2,\n" +
                            "\t\"recommendPeople\": \"[]\",\n" +
                            "\t\"isMultiCoverTitle\": 0,\n" +
                            "\t\"createTime\": 1587005248641,\n" +
                            "\t\"haveVideo\": 0,\n" +
                            "\t\"bwList\": \"\",\n" +
                            "\t\"callback\": \"http://local.fhhapi-service.ifengidc.com/audit/distribute/article/callback\",\n" +
                            "\t\"dataProvider\": \"6\",\n" +
                            "\t\"sourceName\": \"体育道德经\",\n" +
                            "\t\"twoCheckFlag\": 0,\n" +
                            "\t\"tagsNew\": \"\"\n" +
                            "}"))
                    .build();

            HttpResponse<String> send = null;
            try {
                send = client.send(build, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(send.body() + " |||||  id is : " + id);
        });
    }
}
