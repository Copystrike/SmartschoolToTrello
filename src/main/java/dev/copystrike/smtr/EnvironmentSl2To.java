package dev.copystrike.smtr;

import dev.copystrike.smtr.core.Sl2To;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by Nick on 12/15/2020 21:19.
 */
public class EnvironmentSl2To {

    private static void splashScreen() {
        System.out.println();
        System.out.println("  `.. ..  `..           `... `......               `... `......    `....     ");
        System.out.println("`..    `..`..                `..                        `..      `..    `..  ");
        System.out.println(" `..      `..                `..       `..              `..    `..        `..");
        System.out.println("   `..    `..                `..     `..  `..           `..    `..        `..");
        System.out.println("      `.. `..                `..    `..    `..          `..    `..        `..");
        System.out.println("`..    `..`..                `..     `..  `..           `..      `..     `.. ");
        System.out.println("  `.. ..  `........          `..       `..              `..        `....     ");
        System.out.println();
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        splashScreen();
        Sl2To sl2To = new Sl2To();
        sl2To.initialize();
    }
}
