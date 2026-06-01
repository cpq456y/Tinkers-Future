package io.github.kelin.tconfuture.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Automatically syncs the built-in resource pack to the game's resourcepacks folder.
 * Only adds missing files, never deletes existing user modifications.
 */
@SideOnly(Side.CLIENT)
public class BuiltinResourcePackInstaller {
    
    private static final Logger LOGGER = LogManager.getLogger("ResourcePackInstaller");
    private static final String RESOURCE_PACK_NAME = "TinkersFuture_Override";
    private static final String SOURCE_ASSETS_PATH = "assets/tconstruct";
    
    public static void install() {
        try {
            File resourcePacksDir = new File(Minecraft.getMinecraft().gameDir, "resourcepacks");
            if (!resourcePacksDir.exists()) {
                resourcePacksDir.mkdirs();
            }
            
            File packDir = new File(resourcePacksDir, RESOURCE_PACK_NAME);
            
            if (!packDir.exists()) {
                packDir.mkdirs();
                LOGGER.info("Created resource pack directory: {}", packDir.getAbsolutePath());
            }
            
            File packMcmetaFile = new File(packDir, "pack.mcmeta");
            if (!packMcmetaFile.exists()) {
                createPackMcmeta(packDir);
            }
            
            syncAssetsToPack(packDir);
            
        } catch (Exception e) {
            LOGGER.error("Failed to sync builtin resource pack", e);
        }
    }
    
    private static void syncAssetsToPack(File packDir) throws IOException {
        File packAssetsDir = new File(packDir, SOURCE_ASSETS_PATH);
        if (!packAssetsDir.exists()) {
            packAssetsDir.mkdirs();
        }
        
        File[] possibleSourceDirs = {
            new File("../src/main/resources/" + SOURCE_ASSETS_PATH),
            new File("src/main/resources/" + SOURCE_ASSETS_PATH),
            new File("../../src/main/resources/" + SOURCE_ASSETS_PATH),
            new File("build/resources/main/" + SOURCE_ASSETS_PATH),
            new File("../build/resources/main/" + SOURCE_ASSETS_PATH),
        };
        
        for (File sourceDir : possibleSourceDirs) {
            if (sourceDir.exists() && sourceDir.isDirectory()) {
                LOGGER.info("Found source directory: {}", sourceDir.getAbsolutePath());
                int added = syncDirectory(sourceDir, packAssetsDir);
                if (added > 0) {
                    LOGGER.info("Added {} new files to resource pack", added);
                } else {
                    LOGGER.info("Resource pack is up to date, no changes needed");
                }
                return;
            }
        }
        
        URL jarUrl = BuiltinResourcePackInstaller.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            File jarOrDir = new File(new URI(jarUrl.toString()));
            if (jarOrDir.isFile() && jarOrDir.getName().endsWith(".jar")) {
                LOGGER.info("Extracting from jar: {}", jarOrDir.getAbsolutePath());
                int added = syncFromJar(jarOrDir, packDir);
                if (added > 0) {
                    LOGGER.info("Added {} new files from jar to resource pack", added);
                } else {
                    LOGGER.info("Resource pack is up to date, no changes needed");
                }
                return;
            }
        } catch (URISyntaxException e) {
            LOGGER.error("Invalid jar URL: {}", jarUrl, e);
        }
        
        LOGGER.error("Could not find source resources directory!");
    }
    
    private static int syncDirectory(File source, File dest) throws IOException {
        int count = 0;
        File[] files = source.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File destDir = new File(dest, file.getName());
                    destDir.mkdirs();
                    count += syncDirectory(file, destDir);
                } else {
                    File destFile = new File(dest, file.getName());
                    if (!destFile.exists()) {
                        FileUtils.copyFile(file, destFile);
                        LOGGER.info("Added new texture: {}", destFile.getName());
                        count++;
                    }
                }
            }
        }
        return count;
    }
    
    private static int syncFromJar(File jarFile, File packDir) throws IOException {
        int count = 0;
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                
                if (entryName.startsWith(SOURCE_ASSETS_PATH + "/")) {
                    File outputFile = new File(packDir, entryName);
                    
                    if (entry.isDirectory()) {
                        outputFile.mkdirs();
                    } else {
                        if (!outputFile.exists()) {
                            outputFile.getParentFile().mkdirs();
                            try (InputStream in = jar.getInputStream(entry);
                                 FileOutputStream out = new FileOutputStream(outputFile)) {
                                IOUtils.copy(in, out);
                                LOGGER.info("Added new texture: {}", entryName);
                                count++;
                            }
                        }
                    }
                }
            }
        }
        return count;
    }
    
    private static void createPackMcmeta(File packDir) throws IOException {
        File packMcmeta = new File(packDir, "pack.mcmeta");
        String packMcmetaContent = "{\n" +
            "  \"pack\": {\n" +
            "    \"pack_format\": 3,\n" +
            "    \"description\": \"TinkersFuture Texture Override Pack\"\n" +
            "  }\n" +
            "}";
        FileUtils.writeStringToFile(packMcmeta, packMcmetaContent, "UTF-8");
    }
}
