/*
 * $Id$ 
 *
 * Copyright 1999-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.tiles.definition;

import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A TilesFactoryConfig object hold configuration attributes for a tile
 * definition impl.
 *
 * @version $Rev$ $Date$
 * @since Struts 1.1
 */
public class DefinitionsFactoryConfig implements Serializable {

    /**
     * Fully qualified classname of the impl to create.
     * If no classname is set, a default impl is created
     * (of class "org.apache.tiles.xmlDefinition.I18nFactorySet").
     */
    protected String factoryClassname =
        "org.apache.tiles.definition.UrlDefinitionsFactory";

    /**
     * Specifies whether the parser will validate configuration files.
     * Default value is true.
     */
    protected boolean parserValidate = true;

    /**
     * Definition configuration file specified by user.
     */
    protected String definitionConfigFiles = null;

    /**
     * Specifies whether the impl is "module-aware".
     */
    protected boolean moduleAware = true;

    /**
     * The name associated to this impl.
     * <br>
     * With Struts 1.1, this name is the module name to which this impl
     * belong. It is set by the system.
     * <br>
     * In prior versions, this property is not used.
     */
    protected String factoryName;

    /**
     * Alternate name for parser debug details properties in configuration file.
     *
     * @deprecated This will be removed in a release after Struts 1.2.
     */
    public static final String PARSER_DETAILS_PARAMETER_NAME =
        "definitions-parser-details";

    /**
     * Alternate name for parser validate properties in configuration file.
     */
    public static final String PARSER_VALIDATE_PARAMETER_NAME =
        "definitions-parser-validate";

    /**
     * Alternate name for impl classname properties in configuration file.
     */
    public static final String FACTORY_CLASSNAME_PARAMETER_NAME =
        "definitions-impl-class";

    /**
     * Alternate name for definition files properties in configuration file.
     */
    public static final String DEFINITIONS_CONFIG_PARAMETER_NAME =
        "definitions-config";

    /**
     * Alternate name for definition debug details properties in configuration file.
     *
     * @deprecated This will be removed in a release after Struts 1.2.
     */
    public static final String TILES_DETAILS_PARAMETER_NAME = "definitions-debug";

    /**
     * Map of extra attribute available.
     */
    private Map extraAttributes = new HashMap();

    /**
     * Default constructor.
     */
    public DefinitionsFactoryConfig() {
        super();
    }

    /**
     * Constructor.
     * Create configuration object, and initialize it with parameters from Map.
     * Parameters corresponding to an attribute are filtered and stored in appropriate
     * attribute.
     *
     * @param initParameters Map.
     */
    public DefinitionsFactoryConfig(Map initParameters) {
        super();
    }

    /**
     * Get the module aware flag.
     *
     * @return <code>true</code>: user wants a single impl instance,
     *         <code>false</code>: user wants multiple impl instances (one per module with Struts)
     */
    public boolean isModuleAware() {
        return moduleAware;
    }

    /**
     * Set the module aware flag.
     *
     * @param moduleAware <code>true</code>: user wants a single impl instance,
     *                    <code>false</code>: user wants multiple impl instances (one per module with Struts)
     */
    public void setModuleAware(boolean moduleAware) {
        this.moduleAware = moduleAware;
    }

    /**
     * Get the classname of the impl.
     *
     * @return Classname.
     */
    public String getFactoryClassname() {
        return factoryClassname;
    }

    /**
     * Set the classname of the impl..
     *
     * @param aFactoryClassname Classname of the impl.
     */
    public void setFactoryClassname(String aFactoryClassname) {
        factoryClassname = aFactoryClassname;
    }

    /**
     * Determines if the parser is validating.
     *
     * @return <code>true<code> when in validating mode.
     */
    public boolean getParserValidate() {
        return parserValidate;
    }

    /**
     * Set the validating mode for the parser.
     *
     * @param aParserValidate <code>true</code> for validation, <code>false</code> otherwise
     */
    public void setParserValidate(boolean aParserValidate) {
        parserValidate = aParserValidate;
    }

    /**
     * Get the definition config files.
     *
     * @return Defition config files.
     */
    public String getDefinitionConfigFiles() {
        return definitionConfigFiles;
    }

    /**
     * Set the definition config files.
     *
     * @param aDefinitionConfigFiles Definition config files.
     */
    public void setDefinitionConfigFiles(String aDefinitionConfigFiles) {
        definitionConfigFiles = aDefinitionConfigFiles;
    }

    /**
     * Set value of an additional attribute.
     *
     * @param name  Name of the attribute.
     * @param value Value of the attribute.
     */
    public void setAttribute(String name, Object value) {
        extraAttributes.put(name, value);
    }

