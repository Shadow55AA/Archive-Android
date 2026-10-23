package com.atl.equation;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class EqFunction
{
	private StringBuffer name,func,postfix,errormsg;
	public int n,m;
	private boolean berror,bcaneval;
	private ArrayList<String> opstack;
	private ArrayList<EqVariable> numstack;
	
	private ArrayList<EqVariable> lvarsf;
	private HashMap<String,Integer> lvarshm;
	private HashMap<String,EqVariable> gvars;
	private HashMap<String,EqFunction> gfuncs;
	public static int recurcount = 0;
	private boolean bittfunc = false;
	private int numitts = 1,curitt = 0;

	private int tdepth,tn,tm;
	private boolean btmatrix;
	
	public EqFunction()
	{
		name = new StringBuffer();
		func = new StringBuffer();
		postfix = new StringBuffer();
		errormsg = new StringBuffer();
		berror = false;
		opstack = new ArrayList<String>();
		numstack = new ArrayList<EqVariable>();
		
		lvarsf = new ArrayList<EqVariable>();
		lvarshm = new HashMap<String,Integer>();
		gvars = null;
		gfuncs = null;
		n=1;m=1;
		tdepth=0;tn=1;tm=1;
		btmatrix=false;
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

	public void ClearLocalVars()
	{
		lvarsf.clear();
		lvarshm.clear();
	}
	
	public void AddLocalVar(String ivarname)
	{
		if(!lvarshm.containsKey(ivarname)) {
			lvarsf.add(new EqVariable());
			lvarshm.put(ivarname, lvarsf.size() - 1);
		}
	}
	
	public void SetFunction(String ifunc)
	{
		func.setLength(0);
		func.append(ifunc);
		tdepth=0;
		tn=1;tm=1;
		btmatrix=false;
		bittfunc=false;
		GenPostFix();
	}
	
	public String GetFunction()
	{
		return func.toString();
	}
	
	public String[] GetVarNames()
	{
		StringBuffer tmp = new StringBuffer();
		
		tmp.setLength(0);
		Set<String> keys = lvarshm.keySet();
		
		String sarray[] = new String[keys.size()];
		keys.toArray(sarray);
		return sarray;
	}
	
	public int GetNumVars()
	{
		return lvarsf.size();
	}
	
	public void SetLocalVar(String iname,EqVariable ivar)
	{
		if(lvarshm.containsKey(iname))
		{
			lvarsf.set(lvarshm.get(iname),ivar);
		}
	}
	
	public void SetLocalVar(int vind,EqVariable ivar)
	{
		lvarsf.set(vind,ivar);
	}
	
	public String GetPostFix()
	{
		return postfix.toString();
	}
	
	public EqVariable Evaluate(HashMap<String,EqVariable> igvars,HashMap<String,EqFunction> igfuncs)
	{
		recurcount = 0;
		curitt=0;
		gvars = igvars;
		gfuncs = igfuncs;
		berror = false;
		errormsg.setLength(0);
		numstack.clear();
		for(EqFunction tfunc : igfuncs.values())
		{
			tfunc.curitt=0;
		}
		if(bittfunc) {
			numitts = (int) lvarsf.get(0).getValue();
			if(numitts>1000)
				numitts=1000;
			for(int i=0;i<numitts-1;i++)
				EvaluateR(igvars,igfuncs,lvarsf);
		}
		return EvaluateR(igvars,igfuncs,lvarsf);
	}
	
	public EqVariable EvaluateR(HashMap<String,EqVariable> igvars,HashMap<String,EqFunction> igfuncs,ArrayList<EqVariable> ilvarsrec)
	{
		if(!bcaneval)
			return new EqVariable(0d);

		if(curitt>=numitts)
			return new EqVariable(0d);
		curitt++;
		if(curitt>1000)
		{
			berror=true;
			errormsg.append("Cannot create recursive functions.");
			return new EqVariable(0);
		}

		/*recurcount++;
		if(!bittfunc&&recurcount>100)
		{
			berror = true;
			errormsg.append("Cannot create recursive functions.");
			return new EqVariable(0);
		}*/

		StringBuffer numstr = new StringBuffer(32);


		int pfi = 0;
		while(pfi<postfix.length())
		{
			char c = postfix.charAt(pfi);
			if(Character.isDigit(c)||c=='.')
			{
				numstr.setLength(0);
				while(pfi<postfix.length()&&(Character.isDigit(c)||c=='.'))
				{
					numstr.append(c);pfi++;
					if(pfi<postfix.length())
						c = postfix.charAt(pfi);
				}
				PushNum(new EqVariable(Double.valueOf(numstr.toString())));
				pfi--;
			}
			else if(c==']')
			{
				if(pfi+1<postfix.length())
					c=postfix.charAt(pfi+1);
				pfi++;
				numstr.setLength(0);
				while(pfi<postfix.length()&&Character.isDigit(c))
				{
					numstr.append(c);pfi++;
					if(pfi<postfix.length())
						c = postfix.charAt(pfi);
				}
				int tn = Integer.valueOf(numstr.toString()),tm=1;
				if(c==',')
				{
					if(pfi+1<postfix.length())
						c=postfix.charAt(pfi+1);
					pfi++;
					numstr.setLength(0);
					while(pfi<postfix.length()&&Character.isDigit(c))
					{
						numstr.append(c);pfi++;
						if(pfi<postfix.length())
							c = postfix.charAt(pfi);
					}
					tm = Integer.valueOf(numstr.toString());
				}
				EqVariable tvar = new EqVariable();
				if(tvar.setValues(tn,tm,numstack))
				{
					berror=true;
					errormsg.append("Improper Vector or Matrix.");
					return new EqVariable(0d);
				}
				PushNum(tvar);
				pfi--;
			}
			else if(c=='\'')
			{
				EqVariable tvar = new EqVariable(PopNum());
				tvar.transpose();
				PushNum(tvar);
			}
			else if(isOperator(postfix,pfi))
			{
				if(postfix.charAt(pfi)=='+')
				{
					EqVariable b = PopNum();
					EqVariable a = new EqVariable(PopNum());
					if(a.add(b))
					{
						berror=true;
						errormsg.append("Improper use of '+'.");
						return new EqVariable(0d);
					}
					PushNum(a);
				}
				if(postfix.charAt(pfi)=='-')
				{
					EqVariable a = new EqVariable(PopNum());
					a.negate();
					PushNum(a);
				}
				if(postfix.charAt(pfi)=='/')
				{
					EqVariable b = new EqVariable(PopNum());
					EqVariable a = new EqVariable(PopNum());
					if(a.divide(b))
					{
						berror=true;
						errormsg.append("Improper use of '/'.");
						return new EqVariable(0d);
					}
					PushNum(a);
				}
				if(postfix.charAt(pfi)=='*')
				{
					EqVariable b = PopNum();
					EqVariable a = new EqVariable(PopNum());
					if(a.multiply(b))
					{
						berror=true;
						errormsg.append("Improper use of '*'.");
						return new EqVariable(0d);
					}
					PushNum(a);
				}
				if(postfix.charAt(pfi)=='^')
				{
					double b = PopNum().getValue();
					double a = PopNum().getValue();
					PushNum(new EqVariable(Math.pow(a,b)));
				}
			}
			else if(isValidName(postfix,pfi))
			{
				String tname = extractName(postfix,pfi);
				pfi+=tname.length()-1;
				if(isTrig(tname))
				{
					double a = PopNum().getValue();
					if(tname.equals("sin"))
					{
						PushNum(new EqVariable(Math.sin(a)));
					}
					if(tname.equals("cos"))
					{
						PushNum(new EqVariable(Math.cos(a)));
					}
					if(tname.equals("tan"))
					{
						PushNum(new EqVariable(Math.tan(a)));
					}
					if(tname.equals("asin"))
					{
						PushNum(new EqVariable(Math.asin(a)));
					}
					if(tname.equals("acos"))
					{
						PushNum(new EqVariable(Math.acos(a)));
					}
					if(tname.equals("atan"))
					{
						PushNum(new EqVariable(Math.atan(a)));
					}
					if(tname.equals("hsin"))
					{
						PushNum(new EqVariable(Math.sinh(a)));
					}
					if(tname.equals("hcos"))
					{
						PushNum(new EqVariable(Math.cosh(a)));
					}
					if(tname.equals("htan"))
					{
						PushNum(new EqVariable(Math.tanh(a)));
					}
				}
				else if(isResFunc(tname))
				{
					double a = PopNum().getValue();
					if(tname.equals("ln"))
					{
						PushNum(new EqVariable(Math.log(a)));
					}
					if(tname.equals("abs"))
					{
						PushNum(new EqVariable(Math.abs(a)));
					}
				}
				else if(pfi+1<postfix.length()&&postfix.charAt(pfi+1)=='(')
				{
					if(gfuncs.containsKey(tname))
					{
						EqFunction tfunc = gfuncs.get(tname);
						if(tname.equals(name))
						{
							PushNum(lvarsf.get(0));
						}
						else {
							ArrayList<EqVariable> tlvarsrec = new ArrayList<EqVariable>();
							for (int i = 0; i < tfunc.GetNumVars(); i++) {
								tlvarsrec.add(0, new EqVariable(PopNum()));
							}
							PushNum(tfunc.EvaluateR(gvars, gfuncs, tlvarsrec));
							String error = tfunc.GetError();
							if (error != null) {
								berror = true;
								errormsg.append(error);
								return new EqVariable(0d);
							}
						}
					}
					else
					{
						berror = true;
						errormsg.append("Unknown function "+tname+"().");
						return new EqVariable(0d);
					}
					pfi+=2;
				}
				else
				{
					if(tname.equals("Pi"))
					{
						PushNum(new EqVariable(Math.PI));
					}
					else if(tname.equals("Phi"))
					{
						PushNum(new EqVariable(0.5*Math.pow(5, 0.5)+0.5));
					}
					else if(tname.equals("e"))
					{
						PushNum(new EqVariable(Math.E));
					}
					else if(lvarshm.containsKey(tname))
					{
						PushNum(ilvarsrec.get(lvarshm.get(tname)));
					}
					else if(gvars.containsKey(tname))
					{
						PushNum(gvars.get(tname));
					}
					else
					{
						berror = true;
						errormsg.append("Unknown variable "+tname+".");
						return new EqVariable(0d);
					}
				}
			}
			pfi++;
		}


		if(numstack.size()!=1)
		{
			berror = true;
			errormsg.append("Operand mismatch in "+name);
			return new EqVariable(0d);
		}
		EqVariable tans = PopNum();
		n=tans.getN();
		m=tans.getM();
		return tans;
	}
	
	public EqVariable Evaluate()
	{
		return Evaluate(null,null);
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
	
	private boolean isResFunc(String istr)
	{
		if(istr.startsWith("ln"))
		{
			return true;
		}
		if(istr.startsWith("abs"))
		{
			return true;
		}
		return false;
	}
	
	public static boolean isValidName(StringBuffer istr,int ind)
	{
		if(!Character.isLetter(istr.charAt(ind))&&istr.charAt(ind)!='_')
		{
			return false;
		}
		return true;
	}
	
	public static String extractName(StringBuffer istr,int ind)
	{
		StringBuffer tstr = new StringBuffer();
		tstr.setLength(0);
		char c = istr.charAt(ind);
		while(ind<istr.length()&&(Character.isLetter(c)||Character.isDigit(c)||c=='_'))
		{
			tstr.append(c);
			ind++;
			if(ind<istr.length())
				c=istr.charAt(ind);
		}
		ind--;
		return tstr.toString();
	}
	
	public void extractLocalVarNames(StringBuffer istr,int ind)
	{
		berror = false;
		errormsg.setLength(0);
		while(ind<istr.length()&&istr.charAt(ind)!=')')
		{
			while(istr.charAt(ind)==' ')
				ind++;
			if(!isValidName(istr,ind))
			{
				berror = true;
				errormsg.append("Unexpected character '"+istr.charAt(ind)+"'.");
				return;
			}
			String tname = extractName(istr,ind);
			ind+=tname.length();
			lvarsf.add(new EqVariable(lvarsf.size()));
			lvarshm.put(tname,lvarsf.size()-1);
			while(istr.charAt(ind)==' ')
				ind++;
			if(istr.charAt(ind)!=','&&istr.charAt(ind)!=')')
			{
				berror = true;
				errormsg.append("Unexpected character '"+istr.charAt(ind)+"'.");
				return;
			}
			if(istr.charAt(ind)==',')
				ind++;
		}
	}
	
	private int Precedence(String istr)
	{
		if(istr.charAt(0)=='+'||istr.charAt(0)=='-')
		{
			return 0;
		}
		if(istr.charAt(0)=='*'||istr.charAt(0)=='/')
		{
			return 1;
		}
		if(istr.charAt(0)=='^')
		{
			return 2;
		}
		if(isTrig(istr)||isResFunc(istr))
		{
			return 3;
		}
		return -1;
	}
	
	private void PushOp(String istr)
	{
		opstack.add(istr);
	}
	
	private String PopOp()
	{
		if(opstack.size()>0)
			return opstack.remove(opstack.size()-1);
		return null;
	}
	
	private void PushNum(EqVariable idouble)
	{
		numstack.add(idouble);
	}
	
	private EqVariable PopNum()
	{
		if(numstack.size()>0)
			return numstack.remove(numstack.size()-1);
		return null;
	}
	
	private void GenPostFix()
	{
		berror = false;
		errormsg.setLength(0);
		postfix.setLength(0);
		opstack.clear();
		bcaneval = false;
		tdepth=0;
		tn=1;tm=0;
		btmatrix=false;
		
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
			else if(isOperator(func,fni))
			{
				postfix.append(" ");
				if(c=='(')
				{
					PushOp(func.substring(fni,fni+1));
				}
				else if(c==')')
				{
					String tmpop = PopOp();
					while(tmpop!=null&&!tmpop.contains("("))
					{
						postfix.append(tmpop);
						tmpop = PopOp();
					}
					if(tmpop==null)
					{
						berror = true;
						errormsg.append("Missing '('.");
						return;
					}
					else if(tmpop.charAt(0)!='(')
					{
						postfix.append(tmpop+" ");
					}
				}
				else
				{
					String op = func.substring(fni,fni+1);
					if(!bnumlast)
					{
						if(op.charAt(0)=='-')	// Negation
							PushOp(op);
						else
						{
							berror = true;
							errormsg.append("Operator '"+op+"' is not preceded by a number.");
							return;
						}
					}
					else
					{
						boolean bstop = false;
						while(!opstack.isEmpty()&&!bstop)
						{
							String top = PopOp();
							if(Precedence(top)>=Precedence(op))
							{
								postfix.append(top);
							}
							else if(top.equals("-")&&(op.equals("+")||op.equals("-")))
							{
								postfix.append(top);
							}
							else
							{
								PushOp(top);
								bstop = true;
							}
						}
						if(op.equals("-"))	// Subtraction
							PushOp("+");
						PushOp(op);
					}
					bnumlast=false;
				}
			}
			else if(isValidName(func,fni))
			{
				String tname = extractName(func,fni);
				fni+=tname.length()-1;
				if(isTrig(tname))
				{
					PushOp(tname+" ");
				}
				else if(isResFunc(tname))
				{
					PushOp(tname+" ");
				}
				else if(fni+1<func.length()&&func.charAt(fni+1)=='(')
				{
					if(tname.contentEquals(name))
					{
						bittfunc=true;
						//berror = true;
						//errormsg.append("Cannot create recursive functions.");
						//return;
					}
					PushOp(tname+"() ");
					bnumlast=false;
					fni++;
				}
				else	// Variable
				{
					if(tname.equals("Pi"))
					{
						postfix.append(tname+" ");
					}
					else if(tname.equals("Phi"))
					{
						postfix.append(tname+" ");
					}
					else if(tname.equals("e"))
					{
						postfix.append(tname+" ");
					}
					else
						postfix.append(tname+" ");
					bnumlast = true;
				}
			}
			else if(c==',')
			{
				String tmpop = PopOp();
				while(tmpop!=null&&(!tmpop.contains("(")&&tmpop.charAt(0)!='(')&&tmpop.charAt(0)!='[')
				{
					postfix.append(tmpop);
					tmpop = PopOp();
				}
				if(tmpop==null)
				{
					berror = true;
					errormsg.append("Missing '('.");
					return;
				}
				if(tmpop.charAt(0)=='(')
				{
					berror = true;
					errormsg.append("Missing ')'.");
					return;
				}
				if(tmpop.charAt(0)=='[')
				{
					tn++;
				}
				PushOp(tmpop);
				postfix.append(" ");
				bnumlast = false;
			}
			else if(c=='[')
			{
				tdepth++;
				if(tdepth>2)
				{
					berror=true;
					errormsg.append("Too many '['s.");
					return;
				}
				if(tdepth==2) {
					btmatrix=true;
					tn=1;
					tm++;
				}
				PushOp("[");
			}
			else if(c==']') {
				String tmpop = PopOp();
				while(tmpop!=null&&!tmpop.contains("["))
				{
					postfix.append(tmpop);
					tmpop = PopOp();
				}
				if(tmpop==null)
				{
					berror = true;
					errormsg.append("Missing '['.");
					return;
				}
				postfix.append(' ');
				tdepth--;
				if(tdepth==1) {
					bnumlast = false;
				}
				else if(tdepth==0) {
					bnumlast=true;
					postfix.append(c);
					postfix.append(tn);
					if(btmatrix)
					{
						btmatrix=false;
						postfix.append(',');
						postfix.append(tm);
					}
					tn=1;tm=0;
				}
				postfix.append(' ');
			}
			else if(c=='\'')
			{
				postfix.append(c);
			}
			else
			{
				berror = true;
				errormsg.append("Unexpected character '"+c+"'.");
				return;
			}
			
			fni++;
		}
		
		while(!opstack.isEmpty())
		{
			postfix.append(" ");
			postfix.append(PopOp());
		}
		
		bcaneval = true;
	}
}
