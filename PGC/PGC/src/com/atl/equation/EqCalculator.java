package com.atl.equation;

import java.util.HashMap;

import com.atl.ltc.GraphedFuncs;
import com.atl.ltc.PlotView;

public class EqCalculator
{
	public static final int CmdTypeNone = 0;
	public static final int CmdTypeExpression = 1;
	public static final int CmdTypeAsgnVar = 2;
	public static final int CmdTypeAsgnFunc = 3;
	
	private HashMap<String,EqVariable> vars;
	private HashMap<String,EqFunction> funcs;
	private StringBuffer cmdbuffer;
	private EqFunction mainfunc;
	private EqFunction lastasgnfunc;
	private int lastcmdtype;
	
	private boolean berror;
	private StringBuffer outputbuf,errormsg;
	
	private DefineList reservednames;
	
	public EqCalculator()
	{
		vars = new HashMap<String,EqVariable>();
		funcs = new HashMap<String,EqFunction>();
		cmdbuffer = new StringBuffer();
		mainfunc = new EqFunction();
		lastasgnfunc = null;
		
		berror = false;
		outputbuf = new StringBuffer();
		errormsg = new StringBuffer();
		
		reservednames = new DefineList();
		reservednames.addDefine("sin");
		reservednames.addDefine("cos");
		reservednames.addDefine("tan");
		reservednames.addDefine("asin");
		reservednames.addDefine("acos");
		reservednames.addDefine("atan");
		reservednames.addDefine("hsin");
		reservednames.addDefine("hcos");
		reservednames.addDefine("htan");
		reservednames.addDefine("ln");
		reservednames.addDefine("abs");
		reservednames.addDefine("Pi");
		reservednames.addDefine("Phi");
		reservednames.addDefine("e");
	}
	
	public void RunCommand(String icmd)
	{
		berror = false;
		errormsg.setLength(0);
		outputbuf.setLength(0);
		cmdbuffer.setLength(0);
		cmdbuffer.append(icmd);
		
		int eqind = cmdbuffer.indexOf("=");
		if(eqind==-1)
		{
			mainfunc.SetFunction(icmd);
			String terror = mainfunc.GetError();
			if(terror==null)
			{
				EqVariable tans = mainfunc.Evaluate(vars,funcs);
				terror = mainfunc.GetError();
				if(terror==null)
				{
					outputbuf.append(mainfunc.GetPostFix() + " = ");
					tans.print(outputbuf);
					//outputbuf.append(Double.toString(tans.getValue()));
				}
				else
					outputbuf.append(terror);
			}
			else
				outputbuf.append(terror);
			lastcmdtype = CmdTypeExpression;
		}
		else
		{
			if(cmdbuffer.indexOf("=",eqind+1)!=-1)
			{
				berror = true;
				errormsg.append("Cannot have more than one '=' sign.");
				return;
			}
			else
			{
				int cmdi = 0;
				while(cmdbuffer.charAt(cmdi)==' ')
					cmdi++;
				if(EqFunction.isValidName(cmdbuffer, cmdi))
				{
					String name = EqFunction.extractName(cmdbuffer, cmdi);
					cmdi+=name.length();
					if(cmdi<cmdbuffer.length()&&cmdbuffer.charAt(cmdi)=='(')
					{
						if(reservednames.isDefined(name))
						{
							berror = true;
							errormsg.append("Cannot create function named "+name+".");
							return;
						}
						EqFunction tfunc = null;
						if(funcs.containsKey(name))
						{
							tfunc = funcs.get(name);
						}
						else
						{
							tfunc = new EqFunction();
							tfunc.SetName(name);
						}
						tfunc.ClearLocalVars();
						tfunc.extractLocalVarNames(cmdbuffer, cmdi+1);
						String terror = tfunc.GetError();
						if(terror==null)
						{
							tfunc.SetFunction(cmdbuffer.substring(eqind+1));
							terror = tfunc.GetError();
							if(terror==null)
							{
								funcs.put(name, tfunc);
								
								EqVariable teval = tfunc.Evaluate(vars,funcs);
								terror = tfunc.GetError();
								if(terror==null)
								{
									String tvnames[] = tfunc.GetVarNames();
									outputbuf.append(name+"(");
									for(int i=tfunc.GetNumVars()-1;i>=0;i--)
									{
										outputbuf.append(tvnames[i]);
										if(i>0)
											outputbuf.append(",");
									}
									outputbuf.append(") = "+tfunc.GetFunction());
									
									lastasgnfunc = tfunc;
									lastcmdtype = CmdTypeAsgnFunc;
								}
								else
								{
									outputbuf.append(terror);
									funcs.remove(name);
									lastasgnfunc = null;
								}
							}
							else
							{
								outputbuf.append(terror);
								funcs.remove(name);
								lastasgnfunc = null;
							}
						}
						else
						{
							outputbuf.append(terror);
							funcs.remove(name);
							lastasgnfunc = null;
						}
					}
					else
					{
						// Variable
						if(reservednames.isDefined(name))
						{
							berror = true;
							errormsg.append("Cannot create variable named "+name+".");
							return;
						}
						EqVariable tvar = null;
						mainfunc.SetFunction(cmdbuffer.substring(eqind+1));
						String terror = mainfunc.GetError();
						if(terror==null)
						{
							tvar = mainfunc.Evaluate(vars,funcs);
							terror = mainfunc.GetError();
							if(terror==null&&tvar!=null)
							{
								vars.put(name, tvar);
								outputbuf.append(name + " = ");
								tvar.print(outputbuf);
							}
							else
								outputbuf.append(terror);
						}
						else
							outputbuf.append(terror);
						lastcmdtype = CmdTypeAsgnVar;
					}
				}
				else
				{
					berror = true;
					errormsg.append("Invalid name.");
					return;
				}
			}
		}
	}
	
