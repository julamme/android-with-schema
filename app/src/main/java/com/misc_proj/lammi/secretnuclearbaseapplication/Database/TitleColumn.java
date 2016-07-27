package com.misc_proj.lammi.secretnuclearbaseapplication.Database;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;

import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by Laemmi on 27.7.2016.
 */
    public interface TitleColumn {


        @DataType(TEXT) @NotNull
        String TITLE = "title";
    }

