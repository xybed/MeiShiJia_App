package com.mumu.meishijia.http;

import android.content.Context;

import com.mumu.meishijia.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * SSL证书
 * Created by 77 on 2018/6/21 0021.
 */

public class MySSLSocketFactory {

    private static final String KEY_STORE_PASSWORD = "77jiajia";
    private static final String KEY_STORE_TRUST_PASSWORD = "77jiajia";

    public static SSLSocketFactory getSocketFactory(Context context){
        InputStream keyInputStream = context.getResources().openRawResource(R.raw.client);
        InputStream trustInputStream = context.getResources().openRawResource(R.raw.trust);
        try{
            SSLContext sslContext = SSLContext.getInstance("TLS");

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(keyInputStream, KEY_STORE_PASSWORD.toCharArray());

            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(trustInputStream, KEY_STORE_TRUST_PASSWORD.toCharArray());

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, KEY_STORE_PASSWORD.toCharArray());

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);

            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            try {
                keyInputStream.close();
                trustInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
