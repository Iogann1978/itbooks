<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" />

    <xsl:template match="/">
        <html>
            <body>
                <div align="center">
                    <xsl:apply-templates />
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="contents">
        <ol>
            <xsl:for-each select="item">
                <li>
                    <xsl:value-of select="title" />
                    <xsl:value-of select="@num" />
                    <xsl:value-of select="@page" />
                </li>
            </xsl:for-each>
        </ol>
    </xsl:template>
</xsl:stylesheet>