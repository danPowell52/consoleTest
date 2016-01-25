/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payaraConsoleTest;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import java.io.IOException;
import junit.framework.Assert;
import static junit.framework.Assert.assertEquals;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;
/**
 *
 * @author Daniel
 */
public class instanceTest {
    
    @Test
    @InSequence(3)
    public void createInstanceTest(){
        try {
            //create a webclient to act on
            WebClient webClient = new webClientInit().getWebClient();
            
            //go to the new isntance page for danCluster
            HtmlPage instanceForm = webClient.getPage("http://localhost:4848/cluster/cluster/clusterInstanceNew.jsf?clusterName=danCluster1");
            assertEquals("New Clustered Server Instance", instanceForm.getTitleText());
            
            //create the instance 
            HtmlInput instanceName = (HtmlInput)instanceForm.getElementById("propertyForm:propertySheet:propertSectionTextField:NameTextProp:NameText");
            instanceName.setValueAttribute("danInstance1");
            instanceForm.getElementById("propertyForm:propertyContentPage:topButtons:newButton").click();
        } catch (IOException io){
                System.out.println(io);
        }
    }
}
