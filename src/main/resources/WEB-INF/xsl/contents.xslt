<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" />

    <xsl:template match="/">
        <html>
            <head>
                <meta charsey="utf-8" />
            </head>
            <body>
                <xsl:apply-templates select="contents" />
            </body>
        </html>
    </xsl:template>

    <xsl:template match="contents">
        <ul>
            <xsl:for-each select="item">
                <xsl:apply-templates select="." />
            </xsl:for-each>
        </ul>
    </xsl:template>

    <xsl:template match="item">
        <li>
            <span><xsl:value-of select="title" /></span>
            &#160;
            <span><xsl:value-of select="@num" /></span>
            &#160;
            <span><xsl:value-of select="@page" /></span>
            <xsl:for-each select="item">
                <ul>
                    <xsl:apply-templates select="." />
                </ul>
            </xsl:for-each>
        </li>
    </xsl:template>
</xsl:stylesheet>