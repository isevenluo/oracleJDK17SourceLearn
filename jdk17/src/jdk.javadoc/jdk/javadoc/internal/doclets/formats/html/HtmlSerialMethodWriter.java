/*
 * Copyright (c) 1998, 2021, Oracle and/or its affiliates. All rights reserved.
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

package jdk.javadoc.internal.doclets.formats.html;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle;
import jdk.javadoc.internal.doclets.formats.html.markup.TagName;
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlTree;
import jdk.javadoc.internal.doclets.formats.html.markup.Text;
import jdk.javadoc.internal.doclets.toolkit.Content;
import jdk.javadoc.internal.doclets.toolkit.SerializedFormWriter;
import jdk.javadoc.internal.doclets.toolkit.taglets.TagletManager;


/**
 * Generate serialized form for Serializable/Externalizable methods.
 * Documentation denoted by the <code>serialData</code> tag is processed.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public class HtmlSerialMethodWriter extends MethodWriterImpl implements
        SerializedFormWriter.SerialMethodWriter {

    public HtmlSerialMethodWriter(SubWriterHolderWriter writer, TypeElement  typeElement) {
        super(writer, typeElement);
    }

    /**
     * Return the header for serializable methods section.
     *
     * @return a content tree for the header
     */
    @Override
    public Content getSerializableMethodsHeader() {
        HtmlTree ul = new HtmlTree(TagName.UL);
        ul.setStyle(HtmlStyle.blockList);
        return ul;
    }

    /**
     * Return the header for serializable methods content section.
     *
     * @param isLastContent true if the content being documented is the last content.
     * @return a content tree for the header
     */
    @Override
    public Content getMethodsContentHeader(boolean isLastContent) {
        HtmlTree li = new HtmlTree(TagName.LI);
        li.setStyle(HtmlStyle.blockList);
        return li;
    }

    /**
     * Add serializable methods.
     *
     * @param heading the heading for the section
     * @param serializableMethodContent the tree to be added to the serializable methods
     *        content tree
     * @return a content tree for the serializable methods content
     */
    @Override
    public Content getSerializableMethods(String heading, Content serializableMethodContent) {
        Content headingContent = Text.of(heading);
        Content serialHeading = HtmlTree.HEADING(Headings.SerializedForm.CLASS_SUBHEADING, headingContent);
        Content section = HtmlTree.SECTION(HtmlStyle.detail, serialHeading);
        section.add(serializableMethodContent);
        return HtmlTree.LI(section);
    }

    /**
     * Return the no customization message.
     *
     * @param msg the message to be displayed
     * @return no customization message content
     */
    @Override
    public Content getNoCustomizationMsg(String msg) {
        return Text.of(msg);
    }

    /**
     * Add the member header.
     *
     * @param member the method document to be listed
     * @param methodsContentTree the content tree to which the member header will be added
     */
    @Override
    public void addMemberHeader(ExecutableElement member, Content methodsContentTree) {
        Content memberContent = Text.of(name(member));
        Content heading = HtmlTree.HEADING(Headings.SerializedForm.MEMBER_HEADING, memberContent);
        methodsContentTree.add(heading);
        methodsContentTree.add(getSignature(member));
    }

    /**
     * Add the deprecated information for this member.
     *
     * @param member the method to document.
     * @param methodsContentTree the tree to which the deprecated info will be added
     */
    @Override
    public void addDeprecatedMemberInfo(ExecutableElement member, Content methodsContentTree) {
        addDeprecatedInfo(member, methodsContentTree);
    }

    /**
     * Add the description text for this member.
     *
     * @param member the method to document.
     * @param methodsContentTree the tree to which the deprecated info will be added
     */
    @Override
    public void addMemberDescription(ExecutableElement member, Content methodsContentTree) {
        addComment(member, methodsContentTree);
    }

    /**
     * Add the tag information for this member.
     *
     * @param member the method to document.
     * @param methodsContentTree the tree to which the member tags info will be added
     */
    @Override
    public void addMemberTags(ExecutableElement member, Content methodsContentTree) {
        TagletManager tagletManager = configuration.tagletManager;
        Content tagContent = writer.getBlockTagOutput(member, tagletManager.getSerializedFormTaglets());
        HtmlTree dl = HtmlTree.DL(HtmlStyle.notes);
        dl.add(tagContent);
        methodsContentTree.add(dl);
        if (name(member).equals("writeExternal")
                && utils.getSerialDataTrees(member).isEmpty()) {
            serialWarning(member, "doclet.MissingSerialDataTag",
                utils.getFullyQualifiedName(member.getEnclosingElement()), name(member));
        }
    }
}
