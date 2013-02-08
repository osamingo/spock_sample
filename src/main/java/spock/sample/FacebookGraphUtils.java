package spock.sample;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * facebookのGraph APIにリクエストを送る
 * 
 * @author tonomori_osamu
 */
public class FacebookGraphUtils {

	/** 宛先のURL */
	private static final String FACEBOOK_GRAPH_URL = "https://graph.facebook.com/";
	/** OAuth Exceptionのコード */
	private static final int OAUTH_EXCEPTION_CODE = 803;

	public String getGender(String username) {

		// 引数チェック
		if (StringUtils.isBlank(username)) {
			throw new IllegalArgumentException();
		}

		// GETリクエストの作成
		HttpGet httpGet = new HttpGet(FACEBOOK_GRAPH_URL.concat(username));
		String gender = null;
		try {
			// リクエストを実行し、レスポンス取得
			HttpResponse response = new DefaultHttpClient().execute(httpGet);
			// ステータスコードのチェック
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// レスポンスのJSONをMapに格納する
				@SuppressWarnings("unchecked")
				Map<String, Object> resultJson = new ObjectMapper().readValue(EntityUtils.toString(response.getEntity()), HashMap.class);
				// code値のチェック
				if (resultJson.get("code") == null || (Integer) resultJson.get("code") != OAUTH_EXCEPTION_CODE) {
					// 返却値に性別を挿入
					gender = (String) resultJson.get("gender");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// リクエストを破棄する
			if (httpGet != null) {
				httpGet.abort();
			}
		}
		return gender;
	}
}
