package org.adriandoescoding.cpvp_utils;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.IdentifierArgument;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.adriandoescoding.cpvp_utils.config.ArgumentTypes.HexColorArgumentType;
import org.adriandoescoding.cpvp_utils.config.Config;
import org.adriandoescoding.cpvp_utils.config.HighlightedItem;

import java.awt.*;
import java.util.Map;

public class MainClient implements ClientModInitializer {
  private static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandBuildContext dedicated) {
    dispatcher.register(
      LiteralArgumentBuilder
        .<FabricClientCommandSource>literal("cpvp_utils")
        .then(LiteralArgumentBuilder
          .<FabricClientCommandSource>literal("highlight")
          .then(LiteralArgumentBuilder
            .<FabricClientCommandSource>literal("enable")
            .executes((x) -> {
              Config.getInstance().highlightConfig.enable(x);
              return Command.SINGLE_SUCCESS;
            }))
          .then(LiteralArgumentBuilder
            .<FabricClientCommandSource>literal("disable")
            .executes((x) -> {
              Config.getInstance().highlightConfig.disable(x);
              return Command.SINGLE_SUCCESS;
            }))
          .then(LiteralArgumentBuilder
            .<FabricClientCommandSource>literal("toggle")
            .executes((x) -> {
              Config.getInstance().highlightConfig.toggle(x);
              return Command.SINGLE_SUCCESS;
            }))
          .then(LiteralArgumentBuilder
            .<FabricClientCommandSource>literal("set")
            .then(ClientCommandManager
              .argument("item", IdentifierArgument.id())
              .suggests((ctx, builder) ->
                SharedSuggestionProvider.suggest(
                  BuiltInRegistries.ITEM
                    .keySet()
                    .stream()
                    .map(Identifier::toString),
                  builder
                ))
              .then(ClientCommandManager
                .argument("color", HexColorArgumentType.hexColor())
                .executes(ctx -> {
                  Identifier item = ctx.getArgument("item", Identifier.class);
                  Color color = new Color(ctx.getArgument("color", Integer.class));

                  Config.getInstance().highlightConfig.set(item, color);
                  return Command.SINGLE_SUCCESS;
                }))))
          .then(LiteralArgumentBuilder
            .<FabricClientCommandSource>literal("remove")
            .then(ClientCommandManager
              .argument("item", IdentifierArgument.id())
              .suggests((ctx, builder) -> SharedSuggestionProvider.suggest(
                Config.getInstance().highlightConfig.getKeys(), builder
              ))
              .executes(ctx -> {
                Identifier item = ctx.getArgument("item", Identifier.class);

                if (!Config.getInstance().highlightConfig.remove(item)) {
                  throw new IllegalArgumentException("Nothing changed.");
                }
                return Command.SINGLE_SUCCESS;
              })))
          .then(LiteralArgumentBuilder
            .<FabricClientCommandSource>literal("list")
            .executes(x -> {
              for (Map.Entry<Identifier, Color> entry : Config.getInstance().highlightConfig.getEntries()) {
                HighlightedItem item = new HighlightedItem(entry.getKey(), entry.getValue());
                x
                  .getSource()
                  .sendFeedback(item.toText());
              }

              return Command.SINGLE_SUCCESS;
            }))
          .then(LiteralArgumentBuilder
            .<FabricClientCommandSource>literal("get")
            .then(ClientCommandManager
              .argument("item", IdentifierArgument.id())
              .executes(x -> {
                Identifier item = x.getArgument("item", Identifier.class);
                if (Config.getInstance().highlightConfig.isAvailable(item)) {
                  throw new IllegalArgumentException("No such item found.");
                }
                HighlightedItem highlightedItem = HighlightedItem.of(item, Config.getInstance().highlightConfig.get(item));
                x
                  .getSource()
                  .sendFeedback(highlightedItem.toText());
                return Command.SINGLE_SUCCESS;

              })))
        )

        .then(LiteralArgumentBuilder
          .<FabricClientCommandSource>literal("no_totem_icon")
          .then(LiteralArgumentBuilder
            .<FabricClientCommandSource>literal("enable")
            .executes((x) -> {
              Config.getInstance().noTotemConfig.enable(x);
              return Command.SINGLE_SUCCESS;
            }))
          .then(LiteralArgumentBuilder
            .<FabricClientCommandSource>literal("disable")
            .executes((x) -> {
              Config.getInstance().noTotemConfig.disable(x);
              return Command.SINGLE_SUCCESS;
            }))
          .then(LiteralArgumentBuilder
            .<FabricClientCommandSource>literal("toggle")
            .executes((x) -> {
              Config.getInstance().noTotemConfig.toggle(x);
              return Command.SINGLE_SUCCESS;
            }))
        )
        .then(LiteralArgumentBuilder
          .<FabricClientCommandSource>literal("always_show_offhand_slot")
          .then(LiteralArgumentBuilder
            .<FabricClientCommandSource>literal("enable")
            .executes((x) -> {
              Config.getInstance().alwaysShowOffhandSlot.enable(x);
              return Command.SINGLE_SUCCESS;
            }))
          .then(LiteralArgumentBuilder
            .<FabricClientCommandSource>literal("disable")
            .executes((x) -> {
              Config.getInstance().alwaysShowOffhandSlot.disable(x);
              return Command.SINGLE_SUCCESS;
            }))
          .then(LiteralArgumentBuilder
            .<FabricClientCommandSource>literal("toggle")
            .executes((x) -> {
              Config.getInstance().alwaysShowOffhandSlot.toggle(x);
              return Command.SINGLE_SUCCESS;
            }))
        )
    );
  }

  @Override
  public void onInitializeClient() {
    ClientCommandRegistrationCallback.EVENT.register(MainClient::register);
  }
}
