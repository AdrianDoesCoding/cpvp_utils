package org.adriandoescoding.cpvp_utils.config.ArgumentTypes;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.awt.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;

public class HexColorArgumentType implements ArgumentType<Integer> {
  public static final Map<String, Color> ColorMap = new HashMap<>() {{
    put("black", new Color(0x000000));
    put("silver", new Color(0xc0c0c0));
    put("gray", new Color(0x808080));
    put("white", new Color(0xffffff));
    put("maroon", new Color(0x800000));
    put("red", new Color(0xff0000));
    put("purple", new Color(0x800080));
    put("fuchsia", new Color(0xff00ff));
    put("green", new Color(0x008000));
    put("lime", new Color(0x00ff00));
    put("olive", new Color(0x808000));
    put("yellow", new Color(0xffff00));
    put("navy", new Color(0x000080));
    put("blue", new Color(0x0000ff));
    put("teal", new Color(0x008080));
    put("aqua", new Color(0x00ffff));
    put("aliceblue", new Color(0xf0f8ff));
    put("antiquewhite", new Color(0xfaebd7));
    put("aquamarine", new Color(0x7fffd4));
    put("azure", new Color(0xf0ffff));
    put("beige", new Color(0xf5f5dc));
    put("bisque", new Color(0xffe4c4));
    put("blanchedalmond", new Color(0xffebcd));
    put("blueviolet", new Color(0x8a2be2));
    put("brown", new Color(0xa52a2a));
    put("burlywood", new Color(0xdeb887));
    put("cadetblue", new Color(0x5f9ea0));
    put("chartreuse", new Color(0x7fff00));
    put("chocolate", new Color(0xd2691e));
    put("coral", new Color(0xff7f50));
    put("cornflowerblue", new Color(0x6495ed));
    put("cornsilk", new Color(0xfff8dc));
    put("crimson", new Color(0xdc143c));
    put("cyan", new Color(0x00ffff));
    put("darkblue", new Color(0x00008b));
    put("darkcyan", new Color(0x008b8b));
    put("darkgoldenrod", new Color(0xb8860b));
    put("darkgray", new Color(0xa9a9a9));
    put("darkgreen", new Color(0x006400));
    put("darkgrey", new Color(0xa9a9a9));
    put("darkkhaki", new Color(0xbdb76b));
    put("darkmagenta", new Color(0x8b008b));
    put("darkolivegreen", new Color(0x556b2f));
    put("darkorange", new Color(0xff8c00));
    put("darkorchid", new Color(0x9932cc));
    put("darkred", new Color(0x8b0000));
    put("darksalmon", new Color(0xe9967a));
    put("darkseagreen", new Color(0x8fbc8f));
    put("darkslateblue", new Color(0x483d8b));
    put("darkslategray", new Color(0x2f4f4f));
    put("darkslategrey", new Color(0x2f4f4f));
    put("darkturquoise", new Color(0x00ced1));
    put("darkviolet", new Color(0x9400d3));
    put("deeppink", new Color(0xff1493));
    put("deepskyblue", new Color(0x00bfff));
    put("dimgray", new Color(0x696969));
    put("dimgrey", new Color(0x696969));
    put("dodgerblue", new Color(0x1e90ff));
    put("firebrick", new Color(0xb22222));
    put("floralwhite", new Color(0xfffaf0));
    put("forestgreen", new Color(0x228b22));
    put("gainsboro", new Color(0xdcdcdc));
    put("ghostwhite", new Color(0xf8f8ff));
    put("gold", new Color(0xffd700));
    put("goldenrod", new Color(0xdaa520));
    put("greenyellow", new Color(0xadff2f));
    put("grey", new Color(0x808080));
    put("honeydew", new Color(0xf0fff0));
    put("hotpink", new Color(0xff69b4));
    put("indianred", new Color(0xcd5c5c));
    put("indigo", new Color(0x4b0082));
    put("ivory", new Color(0xfffff0));
    put("khaki", new Color(0xf0e68c));
    put("lavender", new Color(0xe6e6fa));
    put("lavenderblush", new Color(0xfff0f5));
    put("lawngreen", new Color(0x7cfc00));
    put("lemonchiffon", new Color(0xfffacd));
    put("lightblue", new Color(0xadd8e6));
    put("lightcoral", new Color(0xf08080));
    put("lightcyan", new Color(0xe0ffff));
    put("lightgoldenrodyellow", new Color(0xfafad2));
    put("lightgray", new Color(0xd3d3d3));
    put("lightgreen", new Color(0x90ee90));
    put("lightgrey", new Color(0xd3d3d3));
    put("lightpink", new Color(0xffb6c1));
    put("lightsalmon", new Color(0xffa07a));
    put("lightseagreen", new Color(0x20b2aa));
    put("lightskyblue", new Color(0x87cefa));
    put("lightslategray", new Color(0x778899));
    put("lightslategrey", new Color(0x778899));
    put("lightsteelblue", new Color(0xb0c4de));
    put("lightyellow", new Color(0xffffe0));
    put("limegreen", new Color(0x32cd32));
    put("linen", new Color(0xfaf0e6));
    put("magenta", new Color(0xff00ff));
    put("mediumaquamarine", new Color(0x66cdaa));
    put("mediumblue", new Color(0x0000cd));
    put("mediumorchid", new Color(0xba55d3));
    put("mediumpurple", new Color(0x9370db));
    put("mediumseagreen", new Color(0x3cb371));
    put("mediumslateblue", new Color(0x7b68ee));
    put("mediumspringgreen", new Color(0x00fa9a));
    put("mediumturquoise", new Color(0x48d1cc));
    put("mediumvioletred", new Color(0xc71585));
    put("midnightblue", new Color(0x191970));
    put("mintcream", new Color(0xf5fffa));
    put("mistyrose", new Color(0xffe4e1));
    put("moccasin", new Color(0xffe4b5));
    put("navajowhite", new Color(0xffdead));
    put("oldlace", new Color(0xfdf5e6));
    put("olivedrab", new Color(0x6b8e23));
    put("orange", new Color(0xffa500));
    put("orangered", new Color(0xff4500));
    put("orchid", new Color(0xda70d6));
    put("palegoldenrod", new Color(0xeee8aa));
    put("palegreen", new Color(0x98fb98));
    put("paleturquoise", new Color(0xafeeee));
    put("palevioletred", new Color(0xdb7093));
    put("papayawhip", new Color(0xffefd5));
    put("peachpuff", new Color(0xffdab9));
    put("peru", new Color(0xcd853f));
    put("pink", new Color(0xffc0cb));
    put("plum", new Color(0xdda0dd));
    put("powderblue", new Color(0xb0e0e6));
    put("rebeccapurple", new Color(0x663399));
    put("rosybrown", new Color(0xbc8f8f));
    put("royalblue", new Color(0x4169e1));
    put("saddlebrown", new Color(0x8b4513));
    put("salmon", new Color(0xfa8072));
    put("sandybrown", new Color(0xf4a460));
    put("seagreen", new Color(0x2e8b57));
    put("seashell", new Color(0xfff5ee));
    put("sienna", new Color(0xa0522d));
    put("skyblue", new Color(0x87ceeb));
    put("slateblue", new Color(0x6a5acd));
    put("slategray", new Color(0x708090));
    put("slategrey", new Color(0x708090));
    put("snow", new Color(0xfffafa));
    put("springgreen", new Color(0x00ff7f));
    put("steelblue", new Color(0x4682b4));
    put("tan", new Color(0xd2b48c));
    put("thistle", new Color(0xd8bfd8));
    put("tomato", new Color(0xff6347));
    put("turquoise", new Color(0x40e0d0));
    put("violet", new Color(0xee82ee));
    put("wheat", new Color(0xf5deb3));
    put("whitesmoke", new Color(0xf5f5f5));
    put("yellowgreen", new Color(0x9acd32));
  }};


