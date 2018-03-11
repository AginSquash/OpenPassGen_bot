package ru.agins.dev.studio;

// I used this lib: https://github.com/rubenlagus/TelegramBots

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

public class Bot extends TelegramLongPollingBot {
	
String pass;
int AdminID = 1234567; //My ChatID		!!!!!!!!!!!!
boolean passExpert = false, num = false, let = false, sym = false, passReady = false, savePass = false, Inline = true, nonUsed=true;;
int passExpertCH = 0, length = 0;

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
		return "123:344234fgds"; //Your token here		!!!!!!!!!!!!
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
                        SendMessage answer = new SendMessage();
                        SendMessage password = new SendMessage();
                        answer.setChatId(message.getChatId().toString()); //who should get the message? the sender from which we got the message...
                        password.setChatId(message.getChatId().toString());
                        String ChatId = message.getChatId().toString();
                       
                        if (savePass)
                        { 
                        			String SavedPass="U have no access. Your ID: " + message.getChatId().toString();
                        			if (Integer.parseInt(ChatId) == AdminID)
                        			{
                        				SavedPass = pass;
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
                        	case 1: switch (message.getText()){ case "+": let = true; ansText = "Symbols?"; break; case "-": let = false; ansText = "Symbols?"; break; default: passExpert=false; passExpertCH = -1; break;} passExpertCH++; break;
                        	case 2: switch (message.getText()){ case "+": sym = true; ansText = "Length here:"; break; case "-": sym = false; ansText = "Length here:"; break; default: passExpert=false; passExpertCH = -1; break;} passExpertCH++; break;
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
                        					pass = Pass;
                        					password.setText(Pass);
                        					passReady = true;
                        					
                        					
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
                        					String Pass;
                        					PassGen PG = new PassGen();
                        					Pass = PG.PassGenMain(true, true, false, 12);               				  
                        				  	answer.setText("We used defalut prop:");
                        				  	password.setText(Pass);
                        				  	pass = Pass;
                        				  	passReady = true;
                        				  	nonUsed = false;

                        				    break;
                        	case "/passexpert":
                        					passExpert = true;                      			      
                        					answer.setText("Do you wanna use num?");
                        					nonUsed = false;
                        					break;
                        		
                        	default: 		if (nonUsed){
                        					answer.setText("Bad command");
                        					passReady = false;
                        					}
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
                                    	nonUsed=true;
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
                	nonUsed = false;
                }
                
                try {

                    sendMessage(answer);           
                 } catch (TelegramApiException e) {
                      		e.printStackTrace();
                              
                      }//end catch()
            }
            
    }//end onUpdateReceived()
 
    
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
