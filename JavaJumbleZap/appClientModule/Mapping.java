import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Mapping {

	private static Map<String, String[]> aMap;
	private static Map<String, List> bMap;
	private static Map<String, List> cMap;
	private static Map<String, List> dMap;
	private static Map<String, List> eMap;
	private static Map<String, List> fMap;
	private static Map<String, List> gMap;
	private static Map<String, List> hMap;
	private static Map<String, String[]> iMap;
	private static Map<String, List> jMap;
	private static Map<String, List> kMap;
	private static Map<String, List> lMap;
	private static Map<String, String[]> mMap;
	private static Map<String, List> nMap;
	private static Map<String, String[]> oMap;
	private static Map<String, List> pMap;
	private static Map<String, List> qMap;
	private static Map<String, String[]> rMap;
	private static Map<String, List> sMap;
	private static Map<String, List> wMap;
	private static Map<String, List> xMap;
	private static Map<String, List> yMap;
	private static Map<String, List> zMap;

	
	private static void init_all() {
		if (aMap==null)	init_a_Map();
		if (mMap==null) init_m_Map();
		if (rMap==null) init_r_Map();
		if (iMap==null) init_i_Map();
		if (oMap==null) init_o_Map();
		
	}
	
	private static void init_a_Map(){
		String[] 
		letras = new String[3];
		letras[0]="@";
		letras[1]="a#";
		letras[2]="A#";
		aMap = new HashMap<String,String[]>();
		aMap.put("a", letras);
		aMap.put("A", letras);
	};

	private static void init_m_Map(){
		String[] 
		letras = new String[4];
		letras[0]="m#";
		letras[1]="M#";
		letras[2]="MM";
		letras[3]="mm";
		mMap = new HashMap<String,String[]>();
		mMap.put("m", letras);
		mMap.put("M", letras);
	};
	
	
	private static void init_r_Map(){
		String[] 
		letras = new String[3];
		letras[0]="r#";
		letras[1]="r#";
		letras[2]="7";
		rMap = new HashMap<String,String[]>();
		rMap.put("r", letras);
		rMap.put("R", letras);
	};

	private static void init_i_Map(){
		String[] 
		letras = new String[3];
		letras[0]="1";
		letras[1]="i#";
		letras[2]="I#";
		iMap = new HashMap<String,String[]>();
		iMap.put("i", letras);
		iMap.put("I", letras);
	};
	
	private static void init_o_Map(){
		String[] 
		letras = new String[3];
		letras[0]="o#";
		letras[1]="O#";
		letras[2]="0";
		oMap = new HashMap<String,String[]>();
		oMap.put("o", letras);
		oMap.put("O", letras);
	};


	private static int genIdexRnd(int max){
		 Random randomGenerator = new Random();
		 int ret = randomGenerator.nextInt(max);
		 return ret;
	 }
	
	 
	private static String convert(char letter) {
		
		String	str_letter	= String.valueOf(letter);
		String	ret			= str_letter;
		int		idx			= 0;
		
		switch (letter) {
		case 'a': case 'A':
			idx 		= genIdexRnd(aMap.size()+1);
			String[] a	= aMap.get(str_letter);
			ret			= a[idx];
			break;
		case 'm': case 'M':
			idx			= genIdexRnd(mMap.size()+1);
			String[] m	= mMap.get(str_letter);
			ret			= m[idx];
			break;
		case 'r': case 'R':
			idx			= genIdexRnd(rMap.size()+1);
			String[] r	= rMap.get(str_letter);
			ret			= r[idx];
			break;
		case 'i': case 'I':
			idx			= genIdexRnd(iMap.size()+1);
			String[] i	= iMap.get(str_letter);
			ret			= i[idx];
			break;
		case 'o': case 'O':
			idx			= genIdexRnd(oMap.size()+1);
			String[] o	= oMap.get(str_letter);
			ret			= o[idx];
			break;
		default:
			break;
		}
		return ret;
	}
	 
	 
	public static String jumble(String str) {
		init_all();
		char[]		inp	= str.toCharArray();
		String[]	out = new String[inp.length];
		
		for (int i = 0; i < inp.length; i++) {
			out[i] = convert( inp[i] );
		}		

		StringBuffer ret = new StringBuffer();

		for (String str1 : out) {
			ret.append(str1);
		}
		return ret.toString();
	}

	public static void main(String[] args) {
		String word1 = "mario";
		String word2 = "Mario";
		String word3 = "MARIO";
		
		System.out.println("- word1=["+word1+"] - word1 jumbled=["+Mapping.jumble( word1 )+"]");
		System.out.println("- word2=["+word2+"] - word1 jumbled=["+Mapping.jumble( word2 )+"]");
		System.out.println("- word3=["+word3+"] - word1 jumbled=["+Mapping.jumble( word3 )+"]");
		
		
	}

}