package customFramework.utils;

import org.openqa.selenium.By;

public class ExtendedBy {

    public By id(String id) {
        return By.id(id);
    }

    public By className(String name) {
        return By.className(name);
    }

    public By cssSelector(String name) {
        return By.cssSelector(name);
    }

    public By name(String str) {
        return By.name(str);
    }

    public By linkText(String name) {
        return By.linkText(name);
    }

    public By partialLinkText(String name) {
        return By.partialLinkText(name);
    }

    public By tagName(String name) {
        return By.tagName(name);
    }

    public By xpath(String name) {
        return By.xpath(name);
    }

    public By href(String param) {
        return By.cssSelector("[href='" + param + "']");
    }

    public By value(String param) {
        return By.cssSelector("[value='" + param + "']");
    }

    public By title(String param) {
        return By.cssSelector("[title='" + param + "']");
    }

    public By type(String param) {
        return By.cssSelector("[type='" + param + "']");
    }

    public By src(String param) {
        return By.cssSelector("[src='" + param + "']");
    }

    public By altAttribute(String param) {
        return By.cssSelector("[alt='" + param + "']");
    }

    public By partialHref(String param) {
        return By.xpath(("//*[contains(@href, '" + param + "')]"));
    }

    public By partialId(String param) {
        return By.xpath(("//*[contains(@id, '" + param + "')]"));
    }

}