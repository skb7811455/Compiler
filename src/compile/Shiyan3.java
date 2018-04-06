package compile;

import java.util.ArrayList;
import java.util.Stack;

public class Shiyan3 {
	private char[][]biao={//  +    -   *   /   id  (   )   #
							{'>','>','<','<','<','<','>','>'}, //+
							{'>','>','<','<','<','<','>','>'}, //-
							{'>','>','>','>','<','<','>','>'},  //*
							{'>','>','>','>','<','<','>','>'},  ///
							{'>','>','>','>','?','?','>','>'},  //id
							{'<','<','<','<','<','<','=','?'},  //(
							{'>','>','>','>','?','?','>','>'},  //)
							{'<','<','<','<','<','<','?','='}}; //#
	//private char[] symbol={'+','-','*','/','i','(',')','#'};
	private ArrayList<Integer>numbers;
	private Stack symbolStack;
	private Stack <Integer>valueStack;
	private char[] chars;
	private StringBuffer s;
	private int change(char token){
		int m;
		if(token=='+'){
			m=0;
		}else if(token=='-'){
			m=1;
		}else if(token=='*'){
			m=2;
		}else if(token=='/'){
			m=3;
		}else if(token=='i'){
			m=4;
		}else if(token=='('){
			m=5;
		}else if(token==')'){
			m=6;
		}else if(token=='#'){
			m=7;
		}else{
			m=-1;
			s.append("输入文法错误");
		}
		return m;
	}
	private void changeToChars(ArrayList tokens){
		for(int i=0;i<tokens.size();i++){
			if(tokens.get(i).equals(5)){
				chars[i]='+';
			}else if(tokens.get(i).equals(6)){
				chars[i]='-';
			}else if(tokens.get(i).equals(7)){
				chars[i]='*';
			}else if(tokens.get(i).equals(8)){
				chars[i]='/';
			}else if(tokens.get(i).equals(4)){
				chars[i]='i';
			}else if(tokens.get(i).equals(15)){
				chars[i]='(';
			}else if(tokens.get(i).equals(16)){
				chars[i]=')';
			}else if(tokens.get(i).equals(-1)){
				chars[i]='#';
			}else{
				System.out.println("输入文法错误");
			}
		}
	}
	private void init(ArrayList tokens,ArrayList numbers){
		s=new StringBuffer(200);
		this.numbers=numbers;
		symbolStack=new Stack();
		valueStack=new Stack<Integer>();
		symbolStack.push('#');//将#压入栈底
		valueStack.push(0);
		tokens.add(-1);
		chars=new char[tokens.size()];
		changeToChars(tokens);
		for(int i=0;i<numbers.size();i++){
			//System.out.println(tokens.get(i));
			//System.out.println(chars[i]);
			System.out.println(numbers.get(i));
		}
	}
	private String subString(int index){
		return String.valueOf(chars).substring(index);
	}
	public String analyze(ArrayList tokens,ArrayList numbers){
		init(tokens,numbers);
		int len=chars.length;
	    String zhan="#";
	    int i=0;
	    int num=0;
		
	    while(i<len){
			int m,n;
			char last;
			n=change(chars[i]);
			m=change((char)symbolStack.peek());
			if(n==-1||m==-1) break;
			else{
				if(biao[m][n]=='<'){
					symbolStack.push(chars[i]);
					if(n==4){
						valueStack.push((int)numbers.get(num));
						num++;
					}
					s.append("当前符号栈:"+zhan+" 关系:"+biao[m][n]+" 剩余符号串:"+subString(i)+" 移进"+"\n");
					zhan+=chars[i];
					//values.push(0)
					i++;
				}else if(biao[m][n]=='>'){
					
					char c;
					int v1;
					int v2;
					do{
						c=(char)symbolStack.pop();
						
						if(c!='i'){
							try{
							v1=valueStack.pop();
							v2=valueStack.pop();
							if(c=='+'){
								valueStack.push(v1+v2);
							}else if(c=='*'){
								valueStack.push(v1*v2);
							}else if(c=='/'){
								valueStack.push(v2/v1);
							}else if(c=='-'){
								valueStack.push(v2-v1);
							}
							}catch(Exception o){
								s.append("语法错误!");
								s.toString();
							}
							
						}																		
						last=c;
						n=change(last);
						m=change((char)symbolStack.peek());
						
					}while(biao[m][n]=='>'||biao[m][n]=='=');
		

					s.append("当前符号栈:"+zhan+" 关系:"+biao[m][n]+" 剩余符号串:"+subString(i)+" 归约"
					+" 最左素短语:"+c+"\n");					
					zhan=zhan.substring(0,zhan.length()-1);
					
				}else if(biao[m][n]=='?'){
					System.out.println("输入文法错误");
					break;
				}else if(biao[m][n]=='='){
					char c=(char)symbolStack.pop();
					if(c=='#'&&chars[i]=='#'){
						s.append("当前符号栈:"+zhan+" 关系:"+biao[m][n]+" 剩余符号串:"+subString(i)+" 接受"+"\n"
								);
						s.append("结果："+valueStack.pop());
						return s.toString();
					}else{
						s.append("当前符号栈:"+zhan+" 关系:"+biao[m][n]+" 剩余符号串:"+subString(i)+" 归约"
								+" 最左素短语:"+c+"\n");
						i++;
						zhan=zhan.substring(0,zhan.length()-1);
					}
				}
			}
		}
		return s.toString();
	}
}
