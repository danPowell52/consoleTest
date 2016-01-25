/*

 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright (c) 2015 C2B2 Consulting Limited. All rights reserved.

 The contents of this file are subject to the terms of the Common Development
 and Distribution License("CDDL") (collectively, the "License").  You
 may not use this file except in compliance with the License.  You can
 obtain a copy of the License at
 https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 or packager/legal/LICENSE.txt.  See the License for the specific
 language governing permissions and limitations under the License.

 When distributing the software, include this License Header Notice in each
 file and include the License file at packager/legal/LICENSE.txt.
 */
package payara.fish.consoletest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class containing methods used to run bash scripts
 * @author Andrew Pielage
 */
public class ExecuteBash
{
    /**
     * Runs a bash script
     * @param scriptName
     * @return A list containing the output of the bash script
     */
    public List<List<String>> runScript(String scriptName)
    {       
        // Parse script into usable format
        BashScriptParser scriptParser = new BashScriptParser();
        List<String> asadminCommands = scriptParser.parseScript(scriptName);  
        
        Process bashProcess = null;
        List<List<String>> scriptOutput = new ArrayList<>();
        
        for (String command : asadminCommands)
        {
            List<String> processOutput = new ArrayList<>();
            
            // Add command name to identify process output
            processOutput.add(scriptParser.commandExtractor(command));
            
            try
            {
                bashProcess = Runtime.getRuntime().exec(command);
                bashProcess.waitFor();
                
                try (BufferedReader br = new BufferedReader(new 
                    InputStreamReader(bashProcess.getInputStream())))
                {
                    while (br.ready())
                    {
                        String line = br.readLine();
//                        System.out.println("TESTING - " + scriptParser.commandExtractor(command) + " - " + line);
                        processOutput.add(line);
                    }
                } 
            }

            catch (IOException | InterruptedException ex) 
            {
                Logger.getLogger(ExecuteBash.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
            
            scriptOutput.add(processOutput);
        }        
        
        return scriptOutput;
    }
}
