package com.misc_proj.lammi.secretnuclearbaseapplication.Database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.Check;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;
import com.misc_proj.lammi.secretnuclearbaseapplication.Database.SecretDatabase.Tables;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;
/**
 * Created by Laemmi on 27.7.2016.
 */
public interface SecretColumns {
    String STATUS_NEW = "new";
    String STATUS_COMPLETED = "completed";

    @DataType(INTEGER) @PrimaryKey
    @AutoIncrement
    String ID = "_id";

    @DataType(INTEGER) @References(table = SecretDatabase.Tables.LISTS, column = ListColumns.ID) String LIST_ID =
            "listId";

    @DataType(TEXT) String NOTE = "note";

    @DataType(TEXT)
    @Check(SecretColumns.STATUS + " in ('" + SecretColumns.STATUS_NEW + "', '"
            + SecretColumns.STATUS_COMPLETED + "')")
    String STATUS = "status";
}
