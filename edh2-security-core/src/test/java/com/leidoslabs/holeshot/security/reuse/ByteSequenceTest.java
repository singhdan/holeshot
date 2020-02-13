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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ByteSequenceTest {

  @Test
  public void testCompareBytes() {
    ByteSequence a = new ArrayByteSequence("a");
    ByteSequence b = new ArrayByteSequence("b");
    ByteSequence abc = new ArrayByteSequence("abc");

    assertLessThan(a, b);
    assertLessThan(a, abc);
    assertLessThan(abc, b);
  }

  private void assertLessThan(ByteSequence lhs, ByteSequence rhs) {
    int result = ByteSequence.compareBytes(lhs, rhs);
    assertTrue(result < 0);
  }

}