package dev.renankrz.library;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CustomPromptProvider implements PromptProvider {

    public AttributedString getPrompt() {
        return new AttributedString(
                "library:> ",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE));
    }

}
