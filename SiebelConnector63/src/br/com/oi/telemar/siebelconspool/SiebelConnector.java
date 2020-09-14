package br.com.oi.telemar.siebelconspool;

import br.com.oi.telemar.config.ChaveSiebel;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;



public class SiebelConnector
{
  private static final String OCORREU_ERRO_500_NO_SIEBEL_NA_HORA_DE_LOGIN = "Ocorreu erro 500 no Siebel na hora de login";
  private static final String OCORREU_ERRO_500_NO_SIEBEL_NA_HORA_DE_LOGOFF = "Ocorreu erro 500 no Siebel na hora de logoff";
  private static final String POST = "POST";
  private static final String SET_COOKIE = "Set-Cookie";
  private static final String COOKIE = "Cookie";
  private static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
  private static final String CONTENT_TYPE = "Content-Type";
  private static final String CONTENT_LENGTH = "Content-Length";
  private static final String DES_CONECTANDO_SIEBEL = SiebelLoginSpool.idClass + "DesConectando Siebel - ";
  private static final String CONECTANDO_SIEBEL = SiebelLoginSpool.idClass + "Conectando Siebel - ";
  private static final String LOGIN = "LOGIN";
  private static final String LOGOFF = "LOGOFF";
  private static final String MSGENVIO = "SWEExtSource=%s&SWEExtCmd=ExecuteLogin&UserName=%s&Password=%s";
  private static final String MSGLOGOFF = "SWEExtSource=%s&SWEExtCmd=Logoff&UserName=%s&Password=%s";
  private static final String URLFORMAT = "http://%s:%s%s";
  private static final String MSGLOG = SiebelLoginSpool.idClass + "Conseguiu alocar a sessão: %s \n";
  private static final String MSGLOG2 = SiebelLoginSpool.idClass + "Server: %s  |  Porta: %s \n";
  
  private ChaveSiebel chave;
  
  protected String getSession()
    throws Exception
  {
    HttpURLConnection conn = getConnection();
    String envio = String.format("SWEExtSource=%s&SWEExtCmd=ExecuteLogin&UserName=%s&Password=%s", new Object[] { this.chave.getSWEExtSource(), this.chave.getUserName(), this.chave.getPassword() });
    conn.setRequestProperty("Content-Length", String.valueOf(envio.length()));
    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
    wr.write(envio);
    wr.flush();

    if (conn.getResponseCode() != 200)
    {

      wr.close();
      conn.disconnect();
      
      throw new Exception("Ocorreu erro 500 no Siebel na hora de login : " + conn.getResponseCode());
    }
    String strSession = conn.getHeaderField("Set-Cookie");
    
    wr.close();
    conn.disconnect();
    return strSession;
  }
  
  protected void logOff(String Session)
    throws Exception
  {
    HttpURLConnection conn = getConnection();
    String envio = String.format("SWEExtSource=%s&SWEExtCmd=Logoff&UserName=%s&Password=%s", new Object[] { this.chave.getSWEExtSource(), this.chave.getUserName(), this.chave.getPassword() });
    conn.setRequestProperty("Content-Length", String.valueOf(envio.length()));
    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    conn.setRequestProperty("Cookie", Session);
    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
    wr.write(envio);
    wr.flush();
    wr.close();
    conn.disconnect();
  }
  

  private HttpURLConnection getConnection()
    throws Exception
  {
    URLConnection conn = null;
    HttpURLConnection connection = null;
    try {
      URL url = new URL(String.format("http://%s:%s%s", new Object[] { this.chave.getServer(), this.chave.getPort(), this.chave.getURL() }));
      
      conn = url.openConnection();
      connection = (HttpURLConnection)conn;
      connection.setConnectTimeout(Integer.parseInt(this.chave.getConnectionTimeout()));
      connection.setReadTimeout(Integer.parseInt(this.chave.getReadTimeout()));
      connection.setDoOutput(true);
      connection.setDoInput(true);
      connection.setRequestMethod("POST");
    }
    catch (Exception e) {
      throw e;
    }
    return connection;
  }
  
  public String getURL() throws Exception
  {
    return String.format("http://%s:%s%s", new Object[] { this.chave.getServer(), this.chave.getPort(), this.chave.getURL() });
  }
  

  protected void setChave(ChaveSiebel chave)
  {
    this.chave = chave;
  }
}