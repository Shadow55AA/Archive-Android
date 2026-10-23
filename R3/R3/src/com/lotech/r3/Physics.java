package com.lotech.r3;

public class Physics
{
	public static void ResolveImpulses(PhysicsContactList icontactlist)
	{
		PhysicsObject obja,objb;
		float tvec[] = {0,0,0,0};
		float vela[] = {0,0,0};
		float angvela[] = {0,0,0};
		float velb[] = {0,0,0};
		float rvel[] = {0,0,0};
		float ra[] = {0,0,0};
		float rb[] = {0,0,0};
		float iia[] = {1,0,0, 0,1,0, 0,0,1};
		float iib[] = {1,0,0, 0,1,0, 0,0,1};
		float j = 0, e = 0.0f;
		float denom = 0;
		int ncs = icontactlist.GetNContacts();
		for(int i=0;i<ncs;i++)
		{
			PhysicsContact cp = icontactlist.GetContact(i);
			obja = cp.obja;
			objb = cp.objb;
			for(int c=0;c<3;c++)
				rvel[c] = obja.linvel[c]-objb.linvel[c];
			j = rvel[0]*cp.norm[0]+rvel[1]*cp.norm[1]+rvel[2]*cp.norm[2];
			if(j<-0.1)
			{
				for(int c=0;c<3;c++)
				{
					ra[c]=cp.point[c]-obja.pos[c];
					rb[c]=cp.point[c]-objb.pos[c];
				}
				LAMatrix.MMRotate3(iia, obja.invinertia, obja.rot);
				LAMatrix.MMRotate3(iib, objb.invinertia, objb.rot);
				denom = 0;
				if(obja.mass>0)
				{
					LAVector.Vec3Cross(tvec, ra,cp.norm);
					LAMatrix.VmulM3(tvec, tvec, iia);
					LAVector.Vec3Cross(tvec, tvec,ra);
					denom+=1/obja.mass+LAVector.Vec3Dot(cp.norm,tvec);
				}
				if(objb.mass>0)
				{
					LAVector.Vec3Cross(tvec, rb,cp.norm);
					LAMatrix.VmulM3(tvec, tvec, iib);
					LAVector.Vec3Cross(tvec, tvec,rb);
					denom+=1/objb.mass+LAVector.Vec3Dot(cp.norm,tvec);
				}
				e = (obja.contactbounce+objb.contactbounce)/2.0f;
				j = -(1+e)*j/denom;
				for(int c=0;c<3;c++)
				{
					vela[c] = obja.linvel[c]+j*cp.norm[c]/obja.mass;
					velb[c] = objb.linvel[c]-j*cp.norm[c]/objb.mass;
				}
				LAVector.Vec3Cross(angvela, ra, cp.norm);
				LAMatrix.VmulM3(angvela, angvela, iia);
				for(int c=0;c<3;c++)
				{
					angvela[c] = obja.angvel[c]+j*angvela[c];
				}
				for(int c=0;c<3;c++)
				{
					if(obja.mass>0)
					{
						obja.linvel[c]=vela[c];
						obja.angvel[c]=angvela[c];
					}
					if(objb.mass>0)
						objb.linvel[c]=velb[c];
				}
			}
		}
	}
	
	public static void SpringContactForces(PhysicsContactList icontactlist,float dt)
	{
		PhysicsObject obja,objb;
		float vela[] = {0,0,0};
		float velb[] = {0,0,0};
		float f = 0;
		int ncs = icontactlist.GetNContacts();
		for(int i=0;i<ncs;i++)
		{
			PhysicsContact cp = icontactlist.GetContact(i);
			obja = cp.obja;
			objb = cp.objb;
			f = -cp.pdepth*1000;
			if(f>0)
			{
				for(int c=0;c<3;c++)
				{
					vela[c] = obja.linvel[c]+dt*f*cp.norm[c]/obja.mass;
					velb[c] = objb.linvel[c]-dt*f*cp.norm[c]/objb.mass;
				}
				for(int c=0;c<3;c++)
				{
					if(obja.mass>0)
						obja.linvel[c]=vela[c];
					if(objb.mass>0)
						objb.linvel[c]=velb[c];
				}
			}
		}
	}
	
	public static void CalcInertiaSphere(float inertia[],float radius,float mass)
	{
		float si = 0.4f*mass*radius*radius;
		for(int i=0;i<9;i++)
			inertia[i]=0;
		inertia[0] = si;
		inertia[4] = si;
		inertia[8] = si;
	}
	
	public static void CalcInertiaCapsule(float inertia[],float csize[],float mass)
	{
		float spvol = (4.0f/3.0f)*(float)Math.PI*csize[0]*csize[0]*csize[0];
		float cyvol = (float)Math.PI*csize[0]*csize[0]*csize[1];
		float cymass = mass*cyvol/(spvol+cyvol);
		float capmass = (mass-cymass)/2.0f;
		float hl = csize[1]/2.0f;
		float ia = 2.0f*(0.259f*capmass*csize[0]*csize[0]+capmass*(9.0f/64.0f+hl*hl));
		float ib = 0.4f*capmass*csize[0]*csize[0];
		ia+=1.0f/12.0f*cymass*(3*csize[0]*csize[0]+csize[1]*csize[1]);
		ib+=cymass*csize[0]*csize[0]/2.0f;
		for(int i=0;i<9;i++)
			inertia[i]=0;
		inertia[0] = ia;
		inertia[4] = ia;
		inertia[8] = ib;
	}
}
