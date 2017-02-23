/*
 * MIT License
 *
 * Copyright (c) 2016 BotMill.io
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package co.aurasphere.botmill.core.internal.util.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class that contains methods that allows BotMill to communicate through the
 * network.
 * 
 * @author Donato Rimenti
 * 
 */
public class NetworkUtils {

	/**
	 * The logger.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(NetworkUtils.class);

	/**
	 * Sends a request.
	 * 
	 * @param request
	 *            the request to send
	 * @return response the response.
	 */
	private static BotMillNetworkResponse send(HttpRequestBase request) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		logger.debug("HTTP request: {}", request.getRequestLine().toString());
		HttpResponse httpResponse = null;
		String responseContent = null;
		BotMillNetworkResponse response = new BotMillNetworkResponse();

		try {

			httpResponse = httpClient.execute(request);
			responseContent = getResponseContent(httpResponse);

			// Does some logging.
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			logger.debug("HTTP Status Code: {}", statusCode);
			// If the status code is > 400 there was an error.
			if (statusCode >= 400) {
				logger.error("HTTP connection failed with error code {}.",
						statusCode);
				response.setError(true);
			}

		} catch (Exception e) {
			logger.error("Error during HTTP connection: ", e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.error("Error while closing HTTP connection: ", e);
			}
		}

		response.setResponse(responseContent);
		return response;
	}

	/**
	 * Utility method that converts an HttpResponse to a String.
	 *
	 * @param response
	 *            the response to convert.
	 * @return a String with the response content.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static String getResponseContent(HttpResponse response)
			throws IOException {
		InputStream inputStream = response.getEntity().getContent();
		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				inputStream);
		InputStreamReader inputStreamReader = new InputStreamReader(
				bufferedInputStream);
		BufferedReader br = new BufferedReader(inputStreamReader);
		StringBuilder builder = new StringBuilder();
		String output = null;
		while ((output = br.readLine()) != null) {
			builder.append(output);
		}
		String responseContent = builder.toString();
		logger.debug("Raw response: {}", responseContent);
		return responseContent;
	}

	/**
	 * Utility to send a POST request.
	 * 
	 * @param url
	 *            the url we need to send the post request to.
	 * @param entity
	 *            the entity that containts the object we need to pass as part
	 *            of the post request.
	 * @return the post response.
	 */
	public static BotMillNetworkResponse post(String url, StringEntity entity) {
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		post.setEntity(entity);
		return send(post);
	}

	/**
	 * Utility to send a GET request.
	 * 
	 * @param url
	 *            the url we need to send the get request to.
	 * @return the get response.
	 */
	public static BotMillNetworkResponse get(String url) {
		HttpGet get = new HttpGet(url);
		return send(get);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NetworkUtils []";
	}

}