  public static final DynamicCommandExceptionType INVALID_COLOR_EXCEPTION = new DynamicCommandExceptionType(
    x -> Component.translatableEscape("cpvp.options.color.invalid", x)
  );

  private HexColorArgumentType() {
  }

  public static HexColorArgumentType hexColor() {
    return new HexColorArgumentType();
  }


  public Integer parse(StringReader stringReader) throws CommandSyntaxException {
    String string = stringReader.readString();
    if (string.startsWith("x")) {
      String hex = string.substring(1);
      int length = hex.length();
      if (length == 3) {
        return ARGB.color(
          expand(Integer.parseInt(hex, 0, 1, 16)), expand(Integer.parseInt(hex, 1, 2, 16)), expand(Integer.parseInt(hex, 2, 3, 16))
        );
      }
      if (length == 6) {
        return ARGB.color(Integer.parseInt(hex, 0, 2, 16), Integer.parseInt(hex, 2, 4, 16), Integer.parseInt(hex, 4, 6, 16));
      }

    }
    if (ColorMap.containsKey(string)) {
      Color color = ColorMap.get(string);
      return ARGB.color(color.getRed(), color.getGreen(), color.getBlue());
    }
    throw INVALID_COLOR_EXCEPTION.create(string);
  }

  private static int expand(int digit) {
    return digit * 17;
  }

  @Override
  public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
    String typed = builder.getRemainingLowerCase();
    if (typed.startsWith("x")) {
      if (typed.length() < 7) {
        for (String character : new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"}) {
          builder.suggest("x%s%s".formatted(
            typed
              .substring(1)
              .toUpperCase(), character
          ));
        }
      }
    } else {
      builder.suggest("x");
    }
    SharedSuggestionProvider.suggest(ColorMap.keySet(), builder);
    return builder.buildFuture();
  }
}
