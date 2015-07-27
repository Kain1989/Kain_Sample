package com.telenav.cloud.search.autonavi.utils.xml;

import com.telenav.cloud.search.autonavi.entity.response.Ats;
import com.telenav.cloud.search.autonavi.entity.response.AutonaviResponse;
import com.telenav.cloud.search.autonavi.entity.response.Poi;
import com.telenav.cloud.search.autonavi.entity.response.PoiList;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zfshi on 7/14/2015.
 */
public class XmlConverter {

    public static void main(String[] args) throws Exception {

        AutonaviResponse autonaviResponse = new AutonaviResponse();

        SAXReader saxReader = new SAXReader();

//        File file = new File(XmlConverter.class.getClassLoader().getResource("Result.xml").toString());

        File file = new File("D:/Result.xml");
        Document document = saxReader.read(file);

        // 获取根元素
        Element root = document.getRootElement();

        Ats ats = convert2Ats(root);
        autonaviResponse.setAts(ats);
        System.out.println(autonaviResponse.toString());
    }

    private static Ats convert2Ats(Element xmlAts) {
        Ats ats = new Ats();
        System.out.println("Root: " + xmlAts.getName());
        generateAtsBody(ats, xmlAts);

        PoiList xmlPoiList = convert2PoiList(xmlAts.element("poi_list"));
        ats.setPoiList(xmlPoiList);

        return ats;
    }

    private static void generateAtsBody(Ats ats, Element xmlAts) {
        if (xmlAts.element("version") != null) {
            ats.setVersion(xmlAts.element("version").getData().toString());
        }
        if (xmlAts.element("result") != null) {
            ats.setResult(xmlAts.element("result").getData().toString());
        }
        if (xmlAts.element("code") != null) {
            ats.setCode(xmlAts.element("code").getData().toString());
        }
        if (xmlAts.element("message") != null) {
            ats.setMessage(xmlAts.element("message").getData().toString());
        }
        if (xmlAts.element("timestamp") != null) {
            ats.setTimestamp(xmlAts.element("timestamp").getData().toString());
        }
        if (xmlAts.element("total") != null) {
            ats.setTotal(xmlAts.element("total").getData().toString());
        }
//        System.out.println("Do not have this tag:" + xmlAts.element("donothave").getData().toString());
    }

    private static PoiList convert2PoiList(Element xmlPoiList) {
        List<Poi> poiList = null;
        if (xmlPoiList != null) {
            poiList = new ArrayList<Poi>();
            for (Element xmlPoi : xmlPoiList.elements("poi")) {
                Poi poi = new Poi();
                System.out.println("=================== POI START ===================");

                if (xmlPoi.element("adcode") != null) {
                    poi.setAdcode(xmlPoi.element("adcode").getData().toString());
                }
                if (xmlPoi.element("address") != null) {
                    poi.setAddress(xmlPoi.element("address").getData().toString());
                }
                if (xmlPoi.element("areacode") != null) {
                    poi.setAreacode(xmlPoi.element("areacode").getData().toString());
                }
                if (xmlPoi.element("average") != null) {
                    poi.setAverage(xmlPoi.element("average").getData().toString());
                }

                if (xmlPoi.element("checked") != null) {
                    poi.setChecked(xmlPoi.element("checked").getData().toString());
                }
                if (xmlPoi.element("citycode") != null) {
                    poi.setCitycode(xmlPoi.element("citycode").getData().toString());
                }
                if (xmlPoi.element("cityname") != null) {
                    poi.setCityname(xmlPoi.element("cityname").getData().toString());
                }

                if (xmlPoi.element("districtname") != null) {
                    poi.setDistrictname(xmlPoi.element("districtname").getData().toString());
                }
                if (xmlPoi.element("districtcode") != null) {
                    poi.setDistrictcode(xmlPoi.element("districtcode").getData().toString());
                }
                if (xmlPoi.element("distance") != null) {
                    poi.setDistance(xmlPoi.element("distance").getData().toString());
                }
                if (xmlPoi.element("deepinfo") != null) {
                    poi.setDeepinfo(xmlPoi.element("deepinfo").getData().toString());
                }

                if (xmlPoi.element("id") != null) {
                    poi.setId(xmlPoi.element("id").getData().toString());
                }
                if (xmlPoi.element("introduction") != null) {
                    poi.setIntroduction(xmlPoi.element("introduction").getData().toString());
                }
                if (xmlPoi.element("introduction_chinese") != null) {
                    poi.setIntroductionChinese(xmlPoi.element("introduction_chinese").getData().toString());
                }

                if (xmlPoi.element("keywords") != null) {
                    poi.setKeywords(xmlPoi.element("keywords").getData().toString());
                }

                if (xmlPoi.element("latitude") != null) {
                    poi.setLatitude(xmlPoi.element("latitude").getData().toString());
                }
                if (xmlPoi.element("latitude_entrance") != null) {
                    poi.setLatitudeEntrance(xmlPoi.element("latitude_entrance").getData().toString());
                }
                if (xmlPoi.element("latitude_exit") != null) {
                    poi.setLatitudeExit(xmlPoi.element("latitude_exit").getData().toString());
                }
                if (xmlPoi.element("localid") != null) {
                    poi.setLocalid(xmlPoi.element("localid").getData().toString());
                }
                if (xmlPoi.element("longitude") != null) {
                    poi.setLongitude(xmlPoi.element("longitude").getData().toString());
                }
                if (xmlPoi.element("longitude_entrance") != null) {
                    poi.setLongitudeEntrance(xmlPoi.element("longitude_entrance").getData().toString());
                }
                if (xmlPoi.element("longitude_exit") != null) {
                    poi.setLongitudeExit(xmlPoi.element("longitude_exit").getData().toString());
                }

                if (xmlPoi.element("name") != null) {
                    poi.setName(xmlPoi.element("name").getData().toString());
                }

                if (xmlPoi.element("opentime") != null) {
                    poi.setOpentime(xmlPoi.element("opentime").getData().toString());
                }

                if (xmlPoi.element("postcode") != null) {
                    poi.setPostcode(xmlPoi.element("postcode").getData().toString());
                }
                if (xmlPoi.element("provincecode") != null) {
                    poi.setProvincecode(xmlPoi.element("provincecode").getData().toString());
                }
                if (xmlPoi.element("provincename") != null) {
                    poi.setProvincename(xmlPoi.element("provincename").getData().toString());
                }

                if (xmlPoi.element("ranksearch") != null) {
                    poi.setRanksearch(xmlPoi.element("ranksearch").getData().toString());
                }
                if (xmlPoi.element("rating") != null) {
                    poi.setRating(xmlPoi.element("rating").getData().toString());
                }

                if (xmlPoi.element("tel") != null) {
                    poi.setTel(xmlPoi.element("tel").getData().toString());
                }
                if (xmlPoi.element("Test") != null) {
                    poi.setText(xmlPoi.element("Test").getData().toString());
                }
                if (xmlPoi.element("type") != null) {
                    poi.setType(xmlPoi.element("type").getData().toString());
                }
                if (xmlPoi.element("typecode") != null) {
                    poi.setTypecode(xmlPoi.element("typecode").getData().toString());
                }

                if (xmlPoi.element("url") != null) {
                    poi.setUrl(xmlPoi.element("url").getData().toString());
                }

                if (xmlPoi.element("weight") != null) {
                    poi.setWeight(xmlPoi.element("weight").getData().toString());
                }

                poiList.add(poi);
                System.out.println("=================== POI END ===================");
            }
        }
        PoiList pois = new PoiList();
        pois.setPois(poiList);
        return pois;
    }
}