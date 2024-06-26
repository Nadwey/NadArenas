/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables;


import java.util.Collection;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.generated.DefaultSchema;
import org.jooq.generated.Keys;
import org.jooq.generated.tables.records.SpawnRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Spawn extends TableImpl<SpawnRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>SPAWN</code>
     */
    public static final Spawn SPAWN = new Spawn();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SpawnRecord> getRecordType() {
        return SpawnRecord.class;
    }

    /**
     * The column <code>SPAWN.ID</code>.
     */
    public final TableField<SpawnRecord, Integer> ID = createField(DSL.name("ID"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>SPAWN.ARENA_ID</code>.
     */
    public final TableField<SpawnRecord, Integer> ARENA_ID = createField(DSL.name("ARENA_ID"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>SPAWN.RELATIVE_X</code>.
     */
    public final TableField<SpawnRecord, Double> RELATIVE_X = createField(DSL.name("RELATIVE_X"), SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * The column <code>SPAWN.RELATIVE_Y</code>.
     */
    public final TableField<SpawnRecord, Double> RELATIVE_Y = createField(DSL.name("RELATIVE_Y"), SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * The column <code>SPAWN.RELATIVE_Z</code>.
     */
    public final TableField<SpawnRecord, Double> RELATIVE_Z = createField(DSL.name("RELATIVE_Z"), SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * The column <code>SPAWN.YAW</code>.
     */
    public final TableField<SpawnRecord, Double> YAW = createField(DSL.name("YAW"), SQLDataType.DOUBLE.nullable(false), this, "");

    /**
     * The column <code>SPAWN.PITCH</code>.
     */
    public final TableField<SpawnRecord, Double> PITCH = createField(DSL.name("PITCH"), SQLDataType.DOUBLE.nullable(false), this, "");

    private Spawn(Name alias, Table<SpawnRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Spawn(Name alias, Table<SpawnRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>SPAWN</code> table reference
     */
    public Spawn(String alias) {
        this(DSL.name(alias), SPAWN);
    }

    /**
     * Create an aliased <code>SPAWN</code> table reference
     */
    public Spawn(Name alias) {
        this(alias, SPAWN);
    }

    /**
     * Create a <code>SPAWN</code> table reference
     */
    public Spawn() {
        this(DSL.name("SPAWN"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<SpawnRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_4;
    }

    @Override
    public Spawn as(String alias) {
        return new Spawn(DSL.name(alias), this);
    }

    @Override
    public Spawn as(Name alias) {
        return new Spawn(alias, this);
    }

    @Override
    public Spawn as(Table<?> alias) {
        return new Spawn(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Spawn rename(String name) {
        return new Spawn(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Spawn rename(Name name) {
        return new Spawn(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Spawn rename(Table<?> name) {
        return new Spawn(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Spawn where(Condition condition) {
        return new Spawn(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Spawn where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Spawn where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Spawn where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Spawn where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Spawn where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Spawn where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Spawn where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Spawn whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Spawn whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
