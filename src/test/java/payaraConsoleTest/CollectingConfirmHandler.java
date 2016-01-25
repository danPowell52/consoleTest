package payaraConsoleTest;


import com.gargoylesoftware.htmlunit.ConfirmHandler;
import com.gargoylesoftware.htmlunit.Page;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniel
 */
public class CollectingConfirmHandler implements ConfirmHandler{
    
    //public CollectingConfirmHandler(){
    //}
    
    @Override
    public boolean handleConfirm(Page page, String message){
        return true;
    }
}
