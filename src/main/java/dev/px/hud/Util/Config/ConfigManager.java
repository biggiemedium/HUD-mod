package dev.px.hud.Util.Config;

import dev.px.hud.HUDMod;
import dev.px.hud.Manager.SocialManager;
import dev.px.hud.Rendering.HUD.Element;
import dev.px.hud.Rendering.HUD.RenderElement;
import dev.px.hud.Rendering.HUD.ToggleableElement;
import dev.px.hud.Rendering.Panel.ClickGUI.Frame;
import dev.px.hud.Util.API.Render.Color.AccentColor;
import dev.px.hud.Util.Settings.Setting;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager { // I dont care if this code is messy I actually fucking hate writing config files

    private Minecraft mc = Minecraft.getMinecraft();
    private File mainFile;
    private File settingsPath;
    private File elementPath;
    private File guiPath;
    private File socialPath;

    public ConfigManager() {
        mainFile = new File(mc.mcDataDir + File.separator + HUDMod.MODID);
        if(!mainFile.exists()) {
            mainFile.mkdirs();
        }

        settingsPath = new File(mainFile + File.separator + "Settings");
        if(!settingsPath.exists()) {
            settingsPath.mkdirs();
        }

        elementPath = new File(mainFile + File.separator + "Elements");
        if(!elementPath.exists()) {
            elementPath.mkdirs();
        }

        guiPath = new File(mainFile + File.separator + "GUI");
        if(!guiPath.exists()) {
            guiPath.mkdirs();
        }

        socialPath = new File(mainFile + File.separator + "Social");
        if(!socialPath.exists()) {
            socialPath.mkdirs();
        }

        loadPreferences();
        loadRenderElements();
        loadToggleElements();
        loadRenderElementPositions();
        loadGUI();
        loadRenderElementSetting();
        loadToggleElementSetting();
        loadSocials();
        loadTheme();
    }

    public void save() {
        saveRenderElements();
        saveToggleElements();
        saveRenderElementPositions();
        saveGUI();
        saveRenderElementSetting();
        saveToggleElementSetting();
        saveSocials();
        saveTheme();
        savePreferences();
    }

    public void loadPreferences() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.settingsPath.getAbsolutePath() + File.separator + "PreferenceBoolean.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String settingName = parts[0].trim();
                    String settingValue = parts[1].trim();

                    for(Setting s : HUDMod.preferenceManager.getPreferences()) {
                        if(s.getValue() instanceof Boolean) {
                            if(s.getName().equalsIgnoreCase(settingName)) {
                                s.setValue(settingValue.equalsIgnoreCase("true"));
                            }
                        }
                    }

                }
            }
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void savePreferences() { // oh god no
        try {
            File f = new File(this.settingsPath.getAbsolutePath() + File.separator + "PreferenceBoolean.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(Setting s : HUDMod.preferenceManager.getPreferences()) {
                if(s.getValue() instanceof Boolean) {
                    String v = (Boolean) s.getValue() ? "true" : "false";
                    writer.write(s.getName() + ":" + v + "\r\n");
                }
            }
            writer.close();
        } catch (Exception ignored) {}

    }

    public void saveTheme() {
        try {
            File file = new File(this.settingsPath, "Theme.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(HUDMod.colorManager.currentColor.getName() + "\r\n");
            writer.close();
        } catch (Exception ignored) {}
    }

    public void loadTheme() {
        try {
            File file = new File(this.settingsPath, "Theme.txt");
            FileInputStream inputStream = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(inputStream);
            BufferedReader writer = new BufferedReader(new InputStreamReader(dis));
            String line;
            while ((line = writer.readLine()) != null) {
                String cl = line.trim();
                String theme = cl.split(":")[0];
                for(AccentColor c : HUDMod.colorManager.getAccentColors()) {
                    if(c.getName().equalsIgnoreCase(theme)) {
                        HUDMod.colorManager.setCurrentColor(c);
                    }
                }
            }
            writer.close();
        } catch (Exception ignored) {}

    }

    public void saveSocials() {
            try {
                File file = new File(this.socialPath, "Socials.txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                HUDMod.socialManager.getSocialMap().forEach((n, r) -> {
                    try {
                        writer.write(n + ":" + r.name());
                        writer.write("\r\n");
                    } catch (IOException e) {e.printStackTrace();}
                    try { // bruh
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                writer.close();
            } catch (Exception ignored) {}

    }

    public void saveGUI() {
        try {
            File file = new File(this.guiPath, "GUI.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(Frame f : HUDMod.screen.getCLICKGUI().getFrames()) {
                String s = f.isOpen() ? "open" : "closed";
                writer.write(f.getName() + ":" + f.getX() + ":" + f.getY() + ":" + s);
                writer.write("\r\n");
            }
            writer.close();
        } catch (Exception ignored) {}

    }

    public void loadSocials() {
        try {
            File f = new File(this.socialPath, "Socials.txt");
            FileInputStream inputStream = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(inputStream);
            BufferedReader writer = new BufferedReader(new InputStreamReader(dis));
            String line;
            while ((line = writer.readLine()) != null) {
                String cl = line.trim();
                String name = cl.split(":")[0];
                String relationShip = cl.split(":")[1];

                HUDMod.socialManager.getSocialMap().put(name, SocialManager.SocialState.valueOf(relationShip));
            }
            writer.close();
        } catch (Exception ignored) {}
    }

    public void loadGUI() {
        try {
            File f = new File(this.guiPath, "GUI.txt");
            FileInputStream inputStream = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(inputStream);
            BufferedReader writer = new BufferedReader(new InputStreamReader(dis));
            String line;
            while ((line = writer.readLine()) != null) {
                String cl = line.trim();
                String name = cl.split(":")[0];
                String x = cl.split(":")[1];
                String y = cl.split(":")[2];
                String open = cl.split(":")[3];

                if(HUDMod.screen.getCLICKGUI().getFramebyName(name) == null) { // || !f.exists()
                    continue;
                }

                boolean openTab = open.equalsIgnoreCase("open");
                HUDMod.screen.getCLICKGUI().getFramebyName(name).setX(Integer.parseInt(x));
                HUDMod.screen.getCLICKGUI().getFramebyName(name).setY(Integer.parseInt(y));
                HUDMod.screen.getCLICKGUI().getFramebyName(name).setOpen(openTab);
            }

        } catch (Exception ignored) {}
    }

    public void loadRenderElements() {
        try {
            File file = new File(this.elementPath, "RenderElements.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                for (Element m : HUDMod.elementInitalizer.getElements()) {
                    if(m instanceof ToggleableElement) {
                        continue;
                    }
                    try {
                        if (m.getName().equals(line)) {
                            m.setToggled(true);
                        }
                    } catch(Exception e) {}
                }
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            saveRenderElements();
        }
    }

    public void saveRenderElements() {
        try {
            File file = new File(this.elementPath, "RenderElements.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (Element module : HUDMod.elementInitalizer.getElements()) {
                if(module instanceof ToggleableElement) {
                    continue;
                }
                try {
                    if (module.isToggled() && !module.getName().matches("null")) {
                        out.write(module.getName());
                        out.write("\r\n");
                    }
                } catch(Exception e) {}
            }
            out.close();
        }
        catch (Exception ignored) {}
    }


    public void loadRenderElementPositions() {
        try {
            File f = new File(this.elementPath, "RenderElementPositions.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            List<String> lines = Files.readAllLines(f.toPath());
            ArrayList<Element> renderElements = new ArrayList<Element>();
            for(Element e : HUDMod.elementInitalizer.getElements()) { // fixed issue
                if(e instanceof ToggleableElement) {
                    continue;
                }
                if(e instanceof RenderElement) {
                    renderElements.add(e);
                }
            }
            int index = 0;
            for (String line : lines) {
                String[] regex = line.split(":");
                Element component = renderElements.get(index);
                assert component != null;
                if(component instanceof RenderElement) {
                    ((RenderElement) component).setX(Integer.parseInt(regex[1]));
                    ((RenderElement) component).setY(Integer.parseInt(regex[2]));
                    component.setToggled(regex[3].equals("open"));

                    index++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveRenderElementPositions() {
        try {
            File file = new File(this.elementPath, "RenderElementPositions.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (Element f : HUDMod.elementInitalizer.getElements()) {
                if(f instanceof ToggleableElement) {
                    continue;
                }
                if(f instanceof RenderElement) {
                    String s = f.isToggled() ? "open" : "closed";
                    out.write(f.getName() + ":" + ((RenderElement) f).getX() + ":" + ((RenderElement) f).getY() + ":" + s);
                    out.write("\r\n");
                }
            }
            out.close();
        }
        catch (Exception ex) {}
    }

    public void saveToggleElements() {
        try {
            File file = new File(this.elementPath, "ToggleElements.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (Element module : HUDMod.elementInitalizer.getElements()) {
                if(module instanceof RenderElement) {
                    continue;
                }
                try {
                    if (module.isToggled() && !module.getName().matches("null") && !module.getName().equalsIgnoreCase("fakeplayer")) {
                        out.write(module.getName());
                        out.write("\r\n");
                    }
                } catch(Exception e) {}
            }
            out.close();
        }
        catch (Exception ignored) {}
    }

    public void loadToggleElements() {
        try {
            File file = new File(this.elementPath, "ToggleElements.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                for (Element m : HUDMod.elementInitalizer.getElements()) {
                    if(m instanceof RenderElement) {
                        continue;
                    }
                    if(m == null) {
                        continue;
                    }
                    try {
                        if (m.getName().equals(line)) {
                            ((ToggleableElement) m).setToggled(true);
                        }
                    } catch(Exception e) {}
                }
            }
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            saveToggleElements();
        }
    }

    public void saveToggleElementSetting() {
        try {
            File f = new File(this.settingsPath.getAbsolutePath() + File.separator + "ToggleBoolean.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(Element s : HUDMod.elementInitalizer.getElements()) {
                if(s instanceof RenderElement) {
                    for(Setting set : s.getSettings()) {
                        if(set.getValue() instanceof Boolean) {
                            String v = (Boolean) set.getValue() ? "true" : "false";
                            writer.write(s.getName() + ":" + set.getName() + ":" + v + "\r\n");
                        }
                    }
                }

            }
            writer.close();
        } catch (Exception ignored) {}

        try {
            File f = new File(this.settingsPath.getAbsolutePath() + File.separator + "ToggleFloat.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(Element s : HUDMod.elementInitalizer.getElements()) {
                if(s instanceof RenderElement) {
                    for(Setting set : s.getSettings()) {
                        if(set.getValue() instanceof Float) {
                            // String v = (Boolean) set.getValue() ? "true" : "false";
                            float v = (float) set.getValue();
                            writer.write(s.getName() + ":" + set.getName() + ":" + v + "\r\n");
                        }
                    }
                }

            }
            writer.close();
        } catch (Exception ignored) {}

        try {
            File f = new File(this.settingsPath.getAbsolutePath() + File.separator + "ToggleDouble.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(Element s : HUDMod.elementInitalizer.getElements()) {
                if(s instanceof RenderElement) {
                    for(Setting set : s.getSettings()) {
                        if(set.getValue() instanceof Double) {
                            // String v = (Boolean) set.getValue() ? "true" : "false";
                            double v = (double) set.getValue();
                            writer.write(s.getName() + ":" + set.getName() + ":" + v + "\r\n");
                        }
                    }
                }

            }
            writer.close();
        } catch (Exception ignored) {}

        try {
            File f = new File(this.settingsPath.getAbsolutePath() + File.separator + "ToggleInteger.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(Element s : HUDMod.elementInitalizer.getElements()) {
                if(s instanceof RenderElement) {
                    for(Setting set : s.getSettings()) {
                        if(set.getValue() instanceof Integer) {
                            // String v = (Boolean) set.getValue() ? "true" : "false";
                            int v = (int) set.getValue();
                            writer.write(s.getName() + ":" + set.getName() + ":" + v + "\r\n");
                        }
                    }
                }

            }
            writer.close();
        } catch (Exception ignored) {}

        try {
            File f = new File(this.settingsPath.getAbsolutePath() + File.separator + "ToggleEnum.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(Element s : HUDMod.elementInitalizer.getElements()) {
                if(s instanceof RenderElement) {
                    for(Setting set : s.getSettings()) {
                        if(set.getValue() instanceof Enum) {
                            // String v = (Boolean) set.getValue() ? "true" : "false";
                            String v = ((Enum<?>) set.getValue()).name();
                            writer.write(s.getName() + ":" + set.getName() + ":" + v + "\r\n");
                        }
                    }
                }

            }
            writer.close();
        } catch (Exception ignored) {}

        try {
            File f = new File(this.settingsPath.getAbsolutePath() + File.separator + "ToggleColor.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(Element s : HUDMod.elementInitalizer.getElements()) {
                if(s instanceof RenderElement) {
                    for(Setting set : s.getSettings()) {
                        if(set.getValue() instanceof Color) {
                            // String v = (Boolean) set.getValue() ? "true" : "false";
                            String v = Integer.toString(((Color) set.getValue()).getRGB());
                            int red = ((Color) set.getValue()).getRed();
                            int green = ((Color) set.getValue()).getGreen();
                            int blue = ((Color) set.getValue()).getBlue();
                            int alpha = ((Color) set.getValue()).getAlpha();
                            writer.write(s.getName() + ":" + set.getName() + ":" + red + ":" + green + ":" + blue + ":" + alpha + "\r\n");
                        }
                    }
                }

            }
            writer.close();
        } catch (Exception ignored) {}
    }

    public void saveRenderElementSetting() {
        try {
            File f = new File(this.settingsPath.getAbsolutePath() + File.separator + "RenderBoolean.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(Element s : HUDMod.elementInitalizer.getElements()) {
                if(s instanceof RenderElement) {
                    for(Setting set : s.getSettings()) {
                        if(set.getValue() instanceof Boolean) {
                            String v = (Boolean) set.getValue() ? "true" : "false";
                            writer.write(s.getName() + ":" + set.getName() + ":" + v + "\r\n");
                        }
                    }
                }

            }
            writer.close();
        } catch (Exception ignored) {}

        try {
            File f = new File(this.settingsPath.getAbsolutePath() + File.separator + "RenderFloat.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(Element s : HUDMod.elementInitalizer.getElements()) {
                if(s instanceof RenderElement) {
                    for(Setting set : s.getSettings()) {
                        if(set.getValue() instanceof Float) {
                           // String v = (Boolean) set.getValue() ? "true" : "false";
                            float v = (float) set.getValue();
                            writer.write(s.getName() + ":" + set.getName() + ":" + v + "\r\n");
                        }
                    }
                }

            }
            writer.close();
        } catch (Exception ignored) {}

        try {
            File f = new File(this.settingsPath.getAbsolutePath() + File.separator + "RenderDouble.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(Element s : HUDMod.elementInitalizer.getElements()) {
                if(s instanceof RenderElement) {
                    for(Setting set : s.getSettings()) {
                        if(set.getValue() instanceof Double) {
                            // String v = (Boolean) set.getValue() ? "true" : "false";
                            double v = (double) set.getValue();
                            writer.write(s.getName() + ":" + set.getName() + ":" + v + "\r\n");
                        }
                    }
                }

            }
            writer.close();
        } catch (Exception ignored) {}

        try {
            File f = new File(this.settingsPath.getAbsolutePath() + File.separator + "RenderInteger.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(Element s : HUDMod.elementInitalizer.getElements()) {
                if(s instanceof RenderElement) {
                    for(Setting set : s.getSettings()) {
                        if(set.getValue() instanceof Integer) {
                            // String v = (Boolean) set.getValue() ? "true" : "false";
                            int v = (int) set.getValue();
                            writer.write(s.getName() + ":" + set.getName() + ":" + v + "\r\n");
                        }
                    }
                }

            }
            writer.close();
        } catch (Exception ignored) {}

        try {
            File f = new File(this.settingsPath.getAbsolutePath() + File.separator + "RenderEnum.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(Element s : HUDMod.elementInitalizer.getElements()) {
                if(s instanceof RenderElement) {
                    for(Setting set : s.getSettings()) {
                        if(set.getValue() instanceof Enum) {
                            // String v = (Boolean) set.getValue() ? "true" : "false";
                            String v = ((Enum<?>) set.getValue()).name();
                            writer.write(s.getName() + ":" + set.getName() + ":" + v + "\r\n");
                        }
                    }
                }

            }
            writer.close();
        } catch (Exception ignored) {}

        try {
            File f = new File(this.settingsPath.getAbsolutePath() + File.separator + "RenderColor.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            for(Element s : HUDMod.elementInitalizer.getElements()) {
                if(s instanceof RenderElement) {
                    for(Setting set : s.getSettings()) {
                        if(set.getValue() instanceof Color) {
                            // String v = (Boolean) set.getValue() ? "true" : "false";
                            String v = Integer.toString(((Color) set.getValue()).getRGB());
                            int red = ((Color) set.getValue()).getRed();
                            int green = ((Color) set.getValue()).getGreen();
                            int blue = ((Color) set.getValue()).getBlue();
                            int alpha = ((Color) set.getValue()).getAlpha();
                            writer.write(s.getName() + ":" + set.getName() + ":" + red + ":" + green + ":" + blue + ":" + alpha + "\r\n");
                        }
                    }
                }

            }
            writer.close();
        } catch (Exception ignored) {}
    }

    public void loadToggleElementSetting() {
        // BOOLEAN
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.settingsPath.getAbsolutePath() + File.separator + "ToggleBoolean.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    String elementName = parts[0].trim();
                    String settingName = parts[1].trim();
                    String value = parts[2].trim();

                    Element e = HUDMod.elementInitalizer.getElementByName(elementName);
                    if(e != null) {
                        for(Setting s : e.getSettings()) {
                            if(s.getValue() instanceof Boolean) {
                                if(s.getName().equalsIgnoreCase(settingName)) {
                                    s.setValue(value.equalsIgnoreCase("true"));
                                }
                            }
                        }
                    }

                }
            }
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }

        // FLOAT
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.settingsPath.getAbsolutePath() + File.separator + "ToggleFloat.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    String elementName = parts[0].trim();
                    String settingName = parts[1].trim();
                    String value = parts[2].trim();

                    Element e = HUDMod.elementInitalizer.getElementByName(elementName);
                    if(e != null) {
                        for(Setting s : e.getSettings()) {
                            if(s.getValue() instanceof Float) {
                                if(s.getName().equalsIgnoreCase(settingName)) {
                                    s.setValue(Float.valueOf(value));
                                }
                            }
                        }
                    }

                }
            }
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }

        // DOUBLE
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.settingsPath.getAbsolutePath() + File.separator + "ToggleDouble.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    String elementName = parts[0].trim();
                    String settingName = parts[1].trim();
                    String value = parts[2].trim();

                    Element e = HUDMod.elementInitalizer.getElementByName(elementName);
                    if(e != null) {
                        for(Setting s : e.getSettings()) {
                            if(s.getValue() instanceof Double) {
                                if(s.getName().equalsIgnoreCase(settingName)) {
                                    s.setValue(Double.valueOf(value));
                                }
                            }
                        }
                    }

                }
            }
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }

        // INTEGER
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.settingsPath.getAbsolutePath() + File.separator + "ToggleInteger.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    String elementName = parts[0].trim();
                    String settingName = parts[1].trim();
                    String value = parts[2].trim();

                    Element e = HUDMod.elementInitalizer.getElementByName(elementName);
                    if(e != null) {
                        for(Setting s : e.getSettings()) {
                            if(s.getValue() instanceof Integer) {
                                if(s.getName().equalsIgnoreCase(settingName)) {
                                    s.setValue(Integer.valueOf(value));
                                }
                            }
                        }
                    }

                }
            }
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }


        // ENUM
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.settingsPath.getAbsolutePath() + File.separator + "ToggleEnum.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    String elementName = parts[0].trim();
                    String settingName = parts[1].trim();
                    String value = parts[2].trim();

                    Element e = HUDMod.elementInitalizer.getElementByName(elementName);
                    if(e != null) {
                        for(Setting s : e.getSettings()) {
                            if(s.getValue() instanceof Enum) {
                                if(s.getName().equalsIgnoreCase(settingName)) {
                                    Enum<?> v = Enum.valueOf(((Enum<?>) s.getValue()).getClass(), value);
                                    ((Setting<Enum<?>>) s).setValue(v);
                                }
                            }
                        }
                    }

                }
            }
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }

        // COLOR
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.settingsPath.getAbsolutePath() + File.separator + "ToggleColor.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 6) {
                    String elementName = parts[0].trim();
                    String settingName = parts[1].trim();
                    String red = parts[2].trim();
                    String green = parts[3].trim();
                    String blue = parts[4].trim();
                    String alpha = parts[5].trim();


                    Element e = HUDMod.elementInitalizer.getElementByName(elementName);
                    if(e != null) {
                        for(Setting s : e.getSettings()) {
                            if(s.getValue() instanceof Color) {
                                if(s.getName().equalsIgnoreCase(settingName)) {
                                    Color c = new Color(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue), Integer.parseInt(alpha));
                                    s.setValue(c);
                                }
                            }
                        }
                    }

                }
            }
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void loadRenderElementSetting() {

        // BOOLEAN
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.settingsPath.getAbsolutePath() + File.separator + "RenderBoolean.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    String elementName = parts[0].trim();
                    String settingName = parts[1].trim();
                    String value = parts[2].trim();

                    Element e = HUDMod.elementInitalizer.getElementByName(elementName);
                    if(e != null) {
                        for(Setting s : e.getSettings()) {
                            if(s.getValue() instanceof Boolean) {
                                if(s.getName().equalsIgnoreCase(settingName)) {
                                    s.setValue(value.equalsIgnoreCase("true"));
                                }
                            }
                        }
                    }

                }
            }
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }

        // FLOAT
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.settingsPath.getAbsolutePath() + File.separator + "RenderFloat.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    String elementName = parts[0].trim();
                    String settingName = parts[1].trim();
                    String value = parts[2].trim();

                    Element e = HUDMod.elementInitalizer.getElementByName(elementName);
                    if(e != null) {
                        for(Setting s : e.getSettings()) {
                            if(s.getValue() instanceof Float) {
                                if(s.getName().equalsIgnoreCase(settingName)) {
                                    s.setValue(Float.valueOf(value));
                                }
                            }
                        }
                    }

                }
            }
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }

        // DOUBLE
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.settingsPath.getAbsolutePath() + File.separator + "RenderDouble.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    String elementName = parts[0].trim();
                    String settingName = parts[1].trim();
                    String value = parts[2].trim();

                    Element e = HUDMod.elementInitalizer.getElementByName(elementName);
                    if(e != null) {
                        for(Setting s : e.getSettings()) {
                            if(s.getValue() instanceof Double) {
                                if(s.getName().equalsIgnoreCase(settingName)) {
                                    s.setValue(Double.valueOf(value));
                                }
                            }
                        }
                    }

                }
            }
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }

        // INTEGER
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.settingsPath.getAbsolutePath() + File.separator + "RenderInteger.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    String elementName = parts[0].trim();
                    String settingName = parts[1].trim();
                    String value = parts[2].trim();

                    Element e = HUDMod.elementInitalizer.getElementByName(elementName);
                    if(e != null) {
                        for(Setting s : e.getSettings()) {
                            if(s.getValue() instanceof Integer) {
                                if(s.getName().equalsIgnoreCase(settingName)) {
                                    s.setValue(Integer.valueOf(value));
                                }
                            }
                        }
                    }

                }
            }
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }


        // ENUM
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.settingsPath.getAbsolutePath() + File.separator + "RenderEnum.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    String elementName = parts[0].trim();
                    String settingName = parts[1].trim();
                    String value = parts[2].trim();

                    Element e = HUDMod.elementInitalizer.getElementByName(elementName);
                    if(e != null) {
                        for(Setting s : e.getSettings()) {
                            if(s.getValue() instanceof Enum) {
                                if(s.getName().equalsIgnoreCase(settingName)) {
                                    Enum<?> v = Enum.valueOf(((Enum<?>) s.getValue()).getClass(), value);
                                    ((Setting<Enum<?>>) s).setValue(v);
                                }
                            }
                        }
                    }

                }
            }
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }

        // COLOR
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.settingsPath.getAbsolutePath() + File.separator + "RenderColor.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 6) {
                    String elementName = parts[0].trim();
                    String settingName = parts[1].trim();
                    String red = parts[2].trim();
                    String green = parts[3].trim();
                    String blue = parts[4].trim();
                    String alpha = parts[5].trim();


                    Element e = HUDMod.elementInitalizer.getElementByName(elementName);
                    if(e != null) {
                        for(Setting s : e.getSettings()) {
                            if(s.getValue() instanceof Color) {
                                if(s.getName().equalsIgnoreCase(settingName)) {
                                    Color c = new Color(Integer.parseInt(red), Integer.parseInt(green), Integer.parseInt(blue), Integer.parseInt(alpha));
                                    s.setValue(c);
                                }
                            }
                        }
                    }

                }
            }
            reader.close();
        } catch (IOException e) { e.printStackTrace(); }
    }


    public Minecraft getMc() {
        return mc;
    }

    public File getMainFile() {
        return mainFile;
    }

    public File getSettingsPath() {
        return settingsPath;
    }

    public File getElementPath() {
        return elementPath;
    }

    public File getGuiPath() {
        return guiPath;
    }

    public File getSocialPath() {
        return socialPath;
    }
}
