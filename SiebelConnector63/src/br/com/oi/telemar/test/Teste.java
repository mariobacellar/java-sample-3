package br.com.oi.telemar.test;
 
import java.util.Calendar;

import br.com.oi.telemar.siebelconspool.SiebelLoginSpool;

public class Teste {

	public static void log(String s) {
		System.out.println(s);
	}
	
	public static void main(String[] args) {

		String nomeChave= "";
		String cookie = "";
		String url="";
		try {
			log("-> main");
					
			nomeChave = "ConfigSiebelExposicaoServicos";
			log("nomeChave=["+nomeChave+"]");
			
			cookie = SiebelLoginSpool.AllocSession(nomeChave);
			log("cookie=["+cookie+"]");
			
			url= SiebelLoginSpool.getUrl(nomeChave);
			log("url=["+url+"]");
			
			SiebelLoginSpool.FreeSession(nomeChave, cookie);
			log("FreeSession=["+nomeChave+"] ["+cookie+"]");
			
			/*
			Calendar cal = Calendar.getInstance();
			String srtdate1 = cal.getTime().toString();
			
		    cal.add(14, 30000 );
			String srtdate2 = cal.getTime().toString();

			log("srtdate1 =["+srtdate1+"]");
			log("srtdate2 =["+srtdate2+"]");
*/
			
			log("<- main");
			
		} catch (Exception e) {
			log("### Main ###");
			e.printStackTrace();
			
			try {
				SiebelLoginSpool.InvalidateSession(nomeChave, cookie);
			} catch (Exception e1) {
				log("### InvalidateSession ###");
				e1.printStackTrace();
			}
			
		}
		
		
	
	}

}
