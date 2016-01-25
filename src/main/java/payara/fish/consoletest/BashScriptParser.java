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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Andrew Pielage
 */
public class BashScriptParser {

    private final Properties properties;

    public BashScriptParser() {
        properties = readProperties();
    }

    /**
     * Parses the asadmin commands from a script into a usable form
     *
     * @param scriptName The name of the script to be parsed
     * @return A List containing the asadmin commands of the script
     */
    public List<String> parseScript(String scriptName) {
        // Get the location of the scripts from the properties file
        final String scriptLocation
                = properties.getProperty("SCRIPTS_LOCATION");

        // Build the full script path
        StringBuilder script = new StringBuilder();
        script.append(scriptLocation);
        script.append(scriptName);

        // Read in the script
        List<String> scriptBody = readScript(script.toString());

        List<String> asadminCommands = new ArrayList<>();

        // Extract the asadmin commands from the script
        for (String line : scriptBody) {
            // If a line starts with ${ASADMIN}...
            if (line.startsWith("${ASADMIN}") || line.startsWith("curl")) {
                // Perform property substitution
                line = propertySubstitution(line);

                // Add asadmin command to list
                asadminCommands.add(line);
            }
        }

        return asadminCommands;
    }

    /**
     * Reads the provided script into a List
     *
     * @param script The script to be read
     * @return A List containing the lines of the script
     */
    private List<String> readScript(String script) {
        List<String> scriptBody = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(script))) {
            while (br.ready()) {
                scriptBody.add(br.readLine());
            }
        } catch (IOException ex) {
            Logger.getLogger(BashScriptParser.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        return scriptBody;
    }

    /**
     * Gets the properties from the properties file
     *
     * @return The properties from the properties file
     */
    private Properties readProperties() {
        final String propertiesLocation = "config/config.properties";
        Properties readProperties = new Properties();

        InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(propertiesLocation);

        try {
            File tempFile = File.createTempFile("config", ".properties");
            tempFile.deleteOnExit();
            IOUtils.copy(inputStream, new FileOutputStream(tempFile));

            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(tempFile))) {
                readProperties.load(bis);
            }
        } catch (IOException ex) {
            Logger.getLogger(BashScriptParser.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

        // Perform property substitution for ASADMIN property
        String asadmin = readProperties.getProperty("ASADMIN");
        asadmin = asadmin.replaceAll(Pattern.quote("${PAYARA_HOME}"),
                readProperties.getProperty("PAYARA_HOME"));
        readProperties.setProperty("ASADMIN", asadmin);

        return readProperties;
    }

    /**
     * Performs any property substitution that would have been done by Bash
     *
     * @param scriptLine The line of the bash script to perform property
     * substitution on
     * @param properties The properties to use for substitution
     * @return
     */
    private String propertySubstitution(String scriptLine) {
        // Replace instances of ${ASADMIN}      
        scriptLine = scriptLine.replaceAll(Pattern.quote("${ASADMIN}"),
                properties.getProperty("ASADMIN"));

        // Replace instances of ${PORT_BASE}
        scriptLine = scriptLine.replaceAll(Pattern.quote("${PORT_BASE}"),
                properties.getProperty("PORT_BASE"));

        // Replace instances of ${DOMAIN_NAME}
        scriptLine = scriptLine.replaceAll(Pattern.quote("${DOMAIN_NAME}"),
                properties.getProperty("DOMAIN_NAME"));

        // Replace instances of ${ADMIN_PORT}
        scriptLine = scriptLine.replaceAll(Pattern.quote("${ADMIN_PORT}"),
                properties.getProperty("ADMIN_PORT"));

        // Replace instances of ${DEPLOYMENTS_LOCATION}
        scriptLine = scriptLine.replaceAll(Pattern
                .quote("${DEPLOYMENTS_LOCATION}"), properties.getProperty("DEPLOYMENTS_LOCATION"));

        return scriptLine;
    }

    /**
     * Extracts the root Asadmin command from the script line
     *
     * @param scriptLine
     * @return
     */
    public String commandExtractor(String scriptLine) {
        String rootCommand = "";

        // Split the script line on spaces
        String splitCommand[] = scriptLine.split("\\s+");

        // Check if an Asadmin command
        if (splitCommand[0].equals(properties.getProperty("ASADMIN"))) {
            // Check if the command used a port modifier
            if (splitCommand[1].equals("-p")) {
                // If it did, then the root command should be fourth bit of text
                rootCommand = splitCommand[3];
            } else {
                // If it didn't, then the root command should be the second bit of text
                rootCommand = splitCommand[1];
            }
        }

        return rootCommand;
    }
}
