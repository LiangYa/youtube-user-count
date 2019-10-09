package com.jialin.demo;

import casia.isiteam.datagroup.search.searcher.ISISearcher;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) {
//        String keyword_must,

//        String matachWords = "习近平 香港 抗议;李克强 香港 抗议;栗战书 香港 抗议;汪洋 香港 抗议;王沪宁 香港 抗议;赵乐际 香港 抗议;韩正 香港 抗议;丁薛祥 香港 抗议;王晨 香港 抗议;刘鹤 香港 抗议;香港特区行政长官;林郑月娥;林鄭月娥;Carrie Lam;香港 政务司司长;香港 保安局局长;香港 律政司司长;香港 張建宗;中国中央人民政府驻香港特别行政区联络办公室;中联办;主任 王志民;香港 中聯辦;Liaison Office of the Central People Government in the Hong Kong Special Administrative Region;LOCPG HK protest;国务院港澳事务办公室;港澳办;港澳辦;发言人杨光;发言人徐露颖;發言人陽光;發言人徐露穎;Hong Kong and Macau Affairs Office;HKMAO Hongkong;香港人权与民主法案;特朗普 香港 抗議;美国 香港 抗議;US hongkong protest;United States hongkong protest;Trump hongkong protest;香港 抗议;香港 反送中;黄之锋;香港 抗議 台灣;香港 反送中 台灣;事件 721;抗议 721;游行 721;集会 721;反修 721;诉求 721;部队 721;军队 721;白衣人 元朗;袭击 元朗;恐袭 元朗;抗议 香港;游行 香港;示威 香港;集会 香港;静坐 香港;动乱 香港;警察 香港;反送中 香港;反修例 香港;抗議 香港;遊行 香港;集會 香港;hongkong protest-extradition march|hongkong antiELAB|hongkong police riot|hongkong demonstratio;hongkong protesters-extradition march|hongkong antiELAB|hongkong police riot|hongkong demonstratio;hongkong extradition bill-extradition march|hongkong antiELAB|hongkong police riot|hongkong demonstratio;hongkong anti-extradition march-extradition march|hongkong antiELAB|hongkong police riot|hongkong demonstratio;hongkong antiELAB-extradition march|hongkong antiELAB|hongkong police riot|hongkong demonstratio;hongkong police riot-extradition march|hongkong antiELAB|hongkong police riot|hongkong demonstratio;hongkong demonstration-extradition march|hongkong antiELAB|hongkong police riot|hongkong demonstratio;游行 香港 七一;抗议 香港 七一;游行 香港 71;抗议 香港 71;遊行 香港 71;示威 香港 71;抗議 香港 71;民主基金会;";
        String matachWords = "鐳射槍;燃燒彈;雷射筆;警棍;催淚彈;催淚劑;海綿彈;胡椒彈;胡椒噴霧;橡膠子彈;裝甲車;水泡車;顏色水;布袋彈;磚頭;鐵籠車;粉末;頭盔;雨傘;黑衣;煙餅;漆彈;墨水彈;天拿水彈;彈叉;雷射;彈珠;弓箭;縱火;汽油彈;榴彈發射器;鋼珠;匕首;錘子;竹枝;油漆;搶槍;斧頭;電動棒球發射器;通渠水;跳閘;焚燒;氣槍;彈弓;強光燈;實彈;化學氣體;公園;機場;遊樂場;領事館;警署;廣場;暴動;遊行;衝突;召集;示威;運動;革命;行動;悼念;罷課;佔領;上訪;靜坐;抗議;追思;三罷;抗爭;絕食;罷工;罷市;屠殺;清場;鎮壓;暴亂;維權;禱告;講座;揭幕;上街;扑街;集結;訴求;襲警;紀念;週年;包圍;衝擊;驅散;對峙;圍堵;平反;圍攻;譴責;請願;集會;燭光晚會;光復;呼籲;破壞地鐵;堵路;祈禱會;燒國旗;推人上街;雙真普選;送中條例;人鏈;光復上水;萬人接機;831黑警恐襲;元朗恐怖襲擊;黑警還眼;佔領立法會;包圍立法會;燒中國旗;抵制十一;歡慶雙十;不合作運動;變死城;廢除《聯合聲明》;廢除JD;全城大三罷;平反新屋嶺;放飛黑氣球;揮萬國旗;戴V煞面具;五大訴求;偉哉香港;反中共;";
        info_event_info_list1("",matachWords);
    }
    public static  void info_event_info_list1(String keyword_must,  String matachWords) {


        String coreserver = "192.168.6.145:9090";
        List<String> slaves = Arrays.asList("192.168.6.145:9091", "192.168.6.145:9092");
//        String[] infoSource = new String[] { "news", "blog",
//                "forum_threads", "mblog_info", "video_brief",
//                "wechat_message_xigua", "appdata" };
        String[] infoSource = new String[]{"forum_threads"};
        ISISearcher.debug = true;
        ISISearcher sc = new ISISearcher(coreserver, slaves, infoSource);
        String stime = null;
        String etime = null;
        try {
            Date now = new Date(new Date().getTime() + 10 * 60 * 1000);
            String dataNow = Date2Str(now, "yyyyMMddHHmmss");
            stime = "20190612000000";
            etime = dataNow;
        } catch (Exception e) {
            e.printStackTrace();
        }


        //2.关键词查询
        StringBuffer allBuffer = new StringBuffer();
        Set<String> uncontain_keywords_set = new HashSet<String>(); // 不包含关键词
        Set<String> contain_keywords_set = new HashSet<String>(); // 包含关键词
        Set<String> input_keywords_set = new HashSet<String>(); // 输入的不包含关键词
        Set<String> input_not_keywords_set = new HashSet<String>(); // 输入的包含关键词
        if (StringUtils.isNotEmpty(keyword_must)) { // 添加页面输入的包含关键词
            input_keywords_set.add(keyword_must);
        }

        if (StringUtils.isNotEmpty(matachWords)) {
            System.out.println("有內容");
            String[] kwys = matachWords.split(";");
            if (null != kwys && kwys.length > 0) {
                for (String kwy : kwys) {
                    input_keywords_set.add(kwy.trim());
                }
            }
        }

        KeywordsUtil.assemble_containts_keywords_and_uncontaints_keywords_query_string(input_keywords_set, input_not_keywords_set, allBuffer, null, " ) OR ( ");

        if (allBuffer.length() > 0) {
            // 获取查询对象
            SolrQuery query = sc.getQuery();
            query.setQuery(allBuffer.toString());
        }

        sc.addPrimitiveTermQuery("domain", "hkgalden.org", casia.isiteam.datagroup.search.commons.FieldOccurs.MUST);
//        sc.addRangeTerms("pubtime", stime, etime, casia.isiteam.datagroup.search.commons.FieldOccurs.MUST); // 时间限制

//			List<String[]> results = sc.facetCountQueryOrderByCountInEnum("author_string", 20);
//            Map<String, Long> author = sc.facetCountQueryOrderByAlphabetMap("author", 20);
        List<String[]> results = sc.facetCountQueryOrderByCount("author", 501);
        if (results == null || results.size() == 0) {
            System.out.println("-------------not found youtube user -------------------------------");
        }
        System.out.println("开始：");
        int count = 0;
        for (String[] result : results) {
            if (result[0] != null && !"".equals(result[0].trim())){
                System.out.println((++count) +"\t"+result[0]+ "\t" + result[1]);
            }
        }
    }

    public static String Date2Str(Date date, String formate) throws Exception{
        String ret = null;
        if(date != null){
            SimpleDateFormat sdf = new SimpleDateFormat(formate);
            ret = sdf.format(date);
        }
        return ret;
    }

}
