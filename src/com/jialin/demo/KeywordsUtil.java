package com.jialin.demo;

import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class KeywordsUtil {
	public static final String TITLE="title";  // 查询标题
	public static final String CONTENT="content"; // 查询内容
	public static final String TITLE_OR_CONTENT=" ) OR ( "; // 查询标题或者内容
	public static final String TITLE_AND_CONTENT=" AND "; // 查询标题和内容
	
	public static void main(String[] args) {
		Set<String> contain_keywords_set = new HashSet<String>();
		Set<String> uncontain_keywords_set = new HashSet<String>();
		StringBuffer sb = new StringBuffer();
		contain_keywords_set.add("");
//		contain_keywords_set.add("徽菜");
//		uncontain_keywords_set.add("");
//		uncontain_keywords_set.add("克 郭敬明 郭德");
		assemble_containts_keywords_and_uncontaints_keywords_query_string(contain_keywords_set, uncontain_keywords_set, sb, null,TITLE_AND_CONTENT);
	}
	
	
	/**
	 * 拼接查询语句
	 * @param contain_keywords_set   包含关键词
	 * @param uncontain_keywords_set  不包含关键词
	 * @param allBuffer   拼接查询语句
	 * @param redkeywords  标红关键词
	 * @param type  TITLE.包含关键词只包含title  CONTENT.包含关键词只包含content  TITLE_OR_CONTENT.包含关键词包含content和title  TITLE_AND_CONTENT.包含关键词包含content或者title
	 * @return
	 */
	public static  String assemble_containts_keywords_and_uncontaints_keywords_query_string(Set<String> contain_keywords_set,Set<String> uncontain_keywords_set,StringBuffer allBuffer,String redkeywords,String type){
		redkeywords = assemble_containts_keywords(contain_keywords_set, allBuffer, redkeywords, type);
		assemble_uncontaints_keywords(uncontain_keywords_set, allBuffer);
		System.out.println(allBuffer);
		return redkeywords;
	}
	
	/**
	 * 拼接包含关键词
	 * @param contain_keywords_set 包含关键词
	 * @param allBuffer  拼接查询语句
	 * @param redkeywords 标红关键词
	 * @param type  TITLE.包含关键词只包含title  CONTENT.包含关键词只包含content  TITLE_OR_CONTENT.包含关键词包含content和title  TITLE_AND_CONTENT.包含关键词包含content或者title
	 * @return
	 */
	public static String assemble_containts_keywords(Set<String> contain_keywords_set,StringBuffer allBuffer,String redkeywords,String type){
		Set<String> process_contain_keywords_set = new HashSet<String>();
		for (String s : contain_keywords_set) {
			if(StringUtils.isNotBlank(s))process_contain_keywords_set.add(s);
		}
		
		if(TITLE.equals(type) || CONTENT.equals(type)){
			if(process_contain_keywords_set != null && process_contain_keywords_set.size() >0){
				allBuffer.append(" +( ");
				for(String keyword :process_contain_keywords_set){
					keyword = keyword.replaceAll("\\s+", " ");
					if(redkeywords != null)redkeywords += keyword+" ";
					String[] keywords = keyword.split(" ");
					StringBuffer buffer = new StringBuffer("");
					for(int i = 0; i < keywords.length; i ++){
						buffer.append(type+":\""+ keywords[i] +"\"");
						if(i < keywords.length - 1){
							buffer.append(" AND ");
						}
					}
					allBuffer.append("(").append(buffer).append(")").append(" OR ");
				}
				allBuffer = allBuffer.delete(allBuffer.length()-3, allBuffer.length()).append(" ) ");;
			}
		}else if(TITLE_OR_CONTENT.equals(type) || TITLE_AND_CONTENT.equals(type)){
			if(process_contain_keywords_set != null && process_contain_keywords_set.size() >0){
				allBuffer.append(" +( ");
				for(String keyword :process_contain_keywords_set){
					keyword = keyword.replaceAll("\\s+", " ");
					if(redkeywords != null)redkeywords += keyword+" ";
					String[] keywords = keyword.split(" ");
					StringBuffer titleBuffer = new StringBuffer("");
					StringBuffer contentBuffer = new StringBuffer("");
					for(int i = 0; i < keywords.length; i ++){
						titleBuffer.append("title:\""+ keywords[i] +"\"");
						contentBuffer.append("content:\""+ keywords[i] +"\"");
						if(i < keywords.length - 1){
							titleBuffer.append(" AND ");
							contentBuffer.append(" AND ");
						}
					}
					allBuffer.append("(").append(titleBuffer).append(type).append(contentBuffer).append(")").append(" OR ");
				}
				allBuffer = allBuffer.delete(allBuffer.length()-3, allBuffer.length()).append(" ) ");
			}
		}
		
		return redkeywords;
	}
	/**
	 * 
	 * @param uncontain_keywords_set 不包含关键词
	 * @param allBuffer  拼接查询语句
	 */
	public static void assemble_uncontaints_keywords(Set<String> uncontain_keywords_set,StringBuffer allBuffer){
		Set<String> process_uncontain_keywords_set = new HashSet<String>();
		if(uncontain_keywords_set != null && uncontain_keywords_set.size() >0){ // 排除关键去重
			for(String unkeyword :uncontain_keywords_set){
				if(StringUtils.isBlank(unkeyword)) continue;
				String[] uks =  unkeyword.split("\\|");
				for(int j = 0;j <uks.length ;j++){
					if(StringUtils.isNotBlank(uks[j].trim()))process_uncontain_keywords_set.add(uks[j].trim());
				}
			}
		}
		if(process_uncontain_keywords_set != null && process_uncontain_keywords_set.size() >0){ // 组合排除关键词
			allBuffer.append(" -(");
			StringBuffer titleBuffer = new StringBuffer("");
			StringBuffer contentBuffer = new StringBuffer("");
			 for (String unkyewords : process_uncontain_keywords_set) {
				  String[] unkeywords = unkyewords.trim().split("\\s+");
				  for(int i = 0; i < unkeywords.length; i ++){
						if(unkeywords.length > 1){
							if(i == 0){
								titleBuffer.append(" (");
								contentBuffer.append("(");
							}
							titleBuffer.append("title:\""+ unkeywords[i] +"\"");
							contentBuffer.append("content:\""+ unkeywords[i] +"\"");
							if(i !=  unkeywords.length -1){
								titleBuffer.append(" AND ");
								contentBuffer.append(" AND ");
							}
							if(i == unkeywords.length -1){
									titleBuffer.append(" ) ");
									contentBuffer.append(" ) ");
							}
							
						}else{
							titleBuffer.append("(title:\""+ unkeywords[i] +"\")");
							contentBuffer.append("(content:\""+ unkeywords[i] +"\")");
							if(i !=  unkeywords.length -1 && unkeywords.length > 1){
								titleBuffer.append(" OR ");
								contentBuffer.append(" OR ");
							}
						}
					}
			  	 titleBuffer.append(" OR ");
				 contentBuffer.append(" OR ");
			}
			 titleBuffer = titleBuffer.delete(titleBuffer.length()-3, titleBuffer.length());
			 contentBuffer = contentBuffer.delete(contentBuffer.length()-3, contentBuffer.length());
			 allBuffer.append(titleBuffer).append(" OR ").append(contentBuffer);
			 allBuffer.append(" ) ");
		}
	}
}
