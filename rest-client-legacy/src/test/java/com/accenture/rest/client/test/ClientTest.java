//package com.accenture.rest.client.test;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpResponseException;
//import org.apache.http.client.methods.HttpUriRequest;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.BlockJUnit4ClassRunner;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.accenture.rest.client.model.analytics.Channel;
//import com.accenture.rest.client.model.analytics.Event;
//import com.accenture.rest.client.model.analytics.Headers;
//import com.accenture.rest.client.model.analytics.TagEvent;
//import com.accenture.rest.client.model.analytics.User;
//import com.accenture.rest.client.models.Address;
//import com.accenture.rest.client.models.BillingProfile;
//import com.accenture.rest.client.models.Body;
//import com.accenture.rest.client.models.Contract;
//import com.accenture.rest.client.models.Customer;
//import com.accenture.rest.client.models.Header;
//import com.accenture.rest.client.models.ProfileServiceBilling;
//import com.accenture.rest.legacy.callback.impl.DefaultCallBack;
//import com.accenture.rest.legacy.client.ClientBuild;
//import com.accenture.rest.legacy.exception.ValidationException;
//import com.accenture.rest.legacy.header.RestHeader;
//import com.accenture.rest.legacy.header.impl.DefaultHeader;
//import com.accenture.rest.legacy.map.impl.BasicMapper;
//import com.accenture.rest.legacy.request.impl.BasicRequestMethods;
//import com.accenture.rest.legacy.urlformat.UrlFormat;
//import com.accenture.rest.legacy.urlformat.impl.BasicUrlFormat;
//import com.accenture.rest.legacy.validation.impl.BasicValidation;
//import com.auth0.jwt.JWTSigner;
//
//
//@RunWith(BlockJUnit4ClassRunner.class)
//public class ClientTest {
//	
//	private static Logger LOGGER = LoggerFactory.getLogger(ClientTest.class);
//	
//	private TagEvent createTagEvent() {
//		TagEvent tagEvent = new TagEvent();
//		tagEvent.setHeaders(new Headers());
//		tagEvent.setEvents(new ArrayList<Event>());
//
//		Event event = new Event();
//		event.setHitType("1");
//		event.setCampaignSource("example campaign source");
//		/*event.setCampaignMedium("example campaign medium");*/
//		event.setCampaignName("example campaign name");
//		event.setCampaignContent("example content campaign");
//
//		event.setEventCategory("1");
//		event.setEventAction("1");
//		event.setEventLabel("Acesso ao app");
//		event.setEventValue("PROMO1");
//		event.setEventDate((new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(new Date(System.currentTimeMillis())));
//
//		event.setChannel(new Channel());
//		event.getChannel().setName("1");
//		event.getChannel().setDeviceOS("Android_tst");
//		event.getChannel().setDeviceModel("MotoG3-TE_tst");
//		event.getChannel().setAppVersion("5.8");
//		tagEvent.getEvents().add(event);
//
//		tagEvent.setUser(new User());
//		tagEvent.getUser().setLogin("3791221698");
//		tagEvent.getUser().setLoginType("USER");
//		tagEvent.getUser().setSegment("PRE");
//		tagEvent.getUser().setSubSegment("CONTROLE");
//		tagEvent.getUser().setLineType("PRE-PAID");
//		tagEvent.getUser().setCsLevel("10");
//		tagEvent.getUser().setPaymentResponsible("Y");
//		tagEvent.getUser().setPlanCode("120");
//		tagEvent.getUser().setPlanName("CONTROLE");
//
//		tagEvent.setHeaders(new Headers());
//		tagEvent.getHeaders().setCnField("3791221698");
//		tagEvent.getHeaders().setSchema("bi");
//		tagEvent.getHeaders().setTid("111111111111");
//		tagEvent.getHeaders().setStid("Teste");
//
//		return tagEvent;
//	}
//	
//	public String createJWT(User user) {
//		Map<String, Object> userMap = user.toMap(); 
//		LOGGER.debug("User map : {}",  userMap);
//
//		JWTSigner signer = new JWTSigner("secret");
//		return signer.sign(userMap);
//	}
//	
//	@Test
//	public void make_sendAndRecive_Then_return_not_null() throws ClientProtocolException, IOException, ValidationException{
//		
//		String url = "http://10.43.1.114:9100/v1/analytics/protected/event";
//		
//		final TagEvent tagEvent = createTagEvent();
//		
//		ClientBuild build = ClientBuild.create();
//		BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("weblogic", "Accenture01"));
//		
//		HttpResponse response = (HttpResponse) build.setCredentials(credentialsProvider)
//		.build()
//		.sendAndRecive(BasicRequestMethods.createPostMethod(new RestHeader<HttpUriRequest, HttpUriRequest>() {
//			@Override
//			public HttpUriRequest build(HttpUriRequest request) {
//				
//				request.addHeader("metadata", createJWT(tagEvent.getUser()));
//				for(Entry<String,String> header : tagEvent.getHeaders().toMap().entrySet()){
//					request.addHeader(header.getKey(), header.getValue());
//				}
//				return request;
//			}
//		}, BasicMapper.createMapperObjectToString(), BasicUrlFormat.createBasicUrlFormat(url), tagEvent),
//				DefaultCallBack.create(BasicValidation.basicValidation(new Integer[]{HttpStatus.SC_NO_CONTENT, HttpStatus.SC_OK}), 
//						BasicMapper.createMapperHttpResponseToHttpResponse()));
//		
//		assertThat(response.getStatusLine().getStatusCode(), is(HttpStatus.SC_NO_CONTENT));
//	}
//	
//	@Test
//	public void make_request_To_ProfileServiceBillingProfile_Then_returnObject() throws ClientProtocolException, IOException, ValidationException{
//		
//		String url = "http://10.43.1.68:17010/jsonSimulator/simulator/ProfileServiceBillingProfileGroupBasicBillingProfileNameLastUpdateTimestamp/{0}";
//		String parameter = "test_nao_alterar";
//		
//		ClientBuild build = ClientBuild.create();
//		
//		BasicCredentialsProvider bb = new BasicCredentialsProvider();
//		bb.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("weblogic", "Accenture01"));
//		
//		ProfileServiceBilling actual = (ProfileServiceBilling) build.setCredentials(bb).build()
//			.sendAndRecive(BasicRequestMethods.createGetMethod(DefaultHeader.addHeaders(), BasicUrlFormat.createBasicUrlFormat(url, parameter)),
//					DefaultCallBack.create(BasicValidation.basicValidation(), 
//							BasicMapper.createMapperHttpResponseToObject(ProfileServiceBilling.class)));
//		
//		ProfileServiceBilling expected = new ProfileServiceBilling();
//		expected.setBody(new Body());
//		expected.getBody().setBillingProfile(new BillingProfile());
//		expected.getBody().getBillingProfile().setBillingProfileId("123456");
//		expected.getBody().getBillingProfile().setCsLevel("10");
//		expected.getBody().getBillingProfile().setCustCode("6.780959");
//		expected.getBody().getBillingProfile().setDocument("537854284");
//		expected.getBody().getBillingProfile().setPaymentResp(true);
//		expected.getBody().setCustomer(new Customer());
//		expected.getBody().getCustomer().setAddress(new Address());
//		expected.getBody().getCustomer().getAddress().setLastUpdateTimestamp("1490583600000");
//		expected.getBody().getCustomer().setContract(new Contract());
//		expected.getBody().getCustomer().getContract().setActivationDate("885046623000");
//		expected.getBody().getCustomer().setName("Angelo BERNARDES");
//		expected.setHeader(new Header());
//		expected.getHeader().setMessageId("50db0f7e-4377-11e4-a183-164230d1df67");
//		
//		assertThat(actual, is(expected) );
//	}
//	
//	@Test(expected=ValidationException.class)
//	public void make_request_To_ProfileServiceBillingProfile_with_Invalid_Number_Then_throws_validationException() throws ClientProtocolException, IOException, ValidationException{
//		
//		String url = "http://10.43.1.68:17010/jsonSimulator/simulator/ProfileServiceBillingProfileGroupBasicBillingProfileNameLastUpdateTimestamp/{0}";
//		String parameter = "1060101";
//		
//		ClientBuild build = ClientBuild.create();
//		
//		BasicCredentialsProvider bb = new BasicCredentialsProvider();
//		bb.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("weblogic", "Accenture01"));
//		
//		ProfileServiceBilling actual = (ProfileServiceBilling) build.setCredentials(bb).build()
//			.sendAndRecive(BasicRequestMethods.createGetMethod(DefaultHeader.addHeaders(), BasicUrlFormat.createBasicUrlFormat(url, parameter)),
//					DefaultCallBack.create(BasicValidation.basicValidation(), 
//							BasicMapper.createMapperHttpResponseToObject(ProfileServiceBilling.class)));
//	}
//	
//	@Test
//	public void make_maper_of_tagEvent_with_null_properties() throws HttpResponseException, IOException{
//		TagEvent tagEvent = createTagEvent();
//		tagEvent.getUser().setPlanCode(null);
//		tagEvent.getEvents().get(0).setChannel(null);
//		String jsonString = BasicMapper.createMapperObjectToString().mapping(tagEvent);
//		
//		assertThat(jsonString.contains("planCode"), is(false));
//		assertThat(jsonString.contains("channel"), is(false));
//		System.out.println(jsonString);
//	}
//	
//	@Test
//	public void create_url_using_url_base_with_one_parameter_then_return_a_string_formated(){
//		UrlFormat<String> url = BasicUrlFormat.createBasicUrlFormat("deveria {0} o valor", "substituir");
//		assertThat(url.generate(), is("deveria substituir o valor"));
//	}
//
//
//	
//}
