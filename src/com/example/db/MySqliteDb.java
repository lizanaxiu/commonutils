package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteDb extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "user_db";
	public static final int VERSION = 1;
	public static final String CONTENT = "content";
	public static final String ID = "id";
	public static final String AGE = "age";

	public MySqliteDb(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	/*
	 * 参数说明 ：上下文对象 数据库表名 数据库工厂类 可以为null 数据库版本
	 * 如果需要对数据库进行升级处理的话，只需要将version的值设置成比之前版本的值大就可以了
	 * 该类会自动去检测，然后调用onUpgrade方法进行数据库的升级处理 super(context, name, factory,
	 * version);
	 */
	public MySqliteDb(Context context) {
		super(context, TABLE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + TABLE_NAME + "(" + ID
				+ "INTEGER primary key autoincrement," + AGE + " text,"
				+ CONTENT + " text)";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		deleteTable(db);
	}

	/**
	 * 往数据库添加数据
	 */
	public void insert() {

	}

	/**
	 * 增删数据库中字段 alter table test add ID CHAR(1) 这样就行 增加数据库列表的字段 alter table test
	 * drop column id 删除数据库中已经有的某个字段
	 */
	public void updateFieldToTable(Context context) {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "alter table " + TABLE_NAME + " add des char";
		db.execSQL(sql);
	}

	public void updateTable(SQLiteDatabase db) {
		// ALTER TABLE mytable ADD time TEXT
		// String sql="alter table "+TABLE_NAME+" add fid char(1)";
		String sql = "ALTER TABLE " + TABLE_NAME + " ADD fid TEXT";
		db.execSQL(sql);
	}

	/**
	 * 更新数据库的数据
	 */
	public void update() {

	}

	/**
	 * 从数据库查询数据 查询全部 查询语句：select * from 表名 where 条件子句 group by 分组子句 having…order
	 * by 排序子句。 分页语句：select * from 表名 limit 记录数 offset 开始位置 或者 select * from 表名
	 * limit 开始位置， (有待验证)
	 */
	public void select(Context context) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "select * from " + TABLE_NAME + "order by " + ID + "desc";
		db.execSQL(sql);
	}

	/**
	 * 从数据库查询数据
	 */
	public void select2() {

	}

	/**
	 * 删除数据库某条数据 删除语句：delete from 表名 where 条件子句
	 */
	public void delete(String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = ID + "=?";
		String[] values = { id };
		db.delete(TABLE_NAME, where, values);
	}

	/**
	 * 删除数据库某条数据 删除语句：delete from 表名 where 条件子句 (有待验证)
	 */
	public void delete2(String id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "delete from" + TABLE_NAME + "where id=" + id;
		db.execSQL(sql);
	}

	/**
	 * 更新数据库表
	 */
	public void updateTable() {

	}

	/**
	 * 删除已经存在的数据库表
	 */
	public void deleteTable(SQLiteDatabase db) {
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
	}

	/**
	 * 创建数据库表
	 * 
	 * @param name
	 * @param db
	 */
	public void createTable(String name, SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE_NAME + "(" + ID
				+ "INTEGER primary key autoincrement, " + CONTENT + " text,"
				+ AGE + " INTEGER)";
		db.execSQL(sql);
	}
}
