# Adept

A universal code formatter implemented using machine learning of aesthetic preferences. 
Includes a dedicated comment formatter that supports C/Java style block and line 
comment syntax, including formal JavaDoc. 

The Adept tool is designed for both command line and embedded use.

## Current built-in language support

- [x] ANTLR syntax
- [x] Java syntax
- [x] StringTemplate syntax
- [x] XVisitor syntax

## Formatting operations

- [x] Code formatter
- [x] Comment formatter 
- [x] Skip header comment formatting option
- [x] Align code fields
- [x] Align comments
- [ ] Wrap long lines

## Installation

[![](https://jitpack.io/v/net.certiv/adept.svg)](https://jitpack.io/#net.certiv/adept)

### For Maven

```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
```
```xml
    <dependency>
        <groupId>net.certiv</groupId>
        <artifactId>adept</artifactId>
        <version>0.3.0</version>
    </dependency>
```

## License

EPL v1

## Status

Adept engine core implemented and tested. 

A suite of visualization tools serve as development aids as well as examples of embedded 
use. The `FormatView` visualization tool provides a working demonstration of embedded 
use.

![FormatView](FormatView.png)


