/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payaraConsoleTest;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import static junit.framework.Assert.assertEquals;
import org.glassfish.api.admin.*;
import org.glassfish.api.*;
//import org.glassfish.embeddable.CommandRunner;
//import org.glassfish.embeddable.*;


/**
 *
 * @author Daniel
 */


public class deleteTest {
    
    CommandRunner runner=server.getHabitat();
    
    
    
    //NOTE: delete Resource must be done before delete cluster of an "unexpected error" will occur!
    public void deleteResource(){
        try {
            WebClient webClient = new webClientInit().getWebClient();

            HtmlPage resourcePage = webClient.getPage("http://localhost:4848/full/customResources.jsf");
            assertEquals("Custom Resources",resourcePage.getTitleText());
            resourcePage.getElementById("propertyForm:resourcesTable:rowGroup1:0:col0:select").click();
            
        } catch (IOException io){
                System.out.println(io);
        } //end catch
    } //end deleteResource
    
    public void deleteCluster(){
        try {
            WebClient webClient = new webClientInit().getWebClient();

            HtmlPage clusterPage = webClient.getPage("http://localhost:4848/cluster/cluster/clusters.jsf");
            assertEquals("Clusters",clusterPage.getTitleText());
            clusterPage.getElementById("propertyForm:clustersTable:rowGroup1:0:col0:select").click();
            
        } catch (IOException io){
                System.out.println(io);
        } //end catch
    } //end deleteCluster
    
    public void deleteNode(){
        try {
            WebClient webClient = new webClientInit().getWebClient();

            HtmlPage nodePage = webClient.getPage("http://localhost:4848/cluster/node/nodes.jsf");
            assertEquals("Nodes",nodePage.getTitleText());
            nodePage.getElementById("propertyForm:nodesTable:rowGroup1:0:col0:select").click();
            
        } catch (IOException io){
                System.out.println(io);
        } //end catch
    } //end deleteCluster
    
    
    
} //end class
