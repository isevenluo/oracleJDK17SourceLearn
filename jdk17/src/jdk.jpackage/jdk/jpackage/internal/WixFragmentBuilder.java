/*
 * Copyright (c) 2021, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package jdk.jpackage.internal;

import java.io.IOException;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jdk.jpackage.internal.IOUtils.XmlConsumer;
import jdk.jpackage.internal.OverridableResource.Source;
import static jdk.jpackage.internal.OverridableResource.createResource;
import static jdk.jpackage.internal.StandardBundlerParam.CONFIG_ROOT;

/**
 * Creates WiX fragment.
 */
abstract class WixFragmentBuilder {

    void setWixVersion(DottedVersion v) {
        wixVersion = v;
    }

    void setOutputFileName(String v) {
        outputFileName = v;
    }

    void initFromParams(Map<String, ? super Object> params) {
        wixVariables = null;
        additionalResources = null;
        configRoot = CONFIG_ROOT.fetchFrom(params);
        fragmentResource = createResource(outputFileName, params).setSourceOrder(
                Source.ResourceDir);
    }

    void logWixFeatures() {
        if (wixVersion.compareTo("3.6") >= 0) {
            Log.verbose(MessageFormat.format(I18N.getString(
                    "message.use-wix36-features"), wixVersion));
        }
    }

    void configureWixPipeline(WixPipeline wixPipeline) {
        wixPipeline.addSource(configRoot.resolve(outputFileName),
                Optional.ofNullable(wixVariables).map(WixVariables::getValues).orElse(
                        null));
    }

    void addFilesToConfigRoot() throws IOException {
        Path fragmentPath = configRoot.resolve(outputFileName);
        if (fragmentResource.saveToFile(fragmentPath) == null) {
            createWixSource(fragmentPath, xml -> {
                for (var fragmentWriter : getFragmentWriters()) {
                    xml.writeStartElement("Fragment");
                    fragmentWriter.accept(xml);
                    xml.writeEndElement();  // <Fragment>
                }
            });
        }

        if (additionalResources != null) {
            for (var resource : additionalResources) {
                resource.resource.saveToFile(configRoot.resolve(
                        resource.saveAsName));
            }
        }
    }

    DottedVersion getWixVersion() {
        return wixVersion;
    }

    static boolean is64Bit() {
        return !("x86".equals(System.getProperty("os.arch")));
    }

    protected Path getConfigRoot() {
        return configRoot;
    }

    protected abstract Collection<XmlConsumer> getFragmentWriters();

    protected void defineWixVariable(String variableName) {
        setWixVariable(variableName, "yes");
    }

    protected void setWixVariable(String variableName, String variableValue) {
        if (wixVariables == null) {
            wixVariables = new WixVariables();
        }
        wixVariables.setWixVariable(variableName, variableValue);
    }

    protected void addResource(OverridableResource resource, String saveAsName) {
        if (additionalResources == null) {
            additionalResources = new ArrayList<>();
        }
        additionalResources.add(new ResourceWithName(resource, saveAsName));
    }

    static void createWixSource(Path file, XmlConsumer xmlConsumer)
            throws IOException {
        IOUtils.createXml(file, xml -> {
            xml.writeStartElement("Wix");
            xml.writeDefaultNamespace("http://schemas.microsoft.com/wix/2006/wi");
            xml.writeNamespace("util",
                    "http://schemas.microsoft.com/wix/UtilExtension");

            xmlConsumer.accept(xml);

            xml.writeEndElement(); // <Wix>
        });
    }

    private static class ResourceWithName {

        ResourceWithName(OverridableResource resource, String saveAsName) {
            this.resource = resource;
            this.saveAsName = saveAsName;
        }
        private final OverridableResource resource;
        private final String saveAsName;
    }

    private DottedVersion wixVersion;
    private WixVariables wixVariables;
    private List<ResourceWithName> additionalResources;
    private OverridableResource fragmentResource;
    private String outputFileName;
    private Path configRoot;
}