	public String getError()
	{
		if(berror)
			return errormsg.toString();
		return null;
	}
	
	public String getOutput()
	{
		return outputbuf.toString();
	}
	
	public int GetLastCommandType()
	{
		return lastcmdtype;
	}
	
	public EqFunction GetFunction()
	{
		return mainfunc;
	}
	
	public EqFunction GetLastFunction()
	{
		return lastasgnfunc;
	}
	
	public EqFunction GetFunction(CharSequence ifname)
	{
		return funcs.get((String)ifname);
	}
	
	public HashMap<String,EqFunction> getFunctionList()
	{
		return funcs;
	}
	
	public int getNumVars()
	{
		return vars.size();
	}
	
	public HashMap<String,EqVariable> getVarList()
	{
		return vars;
	}
	
	public void addVariable(String ivname, String ivar)
	{
		vars.put(ivname, new EqVariable(Double.parseDouble(ivar)));
	}
	
	public void GeneratePlot(PlotView ipview,GraphedFuncs igfuncs)
	{
		int nfuncs = igfuncs.getNumFuncs();
		if(nfuncs<1)
			return;
		if(igfuncs.getGraphMode()==1)
		{
			ipview.setNumFuncs(nfuncs);
			ipview.setNumVars(1);
			ipview.resetVerts();
			double tmaxrange = Double.NEGATIVE_INFINITY;
			double tminrange = Double.POSITIVE_INFINITY;
			float domainx[] = ipview.getDomainX();
			float domainxlength = domainx[1]-domainx[0];
			int lsteps = ipview.getLineSteps();
			int fnum = 0;
			for(int f=0;f<5;f++)
			{
				EqFunction tfunc = igfuncs.getFunction(f);
				if(tfunc!=null)
				{
					for(int i=0;i<=lsteps;i++)
					{
						double x = domainx[0]+(double)i*((double)domainxlength/(double)(lsteps));
						tfunc.SetLocalVar(0, new EqVariable(x));
						double y=tfunc.Evaluate(vars,funcs).getValue();
						if(!Double.isInfinite(y))
						{
							if(y>tmaxrange)
								tmaxrange=y;
							if(y<tminrange)
								tminrange=y;
						}
						ipview.inputVert(i,(float)y,fnum);
					}
					float tfcolor[] = igfuncs.getRColor(f);
					ipview.setFuncColor(fnum, tfcolor);
					fnum++;
				}
			}
			if(Math.abs(tmaxrange-tminrange)<0.00001)
			{
				tmaxrange+=1;
				tminrange-=1;
			}
			ipview.setRangeY((float)tminrange, (float)tmaxrange);
		}
		else if(igfuncs.getGraphMode()==2)
		{
			ipview.setNumFuncs(nfuncs);
			ipview.setNumVars(2);
			ipview.resetVerts();
			double tmaxrange = Double.NEGATIVE_INFINITY;
			double tminrange = Double.POSITIVE_INFINITY;
			float domainx[] = ipview.getDomainX();
			float domainxlength = domainx[1]-domainx[0];
			float domainy[] = ipview.getDomainY();
			float domainylength = domainy[1]-domainy[0];
			int psteps = ipview.getPlaneSteps();
			EqFunction tfunc = igfuncs.getFunction(0);
			if(tfunc!=null)
			{
				for(int j=0;j<=psteps;j++)
				{
					double y = domainy[0]+(double)j*((double)domainylength/(double)(psteps));
					tfunc.SetLocalVar(1, new EqVariable(y));
					for(int i=0;i<=psteps;i++)
					{
						double x = domainx[0]+(double)i*((double)domainxlength/(double)(psteps));
						tfunc.SetLocalVar(0, new EqVariable(x));
						double z=tfunc.Evaluate(vars,funcs).getValue();
						if(!Double.isInfinite(z))
						{
							if(z>tmaxrange)
								tmaxrange=z;
							if(z<tminrange)
								tminrange=z;
						}
						ipview.inputVert(i,j,(float)x,(float)y,(float)z);
					}
				}
			}
			if(Math.abs(tmaxrange-tminrange)<0.00001)
			{
				tmaxrange+=1;
				tminrange-=1;
			}
			ipview.setRangeY((float)tminrange, (float)tmaxrange);
		}
		ipview.scalePlot();
	}
}
