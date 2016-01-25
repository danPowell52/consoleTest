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
public class nodeTest {
    @Test
    @InSequence(1)
    public void createNode(){
            try {
                WebClient webClient = new webClientInit().getWebClient();
                

                //finds node 
                HtmlPage nodePage = webClient.getPage("http://localhost:4848/cluster/node/nodes.jsf");
                assertEquals("Nodes", nodePage.getTitleText());
                
                //for some obscure reason this code must be here or the newNodePage will not be found
                HtmlPage clusterPage = webClient.getPage("http://localhost:4848/cluster/cluster/clusters.jsf");
                assertEquals("Clusters", clusterPage.getTitleText());
                
                HtmlPage newNodePage = nodePage.getElementById("propertyForm:nodesTable:topActionsGroup1:newButton").click();
                assertEquals("New Node", newNodePage.getTitleText());
                HtmlInput nodeName = (HtmlInput)newNodePage.getElementById("propertyForm:propertySheet:propertSectionTextField:nameProp:name");
                nodeName.setValueAttribute("dummyTestNode1");
                HtmlSelect nodeType = (HtmlSelect)newNodePage.getElementById("propertyForm:propertySheet:propertSectionTextField:typeProp:type");
                HtmlOption configType= nodeType.getOptionByText("CONFIG");
                nodeType.setSelectedAttribute(configType, true);
                HtmlInput nodeHost = (HtmlInput)newNodePage.getElementById("propertyForm:propertySheet:propertSectionTextField:NodeHost:NodeHost");
                nodeHost.setValueAttribute("localhost");
                newNodePage.getElementById("propertyForm:propertyContentPage:topButtons:newButton").click();
    
            } catch (IOException io){
                System.out.println(io);
            }
        }
    
    
}
