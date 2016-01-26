/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payaraConsoleTest;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ConfirmHandler;
import com.gargoylesoftware.htmlunit.Page;
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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 *
 * @author Daniel
 */
public class clusterTest {
    WebClient webClient = new webClientInit().getWebClient();
    
    @Test
    @InSequence(2)
    public void createClusterTest(){
        try {
            
            
            //go to new cluster page and create a new cluster
                
            HtmlPage newClusterPage = webClient.getPage("http://localhost:4848/cluster/cluster/clusterNew.jsf");
            assertEquals("New Cluster", newClusterPage.getTitleText());
            HtmlInput clusterName = (HtmlInput)newClusterPage.getElementById("propertyForm:propertySheet:propertySectionTextField:NameTextProp:NameText");
            clusterName.setValueAttribute("danCluster1");
            newClusterPage.getElementById("propertyForm:propertyContentPage:topButtons:newButton").click();
            
            //Selenium();
            //startup();
            //startup2();
            
        } catch (IOException io){
            System.out.println(io);
        } //end try
    }//end method
    
    //attempt to overwrite confirm function
    public void startup(){
        try {
            HtmlPage startClusterPage = webClient.getPage("http://localhost:4848/cluster/cluster/clusters.jsf");
            assertEquals("Clusters", startClusterPage.getTitleText());
            startClusterPage.executeJavaScript("window.confirm = function(msg) {return true;};");
            startClusterPage.getElementById("propertyForm:clustersTable:rowGroup1:0:col0:select").click();
            HtmlPage confirmPage = startClusterPage.getElementById("propertyForm:clustersTable:topActionsGroup1:button2").click();
            confirmPage.executeJavaScript("window.confirm = function(msg) {return true;};");
        }catch(IOException io){
            System.out.println(io);
        }//close try    
    } //close startup method
    
    //attempt to overwrite confirm function
    public void startup2(){
        try {
            ConfirmHandler okHandler = new CollectingConfirmHandler();
            webClient.setConfirmHandler(okHandler);
            
            HtmlPage startClusterPage = webClient.getPage("http://localhost:4848/cluster/cluster/clusters.jsf");
            assertEquals("Clusters", startClusterPage.getTitleText());
            
            startClusterPage.getElementById("propertyForm:clustersTable:rowGroup1:0:col0:select").click();
            startClusterPage.getElementById("propertyForm:clustersTable:topActionsGroup1:button2").click();
        }catch(IOException io){
            System.out.println(io);
        }//close try    
    } //close startup method

    public void Selenium(){
        WebDriver driver = new HtmlUnitDriver();
        
        driver.get("http://localhost:4848/cluster/cluster/clusters.jsf");
        String title = driver.getTitle();
        assertEquals("CLusters",title);
    }
    
} //end class
