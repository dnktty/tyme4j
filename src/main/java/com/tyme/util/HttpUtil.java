package com.tyme.util;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HttpUtil {

  /**
   * 发送 HTTP POST 请求
   *
   * @param urlStr 目标 URL
   * @param params 请求参数（键值对形式）
   * @return 服务器响应的字符串
   * @throws Exception 如果请求过程中出现错误
   */
  public static String postRequest(String urlStr, String params) throws Exception {
    URL url = new URL(urlStr);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    // 设置请求方法为 POST
    connection.setRequestMethod("POST");

    // 设置允许输出
    connection.setDoOutput(true);

    // 设置请求头 Content-Type 为 application/x-www-form-urlencoded
    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

    // 写入请求参数
    try (OutputStream outputStream = connection.getOutputStream()) {
      outputStream.write(params.getBytes(StandardCharsets.UTF_8));
    }

    // 获取响应码
    int responseCode = connection.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
      // 读取响应内容
      StringBuilder response = new StringBuilder();
      try (InputStream inputStream = connection.getInputStream()) {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
          response.append(new String(buffer, 0, length, StandardCharsets.UTF_8));
        }
      }
      return response.toString();
    } else {
      throw new RuntimeException("HTTP POST request failed with error code: " + responseCode);
    }
  }

  /**
   * 发送 HTTP GET 请求
   *
   * @param urlStr 目标 URL
   * @return 服务器响应的字符串
   * @throws Exception 如果请求过程中出现错误
   */
  public static String getRequest(String urlStr) throws Exception {
    URL url = new URL(urlStr);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    // 设置请求方法为 GET
    connection.setRequestMethod("GET");

    // 获取响应码
    int responseCode = connection.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
      // 读取响应内容
      StringBuilder response = new StringBuilder();
      try (InputStream inputStream = connection.getInputStream()) {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
          response.append(new String(buffer, 0, length, StandardCharsets.UTF_8));
        }
      }
      return response.toString();
    } else {
      throw new RuntimeException("HTTP GET request failed with error code: " + responseCode);
    }
  }

  /**
   * 发送带有查询参数的 HTTP GET 请求
   *
   * @param baseUrl 基础 URL
   * @param params 查询参数（键值对形式）
   * @return 服务器响应的字符串
   * @throws Exception 如果请求过程中出现错误
   */
  public static String getWithParamsRequest(String baseUrl, String params) throws Exception {
    // 构建带有查询参数的 URL
    String urlStr = baseUrl + "?" + params;
    return getRequest(urlStr);
  }

  /**
   * 发送 HTTP POST 请求
   *
   * @param urlStr 目标 URL
   * @param requestBody 请求体
   * @param contentType 请求体的内容类型（默认为 "application/x-www-form-urlencoded"）
   * @return 服务器响应的字符串
   * @throws Exception 如果请求过程中出现错误
   */
  public static String postRequest(String urlStr, String requestBody, String contentType) {
    try {
      URL url = new URL(urlStr);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();

      // 设置请求方法为 POST
      connection.setRequestMethod("POST");

      // 设置允许输出
      connection.setDoOutput(true);

      // 设置请求头 Content-Type
      connection.setRequestProperty("Content-Type", contentType);

      // 写入请求体
      try (OutputStream outputStream = connection.getOutputStream()) {
        outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
      }

      // 获取响应码
      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        // 读取响应内容
        StringBuilder response = new StringBuilder();
        try (InputStream inputStream = connection.getInputStream()) {
          byte[] buffer = new byte[1024];
          int length;
          while ((length = inputStream.read(buffer)) != -1) {
            response.append(new String(buffer, 0, length, StandardCharsets.UTF_8));
          }
        }
        return response.toString();
      } else {
        throw new RuntimeException("HTTP POST request failed with error code: " + responseCode);
      }
    } catch (Exception e) {
      log.error("request error", e);
    }
    return null;
  }
}
