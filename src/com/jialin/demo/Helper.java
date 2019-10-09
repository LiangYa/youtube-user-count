//            package com.jialin.demo;
//
//
//            import java.io.File;
//            import java.sql.Timestamp;
//            import java.text.DateFormat;
//            import java.text.NumberFormat;
//            import java.text.ParseException;
//            import java.text.SimpleDateFormat;
//            import java.util.*;
//            import java.util.regex.Matcher;
//            import java.util.regex.Pattern;
//
//            public class Helper {
//                private static final long ONE_MINUTE = 60000L;
//                private static final long ONE_HOUR = 3600000L;
//                private static final long ONE_DAY = 86400000L;
//                private static final long ONE_WEEK = 604800000L;
//                public static final String RED_MARKER_TAG_BEGIN = "<font style=color:#fe7070;>";
//                public static final String RED_MARKER_TAG_END = "</font>";
//
//                /**
//                 * 检查接口返回的结果 是否有值  false：有返回结果
//                 * @param result 调用接口返回的结果
//                 * @return
//                 */
//                public static Boolean checkReturn(String result) {
//                    Boolean falg = false;
//                    try {
//                        JSONObject parseObject = JSON.parseObject(result);
//                        if(parseObject.containsKey("returnData")) {
//                            if(!parseObject.getBoolean("returnData")) {
//                                falg = true;
//                            }
//                        }
//                    } catch (Exception e) {
//                        falg = false;
//                    }
//                    return falg;
//                }
//
//                /**
//                 * 根据间隔数和间隔类型   获取时间点 map
//                 * @param st  开始时间
//                 * @param et  结束时间
//                 * @param blanknum  间隔数
//                 * @param type 间隔类型 “hour” 小时  "day" 天
//                 * @return
//                 * @throws Exception
//                 */
//                public static Map<String,String> getTimeMapByType(Object st,Object et,Integer blanknum,String type) throws Exception{
//                    Map<String,String> map=new LinkedHashMap<String, String>();
//                    Date stime=null;
//                    Date etime=null;
//                    if(st instanceof  Date){
//                        stime=(Date) st;
//                    }
//                    if(et instanceof  Date){
//                        etime=(Date) et;
//                    }
//                    if(st instanceof  String){
//                        stime=Helper.formatDateString((String)st, "yyyyMMdd");
//                    }
//                    if(et instanceof  String){
//                        etime=Helper.formatDateString((String)et, "yyyyMMdd");
//                    }
//                    Date addtime=Helper.formatDateString((String)st, "yyyyMMdd");
//                    Calendar calendar=Calendar.getInstance();
//                    calendar.setTime(addtime);
//                    while(calendar.getTime().getTime()<etime.getTime()){
//                        if("hour".equals(type)){
//                            addtime = calendar.getTime();
//                            String stimeStr=Helper.Date2Str(addtime, "yyyy-MM-dd HH:mm:ss");
//                            map.put(stimeStr, "0");
//                            calendar.add(Calendar.HOUR_OF_DAY, blanknum);
//                        }else{
//                            addtime = calendar.getTime();
//                            String stimeStr=Helper.Date2Str(addtime, "yyyy-MM-dd HH:mm:ss");
//                            map.put(stimeStr, "0");
//                            calendar.add(Calendar.DAY_OF_YEAR, blanknum);
//                        }
//
//
//                    }
//                    return map;
//                }
//
//                /**
//                 * jsonarray中的jsonobj 调用图谱接口 转换 linkhashmap 出错
//                 * 调用前 提前转换为 linkhashmap 格式
//                 * @param jaStr
//                 * @return
//                 */
//                public static JSONArray objToMap(String jaStr) {
//                    JSONArray ja = new JSONArray();
//                    for (Object element : JSON.parseArray(jaStr)) {
//                        JSONObject obj = (JSONObject) element;
//                        LinkedHashMap<String, Object> map  = new LinkedHashMap<String, Object>();
//                        for(String str:obj.keySet()){
//                            map.put(str, obj.get(str));
//                        }
//                        ja.add(map);
//                    }
//                    return ja;
//                }
//                /**
//                 * 权限字段检查
//                 * @param authorityIds
//                 * @param userJson
//                 * @return
//                 */
//                public static UserJson checkAuthority(String authorityIds,UserJson userJson) {
//                    if(null != authorityIds && !"[]".equals(authorityIds)) {
//                        if(StringUtils.isNotEmpty(authorityIds) && !authorityIds.contains("authorityIds")) {
//                            if(JSON.parseArray(authorityIds).size() > 0){
//                                userJson.setAuthorityIds(JSON.parseArray(authorityIds));
//                            }
//                        } else if(authorityIds.contains("authorityIds")) {
//                            userJson.setAuthorityIds(null);
//                        }else {
//                            userJson.setAuthorityIds(null);
//                        }
//                    }else {
//                        userJson.setAuthorityIds(null);
//                    }
//                    return userJson;
//                }
//                /**
//                 * jsonarr to string
//                 * @param objstr
//                 * @return
//                 */
//                public static String jsonArr_to_str(String objstr){
//                    String str="";
//                    if(null!=objstr && !objstr.equals("")){
//                        JSONArray ja = JSONArray.parseArray(objstr);
//                        Iterator<Object> i = ja.iterator();
//
//                        while (i.hasNext()) {
//                            JSONObject ob = (JSONObject) i.next();
//                            if(ob.getString("K")!=null){
//                                if(str.equals("")){
//                                    str = ob.getString("K");
//                                }else{
//                                    str += "," +ob.getString("K");
//                                }
//                            }
//                        }
//                    }
//                    return str;
//                }
//
//
//                 /**
//                 * 将map按value值大小进行排序 o2-o1为倒序 o1-o2为正序
//                 */
//                public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
//                        Map<K, V> map) {
//                    List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(
//                            map.entrySet());
//                    Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
//                        public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
//                            return (o2.getValue()).compareTo(o1.getValue());
//                        }
//                    });
//
//                    Map<K, V> result = new LinkedHashMap<K, V>();
//                    for (Map.Entry<K, V> entry : list) {
//                        result.put(entry.getKey(), entry.getValue());
//                    }
//                    return result;
//                }
//                /**
//                 * 通过HashSet踢除重复元素除去List集合中的重复数据
//                 * @param <T>
//                 *
//                 */
//                public static <T> List<T> removeDuplicate(List<T> list){
//
//                    Set<T> h = new  HashSet<T>(list);
//                    list.clear();
//                    list.addAll(h);
//                    return list;
//                }
//                /**
//                 * string 时间转 date
//                 * @param date
//                 * @param pattern
//                 * @return
//                 */
//                public static Date formatDateString(String date, String pattern) {
//                    try {
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//                        return simpleDateFormat.parse(date);
//                    } catch (Exception e) {
//                        return null;
//                    }
//                }
//                /**
//                 * 计算时间差      严格意义上的时间差
//                 * @param beginDate String 开始时间
//                 * @param endDate String 结束时间
//                 * @param pattern String 时间格式
//                 * @param unit String 返回单位(yy:返回相差多少年,MM:返回相差多少个月,dd:返回相差多少天,HH:返回相差多少个小时,mm:返回相差多少分钟,ss:返回相差多少秒,ms:返回相差多少毫秒)
//                 * @return Long a 时间差的绝对值  a=-1时 表示输入的 返回单位有误;a=-2时 表示输入的时间或者时间格式有误
//                 */
//                public static Long time_difference(String beginDateStr, String endDateStr ,String pattern, String unit) {
//                    Long a =null;
//                    SimpleDateFormat dfs = new SimpleDateFormat(pattern);
//                    try {
//                        Date beginDate = dfs.parse(beginDateStr);
//                        Date endDate = dfs.parse(endDateStr);
//                        a=time_difference(beginDate, endDate, unit);
//                    } catch (ParseException e) {
//                        a=-2L;
//                        e.printStackTrace();
//                    }
//                    return a;
//                }
//                public static Long time_difference(Date beginDate, Date endDate , String unit) {
//                    Long a =null;
//                    try {
//                        Date begin = beginDate;
//                        Date end = endDate;
//                        Calendar beginCalendar = Calendar.getInstance();
//                        Calendar endCalendar = Calendar.getInstance();
//                        if (unit != null && !"".equals(unit)) {
//                            long between = (end.getTime() - begin.getTime());// 得到时间差的毫秒数
//                            if ("yy".equals(unit) || "MM".equals(unit) || "dd".equals(unit)) {
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                                beginCalendar.setTime(sdf.parse(formatDate(begin, "yyyy-MM-dd HH:mm:ss")));
//                                endCalendar.setTime(sdf.parse(formatDate(end, "yyyy-MM-dd HH:mm:ss")));
//                                int n = 0;
//                                if(!endCalendar.after(beginCalendar)){
//                                    Calendar c = Calendar.getInstance();
//                                    c=endCalendar;
//                                    endCalendar=beginCalendar;
//                                    beginCalendar = c;
//                                }
//                                while (endCalendar.after(beginCalendar)) { // 循环对比，直到相等，n
//                                    n++;
//                                    if ("MM".equals(unit)) {
//                                        beginCalendar.add(Calendar.MONTH, 1); // 比较月份，月份+1
//                                    } else {
//                                        beginCalendar.add(Calendar.DATE, 1); // 比较天数，日期+1
//                                    }
//                                }
//                                n = n - 1;
//                                if ("yy".equals(unit)) {
//                                    n = (int) n / 365;
//                                }
//                                a=Math.abs((long)n);
//                            } else if ("HH".equals(unit)) {// 时
//                                a = between / (1000 * 60 * 60);
//                                a=Math.abs(a);
//                            } else if ("mm".equals(unit)) {// 分
//                                a = between / (1000 * 60);
//                                a=Math.abs(a);
//                            } else if ("ss".equals(unit)) {// 秒
//                                a = between / 1000;
//                                a=Math.abs(a);
//                            } else if ("ms".equals(unit)) {// 毫秒
//                                a = between;
//                                a=Math.abs(a);
//                            } else {// 未匹配
//                                a = -1L;
//                            }
//                        } else {// 输入格式为空
//                            a = -1L;
//                        }
//                    } catch (Exception e) {
//                        a=-2L;
//                        e.printStackTrace();
//                    }
//                    return a;
//                }
//                /**
//                 * 时间格式化  date 转string
//                 * @param date
//                 * @param pattern
//                 * @return
//                 */
//                public static synchronized String formatDate(Date date, String pattern) {
//                    try {
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//                        return simpleDateFormat.format(date);
//                    } catch (RuntimeException e) {
//                        return null;
//                    }
//                }
//                /**
//                 * 判断字符串是否为域名（不含中文域名） cbw
//                 * @param addressArr
//                 * @return
//                 */
//                public static boolean isDomain(String addressArr) {
//                    boolean flag=false;
//                    String regex = "(([\\w\\d\\-_]+\\.):?[^-_])+\\w{2,4}";
//                    Pattern p = Pattern.compile(regex);
//
//                    try {
//                        Matcher m = p.matcher(addressArr);
//                        if (m.find()) {
//                            flag = true;
//                        } else {
//                            flag = false;
//                        }
//                    } catch(Exception e) {
//                        flag = false;
//                    }
//                    return flag;
//                }
//                /**
//                 * 内容关键字标红
//                 * @param kws
//                 * @param content
//                 * @return
//                 */
//                public static String KRedMaker(String kws,String content){
//
//                    String str = content.replaceAll("\"", "&quot;").replaceAll("'", "&apos;");
//                    Set<String> kset = new HashSet<String>();
//                    if(StringUtils.isNotBlank(content)){
//                        String[] kw =null;
//                        if(kws != null){
//                            kws = str_to_change_red_keywords(kws);
//                            if(kws.contains("#")){//用于过滤new_ref和paper_ref表中对应hit_keywords字段的关键词,他们以#分割
//                                kw = kws.trim().split("#");
//                            }else{
//                                kw = kws.trim().split("\\s");
//                            }
//                            if(kw != null && kw.length >0){
//
//                                for(int i = 0;i<kw.length;i++){
//                                    if(StringUtils.isNotBlank(kw[i].trim()))
//                                        kset.add(kw[i].trim());
//                                }
//                                for(String s : kset){
//                                    if(s != null && !s.isEmpty()){
//                                        if(Helper.is_english_letter(s)){
//                                            String ss = "[^a-zA-Z]"+s+"[^a-zA-Z]";
//                                            str = IgnoreCaseReplace(str,ss," "+RED_MARKER_TAG_BEGIN+s+RED_MARKER_TAG_END+" ");
//                                        }else{
//                                            str = IgnoreCaseReplace(str,s, RED_MARKER_TAG_BEGIN+s+RED_MARKER_TAG_END);
//                                        }
//
//                                    }
//
//                                }
//                            }
//                        }
//                        return str.trim();
//                    }else{
//                        return null;
//                    }
//                }
//
//
//                /**
//                 * 拼接标红关键词
//                 * @param s
//                 * @return
//                 */
//                public static String str_to_change_red_keywords(String keywords){
//                    String changeStr = "";
//                    if(StringUtils.isNotBlank(keywords) && Helper.is_contants_english_letter(keywords)){ // 判断是否包含英文关键词
//                        String [] str = keywords.split("\\s+"); // 有多组关键词
//                        for(int i = 0;i<str.length;i++){
//                            if(Helper.is_english_letter(str[i]))changeStr += Helper.three_type_string(str[i]);
//                            else changeStr += " "+str[i]+" ";
//                        }
//                    }else{  // 不包含英文关键词
//                        String [] str = keywords.split("\\s+"); // 有多组关键词
//                        for(int i = 0;i<str.length;i++){
//                            changeStr += " "+str[i]+" ";
//                        }
//                    }
//                    return changeStr;
//                }
//
//                /**
//                 * 判断是否包含英文字母
//                 * @param s
//                 * @return
//                 */
//                public static boolean is_contants_english_letter(String s){
//                    String reg = ".*[A-Za-z0-9]+.*";
//                    Pattern p = Pattern.compile(reg);
//                    return  p.matches(reg, s.trim());
//                }
//                /**
//                 *
//                 * 输入bAidu.com得到bAidu.com baidu.com Baidu.com
//                 * @param s
//                 * @return
//                 */
//                public static String three_type_string(String str){
//                    String s = str.trim();
//                    s += " "+s.toLowerCase()+" "+s.toUpperCase()+" "+s.substring(0, 1).toUpperCase() + s.toLowerCase().substring(1)+" ";
//                    return s;
//                }
//
//                /**
//                 * 判断是否是英文和点组成
//                 * @param s
//                 * @return
//                 */
//                public static boolean is_english_letter(String s){
//                    String reg = "[A-Za-z0-9\\.]+";
//                    Pattern p = Pattern.compile(reg);
//                    return  p.matches(reg, s.trim());
//                }
//
//                /**
//                 * 内容关键字标黄
//                 * @param kws
//                 * @param content
//                 * @return
//                 */
//                public static String KYellowMaker(String kws,String content){
//                    String str = content;
//                    if(StringUtils.isNotBlank(content)){
//                        String[] kw =null;
//                        if(kws != null){
//                            if(kws.contains("#")){//用于过滤new_ref和paper_ref表中对应hit_keywords字段的关键词,他们以#分割
//                                kw = kws.trim().split("#");
//                            }else{
//                                kw = kws.trim().split("\\s");
//                            }
//                            if(kw != null && kw.length >0){
//                                for(String s : kw){
//                                    if(s != null && !s.trim().isEmpty()) str = IgnoreCaseReplace(str,s.trim(), "<font style='color:#ffc785;'>"+s+"</font>");
//                                }
//                            }
//                        }
//                        return str;
//                    }else{
//                        return null;
//                    }
//
//                }
//                /**
//                 * 内容关键字标红
//                 * @param kws
//                 * @param content
//                 * @return
//                 */
//                public static String KActionMaker(String kws,String content){
//                    String str = content;
//                    if(StringUtils.isNotBlank(content)){
//                        String[] kw =null;
//                        if(kws != null){
//                            if(kws.contains("#")){//用于过滤new_ref和paper_ref表中对应hit_keywords字段的关键词,他们以#分割
//                                kw = kws.trim().split("#");
//                            }else{
//                                kw = kws.trim().split("\\s");
//                            }
//                            if(kw != null && kw.length >0){
//                                for(String s : kw){
//                                    if(s != null && !s.trim().isEmpty()) str = IgnoreCaseReplace(str,s.trim(), "<font style='color:#ffc785;'>"+s+"</font>");
//                                }
//                            }
//                        }
//                        return str;
//                    }else{
//                        return null;
//                    }
//                }
//                /**
//                 * 内容关键字标红
//                 * @param kws
//                 * @param content
//                 * @return
//                 */
//                public static String KEventMaker(String kws,String content){
//                    String str = content;
//                    if(StringUtils.isNotBlank(content)){
//                        String[] kw =null;
//                        if(kws != null){
//                            if(kws.contains("#")){//用于过滤new_ref和paper_ref表中对应hit_keywords字段的关键词,他们以#分割
//                                kw = kws.trim().split("#");
//                            }else{
//                                kw = kws.trim().split("\\s");
//                            }
//                            if(kw != null && kw.length >0){
//                                for(String s : kw){
//                                    if(s != null && !s.trim().isEmpty()) str = IgnoreCaseReplace(str,s.trim(), "<font style='color:#b8f75d;'>"+s+"</font>");
//                                }
//                            }
//                        }
//                        return str;
//                    }else{
//                        return null;
//                    }
//                }
//                /**
//                 * 内容关键字标红
//                 * @param kws
//                 * @param content
//                 * @return
//                 */
//                public static String KLeaderMaker(String kws,String content){
//                    String str = content;
//                    if(StringUtils.isNotBlank(content)){
//                        String[] kw =null;
//                        if(kws != null){
//                            if(kws.contains("#")){//用于过滤new_ref和paper_ref表中对应hit_keywords字段的关键词,他们以#分割
//                                kw = kws.trim().split("#");
//                            }else{
//                                kw = kws.trim().split("\\s");
//                            }
//                            if(kw != null && kw.length >0){
//                                for(String s : kw){
//                                    if(s != null && !s.trim().isEmpty()) str = IgnoreCaseReplace(str,s.trim(), "<font style='color:#ff00ff;'>"+s+"</font>");
//                                }
//                            }
//                        }
//                        return str;
//                    }else{
//                        return null;
//                    }
//                }
//                /**
//                 * 内容关键字标红
//                 * @param kws
//                 * @param content
//                 * @return
//                 */
//                public static String KTagMaker(String kws,String content){
//                    String str = content;
//                    if(StringUtils.isNotBlank(content)){
//                        String[] kw =null;
//                        if(kws != null){
//                            if(kws.contains("#")){//用于过滤new_ref和paper_ref表中对应hit_keywords字段的关键词,他们以#分割
//                                kw = kws.trim().split("#");
//                            }else{
//                                kw = kws.trim().split("\\s");
//                            }
//                            if(kw != null && kw.length >0){
//                                for(String s : kw){
//                                    if(s != null && !s.trim().isEmpty()) str = IgnoreCaseReplace(str,s.trim(), "<font style='color:#6097d6;'>"+s+"</font>");
//                                }
//                            }
//                        }
//                        return str;
//                    }else{
//                        return null;
//                    }
//                }
//                /**
//                 * 时间格式化
//                 * @param str
//                 * @return
//                 * @throws Exception
//                 */
//                public static Date Str2Date(String str) throws Exception{
//                    Date ret = null;
//                    if(StringUtils.isNotBlank(str)){
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                        ret = sdf.parse(str);
//                    }
//                    return ret;
//                }
//
//                /**
//                 *  时间格式化 精确到时分秒
//                 * @param str
//                 * @return
//                 * @throws Exception
//                 */
//                public static Date Str2DatePrecise(String str) throws Exception{
//                    Date ret = null;
//                    if(StringUtils.isNotBlank(str)){
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        ret = sdf.parse(str);
//                    }
//                    return ret;
//                }
//
//
//                public static Date stringToDate(String dateStr,String formatStr) throws ParseException{
//                    SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
//                    Date date = sdf.parse(dateStr);
//                    return date;
//                }
//
//                public static String Date2Str(Date date) throws Exception{
//                    String ret = null;
//                    if(date != null){
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                        ret = sdf.format(date);
//                    }
//                    return ret;
//                }
//                public static Date Str2Date(String str, String formate) throws Exception{
//                    Date ret = null;
//                    if(StringUtils.isNotBlank(str)){
//                        SimpleDateFormat sdf = new SimpleDateFormat(formate);
//                        ret = sdf.parse(str);
//                    }
//                    return ret;
//                }
//
//                public static String Date2Str(Date date, String formate) throws Exception{
//                    String ret = null;
//                    if(date != null){
//                        SimpleDateFormat sdf = new SimpleDateFormat(formate);
//                        ret = sdf.format(date);
//                    }
//                    return ret;
//                }
//                /**
//                 * 日期格式化，当天返回时分秒，之前返回日期+时间
//                 * @param date
//                 * @return
//                 */
//                public static String getTimeStr(Date date){
//                    long delta = new Date().getTime() - date.getTime();
//                    int day = Helper.daysBetween(date,new Date());
//                    try {
//                        //if(delta > 24L * ONE_HOUR){
//                        if(day==0){
//                            return Date2Str(date,"HH:mm:ss");
//                        }else{
//                            return Date2Str(date, "yyyy-MM-dd HH:mm:ss");
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    return null;
//                }
//
//                /**  返回格式为Today 5:60 pm - 12.06.2014 的字符串
//                 * @param date
//                 * @return
//                 */
//                public static String getTimeformat(Date date){
//                    return null;
//                }
//                /**
//                 * 拼接字符串
//                 * @param checks
//                 * @return
//                 */
//                public static String conStr(Object[] checks) {
//                    String ret = "";
//                    for (Object check : checks) {
//                        if(StringUtils.isNotBlank(check.toString())){
//                            if (StringUtils.isBlank(ret))
//                                ret = check.toString();
//                            else
//                                ret = ret + ", '" + check + "'";
//                        }
//                    }
//                    return ret;
//                }
//                /**
//                 * 列表转字符串
//                 * @param os
//                 * @return
//                 */
//                public static String list2str(List os,String split){
//                    StringBuilder str = new StringBuilder();
//                    for(Object o : os){
//                        if(str.length() == 0){
//                            str.append(o.toString());
//                        } else {
//                            str.append(split+o.toString());
//                        }
//                    }
//                    return str.toString();
//                }
//                /**
//                 * list to string
//                 * @param os
//                 * @param split
//                 * @return
//                 */
//                public static String list2str(String[] os,String split){
//                    StringBuilder str = new StringBuilder();
//                    for(Object o : os){
//                        if(str.length() == 0){
//                            str.append(o.toString());
//                        } else {
//                            str.append(split+o.toString());
//                        }
//                    }
//                    return str.toString();
//                }
//
//
//
//
//                /**
//                 * 内容中的关键词高亮
//                 * @param kws
//                 * @param content
//                 * @return
//                 */
//                public static String RedMaker(String kws,String content){
//                    String str = content;
//                    if(str==null){
//                        return str;
//                    }
//                    String str_marker=null;
//                    String[] kw =null;
//                    if(replaceSpecStr(kws)!=null){
//                        kws = replaceSpecStr(kws);
//                        if(kws.contains("#")){//用于过滤new_ref和paper_ref表中对应hit_keywords字段的关键词,他们以#分割
//                            kw = kws.trim().split("#");
//                        }else{
//                            kw = kws.trim().split("\\s+");
//                        }
//                        kw = array_unique(kw);
//                        if(kw != null && kw.length >0){
//                            String k_str="(";
//                            int i=1;
//                            for(String s : kw){
//                                k_str+=s;
//                                if(kw.length>i){
//                                    k_str+="|";
//                                }
//                                i++;
//                            }
//                              k_str+=")";
//                             str_marker= str.replaceAll(k_str,  "<span class='label label-danger'>$1</span>");
//                        }
//                    }else{
//                        str_marker=content;
//                    }
//                    return str_marker;
//                }
//
//                /**
//                 * 正则替换所有特殊字符 -- 将特殊字符替换为空格
//                 * @param orgStr
//                 * @return
//                 */
//                public static String replaceSpecStr(String orgStr){
//                    if (null!=orgStr&&!"".equals(orgStr.trim())) {
//                        String regEx="[\\s~·`!！@#￥$%^……&*（()）\\-——\\-_=+【\\[\\]】｛{}｝\\|、\\\\；;：:‘'“”\"，,《<。.》>、/？?]";
//                        Pattern p = Pattern.compile(regEx);
//                        Matcher m = p.matcher(orgStr);
//                        return m.replaceAll(" ");
//                    }
//                    return null;
//                }
//
//                /**
//                 * 标题中的关键词高亮
//                 * @param kws
//                 * @param content
//                 * @return
//                 */
//                public static String RedMakerTitle(String kws,String content){
//                    String str = content;
//                    String[] kw =null;
//                    if(kws != null){
//                        if(kws.contains(" ")){//用于过滤new_ref和paper_ref表中对应hit_keywords字段的关键词,他们以#分割
//                            kw = kws.trim().split(" ");
//                        }else{
//                            kw = kws.trim().split("\\s");
//                        }
//                        kw = array_unique(kw);
//                        if(kw != null && kw.length >0){
//                            for(String s : kw){
//                                if(s != null && !s.trim().isEmpty()) str = IgnoreCaseReplace(str,s.trim(), "<span class='label label-danger-title'>"+s+"</span>");
//                            }
//                        }
//                    }
//                    return str;
//
//                }
//                /**
//                 * 去除数组中重复的记录
//                 * @param a
//                 * @return
//                 */
//                public static String[] array_unique(String[] a) {
//                    // array_unique
//                    List<String> list = new LinkedList<String>();
//                    for(int i = 0; i < a.length; i++) {
//                        if(!list.contains(a[i])) {
//                            list.add(a[i]);
//                        }
//                    }
//                    return (String[])list.toArray(new String[list.size()]);
//                }
//                public static String IgnoreCaseReplace(String source, String oldstring,
//                        String newstring){
//                        //Pattern p = Pattern.compile(oldstring, Pattern.CASE_INSENSITIVE);
//                        Pattern p = Pattern.compile(oldstring);
//                        Matcher m = p.matcher(source);
//                        String ret=m.replaceAll(newstring);
//                        return ret;
//                        }
//
//                /**
//                 * 得到几天前的时间
//                 *
//                 * @param d
//                 * @param day
//                 * @return
//                 */
//                public static Date getDateBefore(Date d, int day) {
//                    Calendar now = Calendar.getInstance();
//                    now.setTime(d);
//                    now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
//                    return now.getTime();
//                }
//                /**
//                 * 得到几天前的时间
//                 *
//                 * @param d
//                 * @param day
//                 * @return
//                 */
//                public static String getDateBefore(String date, int day) {
//                    String time = "";
//                    try {
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        Calendar now = Calendar.getInstance();
//                        now.setTime(sdf.parse(date));
//                        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
//                        time = sdf.format(now.getTime());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    return time;
//                }
//
//                /**
//                 * Record转换为json数组
//                 *
//                 * @param list
//                 * @return
//                 */
//                public static JSONArray list2jsonarray(List<Record> list){
//                    JSONArray array=new JSONArray();
//                    String jobj=JSONObject.toJSONString(list);
//                    JSONArray jobjarry=JSONObject.parseArray(jobj);
//                    for(Object obj :jobjarry){
//                        JSONObject jsobj=(JSONObject)obj;
//                        array.add(jsobj.get("columns"));
//                    }
//                    return array;
//                }
//
//                /**
//                 * Record转换为数组
//                 *
//                 * @param list
//                 * @return
//                 */
//                public static List list2array(List<Record> list){
//                    List array=new ArrayList();
//                    String jobj=JSONObject.toJSONString(list);
//                    JSONArray jobjarry=JSONObject.parseArray(jobj);
//                    for(int i=0;i<7;i++){
//                        if(jobjarry.size()>i){
//                            JSONObject jsobj=(JSONObject)jobjarry.get(i);
//                            JSONObject columns=(jsobj.get("columns")==null?null:(JSONObject) jsobj.get("columns"));
//                            array.add(columns==null?null:columns.get("comment_num"));
//                        }else{
//                            array.add(0);
//                        }
//                    }
//                    return array;
//                }
//
//                /**
//                 * Record转换为逗号隔开的字符串
//                 *
//                 * @param list
//                 * @return
//                 */
//                public static String list2splitstr(List<Record> list,String columns){
//                    String res="";
//                    String jobj=JSONObject.toJSONString(list);
//                    JSONArray jobjarry=JSONObject.parseArray(jobj);
//                    for(int i=0;i<jobjarry.size();i++){
//                        JSONObject jsobj=(JSONObject)jobjarry.get(i);
//                        JSONObject obj=(JSONObject) (jsobj.get("columns")==null?0:jsobj.get("columns"));
//                        res+=","+(obj.get(columns));
//                    }
//                    if(res!=""){
//                        return res.substring(1);
//                    }
//                    return "";
//                }
//
//
//                /**
//                 * 获取前几个小时的时间
//                 * @param d
//                 * @param hour
//                 * @return
//                 */
//                public static String getDateTimeBefor(Date d,Integer hour){
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.setTime(d);
//                    calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
//                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            //    	System.out.println("12个小时前的时间：" + df.format(calendar.getTime()));
//            //    	System.out.println("当前的时间：" + df.format(new Date()));
//                    return df.format(calendar.getTime());
//                }
//                /**
//                 * 获取前半小时的时间
//                 * @param d
//                 * @param hour
//                 * @return
//                 */
//                public static String getMinuteBefor(Date d,Integer minute){
//                    Calendar calendar=Calendar.getInstance();
//                    calendar.add(Calendar.MINUTE,-minute);
//                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    String dateStr=sdf.format(calendar.getTimeInMillis());
//                    return sdf.format(calendar.getTime());
//                }
//                /**
//                 * 获取前几个小时的时间
//                 * @param date
//                 * @param hour
//                 * @return
//                 */
//                public static String getDateTimeBefor(String d,Integer hour){
//                    String date = "";
//                    if(!"".equals(d) && d!=null){
//                        try {
//                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                            Calendar calendar = Calendar.getInstance();
//                            calendar.setTime(df.parse(d));
//                            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
//                            date = df.format(calendar.getTime());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    return date;
//                }
//
//                /**
//                 * json字段排序
//                 * @param ja
//                 * @param field
//                 * @param isAsc
//                 */
//                @SuppressWarnings("unchecked")
//                public  static void Jsonsort(net.sf.json.JSONArray ja,final String field, boolean isAsc){
//                    Collections.sort(ja, new Comparator<net.sf.json.JSONObject>() {
//                       public int compare(net.sf.json.JSONObject o1, net.sf.json.JSONObject o2) {
//                            Object f1 = o1.get(field);
//                            Object f2 = o2.get(field);
//                            if(f1 instanceof Number && f2 instanceof Number){
//                               return ((Number)f1).intValue() - ((Number)f2).intValue();
//                            }else{
//                               return f1.toString().compareTo(f2.toString());
//                            }
//                       }
//                      });
//                    if(!isAsc){
//                        Collections.reverse(ja);
//                      }
//                    }
//                /**
//                 * 判断输入的字符串是否满足时间格式 ： yyyy-MM-dd HH:mm:ss
//                 * @param patternString 需要验证的字符串
//                 * @return 合法返回 true ; 不合法返回false
//                 */
//                public static boolean isTimeLegal(String patternString) {
//
//                     Pattern a=Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s((([0-1][0-9])|(2?[0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
//                     Matcher b=a.matcher(patternString);
//                     if(b.matches()) {
//                           return true;
//                     } else {
//                           return false;
//                     }
//                }
//                /**
//                 * 获取两个日期之间的天数
//                 * @param early
//                 * @param late
//                 * @return
//                 */
//                public static int daysBetween(Date early, Date late) {
//
//                    Calendar calst = Calendar.getInstance();
//                    Calendar caled = Calendar.getInstance();
//                    calst.setTime(early);
//                     caled.setTime(late);
//                     //设置时间为0时
//                     calst.set(Calendar.HOUR_OF_DAY, 0);
//                     calst.set(Calendar.MINUTE, 0);
//                     calst.set(Calendar.SECOND, 0);
//                     caled.set(Calendar.HOUR_OF_DAY, 0);
//                     caled.set(Calendar.MINUTE, 0);
//                     caled.set(Calendar.SECOND, 0);
//                    //得到两个日期相差的天数
//                     int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
//                            .getTime().getTime() / 1000)) / 3600 / 24;
//
//                    return days;
//               }
//
//                /**
//                 * 计算时间差
//                 * @param endDate
//                 * @param nowDate
//                 * @return
//                 */
//                public static String getDatePoor(Date endDate, Date nowDate) {
//
//                    if(endDate==null && nowDate==null){
//                        return "";
//                    }
//                    long nd = 1000 * 24 * 60 * 60;
//                    long nh = 1000 * 60 * 60;
//                    long nm = 1000 * 60;
//                    // long ns = 1000;
//                    // 获得两个时间的毫秒时间差异
//                    long diff = endDate.getTime() - nowDate.getTime();
//                    // 计算差多少天
//                    long day = diff / nd;
//                    // 计算差多少小时
//                    long hour = diff % nd / nh;
//                    // 计算差多少分钟
//                    long min = diff % nd % nh / nm;
//                    // 计算差多少秒//输出结果
//                    // long sec = diff % nd % nh % nm / ns;
//                    return day + "天" + hour + "小时" + min + "分钟";
//                }
//
//                /**
//                 * 去除字符串中的空格、回车、换行符、制表符
//                 * @param str
//                 * @return
//                 */
//                public static String replaceBlank(String str) {
//                  String dest = "";
//                  if (str!=null) {
//                   Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//                   Matcher m = p.matcher(str);
//                   dest = m.replaceAll("");
//                  }
//                  return dest;
//                }
//                public static boolean isNumeric(String str){
//                    for (int i = 0; i < str.length(); i++){
//                        if (!Character.isDigit(str.charAt(i))){
//                            return false;
//                        }
//                    }
//                    return true;
//                }
//                /**
//                 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为1,英文字符长度为0.5
//                 * @param String s 需要得到长度的字符串
//                 * @return int 得到的字符串长度
//                 */
//                public static double getLength(String s) {
//                 double valueLength = 0;
//                    String chinese = "[\u4e00-\u9fa5]";
//                    // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
//                    for (int i = 0; i < s.length(); i++) {
//                        // 获取一个字符
//                        String temp = s.substring(i, i + 1);
//                        // 判断是否为中文字符
//                        if (temp.matches(chinese)) {
//                            // 中文字符长度为1
//                            valueLength += 1;
//                        } else {
//                            // 其他字符长度为0.5
//                            valueLength += 0.5;
//                        }
//                    }
//                    //进位取整
//                    return  Math.ceil(valueLength);
//                }
//
//                /**
//                 * 判断字符串是否符合日期格式
//                 * @param str
//                 * @param form
//                 * @return
//                 */
//                public static boolean isValidDate(String str,String form) {
//                    boolean convertSuccess=true;
//                    // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
//                     SimpleDateFormat format = new SimpleDateFormat(form);
//                    try {
//                        // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
//                        format.setLenient(false);
//                        format.parse(str);
//                     } catch (ParseException e) {
//                         // e.printStackTrace();
//                         // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
//                         convertSuccess=false;
//                     }
//                     return convertSuccess;
//                }
//                /**
//                 * 获取分类类型
//                 * @param type
//                 * @return
//                 */
//                public static String getcategorytype(String type){
//                    if(StringUtils.isNotEmpty(type)){
//                        if("1".equals(type.trim())){
//                            return "人物";
//                        }else if("2".equals(type.trim())){
//                            return "地点";
//                        }else if("3".equals(type.trim())){
//                            return "组织";
//                        }else if("4".equals(type.trim())){
//                            return "事件";
//                        }else if("5".equals(type.trim())){
//                            return "专项";
//                        }else if("6".equals(type.trim())){
//                            return "自定义";
//                        }
//                    }
//                    return "";
//                }
//                /**
//                 * 递归   去除所有jsonobject  存入jsonarray
//                 * @param data
//                 * @param j
//                 */
//                public static void  trans(Object  data,net.sf.json.JSONArray j){
//                    net.sf.json.JSONArray ja = net.sf.json.JSONArray.fromObject(data);
//                    for(Object object : ja){
//                        net.sf.json.JSONObject o = net.sf.json.JSONObject.fromObject(object);
//                        net.sf.json.JSONObject r = new net.sf.json.JSONObject();
//                         r.put("name", o.get("name"));
//                         r.put("interCount", o.get("interCount"));
//                         r.put("withZDRcount", o.get("withZDRcount"));
//                         r.put("data_reply_ids", o.get("data_reply_ids"));
//                         r.put("data_ref_ids", o.get("data_ref_ids"));
//                         r.put("userid", o.get("userid"));
//                         r.put("level", o.get("level"));
//                         r.put("site", o.get("site"));
//                         r.put("isZDR", o.get("isZDR"));
//                         r.put("count", o.get("count"));
//                         r.put("id", o.get("id"));
//                         r.put("isFriend",o.get("isFriend"));
//                         r.put("isMyself",o.get("isMyself"));
//                         j.add(r);
//                         if(null!=o.get("child")){
//                             trans(o.get("child"),j);
//                         }
//                    }
//                }
//                /**
//                * 求Map<K,V>中Value(值)的最大值
//                * @param map
//                * @return
//                */
//                public static Object getMaxValue(Map<String, Long> map) {
//                    if (map == null) {
//                        return null;
//                    }
//                    Collection<Long> c = map.values();
//                    Object[] obj = c.toArray();
//                    Arrays.sort(obj);
//                    return obj[obj.length-1];
//                }
//                /**
//                * 求Map<K,V>中Value(值)的最小值
//                * @param map
//                * @return
//                */
//                public static Object getMinValue(Map<String, Long> map) {
//                    if (map == null) {
//                        return null;
//                    }
//                    Collection<Long> c = map.values();
//                    Object[] obj = c.toArray();
//                    Arrays.sort(obj);
//                    return obj[0];
//                }
//                /**
//                 * 获得比率
//                 * @param now
//                 * @param min
//                 * @param max
//                 * @param st
//                 * @param et
//                 * @return
//                 */
//                public static String getRow(Long now ,Long min ,Long max,float st,float et){
//                    // 创建一个数值格式化对象
//                    NumberFormat numberFormat = NumberFormat.getInstance();
//                    // 设置精确到小数点后2位
//                    numberFormat.setMaximumFractionDigits(0);
//                    //float l=  (float) Integer.parseInt(now.toString()) / (float) max * 100;
//                    float ll= (float)(now-min)/(max-min);
//                    float o= (et-st);
//                    float lll = (o * ll + st)*100;
//                    String result =(max>min)? numberFormat.format(lll):"0";
//                    return result;
//                }
//
//                /**
//                 * 根据excel页名称 判断预警区域类型
//                 * 1:政府驻地 2:政府单位 3:公安机关 4:法院 5:检察机关 6:地标建筑 7:广场 8:购物中心 9:公园 10:交通枢纽 11：维族餐厅 12：其他
//                 * @param sheetName
//                 * @return
//                 */
//                public static String getAreaType(String sheetName) {
//                    String result = "";
//                    if(sheetName.equals("政府驻地")){
//                        result = "1";
//                    }else if(sheetName.equals("政府单位")){
//                        result = "2";
//                    }else if(sheetName.equals("公安机关")){
//                        result = "3";
//                    }else if(sheetName.equals("法院")){
//                        result = "4";
//                    }else if(sheetName.equals("检察机关")){
//                        result = "5";
//                    }else if(sheetName.equals("地标建筑")){
//                        result = "6";
//                    }else if(sheetName.equals("广场")){
//                        result = "7";
//                    }else if(sheetName.equals("购物中心")){
//                        result = "8";
//                    }else if(sheetName.equals("公园")){
//                        result = "9";
//                    }else if(sheetName.equals("交通枢纽")){
//                        result = "10";
//                    }else if(sheetName.equals("维族餐厅")){
//                        result = "11";
//                    }else{
//                        result = "12";
//                    }
//                    return result;
//                }
//                /**
//                 * 获取区域类型
//                 * @param areaType
//                 * @return
//                 */
//                public static String getAreaTypes(String areaType){
//                    String area = "";
//                    if(areaType.indexOf("1")!=-1){
//                        area+="政府驻地"+" ";
//                    }
//                    if(areaType.indexOf("2")!=-1){
//                        area+="政府单位"+" ";
//                    }
//                    if(areaType.indexOf("3")!=-1){
//                        area+="公安机关"+" ";
//                    }
//                    if(areaType.indexOf("4")!=-1){
//                        area+="法院"+" ";
//                    }
//                    if(areaType.indexOf("5")!=-1){
//                        area+="检察机关"+" ";
//                    }
//                    if(areaType.indexOf("6")!=-1){
//                        area+="地标建筑"+" ";
//                    }
//                    if(areaType.indexOf("7")!=-1){
//                        area+="广场"+" ";
//                    }
//                    if(areaType.indexOf("8")!=-1){
//                        area+="购物中心"+" ";
//                    }
//                    if(areaType.indexOf("9")!=-1){
//                        area+="公园"+" ";
//                    }
//                    if(areaType.indexOf("10")!=-1){
//                        area+="交通枢纽"+" ";
//                    }
//                    if(areaType.indexOf("11")!=-1){
//                        area+="维族餐厅"+" ";
//                    }
//                    if(areaType.indexOf("12")!=-1){
//                        area+="其他"+" ";
//                    }
//                    return area;
//                }
//                /**
//                 * 根据url获取站点名称
//                 * @param url
//                 * @return
//                 */
//                  public static String getSiteByUrl(String url){
//                        String site=new String();
//                        if(null!=url){
//                            if(url.contains("weibo.com")){
//                                site="新浪微博";
//                            }else if(url.contains("t.qq.com")){
//                                site="腾讯微博";
//                            }else if(url.contains("tieba.baidu.com")){
//                                site="百度贴吧";
//                            }else if(url.contains("tianya.cn")){
//                                site="天涯论坛";
//                            }else if(url.contains("weixin")){
//                                site="微信";
//                            }
//                            return site;
//                        }
//                        return null;
//                    }
//                /**
//                 * 获取头像
//                 * @param imgurl
//                 * @return
//                 */
//                public static String getHeadImg(String imgurl){
//                    String resultImgUrl = null;
//                    String defaultHeadImage = "image://./images/default_headimg/head_img-big-dot.png";
//                    if(imgurl.length()>0){
//                        if (null != imgurl && !imgurl.trim().equals("")) {
//                            resultImgUrl = "image://" + HeadImgPathUtils.getImg_server_path()+"/"+ imgurl;
//                        } else {
//                            resultImgUrl = defaultHeadImage;
//                        }
//                        return resultImgUrl;
//                    }else {
//                        return defaultHeadImage;
//                    }
//                }
//                /**
//                 * 获取时间区间内的不同粒度的时间集合
//                 * @param dBegin
//                 * @param dEnd
//                 * @param granularity 粒度 1：1天  2：两天
//                 * @return
//                 */
//                public static List<Date> findDates(Date dBegin, Date dEnd, int granularity) {
//                    List<Date> lDate = new ArrayList<Date>();
//                    lDate.add(dBegin);
//                    Calendar calBegin = Calendar.getInstance();
//                    // 使用给定的 Date 设置此 Calendar 的时间
//                    calBegin.setTime(dBegin);
//                    Calendar calEnd = Calendar.getInstance();
//                    // 使用给定的 Date 设置此 Calendar 的时间
//                    calEnd.setTime(dEnd);
//                    // 测试此日期是否在指定日期之后
//                    while (dEnd.after(calBegin.getTime())) {
//                        // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
//                        calBegin.add(Calendar.DAY_OF_MONTH, granularity);
//                        lDate.add(calBegin.getTime());
//                    }
//                    return lDate;
//                }
//                /**
//                 * 获取时间区间内的不同粒度的时间集合
//                 * @param dBegin
//                 * @param dEnd
//                 * @param pattern "yyyy-MM-dd HH-mm-ss"
//                 * @param granularity 粒度 1：1天  2：两天
//                 * @return
//                 */
//                public static JSONArray findJsonArrayDates(Date dBegin, Date dEnd, String pattern ,int granularity) {
//                    JSONArray lDate = new JSONArray();
//                    SimpleDateFormat df = new SimpleDateFormat(pattern);
//                    lDate.add(df.format(dBegin));
//                    Calendar calBegin = Calendar.getInstance();
//                    // 使用给定的 Date 设置此 Calendar 的时间
//                    calBegin.setTime(dBegin);
//                    Calendar calEnd = Calendar.getInstance();
//                    // 使用给定的 Date 设置此 Calendar 的时间
//                    calEnd.setTime(dEnd);
//                    // 测试此日期是否在指定日期之后
//                    while (dEnd.after(calBegin.getTime())) {
//                        // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
//                        calBegin.add(Calendar.DAY_OF_MONTH, granularity);
//                        lDate.add(df.format(calBegin.getTime()));
//                    }
//                    return lDate;
//                }
//                public static void main(String[] args) {
//
//                    JSONArray findJsonArrayDates = Helper.findJsonArrayDates(Helper.getDateBefore(new Date(), 30), new Date(), "yyyy-MM-dd 00:00:00", 1);
//                }
//
//
//                /**
//                 * 获取并格式开始和结束日期（如果为空返回近一个月时间 ，格式为： yyyy-MM-dd HH:mm:ss）
//                 * @param etime
//                 * @param stime
//                 * @return
//                 */
//                public static Map<String, String> get_and_format_date(String stime, String etime) {
//                    Map<String,String> map =  new HashMap<String,String>();
//
//                    //时间为空，设置近一个月时间
//                    if(StringUtils.isEmpty(stime) || StringUtils.isEmpty(etime)){
//                        try {
//                            etime = CommonUtils.getCurrentDatePrecise().split(" ")[0];
//                            stime = Helper.getDateBefore(CommonUtils.getCurrentDatePrecise(), -30).split(" ")[0];
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if(stime.length() == 10){
//                        stime += " 00:00:00";
//                    }
//                    if(etime.length() == 10){
//                        etime += " 23:59:59";
//                    }
//                    map.put("etime", etime);
//                    map.put("stime", stime);
//                    return map;
//                }
//
//                /**
//                 * 获取汉字串拼音首字母，英文字符不变
//                 * @param chinese 汉字串
//                 * @return 汉语拼音首字母
//                 */
//                public static String getFirstSpell(String chinese) {
//                        StringBuffer pybf = new StringBuffer();
//                        char[] arr = chinese.toCharArray();
//                        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
//                        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//                        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
//                        for (int i = 0; i < arr.length; i++) {
//                                if (arr[i] > 128) {
//                                        try {
//                                                String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
//                                                if (temp != null) {
//                                                        pybf.append(temp[0].charAt(0));
//                                                }
//                                        } catch (BadHanyuPinyinOutputFormatCombination e) {
//                                                e.printStackTrace();
//                                        }
//                                } else {
//                                        pybf.append(arr[i]);
//                                }
//                        }
//                        return pybf.toString().replaceAll("\\W", "").trim();
//                }
//                /**
//                 * 解析信息汇总查询数据
//                 * @param ja
//                 */
//                public static net.sf.json.JSONArray analyticalData(net.sf.json.JSONArray ja,net.sf.json.JSONArray person){
//                    net.sf.json.JSONArray arr = new net.sf.json.JSONArray();
//                    for (Object object : ja) {
//                        net.sf.json.JSONObject element = net.sf.json.JSONObject.fromObject(object);
//                        net.sf.json.JSONArray list = new net.sf.json.JSONArray();
//                        //人员
//                        if(StringUtils.isNotEmpty(person.getString(0))){
//                            list.add(person.get(0));
//                        }else{
//                            list.add("");
//                        }
//                        //虚拟账号
//                        if(StringUtils.isNotEmpty(element.getString("blogger"))){
//                            list.add(element.getString("blogger"));
//                        }else{
//                            list.add("");
//                        }
//                        //站点
//                        if(StringUtils.isNotEmpty(element.getString("website_name"))){
//                            list.add(element.getString("website_name"));
//                        }else{
//                            list.add("");
//                        }
//                        //情感倾向
//                        if(element.getString("support_level").equals("1")){
//                            list.add("正面");
//                        }else if(element.getString("support_level").equals("2")){
//                            list.add("负面");
//                        }else if(element.getString("support_level").equals("3")){
//                            list.add("中立");
//                        }else{
//                            list.add("");
//                        }
//                        //内容类别
//                        if(StringUtils.isNotEmpty(element.getString("info_category"))){
//                            list.add(element.getString("info_category"));
//                        }else{
//                            list.add("");
//                        }
//                        //标题
//                        if(StringUtils.isNotEmpty(element.getString("title"))){
//                            list.add(element.getString("title"));
//                        }else{
//                            if(StringUtils.isNotEmpty(element.getString("content"))){
//                                if(element.getString("content").length()>10){
//                                    list.add(element.getString("content").substring(0,10));
//                                }else{
//                                    list.add(element.getString("content"));
//                                }
//                            }else{
//                                list.add("");
//                            }
//                        }
//                        //发布时间
//                        if(StringUtils.isNotEmpty(element.getString("pubtime"))){
//                            list.add(element.getString("pubtime"));
//                        }else{
//                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//                            // new Date()为获取当前系统时间
//                            list.add(df.format(new Date()));
//                        }
//                        //源网址
//                        if(StringUtils.isNotEmpty(element.getString("url"))){
//                            list.add(element.getString("url"));
//                        }else{
//                            list.add("");
//                        }
//                        //正文
//                        if(StringUtils.isNotEmpty(element.getString("content"))){
//                            list.add(element.getString("content"));
//                        }else{
//                            list.add("");
//                        }
//                        arr.add(list);
//                    }
//                        return arr;
//                }
//
//                /*
//                 * 根据时间力度选择适当的时间
//                 */
//                public static String getStimeAndETime(String granularity) {
//                    Date now = new Date();
//                    String stime = "";
//                    String etime = "";
//                    try {
//                        String dataNow = Helper.Date2Str(now, "yyyy-MM-dd HH:mm:ss");
//                        //时间类型选择
//                        if (!Util.isEmpty(granularity)) {
//                           switch (Integer.valueOf(granularity)) {
//                               case 1: // 24小时
//                                   stime = Helper.Date2Str(now, "yyyy-MM-dd 00:00:00");//今日
//                                   etime = dataNow;
//                                   break;
//                               case 2: // 3天
//                                   stime = Helper.Date2Str(Helper.getDateBefore(now, 2), "yyyy-MM-dd 00:00:00");//近三天
//                                   etime = dataNow;
//                                   break;
//                               case 3: // 7天
//                                   stime = Helper.Date2Str(Helper.getDateBefore(now, 6), "yyyy-MM-dd 00:00:00");//近一周
//                                   etime = dataNow;
//                                   break;
//                               case 4: // 10天
//                                   stime = Helper.Date2Str(Helper.getDateBefore(now, 9), "yyyy-MM-dd 00:00:00");//近十天
//                                   etime = dataNow;
//                                   break;
//                               case 0: // 全部
//                                   stime = null;
//                                   etime = dataNow;
//                                   break;
//                               case -1: // 自定义
//                                   if (StringUtils.isBlank(etime)) {
//                                       etime = dataNow;
//                                   }
//                                   break;
//                           }
//                        } else {
//                           if (StringUtils.isBlank(etime)) {
//                               etime = dataNow;
//                           }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    return stime +"@"+ etime;
//                }
//
//                /**
//                 * 删除文件夹下面的文件
//                 * @param path
//                 * @return
//                 */
//                public static  boolean delAllFile(String path) {
//                    boolean flag = false;
//                    File file = new File(path);
//                    if (!file.exists()) {
//                        return flag;
//                    }
//                    if (!file.isDirectory()) {
//                        return flag;
//                    }
//                    String[] tempList = file.list();
//                    File temp = null;
//                    for (int i = 0; i < tempList.length; i++) {
//                        if (path.endsWith(File.separator)) {
//                            temp = new File(path + tempList[i]);
//                        } else {
//                            temp = new File(path + File.separator + tempList[i]);
//                        }
//                        if (temp.isFile()) {
//                            temp.delete();
//                        }
//                        if (temp.isDirectory()) {
//                            delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
//                            flag = true;
//                        }
//                    }
//                    return flag;
//                }
//
//                /**
//                 * 配置管理--数据采集列表筛选
//                 * @param granularity
//                 * @return
//                 */
//                public static String getStimeAndEtimeTransmission(String granularity) {
//                    Date now = new Date();
//                    String stime = "";
//                    String etime = "";
//                    try {
//                        String dataNow = Helper.Date2Str(now, "yyyy-MM-dd HH:mm:ss");
//                        //时间类型选择
//                        if (!Util.isEmpty(granularity)) {
//                           switch (Integer.valueOf(granularity)) {
//                               case 1: // 24小时(昨天11点~今天11点)
//                                   //昨天的11点
//                                   Calendar calendar = new GregorianCalendar();
//                                   calendar.setTime(new Date());
//                                   calendar.add(calendar.DATE,-1);
//                                   stime = Helper.Date2Str(calendar.getTime(), "yyyy-MM-dd 11:00:00");
//                                   //今天的11点
//                                   etime = Helper.Date2Str(now, "yyyy-MM-dd 11:59:59");
//                                   break;
//                               case 2: // 3天
//                                   stime = Helper.Date2Str(Helper.getDateBefore(now, 2), "yyyy-MM-dd 00:00:00");//近三天
//                                   etime = dataNow;
//                                   break;
//                               case 3: // 全部
//                                   stime = null;
//                                   etime = dataNow;
//                                   break;
//                               case -1: // 自定义
//                                   if (StringUtils.isBlank(etime)) {
//                                       etime = dataNow;
//                                   }
//                                   break;
//                           }
//                        } else {
//                           if (StringUtils.isBlank(etime)) {
//                               etime = dataNow;
//                           }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    return stime +"@"+ etime;
//                }
//
//
//                public static String format_content(String content){
//                    if(StringUtils.isEmpty(content))return "";
//                    String[] contents = content.split("</?br ?/?>");
//                    String new_content = "";
//                    for (String s : contents) {
//                        s =  s.replaceAll("(</?br ?/?>)|(&nbsp;)|(^　*)|(　$*)|(^ *)|( $*)", "").replaceAll("(　{2,})|( {2,})|( {2,})", " ").trim();
//                        new_content +="<p  class='formatcontent'  >"+s+"</p>";
//                    }
//                    return new_content;
//                }
//
//                public static String reverse_url(String source){
//                    if(source == null)
//                        return null;
//                    StringBuffer reverse = new StringBuffer();
//                    String[] splits = source.split("\\.");
//                    int len = splits.length;
//                    for(int i=len-1; i >=0; i--){
//                        reverse.append(splits[i]);
//                        if(i > 0)
//                            reverse.append(".");
//                    }
//                    return reverse.toString();
//                }
//
//                public static String[] site_name_string(String str){
//                    String s = str.trim();
//                    HashSet<String> hs = new HashSet<String>();
//                    hs.add("*"+ s + "*");
//                    hs.add("*" + s.toLowerCase() + "*");
//                    hs.add("+" + s.substring(0, 1).toUpperCase() + s.toLowerCase().substring(1) + "*");
//                    String[] ss = new String[hs.size()];
//                    ss = hs.toArray(ss);
//                    return ss;
//                }
//
//                public static Date getYesdayEmpty(int beforeDay) throws Exception {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    Calendar rightNow = Calendar.getInstance();
//                    rightNow.set(Calendar.DAY_OF_MONTH, rightNow.get(Calendar.DAY_OF_MONTH) - beforeDay);
//                    // rightNow.set(Calendar.HOUR_OF_DAY, 9);
//                    return sdf.parse(sdf.format(rightNow.getTime()));
//                    // return Calendar.getInstance().getTime();
//                }
//
//                /**
//                 * 清除乱码
//                 * @author dell
//                 *
//                 */
//                public static String cleanGarbledCode(String input){
//
//                        /*StringBuffer br = new StringBuffer();
//
//                        //符号
//                        String cc = "~!@#\\$%\\^&\\*\\(\\)_\\-\\+=\\[\\{\\]\\}\\|\\\\:;'<,>\\.\\?/！@#￥%……&*（）——【】、：；“”‘’《，》。？、·\"";
//                        //String cc = "\\.,";
//                        //String regex="([\u4e00-\u9fa5]+)";
//                        String regex="([\u3400-\u4DB5\u4E00-\u9FA5\u9FA6-\u9FBB\uF900-\uFA2D\uFA30-\uFA6A\uFA70-\uFAD9\uFF00-\uFFEF\u2E80-\u2EFF\u3000-\u303F\u31C0-\u31EF\\d+.\\d+|\\w"+cc+"]+)";
//
//                        Matcher matcher = Pattern.compile(regex).matcher(input);
//                        while(matcher.find()){
//                            br.append(matcher.group(1)).append("");
//                        }*/
//
//                    return input.replaceAll("\\pC", "");
//                }
//
//                /**
//                 * 半小时 ，一小时， 半天 一天前
//                 */
//                public static String getTimeRange(Date date){
//                    // 昨天
//                    //确切时间
//                    if(date!=null){
//                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//                        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
//                        Date now = new Date();
//                        if(sdfDay.format(date).endsWith(sdfDay.format(now))){
//                            //当天内
//                            if(sdf.format(date).equals("00:00:00")){
//                                return sdfDay.format(date);
//                            }else{
//                                return "<span style='color:#ff4400'>"+sdf.format(date)+"</span>";
//                            }
//                            //				return "<span style='color:#ff4400'>"+sdf.format(date)+"</span>";
//                        }else{
//                            return sdfDay.format(date);
//                            //				return "<span style='color:#ff4400'>"+sdf.format(date)+"</span>";
//                        }
//                    }else
//                        return null;
//                }
//
//                public static Timestamp formatTimestampString(String dateStr, String pattern) {
//                    try {
//                        if(dateStr!=null&&!"".equals(dateStr)){
//                            Date date= Helper.formatDateString(dateStr, pattern);
//                            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                            dateStr = sdf.format(date);
//                            return Timestamp.valueOf(dateStr);
//                        }else return null;
//                    } catch (Exception e) {
//                        return null;
//                    }
//                }
//
//            }
