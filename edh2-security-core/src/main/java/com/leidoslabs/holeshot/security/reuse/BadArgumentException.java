/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
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
package com.leidoslabs.holeshot.security.reuse;

import java.util.regex.PatternSyntaxException;

/**
 * <p>BadArgumentException class.</p>
 */
public final class BadArgumentException extends PatternSyntaxException {
  private static final long serialVersionUID = 1L;
  
  /**
   * <p>Constructor for BadArgumentException.</p>
   *
   * @param desc a {@link java.lang.String} object.
   * @param badarg a {@link java.lang.String} object.
   * @param index a int.
   */
  public BadArgumentException(String desc, String badarg, int index) {
    super(desc, badarg, index);
  }
}
