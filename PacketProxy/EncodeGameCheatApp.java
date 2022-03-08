/*
 * Copyright 2019 DeNA Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package packetproxyplugin;


import packetproxy.encode.EncodeHTTPBase;
import packetproxy.http.Http;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncodeGameCheatApp extends EncodeHTTPBase
{
    public EncodeGameCheatApp(){

    }
    public EncodeGameCheatApp(String ALPN) throws Exception {
        super(ALPN);
    }

    SecretKeySpec sks = null;
    String body = "";
    String maxDamage = "";
    String turn = "";
    String userHp = "";
    String username = "";

    @Override
    protected Http decodeServerResponseHttp(Http http) throws Exception {
        return http;
    }

    @Override
    protected Http encodeServerResponseHttp(Http http) throws Exception {
        return http;
    }

    @Override
    protected Http decodeClientRequestHttp(Http http) throws Exception {
        setData(http);

        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, sks);
        byte[] decMaxDamage = c.doFinal(Base64.getDecoder().decode(maxDamage));
        byte[] decTurn = c.doFinal(Base64.getDecoder().decode(turn));
        byte[] decUserHp = c.doFinal(Base64.getDecoder().decode(userHp));
        byte[] decUserName = c.doFinal(Base64.getDecoder().decode(username));

        String retValue = "{\"maxDamage\":\"" + new String(decMaxDamage) + "\",\"turn\":\"" + new String(decTurn) + "\",\"userHp\":\"" + new String(decUserHp) + "\",\"username\":\"" + new String(decUserName) + "\"}";
        http.setBody(retValue.getBytes(StandardCharsets.UTF_8));
        return http;
    }

    @Override
    protected Http encodeClientRequestHttp(Http http) throws Exception {
        setData(http);

        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, sks);
        byte[] encMaxDamage = Base64.getEncoder().encode(c.doFinal(maxDamage.getBytes(StandardCharsets.UTF_8)));
        byte[] encTurn = Base64.getUrlEncoder().encode(c.doFinal(turn.getBytes(StandardCharsets.UTF_8)));
        byte[] encUserHp = Base64.getUrlEncoder().encode(c.doFinal(userHp.getBytes(StandardCharsets.UTF_8)));
        byte[] encUserName = Base64.getUrlEncoder().encode(c.doFinal(username.getBytes(StandardCharsets.UTF_8)));

        String retValue = "{\"maxDamage\":\"" + new String(encMaxDamage) + "\",\"turn\":\"" + new String(encTurn) + "\",\"userHp\":\"" + new String(encUserHp) + "\",\"username\":\"" + new String(encUserName) + "\"}";
        http.setBody(retValue.getBytes(StandardCharsets.UTF_8));
        return http;
    }

    @Override
    public String getName() {
        return "GameCheatApp";
    }

    private void setData(Http http){
        sks = new SecretKeySpec(Base64.getDecoder().decode("QzhCc2ZBRjN3UEdGaDZkOHllSzR6aFF4WU4zY21uWGQ="), "AES");

        body = new String(http.getBody());
        maxDamage = body.split(",")[0].split(":")[1].replace("\\n", "").replace("\"", "");
        turn = body.split(",")[1].split(":")[1].replace("\\n", "").replace("\"", "");
        userHp = body.split(",")[2].split(":")[1].replace("\\n", "").replace("\"", "");
        username = body.split(",")[3].split(":")[1].replace("\\n", "").replace("\"", "").replace("}", "");
    }
}