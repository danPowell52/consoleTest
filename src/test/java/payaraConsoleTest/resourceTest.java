/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payaraConsoleTest;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import java.io.IOException;
import static junit.framework.Assert.assertEquals;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class resourceTest {
    @Test
    @InSequence(4)
    public void newResourceTest(){
        try {
            //create a webclient to act on
            WebClient webClient = new webClientInit().getWebClient();
            
            //go to new resource page as the button test doesnt work
            HtmlPage resourcePage = webClient.getPage("http://localhost:4848/full/customResourceNew.jsf");
            assertEquals("New Custom Resource", resourcePage.getTitleText());
            //assign a resource name
            HtmlInput resourceName = (HtmlInput)resourcePage.getElementById("form:propertySheet:propertSectionTextField:jndiTextProp:jnditext");
            resourceName.setValueAttribute("testResource2");
            //assign a factory class name
            HtmlInput factoryName = (HtmlInput)resourcePage.getElementById("form:propertySheet:propertSectionTextField:factoryClassProp:factoryClass");
            factoryName.setValueAttribute("testFactory2");
            //assign a target
            HtmlSelect targetClusters = (HtmlSelect)resourcePage.getElementById("form:targetSection:targetSectionId:addRemoveProp:commonAddRemove_available");
            HtmlOption target = targetClusters.getOptionByText("danCluster1");
            targetClusters.setSelectedAttribute(target, true);
            resourcePage.getElementById("form:targetSection:targetSectionId:addRemoveProp:commonAddRemove:commonAddRemove_addButton").click();
            //create resource
            
        
        
        
            resourcePage.getElementById("form:propertyContentPage:topButtons:newButton").click();
        
        } catch (IOException io){
                System.out.println(io);
        }
    }
    
    //for some reason if there is not 2 tests the code doesnt work
    @Test    
    public void newResourceTest2(){
        try {
            //create a webclient to act on
            WebClient webClient = new webClientInit().getWebClient();
            
            //go to new resource page as the button test doesnt work
            HtmlPage resourcePage = webClient.getPage("http://localhost:4848/full/customResourceNew.jsf");
            assertEquals("New Custom Resource", resourcePage.getTitleText());
            //assign a resource name
            HtmlInput resourceName = (HtmlInput)resourcePage.getElementById("form:propertySheet:propertSectionTextField:jndiTextProp:jnditext");
            resourceName.setValueAttribute("testResource");
            //assign a factory class name
            HtmlInput factoryName = (HtmlInput)resourcePage.getElementById("form:propertySheet:propertSectionTextField:factoryClassProp:factoryClass");
            factoryName.setValueAttribute("testFactory");
            //assign a target
            HtmlSelect targetClusters = (HtmlSelect)resourcePage.getElementById("form:targetSection:targetSectionId:addRemoveProp:commonAddRemove_available");
            HtmlOption target = targetClusters.getOptionByText("danCluster1");
            targetClusters.setSelectedAttribute(target, true);
            resourcePage.getElementById("form:targetSection:targetSectionId:addRemoveProp:commonAddRemove:commonAddRemove_addButton").click();
            //create resource
            
        
        
        
            resourcePage.getElementById("form:propertyContentPage:topButtons:newButton").click();
        
        } catch (IOException io){
                System.out.println(io);
        }
    }
}
