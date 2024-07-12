package pl.nadwey.nadarenas.common.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralConfig extends OkaeriConfig {
    @Comment("Language file")
    @Comment("Loaded from lang/<file>")
    @CustomKey("language-file")
    String languageFile = "en.yml";
}
