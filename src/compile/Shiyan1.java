package compile;
import java.util.ArrayList;
import java.util.Scanner;

public class Shiyan1 {
	private int state=0;
	private ArrayList tokens;
	private ArrayList<Integer> numbers;
	private String value="";
	private int result;
	private  static int pos=0;
	private static int line=0;
	private final static String[] KEY={"if","while","do","break","then","else","end","repeat","until","read","write"};
	/*public static void main(String[] args){
		Shiyan1 compiler=new Shiyan1();
		Scanner sc=new Scanner(System.in);
		sc.useDelimiter("#");
		String s=sc.next();
		String r;
		while(pos<s.length()){
			r=compiler.GetToken(s);
			if(!r.equals("")){
				System.out.println(r);
			}
		}
		
	}*/
	public void init(){
		state=0;
		value="";
		pos=0;
		line=0;
		tokens=new ArrayList();
		numbers=new ArrayList<Integer>();
	}
	public String Compile(String s){
		String r;
		String result="";
		init();
		
		while(pos<s.length()){
			r=GetToken(s);
			if(!r.equals("")){
				result+=r;
				result+='\n';
			}
		}	
		
		return result;
		
	}
	public ArrayList SendTokens(){
		return tokens;
	}
	public ArrayList SendNumbers(){
		return numbers;
	}
	public String GetToken(String s){
	state=0;
	value="";
	//int result;
	
	char []c=s.toCharArray();
	while(true){
		
		switch(state){
		case 0:
			switch(c[pos]){
			case 'a':case 'b':case 'c':case 'd':case 'e':case 'f':case 'g'
			:case 'h':case 'i':case 'j':case 'k':case 'l':case 'm':case 'n'
			:case 'o':case 'p':case 'q':case 'r':case 's':case 't':case 'u'
			:case 'v':case 'w':case 'x':case 'y':case 'z':{
				state=1;
				break;
			}
			case '0':case '1':case '2':case '3':case '4':case '5':case '6':
			case '7':case '8':case '9':{
				state=3;
				break;
			}
			case '+':{
				state=5;
				break;
			}
			case '-':{
				state=6;
				break;
			}
			case '*':{
				state=7;
				break;
			}
			case '/':{
				state=8;
				break;
			}
			case '=':{
				state=9;
				break;
			}
			case '<':{
				state=10;
				break;
			}
			case '>':{
				state=11;
				break;
			}
			case '{':{
				state=12;
				break;
			}
			case '}':{
				state=13;
				break;
			}
			case ';':{
				state=14;
				break;
			}
			case '(':{
				state=15;
				break;
			}
			case ')':{
				state=16;
				break;
			}
			case ' ':{
				state=17;
				break;
			}
			case '\n':{
				state=17;
				line++;
				break;
			}
			case '\r':{
				state=17;
				break;
			}
			case '\t':{
				state=17;
				break;
			}
			default:state=18;
			break;
			}
		break;
		case 1:{
			while(pos<s.length()&&(isLetter(c[pos])||isNumber(c[pos]))){
				value+=c[pos];
				pos++;
				//String s
			}
			state=2;			
			//break;
		}
		case 2:{
			result=2;
			checkKey(value);
			tokens.add(result);
			return "line"+line+" "+result+","+value;
			//break;
		}
		case 3:{
			while(pos<s.length()&&isNumber(c[pos])){
				value+=c[pos];
				pos++;
			}
			state=4;
			//break;
		}
		case 4:{
			result=4;
			tokens.add(result);
			numbers.add(Integer.parseInt(value));
			return "line"+line+" "+result+","+value;
			//break;
		}
		case 5:{
			result=5;
			
			value=c[pos]+"";
			pos++;
			tokens.add(result);
			return "line"+line+" "+result+","+value;
			//break;
		}
		case 6:{
			result=6;
			
			value=c[pos]+"";
			pos++;
			tokens.add(result);
			return "line"+line+" "+result+","+value;
			//break;
		}
		case 7:{
			result=7;
			
			value=c[pos]+"";
			pos++;
			tokens.add(result);
			return "line"+line+" "+result+","+value;
			//break;
		}
		case 8:{	
			if(c[pos+1]=='/'){
				pos++;
				while(c[pos]!='\n'){
					pos++;
				}
				return "";
			}else{
				result=8;
				value=c[pos]+"";
				pos++;
				tokens.add(result);
				return "line"+line+" "+result+","+value; 
			}		
			//break;
		}
		case 9:{
			result=9;
			
			value=c[pos]+"";
			pos++;
			tokens.add(result);
			return "line"+line+" "+result+","+value;
			//break;
		}
		case 10:{
			result=10;
			
			value=c[pos]+"";
			pos++;
			tokens.add(result);
			return "line"+line+" "+result+","+value;
			//break;
		}
		case 11:{
			result=11;
			
			value=c[pos]+"";
			pos++;
			tokens.add(result);
			return "line"+line+" "+result+","+value;
			//break;
		}
		case 12:{
			result=12;
			
			value=c[pos]+"";
			pos++;
			tokens.add(result);
			return "line"+line+" "+result+","+value;
			//break;
		}
		case 13:{
			result=13;
			
			value=c[pos]+"";
			pos++;
			tokens.add(result);
			return "line"+line+" "+result+","+value;
			//break;
		}
		case 14:{
			result=14;
			
			value=c[pos]+"";
			pos++;
			tokens.add(result);
			return "line"+line+" "+result+","+value;
			//break;
		}
		case 15:{
			result=15;
			
			value=c[pos]+"";
			pos++;
			tokens.add(result);
			return "line"+line+" "+result+","+value;
			//break;
		}
		case 16:{
			result=16;
			
			value=c[pos]+"";
			pos++;
			tokens.add(result);
			return "line"+line+" "+result+","+value;
			//break;
		}
		
		case 17:{
			pos++;			
			return "";			
		}
		case 18:{
			pos++;
			return "line"+line+" "+"error "+c[pos-1];
		}
		
		}
	}
		//return result;
	}
	private void checkKey(String s){
		for(int i=0;i<KEY.length;i++){
			if(s.equals(KEY[i])){
				result=20+i;
			}
		}
	}
	private Boolean isLetter(char c){
		if(c>=97&&c<=122){
			return true;
		}else{
			return false;
		}
	}
	private Boolean isNumber(char c){
		if(c>=48&&c<=57){
			return true;
		}else{
			return false;
		}
	}
	private void error(char c){
		//System.out.println(c);
	}
	
}
