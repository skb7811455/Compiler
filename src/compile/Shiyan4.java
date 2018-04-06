package compile;

import java.util.ArrayList;
import java.util.Stack;

/*
S->Program   40
Program->  block   41
block->  { stmts }   42
stmts->  stmt stmts   43
Stmts-> &  44
stmt->  id = E ; 45
stmt-> while ( bool )  stmt 46
stmt-> block 47
bool->  T <= T 48
 bool-> T >= T 49
bool-> T   50
T->  id    51
T->  num      52
E->id      53
E->E+E   54
E->E-E   55
E->E*E 56
E->E/E 57
E->(E) 58
*/
class rule{
	private int len;
	private String S;
	rule(int l,String s){
		this.len=l;
		this.S=s;
	}
	public int getLen(){
		return len;
	}
	public String getS(){
		return S;
	}
	
}
class rules{
   private rule rules[]={new rule(1,"S"),new rule(1,"Program"),new rule(3,"block"),
		   new rule(2,"stmts"),new rule(0,"stmts"),new rule(4,"stmt"),
		   new rule(5,"stmt"),new rule(1,"stmt"),new rule(4,"bool"),new rule(4,"bool"),
		   new rule(1,"bool"),new rule(1,"T"),new rule(1,"T"),new rule(1,"E"),new rule(3,"E"),
		   new rule(3,"E"),new rule(3,"E"),new rule(3,"E"),new rule(3,"E")};
   public String getRule(int index){
	   return rules[index].getS();
   }
   public int getRuleLen(int index){
	   return rules[index].getLen();
   }
   
}

