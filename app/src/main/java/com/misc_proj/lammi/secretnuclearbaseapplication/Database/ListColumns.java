package com.misc_proj.lammi.secretnuclearbaseapplication.Database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;
/**
 * Created by Laemmi on 27.7.2016.
 */
public interface ListColumns extends TitleColumn {

    @DataType(INTEGER) @PrimaryKey @AutoIncrement String ID = "_id";

    String SECRETS = "secrets";
}
