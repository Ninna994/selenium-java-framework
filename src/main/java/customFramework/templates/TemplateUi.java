package customFramework.templates;

import org.openqa.selenium.By;

public class TemplateUi {
    /*
     * The UI Classes live in UI files in pageElements folder
     * To differentiate page elements from getters and setters, Web Elements are named beginning with "the"
     *
     * Naming convention: theContainerNameObjectLabelElement
     * -- Container - tables/modals
     * -- ObjectLabel - name displayed in the UI
     * -- Element - type of object - button, icon, dropdown, textField etc.
     *
     * Elements are grouped by their location on page
     * FYI - There are 20 dashes/hyphens/equals signs before an after each double backslash
     *
     * Strategies when referencing elements by priorities (from best to worst)
     * -- uniqueId, uniqueId+text
     * -- id
     * -- name
     * -- linkText
     * -- cssSelector + uniqueId, cssSelector + id, cssSelector + name, cssSelector + multiple elements, cssSelector + href
     *    -- = --> equals
     *    -- ^= --> Starts with string
     *    -- $= --> Ends with string
     *    -- *= --> Contains
     *    -- ~= --> Contains in a list
     * -- xpath, xpath+text, xpath + variableText
     * -- xpath + variableText + preceding sibling, xpath + fullPath
     *
     * === AFTER ADDING A PAGE ELEMENT COLLAPSE IT SO THE LIST IS EASIER TO READ THROUGH ===
     */

    public By theEmailInputField() {
        return By.id("emailId");
    }
}