public class Shiyan4 {
	private rules r;
	private ArrayList tokens;
	private String[] strs;
	private Stack<String> symbolStack; 
	private Stack<Integer> stateStack;
	//private String subString="";
	private String s="";
	private StringBuffer symbolS;
	private StringBuffer stateS;
	private StringBuffer actionS;
	private final static int table[][]={//    {   }  (   )   while id   =   ;   <  >     num  #  Program  block    stmts stmt bool  E    T   +    -    *   / 
											{ 4, -1, -1, -1,  -1,  -1, -1, -1, -1, -1,  -1, -1,    2,      3,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S1
											{-1, -1, -1, -1,  -1,  -1, -1, -1, -1, -1,  -1, 99,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S2
											{-1, -1, -1, -1,  -1,  -1, -1, -1, -1, -1,  -1, 41,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S3
											{29, 44, -1, -1,   8,  14, -1, -1, -1, -1,  -1, -1,   -1,      9,       5,   7,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S4
											{-1,  6, -1, -1,  -1,  -1, -1, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S5
											{-1, -1, -1, -1,  -1,  -1, -1, -1, -1, -1,  -1, 42,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S6
											{29, 44, -1, -1,   8,  14, -1, -1, -1, -1,  -1, -1,   -1,      9,      26,   7,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S7
											{-1, -1, 10, -1,  -1,  -1, -1, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S8
											{47, 47, -1, -1,  47,  47, -1, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S9
											{-1, -1, -1, -1,  -1,  28, -1, -1, -1, -1,  32, -1,   -1,     -1,      -1,  -1,  12,   -1, 11,   -1,  -1,  -1,  -1},//S10
											{-1, -1, -1, 50,  -1,  -1, -1, -1, 18, 19,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S11
											{-1, -1, -1, 13,  -1,  -1, -1, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S12
											{29, -1, -1, -1,   8,  14, -1, -1, -1, -1,  -1, -1,   -1,      9,      -1,  27,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S13
											{-1, -1, -1, -1,  -1,  -1, 15, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S14
											{-1, -1, -1, -1,  -1,  33, -1, -1, -1, -1,  16, -1,   -1,     -1,      -1,  -1,  -1,   34, -1,   -1,  -1,  -1,  -1},//S15
											{-1, -1, -1, -1,  -1,  -1, -1, 17, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S16
											{45, 45, -1, -1,  45,  45, -1, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S17
											{-1, -1, -1, -1,  -1,  -1, 20, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S18
											{-1, -1, -1, -1,  -1,  -1, 23, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S19
											{-1, -1, -1, -1,  -1,  22, -1, -1, -1, -1,  24, -1,   -1,     -1,      -1,  -1,  -1,   -1, 21,   -1,  -1,  -1,  -1},//S20
											{-1, -1, -1, 48,  -1,  -1, -1, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S21
											{-1, -1, -1, 51,  -1,  -1, -1, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S22
											{-1, -1, -1, -1,  -1,  22, -1, -1, -1, -1,  24, -1,   -1,     -1,      -1,  -1,  -1,   -1, 25,   -1,  -1,  -1,  -1},//S23
											{-1, -1, -1, 52,  -1,  -1, -1, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S24
											{-1, -1, -1, 49,  -1,  -1, -1, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S25
											{-1, 43, -1, -1,  -1,  -1, -1, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S26
											{46, 46, -1, -1,  46,  46, -1, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S27
											{-1, -1, -1, 51,  -1,  -1, -1, -1, 51, 51,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S28
											
											{29, 44, -1, -1,   8,  14, -1, -1, -1, -1,  -1, -1,   -1,      9,      30,   7,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S29
											{-1, 31, -1, -1,  -1,  -1, -1, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S30
											{42, 42, -1, -1,  42,  42, -1, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S31
											
											{-1, -1, -1, 52,  -1,  -1, -1, -1, 52, 52,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//S32
											
											{-1, -1, -1, -1,  -1,  -1, -1, 53, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   53,  -1,  -1,  -1},//33
											{-1, -1, -1, -1,  -1,  -1, -1, 35, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   36,  37,  38,  39},//34
											{45, 45, -1, -1,  45,  45, -1, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   -1,  -1,  -1,  -1},//35
											{-1, -1, -1, -1,  -1,  33, -1, -1, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   37, -1,   -1,  -1,  -1,  -1},//36
											{-1, -1, -1, -1,  -1,  -1, -1, 54, -1, -1,  -1, -1,   -1,     -1,      -1,  -1,  -1,   -1, -1,   36,  -1,  -1,  -1}//37
													
											};
	
	
	private void changeToStrs(ArrayList tokens){//编码转换为字符串函数
		for(int i=0;i<tokens.size();i++){		
			if(tokens.get(i).equals(12)){
				strs[i]="{";
			}else if(tokens.get(i).equals(13)){
				strs[i]="}";
			}else if(tokens.get(i).equals(15)){
				strs[i]="(";
			}else if(tokens.get(i).equals(16)){
				strs[i]=")";
			}else if(tokens.get(i).equals(21)){
				strs[i]="while";
			}else if(tokens.get(i).equals(2)){
				strs[i]="id";
			}else if(tokens.get(i).equals(5)){
				strs[i]="+";
			}else if(tokens.get(i).equals(6)){
				strs[i]="-";
			}else if(tokens.get(i).equals(7)){
				strs[i]="*";
			}else if(tokens.get(i).equals(8)){
				strs[i]="/";
			}else if(tokens.get(i).equals(9)){
				strs[i]="=";
			}else if(tokens.get(i).equals(10)){
				strs[i]="<";
			}else if(tokens.get(i).equals(11)){
				strs[i]=">";
			}else if(tokens.get(i).equals(14)){
				strs[i]=";";
			}else if(tokens.get(i).equals(4)){
				strs[i]="num";
			}else if(tokens.get(i).equals(-1)){
				strs[i]="#";
			}else{
				System.out.println("输入文法错误");
			}
			//subString+=strs[i];
		}
	}
	Shiyan4(){
		r=new rules();
	}
	private void init(ArrayList tokens){//初始化
		s="";
		//subString="";
		symbolStack=new Stack();
		stateStack=new Stack<Integer>();
		symbolS=new StringBuffer(200);
		actionS=new StringBuffer(200);
		stateS=new StringBuffer(200);
		symbolStack.push("#");//将#压入栈底
		stateStack.push(1);//1状态入栈
		tokens.add(-1);
		
		strs=new String[tokens.size()];
		
		changeToStrs(tokens);	//将词法分析器传来的编码转换成对应字符串		
		
	}
	private int change(String s){//获得当前对应字符串的列号
		int m=-1;
		if(s=="{"){
			m=0;
		}else if(s=="}"){
			m=1;
		}else if(s=="("){
			m=2;
		}else if(s==")"){
			m=3;
		}else if(s=="while"){
			m=4;
		}else if(s=="id"){
			m=5;
		}else if(s=="="){
			m=6;
		}else if(s==";"){
			m=7;
		}else if(s=="<"){
			m=8;
		}else if(s==">"){
			m=9;
		}else if(s=="num"){
			m=10;
		}else if(s=="#"){
			m=11;
		}else if(s=="Program"){
			m=12;
		}else if(s=="block"){
			m=13;
		}else if(s=="stmts"){
			m=14;
		}else if(s=="stmt"){
			m=15;
		}else if(s=="bool"){
			m=16;
		}else if(s=="E"){
			m=17;
		}else if(s=="T"){
			m=18;
		}else if(s=="+"){
			m=19;
		}else if(s=="-"){
			m=20;
		}else if(s=="*"){
			m=21;
		}else if(s=="/"){
			m=22;
		}else{
			m=-1;
		}
		return m;
	}
	private String outStateStack(){
		String s="";
		for(int i=0;i<stateStack.size();i++){
			s+=stateStack.elementAt(i)+" ";
		}
		return s;
	}
	private String outSymbolStack(){
		String s="";
		for(int i=0;i<symbolStack.size();i++){
			s+=symbolStack.elementAt(i);
		}
		return s;
	}
	private void action(int state,String symbol,int index){
		s="   栈中状态:"+outStateStack()+"   栈中符号:"+outSymbolStack()//+"   输入符号串:"+strs[index]
		+"   分析动作:移进"+symbol+",状态转移至S"+state+"\n";
		stateS.append(outStateStack()+"\n");
		symbolS.append(outSymbolStack()+"\n");
		actionS.append("移进"+symbol+",状态转移至S"+state+"\n");
		System.out.println(s);
		stateStack.push(state);
		symbolStack.push(symbol);
	}
	private void goTo(int ruleIndex,int index){
		int len;
		ruleIndex=ruleIndex-40;
		len=r.getRuleLen(ruleIndex);
		
		s="   栈中状态:"+outStateStack()+"   栈中符号:"+outSymbolStack()//+"   输入符号串:"+subString.substring(index)
		+"   分析动作:归约"+",使用规则r"+ruleIndex+"\n";
		stateS.append(outStateStack()+"\n");
		symbolS.append(outSymbolStack()+"\n");
		actionS.append("分析动作:归约"+",使用规则r"+ruleIndex+"\n");
		
		System.out.println(s);
				
		for(int i=0;i<len;i++){
			stateStack.pop();
			symbolStack.pop();
			
		}		
			
		String symbol=r.getRule(ruleIndex);	
		symbolStack.push(symbol);
		
		int n;	
		int m=stateStack.peek();
		if(change(symbol)==-1){
			System.out.println("词法错误");
			return;
		}else{
			n=change(symbol);
		}
		if(table[m-1][n]==-1) System.out.println("语法错误");
		stateStack.push(table[m-1][n]);
		
	}
	public String[] analyze(ArrayList tokens){
		int i=0;
		String output[]=new String[3];
		
		init(tokens);
		this.tokens=tokens;	
		while(i<strs.length){	
		    int n;
			int m=stateStack.peek();
			
			if(change(strs[i])==-1){
				System.out.println("error 词法错误");
				actionS.append("error 词法错误");
				output[0]=stateS.toString();
				output[1]=symbolS.toString();
				output[2]=actionS.toString();
				
				return output;
			}else{
				n=change(strs[i]);
			}		
			if(table[m-1][n]==-1){
				System.out.println(m+" "+n);
				System.out.println("error 语法错误");
				actionS.append("error 语法错误");
				output[0]=stateS.toString();
				output[1]=symbolS.toString();
				output[2]=actionS.toString();
				return output;
			}else if(table[m-1][n]==99){
				System.out.println("分析成功");
				output[0]=stateS.toString();
				output[1]=symbolS.toString();
				output[2]=actionS.toString();
				return output;
			}
			
			if(table[m-1][n]<40){//查表小于40，表示是移进状态
				action(table[m-1][n],strs[i],i);
				i++;
			}else{//查表大于等于40，表示是归约状态
				goTo(table[m-1][n],i);
			}
		}
		return output;
	}
}
