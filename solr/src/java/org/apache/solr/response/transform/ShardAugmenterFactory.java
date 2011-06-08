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
package org.apache.solr.response.transform;

import java.util.Map;

import org.apache.solr.common.params.ShardParams;
import org.apache.solr.request.SolrQueryRequest;


/**
 * @version $Id$
 * @since solr 4.0
 */
public class ShardAugmenterFactory extends TransformerFactory
{
  @Override
  public DocTransformer create(String field, Map<String,String> args, SolrQueryRequest req) {
    String v = req.getParams().get(ShardParams.SHARD_URL);
    if( v == null ) {
      if( req.getParams().getBool(ShardParams.IS_SHARD, false) ) {
        v = "[unknown]";
      }
      else {
        v = "[not a shard request]";
      }
    }
    return new ValueAugmenter( field, v );
  }
}

