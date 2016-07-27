package com.misc_proj.lammi.secretnuclearbaseapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.MapColumns;
import net.simonvt.schematic.annotation.NotifyBulkInsert;
import net.simonvt.schematic.annotation.NotifyDelete;
import net.simonvt.schematic.annotation.NotifyInsert;
import net.simonvt.schematic.annotation.NotifyUpdate;
import net.simonvt.schematic.annotation.TableEndpoint;
import com.misc_proj.lammi.secretnuclearbaseapplication.Database.SecretDatabase.Tables;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Laemmi on 27.7.2016.
 */
@ContentProvider(authority = SecretProvider.AUTHORITY, database = SecretDatabase.class, packageName = "com.misc_proj.lammi.secretnuclearbaseapplication.provider")
public final class SecretProvider {
    public static final String AUTHORITY = "com.misc_proj.lammi.secretnuclearbaseapplication.SecretProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String LISTS = "lists";
        String NOTES = "notes";
        String FROM_LIST = "fromList";
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = Tables.LISTS) public static class Lists {

        @MapColumns
        public static Map<String, String> mapColumns() {
            Map<String, String> map = new HashMap<>();

            map.put(ListColumns.SECRETS, LIST_COUNT);

            return map;
        }

        @ContentUri(
                path = Path.LISTS,
                type = "vnd.android.cursor.dir/list",
                defaultSort = ListColumns.TITLE + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.LISTS);

        @InexactContentUri(
                path = Path.LISTS + "/#",
                name = "LIST_ID",
                type = "vnd.android.cursor.item/list",
                whereColumn = ListColumns.ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(Path.LISTS, String.valueOf(id));
        }

        static final String LIST_COUNT = "(SELECT COUNT(*) FROM "
                + SecretDatabase.SECRETS
                + " WHERE "
                + SecretDatabase.SECRETS
                + "."
                + SecretColumns.LIST_ID
                + "="
                + Tables.LISTS
                + "."
                + ListColumns.ID
                + ")";
    }

    @TableEndpoint(table = SecretDatabase.SECRETS) public static class Notes {

        @ContentUri(
                path = Path.NOTES,
                type = "vnd.android.cursor.dir/note")
        public static final Uri CONTENT_URI = buildUri(Path.NOTES);

        @InexactContentUri(
                name = "NOTE_ID",
                path = Path.NOTES + "/#",
                type = "vnd.android.cursor.item/note",
                whereColumn = SecretColumns.ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(Path.NOTES, String.valueOf(id));
        }

        @InexactContentUri(
                name = "NOTES_FROM_LIST",
                path = Path.NOTES + "/" + Path.FROM_LIST + "/#",
                type = "vnd.android.cursor.dir/list",
                whereColumn = SecretColumns.LIST_ID,
                pathSegment = 2)
        public static Uri fromList(long listId) {
            return buildUri(Path.NOTES, Path.FROM_LIST, String.valueOf(listId));
        }

        @NotifyInsert(paths = Path.NOTES) public static Uri[] onInsert(ContentValues values) {
            final long listId = values.getAsLong(SecretColumns.LIST_ID);
            return new Uri[] {
                    Lists.withId(listId), fromList(listId),
            };
        }

        @NotifyBulkInsert(paths = Path.NOTES)
        public static Uri[] onBulkInsert(Context context, Uri uri, ContentValues[] values, long[] ids) {
            return new Uri[] {
                    uri,
            };
        }

        @NotifyUpdate(paths = Path.NOTES + "/#") public static Uri[] onUpdate(Context context,
                                                                              Uri uri, String where, String[] whereArgs) {
            final long noteId = Long.valueOf(uri.getPathSegments().get(1));
            Cursor c = context.getContentResolver().query(uri, new String[] {
                    SecretColumns.LIST_ID,
            }, null, null, null);
            c.moveToFirst();
            final long listId = c.getLong(c.getColumnIndex(SecretColumns.LIST_ID));
            c.close();

            return new Uri[] {
                    withId(noteId), fromList(listId), Lists.withId(listId),
            };
        }

        @NotifyDelete(paths = Path.NOTES + "/#") public static Uri[] onDelete(Context context,
                                                                              Uri uri) {
            final long noteId = Long.valueOf(uri.getPathSegments().get(1));
            Cursor c = context.getContentResolver().query(uri, null, null, null, null);
            c.moveToFirst();
            final long listId = c.getLong(c.getColumnIndex(SecretColumns.LIST_ID));
            c.close();

            return new Uri[] {
                    withId(noteId), fromList(listId), Lists.withId(listId),
            };
        }
    }
}
