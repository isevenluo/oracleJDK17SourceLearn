/*
 * Copyright (c) 2003, 2019, Oracle and/or its affiliates. All rights reserved.
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

package jdk.javadoc.internal.doclets.toolkit;

import java.util.List;
import java.util.SortedSet;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import jdk.javadoc.internal.doclets.toolkit.util.DocFileIOException;

/**
 * The interface for writing package summary output.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */

public interface PackageSummaryWriter {

    /**
     * Get the header for the summary.
     *
     * @return the header to be added to the content tree
     */
    Content getPackageHeader();

    /**
     * Get the header for the package content.
     *
     * @return a content tree for the package content header
     */
    Content getContentHeader();

    /**
     * Get the header for the package summary.
     *
     * @return a content tree with the package summary header
     */
    Content getSummariesList();

    /**
     * Adds the table of related packages to the documentation tree.
     *
     * @param summaryContentTree the content tree to which the summaries will be added
     */
    void addRelatedPackagesSummary(Content summaryContentTree);

    /**
     * Adds the table of all classes and interfaces to the documentation tree.
     *
     * @param summaryContentTree the content tree to which the summaries will be added
     */
    void addAllClassesAndInterfacesSummary(Content summaryContentTree);

    /**
     * Adds the package description from the "packages.html" file to the documentation
     * tree.
     *
     * @param packageContentTree the content tree to which the package description
     *                           will be added
     */
    void addPackageDescription(Content packageContentTree);

    /**
     * Adds the tag information from the "packages.html" file to the documentation
     * tree.
     *
     * @param packageContentTree the content tree to which the package tags will
     *                           be added
     */
    void addPackageTags(Content packageContentTree);

    /**
     * Adds the package signature.
     *
     * @param packageContentTree the content tree to which the package signature
     *                           will be added
     */
    void addPackageSignature(Content packageContentTree);

    /**
     * Adds the tag information from the "packages.html" or "package-info.java" file to the
     * documentation tree.
     *
     * @param packageContentTree the package content tree to be added
     */
    void addPackageContent(Content packageContentTree);

    /**
     * Adds the footer to the documentation tree.
     */
    void addPackageFooter();

    /**
     * Print the package summary document.
     *
     * @param contentTree the content tree that will be printed
     * @throws DocFileIOException if there is a problem while writing the document
     */
    void printDocument(Content contentTree) throws DocFileIOException;

    /**
     * Gets the package summary tree.
     * @param summaryContentTree the content tree representing the package summary
     * @return a content tree for the package summary
     */
    Content getPackageSummary(Content summaryContentTree);
}
