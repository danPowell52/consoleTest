/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payaraConsoleTest;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 *
 * @author Daniel
 */
public class webClientInit {
    private WebClient webClient;
    public webClientInit(){
        //create a webclient to act on
            webClient = new WebClient(BrowserVersion.CHROME);
            //prevent spurious 404 errors
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            //webClient.getOptions().setThrowExceptionOnScriptError(false);
            
    }
     public WebClient getWebClient(){
         return webClient;
     }
}
