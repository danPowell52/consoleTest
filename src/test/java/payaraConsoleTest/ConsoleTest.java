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
 * @author user
 */
public class ConsoleTest {
    String URL = "http://localhost:5048";
    
    @Test
    public void control() throws IOException, InterruptedException{
        
        createNode();
        createClusterTest();
        createInstanceTest();
        newResourceTest();
        deleteChangesTest();
        
    }
    
    
    public void createNode(){
        System.out.println("THIS IS NUMBER 1");
            try {
                WebClient webClient = new webClientInit().getWebClient();
                

                //finds node 
                HtmlPage nodePage = webClient.getPage(URL+"/cluster/node/nodes.jsf");
                assertEquals("Nodes", nodePage.getTitleText());
                
                //for some obscure reason this code must be here or the newNodePage will not be found
                HtmlPage clusterPage = webClient.getPage(URL+"/cluster/cluster/clusters.jsf");
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
    
    
    
    public void createClusterTest(){
        WebClient webClient = new webClientInit().getWebClient();
        System.out.println("THIS IS NUMBER 2");
        try {
            
            
            //go to new cluster page and create a new cluster
                
            HtmlPage newClusterPage = webClient.getPage(URL+"/cluster/cluster/clusterNew.jsf");
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
    
    
      
    public void createInstanceTest(){
        try {
            System.out.println("THIS IS NUMBER 3");
            //create a webclient to act on
            WebClient webClient = new webClientInit().getWebClient();
            
            //go to the new isntance page for danCluster
            HtmlPage instanceForm = webClient.getPage(URL+"/cluster/cluster/clusterInstanceNew.jsf?clusterName=danCluster1");
            assertEquals("New Clustered Server Instance", instanceForm.getTitleText());
            
            //create the instance 
            HtmlInput instanceName = (HtmlInput)instanceForm.getElementById("propertyForm:propertySheet:propertSectionTextField:NameTextProp:NameText");
            instanceName.setValueAttribute("danInstance1");
            instanceForm.getElementById("propertyForm:propertyContentPage:topButtons:newButton").click();
        } catch (IOException io){
                System.out.println(io);
        }
    }
    
    
    
    public void newResourceTest() throws InterruptedException{
        System.out.println("THIS IS NUMBER 4");
        try {
            //Thread.sleep(25000);
            //create a webclient to act on
            WebClient webClient = new webClientInit().getWebClient();
            
            //go to new resource page as the button test doesnt work
            HtmlPage resourcePage = webClient.getPage(URL+"/full/customResourceNew.jsf");
            assertEquals("New Custom Resource", resourcePage.getTitleText());
            //assign a resource name
            HtmlInput resourceName = (HtmlInput)resourcePage.getElementById("form:propertySheet:propertSectionTextField:jndiTextProp:jnditext");
            resourceName.setValueAttribute("testResource2");
            //assign a factory class name
            HtmlInput factoryName = (HtmlInput)resourcePage.getElementById("form:propertySheet:propertSectionTextField:factoryClassProp:factoryClass");
            factoryName.setValueAttribute("testFactory2");
            //assign a target
            HtmlSelect targetClusters = (HtmlSelect)resourcePage.getElementById("form:targetSection:targetSectionId:addRemoveProp:commonAddRemove_available");
            HtmlOption target = targetClusters.getOptionByText("server");
            targetClusters.setSelectedAttribute(target, true);
            resourcePage.getElementById("form:targetSection:targetSectionId:addRemoveProp:commonAddRemove:commonAddRemove_addButton").click();
            //create resource
            
        
        
        
            resourcePage.getElementById("form:propertyContentPage:topButtons:newButton").click();
        
        } catch (IOException io){
                System.out.println(io);
        }
    }
    
    
    public void deleteChangesTest() throws IOException, InterruptedException {
        
        System.out.println("THIS IS NUMBER 5");
        //get the path of the java project and then run the bash script
        String path = System.getProperty("user.dir");
        String scriptPath = path + "/src/test/java/payaraConsoleTest/";
        Process bash = Runtime.getRuntime().exec("bash "+scriptPath+"deleteChangesScript");
        bash.waitFor();
        
    }
}
