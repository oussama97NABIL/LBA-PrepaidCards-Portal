package com.LBA.tools.connection;

import android.net.http.X509TrustManagerExtensions;
import android.util.Base64;
import android.util.Log;

import com.LBA.MainActivity;
import com.LBA.prepaidPortal.activity.AbstractActivity;
import com.LBA.tools.assets.Globals;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;




/**
 * Created by amine.wahbi on 17/8/2015.
 */
public class HTTPClient extends AbstractActivity {

    ///hajer 20/06/2022 start
    private static void validatePinning(
            X509TrustManagerExtensions trustManagerExt,
            HttpsURLConnection conn, Set<String> validPins)
            throws SSLException {
        String certChainMsg = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            List<X509Certificate> trustedChain =
                    trustedChain(trustManagerExt, conn);
            for (X509Certificate cert : trustedChain) {
                byte[] publicKey = cert.getPublicKey().getEncoded();
                md.update(publicKey, 0, publicKey.length);
                String pin = Base64.encodeToString(md.digest(),
                        Base64.NO_WRAP);
                certChainMsg += "    sha256/" + pin + " : " +
                        cert.getSubjectDN().toString() + "\n";
                if (validPins.contains(pin)) {
                    return;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new SSLException(e);
        }
        throw new SSLPeerUnverifiedException("Certificate pinning " +
                "failure\n  Peer certificate chain:\n" + certChainMsg);
    }
    private static List<X509Certificate> trustedChain(
            X509TrustManagerExtensions trustManagerExt,
            HttpsURLConnection conn) throws SSLException {
        Certificate[] serverCerts = conn.getServerCertificates();
        X509Certificate[] untrustedCerts = Arrays.copyOf(serverCerts,
                serverCerts.length, X509Certificate[].class);
        String host = conn.getURL().getHost();
        try {
            return trustManagerExt.checkServerTrusted(untrustedCerts,
                    "RSA", host);
        } catch (CertificateException e) {
            throw new SSLException(e);
        }
    }
    //hajer 20/06/2022 end

 static private SSLContext trustOurCert() throws Exception{
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
           //3/28/2023 InputStream caInput = new BufferedInputStream(MainActivity.context.getAssets().open("eps/jboss_eps.cer"));
            Certificate ca;
// 3/28/2023           try {
//                ca = cf.generateCertificate(caInput);
//                Log.d("", "ca=" + ((X509Certificate) ca).getSubjectDN(), null);
//            } finally {
//                caInput.close();
//            }
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
 //3/28/2023           keyStore.setCertificateEntry("ca", ca);
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);
            return context;
        }catch(Exception e){
            throw e;
        }
    }


    static public HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
         //younes 06/05/2021   sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 8443));
            registry.register(new Scheme("https", sf, 9443));
            registry.register(new Scheme("https", sf, 443));
            //registry.register(new Scheme("https", sf, 443));
            // registry.register(new Scheme("https", new EasySSLSocketFactory(), 443));
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    /*hajer 20/06/2022
    static public HttpClient getProdHttpClient() throws Exception{
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
        HttpClient client = new DefaultHttpClient(ccm, params);
        return client;
    }
*/
    //hajer 20/06/2022
    static public HttpClient getProdHttpClient() throws Exception{

        //hajer 05/07/2021 start
        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(
                        TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init((KeyStore) null);
        X509TrustManager x509TrustManager = null;
        for (TrustManager trustManager : trustManagerFactory.getTrustManagers()) {
            if (trustManager instanceof X509TrustManager) {
                x509TrustManager = (X509TrustManager) trustManager;
                break;
            }
        }
        X509TrustManagerExtensions trustManagerExt =
                new X509TrustManagerExtensions(x509TrustManager);

        URL url = new URL("https://ibank.cbg.com.gh/");
        HttpsURLConnection urlConnection =
                (HttpsURLConnection) url.openConnection();
        urlConnection.connect();
        Set<String> validPins = Collections.singleton
                //QIZjxHTr9U8i0d88GrX6hld7ZG2YZYCLnLpLr0wdUjQ=
                        ("/4T3H91XyhcjNr5yEfu5kte8+E3tPW0y30Fs+yQhWuA=");
        validatePinning(trustManagerExt, urlConnection, validPins);

        //hajer 05/07/2021 end
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
        HttpClient client = new DefaultHttpClient(ccm, params);
        return client;
    }
    //hajer 20/06/2022

    static public JSONObject  sendPostJSON(String service, JSONObject jsonObject) throws Exception {
        int statusCode=-1;
        // CloseableHttpClient httpclient = null;
        HttpClient httpClient = null;
        Globals.transactionId = null;
        try {
    //swap here between Test version and live version  with comment and uncomment even one
          //hajer 20/06/2022
            httpClient = getNewHttpClient();  //for test version
            //httpClient = getProdHttpClient(); //for live version


            final String url = Globals.serverURL+service;
            HttpPost httpPost = new HttpPost(url);
            Log.d("url",url);
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("User-Agent", "Android cbg-MB("+ Globals.appVersionName+"."+Globals.appVersionCode+") ["+System.getProperty("http.agent")+"]");
            StringEntity se = new StringEntity(jsonObject.toString());
            httpPost.setEntity(se);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                // throw new Exception("sendPostJSON() failed" + httpResponse.getStatusLine().getStatusCode());
                throw new Exception("SERVER ERROR "+httpResponse.getStatusLine().getStatusCode()+". TRY LATER");
            }
            else{
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();
                JSONObject jsonRespObject = new JSONObject(response.toString());


                return jsonRespObject;
            }
        } catch(Exception e){
            Log.d("IB","getNewHttpClient",e);
            e.printStackTrace();
            throw new Exception("COMMUNICATION ERROR. TRY LATER");
        }/* finally {
            // younes
            if(httpClient.getConnectionManager()!=null)
                //httpclient.close();
             httpClient.getConnectionManager().shutdown();

}*/
        finally {
            /**
             if(httpclient!=null)
             httpclient.close();
             **/

        }
    }
    static public JSONObject  sendPostJSONcardDetail(String service, JSONObject jsonObject) throws Exception {
        int statusCode=-1;
        // CloseableHttpClient httpclient = null;
        HttpClient httpClient = null;
        Globals.transactionId = null;
        try {
            //swap here between Test version and live version  with comment and uncomment even one
            //hajer 20/06/2022
            httpClient = getNewHttpClient();  //for test version
            //httpClient = getProdHttpClient(); //for live version


            final String url = Globals.serverURL+service;
            final String AUTH_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODA2MjUzNzksImp0aSI6IjQyODE5OTMxMDg3NzU4MzAiLCJ1c2VyTWFuYWdlbWVudCI6eyJ1c2VyQ29kZSI6IjQyODE5OTMxMDg3NzU4MzAiLCJicmFuY2giOiI1NjAwMSIsImJhbmsiOiIyMDA0OCIsInVzZXJOYW1lIjoiU0FJRE9VIFNPVyIsInVzZXJUeXBlIjoiSSIsInVzZXJQYXNzd29yZCI6ImE5NTg3YzI1YTM3MDg3ZGNiNWJjZGNhNDQwNDRhNDk5IiwibnVtYmVyT2ZUcmllcyI6MCwibnVtYmVyT2ZUcmllc0FsbG93ZWQiOjMsImNvbm5lY3RlZCI6IlkiLCJmaXJzdENvbm5lY3Rpb24iOiJOIiwibmJyZVNlc3Npb25BbGxvd2VkIjowLCJuYnJlU2Vlc2lvbkNvbm5lY3RlZCI6MCwibGVuZ3RoUGFzc3dvcmQiOm51bGwsImNvbXBsZXhpdHlGbGFnIjpudWxsLCJleHBpcmF0aW9uUGFzc3dvcmQiOjAsImRhdGVTdGFydFBhc3MiOm51bGwsImRhdGVFbmRQYXNzIjpudWxsLCJibG9ja0FjY2VzcyI6Ik4iLCJsYW5ndWFnZUNvZGUiOm51bGwsInNlc3Npb25JZCI6bnVsbCwicmVxdWVzdEZvcmdvdFB3ZCI6bnVsbH0sImlhdCI6MTY4MDYyNDc3OX0.VNHfF2lO63m1knXfW_St81ho-_99rNJVsakuLwLQaKE";

            final String AUTH_VALUE = Globals.authenToken;
            HttpPost httpPost = new HttpPost(url);
            Log.d("url",url);
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("User-Agent", "Android cbg-MB("+ Globals.appVersionName+"."+Globals.appVersionCode+") ["+System.getProperty("http.agent")+"]");
            httpPost.addHeader(AUTH_KEY, AUTH_VALUE);
            StringEntity se = new StringEntity(jsonObject.toString());
            httpPost.setEntity(se);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                // throw new Exception("sendPostJSON() failed" + httpResponse.getStatusLine().getStatusCode());
                throw new Exception("SERVER ERROR "+httpResponse.getStatusLine().getStatusCode()+". TRY LATER");
            }
            else{
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();
                JSONObject jsonRespObject = new JSONObject(response.toString());


                return jsonRespObject;
            }
        } catch(Exception e){
            Log.d("IB","getNewHttpClient",e);
            e.printStackTrace();
            throw new Exception("COMMUNICATION ERROR. TRY LATER");
        }/* finally {
            // younes
            if(httpClient.getConnectionManager()!=null)
                //httpclient.close();
             httpClient.getConnectionManager().shutdown();

}*/
        finally {
            /**
             if(httpclient!=null)
             httpclient.close();
             **/

        }
    }
    static public JSONObject  sendPostJSONaccountToCard(String service, JSONObject jsonObject) throws Exception {
        int statusCode=-1;
        // CloseableHttpClient httpclient = null;
        HttpClient httpClient = null;
        Globals.transactionId = null;
        try {
            //swap here between Test version and live version  with comment and uncomment even one
            //hajer 20/06/2022
            httpClient = getNewHttpClient();  //for test version
            //httpClient = getProdHttpClient(); //for live version


            final String url = Globals.serverURL+service;
            final String AUTH_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODA1MzU0NjEsImp0aSI6IjQyODE5OTMxMDg3NzU4MzAiLCJ1c2VyTWFuYWdlbWVudCI6eyJ1c2VyQ29kZSI6IjQyODE5OTMxMDg3NzU4MzAiLCJicmFuY2giOiI1NjAwMSIsImJhbmsiOiIyMDA0OCIsInVzZXJOYW1lIjoiU0FJRE9VIFNPVyIsInVzZXJUeXBlIjoiSSIsInVzZXJQYXNzd29yZCI6ImE5NTg3YzI1YTM3MDg3ZGNiNWJjZGNhNDQwNDRhNDk5IiwibnVtYmVyT2ZUcmllcyI6MCwibnVtYmVyT2ZUcmllc0FsbG93ZWQiOjMsImNvbm5lY3RlZCI6IlkiLCJmaXJzdENvbm5lY3Rpb24iOiJOIiwibmJyZVNlc3Npb25BbGxvd2VkIjowLCJuYnJlU2Vlc2lvbkNvbm5lY3RlZCI6MCwibGVuZ3RoUGFzc3dvcmQiOm51bGwsImNvbXBsZXhpdHlGbGFnIjpudWxsLCJleHBpcmF0aW9uUGFzc3dvcmQiOjAsImRhdGVTdGFydFBhc3MiOm51bGwsImRhdGVFbmRQYXNzIjpudWxsLCJibG9ja0FjY2VzcyI6Ik4iLCJsYW5ndWFnZUNvZGUiOm51bGwsInNlc3Npb25JZCI6bnVsbCwicmVxdWVzdEZvcmdvdFB3ZCI6bnVsbH0sImlhdCI6MTY4MDUzNDg2MX0.2MxbmNKFazrFgvjVemmH4RIMQTPIbqqTXW69GsU62g4";

            final String AUTH_VALUE = Globals.authenToken;
            HttpPost httpPost = new HttpPost(url);
            Log.d("url",url);
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("User-Agent", "Android cbg-MB("+ Globals.appVersionName+"."+Globals.appVersionCode+") ["+System.getProperty("http.agent")+"]");
            httpPost.addHeader(AUTH_KEY, AUTH_VALUE);
            StringEntity se = new StringEntity(jsonObject.toString());
            httpPost.setEntity(se);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                // throw new Exception("sendPostJSON() failed" + httpResponse.getStatusLine().getStatusCode());
                throw new Exception("SERVER ERROR "+httpResponse.getStatusLine().getStatusCode()+". TRY LATER");
            }
            else{
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();
                JSONObject jsonRespObject = new JSONObject(response.toString());


                return jsonRespObject;
            }
        } catch(Exception e){
            Log.d("IB","getNewHttpClient",e);
            e.printStackTrace();
            throw new Exception("COMMUNICATION ERROR. TRY LATER");
        }/* finally {
            // younes
            if(httpClient.getConnectionManager()!=null)
                //httpclient.close();
             httpClient.getConnectionManager().shutdown();

}*/
        finally {
            /**
             if(httpclient!=null)
             httpclient.close();
             **/

        }
    }
    static public JSONObject  sendPostJSONLastTransactions(String service, JSONObject jsonObject) throws Exception {
        int statusCode=-1;
        // CloseableHttpClient httpclient = null;
        HttpClient httpClient = null;
        Globals.transactionId = null;
        try {
            //swap here between Test version and live version  with comment and uncomment even one
            //hajer 20/06/2022
            httpClient = getNewHttpClient();  //for test version
            //httpClient = getProdHttpClient(); //for live version


            final String url = Globals.serverURL+service;
            final String AUTH_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODA1MzU0NjEsImp0aSI6IjQyODE5OTMxMDg3NzU4MzAiLCJ1c2VyTWFuYWdlbWVudCI6eyJ1c2VyQ29kZSI6IjQyODE5OTMxMDg3NzU4MzAiLCJicmFuY2giOiI1NjAwMSIsImJhbmsiOiIyMDA0OCIsInVzZXJOYW1lIjoiU0FJRE9VIFNPVyIsInVzZXJUeXBlIjoiSSIsInVzZXJQYXNzd29yZCI6ImE5NTg3YzI1YTM3MDg3ZGNiNWJjZGNhNDQwNDRhNDk5IiwibnVtYmVyT2ZUcmllcyI6MCwibnVtYmVyT2ZUcmllc0FsbG93ZWQiOjMsImNvbm5lY3RlZCI6IlkiLCJmaXJzdENvbm5lY3Rpb24iOiJOIiwibmJyZVNlc3Npb25BbGxvd2VkIjowLCJuYnJlU2Vlc2lvbkNvbm5lY3RlZCI6MCwibGVuZ3RoUGFzc3dvcmQiOm51bGwsImNvbXBsZXhpdHlGbGFnIjpudWxsLCJleHBpcmF0aW9uUGFzc3dvcmQiOjAsImRhdGVTdGFydFBhc3MiOm51bGwsImRhdGVFbmRQYXNzIjpudWxsLCJibG9ja0FjY2VzcyI6Ik4iLCJsYW5ndWFnZUNvZGUiOm51bGwsInNlc3Npb25JZCI6bnVsbCwicmVxdWVzdEZvcmdvdFB3ZCI6bnVsbH0sImlhdCI6MTY4MDUzNDg2MX0.2MxbmNKFazrFgvjVemmH4RIMQTPIbqqTXW69GsU62g4";

            final String AUTH_VALUE = Globals.authenToken;
            HttpPost httpPost = new HttpPost(url);
            Log.d("url",url);
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("User-Agent", "Android cbg-MB("+ Globals.appVersionName+"."+Globals.appVersionCode+") ["+System.getProperty("http.agent")+"]");
            httpPost.addHeader(AUTH_KEY, AUTH_VALUE);
            StringEntity se = new StringEntity(jsonObject.toString());
            httpPost.setEntity(se);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                // throw new Exception("sendPostJSON() failed" + httpResponse.getStatusLine().getStatusCode());
                throw new Exception("SERVER ERROR "+httpResponse.getStatusLine().getStatusCode()+". TRY LATER");
            }
            else{
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();
                JSONObject jsonRespObject = new JSONObject(response.toString());


                return jsonRespObject;
            }
        } catch(Exception e){
            Log.d("IB","getNewHttpClient",e);
            e.printStackTrace();
            throw new Exception("COMMUNICATION ERROR. TRY LATER");
        }/* finally {
            // younes
            if(httpClient.getConnectionManager()!=null)
                //httpclient.close();
             httpClient.getConnectionManager().shutdown();

}*/
        finally {
            /**
             if(httpclient!=null)
             httpclient.close();
             **/

        }
    }



            }
