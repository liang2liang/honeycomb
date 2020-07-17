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
    public static String getString(String url) {
        return getStringResponse(url).body();
    }

    @SneakyThrows
    public static byte[] getBytes(String url) {
        return getByteResponse(url).body();
    }

    @SneakyThrows
    public static HttpResponse<byte[]> getByteResponse(String url) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
    }

    @SneakyThrows
    public static HttpResponse<String> getStringResponse(String url) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    @SneakyThrows
    public static Path getPath(String url, String filePath) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofFile(Paths.get(filePath))).body();
    }

    public static void main(String[] args) throws IOException, InterruptedException {

//        long id = 6656378127318533000L;

        LongStream.range(665637812731873320L, 765637812731873321L).forEach(id -> {
            HttpRequest build = HttpRequest.newBuilder()
                    .uri(URI.create("http://test0.galio.ifeng.com/api/entrance/public/submit-article-task"))
                    .header("Authorization", "Bearer 1ab43420-bec8-4d38-8bf6-89c057a31f0b")
                    .header("Content-Type", "application/json")
//                    .header("requestId", String.valueOf(id))
//                .header("Cookie", "gksskpitn=c502e55a-0426-4300-97de-bdd206c2a808; SERVERID=1fa1f330efedec1559b3abbcb6e30f50|1587092137|1587092137")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"bwDetail\":\"\",\"qualityTask\":0,\"sensitiveScore\":0.0,\"businessId\":\"zmt_art\",\"mediaId\":\"1529623\",\"type\":\"article\",\"favorData\":\"[]\",\"recommendArea\":\"[]\",\"sourceUrl\":\"http://house.cnfol.com/fangchanyaowen/20200623/28231436.shtml\",\"auditorTime\":0,\"sensitiveWord\":\"\",\"subTitle\":\"\",\"coverImage\":\"\",\"userTags\":\"\",\"id\":" + id + ",\"contentType\":\"article\",\"specialPriority\":\"{\\\"isCreation\\\":3,\\\"isNewUserArticle\\\":0,\\\"highQuality\\\":1,\\\"accountDataSource\\\":1}\",\"publishTime\":1592878441000,\"machineStatus\":0,\"garbageWords\":\"\",\"title1\":\"上海大树投资3.52亿元竞得嘉兴海盐县7万平宅地\",\"auditorDetail\":\"\",\"title2\":\"\",\"accountLevel\":4,\"haveAudio\":0,\"simScore\":0.0,\"machineDetail\":\"\",\"tags\":\"\",\"bucket\":\"none_bucket\",\"attach1\":\"\",\"attach2\":\"\",\"auditorId\":0,\"longTime\":0,\"field\":\"none_field\",\"sourceIp\":\"\",\"sensitiveLevel\":0,\"auditorStatus\":0,\"guid\":\"6681000319117170819\",\"contentQuality\":0,\"dataSource\":\"20005\",\"algCatNameNew\":\"\",\"sourceAlias\":\"中金在线-房产频道\",\"finalStatus\":0,\"machineTime\":0,\"recommendOperate\":\"nothing\",\"chnCatNameNew\":\"财经\",\"content\":\"<!-- FORMATER --> \\n<p>中国网地产讯 23日，上海大树投资控股集团有限公司以总价35193.58万元竞得嘉兴海盐县20-111号地块，楼面价3253元/㎡，溢价率0.31%。 地块位于澉浦镇，澉浦镇东至星光大道、南至澉湖路、西至纵四路、北至南浦路。出让面积72118㎡，容积率1.3-1.5，建筑面积108177㎡。其中居住用地面积不大于69855㎡，计容建筑面积90812-104782㎡；商业用地面积不小于2263㎡，计容建筑面积2950-3394㎡。起始价35085万元，楼面起价3243元/㎡。</p>\",\"priorityReference\":0.0,\"longTimeExpireTime\":0,\"specialMark\":\"正常\",\"chnCatName\":\"财经\",\"auditorName\":\"\",\"algCatName\":\"\",\"sourceFrom\":\"抓站\",\"auditRegion\":\"\",\"eventTask\":0,\"sourceCreator\":\"中金在线TB\",\"summary\":\"\",\"tupuData\":\"{\\\"classV1\\\":\\\"房产\\\",\\\"classV2\\\":\\\"房企经营动态\\\",\\\"id\\\":\\\"110\\\"}\\n\",\"qualityCheck\":0,\"original\":0,\"reserve5\":\"\",\"reserve4\":\"\",\"contentQualityTag\":\"\",\"reserve1\":3,\"questionMark\":\"\",\"textLength\":0,\"reserve3\":\"\",\"reserve2\":2,\"recommendPeople\":\"[]\",\"isMultiCoverTitle\":0,\"createTime\":1592879149944,\"haveVideo\":0,\"bwList\":\"\",\"callback\":\"http://local.fhhapi-service.ifengidc.com/audit/distribute/article/callback\",\"dataProvider\":\"6\",\"sourceName\":\"中金在线\",\"twoCheckFlag\":0,\"tagsNew\":\"\"}"))
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
