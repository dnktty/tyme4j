package com.tyme.ditu;

/**
 * @describe:
 * @author: kenschen
 * @date 2024-08-19
 */
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class GeographyUtil {
  // 高德地图应用key
  public static final String GAODE_KEY = "1fce92a0d553db92b7cc4cbcfc245ed2";

  /**
   * 地址转换为经纬度
   *
   * @param address 地址
   * @param key 高德地图应用key
   * @return 经纬度
   */
  public static Geography getLonAndLat(String address, String key) {
    Geography geo = new Geography();
    try {
      if (key == null) {
        key = GAODE_KEY;
      }
      // 返回输入地址address的经纬度信息, 格式是 经度,纬度
      String queryUrl = "http://restapi.amap.com/v3/geocode/geo?key=" + key + "&address=" + address;
      // 高德接口返回的是JSON格式的字符串
      String queryResult = getResponse(queryUrl);
      JSONObject obj = JSONObject.parseObject(queryResult);
      if (null != obj && obj.get("status").toString().equals("1")) {
        JSONObject jobJSON = obj.getJSONArray("geocodes").getJSONObject(0);
        String location = jobJSON.get("location").toString();
        log.info("经纬度：{}", location);
        String[] lonAndLat = location.split(",");
        if (lonAndLat != null && lonAndLat.length == 2) {
          geo.setLongitude(Double.valueOf(lonAndLat[0]).doubleValue());
          geo.setLatitude(Double.valueOf(lonAndLat[1]).doubleValue());
        }
        return geo;
      } else {
        log.warn("获取经纬度数据失败：{}", queryResult);
        throw new RuntimeException(StringUtils.join("地址转换经纬度失败，错误：", queryResult));
      }
    } catch (UnknownHostException e) {
      log.error("获取经纬度失败: {}", e.getClass());
      // 默认返回长沙经纬度
      geo.setLatitude(28.228304);
      geo.setLongitude(112.938882);
    } catch (Exception e) {
      log.error("获取经纬度失败", e);
    }
    return geo;
  }

  /**
   * 将经纬度getLng， getLat 通过getAMapByLngAndLat方法转换地址
   *
   * @param getLng 经度
   * @param getLat 纬度
   * @param key 高德地图应用key
   * @return 地址名称
   * @throws Exception
   */
  public static String getAMapByLngAndLat(String getLng, String getLat, String key)
      throws Exception {
    if (key == null) {
      key = GAODE_KEY;
    }
    String url;
    try {
      url =
          "http://restapi.amap.com/v3/geocode/regeo?output=JSON&location="
              + getLng
              + ","
              + getLat
              + "&key="
              + key
              + "&radius=0&extensions=base";
      String queryResult = getResponse(url); // 高德接品返回的是JSON格式的字符串
      // 将获取结果转为json数据
      JSONObject obj = JSONObject.parseObject(queryResult);
      log.info("obj为：{}", obj);
      if (obj.get("status").toString().equals("1")) {
        // 如果没有返回-1
        JSONObject regeocode = obj.getJSONObject("regeocode");
        if (regeocode.size() > 0) {
          // 在regeocode中拿到 formatted_address 具体位置
          return regeocode.get("formatted_address").toString();
        } else {
          throw new RuntimeException("未找到相匹配的地址！");
        }
      } else {
        throw new RuntimeException("请求错误！");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "-1";
  }

  /**
   * 根据两个定位点的经纬度算出两点间的距离
   *
   * @param startLonLat 起始经纬度
   * @param endLonLat 结束经纬度（目标经纬度）
   * @param key 高德地图应用key
   * @return 两个定位点之间的距离
   */
  private static long getDistance(String startLonLat, String endLonLat, String key)
      throws Exception {
    if (key == null) {
      key = GAODE_KEY;
    }
    // 返回起始地startAddr与目的地endAddr之间的距离，单位：米
    String queryUrl =
        "http://restapi.amap.com/v3/distance?key="
            + key
            + "&origins="
            + startLonLat
            + "&destination="
            + endLonLat;
    String queryResult = getResponse(queryUrl);
    JSONObject obj = JSONObject.parseObject(queryResult);
    JSONArray ja = obj.getJSONArray("results");
    JSONObject jobO = JSONObject.parseObject(ja.getString(0));
    Long result = Long.parseLong(jobO.get("distance").toString());
    return result;
  }

  /**
   * 发送请求
   *
   * @param serverUrl 请求地址
   */
  private static String getResponse(String serverUrl) throws Exception {
    // 用JAVA发起http请求，并返回json格式的结果
    StringBuffer result = new StringBuffer();
    URL url = new URL(serverUrl);
    URLConnection conn = url.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String line;
    while ((line = in.readLine()) != null) {
      result.append(line);
    }
    in.close();
    return result.toString();
  }
}
