package com.atl.equation;

import java.util.ArrayDeque;

public class EqFunction
{
	private StringBuffer name,func,postfix,errormsg;
	private boolean berror;
	private ArrayDeque<String> opstack;
	private DefineList coreresnames;
	
	public EqFunction()
	{
		name = new StringBuffer();
		func = new StringBuffer();
		postfix = new StringBuffer();
		errormsg = new StringBuffer();
		berror = false;
		opstack = new ArrayDeque<String>(10);
		coreresnames = new DefineList();
		coreresnames.addDefine("sin");
		coreresnames.addDefine("cos");
		coreresnames.addDefine("tan");
		coreresnames.addDefine("asin");
		coreresnames.addDefine("acos");
		coreresnames.addDefine("atan");
		coreresnames.addDefine("hsin");
		coreresnames.addDefine("hcos");
		coreresnames.addDefine("htan");
		
	}
	
	public void SetName(String iname)
	{
		name.setLength(0);
		name.append(iname);
	}
	
	public String GetName()
	{
		return name.toString();
	}
	
	public void SetFunction(String ifunc)
	{
		func.setLength(0);
		func.append(ifunc);
		GenPostFix();
	}
	
	public String GetPostFix()
	{
		return postfix.toString();
	}
	
	public void Evaluate()
	{
	}
	
	public String GetError()
	{
		if(berror)
			return errormsg.toString();
		return null;
	}
	
	private boolean isOperator(StringBuffer istr,int ind)
	{
		if(istr.charAt(ind)=='+')
			return true;
		if(istr.charAt(ind)=='-')
			return true;
		if(istr.charAt(ind)=='/')
			return true;
		if(istr.charAt(ind)=='*')
			return true;
		if(istr.charAt(ind)=='(')
			return true;
		if(istr.charAt(ind)==')')
			return true;
		if(istr.charAt(ind)=='^')
			return true;
		return false;
	}
	
	private boolean isTrig(String istr)
	{
		if(istr.startsWith("sin"))
			return true;
		if(istr.startsWith("cos"))
			return true;
		if(istr.startsWith("tan"))
			return true;
		if(istr.startsWith("asin"))
			return true;
		if(istr.startsWith("acos"))
			return true;
		if(istr.startsWith("atan"))
			return true;
		if(istr.startsWith("hsin"))
			return true;
		if(istr.startsWith("hcos"))
			return true;
		if(istr.startsWith("htan"))
			return true;
		return false;
	}
	
	private int Precedence(String istr)
	{
		if(istr.equals("+")||istr.equals("-"))
		{
			return 0;
		}
		if(istr.equals("*")||istr.equals("/"))
		{
			return 1;
		}
		if(istr.equals("^"))
		{
			return 2;
		}
		if(isTrig(istr))
		{
			return 3;
		}
		return -1;
	}
	
	private void PushOp(String istr)
	{
		opstack.addLast(istr);
	}
	
	private String PopOp()
	{
		return opstack.pollLast();
	}
	
	private void GenPostFix()
	{
		berror = false;
		errormsg.setLength(0);
		postfix.setLength(0);
		while(!opstack.isEmpty())
			PopOp();
		
		boolean bnumlast = false;
		int fni=0;
		while(fni<func.length())
		{
			char c = func.charAt(fni);
			if(c==' ')
			{
				
			}
			else if(Character.isDigit(c)||c=='.')
			{
				postfix.append(c);
				bnumlast = true;
			}
			else
			{
				if(isOperator(func,fni))
				{
					postfix.append(" ");
					if(c=='(')
					{
						
					}
					else if(c==')')
					{
					}
					else
					{
						String strop = func.substring(fni,fni+2);
						
						boolean bstop = false;
						while(!opstack.isEmpty()&&!bstop)
						{
							String top = PopOp();
							if(Precedence(top)>Precedence(strop))
							{
								postfix.append(top);
							}
							else
							{
								PushOp(top);
								bstop = true;
							}
						}
						PushOp(strop);
						bnumlast=false;
					}
				}
				if(isTrig(func.substring(fni)))
				{
					
				}
				if(c==',')
				{
					
				}
			}
			
			fni++;
		}
		
		while(!opstack.isEmpty())
		{
			postfix.append(" ");
			postfix.append(PopOp());
		}
	}
}
