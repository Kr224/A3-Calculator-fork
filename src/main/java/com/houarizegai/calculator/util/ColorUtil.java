package com.houarizegai.calculator.util;

import java.awt.Color;
public class ColorUtil {

    // Prevent instantiation of the utility class
    private ColorUtil() {
        throw new AssertionError("Constructor is not allowed");
    }

    private static final int RED_START_INDEX = 0;
    private static final int GREEN_START_INDEX = 2;
    private static final int BLUE_START_INDEX = 4;
    private static final int HEX_RADIX = 16;

    /**
     * Converts a hexadecimal color string to a Color object.
     *
     * @param colorHex the hexadecimal color string
     * @return the Color object, or null if the input is null
     */
    public static Color hex2Color(String colorHex) {
        if (colorHex == null) {
            return null;
        }

        int red = getColorComponent(colorHex, RED_START_INDEX, GREEN_START_INDEX);
        int green = getColorComponent(colorHex, GREEN_START_INDEX, BLUE_START_INDEX);
        int blue = getColorComponent(colorHex, BLUE_START_INDEX);

        return new Color(red, green, blue);
    }

    private static int getColorComponent(String colorHex, int start, int end) {
        String component = colorHex.substring(start, end);
        return Integer.parseInt(component, HEX_RADIX);
    }

    private static int getColorComponent(String colorHex, int start) {
        String component = colorHex.substring(start);
        return Integer.parseInt(component, HEX_RADIX);
    }
}
