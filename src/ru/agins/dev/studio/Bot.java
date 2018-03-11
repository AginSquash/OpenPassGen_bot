package ru.agins.dev.studio;

import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import java.util.ArrayList;
import java.util.List;
import ru.agins.dev.studio.PassGen;
import ru.agins.dev.studio.SavePass;
import java.util.Timer;
import java.util.TimerTask;

public class Bot extends TelegramLongPollingBot {
 


public class sp1taskT extends TimerTask {

		@Override
		public void run() {
			sp1.NotUsed = true;
			sp1timer.cancel();
			
		}

	}

public class sp2taskT extends TimerTask {

	@Override
	public void run() {
		sp2.NotUsed = true;
		sp2timer.cancel();
	}

}

public class sp3taskT extends TimerTask {

	@Override
	public void run() {
		sp3.NotUsed = true;
		sp3timer.cancel();
	}

}
private Timer sp1timer, sp2timer, sp3timer;
String passOut, pass1;
Long[] passNowID = new Long [10];
SavePass sp1 = new SavePass();
SavePass sp2 = new SavePass();
SavePass sp3 = new SavePass();
TimerTask sp1task=new sp1taskT();
TimerTask sp2task=new sp2taskT();
TimerTask sp3task=new sp3taskT();

String[] passNow = new String[10];

boolean passExpert = false, num = false, let = false, sym = false, passReady = false, savePass = false, Inline = true;
int passExpertCH = 0, length = 0, passNowCH = 0;

	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(new Bot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
 
	//@Override
	public String getBotUsername() {
		return "Open Passwords Generator";
	}
 
	@Override
	public String getBotToken() {
		return "123:32313fdsdfs"; //your token here
	}
 
    @Override
    @SuppressWarnings("deprecation")
	public void onUpdateReceived(Update update) {
            
            //check if the update has a message
            if(update.hasMessage()){
                    Message message = update.getMessage();
                    
                    //check if the message has text. it could also  contain for example a location ( message.hasLocation() )
                    if(message.hasText()){
                            
                        //create a object that contains the information to send back the message
                    	//create a object that contains the information to send back the message
                        SendMessage answer = new SendMessage();
                        SendMessage password = new SendMessage();
                        answer.setChatId(message.getChatId().toString()); //who should get the message? the sender from which we got the message...
                        password.setChatId(message.getChatId().toString());
                        String ChatId = message.getChatId().toString();
                        Long ChatId2 = message.getChatId();
                       
                        if (savePass)
                        { 
                        			String SavedPass="Error Here";
                        			//Long ID = sp1.GetChatID();
                        			if (sp1.NotUsed==false)
                        			{
                        				SavedPass="here";
                        				if (Integer.parseInt(String.valueOf(sp1.GetChatID()))==Integer.parseInt(ChatId)){              //if (sp1.GetChatID()==message.getChatId()){                					
                        					SavedPass=sp1.pass;
                        					sp1.NotUsed=true;
                        					sp1timer.cancel();
                        				}
                        			}
                        			if (sp2.NotUsed==false)
                        			{
                        				if (Integer.parseInt(String.valueOf(sp2.GetChatID()))==Integer.parseInt(ChatId)){  
                        					SavedPass=sp2.pass;
                        					sp2.NotUsed=true;
                        					sp2timer.cancel();
                        				}
                        			}
                        			if (sp3.NotUsed==false)
                        			{
                        				if (Integer.parseInt(String.valueOf(sp3.GetChatID()))==Integer.parseInt(ChatId)){  
                        					SavedPass=sp3.pass;
                        					sp3.NotUsed=true;
                        					sp3timer.cancel();
                        				}
                        			}
                        			answer.setText("#opgpass " + message.getText() + ":");
                        			password.setText(SavedPass);
                        			passReady = true;
                        			savePass = false; 
                        			Inline = false;                        	
                        }
                        
                        if (passExpert)
                        {
                        	String ansText="• Use \"+\" or \"-\"\n• Do not use only \"-\"\n• Length need be more then 0";
                        	switch (passExpertCH){
                        		
                        	case 0:	switch (message.getText()){ case "+": num = true; ansText ="What about let?"; break; case "-": num = false; ansText="What about let?"; break; default: passExpert=false; passExpertCH = -1; break;} passExpertCH++; break;                     
                        	case 1: switch (message.getText()){ case "+": let = true; ansText = "symbols?"; break; case "-": let = false; ansText = "symbols?"; break; default: passExpert=false; passExpertCH = -1; break;} passExpertCH++; break;
                        	case 2: switch (message.getText()){ case "+": sym = true; ansText = "length here:"; break; case "-": sym = false; ansText = "length here:"; break; default: passExpert=false; passExpertCH = -1; break;} passExpertCH++; break;
                        	case 3:		
                        				if (isDigit(message.getText())&&((num==true)||(let==true)||(sym==true)))
                        				{
                        					String Pass;
                        					length = Integer.parseInt(message.getText());
                        					passExpertCH = 0;
                        					passExpert=false;
                        					PassGen PG = new PassGen();
                        					Pass = PG.PassGenMain(num, let, sym, length);
                        					answer.setText("Your password here:");
                        					spChecker(ChatId2, Pass);   
                        					password.setText(Pass);
                        					passReady = true;
                        					passOut = "";
                        				}else{
                            				passExpertCH = 0;
                            				passExpert=false;
                        				}
                        				break;
                        	}
                        	if (passReady != true){
                        		answer.setText(ansText);
                        	}
                        }
                        	
                        
                        switch (message.getText()){
                        
                        	case "/pass": 
                        					spCheckerId(ChatId);
                        					String Pass;
                        					PassGen PG = new PassGen();
                        					Pass = PG.PassGenMain(true, true, false, 12);
                        					spChecker(ChatId2, Pass);                       				  
                        				  	answer.setText("We used defalut prop:");
                        				  	password.setText(Pass);
                        				  	passReady = true;
                        				  	passOut = "";
                        				  break;
                        	case "/passexpert":
                        			//spCheckerId(ChatId2);
                        			passOut = "";
                        			passExpert = true;
                        			      
                        			answer.setText("Do you wanna use num?");
                        		break;
                        	
                        }
                       
                       try {

                          sendMessage(answer); 			//at the end, so some magic and send the message ;)
                                    if (passReady)
                                    {
                                    	if (Inline) {
                                    		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                                    		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                                    		List<InlineKeyboardButton> rowInline = new ArrayList<>();
                                    		rowInline.add(new InlineKeyboardButton().setText("Save this pass").setCallbackData("save_msg"));
                                    		// Set the keyboard to the markup
                                    		rowsInline.add(rowInline);
                                    		// Add it to the message
                                    		markupInline.setKeyboard(rowsInline);
                                    		password.setReplyMarkup(markupInline);
                                    	}
                                    	sendMessage(password);
                                    	passOut="";
                                    	passReady=false;
                                    	Inline = true;
                                    }
                                    
                       } catch (TelegramApiException e) {
                            		e.printStackTrace();
                                    
                            }//end catch()
                    }//end if()
            }else if (update.hasCallbackQuery()) {
            	String call_data = update.getCallbackQuery().getData();
            	SendMessage answer = new SendMessage();
                answer.setChatId(update.getCallbackQuery().getMessage().getChatId());
                if (call_data.equals("save_msg"))
                {
                	savePass = true;
                	answer.setText("Enter name for this pass: ");
                }
                
                try {

                    sendMessage(answer);           
                 } catch (TelegramApiException e) {
                      		e.printStackTrace();
                              
                      }//end catch()
            }
            
    }//end onUpdateReceived()
 
    public void spChecker(long ChatId2, String Pass){
    	
    	if (sp1.NotUsed)
	  	{
    		if (sp1.ChatID!=0){
    			sp1timer.cancel();
    	  		sp1timer.purge();
    	  		sp1timer=null;
    	  		
    		}
	  		sp1.ChatID=ChatId2; 		  		
	  		sp1.pass=Pass;	  	
	  		sp1.NotUsed=false;	
	  		
	  		
	  		
	  		sp1task = new sp1taskT();
	  		sp1timer = new Timer();
	  		sp1timer.scheduleAtFixedRate(sp1task,300000, 300000);
	  		
	  	}
	  	if (sp2.NotUsed)
	  	{
	  		sp2.ChatID=ChatId2;
	  		sp2.pass=Pass;
	  		sp2.NotUsed=false;
	  		sp2task = new sp2taskT();
	  		sp2timer = new Timer();
	  		
	  		sp2timer.schedule(sp2task, 300000);
	  	}
	  	if (sp3.NotUsed)
	  	{
	  		sp3.ChatID=ChatId2;
	  		sp3.pass=Pass;
	  		sp3.NotUsed=false;
	  		sp3timer = new Timer();
	  		sp3timer.schedule(sp3task, 300000);
	  	}
	  	
    }
    
    public void spCheckerId(String ChatId){

    	
    	if (Integer.parseInt(String.valueOf(sp1.GetChatID()))==Integer.parseInt(ChatId)){        				
			sp1.NotUsed=true;
			//sp1timer.cancel();
			//sp1timer = new Timer();
			//sp1task = new sp1taskT();
	  	//	sp1timer.schedule(sp1task, 300000);
			
		}
    	
    	if (Integer.parseInt(String.valueOf(sp2.GetChatID()))==Integer.parseInt(String.valueOf(ChatId))){            				
			sp2.NotUsed=true;

			
		}
    	
    	if (Integer.parseInt(String.valueOf(sp3.GetChatID()))==Integer.parseInt(String.valueOf(ChatId))){            				
			sp3.NotUsed=true;

    	}
   	
    }
    
	//@SuppressWarnings("deprecation")
	private void sendMsg(Message message, String text) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(message.getChatId().toString());
		sendMessage.setReplyToMessageId(message.getMessageId());
		sendMessage.setText(message.getText());
		try {
			
			sendMessage(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	
	private static boolean isDigit(String s) throws NumberFormatException {
	    try {
	        Integer.parseInt(s);
	        if (Integer.parseInt(s)>0)
	        {
	        return true;
	        } else {return false;}
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
 
}
