package so.born;

import com.google.protobuf.Any;
import com.google.protobuf.util.JsonFormat;
import com.google.protobuf.util.JsonFormat.Printer;
import com.google.protobuf.util.JsonFormat.TypeRegistry;

import so.born.Types.Carousel;
import so.born.Types.Hero;

public class Example {
    public static void main(String[] args) throws Exception {
       Printer printer = JsonFormat.printer()
                .usingTypeRegistry(TypeRegistry.newBuilder()
                        .add(Types.getDescriptor().getMessageTypes())
                        .build());

        Hero hero = Types.Hero.newBuilder()
                .setTitle("Welcome Hero")
                .setSubtitle("Very Interesting")
                .setImage("Beautiful image")
                .setColor("red")
                .addItems(Types.Item.newBuilder()
                        .setTitle("title1"))
                .addItems(Types.Item.newBuilder()
                        .setTitle("title2"))
                .build();
        Carousel carousel = Types.Carousel.newBuilder()
                .setTitle("Just for You")
                .addItems(Types.Item.newBuilder()
                        .setTitle("title1"))
                .addItems(Types.Item.newBuilder()
                        .setTitle("title2"))
                .build();

        Types.Home homeAny = Types.Home.newBuilder()
                .addItems(Any.pack(hero, "home"))
                .addItems(Any.pack(carousel, "home"))
                .build();

        System.out.println(printer.print(homeAny));

        System.out.println("Is Home " + homeAny.getItems(0).is(Types.Home.class));
        System.out.println("Is Hero " + homeAny.getItems(0).is(Types.Hero.class));
        System.out.println(homeAny.getItems(0).getTypeUrl());

        Types.HomeSimple homeSimple = Types.HomeSimple.newBuilder()
                .addItems(Types.ManualAny.newBuilder()
                        .setHero(hero))
                .addItems(Types.ManualAny.newBuilder()
                        .setCarousel(carousel))
                .build();

        System.out.println(printer.print(homeSimple));
    }
}
