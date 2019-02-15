package hse.se.aaizmaylov.terminalclient.terminal;

public class TerminalPrinter {
    private static boolean coloringEnabled = false;

    private static String osName;

    static {
        osName = System.getProperty("os.name");

        if (systemAllowColoring())
            coloringEnabled = true;
    }

    public static boolean isColoringEnabled() {
        return coloringEnabled;
    }

    private static boolean systemAllowColoring() {
        return osName.toLowerCase().contains("linux") || osName.toLowerCase().contains("macos");
    }

    public static void enableColoring() {
        if (systemAllowColoring())
            coloringEnabled = true;
    }

    public static void disableColoring() {
        coloringEnabled = false;
    }

    public static void println(Object msg) {
        System.out.println(msg);
    }

    public static void println(String flag, Object msg) {
        System.out.println((coloringEnabled ? flag : "") + msg + (coloringEnabled ? TerminalColors.RESET : ""));
    }

    public static void print(Object msg) {
        System.out.print(msg);
    }

    public static void print(String flag, Object msg) {
        System.out.print((coloringEnabled ? flag : "") + msg + (coloringEnabled ? TerminalColors.RESET : ""));
    }

    private TerminalPrinter() {}
}
