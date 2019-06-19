<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" encoding="UTF-8" indent="yes" />

    <xsl:template match="/">
        <contents>
            <xsl:apply-templates select="contents" />
        </contents>
    </xsl:template>

    <xsl:template match="contents">
        <xsl:for-each select="item">
            <xsl:apply-templates select="." />
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="item">
        <item>
            <xsl:attribute name="num">
                <xsl:value-of select="@num" />
            </xsl:attribute>
            <xsl:attribute name="page">
                <xsl:value-of select="@page" />
            </xsl:attribute>
            <xsl:element name="title">
                <xsl:value-of select="title" />
            </xsl:element>

            <xsl:for-each select="item">
                <xsl:apply-templates select="." />
            </xsl:for-each>
        </item>
    </xsl:template>
</xsl:stylesheet>