/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hive.jdbc.jwt;

import org.apache.hive.jdbc.HttpRequestInterceptorBase;
import org.apache.hive.service.auth.HttpAuthUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.client.CookieStore;
import org.apache.http.protocol.HttpContext;

import java.util.Map;

/**
 * This implements the logic to intercept the HTTP requests from the Hive Jdbc connection
 * and adds JWT auth header.
 */
public class HttpJwtAuthRequestInterceptor extends HttpRequestInterceptorBase {

  private final String signedJwt;

  public HttpJwtAuthRequestInterceptor(String signedJwt, CookieStore cookieStore, String cn,
                                       boolean isSSL, Map<String, String> additionalHeaders,
                                       Map<String, String> customCookies) {
    super(cookieStore, cn, isSSL, additionalHeaders, customCookies);
    this.signedJwt = signedJwt;
  }

  @Override
  protected void addHttpAuthHeader(HttpRequest httpRequest, HttpContext httpContext) {
    httpRequest.addHeader(HttpHeaders.AUTHORIZATION, HttpAuthUtils.BEARER + " " + signedJwt);
  }

  @Override
  protected String getAuthType() {
    return "JWT";
  }
}
