package com.lotech.tubesurfer;

public class PhysicsContactList
{
	private int ncontacts,maxncontacts;
	private PhysicsContact contacts[];
	
	public PhysicsContactList(int imaxncontacts)
	{
		ncontacts = 0;
		maxncontacts = imaxncontacts;
		contacts = new PhysicsContact[maxncontacts];
		for(int i=0;i<maxncontacts;i++)
			contacts[i] = new PhysicsContact();
	}
	
	public void Clear()
	{
		ncontacts=0;
	}
	
	public PhysicsContact GetNewContact()
	{
		if(ncontacts<maxncontacts)
		{
			return contacts[ncontacts++];
		}
		return null;
	}
	
	public int GetNContacts()
	{
		return ncontacts;
	}
	
	public PhysicsContact GetContact(int ind)
	{
		if(ind>ncontacts||ind<0)
			return null;
		return contacts[ind];
	}
}
