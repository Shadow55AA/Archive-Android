package com.atl.equation;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Alex on 4/29/2017.
 */
public class EqVariable {
    private double[] values = {0d};
    private int n,m;

    public EqVariable()
    {
        n=1;m=1;
    }

    public EqVariable(double ivalue)
    {
        n=1;m=1;
        values[0]=ivalue;
    }

    public EqVariable(EqVariable ivar)
    {
        if(ivar==null) {
            n=1;m=1;
            return;
        }
        n=ivar.n;
        m=ivar.m;
        values = new double[n*m];
        for(int i=0;i<n*m;i++)
            values[i]=ivar.values[i];
    }

    public void setValue(double ivalue) {values[0]=ivalue;}

    public void setValue(int ind,double ivalue) {if(ind>=0&&ind<n*m) values[ind]=ivalue;}

    public boolean setValues(int in,int im, ArrayList<EqVariable> inumstack)
    {
        n=in;
        m=im;
        int tsize = n*m;
        values = new double[tsize];
        for(int i=tsize-1;i>=0;i--)
        {
            if(inumstack.size()>0) {
                EqVariable tvar =  inumstack.remove(inumstack.size() - 1);
                if(tvar.isScalar())
                    values[i] = tvar.getValue();
                else
                    return true;
            }
            else
                return true;
        }
        return false;
    }
    public double getValue() {return values[0];}

    public double getValue(int ind) {
        if(ind>=0&&ind<n*m)
            return values[ind];
        return 0;
    }

    public int getN() {return n;}
    public int getM() {return m;}

    public boolean isScalar() {if(n==1&&m==1)return true; return false;}

    public boolean isVector() {if((n==1&&m>1)||(n>1&&m==1)) return true; return false;}

    public void print(StringBuffer ioutbuff)
    {
        if(n==1&&m==1) {
            ioutbuff.append(values[0]);
            return;
        }
        ioutbuff.append('[');
        if(m>1)
            ioutbuff.append('[');
        for(int j=0;j<m;j++) {
            for (int i = 0; i < n; i++) {
                ioutbuff.append(values[i+j*n]);
                if(i!=n-1)
                    ioutbuff.append(",");
            }
            if(m>1&&j!=m-1)
                ioutbuff.append("][");
        }
        ioutbuff.append(']');
        if(m>1)
            ioutbuff.append(']');
    }

    public boolean add(EqVariable b)
    {
        if(n!=b.n||m!=b.m)
            return true;
        for(int i=0;i<n*m;i++)
            values[i]+=b.values[i];
        return false;
    }

    public void negate()
    {
        for(int i=0;i<n*m;i++)
            values[i]=-values[i];
    }

    public void transpose()
    {
        if(n*m==1)
            return;
        if(isVector())
        {
            int tn = n;
            n=m;
            m=tn;
            return;
        }
        double newvalues[] = new double[n*m];
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                newvalues[j+i*m]=values[i+j*n];
            }
        }
        int tn = n;
        n=m;
        m=tn;
        values=newvalues;
    }

    public double determinant()
    {
        if(n!=m)
            return 0;
        if(n==1)
        {
            return 1;
        }
        if(n==2)
            return values[0]*values[3]-values[1]*values[2];
        if(n==3)
            return values[0]*values[4]*values[8]+values[1]*values[5]*values[6]+values[2]*values[3]*values[7]
                    -values[2]*values[4]*values[6]-values[1]*values[3]*values[8]-values[0]*values[5]*values[7];
        return 1;
    }

    public boolean inverse()
    {
        if(n!=m)
            return true;
        if(n==1) {
            values[0] = 1d / values[0];
            return false;
        }
        if(n==2)
        {
            double det = determinant();
            if(det==0d)
                return true;
            double d = values[0]/det;
            values[0]=values[3]/det;
            values[1]=-values[1]/det;
            values[2]=-values[2]/det;
            values[3]=d;
            return false;
        }
        return true;
    }

    public boolean multiply(EqVariable b)
    {
        if(isScalar()&&b.isScalar())
        {
            values[0]=values[0]*b.values[0];
            return false;
        }
        if(isScalar()&&!b.isScalar())
        {
            double tvalue = values[0];
            n=b.n;
            m=b.m;
            values = new double[n*m];
            for(int i=0;i<n*m;i++)
                values[i]=b.values[i]*tvalue;
            return false;
        }
        if(!isScalar()&&b.isScalar())
        {
            for(int i=0;i<n*m;i++)
                values[i]*=b.values[0];
            return false;
        }
        if(n!=b.m)
            return true;
        int tsize = m*b.n;
        double newvalues[] = new double[tsize];
        double tsum = 0d;
        for(int j=0;j<m;j++)
        {
            for(int i=0;i<b.n;i++)
            {
                tsum=0d;
                for(int x=0;x<n;x++)
                {
                    tsum+=values[x+j*n]*b.values[i+x*b.n];
                }
                newvalues[i+j*b.n]=tsum;
            }
        }
        values = newvalues;
        n=b.n;
        return false;
    }

    public boolean divide(EqVariable b)
    {
        if(isScalar()&&b.isScalar())
        {
            values[0]=values[0]/b.values[0];
            return false;
        }
        else if(!isScalar()&&b.isScalar())
        {
            int tsize = n*m;
            for(int i=0;i<tsize;i++)
                values[i]=values[i]/b.values[0];
            return false;
        }
        else
        {
            if(b.inverse())
                return true;
            return multiply(b);
        }
    }

}
