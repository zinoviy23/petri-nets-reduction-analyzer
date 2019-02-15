package hse.se.aaizmaylov.terminalclient.terminal;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class TerminalPrinterTest {
    @Test
    public void testIsColoringEnabled() {
        Pattern pattern = Pattern.compile(".*(linux|macos).*");
        assertEquals(pattern.matcher(System.getProperty("os.name").toLowerCase()).matches(),
                TerminalPrinter.isColoringEnabled());
    }
}