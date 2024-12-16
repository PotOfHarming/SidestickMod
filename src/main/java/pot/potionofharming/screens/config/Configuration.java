package pot.potionofharming.screens.config;

import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Configuration {
    public static int stickId = -1;
    public static double sensitivity = 100;


    private static Path getConfigPath(String file) {
        return FabricLoader.getInstance().getConfigDir().resolve(file);
    }

    public static void saveConfig() {
        Path path = getConfigPath("Sidestickmod.txt");
        System.out.println(path.toString());
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileWriter writer = new FileWriter(path.toFile())) {
            String text = "stick=%stick\nsensitivity=%sens"
                    .replaceAll("%sens", String.valueOf(sensitivity))
                    .replaceAll("%stick", String.valueOf(stickId));
            writer.write(text);
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadConfig() {
        Path path = getConfigPath("Sidestickmod.txt");
        System.out.println(path.toString());
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toString()))) {
            ArrayList<String> values = new ArrayList<>();
            String l;
            while ((l=reader.readLine())!=null){
                values.add(l.split("=", 2)[1]);
            }
            stickId = Integer.parseInt(values.get(0));
            sensitivity = Double.parseDouble(values.get(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void setSensitivity(double val) {
        sensitivity = val;
        saveConfig();
    }
    public static double getSensitivity() {
        return sensitivity;
    }

    public static void setStickId(int val) {
        stickId = val;
        saveConfig();
    }
    public static int getStickId() {
        return stickId;
    }
}
