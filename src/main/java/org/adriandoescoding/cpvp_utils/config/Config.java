package org.adriandoescoding.cpvp_utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.resources.Identifier;
import org.adriandoescoding.cpvp_utils.config.GsonTypeAdapters.ColorTypeAdapter;
import org.adriandoescoding.cpvp_utils.config.GsonTypeAdapters.IdentifierTypeAdapter;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.adriandoescoding.cpvp_utils.Constants.CONFIG_DIR;
import static org.adriandoescoding.cpvp_utils.Constants.CONFIG_FILE;

public class Config {
  private static final Gson gson = new GsonBuilder()
    .registerTypeAdapter(Color.class, new ColorTypeAdapter())
    .registerTypeAdapter(Identifier.class, new IdentifierTypeAdapter())
    .create()
    ;
  private static final Config instance = Config.load();


  public static Config getInstance() {
    return instance;
  }

  public String serialize() {
    return gson.toJson(this, Config.class);
  }

  public static Config load() {
    CONFIG_DIR.mkdirs();
    if (!CONFIG_FILE.exists()) {
      new Config().save();
      return load();
    }
    try {
      String json = Files.readString(CONFIG_FILE.toPath(), StandardCharsets.UTF_8);
      return load(json);
    } catch (IOException e) {
      throw new RuntimeException("Unable to load config from file: %s".formatted(CONFIG_FILE.getAbsolutePath()), e);
    }
  }

  public static Config load(String json) {
    return gson.fromJson(json, Config.class);
  }

  public void save() {
    String json = this.serialize();
    this.save(json);
  }


  public void save(String json) {
    try {
      PrintWriter out = new PrintWriter(CONFIG_FILE);
      out.println(json);
      out.close();
    } catch (Exception e) {
      throw new RuntimeException("Unable to save config to file: %s".formatted(CONFIG_FILE.getAbsolutePath()), e);
    }
  }

  public static void saveInstance() {
    getInstance().save();
  }

  public NoTotemConfig noTotemConfig = new NoTotemConfig(true);
  public HighlightConfig highlightConfig = new HighlightConfig(true);
  public AlwaysShowOffhandSlotConfig alwaysShowOffhandSlot = new AlwaysShowOffhandSlotConfig(true);
}
