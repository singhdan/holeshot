/*
 * Licensed to The Leidos Corporation under one or more contributor license agreements.  
 * See the NOTICE file distributed with this work for additional information regarding copyright ownership.
 * The Leidos Corporation licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.leidoslabs.holeshot.tileserver.jaxrs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import com.google.common.collect.ImmutableMap;
import com.leidoslabs.holeshot.tileserver.utils.HttpRangeHeader;

/**
 * Provider for HttpRangeHeader parameter conversions
 */
@Provider
public class TileserviceParamConverterProvider implements ParamConverterProvider {
   private static final ImmutableMap<Class<?>, ParamConverter<?>> PROVIDERS =
         ImmutableMap.of(HttpRangeHeader.class, new HttpRangeHeaderConverter());
   
   @Override
   public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
      return (ParamConverter<T>)PROVIDERS.get(rawType);
   }
}
