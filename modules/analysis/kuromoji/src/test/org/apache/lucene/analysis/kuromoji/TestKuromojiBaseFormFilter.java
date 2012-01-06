package org.apache.lucene.analysis.kuromoji;

/**
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

import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.kuromoji.Tokenizer.Mode;

public class TestKuromojiBaseFormFilter extends BaseTokenStreamTestCase {
  private Analyzer analyzer;

  public void setUp() throws Exception {
    super.setUp();
    final org.apache.lucene.analysis.kuromoji.Tokenizer t = 
        org.apache.lucene.analysis.kuromoji.Tokenizer.builder().mode(Mode.NORMAL).build();
    analyzer = new Analyzer() {

      @Override
      protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        Tokenizer tokenizer = new KuromojiTokenizer(t, reader);
        return new TokenStreamComponents(tokenizer, new KuromojiBaseFormFilter(tokenizer));
      }
    };
  }
  
  public void testBasics() throws IOException {
    assertAnalyzesTo(analyzer, "それはまだ実験段階にあります。",
        new String[] { "それ", "は", "まだ", "実験", "段階", "に", "ある", "ます", "。" }
    );
  }
  
  public void testRandomStrings() throws IOException {
    checkRandomData(random, analyzer, 10000);
  }
}