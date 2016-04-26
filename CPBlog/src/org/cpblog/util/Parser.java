/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cpblog.util;

import java.util.ArrayList;
import java.util.List;

import org.cpblog.domain.Blog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import org.kymjs.kjframe.utils.KJLoger;
import org.kymjs.kjframe.utils.SystemTool;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import android.content.Context;
import android.util.Log;
 

/**
 * 解析工具类
 * 
 * @author kymjs
 * 
 */
public class Parser {

	/**
	 * 首页博客列表
	 * 
	 * @param json
	 * @return
	 */
	public static List<Blog> getBlogList(String json) {
		List<Blog> datas = new ArrayList<Blog>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				Blog data = new Blog();
				JSONObject obj = jsonArray.getJSONObject(i);
				data.setId(obj.optString("id", "-1"));
				data.setTitle(obj.optString("title", "无题"));
				data.setUrl(obj.optString("url", "http://blog.kymjs.com/"));
				data.setImageUrl(obj.optString("imageUrl", ""));
				data.setDate(obj.optString("date", "未知时间"));
				data.setIsRecommend(obj.optInt("isRecommend", 0));
				data.setAuthor(obj.optString("author", "佚名"));
				data.setIsAuthor(obj.optInt("isAuthor", 0));
				data.setDescription(obj.optString("description", "暂无简介"));
				datas.add(data);
			}
		} catch (JSONException e) {
			Log.e("kymjs", "getBlogList()解析异常");
		}
		return datas;
	}
	
	
	 public static <T> T xmlToBean(Class<T> type, String xml) {
	        T data = null;
	        try {
	            XStream xStream = new XStream(new DomDriver("UTF-8"));
	            xStream.processAnnotations(type);
	            data = (T) xStream.fromXML(xml);
	        } catch (Exception e) {
	            try {
	                data = type.newInstance();
	            } catch (Exception ee) {
	            } finally {
	                Log.e("kymjs", "xml解析异常");
	            }
	        }
	        return data;
	    }

}
