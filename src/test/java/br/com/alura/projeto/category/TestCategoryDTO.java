package br.com.alura.projeto.category;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCategoryDTO {


    public static final String REGEX_COLOR = "^#([0-9A-Fa-f]{6}|[0-9A-Fa-f]{3})$";

    @Test
    public void testColor__pattern_validation___should_be_successful() {
        var validOptions = List.of(
            "#FFFFFF",
            "#000000",
            "#ABC",
            "#123456",
            "#00C86F",
            "#6BD1FF",
            "#9CD33B",
            "#7B71FF",
            "#F16165",
            "#DC6EBE",
            "#FFBA05",
            "#FF8C2A",
            "#00C86F",
            "#6BD1FF",
            "#9CD33B",
            "#7B71FF",
            "#F16165",
            "#DC6EBE",
            "#FFBA05",
            "#FF8C2A"
        );
        assertAll(() -> validOptions.forEach(
            option -> assertTrue(
                option.matches(REGEX_COLOR),
                () -> option + " invalid value"
            )
        ));
    }

    @Test
    public void testColor__pattern_validation___should_fail() {
        var validOptions = List.of(
            "#gGgGgG",
            "#HhhHhh",
            "#iiIiIi",
            "#JjJjjJ",
            "#kKkKKk",
            "#llLllL",
            "#mMmmMm",
            "#nNNnNN",
            "#OoOoOo",
            "#pPPpPp",
            "#qqQqqQ",
            "#RrrrRr",
            "#SSSsSS",
            "#TtTTtt",
            "#TtTTtt",
            "#xxXxxX",
            "#yYyyYy",
            "#wWwwWw",
            "#zzZzZZ",
            "FFFFFF",
            "FFFFFF",
            "000000",
            "#.ABC",
            "##FFFFFF",
            "&FFFFFF",
            "&000000",
            "$FFFFFF",
            "$000000",
            "$FFFFFF",
            "#FFFFFFF",
            "#FFFFF",
            "#FFFF",
            " #FFFFFF"
        );
        assertAll(() -> validOptions.forEach(
            option -> assertFalse(
                option.matches(REGEX_COLOR),
                () -> option + " is a valid value"
            )
        ));
    }

}
