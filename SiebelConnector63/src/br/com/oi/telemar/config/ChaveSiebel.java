/*    */ package br.com.oi.telemar.config;
/*    */ 
/*    */ public class ChaveSiebel {
/*    */   private String NomeChave;
/*    */   private String URL;
/*    */   private String Server;
/*    */   private String Port;
/*    */   private String SWEExtSource;
/*    */   private String MaxSessions;
/*    */   private String Timeout;
/*    */   private String UserName;
/*    */   private String Password;
/*    */   private String MaxInvalidates;
/*    */   private String ConnectionTimeout;
/*    */   private String ReadTimeout;
/*    */   
/*    */   public String getConnectionTimeout() {
/* 18 */     return this.ConnectionTimeout;
/*    */   }
/*    */   
/* 21 */   public void setConnectionTimeout(String connectionTimeout) { this.ConnectionTimeout = connectionTimeout; }
/*    */   
/*    */   public String getReadTimeout() {
/* 24 */     return this.ReadTimeout;
/*    */   }
/*    */   
/* 27 */   public void setReadTimeout(String readTimeout) { this.ReadTimeout = readTimeout; }
/*    */   
/*    */   public String getMaxInvalidates() {
/* 30 */     return this.MaxInvalidates;
/*    */   }
/*    */   
/* 33 */   public void setMaxInvalidates(String maxInvalidates) { this.MaxInvalidates = maxInvalidates; }
/*    */   
/*    */   public String getMaxSessions() {
/* 36 */     return this.MaxSessions;
/*    */   }
/*    */   
/* 39 */   public void setMaxSessions(String maxSessions) { this.MaxSessions = maxSessions; }
/*    */   
/*    */   public String getNomeChave() {
/* 42 */     return this.NomeChave;
/*    */   }
/*    */   
/* 45 */   public void setNomeChave(String nomeChave) { this.NomeChave = nomeChave; }
/*    */   
/*    */   public String getPassword() {
/* 48 */     return this.Password;
/*    */   }
/*    */   
/* 51 */   public void setPassword(String password) { this.Password = password; }
/*    */   
/*    */   public String getPort() {
/* 54 */     return this.Port;
/*    */   }
/*    */   
/* 57 */   public void setPort(String port) { this.Port = port; }
/*    */   
/*    */   public String getServer() {
/* 60 */     return this.Server;
/*    */   }
/*    */   
/* 63 */   public void setServer(String server) { this.Server = server; }
/*    */   
/*    */   public String getSWEExtSource() {
/* 66 */     return this.SWEExtSource;
/*    */   }
/*    */   
/* 69 */   public void setSWEExtSource(String extSource) { this.SWEExtSource = extSource; }
/*    */   
/*    */   public String getTimeout() {
/* 72 */     return this.Timeout;
/*    */   }
/*    */   
/* 75 */   public void setTimeout(String timeout) { this.Timeout = timeout; }
/*    */   
/*    */   public String getURL() {
/* 78 */     return this.URL;
/*    */   }
/*    */   
/* 81 */   public void setURL(String url) { this.URL = url; }
/*    */   
/*    */   public String getUserName() {
/* 84 */     return this.UserName;
/*    */   }
/*    */   
/* 87 */   public void setUserName(String userName) { this.UserName = userName; }
/*    */ }


/* Location:              C:\JDeveloper\mywork\ServiceBusApplication\SiebelConnector63\JAR\SiebelConnector63.jar!\br\com\oi\telemar\config\ChaveSiebel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */