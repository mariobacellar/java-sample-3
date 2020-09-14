package br.com.oi.telemar.siebelconspool;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.oi.telemar.config.ChaveSiebel;


public class SiebelConnectorPool
{
  private static final String REINICIANDO_POR_EXCESSO_INVALIDACOES = SiebelLoginSpool.idClass + "Reiniciando por excesso de InvalidaÃ§Ãµes o POOL : ";
  private static final String DESTRUINDO_POOL = SiebelLoginSpool.idClass + "Destruindo o POOL : ";
  private static final String LOGOFF_SESSAO = SiebelLoginSpool.idClass + "LOGOFF da sessÃ£o : ";
  private static final String VAI_LANCAR_EXCECAO = SiebelLoginSpool.idClass + "Vai lanÃ§ar exceÃ§Ã£o";
  private static final String CONSEGUIU_LIBERAR_SESSOES = SiebelLoginSpool.idClass + "Conseguiu liberar sessÃµes";
  private static final String EXPIROU_SESSAO = SiebelLoginSpool.idClass + "Expirou a sessÃ£o: %s";
  private static final String INVALIDANDO_SESSAO = SiebelLoginSpool.idClass + "Invalidando a sessÃ£o: %s";
  private static final String LIBERANDO_A_SESSAO = SiebelLoginSpool.idClass + "Liberando a sessÃ£o: %s";
  private static final String NOVA_SESSAO = SiebelLoginSpool.idClass + "Criando nova sessÃ£o";
  private static final String LIMITE_POOL_ATINGIDO = SiebelLoginSpool.idClass + "Limite mÃ¡ximo do pool atingido";
  private static final String PEGANDO_SESSAO_IDLE = SiebelLoginSpool.idClass + "Pegando a sessÃ£o no Idle";
  private List IdlePool = null;
  private HashMap ActivePool = null;
  private SiebelConnector con;
  private ChaveSiebel Chave;
  private int TotalSession = 0;
  private int MaxSession = 0;
  private int MaxInvalidate = 0;
  private int TotalInvalidate = 0;
  
  public SiebelConnectorPool(ChaveSiebel chave)
  {
    this.con = new SiebelConnector();
    this.con.setChave(chave);
    this.MaxSession = Integer.parseInt(chave.getMaxSessions());
    this.MaxInvalidate = Integer.parseInt(chave.getMaxInvalidates());
    this.IdlePool = new ArrayList();
    this.ActivePool = new HashMap();
    this.Chave = chave;
  }
  
  public String getURL() throws Exception
  {
    return this.con.getURL();
  }
  
  public String getSession(boolean force) throws Exception
  {
    return getSession(null, force);
  }
  
  private String getSession(SiebelSession sessionCriada, boolean force)
    throws Exception
  {
    SiebelSession session = null;
    boolean novaSessao = force;
    
    synchronized (this) {
      if (!novaSessao) {
        if ((sessionCriada != null) && (this.TotalSession < this.MaxSession)) {
          this.TotalSession += 1;
          setActive(sessionCriada);
          
          return sessionCriada.getSession();
        }
        
        if (this.IdlePool.size() > 0) {
          session = getIdleSession();
          setActive(session);


        }
        else if (this.TotalSession >= this.MaxSession) {
          if (liberaExpirados() > 0) {
            session = getIdleSession();
            setActive(session);

          }
          else
          {
            throw new Exception(LIMITE_POOL_ATINGIDO);
          }
        }
        else {
          novaSessao = true;
        }
      }
    }
    




    if (novaSessao)
    {
      session = new SiebelSession(this.con.getSession());
      return getSession(session, false);
    }
    

    return session.getSession();
  }
  

  private SiebelSession getIdleSession()
  {
    SiebelSession session = (SiebelSession)this.IdlePool.get(0);
    this.IdlePool.remove(0);
    return session;
  }
  
  private void setActive(SiebelSession session)
  {
    Calendar cal = Calendar.getInstance();
    cal.add(14, Integer.parseInt(this.Chave.getTimeout()));
    session.setLive(cal);
    this.ActivePool.put(session.getSession(), session);
  }
  
  protected void freeSession(String SessionId) throws Exception
  {
    synchronized (this)
    {
      if (this.ActivePool.containsKey(SessionId)) {
        this.IdlePool.add(this.ActivePool.remove(SessionId));
        this.TotalInvalidate = 0;
      }
    }
  }
  
  protected void invalidateSession(String SessionId) throws Exception
  {
    synchronized (this)
    {
      if (this.ActivePool.remove(SessionId) != null) {
        this.TotalSession -= 1;
        this.con.logOff(SessionId);
        
        if (this.TotalInvalidate >= this.MaxInvalidate)
        {

          this.TotalInvalidate = 0;
          destroy();
        }
        else
        {
          this.TotalInvalidate += 1;
        }
      }
    }
  }
  
  protected void destroy() throws Exception {
    synchronized (this)
    {
      for (Iterator iter = this.IdlePool.iterator(); iter.hasNext();) {
        SiebelSession element = (SiebelSession)iter.next();
        this.con.logOff(element.getSession());
      }
      
      this.TotalSession -= this.IdlePool.size();
      this.IdlePool.clear();
    }
  }
  
  private int liberaExpirados() throws Exception {
    Calendar now = Calendar.getInstance();
    int total = 0;
    HashMap cl = (HashMap)this.ActivePool.clone();
    for (Iterator iter = cl.values().iterator(); iter.hasNext();) {
      SiebelSession element = (SiebelSession)iter.next();
      if (now.after(element.getLive()))
      {
        freeSession(element.getSession());
        total++;
      }
    }
    return total;
  }
  

  protected String getContadores()
  {
    String retorno = String.format("%s,%d,%d,%d,%d", new Object[] { this.Chave.getNomeChave(), Integer.valueOf(this.TotalSession), Integer.valueOf(this.MaxSession), Integer.valueOf(this.IdlePool.size()), Integer.valueOf(this.ActivePool.size()) });
    return retorno;
  }
  
  protected String[] getStatusSessoes() {
    String[] retorno = new String[this.TotalSession];
    int count = 0;
    synchronized (this) {
      for (Iterator iter = this.ActivePool.values().iterator(); iter.hasNext();) {
        SiebelSession element = (SiebelSession)iter.next();
        retorno[(count++)] = String.format("%s,ATIVA", new Object[] { element.Session });
      }
      for (Iterator iter = this.IdlePool.iterator(); iter.hasNext();) {
        SiebelSession element = (SiebelSession)iter.next();
        retorno[(count++)] = String.format("%s,DISPONIVEL", new Object[] { element.Session });
      }
    }
    
    return retorno;
  }
  




  private class SiebelSession
  {
    private String Session = null;
    private Calendar Live = null;
    
    public void setLive(Calendar live) {
      this.Live = live;
    }
    
    public SiebelSession(String session) {
      this.Session = session;
    }
    
    public Calendar getLive() {
      return this.Live;
    }
    
    public String getSession() {
      return this.Session;
    }
  }
}