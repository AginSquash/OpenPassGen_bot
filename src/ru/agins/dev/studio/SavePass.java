package ru.agins.dev.studio;

public class SavePass {
	public int ID;
	public Long ChatID=(long) 0;
	public String pass;	
	public boolean NotUsed=true;
	
	public int GetID(){	
	return ID;	
	}
	
	public Long GetChatID(){			
		return ChatID;	
		}
	
	public String Pass(){	
		return pass;	
		}
	
	public boolean NotUsed(){
		return NotUsed;
	}
}