    /**
     * Get value of an additional attribute.
     *
     * @param name Name of the attribute.
     * @return Value of the attribute, or null if not found.
     */
    public Object getAttribute(String name) {
        return extraAttributes.get(name);
    }

    /**
     * Get additional attributes as a Map.
     *
     * @return Map A Map containing attribute name - value pairs.
     */
    public Map getAttributes() {
        Map map = new HashMap(extraAttributes);
        // Add property attributes using old names
        /*
          map.put(DEFINITIONS_CONFIG_PARAMETER_NAME, getDefinitionConfigFiles());
          map.put(TILES_DETAILS_PARAMETER_NAME, Integer.toString(getDebugLevel()) );
          map.put(PARSER_DETAILS_PARAMETER_NAME, Integer.toString(getParserDebugLevel()) );
          map.put(PARSER_VALIDATE_PARAMETER_NAME, new Boolean(getParserValidate()).toString() );
        
          if( ! "org.apache.tiles.xmlDefinition.I18nFactorySet".equals(getFactoryClassname()) )
          map.put(FACTORY_CLASSNAME_PARAMETER_NAME, getFactoryClassname());
        */
        return map;
    }

    /**
     * Populate this config object from properties map, based on
     * the specified name/value pairs.  This method uses the populate() method from
     * org.apache.commons.beanutils.BeanUtil.
     * <p/>
     * Properties keys are scanned for old property names, and linked to the new name
     * if necessary. This modifies the properties map.
     * <p/>
     * The particular setter method to be called for each property is
     * determined using the usual JavaBeans introspection mechanisms.  Thus,
     * you may identify custom setter methods using a BeanInfo class that is
     * associated with the class of the bean itself.  If no such BeanInfo
     * class is available, the standard method name conversion ("set" plus
     * the capitalized name of the property in question) is used.
     * <p/>
     * <strong>NOTE</strong>:  It is contrary to the JavaBeans Specification
     * to have more than one setter method (with different argument
     * signatures) for the same property.
     *
     * @param properties Map keyed by property name, with the
     *                   corresponding (String or String[]) value(s) to be set.
     * @throws IllegalAccessException    if the caller does not have
     *                                   access to the property accessor method.
     * @throws InvocationTargetException if the property accessor method
     *                                   throws an exception.
     * @see org.apache.commons.beanutils.BeanUtils
     */
    public void populate(Map properties)
        throws IllegalAccessException, InvocationTargetException {

        // link old parameter names for backward compatibility
        linkOldPropertyNames(properties);
        populateExtraAttributes(properties);
        BeanUtils.populate(this, properties);
    }

    /**
     * Link old property names to new property names.
     * This modifies the map.
     *
     * @param properties Map keyed by property name, with the
     *                   corresponding (String or String[]) value(s) to be set.
     */
    static public void linkOldPropertyNames(Map properties) {
        Set entries = properties.entrySet();
        Map toAdd = new HashMap();
        Iterator i = entries.iterator();
        while (i.hasNext()) {
            Map.Entry entry = (Map.Entry) i.next();

            if (DEFINITIONS_CONFIG_PARAMETER_NAME.equals(entry.getKey())) {
                toAdd.put("definitionConfigFiles", entry.getValue());

            } else if (FACTORY_CLASSNAME_PARAMETER_NAME.equals(entry.getKey())) {
                toAdd.put("factoryClassname", entry.getValue());

            } else if (PARSER_DETAILS_PARAMETER_NAME.equals(entry.getKey())) {
                toAdd.put("parserDebugLevel", entry.getValue());

            } else if (PARSER_VALIDATE_PARAMETER_NAME.equals(entry.getKey())) {
                toAdd.put("parserValidate", entry.getValue());

            } else if (TILES_DETAILS_PARAMETER_NAME.equals(entry.getKey())) {
                toAdd.put("debugLevel", entry.getValue());
            }
        }

        if (toAdd.size() > 0) {
            properties.putAll(toAdd);
        }
    }

    /**
     * Get the impl name.
     */
    public String getFactoryName() {
        return factoryName;
    }

    /**
     * Set the impl name.
     *
     * @param factoryName Name of the impl.
     */
    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    /**
     * Adds all implementation-specific extra attributes from the configuration.
     *
     * @param properties Map keyed by property name, with the
     *                   corresponding (String or String[]) value(s) to be set.
     */
    protected void populateExtraAttributes(Map properties) {
        setAttribute(DefinitionsFactory.READER_IMPL_PROPERTY,
            properties.get(DefinitionsFactory.READER_IMPL_PROPERTY));
        setAttribute(DefinitionsFactory.DEFINITIONS_IMPL_PROPERTY,
            properties.get(DefinitionsFactory.DEFINITIONS_IMPL_PROPERTY));
    }
}