package org.adriandoescoding.cpvp_utils;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.io.File;

public class Constants {
  public static final Identifier NO_TOTEM_ICON = Identifier.of(
    "cpvp_utils",
    "no_totem"
  );
  public static final File CONFIG_DIR = FabricLoader
    .getInstance()
    .getConfigDir()
    .resolve("cpvp_utils")
    .toFile()
    ;
  public static final File CONFIG_FILE = new File(CONFIG_DIR, "cpvp_utils.json");
}
