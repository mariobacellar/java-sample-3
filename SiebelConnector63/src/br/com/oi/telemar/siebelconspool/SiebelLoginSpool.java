package br.com.oi.telemar.siebelconspool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import br.com.oi.telemar.config.ChaveFactory;
import br.com.oi.telemar.config.ChaveSiebel;

public class SiebelLoginSpool
{
  public static String idClass;
  private static final String ERRO_CARREGANDO_ARQUIVO_CONFIGURACAO = "Erro carregando arquivo de configura��o";
  private static final String CRIACAO_DO_SIEBEL_LOGIN_SPOOL = "-------- Cria��o do SiebelLoginSpool --------";
  private static String CHAVE_SIEBEL_NAO_ENCONTRADA = null;
  private static String CRIOU_POOL_PELA_CHAVE = null;
  private static String PEGOU_POOL_PELA_CHAVE = null;
  private static final String CONFIGFILEPATH = "C:\\xml\\SiebelLoginSpool.xml";//"/webaplic/resources/SiebelLoginSpool/SiebelLoginSpool.xml";
  private static SiebelLoginSpool instance = new SiebelLoginSpool();
  private HashMap chavesSiebel;
  
  public static SiebelLoginSpool getInstance()
  {
    return instance;
  }
  
  private HashMap siebelPool = new HashMap();
  private ChaveFactory chavefactory = null;
  
  public SiebelLoginSpool()
  {
    Random randomid = new Random(System.currentTimeMillis());
    idClass = "(" + String.valueOf(randomid.nextInt(999999999)) + ") - ";
    
    CHAVE_SIEBEL_NAO_ENCONTRADA = idClass + "Chave Siebel informada, n�o foi encontrada";
    CRIOU_POOL_PELA_CHAVE = idClass + "Criou pool pela chave: %s";
    PEGOU_POOL_PELA_CHAVE = idClass + "Pegou pool pela chave: %s";
    
    //this.chavefactory = new ChaveFactory("/webaplic/resources/SiebelLoginSpool/SiebelLoginSpool.xml");
    //this.chavefactory = new ChaveFactory("./src/SiebelLoginSpool.xml");this.chavefactory = new ChaveFactory("./src/SiebelLoginSpool.xml");
    this.chavefactory = new ChaveFactory("C:\\xml\\SiebelLoginSpool.xml");
    carregaConfig();
  }
  
  private SiebelConnectorPool getPool(String nomeChave)
    throws Exception
  {
    SiebelConnectorPool retorno = null;
    synchronized (this.siebelPool)
    {
      if (this.siebelPool.containsKey(nomeChave))
      {
        retorno = (SiebelConnectorPool)this.siebelPool.get(nomeChave);
      }
      else if (this.chavesSiebel.containsKey(nomeChave))
      {
        ChaveSiebel cs = (ChaveSiebel)this.chavesSiebel.get(nomeChave);
        SiebelConnectorPool scp = new SiebelConnectorPool(cs);
        this.siebelPool.put(nomeChave, scp);
        retorno = scp;
      }
    }
    return retorno;
  }
  
  private void carregaConfig()
  {
    if (this.chavefactory.isNewer()) {
      try
      {
        this.chavesSiebel = this.chavefactory.retornaChaves();
        HashMap cl = this.siebelPool;
        for (Iterator iter = cl.values().iterator(); iter.hasNext();)
        {
          SiebelConnectorPool element = (SiebelConnectorPool)iter.next();
          element.destroy();
        }
        this.siebelPool = new HashMap();
      }
      catch (Exception e)
      {
        System.out.println(e.getLocalizedMessage());
      }
    }
  }
  
  protected String getSession(String nomeChave, boolean force) throws Exception {
	String session = null;
	carregaConfig();

	if (!this.chavesSiebel.containsKey(nomeChave)) {
	  throw new Exception(CHAVE_SIEBEL_NAO_ENCONTRADA);
	}
	
	session = getPool(nomeChave).getSession(force);
	return session;
  }
  
  protected void setFreeSession(String nomeChave, String SessionId)
    throws Exception
  {
    if (this.chavesSiebel.containsKey(nomeChave)) {
      getPool(nomeChave).freeSession(SessionId);
    }
  }
  
  protected String getURLSession(String nomeChave)
    throws Exception
  {
    if (this.chavesSiebel.containsKey(nomeChave)) {
      return getPool(nomeChave).getURL();
    }
    return null;
  }
  
  protected void setInvalidateSession(String nomeChave, String SessionId)
    throws Exception
  {
    if (this.chavesSiebel.containsKey(nomeChave)) {
      getPool(nomeChave).invalidateSession(SessionId);
    }
  }
  
  protected String[] getActualSessions(String nomeChave)
    throws Exception
  {
    this.chavesSiebel.containsKey(nomeChave);
    
    return getPool(nomeChave).getStatusSessoes();
  }
  
  protected String[] getContadores()
    throws Exception
  {
    carregaConfig();
    String[] retorno = new String[this.siebelPool.size()];
    int c = 0;
    for (Iterator iter = this.siebelPool.values().iterator(); iter.hasNext();)
    {
      SiebelConnectorPool pool = (SiebelConnectorPool)iter.next();
      retorno[(c++)] = pool.getContadores();
    }
    return retorno;
  }
  
  public static String AllocSession(String nomeChave) throws Exception {
    return getInstance().getSession(nomeChave, false);
  }
  
  public static String ForceAllocSession(String nomeChave)
    throws Exception
  {
    return getInstance().getSession(nomeChave, true);
  }
  
  public static String getUrl(String nomeChave)
    throws Exception
  {
    return getInstance().getURLSession(nomeChave);
  }
  
  public static void FreeSession(String nomeChave, String SessionId)
    throws Exception
  {
    getInstance().setFreeSession(nomeChave, SessionId);
  }
  
  public static void InvalidateSession(String nomeChave, String SessionId)
    throws Exception
  {
    getInstance().setInvalidateSession(nomeChave, SessionId);
  }
  
  public static String[] ActualSessions(String nomeChave)
    throws Exception
  {
    return getInstance().getActualSessions(nomeChave);
  }
  
  public static String[] Contadores()
    throws Exception
  {
    return getInstance().getContadores();
  }
}
