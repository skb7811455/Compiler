package compile;

import java.util.ArrayList;

public class Shiyan2 {
	private ArrayList tokens;
	private static int pos=0;
	private int size;
	private String result="";
	private boolean errorFlag=false;
	public String Compile(ArrayList tokens){
		init(tokens);
		/*for(int i=0;i<size;i++){
			System.out.println(tokens.get(i));
		}*/
		program();
		if(errorFlag||size!=pos) result+="error";
		else result+="语法分析成功";
		System.out.println(size);
		System.out.println(pos);
		return result;
		
	}
	private void init(ArrayList tokens){
		this.tokens=tokens;
		this.size=tokens.size();
		this.pos=0;
		this.result="";
		this.errorFlag=false;
	}
	public void program(){
		block();
		System.out.println(pos);
	}
	public void block(){
		if(errorFlag) return;
		if(tokens.get(pos).equals(12)){
			result+="block-->{stmts}"+"\n";
			pos++;
			//System.out.println(pos);
			stmts();
			if(pos>=size){
				error();
				return;
			}
			if(tokens.get(pos).equals(13)){
				pos++;
			}else{
				error();
				return;
			}
		}else{
			error();
			return;
		}
	}
	public boolean checkBlock(){
		if(tokens.get(pos).equals(13)||tokens.get(pos).equals(2)
		||tokens.get(pos).equals(20)||tokens.get(pos).equals(21)
		||tokens.get(pos).equals(22)||tokens.get(pos).equals(23)
		||tokens.get(pos).equals(12)){
			return true;
		}else{
			return false;
		}
	}
	public void error(){
		errorFlag=true;
	}
	public void stmts(){
		if(errorFlag) return;
		if(pos>=size) return;
		if(tokens.get(pos).equals(13)){
			//pos++;
			result+="stmts-->null"+"\n";
			return;
		}else{
			result+="stmts-->stmt stmts"+"\n";
			stmt();
			stmts();
		}
	}
	public void stmt(){
		if(errorFlag) return;
		if(tokens.get(pos).equals(2)){
			result+="stmt-->id=expr"+"\n";
			pos++;
			if(tokens.get(pos).equals(9)){
				pos++;
				expr();
				if(tokens.get(pos).equals(14)){
					pos++;
				}else{
					error();
					return;
				}
			}else{
				error();
				return;
			}
		}else if(tokens.get(pos).equals(20)){
			result+="stmt-->if (bool) stmt"+"\n";
			pos++;
			IF();
		}else if(tokens.get(pos).equals(21)){
			result+="stmt-->while (bool) stmt"+"\n";
			pos++;
			if(tokens.get(pos).equals(15)){
				pos++;
				bool();
				if(tokens.get(pos).equals(16)){
					pos++;
					stmt();
				}else{
					error();
					return;
				}
			}else{
				error();
				return;
			}
		}else if(tokens.get(pos).equals(23)){
			pos++;
			result+="stmt-->break"+"\n";
		}else if(tokens.get(pos).equals(12)){
			block();
		}else if(tokens.get(pos).equals(22)){
			result+="stmt-->do stmt while (bool)"+"\n";
			pos++;
			stmt();
			if(tokens.get(pos).equals(21)){
				pos++;
				if(tokens.get(pos).equals(15)){
					pos++;
					bool();
					if(tokens.get(pos).equals(16)){
						pos++;
					}else{
						error();
						return;
					}
				}else{
					error();
					return;
				}
			}else{
				error();
				return;
			}
			
		}
	}
	public void expr(){
		if(errorFlag) return;
		result+="expr-->term E_"+"\n";
		term();
		E_();
		
	}
	public void IF(){
		if(errorFlag) return;
		if(tokens.get(pos).equals(15)){
			pos++;
			bool();
			if(tokens.get(pos).equals(16)){
				pos++;
				stmt();
			}else{
				error();
				return;
			}
			if(tokens.get(pos).equals(25)){
				pos++;
				result+="else stmt"+"\n";
				stmt();
			}
			
		}else{
			error();
			return;
		}
	}
	
	public void bool(){
		if(errorFlag) return;
		result+="bool-->expr B"+"\n";
		expr();
		B();
	}
	public void B(){	
		if(errorFlag) return;
		if(tokens.get(pos).equals(10)){
			pos++;
			result+="B--> < S"+"\n";
			S();
		}else if(tokens.get(pos).equals(11)){
			pos++;
			result+="B--> > M"+"\n";
			M();
		}else if(tokens.get(pos).equals(16)){
			result+="B-->null"+"\n";
			return;
		}
	}
	public void M(){
		if(errorFlag) return;
		if(tokens.get(pos).equals(9)){
			result+="M-->=expr"+"\n";
			pos++;			
			expr();
		}else{
			result+="M-->expr"+"\n";
			expr();
		}
	}
	public void S(){
		if(errorFlag) return;
		if(tokens.get(pos).equals(9)){
			result+="S--<=expr"+"\n";
			pos++;	
			expr();
		}else{
			result+="S-->expr"+"\n";
			expr();
		}
	}
	public void E_(){
		if(errorFlag) return;
		if(tokens.get(pos).equals(5)){
			result+="E_-->+term"+"\n";
			pos++;
			term();
			E_();
		}else if(tokens.get(pos).equals(6)){
			result+="E_-->-term"+"\n";
			pos++;
			term();
			E_();
		}else{
			result+="E_-->null"+"\n";
			return;
		}
	}
	
	public void term(){
		if(errorFlag) return;
		result+="term-->factor T_"+"\n";
		factor();
		T_();
		
		
	}
	public void T_(){
		if(errorFlag) return;
		if(tokens.get(pos).equals(7)){
			result+="T_-->*factor T_"+"\n";
			pos++;
			factor();
			T_();
		}else if(tokens.get(pos).equals(8)){
			result+="T_-->/factor T_"+"\n";
			pos++;
			factor();
			T_();
		}else{
			result+="T_-->null"+"\n";
			return;
		}
	}
	public void factor(){
		if(errorFlag) return;
		if(tokens.get(pos).equals(15)){
			result+="factor-->(expr)"+"\n";
			pos++;
			expr();
			if(tokens.get(pos).equals(16)){
				pos++;
			}else{
				error();
				return;
			}
		}else if(tokens.get(pos).equals(2)){
			result+="factor-->id"+"\n";		
			pos++;
		}else if(tokens.get(pos).equals(4)){
			result+="factor-->num"+"\n";
			pos++;
		}else{
			error();
			return;
		}
	}
}
